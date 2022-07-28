package com.techzealot.collection;

public interface MyCollection<E> extends Iterable<E> {

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    boolean contains(Object element);

    Object[] toArray();


    boolean add(E e);

    boolean remove(Object o);

    boolean addAll(MyCollection<? extends E> c);

    boolean removeAll(MyCollection<?> c);

    boolean retainAll(MyCollection<?> c);

    void clear();
}
