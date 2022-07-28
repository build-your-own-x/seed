package com.techzealot.collection.list;

import com.techzealot.collection.MyCollection;
import com.techzealot.collection.MyDeque;
import com.techzealot.collection.MyList;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 可实现Queue和stack功能
 * elements can be null
 *
 * @param <E>
 */
public class MyLinkedList<E> implements MyList<E>, MyDeque<E>, Serializable, Cloneable {

    /**
     * 链表的头尾指针要么全为空,要么全不为空
     * (first==null&&last==null)||(first!=null&&last!=null)
     * first不变式:(first==null&&last==null)||(first.prev==null)
     * last不变式:(first==null&&last==null)||(last.next==null)
     */
    private transient Node<E> first;
    private transient Node<E> last;
    private transient int size = 0;
    private transient int modCount = 0;

    public MyLinkedList() {
    }

    public MyLinkedList(@NonNull MyCollection<? extends E> c) {
        //addAll(c);
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
        Node<E> prev = succ.prev;
        Node<E> newNode = new Node<>(prev, e, succ);
        succ.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
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
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
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
    public boolean addAll(MyCollection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(MyCollection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(MyCollection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object element) {
        return indexOf(element) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
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
}
