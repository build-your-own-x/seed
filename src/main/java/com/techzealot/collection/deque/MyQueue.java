package com.techzealot.collection.deque;

import com.techzealot.collection.MyCollection;

/**
 * @param <E>
 */
public interface MyQueue<E> extends MyCollection<E> {
    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();
}
