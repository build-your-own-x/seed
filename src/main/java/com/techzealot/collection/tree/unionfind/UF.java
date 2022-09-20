package com.techzealot.collection.tree.unionfind;

/**
 * 高效解决连接问题
 */
public interface UF {
    default boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * @param p
     * @param q
     */
    void union(int p, int q);

    /**
     * @param p 元素id
     * @return 集合id
     */
    int find(int p);

    int size();
}
