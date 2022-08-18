package com.techzealot.collection.set;

import java.util.Comparator;

public interface MySortedSet<E> extends MySet<E> {
    Comparator<? super E> comparator();
}
