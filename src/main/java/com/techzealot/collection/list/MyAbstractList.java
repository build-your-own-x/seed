package com.techzealot.collection.list;

import com.techzealot.collection.MyAbstractCollection;

import java.util.Iterator;
import java.util.Objects;

public abstract class MyAbstractList<E> extends MyAbstractCollection<E> implements MyList<E> {
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this) {
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof MyList)) return false;
        Iterator<E> e1 = iterator();
        Iterator<?> e2 = ((MyList<?>) o).iterator();
        while (e1.hasNext() && e2.hasNext()) {
            if (!Objects.equals(e1.next(), e2.next())) {
                return false;
            }
        }
        return !e1.hasNext() && !e2.hasNext();
    }
}
