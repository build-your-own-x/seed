package com.techzealot.collection.tree.trie;

import java.util.List;

/**
 * TODO: 压缩字典树
 *
 * @param <V>
 */
public class CompressedTrie<V> implements CharTrie<V> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(String word) {
        return false;
    }

    @Override
    public void put(String key, V value) {

    }

    @Override
    public V get(String key) {
        return null;
    }

    @Override
    public V remove(String key) {
        return null;
    }

    @Override
    public boolean startWith(String prefix) {
        return false;
    }

    @Override
    public List<String> keyList() {
        return null;
    }
}
