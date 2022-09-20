package com.techzealot.collection.tree.unionfind;

import java.text.MessageFormat;

/**
 * quick find
 */
public class ArrayUF implements UF {

    /**
     * 元素集合id
     */
    private int[] ids;

    public ArrayUF(int size) {
        this.ids = new int[size];
        //初始化时所有元素具有不同的集合id,即互不相连
        for (int i = 0; i < size; i++) {
            ids[i] = i;
        }
    }

    /**
     * O(n)
     *
     * @param p
     * @param q
     */
    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);
        if (pId == qId) return;
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == pId) {
                ids[i] = qId;
            }
        }
    }

    /**
     * O(1)
     *
     * @param p 元素id
     * @return
     */
    @Override
    public int find(int p) {
        if (p < 0 || p >= ids.length)
            throw new IllegalArgumentException(MessageFormat.format("{0} is out of bound:[0,{1})", p, ids.length));
        return ids[p];
    }

    @Override
    public int size() {
        return ids.length;
    }
}
