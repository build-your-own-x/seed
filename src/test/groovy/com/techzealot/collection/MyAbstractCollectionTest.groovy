package com.techzealot.collection

import com.techzealot.collection.deque.MyArrayDeque
import com.techzealot.collection.deque.MyPriorityQueue
import com.techzealot.collection.list.MyArrayList
import com.techzealot.collection.list.MyLinkedList
import spock.lang.Specification
import spock.util.mop.Use

@Category(MyAbstractCollection)
class MyAbstractCollectionExtensions {
    <E> boolean eq(List<E> expected) {
        return this.toArray() == expected.toArray()
    }
}

@Use(MyAbstractCollectionExtensions)
class MyAbstractCollectionTest extends Specification {
    def "test toString"(MyAbstractCollection c, String expected) {
        when:
        def out = c.toString()
        then:
        out == expected
        where:
        c                         | expected
        new MyArrayList()         | [].toString()
        MyArrayList.of(*1..10)    | [*1..10].toString()
        new MyLinkedList()        | [].toString()
        MyLinkedList.of(*1..10)   | [*1..10].toString()
        new MyArrayDeque()        | [].toString()
        MyArrayDeque.of(*1..10)   | [*1..10].toString()
        new MyPriorityQueue()     | [].toString()
        MyPriorityQueue.of(*5..1) | [1, 2, 3, 5, 4].toString()
    }

    def "test clone"(MyAbstractCollection c) {
        when:
        def clone = c.clone() as MyAbstractCollection
        then:
        clone !== c
        clone.toArray() == c.toArray()
        where:
        c                             | _
        new MyArrayList()             | _
        new MyLinkedList()            | _
        new MyArrayDeque()            | _
        MyArrayList.of(*1..10, null)  | _
        MyLinkedList.of(*1..10, null) | _
        MyArrayDeque.of(*1..10)       | _
    }

    def "test serialize and deserialize"(MyAbstractCollection input) {
        when:
        def bos = new ByteArrayOutputStream()
        ObjectOutputStream oos = new ObjectOutputStream(bos)
        oos.writeObject(input)
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()))
        def out = (MyAbstractCollection) ois.readObject()
        then:
        out !== input
        out.toArray() == input.toArray()
        where:
        input                      | _
        new MyArrayList<>()        | _
        MyArrayList.of(*1..10)     | _
        new MyLinkedList<>()       | _
        MyLinkedList.of(*1..10)    | _
        new MyArrayDeque<>()       | _
        MyArrayDeque.of(*1..10)    | _
        new MyPriorityQueue()      | _
        MyPriorityQueue.of(*1..10) | _
    }

    def "test size"(MyAbstractCollection c, int expected) {
        when:
        def size = c.size()
        then:
        size == expected
        where:
        c                          | expected
        new MyArrayList()          | 0
        new MyLinkedList<>()       | 0
        new MyArrayDeque<>()       | 0
        new MyPriorityQueue<>()    | 0
        MyArrayList.of(*1..10)     | 10
        MyLinkedList.of(*1..10)    | 10
        MyArrayDeque.of(*1..10)    | 10
        MyPriorityQueue.of(*1..10) | 10
    }

    def "test isEmpty"(MyAbstractCollection c) {
        when:
        def b = c.isEmpty()
        then:
        b
        when:
        c.add(new Object())
        then:
        !c.isEmpty()
        where:
        c                     | _
        new MyArrayList<>()   | _
        new MyLinkedList<>()  | _
        new MyArrayDeque<>()  | _
        new MyPriorityQueue() | _
    }

    def "test contains"(MyAbstractCollection c, List<Boolean> expected) {
        when:
        def out = []
        out += c.contains(new Object())
        out += c.contains(null)
        out += c.contains(Integer.MAX_VALUE)
        out += c.contains(5)
        then:
        out == expected
        where:
        c                             | expected
        new MyArrayList()             | [false] * 4
        new MyLinkedList()            | [false] * 4
        new MyArrayDeque()            | [false] * 4
        new MyPriorityQueue()         | [false] * 4
        MyArrayList.of(*1..10)        | [false] * 3 + [true]
        MyArrayList.of(*1..10, null)  | [false, true, false, true]
        MyLinkedList.of(*1..10)       | [false] * 3 + [true]
        MyLinkedList.of(*1..10, null) | [false, true, false, true]
        MyArrayDeque.of(*1..10)       | [false] * 3 + [true]
        MyPriorityQueue.of(*1..10)    | [false] * 3 + [true]
    }


    def "test toArray"(MyAbstractCollection c, List expected) {
        when:
        def out = c.toArray()
        then:
        out == expected.toArray()
        where:
        c                             | expected
        new MyArrayList()             | []
        new MyLinkedList()            | []
        new MyArrayDeque()            | []
        new MyPriorityQueue()         | []
        MyArrayList.of(*1..10, null)  | [*1..10] + [null]
        MyLinkedList.of(*1..10, null) | [*1..10] + [null]
        MyArrayDeque.of(*1..10)       | [*1..10]
        MyPriorityQueue.of(*5..1)     | [1, 2, 3, 5, 4]
    }

    def "test add(Object):allow null"(MyAbstractCollection c, List expected) {
        when:
        def r = c.add(11)
        c.add(null)
        then:
        r
        c.eq(expected)
        where:
        c                       | expected
        new MyArrayList()       | [11, null]
        new MyLinkedList()      | [11, null]
        MyArrayList.of(*1..10)  | [*1..11, null]
        MyLinkedList.of(*1..10) | [*1..11, null]
    }

    def "test add(Object) to disallow null collections"(MyAbstractCollection c, List expected) {
        when:
        def r1 = c.add(11)
        def r2 = c.add(null)
        then:
        true
        r1
        c.eq(expected)
        thrown(NullPointerException)
        where:
        c                         | expected
        new MyArrayDeque()        | [11]
        MyArrayDeque.of(*1..10)   | [*1..11]

        new MyPriorityQueue()     | [11]
        MyPriorityQueue.of(*5..1) | [1, 2, 3, 5, 4, 11]
    }

    def "test remove(Object)"(MyAbstractCollection c, List<Boolean> removeResults, List elements) {
        when:
        def out = []
        out += c.remove(new Object())
        out += c.remove(null)
        out += c.remove(100)
        out += c.remove(5)
        then:
        out == removeResults
        c.eq(elements)
        where:
        c                             | removeResults              | elements
        new MyArrayList()             | [false] * 4                | []
        new MyLinkedList()            | [false] * 4                | []
        new MyArrayDeque()            | [false] * 4                | []
        new MyPriorityQueue()         | [false] * 4                | []
        MyArrayList.of(*1..10)        | [false] * 3 + [true]       | [*1..4] + [*6..10]
        MyArrayList.of(*1..10, null)  | [false, true, false, true] | [*1..4] + [*6..10]
        MyLinkedList.of(*1..10)       | [false] * 3 + [true]       | [*1..4] + [*6..10]
        MyLinkedList.of(*1..10, null) | [false, true, false, true] | [*1..4] + [*6..10]
        MyArrayDeque.of(*1..10)       | [false] * 3 + [true]       | [*1..4] + [*6..10]
        MyPriorityQueue.of(*5..1)     | [false] * 3 + [true]       | [1, 2, 3, 4]
    }

    def "test addAll"(MyAbstractCollection c, MyAbstractCollection added, boolean addResult, List expected) {
        when:
        def r = c.addAll(added)
        then:
        r == addResult
        c.eq(expected)
        where:
        c                         | added                         | addResult | expected
        new MyArrayList()         | new MyArrayList()             | false     | []
        new MyArrayList()         | new MyLinkedList()            | false     | []
        new MyArrayList()         | new MyArrayDeque()            | false     | []
        new MyArrayList()         | MyArrayList.of(*1..10, null)  | true      | [*1..10, null]
        new MyArrayList()         | MyLinkedList.of(*1..10, null) | true      | [*1..10, null]
        new MyArrayList()         | MyArrayDeque.of(*1..10)       | true      | [*1..10]
        new MyLinkedList()        | MyArrayList.of(*1..10, null)  | true      | [*1..10, null]
        new MyLinkedList()        | MyLinkedList.of(*1..10, null) | true      | [*1..10, null]
        new MyLinkedList()        | MyArrayDeque.of(*1..10)       | true      | [*1..10]
        new MyArrayDeque()        | MyArrayList.of(*1..10)        | true      | [*1..10]
        new MyArrayDeque()        | MyLinkedList.of(*1..10)       | true      | [*1..10]
        new MyArrayDeque()        | MyArrayDeque.of(*1..10)       | true      | [*1..10]
        new MyPriorityQueue()     | MyArrayList.of(*1..10)        | true      | [*1..10]

        MyArrayList.of(*1..10)    | MyArrayList.of(*1..10, null)  | true      | [*1..10] * 2 + [null]
        MyArrayList.of(*1..10)    | MyLinkedList.of(*1..10, null) | true      | [*1..10] * 2 + [null]
        MyArrayList.of(*1..10)    | MyArrayDeque.of(*1..10)       | true      | [*1..10] * 2
        MyLinkedList.of(*1..10)   | MyArrayList.of(*1..10, null)  | true      | [*1..10] * 2 + [null]
        MyLinkedList.of(*1..10)   | MyLinkedList.of(*1..10, null) | true      | [*1..10] * 2 + [null]
        MyLinkedList.of(*1..10)   | MyArrayDeque.of(*1..10)       | true      | [*1..10] * 2
        MyArrayDeque.of(*1..10)   | MyArrayList.of(*1..10)        | true      | [*1..10] * 2
        MyArrayDeque.of(*1..10)   | MyLinkedList.of(*1..10)       | true      | [*1..10] * 2
        MyArrayDeque.of(*1..10)   | MyArrayDeque.of(*1..10)       | true      | [*1..10] * 2
        MyPriorityQueue.of(*1..5) | MyPriorityQueue.of(6, 10)     | true      | [*1..6, 10]
    }

    def "test removeAll"(MyAbstractCollection c, MyAbstractCollection removed, boolean removeResult, List expected) {
        when:
        def r = c.removeAll(removed)
        then:
        r == removeResult
        c.eq(expected)
        where:
        c                          | removed                       | removeResult | expected
        new MyArrayList()          | new MyArrayList()             | false        | []
        new MyArrayList()          | new MyLinkedList()            | false        | []
        new MyArrayList()          | new MyArrayDeque()            | false        | []
        new MyArrayList()          | MyArrayList.of(*1..10, null)  | false        | []
        new MyArrayList()          | MyLinkedList.of(*1..10, null) | false        | []
        new MyArrayList()          | MyArrayDeque.of(*1..10)       | false        | []
        new MyLinkedList()         | MyArrayList.of(*1..10, null)  | false        | []
        new MyLinkedList()         | MyLinkedList.of(*1..10, null) | false        | []
        new MyLinkedList()         | MyArrayDeque.of(*1..10)       | false        | []
        new MyArrayDeque()         | MyArrayList.of(*1..10)        | false        | []
        new MyArrayDeque()         | MyLinkedList.of(*1..10)       | false        | []
        new MyArrayDeque()         | MyArrayDeque.of(*1..10)       | false        | []
        new MyPriorityQueue()      | MyPriorityQueue.of(*1..10)    | false        | []

        MyArrayList.of(*1..10)     | MyArrayList.of(*5..10, null)  | true         | [*1..4]
        MyArrayList.of(*1..10)     | MyLinkedList.of(*5..10, null) | true         | [*1..4]
        MyArrayList.of(*1..10)     | MyArrayDeque.of(*5..10)       | true         | [*1..4]
        MyLinkedList.of(*1..10)    | MyArrayList.of(*5..10, null)  | true         | [*1..4]
        MyLinkedList.of(*1..10)    | MyLinkedList.of(*5..10, null) | true         | [*1..4]
        MyLinkedList.of(*1..10)    | MyArrayDeque.of(*5..10)       | true         | [*1..4]
        MyArrayDeque.of(*1..10)    | MyArrayList.of(*5..10)        | true         | [*1..4]
        MyArrayDeque.of(*1..10)    | MyLinkedList.of(*5..10)       | true         | [*1..4]
        MyArrayDeque.of(*1..10)    | MyArrayDeque.of(*5..10)       | true         | [*1..4]
        MyPriorityQueue.of(*1..10) | MyPriorityQueue.of(*5..10)    | true         | [*1..4]

        MyArrayList.of(*1..10)     | new MyArrayList()             | false        | [*1..10]
        MyLinkedList.of(*1..10)    | new MyArrayList()             | false        | [*1..10]
        MyArrayDeque.of(*1..10)    | new MyArrayList()             | false        | [*1..10]
        MyPriorityQueue.of(*1..10) | new MyArrayList()             | false        | [*1..10]
    }

    def "test retainAll"(MyAbstractCollection c, MyAbstractCollection retained, boolean retainResult, List expected) {
        when:
        def r = c.retainAll(retained)
        then:
        r == retainResult
        c.eq(expected)
        where:
        c                          | retained                      | retainResult | expected
        new MyArrayList()          | new MyArrayList()             | false        | []
        new MyArrayList()          | new MyLinkedList()            | false        | []
        new MyArrayList()          | new MyArrayDeque()            | false        | []
        new MyArrayList()          | MyArrayList.of(*1..10, null)  | false        | []
        new MyArrayList()          | MyLinkedList.of(*1..10, null) | false        | []
        new MyArrayList()          | MyArrayDeque.of(*1..10)       | false        | []
        new MyLinkedList()         | MyArrayList.of(*1..10, null)  | false        | []
        new MyLinkedList()         | MyLinkedList.of(*1..10, null) | false        | []
        new MyLinkedList()         | MyArrayDeque.of(*1..10)       | false        | []
        new MyArrayDeque()         | MyArrayList.of(*1..10)        | false        | []
        new MyArrayDeque()         | MyLinkedList.of(*1..10)       | false        | []
        new MyArrayDeque()         | MyArrayDeque.of(*1..10)       | false        | []
        new MyPriorityQueue()      | MyPriorityQueue.of(*1..10)    | false        | []

        MyArrayList.of(*1..10)     | MyArrayList.of(*5..10, null)  | true         | [*5..10]
        MyArrayList.of(*1..10)     | MyLinkedList.of(*5..10, null) | true         | [*5..10]
        MyArrayList.of(*1..10)     | MyArrayDeque.of(*5..10)       | true         | [*5..10]
        MyLinkedList.of(*1..10)    | MyArrayList.of(*5..10, null)  | true         | [*5..10]
        MyLinkedList.of(*1..10)    | MyLinkedList.of(*5..10, null) | true         | [*5..10]
        MyLinkedList.of(*1..10)    | MyArrayDeque.of(*5..10)       | true         | [*5..10]
        MyArrayDeque.of(*1..10)    | MyArrayList.of(*5..10)        | true         | [*5..10]
        MyArrayDeque.of(*1..10)    | MyLinkedList.of(*5..10)       | true         | [*5..10]
        MyArrayDeque.of(*1..10)    | MyArrayDeque.of(*5..10)       | true         | [*5..10]
        MyPriorityQueue.of(*1..10) | MyPriorityQueue.of(*5..10)    | true         | [5, 7, 6, 8, 10, 9]

        MyArrayList.of(*1..10)     | new MyArrayList()             | true         | []
        MyLinkedList.of(*1..10)    | new MyArrayList()             | true         | []
        MyArrayDeque.of(*1..10)    | new MyArrayList()             | true         | []
        MyPriorityQueue.of(*1..10) | new MyArrayList()             | true         | []

        MyArrayList.of(*1..10)     | MyArrayList.of(*1..10)        | false        | [*1..10]
        MyLinkedList.of(*1..10)    | MyArrayList.of(*1..10)        | false        | [*1..10]
        MyArrayDeque.of(*1..10)    | MyArrayList.of(*1..10)        | false        | [*1..10]
        MyPriorityQueue.of(*1..10) | MyArrayList.of(*1..10)        | false        | [*1..10]

    }

    def "test clear"(MyAbstractCollection c) {
        when:
        c.clear()
        then:
        c.size() == 0
        c.isEmpty()
        where:
        c                          | _
        MyArrayList.of(*1..10)     | _
        MyLinkedList.of(*1..10)    | _
        MyArrayDeque.of(*1..10)    | _
        MyPriorityQueue.of(*1..10) | _
    }
}
