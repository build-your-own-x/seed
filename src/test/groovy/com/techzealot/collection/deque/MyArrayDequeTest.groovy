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
        where:
        c                      | expected
        new MyArrayDeque()     | []
        MyArrayList.of(*1..2)  | [*1..2]
        MyLinkedList.of(*1..3) | [*1..3]
        MyArrayDeque.of(*1..4) | [*1..4]
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
//
//    def "test contains"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test toArray"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test add"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
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
//    def "test addFirst"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test addLast"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
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
//    def "test pollFirst"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test pollLast"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test getFirst"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test getLast"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test peekFirst"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
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
