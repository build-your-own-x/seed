package com.techzealot.collection.deque;

import com.techzealot.collection.MyAbstractCollection;
import com.techzealot.collection.MyCollection;

import java.io.Serializable;
import java.util.Iterator;

public class MyPriorityQueue<E> extends MyAbstractCollection<E>
        implements MyQueue<E>, Serializable, Cloneable {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(Object element) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(MyCollection<? extends E> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
