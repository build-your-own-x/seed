package com.techzealot.collection.list;

import com.techzealot.collection.MyCollection;
import com.techzealot.collection.MyList;
import lombok.NonNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;

/**
 * 实现 增删改查 序列化优化 泛型支持 随机访问
 * <p>
 * 支持存放null
 *
 * @param <E>
 */
public class MyArrayList<E> implements MyList<E>,
        RandomAccess, Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 4751541124667947110L;

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    /**
     * 标记主动设置长度为0的空数组,与初始空数组区分开
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
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

    /**
     * 返回可变List
     *
     * @param elements
     * @param <E>
     * @return
     */
    public static <E> MyArrayList<E> of(@NonNull E... elements) {
        MyArrayList<E> list = new MyArrayList<>(elements.length);
        for (E element : elements) {
            list.add(element);
        }
        return list;
    }

    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        //采用无参构造器初始化时保证容量不小于默认容量
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public int size() {
        return size;
    }

    public int getCapacity() {
        return elementData.length;
    }

    public boolean add(E element) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = element;
        return true;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        if (minCapacity - elementData.length > 0) {
            grow(minCapacity);
        }
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //确保有足够空间
        ensureCapacityInternal(size + 1);
        //index开始后续所有元素向后移动一位留出空位
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * @param c
     * @return true if the list is changed after called
     */
    public boolean addAll(MyCollection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    public E remove(int index) {
        rangeCheck(index);
        modCount++;
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        //let gc work
        elementData[--size] = null;
        return oldValue;
    }

    /**
     * remove the first one
     *
     * @param element
     * @return
     */
    public boolean remove(E element) {
        if (element == null) {
            for (int index = 0; index < size; index++) {
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (element.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * skip bounds check and return value
     *
     * @param index
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
    }

    public boolean removeAll(@NonNull MyCollection<E> c) {
        return batchRemove(c, false);
    }

    public boolean retainAll(@NonNull MyCollection<E> c) {
        return batchRemove(c, true);
    }

    private boolean batchRemove(MyCollection<?> c, boolean complement) {
        Object[] elements = this.elementData;
        //读写双指针
        int read = 0;
        int write = 0;
        boolean modified = false;
        try {
            for (; read < size; read++) {
                //some collection impl may throw when call contains
                if (c.contains(elements[read]) == complement) {
                    elements[write++] = elements[read];
                }
            }
        } finally {
            //if MyCollection.contains throws,preserve behavior
            //此时数据可能已经删除了一部分
            if (read != size) {
                //copy unhandled elements from exception point
                System.arraycopy(elements, read, elements, write, size - read);
                write += size - read;
            }
            if (write != size) {
                //clear to let gc do its work
                for (int i = write; i < size; i++) {
                    elements[i] = null;
                }
                modCount += size - write;
                size = write;
                modified = true;
            }
        }
        return modified;
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
     * 此处不使用泛型，使用泛型会导致适应性变差
     *
     * @param element
     * @return
     */
    public boolean contains(Object element) {
        return indexOf(element) >= 0;
    }

    public int indexOf(Object element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 从数组中获取元素时优先使用,负数数组本身会抛出异常
     *
     * @param index
     */
    private void rangeCheck(int index) {
        //获取时index!=size
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundMsg(index));
        }
    }

    /**
     * 添加元素时检查索引,需要检查负数和越界
     *
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        //index==size合法
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

    @Serial
    private void writeObject(ObjectOutputStream oos) {

    }

    @Serial
    private void readObject(ObjectInputStream ois) {

    }

    /**
     * todo
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyArrayList<?> that = (MyArrayList<?>) o;
        return Arrays.equals(elementData, that.elementData);
    }

    /**
     * todo
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(elementData);
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
