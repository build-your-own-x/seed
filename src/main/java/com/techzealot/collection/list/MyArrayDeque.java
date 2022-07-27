package com.techzealot.collection.list;

import com.techzealot.collection.MyDeque;
import com.techzealot.collection.MyQueue;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 双端队列，可作为Stack和Queue使用，比LinkedList更高效
 */
public class MyArrayDeque<E> implements Serializable, MyQueue<E>, MyDeque<E> {
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
}
