package com.techzealot.collection.deque;

import java.util.Iterator;

/**
 * 双端队列，可同时实现队列和栈的功能
 *
 * @param <E>
 */
public interface MyDeque<E> extends MyQueue<E> {
    void addFirst(E e);

    void addLast(E e);

    boolean offerFirst(E e);

    boolean offerLast(E e);

    E removeFirst();

    E removeLast();

    E pollFirst();

    E pollLast();

    E getFirst();

    E getLast();

    E peekFirst();

    E peekLast();

    boolean removeFirstOccurrence(Object o);

    boolean removeLastOccurrence(Object o);

    void push(E e);

    E pop();

    Iterator<E> descendingIterator();
}
