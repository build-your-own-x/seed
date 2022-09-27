package com.techzealot.collection.tree.segmenttree

import spock.lang.Shared
import spock.lang.Specification

class SparseTableTest extends Specification {
    @Shared
    def range = new Random().ints(-100, 100).limit(30).toArray()

    def "test RMQ use ST"(int l, int r) {
        when:
        def st = new SparseTable(range)
        then:
        st.max(l, r) == max(range, l, r)
        st.min(l, r) == min(range, l, r)
        where:
        [l, r] << generateLR(range.length)
    }

    List generateLR(int len) {
        def list = []
        (0..<len).each { i ->
            (i..<len).each { j ->
                list.add([i, j])
            }
        }
        list
    }


    static max(int[] range, int i, int j) {
        int max = range[i]
        for (k in i..j) {
            max = Math.max(max, range[k])
        }
        return max
    }

    static min(int[] range, int i, int j) {
        int min = range[i]
        for (k in i..j) {
            min = Math.min(min, range[k])
        }
        return min
    }
}
