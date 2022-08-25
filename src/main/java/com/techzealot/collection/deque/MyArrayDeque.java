package com.techzealot.collection.deque;

import com.techzealot.collection.MyAbstractCollection;
import com.techzealot.collection.MyCollection;
import lombok.NonNull;

import java.io.*;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * 双端队列，可作为Stack和Queue使用，比LinkedList更高效且符合接口定义
 * 不支持获取或设置非首尾元素
 * 不支持null值，返回null表示集合已无更多元素
 * ArrayDeque少有需要equals和hashCode的场景,故采用默认实现
 */
public class MyArrayDeque<E> extends MyAbstractCollection<E>
        implements MyDeque<E>, Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = -1042548399068950370L;
    /**
     * 必须为2^n,便于快速计算head,tail移动后索引
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

    public MyArrayDeque(@NonNull MyCollection<? extends E> c) {
        allocateElements(c.size());
        addAll(c);
    }

    public static <E> MyArrayDeque<E> of(@NonNull E... elements) {
        MyArrayDeque<E> deque = new MyArrayDeque<>(elements.length);
        for (E e : elements) {
            deque.add(e);
        }
        return deque;
    }

    /**
     * 求出大于给定整数的最小2^n,为保证add前有足够空间不能等于
     * 巧妙利用当m=2^k时,(x++)%m==(x++)&(m-1)且左右循环移动时仍然符合该条件
     *
     * @param numElements
     * @return
     */
    private static int calculateSize(int numElements) {
        int initialCapacity = MIN_INITIAL_CAPACITY;
        if (numElements >= initialCapacity) {
            initialCapacity = numElements;
            //思路为将二进制第一个1后所有二进制位变为1，然后再+1
            initialCapacity |= (initialCapacity >>> 1);
            initialCapacity |= (initialCapacity >>> 2);
            initialCapacity |= (initialCapacity >>> 4);
            initialCapacity |= (initialCapacity >>> 8);
            initialCapacity |= (initialCapacity >>> 16);
            initialCapacity++;
            //overflow back off,此时必为-2^31
            if (initialCapacity < 0) {
                initialCapacity >>>= 1;//此时大小为2^30
            }
        }
        return initialCapacity;
    }

    private void allocateElements(int numElements) {
        elements = new Object[calculateSize(numElements)];
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

    public int capacity() {
        return elements.length;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        int i = head;
        int mask = elements.length - 1;
        //此处也可以使用elements[i]!=null作为循环条件
        while (i != tail) {
            if (o.equals(elements[i])) {
                return true;
            }
            i = (i + 1) & mask;
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        return copyElementsTo(new Object[size()]);
    }

    private <T> T[] copyElementsTo(T[] a) {
        if (head < tail) {
            System.arraycopy(elements, head, a, 0, size());
        } else if (head > tail) {
            int headPortionLen = elements.length - head;
            System.arraycopy(elements, head, a, 0, headPortionLen);
            System.arraycopy(elements, 0, a, headPortionLen, tail);
        }
        //head==tail 返回传入空数组
        return a;
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
        return removeFirstOccurrence(o);
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
        return new DeqIterator();
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
    //从设计上可以保证添加之前集合一定有足够剩余空间
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
        if (o == null) {
            return false;
        }
        int mask = elements.length - 1;
        for (int i = head; i != tail; i = (i + 1) & mask) {
            if (o.equals(elements[i])) {
                delete(i);
                return true;
            }
        }
        return false;
    }

    /**
     * @param i
     * @return true if elements move backwards
     */
    private boolean delete(int i) {
        checkInvariants();
        Object[] elements = this.elements;
        int h = head;
        int t = tail;
        int mask = elements.length - 1;
        //索引i之前元素个数
        int front = (i - h) & mask;
        //索引i之后元素个数
        int back = (t - i) & mask;
        int size = (t - h) & mask;
        if (front >= size) {
            throw new ConcurrentModificationException();
        }
        //优化:比较以索引作为分割的前后两部分数量,尽量移动最少量数据
        //head<tail:元素一定在head,tail之间;head>tail:元素一定分为两部分首尾相连;由deque的添加操作决定前面的条件成立,head相对tail的位置一定在元素增长方向的左边
        if (front < back) {
            if (h <= i) {
                System.arraycopy(elements, h, elements, h + 1, front);
            } else {
                System.arraycopy(elements, 0, elements, 1, i);
                elements[0] = elements[mask];
                System.arraycopy(elements, h, elements, h + 1, mask - h);
            }
            elements[h] = null;
            head = (h + 1) & mask;
            return false;
        } else {
            //copy null tail as well
            if (i < t) {
                System.arraycopy(elements, i + 1, elements, i, back);
                tail = t - 1;
            } else {
                System.arraycopy(elements, i + 1, elements, i, mask - i);
                elements[mask] = elements[0];
                System.arraycopy(elements, 1, elements, 0, t);
                tail = (t - 1) & mask;
            }
            //elements[t] = null; //unnecessary, tail is always null
            return true;
        }
    }

    /**
     * 若有元素:
     * head之后必有元素且转动方向保持,之前必无元素
     * tail之前必有元素且转动方向保持,之后必无元素
     */
    private void checkInvariants() {
        //队尾必为空
        assert elements[tail] == null;
        //队列为空时队头也为空 队列不为空时队头不为空且队尾前一个元素必不为空
        assert head == tail ? elements[head] == null :
                (elements[head] != null && elements[(tail - 1) & elements.length - 1] != null);
        //队头的前一个必为空
        assert elements[(head - 1) & elements.length - 1] == null;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            return false;
        }
        //特意实现一种与removeFirstOccurrence不同的方式,开阔思路
        int mask = elements.length - 1;
        int i = (tail - 1) & mask;
        Object x;
        while ((x = elements[i]) != null) {
            if (o.equals(x)) {
                delete(i);
                return true;
            }
            i = (i - 1) & mask;
        }
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
        return new DescendingIterator();
    }

    /**
     * 无法像数组一样优化,所有空间都是必要的
     *
     * @return
     */
    @Override
    public MyArrayDeque<E> clone() {
        try {
            MyArrayDeque result = (MyArrayDeque) super.clone();
            result.elements = Arrays.copyOf(elements, elements.length);
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Serial
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int size = s.readInt();
        allocateElements(size);
        head = 0;
        tail = size;
        for (int i = 0; i < size; i++) {
            elements[i] = s.readObject();
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(size());
        int mask = elements.length - 1;
        for (int i = head; i != tail; i = (i + 1) & mask) {
            s.writeObject(elements[i]);
        }
    }

    private class DeqIterator<E> implements Iterator<E> {
        private int cursor = head;
        private int fence = tail;
        private int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != fence;
        }

        @Override
        public E next() {
            if (cursor == fence) {
                throw new NoSuchElementException();
            }
            E result = (E) elements[cursor];
            if (result == null || tail != fence) {
                throw new ConcurrentModificationException();
            }
            lastRet = cursor;
            cursor = (cursor + 1) & (elements.length - 1);
            return result;
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            //后半部分移动需要调整,cursor-1
            if (delete(lastRet)) {
                cursor = (cursor - 1) & (elements.length - 1);
                fence = tail;
            }
            lastRet = -1;
        }

        @Override
        public void forEachRemaining(@NonNull Consumer<? super E> action) {
            Object[] a = elements;
            int mask = a.length - 1;
            int f = fence;
            int i = cursor;
            //一次性调整遍历指针到末尾,提升性能
            cursor = f;
            while (i != f) {
                E e = (E) elements[i];
                if (e == null) {
                    throw new ConcurrentModificationException();
                }
                action.accept(e);
                i = (i + 1) & mask;
            }
        }
    }

    private class DescendingIterator<E> implements Iterator<E> {
        /**
         * this class is nearly a mirror-image of DeqIterator
         */
        private int cursor = tail;
        private int fence = head;
        private int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != fence;
        }

        @Override
        public E next() {
            if (cursor == fence) {
                throw new NoSuchElementException();
            }
            cursor = (cursor - 1) & (elements.length - 1);
            E result = (E) elements[cursor];
            if (result == null || fence != head) {
                throw new ConcurrentModificationException();
            }
            lastRet = cursor;
            return result;
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            //前半部分未遍历数据被移动,需要回退,cursor+1
            if (!delete(lastRet)) {
                cursor = (cursor + 1) & (elements.length - 1);
                fence = head;
            }
            lastRet = -1;
        }
    }

}
