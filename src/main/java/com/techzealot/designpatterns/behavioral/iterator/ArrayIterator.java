package com.techzealot.designpatterns.behavioral.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayIterator<E> implements Iterator<E> {

    private int cursor;

    private final E[] elements;

    public ArrayIterator(E[] elements) {
        this.elements = Objects.requireNonNull(elements);
    }

    @Override
    public boolean hasNext() {
        return cursor < elements.length;
    }

    @Override
    public E next() {
        if (cursor >= elements.length) {
            throw new NoSuchElementException();
        }
        return elements[cursor++];
    }
}
