package com.techzealot.collection.deque

import com.techzealot.collection.MyAbstractCollectionExtensions
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

    <E> boolean eleEq(List<E> list) {
        return Reflect.on(this).field("elements").get() == list.toArray()
    }
}

@Use(MyArrayDequeExtensions)
@Use(MyAbstractCollectionExtensions)
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

    def "test add"() {
        given:
        def deque = new MyArrayDeque()
        when:
        (0..16).each {
            deque.add(it)
        }
        then:
        deque.size() == 17
        deque.eq([*0..16])
    }

    def "test offer"() {
        given:
        def deque = new MyArrayDeque()
        when:
        (0..16).each {
            deque.offer(it)
        }
        then:
        deque.size() == 17
        deque.eq([*0..16])
    }

    def "test remove"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.remove() == 1
        deque.remove() == 2
        deque.remove() == 3
        when:
        deque.remove()
        then:
        thrown(NoSuchElementException)
    }

    def "test poll"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.poll() == 1
        deque.poll() == 2
        deque.poll() == 3
        deque.poll() == null
    }

    def "test element"() {
        when:
        def deque = MyArrayDeque.of(1)
        then:
        deque.element() == 1
        when:
        deque.poll()
        deque.element()
        then:
        thrown(NoSuchElementException)
    }

    def "test peek"() {
        when:
        def deque = MyArrayDeque.of(1)
        then:
        deque.peek() == 1
        when:
        deque.poll()
        then:
        deque.peek() == null
    }

    def "test clear"() {
        given:
        def deque = MyArrayDeque.of(*1..16)
        when:
        deque.clear()
        then:
        deque.size() == 0
        deque.eleEq([null] * 32)
    }

    def "test remove(Object)"() {
        when:
        def deque = MyArrayDeque.of(*1..16)
        then:
        !deque.remove(null)
        !deque.remove(new Object())
        !deque.remove(17)
        deque.size() == 16
        deque.remove(10)
        deque.size() == 15
        deque.eleEq([*1..9] + [*11..16] + [null] * 17)
    }

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

    def "test offerFirst"() {
        given:
        def deque = new MyArrayDeque<Integer>()
        when:
        (0..16).each {
            deque.offerFirst(it)
        }
        then:
        deque.size() == 17
        deque.eq([*16..0])
    }

    def "test offerLast"() {
        given:
        def deque = new MyArrayDeque<Integer>()
        when:
        (0..16).each {
            deque.offerLast(it)
        }
        then:
        deque.size() == 17
        deque.eq([*0..16])
    }

    def "test removeFirst"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.removeFirst() == 1
        deque.removeFirst() == 2
        deque.removeFirst() == 3
        when:
        deque.removeFirst()
        then:
        thrown(NoSuchElementException)
    }

    def "test removeLast"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.removeLast() == 3
        deque.removeLast() == 2
        deque.removeLast() == 1
        when:
        deque.removeLast()
        then:
        thrown(NoSuchElementException)
    }

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
        given:
        def stack = new MyArrayDeque()
        when:
        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.push(4)
        then:
        stack.pop() == 4
        stack.pop() == 3
        stack.pop() == 2
        stack.pop() == 1
        when:
        stack.pop()
        then:
        thrown(NoSuchElementException)
    }

    def "test use as queue"() {
        given:
        def queue = new MyArrayDeque()
        when:
        queue.offer(1)
        queue.offer(2)
        queue.offer(3)
        queue.offer(4)
        then:
        queue.poll() == 1
        queue.poll() == 2
        queue.poll() == 3
        queue.poll() == 4
        queue.poll() == null
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

    def "test peekLast"() {
        when:
        def deque = MyArrayDeque.of(*1..3)
        then:
        deque.peekLast() == 3
        when:
        deque.pollLast()
        then:
        deque.peekLast() == 2
        when:
        deque.pollLast()
        then:
        deque.peekLast() == 1
        when:
        deque.pollLast()
        then:
        deque.peekLast() == null
    }

    /**
     * todo 增加各种场景保证覆盖率
     * @return
     */
    def "test removeFirstOccurrence"() {
        given:
        def deque = MyArrayDeque.of(1, 1, 2, 2)
        when:
        def b1 = deque.removeFirstOccurrence(null)
        def b2 = deque.removeFirstOccurrence(new Object())
        def b3 = deque.removeFirstOccurrence(3)
        then:
        !(b1 || b2 || b3)
        deque.size() == 4
        deque.removeFirstOccurrence(1)
        deque.eleEq([null] + [1, 2, 2] + [null] * 4)
        deque.size() == 3
        deque.removeFirstOccurrence(1)
        deque.eleEq([null] * 2 + [2, 2] + [null] * 4)
        deque.size() == 2
        deque.removeFirstOccurrence(2)
        deque.eleEq([null] * 3 + [2] + [null] * 4)
        deque.size() == 1
        deque.removeFirstOccurrence(2)
        deque.eleEq([null] * 8)
        deque.size() == 0
        !deque.removeFirstOccurrence(1)
        deque.size() == 0
    }

    def "test removeLastOccurrence"() {
        when:
        def deque = MyArrayDeque.of(1, 1, 2, 2)
        then:
        !deque.removeLastOccurrence(null)
        !deque.removeLastOccurrence(new Object())
        !deque.removeLastOccurrence(11)
        deque.removeLastOccurrence(1)
        deque.removeLastOccurrence(1)
        deque.removeLastOccurrence(2)
        deque.removeLastOccurrence(2)
        deque.isEmpty()
        !deque.removeLastOccurrence(1)
        !deque.removeLastOccurrence(2)
    }

    def "test push"() {
        given:
        def deque = new MyArrayDeque<Integer>(7)
        when:
        (0..6).each {
            deque.push(it)
        }
        then:
        deque.size() == 7
        deque.eleEq([null] + [*6..0])
        when:
        deque.push(7)
        deque.push(8)
        then:
        deque.size() == 9
        deque.eleEq([*7..0] + [null] * 7 + [8])
    }

    def "test pop"() {
        when:
        def deque = MyArrayDeque.of(*1..4)
        then:
        deque.pop() == 1
        deque.pop() == 2
        deque.pop() == 3
        deque.pop() == 4
        when:
        deque.pop()
        then:
        thrown(NoSuchElementException)
    }

    def "test descendingIterator"() {
        given:
        def deque = MyArrayDeque.of(*1..16)
        when:
        def it = deque.descendingIterator()
        def out = []
        while (it.hasNext()) {
            def next = it.next()
            out += next
            if (next == 10) {
                it.remove()
            }
        }
        then:
        !it.hasNext()
        out == [*16..1]
        deque.eq([*1..9] + [*11..16])
        when:
        it.next()
        then:
        thrown(NoSuchElementException)
    }


    def "test iterator"() {
        given:
        def deque = MyArrayDeque.of(*1..16)
        when:
        def it = deque.iterator()
        def out = []
        while (it.hasNext()) {
            def next = it.next()
            out += next
            if (next == 10) {
                it.remove()
            }
        }
        then:
        !it.hasNext()
        out == [*1..16]
        deque.eq([*1..9] + [*11..16])
        when:
        it.next()
        then:
        thrown(NoSuchElementException)
    }

    def "test iterator.forEachRemaining"() {
        given:
        def deque = MyArrayDeque.of(*1..16)
        when:
        def it = deque.iterator()
        def out = []
        while (it.hasNext()) {
            if (it.next() == 10) {
                break;
            }
        }
        it.forEachRemaining { e ->
            out += e
        }
        then:
        !it.hasNext()
        out == [*11..16]
    }
}
