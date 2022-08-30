package com.techzealot.collection.tree;

/**
 * 二分搜索树
 */
public class BST<E extends Comparable<E>> {
    private Node root;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(E e) {
        return true;
    }

    public boolean remove(Object o) {
        return true;
    }

    public boolean contains(Object o) {
        return true;
    }

    private class Node {
        E e;
        Node left;
        Node right;

        public Node(E e) {
            this.left = null;
            this.e = e;
            this.right = null;
        }
    }
}
