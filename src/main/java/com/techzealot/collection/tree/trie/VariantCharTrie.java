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

    /**
     * 词频统计
     *
     * @param word
     * @return
     */
    public int search(String word) {
        return -1;
    }

    /**
     * 前缀词频统计
     *
     * @param word
     * @return
     */
    public int prefixNumber(String word) {
        return -1;
    }

    /**
     * 字符串排序
     *
     * @return
     */
    public List<String> sort() {
        return null;
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
