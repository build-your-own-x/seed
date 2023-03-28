package com.techzealot.collection.tree.bst.rbtree;

import com.techzealot.collection.tree.bst.AbstractBSTMap;

import java.util.Set;

/**
 * @see java.util.TreeMap
 * 传统红黑树:等价于2-3-4树(4阶B树)
 * 添加:O(logn),O(1)旋转
 * 删除:O(logn),O(1)旋转
 * 任何不平衡都会在三次旋转内解决
 * 1.插入
 * 最多旋转2次:进行下一次递归的前提是4节点上溢,进行的是变色操作;在递归时一旦开始旋转(2次内结束)，递归就会结束
 * 2.删除
 * 最多旋转3次:
 */
public class RBTree<K, V> extends AbstractBSTMap<K, V> {

    private static boolean BLACK = false;
    private static boolean RED = true;

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

    /**
     * 黑色代表节点为空节点或节点为黑色:重平衡时黑节点和空节点的处理逻辑是一致的
     *
     * @param p
     * @return
     */
    private boolean colorOf(Node p) {
        return p == null ? BLACK : p.color;
    }

    private Node leftOf(Node p) {
        return p == null ? null : p.left;
    }

    private Node rightOf(Node p) {
        return p == null ? null : p.right;
    }

    private Node parentOf(Node p) {
        return p == null ? null : p.parent;
    }

    private void setColor(Node p, boolean color) {
        if (p != null) {
            p.color = color;
        }
    }

    private class Node implements Entry<K, V> {
        Node left;
        Node right;
        Node parent;
        K key;
        V value;
        //新节点默认为黑色:新节点需要显式染成红色,避免无意义赋值
        boolean color = BLACK;

        public Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }

        @Override
        public Entry<K, V> left() {
            return left;
        }

        @Override
        public Entry<K, V> right() {
            return right;
        }
    }
}
