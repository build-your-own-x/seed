package com.techzealot.collection.list;

/**
 * 可实现Queue和stack功能
 *
 * @param <E>
 */
public class LinkedList<E> {

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
