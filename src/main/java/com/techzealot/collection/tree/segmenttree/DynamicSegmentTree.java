package com.techzealot.collection.tree.segmenttree;

import java.text.MessageFormat;

/**
 * 动态线段树,采用树结构,节省空间
 */
public class DynamicSegmentTree<E> implements RangeOperation<E> {
    private final Merger<E> merger;
    private final int length;
    private Node root;

    public DynamicSegmentTree(E[] range, Merger<E> merger) {
        int len = range.length;
        if (len == 0) {
            throw new IllegalArgumentException("empty range");
        }
        this.length = len;
        this.merger = merger;
        root = buildSegmentTree(root, 0, len - 1, range);
    }

    private Node buildSegmentTree(Node node, int rangeLeft, int rangeRight, E[] range) {
        if (node == null) {
            node = new Node();
        }
        if (rangeRight == rangeLeft) {
            node.e = range[rangeLeft];
            return node;
        }
        int mid = rangeLeft + (rangeRight - rangeLeft) / 2;
        node.left = buildSegmentTree(node.left, rangeLeft, mid, range);
        node.right = buildSegmentTree(node.right, mid + 1, rangeRight, range);
        node.e = merger.merge(node.left.e, node.right.e);
        return node;
    }

    @Override
    public void update(int index, E e) {
        if (index < 0 || index >= length) {
            throw new IllegalArgumentException();
        }
        update(root, 0, length - 1, index, e);
    }

    private void update(Node node, int rangeLeft, int rangeRight, int index, E e) {
        if (rangeLeft == index && rangeRight == index) {
            node.e = e;
            return;
        }
        int mid = rangeLeft + (rangeRight - rangeLeft) / 2;
        if (index <= mid) {
            update(node.left, rangeLeft, mid, index, e);
        } else {
            update(node.right, mid + 1, rangeRight, index, e);
        }
        node.e = merger.merge(node.left.e, node.right.e);
    }

    @Override
    public E query(int i, int j) {
        if (i < 0 || i >= length || j < 0 || j >= length || i > j) {
            throw new IllegalArgumentException(MessageFormat.format("illegal range[{0},{1}]", i, j));
        }
        return query(root, 0, length - 1, i, j);
    }

    private E query(Node node, int rangeLeft, int rangeRight, int queryLeft, int queryRight) {
        if (rangeLeft == queryLeft && rangeRight == queryRight) {
            return node.e;
        }
        int mid = rangeLeft + (rangeRight - rangeLeft) / 2;
        if (queryRight <= mid) {
            return query(node.left, rangeLeft, mid, queryLeft, queryRight);
        }
        if (queryLeft > mid) {
            return query(node.right, mid + 1, rangeRight, queryLeft, queryRight);
        }
        E left = query(node.left, rangeLeft, mid, queryLeft, mid);
        E right = query(node.right, mid + 1, rangeRight, mid + 1, queryRight);
        return merger.merge(left, right);
    }

    private class Node {
        E e;
        Node left;
        Node right;
    }
}
