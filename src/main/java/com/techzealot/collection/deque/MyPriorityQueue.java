package com.techzealot.collection.deque;

import com.techzealot.collection.MyCollection;
import com.techzealot.collection.list.MyArrayList;
import com.techzealot.collection.set.MySortedSet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class MyPriorityQueue<E> extends MyAbstractQueue<E>
        implements Serializable {
    @Serial
    private static final long serialVersionUID = 5385316803628032808L;

    private final static int DEFAULT_INIT_CAPACITY = 11;
    private final Comparator<? super E> comparator;
    private transient Object[] queue;
    private int size = 0;
    private transient int modCount = 0;

    public MyPriorityQueue() {
        this(DEFAULT_INIT_CAPACITY, null);
    }

    public MyPriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    public MyPriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_INIT_CAPACITY, comparator);
    }

    public MyPriorityQueue(int initialCapacity, Comparator<? super E> comparator) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }
        this.queue = new Object[initialCapacity];
        this.comparator = comparator;
    }

    public MyPriorityQueue(MyCollection<? extends E> c) {
        if (c instanceof MySortedSet<?> ss) {
            this.comparator = (Comparator<? super E>) ss.comparator();
            initElementsFromCollection(c);
        } else if (c instanceof MyPriorityQueue<?> pq) {
            this.comparator = (Comparator<? super E>) pq.comparator();
            initFromPriorityQueue((MyPriorityQueue<? extends E>) pq);
        } else {
            this.comparator = null;
            initFromCollection(c);
        }
    }

    public MyPriorityQueue(MySortedSet<? extends E> c) {
        this.comparator = (Comparator<? super E>) c.comparator();
        initElementsFromCollection(c);
    }

    public MyPriorityQueue(MyPriorityQueue<? extends E> c) {
        this.comparator = (Comparator<? super E>) c.comparator;
        initFromPriorityQueue(c);
    }

    /**
     * 仅按原集合toArray复制数据,不做顺序处理
     *
     * @param c
     */
    private void initElementsFromCollection(MyCollection<? extends E> c) {
        Object[] a = c.toArray();
        //避免有些接口toArray不符合接口定义，仍关联底层数据
        if (c.getClass() != MyArrayList.class) {
            a = Arrays.copyOf(a, a.length);
        }
        int len = a.length;
        //如果有序集合通过支持null的comparator构造的,可能包含null,且有序集合后续不会排序,因此需要强制检查null
        //如果是无序集合,在后续heapify时会报错,此处假定大部分情况不含null,不做判断以提升性能
        if (this.comparator != null) {
            for (int i = 0; i < len; i++) {
                if (a[i] == null) {
                    throw new NullPointerException();
                }
            }
        }
        this.queue = a;
        this.size = len;
    }

    private void initFromPriorityQueue(MyPriorityQueue<? extends E> pq) {
        if (pq.getClass() == MyPriorityQueue.class) {
            queue = pq.toArray();
            this.size = pq.size;
        } else {
            initFromCollection(pq);
        }
    }

    private void initFromCollection(MyCollection<? extends E> c) {
        initElementsFromCollection(c);
        heapify();
    }

    private void heapify() {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(queue, size);
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public void clear() {
        modCount++;
        for (int i = 0; i < size; i++) {
            queue[i] = null;
        }
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Serial
    private void readObject(ObjectInputStream s) {

    }

    @Serial
    private void writeObject(ObjectOutputStream s) {

    }

    /**
     * 实用方法:使用指定元素替换根节点,可通过poll+offer间接实现(siftDown+siftUp)但效率不高
     * 可直接替换根节点然后siftDown更高效
     */
    public void replace(E e) {

    }
}
