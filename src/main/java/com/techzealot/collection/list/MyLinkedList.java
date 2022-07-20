package com.techzealot.collection.list;

import java.io.Serializable;

/**
 * 可实现Queue和stack功能
 *
 * @param <E>
 */
public class MyLinkedList<E> implements MyQueue<E>, MyStack<E>, Serializable, Cloneable {

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
