package com.techzealot.collection

import com.techzealot.collection.deque.MyArrayDeque
import com.techzealot.collection.list.MyArrayList
import com.techzealot.collection.list.MyLinkedList
import spock.lang.Specification

class MyAbstractCollectionTest extends Specification {
    def "test toString"(MyAbstractCollection c, String expected) {
        when:
        def out = c.toString()
        def list = [*1..10]
        then:
        out == expected
        where:
        c                               | expected
        new MyArrayList()               | [].toString()
        MyArrayList.of(list.toArray())  | list.toString()
        new MyLinkedList()              | [].toString()
        MyLinkedList.of(list.toArray()) | list.toString()
        new MyArrayDeque()              | [].toString()
        MyArrayDeque.of(list.toArray()) | list.toString()
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
        input                   | _
        new MyArrayList<>()     | _
        MyArrayList.of(*1..10)  | _
        new MyLinkedList<>()    | _
        MyLinkedList.of(*1..10) | _
        new MyArrayDeque<>()    | _
        MyArrayDeque.of(*1..10) | _
    }

    def "test size"(MyAbstractCollection c, List from) {
        when:
        def size = c.size()
        then:
        size == from.size()
        where:
        c                               | from
        new MyArrayList()               | []
        new MyLinkedList<>()            | []
        new MyArrayDeque<>()            | []
        MyArrayList.of(from.toArray())  | [*1..10]
        MyLinkedList.of(from.toArray()) | [*1..10]
        MyArrayDeque.of(from.toArray()) | [*1..10]
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
        c                    | _
        new MyArrayList<>()  | _
        new MyLinkedList<>() | _
        new MyArrayDeque<>() | _
    }

    def "test contains"() {

    }

    def "test toArray"() {

    }

    def "test add(Object)"() {

    }

    def "test remove(Object)"() {

    }

    def "test addAll"() {

    }

    def "test removeAll"() {

    }

    def "test retainAll"() {

    }

    def "test clear"() {

    }
}
