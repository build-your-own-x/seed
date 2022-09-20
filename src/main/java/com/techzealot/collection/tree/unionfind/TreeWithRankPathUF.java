package com.techzealot.collection.tree.unionfind;

import java.text.MessageFormat;

/**
 * 路径压缩后不需要维护rank:
 * 1.优化性能
 * 2.rank不再代表树高,仅作为合并时的参考值
 * O(log*n)快于O(logN)近似于O(1)
 */
public class TreeWithRankPathUF extends TreeWithRankUF {
    public TreeWithRankPathUF(int size) {
        super(size);
    }

    @Override
    public int find(int p) {
        if (p < 0 || p >= parents.length) {
            throw new IllegalArgumentException(MessageFormat.format("{0} is out of bound:[0,{1})", p, parents.length));
        }
        return findAndCompressed2(p);
    }

    private int findAndCompressed1(int p) {
        while (p != parents[p]) {
            //其中一种压缩方式
            parents[p] = parents[parents[p]];
            p = parents[p];
        }
        return p;
    }

    /**
     * 递归压缩，直接将高度压缩为2
     *
     * @param p
     * @return p节点的根节点
     */
    private int findAndCompressed2(int p) {
        if (p != parents[p]) {
            parents[p] = findAndCompressed2(parents[p]);
        }
        return parents[p];
    }
}
