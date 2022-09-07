package com.techzealot.collection.tree.bst;

import lombok.NonNull;

import java.util.Comparator;

/**
 * support rank and select
 * max=rank(size)
 * min=rank(1)
 * predecessor(e)=select(rank(e)-1)
 * successor(e)=select(rank(e)+1)
 */
public class RankedBST<E> extends AbstractBST<E> {

    private Node root;

    public RankedBST() {
    }

    public RankedBST(Comparator<E> comparator) {
        super(comparator);
    }

    public static <T> RankedBST<T> of(@NonNull T... elements) {
        RankedBST<T> bst = new RankedBST<>();
        for (T element : elements) {
            bst.add(element);
        }
        return bst;
    }

    @Override
    protected Node root() {
        return root;
    }

    @Override
    public int size() {
        return root == null ? 0 : root.size;
    }

    @Override
    public boolean add(@NonNull E e) {
        return false;
    }

    private Node add(Node node, E e) {
        if (node == null) {
            //todo size
            return new Node(e);
        }
        if (compare(e, node.e) == 0) {
            return node;
        } else if (compare(e, node.e) < 0) {
            node.left = add(node.left, e);
            return node;
        } else {
            node.right = add(node.right, e);
            return node;
        }
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E removeMin() {
        return null;
    }

    @Override
    public E removeMax() {
        return null;
    }

    public int rank(E e) {
        return 0;
    }

    public E select(int k) {
        if (k < 1 || k > size()) return null;
        return null;
    }

    @Override
    public E minimum() {
        return select(1);
    }

    @Override
    public E maximum() {
        return select(size());
    }

    @Override
    public E predecessor(E e) {
        return select(rank(e) - 1);
    }

    @Override
    public E successor(E e) {
        return select(rank(e) + 1);
    }

    private class Node implements BST.Node<E> {
        int size;
        Node left;
        E e;
        Node right;

        public Node(E e) {
            this.left = null;
            this.e = e;
            this.right = null;
        }

        @Override
        public Node left() {
            return left;
        }

        @Override
        public E value() {
            return e;
        }

        @Override
        public Node right() {
            return right;
        }
    }
}
