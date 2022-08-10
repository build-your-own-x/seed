package com.techzealot.collection.list;

import com.techzealot.collection.MyCollection;
import lombok.NonNull;

import java.io.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * 未实现Queue和Deque功能,主要是LinkedList支持元素为null,Deque中null表示集合已经为空，存在歧义
 * elements can be null
 *
 * @param <E>
 */
public class MyLinkedList<E> extends MyAbstractList<E>
        implements MyList<E>, Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1538564936182943121L;
    /**
     * 链表的头尾指针要么全为空,要么全不为空
     * (first==null&&last==null)||(first!=null&&last!=null)
     * first不变式:(first==null&&last==null)||(first.prev==null)
     * last不变式:(first==null&&last==null)||(last.next==null)
     */
    private transient Node<E> first;
    private transient Node<E> last;
    private transient int size = 0;

    public MyLinkedList() {
    }

    public MyLinkedList(@NonNull MyCollection<? extends E> c) {
        addAll(c);
    }

    public static <E> MyLinkedList<E> of(E... elements) {
        MyLinkedList<E> linkedList = new MyLinkedList<>();
        for (E element : elements) {
            linkedList.add(element);
        }
        return linkedList;
    }

    private void linkFirst(E e) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
        modCount++;
    }

    private void linkLast(E e) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(last, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    private void linkBefore(E e, @NonNull Node<E> succ) {
        Node<E> pred = succ.prev;
        Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
        modCount++;
    }

    private E unlinkFirst(@NonNull Node<E> f) {
        E item = f.item;
        Node<E> next = f.next;
        //help gc
        f.item = null;
        f.next = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = null;
        }
        size--;
        modCount++;
        return item;
    }

    private E unlinkLast(@NonNull Node<E> l) {
        E item = l.item;
        Node<E> prev = l.prev;
        l.prev = null;
        l.item = null;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = null;
        }
        size--;
        modCount++;
        return item;
    }

    private E unlink(@NonNull Node<E> x) {
        Node<E> prev = x.prev;
        E item = x.item;
        Node<E> next = x.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        modCount++;
        return item;
    }

    private Node<E> node(int index) {
        if (index < size >> 1) {
            Node<E> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    /**
     * check if a valid element index,for read
     *
     * @param index
     */
    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * check if a valid position for add or iterate
     *
     * @param index
     */
    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }


    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }
        return result;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean addAll(@NonNull MyCollection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public void clear() {
        //help gc
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.prev = null;
            x.item = null;
            x.next = null;
            x = next;
        }
        first = last = null;
        size = 0;
        modCount++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr(0);
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldValue = x.item;
        x.item = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(element);
        } else {
            linkBefore(element, node(index));
        }
    }

    @Override
    public boolean addAll(int index, @NonNull MyCollection<? extends E> c) {
        checkPositionIndex(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            modCount++;
            return false;
        }
        Node<E> pred, succ;
        if (index == size) {
            pred = last;
            succ = null;
        } else {
            succ = node(index);
            pred = succ.prev;
        }
        for (Object o : a) {
            @SuppressWarnings("unchecked")
            E e = (E) o;
            Node<E> newNode = new Node<>(pred, e, succ);
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }
        if (succ == null) {
            last = pred;
        } else {
            succ.prev = pred;
            pred.next = succ;
        }
        size += numNew;
        modCount++;
        return true;
    }

    @Override
    public E removeAt(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public int indexOf(Object o) {
        Node<E> x = first;
        if (o == null) {
            for (int i = 0; i < size; i++, x = x.next) {
                if (x.item == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++, x = x.next) {
                if (o.equals(x.item)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * shadow copy
     *
     * @return
     */
    @Override
    public Object clone() {
        try {
            MyLinkedList<E> clone = (MyLinkedList<E>) super.clone();
            //put clone into "virgin" state
            clone.first = clone.last = null;
            clone.size = 0;
            clone.modCount = 0;
            //initialize elements
            for (Node<E> x = first; x != null; x = x.next) {
                clone.add(x.item);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    /**
     * 优化:仅需要依次输出节点中的数据即可，无需序列化Node本身
     *
     * @param s
     * @throws IOException
     */
    @Serial
    private void writeObject(ObjectOutputStream s) throws IOException {
        int expectedModCount = modCount;
        s.defaultWriteObject();
        s.writeInt(size);
        for (Node<E> x = first; x != null; x = x.next) {
            s.writeObject(x.item);
        }
        if (expectedModCount != modCount) {
            throw new ConcurrentModificationException();
        }
    }

    @Serial
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int size = s.readInt();
        for (int i = 0; i < size; i++) {
            linkLast((E) s.readObject());
        }
    }


    private static class Node<E> {
        Node<E> prev;
        E item;
        Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private class Itr implements Iterator<E> {
        private Node<E> next;
        private Node<E> lastReturned;
        private int nextIndex;
        private int expectedModCount = modCount;

        public Itr(int index) {
            //此处index==size时为合法条件,也可应对空集合的场景
            checkPositionIndex(index);
            this.nextIndex = index;
            next = index == size ? null : node(index);
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = next;
            nextIndex++;
            next = next.next;
            return lastReturned.item;
        }

        /**
         * @see LinkedList.ListItr#remove()
         */
        @Override
        public void remove() {
            checkForComodification();
            if (lastReturned == null) {
                throw new NoSuchElementException();
            }
            //考虑前向遍历,前向遍历时不变式:lastReturned==next,删除后nextIndex不能--
            Node<E> lastNext = lastReturned.next;
            //remove last returned node
            unlink(lastReturned);
            //前向遍历
            if (lastReturned == next) {
                next = lastNext;
            } else {
                //后向遍历
                nextIndex--;
            }
            lastReturned = null;
            expectedModCount++;
        }

        @Override
        public void forEachRemaining(@NonNull Consumer<? super E> action) {
            while (modCount == expectedModCount && nextIndex < size) {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
