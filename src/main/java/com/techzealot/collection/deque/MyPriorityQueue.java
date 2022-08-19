package com.techzealot.collection.deque;

import com.techzealot.collection.MyCollection;
import com.techzealot.collection.list.MyArrayList;
import com.techzealot.collection.set.MySortedSet;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class MyPriorityQueue<E> extends MyAbstractQueue<E>
        implements Serializable {
    @Serial
    private static final long serialVersionUID = 5385316803628032808L;

    private final static int DEFAULT_INIT_CAPACITY = 11;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
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

    private Object[] ensureNonEmpty(Object[] arr) {
        return arr.length > 0 ? arr : new Object[1];
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
        //1.this.comparator != null条件的必要性:
        //如果有序集合通过支持null的comparator构造的,可能包含null,且有序集合后续不会排序,因此需要强制检查null
        //如果是无序集合,在后续heapify时会报错,此处假定大部分情况不含null,不做判断以提升性能
        //2.len == 1条件的必要性:
        //当有一个元素时不会触发排序,直接设置为根节点,此时不会触发NPE,因此需要主动判定以保障初始化后不会有空元素
        //3.其他情形:当两个条件都不满足时,说明传入的是无序集合且有不少于2个元素,若有null会在后续排序时报NPE,不预先判断以提升性能
        if (len == 1 || this.comparator != null) {
            for (int i = 0; i < len; i++) {
                if (a[i] == null) {
                    throw new NullPointerException();
                }
            }
        }
        this.queue = ensureNonEmpty(a);
        this.size = len;
    }

    private void initFromPriorityQueue(MyPriorityQueue<? extends E> pq) {
        if (pq.getClass() == MyPriorityQueue.class) {
            queue = ensureNonEmpty(pq.toArray());
            this.size = pq.size;
        } else {
            initFromCollection(pq);
        }
    }

    private void initFromCollection(MyCollection<? extends E> c) {
        initElementsFromCollection(c);
        heapify();
    }

    /**
     * 堆化原理:从最后一个非叶子节点(size >>> 1) - 1开始依次对所有非叶子节点向下筛选直到根节点
     */
    private void heapify() {
        //size==1时不做筛选操作
        for (int i = (size >>> 1) - 1; i >= 0; i--) {
            siftDown(i, (E) queue[i]);
        }
    }

    /**
     * 向下调整节点排序直到叶子结点(i>最后一个非叶子节点索引),叶子结点是连续排列且在数组后半段
     *
     * @param i
     * @param e
     */
    private void siftDown(int i, E e) {
        if (comparator != null) {
            siftDownUsingComparator(i, e);
        } else {
            siftDownComparable(i, e);
        }
    }

    private void siftDownUsingComparator(int k, E e) {
        while (k < size >>> 1) {
            int child = (size << 1) + 1;
            Object least = queue[child];
            int right = child + 1;
            if (right < size && comparator.compare((E) queue[right], (E) least) < 0) {
                least = queue[child = right];
            }
            if (comparator.compare(e, (E) least) < 0) {
                break;
            }
            queue[k] = least;
            k = child;
        }
        queue[k] = e;
    }

    private void siftDownComparable(int k, E e) {
        Comparable x = (Comparable) e;
        //k <= (size >> 1) - 1表示向下筛选直到最后一个节点,也可以优化为k< size>>>1
        while (k <= (size >> 1) - 1) {
            //一定有左孩子,不一定有右孩子
            //比较当前元素与最小的孩子
            // 此处的技巧是可以减少if/else判断,只需在右孩子为最小时重新赋值，简化代码
            //child表示最小孩子的索引，赋初值,假设左孩子为三个元素中最小的
            int child = (k << 1) + 1;
            //右孩子=左孩子+1
            int right = child + 1;
            //最小孩子引用
            Object least = queue[child];
            //r<size表示有右孩子
            // 当右孩子小于左孩子时,重新赋值最小的孩子
            if (child < size && ((Comparable) queue[right]).compareTo(least) < 0) {
                //重新绑定最小孩子及其索引
                least = queue[child = right];
            }
            //如果根节点(此时根节点不是queue[k],需要使用初始的e亦即x)小于最小节点不做调整跳出循环
            if (x.compareTo(least) < 0) {
                break;
            }
            //如果根节点大于最小的孩子节点,按理说需要交换两个节点,并更新下次迭代索引为最小孩子索引
            //此处有一个优化:将根节点替换为最小孩子节点,然后从最小孩子节点位置开始下一轮迭代,但比较时采用x而不是queue[k],可以优化无意义的赋值
            queue[k] = least;
            k = child;
        }
        //迭代完成后k指向的位置即为x节点最后到达的位置
        queue[k] = e;
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return queue.length;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(0) != -1;
    }

    private int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (o.equals(queue[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 返回数组是无序的(不保证顺序),但是与底层数组顺序相同,因此可以直接用来构造自身
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(queue, size);
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        modCount++;
        int i = size;
        if (i > queue.length) {
            grow(i + 1);
        }
        siftUp(i, e);
        size = i + 1;
        return true;
    }

    private void siftUp(int k, E e) {
        if (comparator != null) {
            siftUpUsingComparator(k, e);
        } else {
            siftUpComparable(k, e);
        }
    }

    private void siftUpComparable(int k, E e) {
        while (k > 0) {
            int p = (k - 1) >> 1;
            E parent = (E) queue[p];
            if (comparator.compare(e, parent) > 0) {
                break;

            }
            queue[k] = parent;
            k = p;
        }
        queue[k] = e;
    }

    private void siftUpUsingComparator(int k, E e) {

    }

    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        //double size if small,else grow by 50%
        int newCapacity = oldCapacity + (oldCapacity < 64 ? (oldCapacity + 2) : oldCapacity >> 1);
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        queue = Arrays.copyOf(queue, newCapacity);
    }

    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        int s = --size;
        modCount++;
        E result = (E) queue[0];
        E x = (E) queue[s];
        queue[s] = null;
        if (s != 0) {
            siftDown(0, x);
        }
        return result;
    }

    @Override
    public E peek() {
        return size == 0 ? null : (E) queue[0];
    }

    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i == -1) {
            return false;
        } else {
            removeAt(i);
            return true;
        }
    }

    private void removeAt(int i) {
        modCount++;
        int s = --size;
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
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        queue = new Object[size];
        for (int i = 0; i < size; i++) {
            queue[i] = s.readObject();
        }
        //规范未定义序列化顺序,为保障正确,此处需要再次堆化
        heapify();
    }

    @Serial
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        //write out all in proper order
        for (int i = 0; i < size; i++) {
            s.writeObject(queue[i]);
        }
    }

    /**
     * 实用方法:使用指定元素替换根节点,可通过poll+offer间接实现(siftDown+siftUp)但效率不高
     * 可直接替换根节点然后siftDown更高效
     */
    public E replace(E e) {
        if (size == 0) {
            offer(e);
            return null;
        }
        modCount++;
        E result = (E) queue[0];
        siftDown(0, e);
        return result;
    }
}
