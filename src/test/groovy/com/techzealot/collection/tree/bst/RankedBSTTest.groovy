package com.techzealot.collection.tree.bst

import spock.lang.Specification

class RankedBSTTest extends Specification {
    def "test rank"(Object[] from, Object o, int expected) {
        when:
        def rankedBST = RankedBST.of(from)
        then:
        rankedBST.rank(o) == expected
        where:
        from                                | o    | expected
        []                                  | 1    | -2
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | null | -2
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 1    | 0
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | -1   | -2
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 6    | 5
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 11   | 10
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 100  | -2
    }

    def "test select"(Object[] from, int rank, Object expected) {
        when:
        def rankedBST = RankedBST.of(from)
        then:
        rankedBST.select(rank) == expected
        where:
        from                                | rank | expected
        []                                  | 1    | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 0    | 1
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | -1   | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 12   | null
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 3    | 4
        [4, 2, 6, 1, 3, 5, 10, 8, 7, 9, 11] | 9    | 10
    }
}
