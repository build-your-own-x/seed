package com.techzealot.collection.tree.bst.rbtree;

import com.techzealot.collection.tree.bst.AbstractBSTSet;
import com.techzealot.collection.tree.bst.BSTSet;

/**
 * 左偏红黑树:LLRB-23
 */
public class LeftRBTree<E> extends AbstractBSTSet<E> {
    @Override
    protected Node root() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void put(E e) {

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

    private class Node implements BSTSet.Node<E> {

        @Override
        public BSTSet.Node<E> left() {
            return null;
        }

        @Override
        public E value() {
            return null;
        }

        @Override
        public BSTSet.Node<E> right() {
            return null;
        }
    }
}
