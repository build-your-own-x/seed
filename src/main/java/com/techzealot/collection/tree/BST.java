package com.techzealot.collection.tree;

import lombok.NonNull;

import java.util.function.Consumer;

/**
 * 二分搜索树:该实现不包含重复元素
 */
public class BST<E extends Comparable<E>> {
    private Node root;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param e
     * @return false if e already exists
     */
    public boolean add(E e) {
        Boolean modified = false;
        add(root, e, modified);
        return modified;
    }

    public boolean add0(@NonNull E e) {
        if (root == null) {
            root = new Node(e);
            size++;
            return true;
        }
        return add0(root, e);
    }

    /**
     * 优化：空节点视作空树
     * 递归实现插入节点
     * 返回当前遍历的根节点或新插入节点
     *
     * @param node
     * @param e
     * @return
     */
    private Node add(Node node, E e, Boolean modified) {
        if (node == null) {
            size++;
            modified = true;
            return new Node(e);
        }
        if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e, modified);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e, modified);
        }
        return node;
    }

    /**
     * 比较复杂的递归，不够简洁
     *
     * @param node
     * @param e
     * @return
     */
    private boolean add0(Node node, E e) {
        if (e.compareTo(node.e) < 0) {
            if (node.left != null) {
                return add0(node.left, e);
            } else {
                node.left = new Node(e);
                size++;
                return true;
            }
        } else if (e.compareTo(node.e) > 0) {
            if (node.right != null) {
                return add0(node.right, e);
            } else {
                node.right = new Node(e);
                size++;
                return true;
            }
        }
        return false;
    }

    public boolean remove(Object o) {
        return true;
    }

    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return contains(root, o);
    }

    private boolean contains(Node node, Object o) {
        if (node == null) {
            return false;
        }
        Comparable x = (Comparable) o;
        if (x.compareTo(node.e) == 0) {
            return true;
        } else if (x.compareTo(node.e) < 0) {
            return contains(node.left, o);
        } else {
            return contains(node.right, o);
        }
    }

    /**
     * 前序遍历：根节点->左子树->右子树
     *
     * @param action
     */
    public void preOrder(Consumer<? super E> action) {
        preOrder(root, action);
    }

    /**
     * @param node
     * @param action
     */
    private void preOrder(Node node, Consumer<? super E> action) {
        if (node == null) return;
        action.accept(node.e);
        preOrder(node.left, action);
        preOrder(node.right, action);
    }

    /**
     * 中序遍历：左子树->根节点->右子树
     * 应用：遍历顺序就是从小到大的顺序
     *
     * @param action
     */
    public void inOrder(Consumer<? super E> action) {
        inOrder(root, action);
    }

    private void inOrder(Node node, Consumer<? super E> action) {
        if (node == null) return;
        inOrder(node.left, action);
        action.accept(node.e);
        inOrder(node.right, action);
    }

    /**
     * 应用：内存释放
     *
     * @param action
     */
    public void postOrder(Consumer<? super E> action) {
        postOrder(root, action);
    }

    private void postOrder(Node node, Consumer<? super E> action) {
        if (node == null) return;
        postOrder(node.left, action);
        postOrder(node.right, action);
        action.accept(node.e);
    }

    private class Node {
        E e;
        Node left;
        Node right;

        public Node(E e) {
            this.left = null;
            this.e = e;
            this.right = null;
        }
    }
}
