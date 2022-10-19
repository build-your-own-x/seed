package com.techzealot.collection.tree.bst


import spock.lang.Specification

class AbstractBSTTest extends Specification {
    def "test size"(Object[] from, int expected) {
        when:
        def basedBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        basedBST.size() == expected
        rankedBST.size() == expected
        where:
        from       | expected
        []         | 0
        [*1..100]  | 100
        [*1000..1] | 1000
    }

    def "test isEmpty"(Object[] from, boolean expected) {
        when:
        def basedBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        basedBST.isEmpty() == expected
        rankedBST.isEmpty() == expected
        where:
        from      | expected
        []        | true
        [1]       | false
        [*1..100] | false
    }

    private class Person {
        private int age
        private String name

        Person(int age, String name) {
            this.age = age
            this.name = name
        }


        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Person person = (Person) o

            if (age != person.age) return false
            if (name != person.name) return false

            return true
        }

        int hashCode() {
            int result
            result = age
            result = 31 * result + name.hashCode()
            return result
        }
    }

    def "test put"(Object[] from, List expected) {
        given:
        def basedBST = BaseBST.of()
        def rankedBST = RankedBST.of()
        when:
        from.each {
            basedBST.put(it)
            rankedBST.put(it)
        }
        then:
        basedBST.toList() == expected
        rankedBST.toList() == expected
        where:
        from                  | expected
        []                    | []
        [4, 2, 6, 1, 3, 5, 7] | [*1..7]
        [*1..7]               | [*1..7]
    }

    def "test put as replace"(Object[] from, List expected) {
        given:
        def comparator = (Person p1, Person p2) -> { p1.age - p2.age }
        def basedBST = new BaseBST(comparator)
        def rankedBST = new RankedBST(comparator)
        when: "测试覆盖"
        from.each {
            basedBST.put(it)
            rankedBST.put(it)
        }
        basedBST.put(new Person(3, "cc"))
        rankedBST.put(new Person(3, "cc"))
        then:
        basedBST.toList() == expected
        rankedBST.toList() == expected
        where:
        from                                                         | expected
        [new Person(1, "a"), new Person(2, "b"), new Person(3, "c")] | [new Person(1, "a"), new Person(2, "b"), new Person(3, "cc")]
    }

    def "test put non-comparable and no comparator"() {
        given:
        def basedBST = new BaseBST()
        def rankedBST = new RankedBST()
        when:
        //第一个元素由于不会与其他元素比较不会触发类转换异常
        basedBST.put(new Object())
        basedBST.put(new Object())
        //添加第二个元素触发异常
        rankedBST.put(new Object())
        rankedBST.put(new Object())
        then:
        thrown(ClassCastException)
    }


    def "test remove"(Object[] from, Object o, boolean expected, List list) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.remove(o) == expected
        rankedBST.remove(o) == expected
        baseBST.toList() == list
        rankedBST.toList() == list
        where:
        from                                | o            | expected | list
        []                                  | null         | false    | []
        []                                  | new Object() | false    | []
        [4, 2, 6, 1, 3, 5, 7]               | null         | false    | [*1..7]
        [4, 2, 6, 1, 3, 5, 7]               | new Object() | false    | [*1..7]
        [4, 2, 6, 1, 3, 5, 7]               | 8            | false    | [*1..7]
        [1]                                 | 1            | true     | []
        [4, 2, 6, 1, 3, 5, 10, 8, 11, 7, 9] | 6            | true     | [*1..5, *7..11]
        [4, 2, 6, 1, 3, 5]                  | 1            | true     | [*2..6]
    }

    def "test contains"(Object[] from, Object o, boolean expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.contains(o) == expected
        rankedBST.contains(o) == expected
        where:
        from                  | o            | expected
        []                    | null         | false
        []                    | new Object() | false
        [4, 2, 6, 1, 3, 5, 7] | null         | false
        [4, 2, 6, 1, 3, 5, 7] | new Object() | false
        [4, 2, 6, 1, 3, 5, 7] | 6            | true
        [4, 2, 6, 1, 3, 5, 7] | 2            | true
        [4, 2, 6, 1, 3, 5, 7] | 8            | false
    }
//
//    def "test preOrderNR"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test inOrderNR"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test postOrderNR"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
    def "test preOrder"(Object[] from, List expected) {
        given:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        when:
        def out1 = []
        def out2 = []
        baseBST.preOrder(out1::add)
        rankedBST.preOrder(out2::add)
        then:
        out1 == expected
        out2 == expected
        where:
        from                  | expected
        []                    | []
        [4, 2, 6, 1, 3, 5, 7] | [4, 2, 1, 3, 6, 5, 7]
    }

    def "test inOrder"(Object[] from, List expected) {
        given:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        when:
        def out1 = []
        def out2 = []
        baseBST.inOrder(out1::add)
        rankedBST.inOrder(out2::add)
        then:
        out1 == expected
        out2 == expected
        where:
        from                  | expected
        []                    | []
        [4, 2, 6, 1, 3, 5, 7] | [*1..7]
    }

    def "test postOrder"(Object[] from, List expected) {
        given:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        when:
        def out1 = []
        def out2 = []
        baseBST.postOrder(out1::add)
        rankedBST.postOrder(out2::add)
        then:
        out1 == expected
        out2 == expected
        where:
        from                  | expected
        []                    | []
        [4, 2, 6, 1, 3, 5, 7] | [1, 3, 2, 5, 7, 6, 4]
    }

    def "test levelOrder"(Object[] from, List expected) {
        given:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        when:
        def out1 = []
        def out2 = []
        baseBST.levelOrder(out1::add)
        rankedBST.levelOrder(out2::add)
        then:
        out1 == expected
        out2 == expected
        where:
        from                  | expected
        []                    | []
        [4, 6, 2, 1, 5, 7, 3] | [4, 2, 6, 1, 3, 5, 7]
    }

    def "test minimum"(Object[] from, Object expected) {
        given:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        when:
        def m1 = baseBST.minimum()
        def m2 = rankedBST.minimum()
        then:
        m1 == expected
        m2 == expected
        where:
        from                  | expected
        []                    | null
        [4, 6, 2, 1, 5, 7, 3] | 1
        [4, 2, 3]             | 2
    }

    def "test removeMin"(Object[] from, Object min, List expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.removeMin() == min
        rankedBST.removeMin() == min
        baseBST.toList() == expected
        rankedBST.toList() == expected
        where:
        from                  | min  | expected
        []                    | null | []
        [4, 6, 2, 1, 5, 7, 3] | 1    | [*2..7]
        [4, 2, 3]             | 2    | [3, 4]
    }

    def "test maximum"(Object[] from, Object expected) {
        given:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        when:
        def m1 = baseBST.maximum()
        def m2 = rankedBST.maximum()
        then:
        m1 == expected
        m2 == expected
        where:
        from                  | expected
        []                    | null
        [4, 6, 2, 1, 5, 7, 3] | 7
        [4, 6, 5]             | 6
    }

    def "test removeMax"(Object[] from, Object max, List expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.removeMax() == max
        rankedBST.removeMax() == max
        baseBST.toList() == expected
        rankedBST.toList() == expected
        where:
        from                  | max  | expected
        []                    | null | []
        [4, 6, 2, 1, 5, 7, 3] | 7    | [*1..6]
        [4, 6, 5]             | 6    | [4, 5]
    }

    def "test floor"(Object[] from, Object floor, Object expected) {
        given:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        when:
        def f1 = baseBST.floor(floor)
        def f2 = rankedBST.floor(floor)
        then:
        f1 == expected
        f2 == expected
        where:
        from                  | floor | expected
        []                    | 10    | null
        [4, 6, 2, 1, 5, 9, 3] | 8     | 6
        [4, 6, 2, 1, 5, 9, 3] | 1     | 1
        [4, 6, 2, 1, 5, 9, 3] | -1    | null
    }

    def "test ceiling"(Object[] from, Object ceiling, Object expected) {
        given:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        when:
        def f1 = baseBST.ceiling(ceiling)
        def f2 = rankedBST.ceiling(ceiling)
        then:
        f1 == expected
        f2 == expected
        where:
        from                  | ceiling | expected
        []                    | 10      | null
        [4, 6, 2, 1, 5, 9, 3] | 10      | null
        [4, 6, 2, 1, 5, 9, 3] | 8       | 9
        [4, 6, 2, 1, 5, 9, 3] | 1       | 1
        [4, 6, 2, 1, 5, 9, 3] | -1      | 1
    }

    def "test constructors"(Object[] from, List expected, Comparator comparator) {
        when:
        def baseBST = new BaseBST(comparator)
        def rankedBST = new RankedBST(comparator)
        from.each {
            baseBST.put(it)
            rankedBST.put(it)
        }
        then:
        baseBST.toList() == expected
        rankedBST.toList() == expected
        where:
        from                  | expected | comparator
        []                    | []       | null
        []                    | []       | Comparator<Integer>.naturalOrder()
        [4, 2, 6, 1, 3, 5, 7] | [*1..7]  | null
        [4, 2, 6, 1, 3, 5, 7] | [*1..7]  | Comparator<Integer>.naturalOrder()
        [4, 2, 6, 1, 3, 5, 7] | [*7..1]  | Comparator<Integer>.reverseOrder()
    }

    def "test of and toList"(Object[] from, List expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.toList() == expected
        rankedBST.toList() == expected
        where:
        from                  | expected
        []                    | []
        [4, 2, 6, 1, 3, 5, 7] | [*1..7]
        [*1..7]               | [*1..7]
    }

    def "test of from null array or null element"(Object[] array) {
        when:
        BaseBST.of(array)
        then:
        thrown(NullPointerException)
        when:
        RankedBST.of(array)
        then:
        thrown(NullPointerException)
        where:
        array           | _
        null            | _
        [1, 2, 3, null] | _
    }

    def "test predecessor and superPredecessor"(Object[] from, Object predecessor, Object expected) {
        when:
        def rankedBST = RankedBST.of(from)
        def baseBST = BaseBST.of(from)
        then:
        rankedBST.predecessor(predecessor) == expected
        baseBST.predecessor(predecessor) == expected
        baseBST.superPredecessor(predecessor) == expected
        where:
        from                                | predecessor | expected
        []                                  | 1           | null
        [1]                                 | 1           | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 1           | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | -1          | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 12          | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 10          | 9
        [4, 2, 6, 1, 3, 5, 10, 11]          | 10          | 6
        [4, 2, 11, 1, 3, 10]                | 10          | 4
    }

    def "test successor and superSuccessor"(Object[] from, Object successor, Object expected) {
        when:
        def rankedBST = RankedBST.of(from)
        def baseBST = BaseBST.of(from)
        then:
        rankedBST.successor(successor) == expected
        baseBST.successor(successor) == expected
        baseBST.superSuccessor(successor) == expected
        where:
        from                                | successor | expected
        []                                  | 1         | null
        [1]                                 | 1         | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | -1        | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 11        | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 12        | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 6         | 7
        [4, 2, 11, 1, 3, 10, 8, 7, 9]       | 10        | 11
        [4, 2, 3]                           | 3         | 4
    }

    def "test levelOrderIterator"(Object[] from, List expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.levelOrderIterator().toList() == expected
        rankedBST.levelOrderIterator().toList() == expected
        where:
        from                  | expected
        []                    | []
        [1]                   | [1]
        [4, 2, 1, 3, 6, 5, 7] | [4, 2, 6, 1, 3, 5, 7]
    }

    def "test preOrderIterator"(Object[] from, List expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.preOrderIterator().toList() == expected
        rankedBST.preOrderIterator().toList() == expected
        where:
        from                  | expected
        []                    | []
        [1]                   | [1]
        [4, 2, 1, 3, 6, 5, 7] | [4, 2, 1, 3, 6, 5, 7]
    }

    def "test inOrderIterator"(Object[] from, List expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.inOrderIterator().toList() == expected
        rankedBST.inOrderIterator().toList() == expected
        where:
        from                     | expected
        []                       | []
        [1]                      | [1]
        [4, 2, 1, 3, 6, 5, 7]    | [*1..7]
        [5, 3, 7, 1, 4, 2, 6, 8] | [*1..8]
    }

    def "test postOrderIterator"(Object[] from, List expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.postOrderIterator().toList() == expected
        rankedBST.postOrderIterator().toList() == expected
        where:
        from                            | expected
        []                              | []
        [1]                             | [1]
        [4, 2, 1, 3, 6, 5, 7]           | [1, 3, 2, 5, 7, 6, 4]
        [7, 5, 9, 1, 6, 8, 10, 3, 2, 4] | [2, 4, 3, 1, 6, 5, 8, 10, 9, 7]
    }

    def "test toString"(Object[] from, String expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.toString() == expected
        rankedBST.toString() == expected
        where:
        from                            | expected
        []                              | ""
        [1]                             | "1\n"
        [4, 2, 1, 3, 6, 5, 7]           | '''\
4
[L]2
[L][L]1
[L][R]3
[R]6
[R][L]5
[R][R]7
'''
        [7, 5, 9, 1, 6, 8, 10, 3, 2, 4] | '''\
7
[L]5
[L][L]1
[L][L][R]3
[L][L][R][L]2
[L][L][R][R]4
[L][R]6
[R]9
[R][L]8
[R][R]10
'''
    }

    def "test root"(Object[] from, Object expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        then:
        baseBST.root()?.value() == expected
        rankedBST.root()?.value() == expected
        where:
        from                            | expected
        []                              | null
        [1]                             | 1
        [4, 2, 1, 3, 6, 5, 7]           | 4
        [7, 5, 9, 1, 6, 8, 10, 3, 2, 4] | 7
    }

    def "test preOrderNR"(Object[] from, List expected) {
        when:
        def baseBST = BaseBST.of(from)
        def rankedBST = RankedBST.of(from)
        def baseList = []
        def rankedList = []
        baseBST.preOrderNR {
            baseList += it
        }
        rankedBST.preOrderNR {
            rankedList += it
        }
        then:
        baseList == expected
        rankedList == expected
        where:
        from                            | expected
        []                              | []
        [1]                             | [1]
        [4, 2, 3, 1, 6, 7, 5]           | [4, 2, 1, 3, 6, 5, 7]
        [7, 5, 9, 1, 6, 8, 10, 3, 2, 4] | [7, 5, 1, 3, 2, 4, 6, 9, 8, 10]
    }

}
