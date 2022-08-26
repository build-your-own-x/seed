package com.techzealot

import spock.lang.Specification

class LenientMatchTest extends Specification {
    /**
     * =~ 转换为Set后进行比较
     * x=~y ==> x as Set == y as Set
     */
    def "test lenient match"(List list, List expected, List not) {
        when:
        def x = list
        then:
        x =~ expected
        !(x =~ not).matches()
        where:
        list         | expected  | not
        [1, 2, 3, 3] | [1, 2, 3] | [1, 2]
        [1, 2, 3, 3] | [1, 3, 2] | [1, 2]
        [1, 2, 3, 3] | [3, 2, 1] | [2, 4]
    }

    /**
     * ==~ ==> containsInAnyOrder
     * 不能判定反面
     */
    def "test strict match"(List list, List expected) {
        when:
        def x = list
        then:
        x ==~ expected
        where:
        list            | expected
        [1, 2, 2, 3, 3] | [1, 2, 3, 2, 3]
        [1, 2, 2, 3, 3] | [1, 2, 3, 2, 3]
        [1, 2, 2, 3, 3] | [3, 3, 1, 2, 2]
    }
}