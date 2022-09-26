package com.techzealot.collection.tree.segmenttree

import spock.lang.Specification

class SparseTableTest extends Specification {

    def range = [3, 2, 4, 5, 6, 8, 1, 2, 9, 7] as int[]

    def "test RMQ use ST"(int l, int r, int max, int min) {
        when:
        def st = new SparseTable(range)
        then:
        st.max(l, r) == max
        st.min(l, r) == min
        where:
        l | r | max | min
        0 | 0 | 3   | 3
        0 | 1 | 3   | 2
        0 | 2 | 4   | 2
        0 | 3 | 5   | 2
        0 | 4 | 6   | 2
        0 | 5 | 8   | 2
        0 | 6 | 8   | 1
        0 | 7 | 8   | 1
        0 | 8 | 9   | 1
        0 | 9 | 9   | 1
    }
}
