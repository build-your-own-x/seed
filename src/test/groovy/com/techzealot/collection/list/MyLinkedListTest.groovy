package com.techzealot.collection.list

import org.joor.Reflect
import spock.lang.Specification
import spock.util.mop.Use

@Category(MyLinkedList.class)
class MyLinkedListExtensions {
    public <E> boolean eq(List<E> expected) {
        return this.toArray() == expected.toArray()
    }
}

@Use(MyLinkedListExtensions.class)
class MyLinkedListTest extends Specification {
    def "test init with no args"() {
        when:
        MyLinkedList<Integer> list = new MyLinkedList<>()
        then:
        list.size() == 0
        Reflect.on(list).field("first").get() == null
        Reflect.on(list).field("last").get() == null
    }

    def "test of"() {
        when:
        def list = MyLinkedList.of(*1..10)
        then:
        list.eq([*1..10])
    }

    def "test init with collection"() {
        given:
        def list = MyArrayList.<Integer> of(*1..6)
        def empty = new MyArrayList<Integer>()
        when:
        new MyLinkedList<Integer>(null)
        then:
        thrown(NullPointerException)
        when:
        def linkedlist1 = new MyLinkedList<Integer>(empty)
        def linkedlist2 = new MyLinkedList<Integer>(list)
        then:
        linkedlist1.size() == 0
        linkedlist2.eq([*1..6])
    }

    def "test toArray"() {
        given:
        def list = MyLinkedList.of(*1..10)
        def empty = new MyLinkedList()
        when:
        def a1 = list.toArray()
        def a2 = empty.toArray()
        then:
        a1 == [*1..10] as Integer[]
        a2 == [] as Object[]
    }

    def "test add(E)"() {
        given:
        def list = MyLinkedList.of(1, 2, 3)
        when: "add last"
        def r1 = list.add(4)
        def r2 = list.add(null)
        then:
        r1
        r2
        list.size() == 5
        list.eq([*1..4, null])
    }

    def "test add(int,E)"() {
        given:
        def empty = new MyLinkedList<Integer>()
        def list = MyLinkedList.<Integer> of(*1..6)
        when:
        empty.add(empty.size() + 1, -1)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        empty.add(0, 1)
        then:
        empty.eq([1])
        when:
        list.add(0, 10)
        list.add(7, 10)
        then:
        list.eq([10, *1..6, 10])
        when:
        list.add(1, 10)
        then:
        list.eq([10, 10, *1..6, 10])
    }

    def "test addAll"() {
        given:
        def list = new MyLinkedList<Integer>()
        def add = MyLinkedList.of(*1..6)
        def empty = new MyArrayList<Integer>()
        when:
        list.addAll(null)
        then:
        thrown(NullPointerException)
        when:
        def r1 = list.addAll(empty)
        then:
        !r1
        list.eq([])
        when:
        def r2 = list.addAll(add)
        then:
        r2
        list.eq([*1..6])
    }

    def "test addAll(int,c)"() {
        given:
        def list = MyLinkedList.of(*1..6)
        def add = MyLinkedList.of(*1..3)
        def empty = new MyLinkedList<Integer>()
        when:
        list.addAll(0, null)
        then:
        thrown(NullPointerException)
        when:
        list.addAll(list.size() + 1, empty)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        def r1 = list.addAll(list.size(), empty)
        then:
        !r1
        list.eq([*1..6])
        when:
        def r2 = list.addAll(0, add)
        then:
        r2
        list.eq([*1..3, *1..6])
        when:
        def r3 = list.addAll(list.size(), add)
        then:
        r3
        list.eq([*1..3, *1..6, *1..3])
        when:
        def r4 = list.addAll(3, add)
        then:
        r4
        list.eq([*1..3, *1..3, *1..6, *1..3])
    }

    def "test remove(int)"() {
        given:
        def list = MyLinkedList.of("a", "b", "c", null, null)
        when:
        list.remove(-1)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        list.remove(list.size())
        then:
        thrown(IndexOutOfBoundsException)
        when: "remove first half"
        def r1 = list.remove(1)
        then:
        r1 == "b"
        list.eq(["a", "c", null, null])
        when: "remove first"
        def r2 = list.remove(0)
        then:
        r2 == "a"
        list.eq(["c", null, null])
        when: "remove last half"
        def r3 = list.remove(1)
        then:
        r3 === null
        list.eq(["c", null])
        when: "remove last"
        def r4 = list.remove(1)
        then:
        r4 === null
        list.eq(["c"])
    }

    def "test remove(Object)"() {
        given:
        def list = MyLinkedList.of("a", "b", "c", null, "d", null)
        when:
        def r1 = list.remove(null)
        then:
        r1
        list.eq([*"a".."d", null])
        when:
        list.remove(null)
        def r2 = list.remove("e")
        then:
        !r2
        list.eq([*"a".."d"])
        when:
        def r3 = list.remove("a")
        then:
        r3
        list.eq(["b", "c", "d"])
    }

    def "test removeAll"() {
        given:
        def list = MyLinkedList.of(*1..6, null)
        def empty = new MyLinkedList<Integer>()
        def deleted = MyLinkedList.of(1, 3, 5, 7, null)
        when:
        list.removeAll(null)
        then:
        thrown(NullPointerException)
        when:
        def r1 = list.removeAll(empty)
        then:
        !r1
        list.eq([*1..6, null])
        when:
        def r2 = list.removeAll(deleted)
        then:
        r2
        list.eq([2, 4, 6])
    }

    def "test retainAll"() {
        given:
        def list = MyLinkedList.of(*1..6)
        def retained = MyArrayList.of(2, 4)
        def empty = MyLinkedList.of()
        when:
        def r1 = list.retainAll(retained)
        then:
        list.eq([2, 4])
        when:
        def r2 = list.retainAll(empty)
        then:
        r2
        list.eq([])
    }

    def "test clear"() {
        given:
        def empty = new MyLinkedList()
        def list = MyLinkedList.of(*1..10)
        when:
        empty.clear()
        list.clear()
        then:
        empty.eq([])
        list.eq([])
    }

    def "test size"() {
        given:
        def empty = new MyLinkedList()
        def list = MyLinkedList.of(*1..10)
        when:
        def size1 = empty.size()
        def size2 = list.size()
        then:
        size1 == 0
        size2 == 10
    }

    def "test contains"() {
        given:
        def list = MyLinkedList.of(*1..10, null)
        when:
        def r1 = list.contains(null)
        def r2 = list.contains(20)
        def r3 = list.contains(1)
        then:
        r1
        !r2
        r3
        when:
        list.remove(null)
        def r4 = list.contains(null)
        then:
        !r4
    }

    def "test indexOf"() {
        given:
        def list = MyLinkedList.of(*1..10)
        when:
        def r1 = list.indexOf(null)
        def r2 = list.indexOf(1)
        def r3 = list.indexOf(11)
        def r4 = list.indexOf(new Object())
        then:
        r1 == -1
        r2 == 0
        r3 == -1
        r4 == -1
    }

    def "test get"() {
        given:
        def list = MyLinkedList.of(*1..5)
        when:
        list.get(-1)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        list.get(list.size())
        then:
        thrown(IndexOutOfBoundsException)
        then:
        [list.get(0), list.get(1), list.get(2), list.get(3), list.get(4)] == [*1..5]
    }

    def "test set"() {
        given:
        def list = MyLinkedList.of(*1..5)
        when:
        list.set(-1, null)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        list.set(list.size(), null)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        list.set(0, null)
        then:
        list.eq([null, *2..5])
        when:
        list.set(4, null)
        then:
        list.eq([null, *2..4, null])
        when:
        list.set(2, null)
        then:
        list.eq([null, 2, null, 4, null])
    }

}
