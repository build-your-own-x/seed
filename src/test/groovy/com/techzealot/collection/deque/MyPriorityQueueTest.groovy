package com.techzealot.collection.deque

import com.techzealot.collection.MyAbstractCollectionExtensions
import com.techzealot.collection.MyCollection
import com.techzealot.collection.list.MyArrayList
import com.techzealot.collection.list.MyLinkedList
import org.joor.Reflect
import spock.lang.Ignore
import spock.lang.Specification
import spock.util.mop.Use

@Use(MyAbstractCollectionExtensions)
class MyPriorityQueueTest extends Specification {

    def "test constructor()"() {
        when:
        def pq = new MyPriorityQueue()
        then:
        pq.size() == 0
        pq.capacity() == 11
        pq.comparator() == null
    }

    def "test constructor(int>0)"(int initialCapacity, int expectedCapacity) {
        when:
        def pq = new MyPriorityQueue(initialCapacity)
        then:
        pq.size() == 0
        pq.capacity() == expectedCapacity
        pq.comparator() == null
        where:
        initialCapacity | expectedCapacity
        1               | 1
        10              | 10
        10 * 10         | 10 * 10
    }

    def "test constructor(int<=0)"(int initialCapacity) {
        when:
        def pq = new MyPriorityQueue(initialCapacity)
        then:
        thrown(IllegalArgumentException)
        where:
        initialCapacity | _
        0               | _
        -1              | _
        -10 * 10        | _
    }

    def "test constructor(Comparator)"(Comparator cmp, Comparator expectedCmp) {
        when:
        def pq = new MyPriorityQueue((Comparator) cmp)
        then:
        pq.size() == 0
        pq.capacity() == 11
        pq.comparator() == expectedCmp
        where:
        cmp                       | expectedCmp
        null                      | null
        Comparator.naturalOrder() | Comparator.naturalOrder()
        Comparator.reverseOrder() | Comparator.reverseOrder()
    }

    def "test constructor(int,Comparator)"(int initialCapacity, Comparator cmp, int expectedCapacity, Comparator expectedCmp) {
        when:
        def pq = new MyPriorityQueue(initialCapacity, cmp)
        then:
        pq.size() == 0
        pq.capacity() == expectedCapacity
        pq.comparator() == expectedCmp
        where:
        initialCapacity | cmp                       | expectedCapacity | expectedCmp
        10              | null                      | 10               | null
        10              | Comparator.naturalOrder() | 10               | Comparator.naturalOrder()
        10              | Comparator.reverseOrder() | 10               | Comparator.reverseOrder()
    }

    //todo add MySortedSet
    def "test constructor(MyCollection:no null)"(MyCollection c, List list) {
        when:
        //需要向上转型否则会调错构造函数
        def pq = new MyPriorityQueue(c as MyCollection)
        then:
        pq.size() == c.size()
        pq.capacity() == (c.size() == 0 ? 1 : c.size())
        pq.eq(list)
        where:
        c                                      | list
        new MyArrayList()                      | []
        MyArrayList.of(1)                      | [1]
        MyArrayList.of(*5..1)                  | [1, 2, 3, 5, 4]
        MyLinkedList.of(*5..1)                 | [1, 2, 3, 5, 4]
        MyPriorityQueue.of(*5..1)              | [1, 2, 3, 5, 4]
        MyArrayList.of(5, 5, 4, 3, 3, 2, 1, 1) | [1, 3, 1, 5, 3, 2, 4, 5]
    }

    //todo add MySortedSet
    def "test constructor(MyCollection:contains null)"(MyCollection c) {
        when:
        def pq = new MyPriorityQueue(c)
        then:
        thrown(NullPointerException)
        where:
        c                                | _
        MyArrayList.of([null].toArray()) | _
        MyArrayList.of(*1..10, null)     | _
    }

    def "test constructor(MyCollection:non-Comparable)"(MyCollection c) {
        when:
        def pq = new MyPriorityQueue(c)
        then:
        thrown(ClassCastException)
        where:
        c                                          | _
        MyArrayList.of(*1..10, new Object())       | _
        MyArrayList.of(new Object(), new Object()) | _
    }

    //todo
    @Ignore("TODO")
    def "test constructor(MySortedSet:no null)"() {

    }

    //todo
    @Ignore("TODO")
    def "test constructor(MySortedSet:contains null)"() {

    }

    def "test constructor(MyPriorityQueue)"(MyPriorityQueue mpq, int expectedCapacity, List list) {
        when:
        def pq = new MyPriorityQueue(mpq)
        then:
        pq.size() == mpq.size()
        pq.capacity() == expectedCapacity
        pq.eq(list)
        where:
        mpq                       | expectedCapacity | list
        new MyPriorityQueue()     | 1                | []
        MyPriorityQueue.of(*5..1) | 5                | [1, 2, 3, 5, 4]
    }

    def "test capacity"(MyPriorityQueue mpq, int expectedCapacity) {
        when:
        def capacity = mpq.capacity()
        then:
        capacity == expectedCapacity
        where:
        mpq                                         | expectedCapacity
        new MyPriorityQueue()                       | 11
        new MyPriorityQueue(new MyArrayList())      | 1
        new MyPriorityQueue(MyArrayList.of(*1..10)) | 10
    }

    def "test offer"(MyPriorityQueue mpq, List added, int expectedSize, List list) {
        when:
        for (final def a in added) {
            mpq.add(a)
        }
        then:
        mpq.size() == expectedSize
        mpq.eq(list)
        where:
        mpq                                            | added         | expectedSize | list
        new MyPriorityQueue()                          | [5, 2]        | 2            | [2, 5]
        MyPriorityQueue.of(*5..1)                      | [5, 2]        | 7            | [1, 2, 2, 5, 4, 5, 3]
        new MyPriorityQueue(Comparator.naturalOrder()) | [*5..1, 5, 2] | 7            | [1, 2, 2, 5, 3, 5, 4]
    }

    def "test poll"() {
        when:
        def pq = new MyPriorityQueue<>(Comparator.naturalOrder())
        (5..1).each {
            pq.add(it)
        }
        then:
        pq.poll() == 1
        pq.poll() == 2
        pq.poll() == 3
        pq.poll() == 4
        pq.poll() == 5
        pq.poll() == null
    }

    def "test remove"() {
        when:
        def pq = MyPriorityQueue.of(*5..1)
        then:
        pq.remove() == 1
        pq.remove() == 2
        pq.remove() == 3
        pq.remove() == 4
        pq.remove() == 5
        when:
        pq.remove()
        then:
        thrown(NoSuchElementException)
    }

    def "test remove(Object)"() {
        when:
        def pq = MyPriorityQueue.of(1, 2, 20, 3, 4, 21, 22, 5, 6, 7, 8, 23, 24, 26, 25, 9, 30)
        then: "return null"
        !pq.remove(10000)
        !pq.remove(null)
        !pq.remove(new Object())
        then: "siftDown"
        pq.remove(22)
        pq.eq([1, 2, 20, 3, 4, 21, 25, 5, 6, 7, 8, 23, 24, 26, 30, 9])
        then: "siftUp"
        pq.remove(25)
        pq.eq([1, 2, 9, 3, 4, 21, 20, 5, 6, 7, 8, 23, 24, 26, 30])
        when: "siftDownComparing:right>left"
        def pqc = new MyPriorityQueue(Comparator.naturalOrder())
        pqc.addAll(MyArrayList.of(1, 3, 2, 5, 4))
        pqc.poll()
        then:
        pqc.eq([2, 3, 4, 5])
    }

    def "test peek"() {
        when:
        def pq = MyPriorityQueue.of(*5..1)
        then:
        pq.peek() == 1
        pq.poll() == 1
        pq.peek() == 2
        pq.poll() == 2
        pq.peek() == 3
        pq.poll() == 3
        pq.peek() == 4
        pq.poll() == 4
        pq.peek() == 5
        pq.poll() == 5
        pq.peek() == null
        pq.poll() == null
    }

    def "test element"() {
        when:
        def pq = MyPriorityQueue.of(*5..1)
        then:
        pq.element() == 1
        pq.poll() == 1
        pq.element() == 2
        pq.poll() == 2
        pq.element() == 3
        pq.poll() == 3
        pq.element() == 4
        pq.poll() == 4
        pq.element() == 5
        pq.poll() == 5
        pq.poll() == null
        when:
        pq.element()
        then:
        thrown(NoSuchElementException)

    }

    def "test clear"() {
        when:
        def empty = new MyPriorityQueue()
        def mpq = MyPriorityQueue.of(*5..1)
        then:
        mpq.eq([1, 2, 3, 5, 4])
        when:
        mpq.clear()
        then:
        mpq.eq([])
        mpq.capacity() == 5
        mpq.size() == 0
        when:
        empty.clear()
        then:
        empty.size() == 0
        empty.eq([])
    }

    def "test iterator:iterate"() {
        given:
        def pq = MyPriorityQueue.of(*5..1)
        def it = pq.iterator()
        def out = []
        when:
        while (it.hasNext()) {
            out += it.next()
        }
        then:
        out == [1, 2, 3, 5, 4]
    }

    def "test iterator:remove"() {
        given:
        def pq = MyPriorityQueue.of(1, 2, 20, 3, 4, 21, 22, 5, 6, 7, 8, 23, 24, 26, 25, 9, 30)
        def it = pq.iterator()
        when:
        while (it.hasNext()) {
            def e = it.next()
            //22:删除后替换元素在未遍历部分
            //25:删除后替换元素在已遍历部分
            //9:删除在已遍历部分元素
            if (e == 22 || e == 25 || e == 9) {
                it.remove()
            }
        }
        then:
        pq.eq([1, 2, 20, 3, 4, 21, 26, 5, 6, 7, 8, 23, 24, 30])
    }

    def "test iterator:forEachRemaining"() {
        given:
        def pq = MyPriorityQueue.of(*5..1)
        def it = pq.iterator()
        def out = []
        when:
        while (it.hasNext()) {
            if (it.next() == 3) {
                break;
            }
        }
        it.forEachRemaining {
            out += it
        }
        then:
        out == [5, 4]
    }

    def "test replace"() {
        when:
        def pq = MyPriorityQueue.of(*5..1)
        then:
        pq.eq([1, 2, 3, 5, 4])
        pq.replace(6) == 1
        pq.eq([2, 4, 3, 5, 6])
        pq.replace(2) == 2
        pq.eq([2, 4, 3, 5, 6])
        when:
        pq.replace(null)
        then:
        thrown(NullPointerException)
        when: "size=0"
        def pq0 = new MyPriorityQueue()
        pq0.replace(1)
        then:
        pq0.eq([1])
        when:
        pq0.replace(null)
        then:
        thrown(NullPointerException)
    }

    def "test hugeCapacity"(int minCapacity, int expected) {
        given:
        def mpq = Reflect.onClass(MyPriorityQueue.class)
        when:
        def capacity = mpq.call("hugeCapacity", minCapacity).get()
        then:
        capacity == expected
        where:
        minCapacity                   | expected
        Integer.MAX_VALUE - 4         | Integer.MAX_VALUE
        (Integer.MAX_VALUE >> 1) + 10 | Integer.MAX_VALUE - 8
    }
}
