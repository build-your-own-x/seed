package com.techzealot.collection.list;

import com.techzealot.collection.MyCollection;

public interface MyList<E> extends MyCollection<E> {

    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    boolean addAll(int index, MyCollection<? extends E> c);

    E remove(int index);

    int indexOf(Object o);
}
