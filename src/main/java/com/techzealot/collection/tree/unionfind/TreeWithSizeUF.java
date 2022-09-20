package com.techzealot.collection.tree.unionfind;

import java.util.Arrays;

/**
 * 基于树的大小优化,小树合并到大树(不一定最优,树高小的合并到树高大的才最优)
 */
public class TreeWithSizeUF extends TreeUF {
    private int[] sizes;

    public TreeWithSizeUF(int size) {
        super(size);
        sizes = new int[size];
        Arrays.fill(sizes, 1);
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;
        //元素个数少的合并到多的
        if (sizes[pRoot] < sizes[qRoot]) {
            parents[pRoot] = qRoot;
            sizes[qRoot] += sizes[pRoot];
        } else {
            parents[qRoot] = pRoot;
            sizes[pRoot] += sizes[qRoot];
        }
    }
}
