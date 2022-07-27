package com.techzealot.collection.list;

import com.techzealot.collection.MyDeque;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 可实现Queue和stack功能
 *
 * @param <E>
 */
public class MyLinkedList<E> implements MyDeque<E>, Serializable, Cloneable {

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(Object element) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    static class Node<E> {
        Node<E> prev;
        E item;
        Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
