package com.techzealot.collection.tree.unionfind

import spock.lang.Specification

class UFTest extends Specification {
    /**
     * 使用综合案例测试所有实现
     * 单独测试各个方法意义不大
     * 通过行为进行测试而非具体实现
     */
    def "test union find in usecase"(UF uf, int size) {
        when:
        uf.union(1, 2)
        uf.union(3, 4)
        uf.union(5, 6)
        then:
        uf.size() == size
        uf.isConnected(1, 2)
        uf.isConnected(3, 4)
        uf.isConnected(5, 6)
        !uf.isConnected(1, 3)
        !uf.isConnected(3, 7)
        !uf.isConnected(5, 8)
        where:
        uf                          | size
        new ArrayUF(10)             | 10
        new TreeUF(10)              | 10
        new TreeWithSizeUF(10)      | 10
        new TreeWithRankUF(100)     | 100
        new TreeWithRankPathUF(100) | 100
    }
}
