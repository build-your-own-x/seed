package com.techzealot.collection.list;

import lombok.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.RandomAccess;

/**
 * 实现 增删改查 序列化优化 泛型支持 随机访问
 *
 * @param <E>
 */
public class MyArrayList<E> implements MyCollection<E>,
        RandomAccess, Serializable, Cloneable {

    private static final int DEFAULT_CAPACITY = 10;
    
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    /**
     * 标记主动设置长度为0的空数组,与初始空数组区分开
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};
    /**
     * 此处需要使用Object数组，因为无法使用泛型的形式(new E[size])创建数组
     */
    private transient Object[] elementData;
    /**
     * 数组实际元素数量
     */
    private int size;

    /**
     * 初始时所有实例共享同一个空数组示例，节省内存
     * 会在add时初始化为默认大小容量
     */
    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("illegal initialCapacity: " + initialCapacity);
        }
    }

    public MyArrayList(@NonNull MyCollection<? extends E> c) {
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            //可以保证ArrayList的toArray方法会返回新的数组
            if (c.getClass() == MyArrayList.class) {
                elementData = a;
            } else {
                //避免toArray方法未遵循规范返回新的数组
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            elementData = EMPTY_ELEMENTDATA;
        }
    }

    @Override
    public int size() {
        return size;
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

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
