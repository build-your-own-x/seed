package com.techzealot.collection.tree.trie;

import lombok.NonNull;

import java.util.*;

public class SimpleCharTrie<V> implements CharTrie<V> {

    private int size;

    private Node root;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(@NonNull String word) {
        if (root == null) return false;
        return retrieve(root, word, 0) != null;
    }

    private Node retrieve(Node node, String word, int index) {
        if (index == word.length()) {
            return node.word ? node : null;
        }
        char c = word.charAt(index);
        Node next = node.next.get(c);
        if (next == null) return null;
        return retrieve(next, word, index + 1);
    }

    /**
     * 使用递归
     *
     * @param key
     * @param value
     */
    @Override
    public void put(@NonNull String key, V value) {
        root = put(root, key, 0, value);
        size++;
    }

    @Override
    public V get(@NonNull String key) {
        Node node = retrieve(root, key, 0);
        return node == null ? null : node.value;
    }

    private Node put(Node node, String key, int index, V value) {
        if (node == null) {
            node = new Node();
        }
        int length = key.length();
        if (index == length) {
            node.value = value;
            node.word = true;
            return node;
        }
        char c = key.charAt(index);
        node.next.put(c, put(node.next.get(c), key, index + 1, value));
        return node;
    }

    @Override
    public V remove(@NonNull String key) {
        if (root == null) return null;
        Object[] removed = new Object[]{null};
        root = remove(root, key, 0, removed);
        if (removed[0] == null) {
            return null;
        }
        size--;
        return ((Node) removed[0]).value;
    }

    private Node remove(Node node, String key, int index, Object[] removed) {
        if (index == key.length()) {
            if (!node.word) {
                return node;
            }
            removed[0] = node;
            if (node.next.isEmpty()) {
                return null;
            } else {
                node.word = false;
                return node;
            }
        }
        char c = key.charAt(index);
        Node next = node.next.get(c);
        if (next == null) return node;
        if (remove(next, key, index + 1, removed) == null) {
            node.next.remove(c);
            if (node.next.isEmpty() && !node.word) {
                return null;
            }
        }
        return node;
    }

    /**
     * 暂不支持正则表达式
     *
     * @param prefix
     * @return
     */
    @Override
    public boolean startWith(@NonNull String prefix) {
        if (root == null) return false;
        return startWith(root, prefix, 0);
    }

    private boolean startWith(Node node, String prefix, int index) {
        if (index == prefix.length()) return true;
        Node next = node.next.get(prefix.charAt(index));
        if (next == null) return false;
        return startWith(next, prefix, index + 1);
    }

    /**
     * 按字母顺序的字符串列表
     *
     * @return
     */
    @Override
    public List<String> keyList() {
        if (root == null) return Collections.emptyList();
        return keyList(root);
    }

    private List<String> keyList(Node node) {
        List<String> ret = new ArrayList<>();
        Map<Character, Node> next = node.next;
        if (next.isEmpty()) {
            return ret;
        }
        for (Map.Entry<Character, Node> entry : next.entrySet()) {
            Node nextNode = entry.getValue();
            Character key = entry.getKey();
            if (nextNode.word) {
                ret.add(key.toString());
            }
            List<String> strings = keyList(nextNode);
            for (String string : strings) {
                ret.add(key.toString() + string);
            }
        }
        return ret;
    }

    private class Node {
        //是否单词结尾
        boolean word;
        //子节点映射,数据存储在连接线中而非节点中
        Map<Character, Node> next = new TreeMap<>();

        V value;
    }
}
