package com.techzealot.collection.list;

public interface MyCollection<E> extends Iterable<E> {

    Object[] toArray();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }
}
