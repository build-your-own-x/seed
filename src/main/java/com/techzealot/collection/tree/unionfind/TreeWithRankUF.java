package com.techzealot.collection.tree.unionfind;

import java.util.Arrays;

/**
 * 基于rank(近似树高,路径压缩后不会处理作为参考)的优化,效果最直接
 */
public class TreeWithRankUF extends TreeUF {
    /**
     * 节点树高
     */
    private int[] ranks;

    public TreeWithRankUF(int size) {
        super(size);
        ranks = new int[size];
        Arrays.fill(ranks, 1);
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;
        //rank低的合并到rank高的
        if (ranks[pRoot] < ranks[qRoot]) {
            parents[pRoot] = qRoot;
        } else {
            parents[qRoot] = pRoot;
        }
        if (ranks[pRoot] == ranks[qRoot]) {
            //相等时树高+1
            ranks[qRoot] += 1;
        }
    }
}
