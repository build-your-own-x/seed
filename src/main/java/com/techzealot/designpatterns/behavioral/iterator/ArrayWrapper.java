package com.techzealot.designpatterns.behavioral.iterator;

import lombok.NonNull;

import java.util.Iterator;

public record ArrayWrapper<E>(E[] elements) implements Iterable<E> {

    @Override
    @NonNull
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elements);
    }
}
