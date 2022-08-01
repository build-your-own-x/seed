package com.techzealot.collection.list;

import com.techzealot.collection.MyCollection;
import com.techzealot.collection.MyDeque;
import com.techzealot.collection.MyList;
import lombok.NonNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;

/**
 * 可实现Queue和stack功能
 * elements can be null
 *
 * @param <E>
 */
public class MyLinkedList<E> implements MyList<E>, MyDeque<E>, Serializable, Cloneable {

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
    private transient int modCount = 0;

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

    //todo 由父类通过iterator实现
    @Override
    public boolean removeAll(@NonNull MyCollection<?> c) {
        if (c.isEmpty()) {
            modCount++;
            return false;
        }
        int removed = 0;
        for (Node<E> x = first; x != null; ) {
            //迭代删除需注意先保存下一个元素指针
            Node<E> next = x.next;
            if (c.contains(x.item)) {
                unlink(x);
                removed++;
            }
            x = next;
        }
        return removed != 0;
    }

    //todo 由父类通过iterator实现
    @Override
    public boolean retainAll(@NonNull MyCollection<?> c) {
        if (c.isEmpty()) {
            modCount++;
            clear();
            return true;
        }
        int removed = 0;
        for (Node<E> x = first; x != null; ) {
            //迭代删除需注意先保存下一个元素指针
            Node<E> next = x.next;
            if (!c.contains(x.item)) {
                unlink(x);
                removed++;
            }
            x = next;
        }
        return removed != 0;
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
    public boolean contains(Object element) {
        return indexOf(element) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
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
    public E remove(int index) {
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

    @Override
    public MyLinkedList<E> clone() {
        try {
            MyLinkedList clone = (MyLinkedList) super.clone();
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
