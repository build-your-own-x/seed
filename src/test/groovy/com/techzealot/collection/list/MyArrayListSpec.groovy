package com.techzealot.collection.list


import org.joor.Reflect
import spock.lang.Specification
import spock.lang.Unroll

class MyArrayListSpec extends Specification {
    def "init with no args"() {
        when:
        MyArrayList<Integer> list = new MyArrayList<>()
        def elementData = Reflect.on(list).field("elementData")
        then:
        list.size() == 0
        elementData != null
        elementData == Reflect.onClass(MyArrayList.class).field("DEFAULTCAPACITY_EMPTY_ELEMENTDATA")
    }

    @Unroll
    def "init with initial capacity"(int initialCapacity) {
        when: "init with illegal capacity"
        new MyArrayList<Integer>(-1)
        then:
        thrown(IllegalArgumentException)
        when:
        def empty = new MyArrayList<Integer>(0)
        def elementData = Reflect.on(empty).field("elementData")
        then:
        elementData == Reflect.onClass(MyArrayList.class).field("EMPTY_ELEMENTDATA")
        when:
        MyArrayList<Integer> list = new MyArrayList<>(initialCapacity)
        then:
        list.size() == 0
        list.getCapacity() == initialCapacity
        where:
        initialCapacity | _
        1               | _
        10              | _
        1024            | _
    }

    def "init with collection"(MyCollection<Integer> collection) {

    }

    def "add at last"() {
        when: "init with no arg"
        MyArrayList<Integer> c = new MyArrayList<>()
        c.add(1)
        then:
        c.size() == 1
        c.getCapacity() == 10
        c.get(0) == 1
        when: "init with capacity 0"
        MyArrayList<Integer> c1 = new MyArrayList<>(0)
        c1.add(1)
        then:
        c1.size() == 1
        c1.getCapacity() == 1
        c1.get(0) == 1
        when: "init with capacity 16"
        MyArrayList<Integer> c2 = new MyArrayList<>(-1)
        c2.add(1)
        then:
        c2.size() == 1
        c2.getCapacity() == 1
        c2.get(0) == 1
    }

    def "add by index"() {

    }

    def "set by index"() {

    }

    def "get element by index"() {

    }
}
