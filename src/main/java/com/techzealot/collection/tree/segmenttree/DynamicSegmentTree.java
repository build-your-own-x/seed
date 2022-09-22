package com.techzealot.collection.tree.segmenttree;

/**
 * 动态线段树,采用树结构,节省空间
 */
public class DynamicSegmentTree<E> implements RangeOperation<E> {
    private Node root;

    @Override
    public void update(int index, E e) {

    }

    @Override
    public E query(int i, int j) {
        return null;
    }

    private class Node {
        E e;
        int l;
        int r;
        Node left;
        Node right;
    }
}
