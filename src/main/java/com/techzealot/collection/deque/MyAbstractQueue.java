package com.techzealot.collection.deque;

import com.techzealot.collection.MyAbstractCollection;
import com.techzealot.collection.MyCollection;

import java.util.NoSuchElementException;

public abstract class MyAbstractQueue<E> extends MyAbstractCollection<E>
        implements MyQueue<E> {
    @Override
    public boolean add(E e) {
        if (offer(e)) {
            return true;
        } else {
            throw new IllegalStateException("queue full");
        }
    }

    @Override
    public E element() {
        E e = peek();
        if (e == null) {
            throw new NoSuchElementException();
        }
        return e;
    }

    @Override
    public boolean addAll(MyCollection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c == this) {
            throw new IllegalStateException();
        }
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        while (poll() != null) ;
    }

    @Override
    public E remove() {
        E e = poll();
        if (e == null) {
            throw new NoSuchElementException();
        }
        return e;
    }
}
