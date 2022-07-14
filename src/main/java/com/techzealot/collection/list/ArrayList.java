package com.techzealot.collection.list;

import java.io.Serializable;
import java.util.Collection;
import java.util.RandomAccess;

/**
 * 实现 增删改查 序列化优化 泛型支持 随机访问
 *
 * @param <E>
 */
public class ArrayList<E> implements RandomAccess, Serializable {

    private transient E[] elementData;

    private int size;

    public E add(E e) {
        return null;
    }

    public void addAll(Collection<E> c) {

    }

    public E remove(int index) {
        return null;
    }

    public void removeAll(Collection<E> c) {

    }

    public void set(int index, E element) {

    }

    public E get(int index) {
        return null;
    }

    private void grow() {

    }


}
