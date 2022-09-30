package com.techzealot.collection.tree.segmenttree

import spock.lang.Specification

class RangeOperationTest extends Specification {
    def "test update"(RangeOperation rop) {
        when:
        rop.update(2, 4)
        rop.update(1, 4)
        then:
        rop.query(0, 2) == 9
        rop.query(0, 3) == 13
        where:
        rop                                                                   | _
        new SumArray([1, 2, 3, 4] as int[])                                   | _
        new ArraySegmentTree([1, 2, 3, 4] as Object[], (a1, a2) -> a1 + a2)   | _
        new DynamicSegmentTree([1, 2, 3, 4] as Object[], (a1, a2) -> a1 + a2) | _
    }

    def "test update with illegal args"(RangeOperation rop, int i, int e) {
        when:
        rop.update(i, e)
        then:
        thrown(IllegalArgumentException)
        where:
        rop                                                                   | i  | e
        new SumArray([1, 2, 3, 4] as int[])                                   | -1 | 1
        new SumArray([1, 2, 3, 4] as int[])                                   | 4  | 1
        new ArraySegmentTree([1, 2, 3, 4] as Object[], (a1, a2) -> a1 + a2)   | -1 | 1
        new ArraySegmentTree([1, 2, 3, 4] as Object[], (a1, a2) -> a1 + a2)   | 4  | 1
        new DynamicSegmentTree([1, 2, 3, 4] as Object[], (a1, a2) -> a1 + a2) | -1 | 1
        new DynamicSegmentTree([1, 2, 3, 4] as Object[], (a1, a2) -> a1 + a2) | 4  | 1
    }

    def "test query"(List from, int i, int j, int sum) {
        when:
        def sumArray = new SumArray(from as int[])
        def merger = (a1, a2) -> a1 + a2
        def ast = new ArraySegmentTree(from as Object[], merger)
        def dst = new DynamicSegmentTree(from as Object[], merger)
        then:
        sumArray.query(i, j) == sum
        ast.query(i, j) == sum
        dst.query(i, j) == sum
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
        def from = new int[]{1, 2, 3}
        def sumArray = new SumArray(from)
        def merger = (a1, a2) -> a1 + a2
        def ast = new ArraySegmentTree(from as Object[], merger)
        def dst = new DynamicSegmentTree(from as Object[], merger)
        when:
        sumArray.query(i, j)
        ast.query(i, j)
        dst.query(i, j)
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
