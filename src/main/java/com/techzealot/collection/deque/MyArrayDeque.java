package com.techzealot.collection.deque;

import com.techzealot.collection.MyAbstractCollection;
import com.techzealot.collection.MyCollection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 双端队列，可作为Stack和Queue使用，比LinkedList更高效且符合接口定义
 * 不支持获取或设置非首尾元素
 * 不支持null值，返回null表示集合已无更多元素
 */
public class MyArrayDeque<E> extends MyAbstractCollection<E>
        implements MyDeque<E>, Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = -1042548399068950370L;
    /**
     * 必须为2^n,便于快速计算size
     */
    private static final int MIN_INITIAL_CAPACITY = 8;
    private transient int head;
    private transient int tail;
    private transient Object[] elements;

    public MyArrayDeque() {
        elements = new Object[16];
    }

    public MyArrayDeque(int numElements) {
        allocateElements(numElements);
    }

    public MyArrayDeque(MyCollection<? extends E> c) {
        allocateElements(c.size());
        addAll(c);
    }

    private void allocateElements(int numElements) {
        elements = new Object[calculateSize(numElements)];
    }

    /**
     * 求出大于等于给定整数的最小2^n
     * 巧妙利用当m=2^k时,x%m==x&(m-1)且左右移动时仍然符合该条件
     *
     * @param numElements
     * @return
     */
    private int calculateSize(int numElements) {
        int initialCapacity = MIN_INITIAL_CAPACITY;
        if (numElements >= initialCapacity) {
            initialCapacity = numElements - 1;
            //思路为将最高位后所有二进制位变为1，然后再+1即为所求
            initialCapacity |= (initialCapacity >>> 1);
            initialCapacity |= (initialCapacity >>> 2);
            initialCapacity |= (initialCapacity >>> 4);
            initialCapacity |= (initialCapacity >>> 8);
            initialCapacity |= (initialCapacity >>> 16);
            //overflow back off,此时必为-2^31
            if (initialCapacity < 0) {
                initialCapacity >>>= 1;//此时大小为2^30
            }
        }
        return initialCapacity;
    }

    /**
     * 要求底层数组大小必为2^n
     *
     * @return
     */
    @Override
    public int size() {
        return (tail - head) & (elements.length - 1);
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
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
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
        int h = head;
        int t = tail;
        if (h != t) {
            head = tail = 0;
            int i = h;
            int mask = elements.length - 1;
            do {
                elements[i] = null;
                i = (i + 1) & mask;
            } while (i != t);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public void addFirst(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        head = (head - 1) & (elements.length - 1);
        elements[head] = e;
        if (head == tail) {
            doubleCapacity();
        }
    }

    private void doubleCapacity() {
        //进入此方法时数组一定已满
        assert head == tail;
        int p = head;
        int n = elements.length;
        int r = n - p;//计算出要向右侧移动的元素个数
        int newCapacity = n << 1;
        if (newCapacity < 0) {
            throw new IllegalStateException("Deque too big");
        }
        Object[] a = new Object[newCapacity];
        //分别拷贝两部分数据至新数组对应位置
        System.arraycopy(elements, p, a, 0, r);
        System.arraycopy(elements, 0, a, r, p);
        elements = a;
        head = 0;
        tail = n;
    }

    //底层真正实现为:addFirst,addLast,pollFirst,pollLast

    @Override
    public void addLast(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        elements[tail] = e;
        tail = (tail + 1) & (elements.length - 1);
        if (head == tail) {
            doubleCapacity();
        }
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        E x = pollFirst();
        if (x == null) {
            throw new NoSuchElementException();
        }
        return x;
    }

    @Override
    public E removeLast() {
        E x = pollLast();
        if (x == null) {
            throw new NoSuchElementException();
        }
        return x;
    }

    @Override
    public E pollFirst() {
        int h = head;
        E result = (E) elements[h];
        if (result == null) {
            return null;
        }
        //非空时需要将当前元素置空并右移一位head指针
        elements[h] = null;
        head = (h + 1) & (elements.length - 1);
        return result;
    }

    @Override
    public E pollLast() {
        int t = (tail - 1) & (elements.length - 1);
        E result = (E) elements[t];
        if (result == null) {
            return null;
        }
        elements[t] = null;
        tail = t;
        return result;
    }

    @Override
    public E getFirst() {
        E result = (E) elements[head];
        if (result == null) {
            throw new NoSuchElementException();
        }
        return result;
    }

    @Override
    public E getLast() {
        E result = (E) elements[(tail - 1) & (elements.length - 1)];
        if (result == null) {
            throw new NoSuchElementException();
        }
        return result;
    }

    @Override
    public E peekFirst() {
        return (E) elements[head];
    }

    @Override
    public E peekLast() {
        return (E) elements[(tail - 1) & (elements.length - 1)];
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public MyArrayDeque<E> clone() {
        try {
            MyArrayDeque clone = (MyArrayDeque) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Serial
    private void readObject(ObjectInputStream s) {
    }

    @Serial
    private void writeObject(ObjectOutputStream s) {

    }

    //ArrayDeque少有需要equals和hashCode的场景,故采用默认实现
}
