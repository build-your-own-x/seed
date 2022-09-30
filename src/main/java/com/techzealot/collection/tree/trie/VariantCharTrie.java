package com.techzealot.collection.tree.trie;

import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 字典树可以存储附加信息以便完成更多统计任务
 * <p>
 * 此实现尽量不使用递归算法
 *
 * @param <V>
 */
public class VariantCharTrie<V> implements CharTrie<V> {

    private Node root;

    @Override
    public int size() {
        if (root == null) return 0;
        return root.pass;
    }

    @Override
    public boolean contains(String word) {
        Node node = retrieve(word);
        return node != null && node.end > 0;
    }

    private void check(@NonNull String word) {
        int length = word.length();
        if (length == 0) {
            throw new IllegalArgumentException();
        }
    }

    private Node retrieve(String word) {
        if (word == null) return null;
        int len = word.length();
        if (root == null || len == 0) return null;
        Node node = root;
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            Node nextNode = node.next.get(c);
            if (nextNode == null) return null;
            node = nextNode;
        }
        return node;
    }

    @Override
    public void put(String key, V value) {
        check(key);
        int length = key.length();
        if (root == null) {
            root = new Node();
        }
        root.pass++;
        Node node = root;
        for (int i = 0; i < length; i++) {
            char c = key.charAt(i);
            Node nextNode = node.next.get(c);
            if (nextNode == null) {
                nextNode = new Node();
                node.next.put(c, nextNode);
            }
            nextNode.pass++;
            node = nextNode;
        }
        node.end++;
        node.v = value;
    }

    @Override
    public V get(String key) {
        Node node = retrieve(key);
        return node == null ? null : node.v;
    }

    @Override
    public V remove(String key) {
        if (root == null || key == null || key.length() == 0) return null;
        Object[] removed = new Object[]{null};
        root = remove(root, key, 0, removed);
        if (removed[0] == null) return null;
        return (V) removed[0];
    }

    private Node remove(Node node, String key, int index, Object[] removed) {
        int length = key.length();
        if (index == length) {
            if (node.end > 0) {
                removed[0] = node.v;
                if (node.next.isEmpty()) {
                    return null;
                } else {
                    node.pass -= node.end;
                    node.v = null;
                    node.end = 0;
                    return node;
                }
            } else {
                return node;
            }
        }
        char c = key.charAt(index);
        Node nextNode = node.next.get(c);
        if (nextNode == null) return node;
        int pass = nextNode.pass;
        int end = nextNode.end;
        Node newNextNode = remove(nextNode, key, index + 1, removed);
        if (newNextNode == null) {
            node.next.remove(c);
            node.pass -= pass;
            if (node.next.isEmpty() && node.end == 0) {
                return null;
            }
        } else {
            node.pass -= (pass - newNextNode.pass);
        }
        return node;
    }

    @Override
    public boolean startWith(String prefix) {
        return retrieve(prefix) != null;
    }

    @Override
    public List<String> keyList() {
        Map<String, Node> nodes = nodesMap(root);
        return nodes.keySet().stream().toList();
    }

    public Map<String, V> searchPrefix(String prefix) {
        Map<String, V> ret = new TreeMap<>();
        Node rt = retrieve(prefix);
        if (rt == null) return Collections.emptyMap();
        if (rt.end > 0) {
            ret.put(prefix, rt.v);
        }
        Map<String, Node> nodes = nodesMap(rt);
        nodes.forEach((k, v) -> {
            ret.put(prefix + k, v.v);
        });
        return ret;
    }

    /**
     * 获取以root节点为根节点的所有为word子节点及对应字符串(end>0)
     *
     * @param root
     * @return
     */
    private Map<String, Node> nodesMap(Node root) {
        if (root == null) return Collections.emptyMap();
        Map<String, Node> ret = new TreeMap<>();
        for (Map.Entry<Character, Node> entry : root.next.entrySet()) {
            Character key = entry.getKey();
            Node node = entry.getValue();
            if (node.end > 0) {
                ret.put(key.toString(), node);
            }
            Map<String, Node> nextMap = nodesMap(node);
            for (Map.Entry<String, Node> nodeEntry : nextMap.entrySet()) {
                ret.put(key.toString() + nodeEntry.getKey(), nodeEntry.getValue());
            }
        }
        return ret;
    }

    /**
     * 词频统计
     *
     * @param word
     * @return
     */
    public int countWord(String word) {
        Node node = retrieve(word);
        return node == null ? 0 : node.end;
    }

    /**
     * 前缀词频统计
     *
     * @param prefix
     * @return
     */
    public int countPrefix(String prefix) {
        Node node = retrieve(prefix);
        return node == null ? 0 : node.pass;
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
