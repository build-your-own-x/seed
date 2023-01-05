package com.techzealot.collection.tree.bst.rbtree;

import com.techzealot.collection.tree.bst.AbstractBSTMap;

import java.util.Set;

/**
 * @see java.util.TreeMap
 * 传统红黑树:任何不平衡都会在三次旋转内解决
 * 等价于2-3-4树(4阶B树)
 */
public class RBTree<K, V> extends AbstractBSTMap<K, V> {

    @Override
    protected Entry<K, V> root() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public void removeMin() {

    }

    @Override
    public void removeMax() {

    }
}
