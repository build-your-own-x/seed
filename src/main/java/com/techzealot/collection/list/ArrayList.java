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

    public static final int DEFAULT_CAPACITY = 10;
    public static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    /**
     * 标记主动设置长度为0的空数组,与初始空数组区分开
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private transient Object[] elementData;
    private int size;

    /**
     * 初始时所有实例共享同一个空数组示例，节省内存
     * 会在add时初始化为默认大小容量
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("illegal initialCapacity: " + initialCapacity);
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getCapacity() {
        return elementData.length;
    }

    public boolean add(E element) {
        add(size, element);
        return true;
    }

    public void add(int index, E element) {
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
