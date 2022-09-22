package com.techzealot.collection.tree.segmenttree

import spock.lang.Specification

class RangeOperationTest extends Specification {
    def "test update"() {
        given:
        def sumArray = new SumArray([1, 2, 3, 4] as int[])
        when:
        sumArray.update(2, 4)
        sumArray.update(1, 4)
        then:
        sumArray.query(0, 2) == 9
        sumArray.query(0, 3) == 13
    }

    def "test update with illegal args"(int i, int e) {
        given:
        def sumArray = new SumArray([1, 2, 3, 4] as int[])
        when:
        sumArray.update(i, e)
        then:
        thrown(IllegalArgumentException)
        where:
        i  | e
        -1 | 1
        4  | 1
    }

    def "test query"(List from, int i, int j, int sum) {
        when:
        def sumArray = new SumArray(from as int[])
        then:
        sumArray.query(i, j) == sum
        where:
        from        | i | j | sum
        [-1, -2, 1] | 0 | 0 | -1
        [-1, -2, 1] | 0 | 1 | -3
        [-1, -2, 1] | 0 | 2 | -2
        [-1, -2, 1] | 1 | 1 | -2
        [-1, -2, 1] | 1 | 2 | -1
        [-1, -2, 1] | 2 | 2 | 1
    }

    def "test query with illegal args"(int i, int j) {
        given:
        def sumArr = new SumArray(new int[]{1, 2, 3})
        when:
        sumArr.query(i, j)
        then:
        thrown(IllegalArgumentException)
        where:
        i  | j
        -1 | 0
        -1 | -1
        0  | 3
        2  | 1
    }
}
