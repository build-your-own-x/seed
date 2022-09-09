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

    def "test add"(Object[] from, List expected) {
        given:
        def basedBST = BaseBST.of()
        def rankedBST = RankedBST.of()
        when:
        from.each {
            basedBST.add(it)
            rankedBST.add(it)
        }
        def a1 = basedBST.add(8)
        def a2 = rankedBST.add(8)
        def a3 = basedBST.add(8)
        def a4 = rankedBST.add(8)
        then:
        a1 && a2
        !a3 && !a4
        basedBST.toList() == expected
        rankedBST.toList() == expected
        where:
        from                  | expected
        []                    | [8]
        [4, 2, 6, 1, 3, 5, 7] | [*1..8]
        [*1..7]               | [*1..8]
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
        from                         | o            | expected | list
        []                           | null         | false    | []
        []                           | new Object() | false    | []
        [4, 2, 6, 1, 3, 5, 7]        | null         | false    | [*1..7]
        [4, 2, 6, 1, 3, 5, 7]        | new Object() | false    | [*1..7]
        [4, 2, 6, 1, 3, 5, 7]        | 8            | false    | [*1..7]
        [1]                          | 1            | true     | []
        [4, 2, 6, 1, 3, 5, 8, 9, 10] | 6            | true     | [*1..5, *8..10]
        [4, 2, 6, 1, 3, 5]           | 6            | true     | [*1..5]
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
//    def "test preOrder"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test inOrder"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test postOrder"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test levelOrder"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test minimum"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test removeMin"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test maximum"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test removeMax"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test floor"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }
//
//    def "test ceiling"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }

    def "test constructors"(Object[] from, List expected, Comparator comparator) {
        when:
        def baseBST = new BaseBST(comparator)
        def rankedBST = new RankedBST(comparator)
        from.each {
            baseBST.add(it)
            rankedBST.add(it)
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
}
