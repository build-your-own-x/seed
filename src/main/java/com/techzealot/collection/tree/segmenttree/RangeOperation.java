package com.techzealot.collection.tree.segmenttree;

/**
 * 区间统计问题
 *
 * @param <E>
 */
public interface RangeOperation<E> {

    /**
     * 更新指定索引上的元素
     *
     * @param index
     * @param e
     */
    void update(int index, E e);

    /**
     * 查询区间[i,j]中元素的指定统计信息
     *
     * @param i
     * @param j
     * @return
     */
    E query(int i, int j);

    /**
     * 指定统计操作
     *
     * @param <E>
     */
    interface Merger<E> {
        E merge(E a, E b);
    }
}
