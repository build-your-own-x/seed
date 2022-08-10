package com.techzealot.collection;

import lombok.NonNull;

import java.util.Iterator;

public interface MyCollection<E> extends Iterable<E> {

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    boolean contains(Object o);

    Object[] toArray();


    boolean add(E e);

    boolean remove(Object o);

    default boolean addAll(MyCollection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

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
