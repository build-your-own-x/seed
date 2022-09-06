package com.techzealot.collection.tree;

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
    public boolean add(E e) {
        return false;
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
        return null;
    }

    private class Node<E> extends BST.Node<E> {
        int size;

        public Node(E e) {
            super(e);
        }
    }
}
