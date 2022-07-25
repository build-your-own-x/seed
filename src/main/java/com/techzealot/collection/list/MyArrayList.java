package com.techzealot.collection.list;

import lombok.NonNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
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

    @Serial
    private static final long serialVersionUID = 4751541124667947110L;

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
    private int size = 0;

    /**
     * 修改次数
     * 避免迭代的同时被修改
     * fail-fast
     */
    private transient int modCount = 0;

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

    public boolean add(int index, E element) {
        return true;
    }

    public boolean addAll(Collection<E> c) {
        return true;
    }

    private void grow() {

    }

    public E remove(int index) {
        return null;
    }

    public boolean remove(E element) {
        return true;
    }

    public boolean removeAll(Collection<E> c) {
        return true;
    }

    /**
     * 设置新值返回旧值
     *
     * @param index
     * @param element
     * @return
     */
    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    /**
     * 从数组中获取元素时优先使用,负数数组本身会抛出异常
     *
     * @param index
     */
    private void rangeCheck(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException(outOfBoundMsg(index));
        }
    }

    /**
     * 添加元素时检查索引,需要检查负数和越界
     *
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundMsg(index));
        }
    }

    private String outOfBoundMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elementData[index];
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public MyArrayList<E> clone() {
        try {
            MyArrayList clone = (MyArrayList) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream oos) {

    }

    private void readObject(ObjectInputStream ois) {

    }

    private class Itr implements Iterator<E> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            return null;
        }
    }
}
