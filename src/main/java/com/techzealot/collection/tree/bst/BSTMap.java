package com.techzealot.collection.tree.bst;

import java.util.Set;

public interface BSTMap<K, V> {
    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    V get(Object key);

    V put(K key, V value);

    V remove(Object key);

    Set<Entry<K, V>> entrySet();

    void removeMin();

    void removeMax();

    interface Entry<K, V> {
        K key();

        V value();

        Entry<K, V> left();

        Entry<K, V> right();
    }
}
