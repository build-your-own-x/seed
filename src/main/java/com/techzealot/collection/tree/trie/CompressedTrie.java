package com.techzealot.collection.tree.trie;

/**
 * 压缩字典树
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
    public void add(String key, V value) {

    }

    @Override
    public V remove(String key) {
        return null;
    }

    @Override
    public boolean startWith(String prefix) {
        return false;
    }
}
