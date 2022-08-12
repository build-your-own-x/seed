package com.techzealot.collection.deque

import com.techzealot.collection.MyCollection
import com.techzealot.collection.list.MyArrayList
import com.techzealot.collection.list.MyLinkedList
import org.joor.Reflect
import spock.lang.Specification
import spock.util.mop.Use

@Category(MyArrayDeque)
class MyArrayDequeExtensions {
    int reflectHead() {
        return Reflect.on(this).field("head").get()
    }

    int reflectTail() {
        return Reflect.on(this).field("tail").get()
    }

    <E> boolean eq(List<E> list) {
        return this.toArray() == list.toArray()
    }

    <E> boolean eleEq(List<E> list) {
        return Reflect.on(this).field("elements").get() == list.toArray()
    }
}

@Use(MyArrayDequeExtensions)
class MyArrayDequeTest extends Specification {
    def "init with on arg"() {
        when:
        def deque = new MyArrayDeque<Integer>()
        then:
        deque.capacity() == 16
        deque.reflectHead() == 0
        deque.reflectTail() == 0
    }

    def "init with capacity"(int capacity, int expected) {
        when:
        def deque = new MyArrayDeque<Integer>(capacity)
        then:
        deque.reflectHead() == 0
        deque.reflectTail() == 0
        then:
        deque.capacity() == expected
        where:
        capacity | expected
        -1       | 8
        0        | 8
        1        | 8
        3        | 8
        9        | 16
        16       | 32
        17       | 32
    }

    def "init with MyCollection"(MyCollection<Integer> c, List<Integer> expected) {
        when:
        def deque = new MyArrayDeque<>(c)
        then:
        deque.size() == c.size()
        deque.eq(expected)
        when: "collection contains null"
        c.add(null)
        new MyArrayDeque<>(c)
        then:
        thrown(NullPointerException)
        where:
        c                      | expected
        new MyArrayDeque()     | []
        MyArrayList.of(*1..2)  | [*1..2]
        MyLinkedList.of(*1..3) | [*1..3]
        MyArrayDeque.of(*1..4) | [*1..4]
    }

    def "calculateSize"(int nums, int expected) {
        when:
        def capacity = Reflect.onClass(MyArrayDeque.class).call("calculateSize", nums).get()
        then:
        capacity == expected
        where:
        nums              | expected
        Integer.MIN_VALUE | 8
        -1                | 8
        0                 | 8
        7                 | 8
        8                 | 16
        32                | 64
        31                | 32
        Integer.MAX_VALUE | 0x40000000
    }


    def "test size"(MyArrayDeque<Integer> deque, int expectedSize, int expectedHead, int expectedTail) {
        when:
        def size = deque.size()
        def head = deque.reflectHead()
        def tail = deque.reflectTail()
        then:
        size == expectedSize
        head == expectedHead
        tail == expectedTail
        where:
        deque                  | expectedSize | expectedHead | expectedTail
        new MyArrayDeque()     | 0            | 0            | 0
        MyArrayDeque.of(*1..4) | 4            | 0            | 4
        MyArrayDeque.of(*1..9) | 9            | 0            | 9
    }

    def "test capacity"(MyArrayDeque<Integer> deque, int expectedCapacity) {
        when:
        def capacity = deque.capacity()
        then:
        capacity == expectedCapacity
        where:
        deque                   | expectedCapacity
        new MyArrayDeque()      | 16
        MyArrayDeque.of(1)      | 8
        MyArrayDeque.of(*1..7)  | 8
        MyArrayDeque.of(*1..9)  | 16
        MyArrayDeque.of(*1..16) | 32
        MyArrayDeque.of(*1..31) | 32
    }

    def "test contains"() {
        when:
        def empty = new MyArrayDeque()
        def deque = MyArrayDeque.of(*1..5)
        then:
        !empty.contains(null)
        !empty.contains(new Object())
        deque.contains(1)
        deque.contains(2)
        deque.contains(3)
        deque.contains(4)
        deque.contains(5)
        !deque.contains(null)
        !deque.contains(new Object())
    }

    def "test toArray"(MyArrayDeque deque, List expected) {
        when:
        def arr = deque.toArray()
        then:
        arr == expected.toArray()
        where:
        deque                   | expected
        new MyArrayDeque()      | []
        MyArrayDeque.of(*1..10) | [*1..10]
    }

    def "test add"() {

    }
//
//    def "test offer"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test remove"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test poll"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test element"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test peek"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test testRemove"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test clear"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
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
    /**
     * 底层实现,详细测试
     */
    def "test addFirst"() {
        given:
        def deque = new MyArrayDeque(4)
        when:
        deque.addFirst(0)
        deque.addFirst(1)
        then:
        deque.eleEq([null] * 6 + [1, 0])
        when:
        (2..6).each {
            deque.addFirst(it)
        }
        then:
        deque.eleEq([null] + [*6..0])
        when: "test capacity expand"
        deque.addFirst(7)
        then: "扩容后重排列"
        deque.eleEq([*7..0] + [null] * 8)
    }

    /**
     * 底层实现,详细测试
     */
    def "test addLast"() {
        given:
        def deque = new MyArrayDeque(4)
        when:
        deque.addLast(0)
        deque.addLast(1)
        then:
        deque.eleEq([0, 1] + [null] * 6)
        when:
        (2..6).each {
            deque.addLast(it)
        }
        then:
        deque.eleEq([*0..6] + [null])
        when: "test capacity expand"
        deque.addLast(7)
        then:
        deque.eleEq([*0..7] + [null] * 8)
    }
//
//    def "test offerFirst"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test offerLast"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test removeFirst"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test removeLast"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
    def "test pollFirst"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.pollFirst() == 1
        deque.pollFirst() == 2
        deque.pollFirst() == 3
        deque.pollFirst() == null
        deque.pollFirst() == null
    }


    def "test pollLast"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.pollLast() == 3
        deque.pollLast() == 2
        deque.pollLast() == 1
        deque.pollLast() == null
        deque.pollLast() == null
    }

    def "test use as stack"() {

    }

    def "test use as queue"() {

    }

    def "test getFirst"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.getFirst() == 1
        when:
        deque.pollFirst()
        then:
        deque.getFirst() == 2
        when:
        deque.pollFirst()
        then:
        deque.getFirst() == 3
        when:
        deque.pollFirst()
        deque.getFirst()
        then:
        thrown(NoSuchElementException)
    }

    def "test getLast"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.getLast() == 3
        when:
        deque.pollLast()
        then:
        deque.getLast() == 2
        when:
        deque.pollLast()
        then:
        deque.getLast() == 1
        when:
        deque.pollLast()
        deque.getLast()
        then:
        thrown(NoSuchElementException)
    }

    def "test peekFirst"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.peekFirst() == 1
        when:
        deque.pollFirst()
        then:
        deque.peekFirst() == 2
        when:
        deque.pollFirst()
        then:
        deque.peekFirst() == 3
        when:
        deque.pollFirst()
        then:
        deque.peekFirst() == null
    }
//
//    def "test peekLast"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test removeFirstOccurrence"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test removeLastOccurrence"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test push"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test pop"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test descendingIterator"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test clone"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
}
