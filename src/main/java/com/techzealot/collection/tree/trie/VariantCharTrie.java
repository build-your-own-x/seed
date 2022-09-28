package com.techzealot.collection.tree.trie;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 字典树可以存储附加信息以便完成更多统计任务
 *
 * @param <V>
 */
public class VariantCharTrie<V> implements CharTrie<V> {

    private int size;

    @Override
    public int size() {
        return size;
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

    @Override
    public List<String> keyList() {
        return null;
    }

    public List<Map<String, V>> search(String prefix) {
        return null;
    }

    /**
     * 词频统计
     *
     * @param word
     * @return
     */
    public int countWord(String word) {
        return -1;
    }

    /**
     * 前缀词频统计
     *
     * @param prefix
     * @return
     */
    public int countPrefix(String prefix) {
        return -1;
    }


    private class Node {
        //结尾次数
        int end;
        //经过次数
        int pass;
        V v;
        //more fields...
        Map<Character, Node> next = new TreeMap<>();
    }
}
