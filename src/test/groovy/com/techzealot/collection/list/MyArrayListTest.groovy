package com.techzealot.collection.list

import com.techzealot.collection.MyAbstractCollectionExtensions
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.mop.Use

@Use(MyAbstractCollectionExtensions)
class MyArrayListTest extends Specification {

    def "init with no args"() {
        when:
        def list = new MyArrayList<>()
        def elementData = list["elementData"]
        then:
        list.size() == 0
        list.getCapacity() == 0
        elementData != null
        elementData == MyArrayList.class["DEFAULTCAPACITY_EMPTY_ELEMENTDATA"]
    }

    @Unroll
    def "init with initial capacity"(int initialCapacity, int expected) {
        when: "init with illegal capacity"
        new MyArrayList<Integer>(-1)
        then:
        thrown(IllegalArgumentException)
        when: "init with capacity 0"
        def empty = new MyArrayList<Integer>(0)
        def elementData = empty["elementData"]
        then:
        elementData == MyArrayList.class["EMPTY_ELEMENTDATA"]
        when: "init with legal args"
        def list = new MyArrayList<>(initialCapacity)
        then:
        list.size() == 0
        list.getCapacity() == initialCapacity
        where:
        initialCapacity | expected
        1               | 1
        10              | 10
        1024            | 1024
    }

    def "init with collection"() {
        given: "empty collection and non-empty collection"
        def empty = new MyArrayList<Integer>()
        def c = MyArrayList.of(1, 2, 3)
        when:
        new MyLinkedList<Integer>(null)
        then:
        thrown(NullPointerException)
        when:
        def linklist = MyLinkedList.<Integer> of(*1..5)
        def l = new MyArrayList<Integer>(linklist)
        then:
        l.eq([*1..5])
        when:
        def list = new MyArrayList<Integer>(empty)
        then:
        list.size() == 0
        list.getCapacity() == 0
        list["elementData"] == MyArrayList.class["EMPTY_ELEMENTDATA"]
        when:
        def mc = new MyArrayList<>(c);
        then:
        mc.getCapacity() == 3
        mc.size() == 3
        mc.eq([1, 2, 3])
        when:
        mc.get(mc.size())
        then:
        thrown(IndexOutOfBoundsException)
    }

    def "add at tail of list"() {
        given: "init with no arg"
        def c = new MyArrayList<>()
        when:
        c.add(1)
        then:
        c.size() == 1
        c.getCapacity() == 10
        c.get(0) == 1
        when: "init with capacity 0"
        def c1 = new MyArrayList<>(0)
        c1.add(1)
        then:
        c1.size() == 1
        c1.getCapacity() == 1
        c1.get(0) == 1
        when: "init with capacity 4"
        def c2 = new MyArrayList<>(4)
        c2.add(1)
        c2.add(2)
        c2.add(3)
        then:
        c2.size() == 3
        c2.getCapacity() == 4
        c2.eq([1, 2, 3])
        when: "get out of index"
        c2.get(c2.size())
        then:
        thrown(IndexOutOfBoundsException)
        when:
        c2.add(4)
        c2.add(5)
        then:
        c2.size() == 5
        c2.getCapacity() == 6
        c2.eq([1, 2, 3, 4, 5])
    }

    def "add by index"() {
        given:
        def c = MyArrayList.of(1, 2, 3)
        when: "add at head"
        c.add(0, 0)
        then:
        c.size() == 4
        c.getCapacity() == 4
        c.eq([0, 1, 2, 3])
        when: "add at tail"
        c.add(4, 4)
        then:
        c.size() == 5
        c.getCapacity() == 6
        c.get(4) == 4
        when: "add in the middle"
        c.add(2, 22)
        then:
        c.size() == 6
        c.getCapacity() == 6
        c.eq([0, 1, 22, 2, 3, 4])
        when: "add out of size"
        c.add(c.size() + 1, 7)
        then:
        thrown(IndexOutOfBoundsException)
    }

    @Ignore("out of memory")
    def "add on huge capacity"(int initialCapacity, int expected) {
        given:
        def mc = new MyArrayList<Byte>(initialCapacity)
        when:
        for (i in 0..<initialCapacity) {
            mc.set(i, (byte) 1)
        }
        mc.add((byte) 0)
        mc.add((byte) 0)
        then:
        mc.getCapacity() == expected
        where:
        initialCapacity         | expected
        Integer.MAX_VALUE * 0.8 | Integer.MAX_VALUE - 8
    }

    def "hugeCapacity"(int minCapacity, int expected) {
        when:
        MyArrayList."hugeCapacity"(Integer.MAX_VALUE + 1)
        then:
        def e = thrown(OutOfMemoryError)
        when:
        def r = MyArrayList."hugeCapacity"(minCapacity)
        then:
        r == expected
        where:
        minCapacity             | expected
        Integer.MAX_VALUE       | Integer.MAX_VALUE
        Integer.MAX_VALUE * 0.8 | Integer.MAX_VALUE - 8
    }

    def "out of memory"() {
        when:
        def mc = new MyArrayList<Byte>(Integer.MAX_VALUE)
        then:
        thrown(OutOfMemoryError)
    }

    def "add all"() {
        given:
        def c1 = MyArrayList.of(1, 2)
        when: "add to empty list"
        def c2 = new MyArrayList<>()
        def r = c2.addAll(c1)
        then:
        r
        c2.getCapacity() == 10
        c2.size() == 2
        c2.eq([1, 2])
        when: "add to list with elements"
        def c3 = new MyArrayList<>(4)
        c3.add(3)
        c3.add(4)
        c3.addAll(c1)
        then:
        c3.size() == 4
        c3.getCapacity() == 4
        c3.eq([3, 4, 1, 2])
        when: "add a empty collection"
        def empty = new MyArrayList()
        def list = MyArrayList.of(1, 2, 3)
        r = list.addAll(empty)
        then:
        !r
        list.eq([1, 2, 3])
    }

    def "add all at index"() {
        given:
        def empty = new MyArrayList();
        def add = MyArrayList.of(1, 2, 3)
        def list = MyArrayList.of(*1..6)
        when:
        list.addAll(list.size() + 1, empty)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        def r = list.addAll(0, empty)
        then:
        !r
        list.eq([*1..6])
        when:
        r = list.addAll(3, add)
        then:
        r
        list.size() == 9
        list.getCapacity() == 9
        list.eq([*1..3, *1..3, *4..6])
    }

    def "remove by index"() {
        given:
        def m = MyArrayList.of("a", "b", "c", "d", "e")
        when:
        m.removeAt(m.size())
        then:
        thrown(IndexOutOfBoundsException)
        when: "remove head"
        def r = m.removeAt(0)
        then:
        r == "a"
        m.eq(["b", "c", "d", "e"])
        when: "remove tail"
        r = m.removeAt(3)
        then:
        r == "e"
        m.eq(["b", "c", "d"])
        when: "remove middle"
        r = m.removeAt(1)
        then:
        r == "c"
        m.eq(["b", "d"])
    }

    def "remove by object"() {
        given:
        def m = MyArrayList.of("a", "b", "c", "d", "d", "e", null)
        when:
        def r = m.remove("a")
        then:
        r
        m.eq(["b", "c", "d", "d", "e", null])
        when:
        r = m.remove("d")
        then:
        r
        m.eq(["b", "c", "d", "e", null])
        when:
        r = m.remove("d")
        then:
        r
        m.eq(["b", "c", "e", null])
        then: "validate return value"
        m.remove("b")
        !m.remove("x")
        when:
        r = m.remove(null)
        then:
        r
        m.eq(["c", "e"])
    }

    def "set by index"() {
        given:
        def m = new MyArrayList<>(4)
        when:
        m.set(0, 0)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        m.add(1)
        m.add(1)
        m.add(1)
        m.add(1)
        m.set(0, 1)
        m.set(1, 1)
        m.set(2, 1)
        m.set(3, null)
        then:
        m.eq([1, 1, 1, null])
    }

    def "get element by index"() {
        when:
        def m = MyArrayList.of(1, 2, 3, 4, 5)
        then:
        m.get(0) == 1
        m.get(1) == 2
        m.get(2) == 3
        m.get(3) == 4
        m.get(4) == 5
    }

    def "index of"() {
        when:
        def m = MyArrayList.of(1, 2, 3, 4, 5, null)
        then:
        m.indexOf(1) == 0
        m.indexOf(2) == 1
        m.indexOf(3) == 2
        m.indexOf(4) == 3
        m.indexOf(5) == 4
        m.indexOf(Integer.valueOf(1)) == 0
        m.indexOf(null) == 5
        m.indexOf(10) == -1
    }

    @Ignore("TODO")
    def "removeAll when contains throws"() {

    }

    @Ignore("TODO")
    def "retainAll when contains throws"() {

    }

    def "clear"() {
        given:
        def c = MyArrayList.<Integer> of(*1..5)
        when:
        c.clear()
        def elements = c["elementData"] as Object[]
        then:
        c.size() == 0
        c.getCapacity() == 5
        elements == [null] * 5 as Object[]
    }

    def "test equals"() {
        when:
        def empty1 = new MyArrayList()
        def empty2 = new MyArrayList()
        def list1 = MyArrayList.of(*1..10)
        def list2 = MyArrayList.of(*1..10)
        def list3 = MyArrayList.of(*1..9)
        def list4 = MyLinkedList.of(*1..9)
        then:
        empty1 == empty2
        empty2 == empty1
        list1 == list2
        list2 == list1
        list3 == list4
        list1 != list3
    }

    def "test hashCode"() {
        when:
        def empty = new MyArrayList()
        def list = MyArrayList.of(*1..10)
        then:
        empty.hashCode() == [].hashCode()
        list.hashCode() == [*1..10].hashCode()
    }

    def "clone"() {
        given:
        def list = MyArrayList.of(*1..10)
        when:
        def clone = list.clone() as MyArrayList
        def cloneElements = list["elementData"]
        def listElements = clone["elementData"]
        then:
        clone !== list
        clone.size() == 10
        clone.modCount == 0
        listElements !== cloneElements
        listElements == [*1..10]
    }

    def "iterator for concurrent modify"() {
        given:
        def list1 = MyArrayList.of(*1..10)
        def list2 = MyArrayList.of(*1..10)
        when:
        for (def e1 in list1) {
            if (e1 == 5) {
                list1.remove(e1)
            }
        }
        then:
        list1.eq([*1..4, *6..10])
        thrown(ConcurrentModificationException)
        when:
        for (i in 0..<list2.size()) {
            if (i == 4) {
                list2.removeAt(i)
            }
            list2.get(i)
        }
        then:
        list2.eq([*1..4, *6..10])
        thrown(IndexOutOfBoundsException)
    }

    def "iterator"() {
        given:
        def empty = new MyArrayList()
        def list = MyArrayList.<Integer> of(*1..10)
        def emptyItr = empty.iterator()
        when:
        emptyItr.next()
        then:
        thrown(NoSuchElementException)
        when:
        def r1 = emptyItr.hasNext()
        then:
        !r1
        when:
        def list1 = []
        def it = list.iterator()
        while (it.hasNext()) {
            list1 += it.next()
        }
        then:
        list1 == [*1..10]
        when:
        def list2 = []
        for (Integer e : list) {
            list2 += e
        }
        then:
        list2 == [*1..10]
        when: "remove"
        it = list.iterator()
        while (it.hasNext()) {
            if (it.next() % 2 == 0) {
                it.remove()
            }
        }
        then:
        list.toArray() == (1..10).step(2).toArray()
        when: "test remaining"
        it = list.iterator()
        while (it.hasNext()) {
            if (it.next() > 5) {
                break;
            }
        }
        def list3 = []
        it.forEachRemaining {
            list3 += it
        }
        then:
        list3 == [9]
        !it.hasNext()
    }
}
