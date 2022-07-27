package com.techzealot.collection.list


import org.joor.Reflect
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

@Category(MyArrayList)
class ListUtils {
    def <E> boolean listEquals(List<E> expected) {
        return this.toArray() == expected as E[]
    }
}

class MyArrayListSpec extends Specification {

    def "init with no args"() {
        when:
        MyArrayList<Integer> list = new MyArrayList<>()
        def elementData = Reflect.on(list).field("elementData")
        then:
        list.size() == 0
        list.getCapacity() == 0
        elementData != null
        elementData == Reflect.onClass(MyArrayList.class).field("DEFAULTCAPACITY_EMPTY_ELEMENTDATA")
    }

    @Unroll
    def "init with initial capacity"(int initialCapacity) {
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

    def "init with collection"() {
        given:
        MyArrayList<Integer> c = MyArrayList.of(1, 2, 3)
        when:
        MyArrayList<Number> mc = new MyArrayList<>(c);
        then:
        mc.getCapacity() == 3
        mc.size() == 3
        use(ListUtils) {
            mc.listEquals([1, 2, 3])
        }
        when:
        mc.get(3)
        then:
        thrown(IndexOutOfBoundsException)
    }

    def "add at tail of array"() {
        given: "init with no arg"
        MyArrayList<Integer> c = new MyArrayList<>()
        when:
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
        when: "init with capacity 4"
        MyArrayList<Integer> c2 = new MyArrayList<>(4)
        c2.add(1)
        c2.add(2)
        c2.add(3)
        then:
        c2.size() == 3
        c2.getCapacity() == 4
        ListUtils.listEquals(c2, [1, 2, 3])
        when: "get out of index"
        c2.get(3)
        then:
        thrown(IndexOutOfBoundsException)
        when:
        c2.add(4)
        c2.add(5)
        then:
        c2.size() == 5
        c2.getCapacity() == 6
        ListUtils.listEquals(c2, [1, 2, 3, 4, 5])
    }

    def "add by index"() {
        given:
        MyArrayList<Integer> c = MyArrayList.of(1, 2, 3)
        when: "add at head"
        c.add(0, 0)
        then:
        c.size() == 4
        c.getCapacity() == 4
        ListUtils.listEquals(c, [0, 1, 2, 3])
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
        ListUtils.listEquals(c, [0, 1, 22, 2, 3, 4])
        when: "add out of size"
        c.add(7, 7)
        then:
        thrown(IndexOutOfBoundsException)
    }

    def "add all"() {
        given:
        MyArrayList<Integer> c1 = MyArrayList.of(1, 2)
        when: "add to empty list"
        MyArrayList<Number> c2 = new MyArrayList<>()
        c2.addAll(c1)
        then:
        c2.getCapacity() == 10
        c2.size() == 2
        ListUtils.listEquals(c2, [1, 2])
        when: "add to list with elements"
        MyArrayList<Integer> c3 = new MyArrayList<>(4)
        c3.add(3)
        c3.add(4)
        c3.addAll(c1)
        then:
        c3.size() == 4
        c3.getCapacity() == 4
        ListUtils.listEquals(c3, [3, 4, 1, 2])
    }

    def "remove by index"() {
        given:
        MyArrayList<Integer> m = MyArrayList.of(1, 2, 3, 4, 5)
        when:
        m.remove(6)
        then:
        thrown(IndexOutOfBoundsException)
        when: "remove head"
        m.remove(0)
        then:
        ListUtils.listEquals(m, [2, 3, 4, 5])
        when: "remove tail"
        m.remove(3)
        then:
        ListUtils.listEquals(m, [2, 3, 4])
        when: "remove middle"
        m.remove(1)
        then:
        ListUtils.listEquals(m, [2, 4])
    }

    def "remove by object"() {
        given:
        MyArrayList<String> m = MyArrayList.of("a", "b", "c", "d", "d", "e", null)
        when:
        m.remove("a")
        then:
        ListUtils.listEquals(m, ["b", "c", "d", "d", "e", null])
        when:
        m.remove("d")
        then:
        ListUtils.listEquals(m, ["b", "c", "d", "e", null])
        when:
        m.remove("d")
        then:
        ListUtils.listEquals(m, ["b", "c", "e", null])
        then: "validate return value"
        m.remove("b")
        !m.remove("x")
        when:
        m.remove(null)
        then:
        ListUtils.listEquals(m, ["c", "e"])
    }

    def "remove by collection"() {

    }

    def "set by index"() {
        given:
        MyArrayList<Integer> m = new MyArrayList<>(4)
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
        ListUtils.listEquals(m, [1, 1, 1, null])
    }

    def "get element by index"() {
        when:
        MyArrayList<Integer> m = MyArrayList.of(1, 2, 3, 4, 5)
        then:
        m.get(0) == 1
        m.get(1) == 2
        m.get(2) == 3
        m.get(3) == 4
        m.get(4) == 5
    }

    def "to array"() {
        when:
        MyArrayList<Integer> m = MyArrayList.of(1, 2, 3)
        then:
        ListUtils.listEquals(m, [1, 2, 3])
    }

    def "contains"() {
        when:
        MyArrayList<Integer> m = MyArrayList.of(1, 2, 3, 4, 5, null)
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
        MyArrayList<Integer> m = MyArrayList.of(1, 2, 3, 4, 5, null)
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
        MyArrayList<Integer> mc = MyArrayList.of(1, 2, 3, 4, 5, 6)
        MyArrayList<Integer> deleted = MyArrayList.of(1, 3, 5)
        when:
        mc.removeAll(deleted)
        then:
        ListUtils.listEquals(mc, [2, 4, 6])
    }

    @Ignore("TODO")
    def "removeAll when contains throws"() {

    }

    def "retainAll"() {
        given:
        MyArrayList<Integer> mc = MyArrayList.of(1, 2, 3, 4, 5, 6)
        MyArrayList<Integer> deleted = MyArrayList.of(1, 3, 5)
        when:
        mc.retainAll(deleted)
        then:
        ListUtils.listEquals(mc, [1, 3, 5])
    }

    @Ignore("TODO")
    def "retainAll when contains throws"() {
        
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
