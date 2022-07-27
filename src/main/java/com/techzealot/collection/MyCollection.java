package com.techzealot.collection;

public interface MyCollection<E> extends Iterable<E> {

    Object[] toArray();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    boolean contains(Object element);
}
