package com.techzealot.collection.tree.trie;

/**
 *
 */
public interface CharTrie<V> {
    int size();

    boolean contains(String word);

    void add(String key, V value);

    V remove(String key);

    boolean startWith(String prefix);
}
