package com.techzealot.collection.list;

import com.techzealot.collection.MyCollection;
import lombok.NonNull;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * IntArrayList实现:内部Object[]换为[],其他思路类似
 * 实现 增删改查 序列化优化 泛型支持 随机访问
 * <p>
 * 支持存放null
 *
 * @param <E>
 */
public class MyArrayList<E> extends MyAbstractList<E>
        implements MyList<E>, RandomAccess, Serializable, Cloneable {

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

    @Override
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

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //确保有足够空间
        ensureCapacityInternal(size + 1);
        //index开始后续所有元素向后移动一位留出空位
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public boolean addAll(int index, @NonNull MyCollection<? extends E> c) {
        rangeCheckForAdd(index);
        if (c.size() == 0) {
            modCount++;
            return false;
        }
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
        }
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return true;
    }

    /**
     * @param c
     * @return true if the list is changed after called
     */
    @Override
    public boolean addAll(@NonNull MyCollection<? extends E> c) {
        if (c.size() == 0) {
            modCount++;
            return false;
        }
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return true;
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

    @Override
    public E removeAt(int index) {
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
     * remove the first equals one
     *
     * @param element
     * @return
     */
    @Override
    public boolean remove(Object element) {
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

    @Override
    public boolean removeAll(@NonNull MyCollection<?> c) {
        return batchRemove(c, false);
    }

    @Override
    public boolean retainAll(@NonNull MyCollection<?> c) {
        return batchRemove(c, true);
    }

    @Override
    public void clear() {
        modCount++;
        //clear to let gc work
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
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
    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    @Override
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
    @Override
    public boolean contains(Object element) {
        return indexOf(element) >= 0;
    }

    @Override
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

    /**
     * shadow copy
     *
     * @return
     */
    @Override
    public Object clone() {
        try {
            //默认的clone实现原理 bitwise copy
            MyArrayList<?> v = (MyArrayList<?>) super.clone();
            //切断新旧List联系
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream s) throws IOException {
        int expectedModCount = modCount;
        s.defaultWriteObject();
        //compatibility with clone
        s.writeInt(size);
        //优化:仅序列化有效元素(<size)
        for (int i = 0; i < size; i++) {
            s.writeObject(elementData[i]);
        }
        if (expectedModCount != modCount) {
            throw new ConcurrentModificationException();
        }
    }

    @Serial
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        //ignored
        s.readInt();
        if (size > 0) {
            //allocate size array
            Object[] elements = new Object[size];
            for (int i = 0; i < size; i++) {
                elements[i] = s.readObject();
            }
            elementData = elements;
        } else if (size == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new InvalidObjectException("Invalid size: " + size);
        }
    }

    /**
     * List使用迭代器进行遍历时增加并发修改检测
     */
    private class Itr implements Iterator<E> {

        int cursor;//next index to return
        int lastRet = -1;//last returned index,-1 if no such
        int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }
            //局部变量更高效
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        /**
         * 必须在调用一次next之后才能调用
         *
         * @return
         */
        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            try {
                checkForComodification();
                MyArrayList.this.removeAt(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * 增加边界检查和并发修改检测
         */
        @Override
        public void forEachRemaining(@NonNull Consumer<? super E> action) {
            int size = MyArrayList.this.size;
            //使用局部变量进行迭代可以避免多次访问堆中变量，栈中更新代价更低
            int i = cursor;
            if (i >= size) {
                return;
            }
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            Object[] elementData = MyArrayList.this.elementData;
            while (i != size && modCount == expectedModCount) {
                action.accept((E) elementData[i++]);
            }
            //由于剩余元素的迭代是不可中断的，可以一次性更新cursor,lastRet,避免在上面的while种多次无效更新
            //指向未发生修改时最后一个元素的下一个索引
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
