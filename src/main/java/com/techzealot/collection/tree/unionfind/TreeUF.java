package com.techzealot.collection.tree.unionfind;

import java.text.MessageFormat;

/**
 * quick union
 */
public class TreeUF implements UF {

    protected int[] parents;

    public TreeUF(int size) {
        parents = new int[size];
        for (int i = 0; i < size; i++) {
            parents[i] = i;
        }
    }

    /**
     * O(h)
     *
     * @param p
     * @param q
     */
    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;
        parents[pRoot] = qRoot;
    }

    /**
     * O(h)
     *
     * @param p 元素id
     * @return
     */
    @Override
    public int find(int p) {
        if (p < 0 || p >= parents.length) {
            throw new IllegalArgumentException(MessageFormat.format("{0} is out of bound:[0,{1})", p, parents.length));
        }
        while (p != parents[p]) {
            p = parents[p];
        }
        return p;
    }

    @Override
    public int size() {
        return parents.length;
    }
}
