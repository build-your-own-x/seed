package com.techzealot.collection;

import lombok.NonNull;

import java.util.Iterator;

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

    default boolean removeAll(@NonNull MyCollection<?> c) {
        Iterator<?> it = iterator();
        boolean modified = false;
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    default boolean retainAll(@NonNull MyCollection<?> c) {
        Iterator<?> it = iterator();
        boolean modified = false;
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    void clear();
}
