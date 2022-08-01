package com.techzealot.collection.list


import org.joor.Reflect
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.mop.Use

@Category(MyArrayList)
class MyArrayListExtensions {
    def <E> boolean eq(List<E> expected) {
        return this.toArray() == expected as E[]
    }
}

@Use(MyArrayListExtensions)
class MyArrayListSpec extends Specification {

    def "init with no args"() {
        when:
        def list = new MyArrayList<>()
        def elementData = Reflect.on(list).field("elementData")
        then:
        list.size() == 0
        list.getCapacity() == 0
        elementData != null
        elementData == Reflect.onClass(MyArrayList.class).field("DEFAULTCAPACITY_EMPTY_ELEMENTDATA")
    }

    @Unroll
    def "init with initial capacity"(int initialCapacity, int expected) {
        when: "init with illegal capacity"
        new MyArrayList<Integer>(-1)
        then:
        thrown(IllegalArgumentException)
        when: "init with capacity 0"
        def empty = new MyArrayList<Integer>(0)
        def elementData = Reflect.on(empty).field("elementData")
        then:
        elementData == Reflect.onClass(MyArrayList.class).field("EMPTY_ELEMENTDATA")
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
        //todo init with other collection type
        when:
        def list = new MyArrayList<Integer>(empty)
        then:
        list.size() == 0
        list.getCapacity() == 0
        Reflect.on(list).field("elementData") == Reflect.onClass(MyArrayList.class).field("EMPTY_ELEMENTDATA")
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
        given:
        def reflect = Reflect.onClass(MyArrayList.class)
        when:
        reflect.call("hugeCapacity", Integer.MAX_VALUE + 1).get()
        then:
        def e = thrown(Exception)
        e.asString().contains("java.lang.OutOfMemoryError")
        when:
        def r = reflect.call("hugeCapacity", minCapacity).get()
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
        m.remove(m.size())
        then:
        thrown(IndexOutOfBoundsException)
        when: "remove head"
        def r = m.remove(0)
        then:
        r == "a"
        m.eq(["b", "c", "d", "e"])
        when: "remove tail"
        r = m.remove(3)
        then:
        r == "e"
        m.eq(["b", "c", "d"])
        when: "remove middle"
        r = m.remove(1)
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

    def "to array"() {
        when:
        def m = MyArrayList.of(1, 2, 3)
        then:
        m.eq([1, 2, 3])
    }

    def "contains"() {
        when:
        def m = MyArrayList.of(1, 2, 3, 4, 5, null)
        then:
        m.contains(1)
        m.contains(2)
        m.contains(3)
        m.contains(4)
        m.contains(5)
        m.contains(Integer.valueOf(1))
        m.contains(null)
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

    def "removeAll"() {
        given:
        def mc = MyArrayList.of(1, 2, 3, 4, 5, 6, null)
        def deleted = MyArrayList.of(1, 3, 5, null)
        def empty = new MyArrayList()
        when:
        mc.removeAll(null)
        then:
        thrown(NullPointerException)
        when:
        mc.removeAll(empty)
        then:
        mc.eq((1..6) + null)
        when:
        mc.removeAll(deleted)
        then:
        mc.eq([2, 4, 6])
    }

    @Ignore("TODO")
    def "removeAll when contains throws"() {

    }

    def "retainAll"() {
        given:
        def mc = MyArrayList.of(1, 2, 3, 4, 5, 6, null)
        def deleted = MyArrayList.of(1, 3, 5, null)
        def empty = new MyArrayList()
        when:
        mc.retainAll(deleted)
        then:
        mc.eq([1, 3, 5, null])
        when:
        mc.retainAll(empty)
        then:
        mc.eq([])
    }

    @Ignore("TODO")
    def "retainAll when contains throws"() {

    }

    def "clear"() {
        given:
        def c = MyArrayList.<Integer> of(*1..5)
        when:
        c.clear()
        def elements = Reflect.on(c).field("elementData").get() as Object[]
        then:
        c.size() == 0
        c.getCapacity() == 5
        elements == [null] * 5 as Object[]
    }

    def "equals and hashcode"() {

    }

    def "clone"() {

    }

    def "serialize and deserialize"() {

    }

    def "iterator"() {

    }

}
