package com.techzealot.collection.tree.segmenttree;

/**
 * Range Minimum/Maximum Query
 * 线段树以O(mlogn)解决RMQ问题(m是查询次数)
 * 但是如果m很大(一般1e6以上)，但是n比较小，就可以用ST算法解决RMQ问题
 * O(nlogn)预处理+O(1)查询
 */
public interface SparseTable<E> {
    /**
     * 求区间[i,j]最小值
     *
     * @param i
     * @param j
     * @return
     */
    E min(int i, int j);

    /**
     * 求区间[i,j]最大值
     *
     * @param i
     * @param j
     * @return
     */
    E max(int i, int j);
}
