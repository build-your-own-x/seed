package com.techzealot.collection.tree.bst;

import java.util.Comparator;

public abstract class AbstractBSTMap<K, V> implements BSTMap<K, V> {
    protected final Comparator<? super K> comparator;

    public AbstractBSTMap() {
        this(null);
    }

    public AbstractBSTMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    protected int compare(Object k1, Object k2) {
        if (this.comparator != null) return this.comparator.compare((K) k1, (K) k2);
        Comparable x1 = (Comparable) k1;
        Comparable x2 = (Comparable) k2;
        return x1.compareTo(x2);
    }

    protected abstract Entry<K, V> root();

    protected Entry<K, V> getEntry(Object key) {
        Entry<K, V> p = root();
        while (p != null) {
            int cmp = compare(key, p.key());
            if (cmp > 0) {
                p = p.right();
            } else if (cmp < 0) {
                p = p.left();
            } else {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return root() == null;
    }
}
