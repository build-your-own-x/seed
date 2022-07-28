package com.techzealot.collection.list;

import com.techzealot.collection.MyCollection;
import com.techzealot.collection.MyDeque;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 双端队列，可作为Stack和Queue使用，比LinkedList更高效且意义明确
 * 不支持获取或设置非首尾元素
 */
public class MyArrayDeque<E> implements Serializable, MyDeque<E> {
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
    public boolean removeAll(MyCollection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(MyCollection<?> c) {
        return false;
    }

    @Override
    public void clear() {

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
