package com.techzealot.collection.tree.segmenttree;

import lombok.NonNull;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 适用于求区间统计信息，元素总量不变，元素可以更新
 * 线段树为平衡二叉树，可以看做满二叉树
 * n个元素构建的基于数组的线段树需要4n的空间:
 * 1.满二叉树最后一行的元素个数约等于其余元素个数
 * 2.当n=2^k时,所有元素落在最后一行,线段树需要2n空间,当n=2^k+m时,n个元素落在最后两行,最后一行元素个数最多近似于2n,倒数第二行近似于n,故总树近似小于4n
 * 3.使用时将线段树看做满二叉树，便于使用数组表达，简化计算和存储
 * 可以考虑使用树结构优化存储空间
 */
public class ArraySegmentTree<E> implements RangeOperation<E> {
    private E[] data;

    private E[] tree;

    private Merger<E> merger;

    public ArraySegmentTree(@NonNull E[] data, Merger<E> merger) {
        int length = data.length;
        if (length == 0) {
            throw new IllegalArgumentException("empty data");
        }
        this.data = Arrays.copyOf(data, length);
        tree = (E[]) new Object[4 * length];
        this.merger = merger;
        buildSegmentTree(0, 0, length - 1);
    }

    private void buildSegmentTree(int treeIndex, int rangeLeft, int rangeRight) {
        if (rangeLeft == rangeRight) {
            tree[treeIndex] = data[rangeLeft];
            return;
        }
        int mid = rangeLeft + (rangeRight - rangeLeft) / 2;
        int leftIndex = leftIndex(treeIndex);
        int rightIndex = rightIndex(treeIndex);
        buildSegmentTree(leftIndex, rangeLeft, mid);
        buildSegmentTree(rightIndex, mid + 1, rangeRight);
        tree[treeIndex] = merger.merge(tree[leftIndex], tree[rightIndex]);
    }

    private int leftIndex(int treeIndex) {
        return treeIndex << 1 + 1;
    }

    private int rightIndex(int treeIndex) {
        return treeIndex << 1 + 2;
    }

    /**
     * O(logn)
     *
     * @param index
     * @param e
     */
    @Override
    public void update(int index, E e) {
        int length = data.length;
        if (index < 0 || index >= length) {
            throw new IllegalArgumentException();
        }
        data[index] = e;
        update(0, 0, length - 1, index, e);
    }

    /**
     * 递归更新线段树各受影响节点
     *
     * @param treeIndex
     * @param rangeLeft
     * @param rangeRight
     * @param index
     * @param e
     */
    private void update(int treeIndex, int rangeLeft, int rangeRight, int index, E e) {
        if (rangeLeft == index && rangeRight == index) {
            tree[index] = e;
            return;
        }
        int mid = rangeLeft + (rangeRight - rangeLeft) / 2;
        int leftIndex = leftIndex(treeIndex);
        int rightIndex = rightIndex(treeIndex);
        if (index <= mid) {
            update(leftIndex, rangeLeft, mid, index, e);
        } else {
            update(rightIndex, mid + 1, rangeRight, index, e);
        }
        tree[treeIndex] = merger.merge(tree[leftIndex], tree[rightIndex]);
    }

    /**
     * O(logn)
     *
     * @param i
     * @param j
     * @return
     */
    @Override
    public E query(int i, int j) {
        int length = data.length;
        if (i < 0 || i >= length || j < 0 || j >= length || i > j) {
            throw new IllegalArgumentException(MessageFormat.format("illegal range[{0},{1}]", i, j));
        }
        return query(0, 0, length - 1, i, j);
    }

    private E query(int treeIndex, int rangeLeft, int rangeRight, int queryLeft, int queryRight) {
        if (rangeLeft == queryLeft && rangeRight == queryRight) {
            return tree[treeIndex];
        }
        int mid = rangeLeft + (rangeRight - rangeLeft) / 2;
        int leftIndex = leftIndex(treeIndex);
        if (queryRight <= mid) {
            return query(leftIndex, rangeLeft, mid, queryLeft, queryRight);
        }
        int rightIndex = rightIndex(treeIndex);
        if (queryLeft >= mid + 1) {
            return query(rightIndex, mid + 1, rangeRight, queryLeft, queryRight);
        }
        E left = query(leftIndex, rangeLeft, mid, queryLeft, mid);
        E right = query(rightIndex, mid + 1, rangeRight, mid + 1, queryRight);
        return merger.merge(left, right);
    }
}
