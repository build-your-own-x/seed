package com.techzealot.collection.tree.bst;

import java.util.Comparator;
import java.util.Set;

public abstract class AbstractBSTMap<K, V> implements BSTMap<K, V> {
    protected final Comparator<K> comparator;

    public AbstractBSTMap() {
        this(null);
    }

    public AbstractBSTMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    protected int compare(K o1, K o2) {
        if (this.comparator != null) return this.comparator.compare(o1, o2);
        Comparable x1 = (Comparable) o1;
        Comparable x2 = (Comparable) o2;
        return x1.compareTo(x2);
    }

    abstract protected Entry<K, V> root();

    @Override
    public boolean isEmpty() {
        return root() == null;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
