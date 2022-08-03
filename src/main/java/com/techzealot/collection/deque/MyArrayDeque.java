package com.techzealot.collection.deque;

import com.techzealot.collection.MyAbstractCollection;
import com.techzealot.collection.MyCollection;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 双端队列，可作为Stack和Queue使用，比LinkedList更高效且意义明确
 * 不支持获取或设置非首尾元素
 */
public class MyArrayDeque<E> extends MyAbstractCollection<E>
        implements MyDeque<E>, Serializable, Cloneable {

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
