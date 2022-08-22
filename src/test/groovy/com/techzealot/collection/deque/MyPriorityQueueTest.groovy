package com.techzealot.collection.deque

import com.techzealot.collection.MyCollection
import com.techzealot.collection.list.MyArrayList
import spock.lang.Ignore
import spock.lang.Specification

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
        def pq = new MyPriorityQueue(c)
        then:
        pq.size() == c.size()
        pq.capacity() == (c.size() == 0 ? 1 : c.size())
        pq.toArray() == list.toArray()
        where:
        c                                      | list
        new MyArrayList()                      | []
        MyArrayList.of(1)                      | [1]
        MyArrayList.of(*5..1)                  | [1, 2, 3, 5, 4]
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
        pq.toArray() == list.toArray()
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
        mpq.toArray() == list.toArray()
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

    def "test clear"() {
        when:
        def empty = new MyPriorityQueue()
        def mpq = MyPriorityQueue.of(*5..1)
        then:
        mpq.toArray() == [1, 2, 3, 5, 4]
        when:
        mpq.clear()
        then:
        mpq.toArray() == []
        mpq.capacity() == 5
        mpq.size() == 0
    }
//
//    def "test iterator"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test replace"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
}
