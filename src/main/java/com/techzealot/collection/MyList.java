package com.techzealot.collection;

public interface MyList<E> extends MyCollection<E> {
    
    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    int indexOf(Object o);
}
