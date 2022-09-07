package com.techzealot.collection.tree.bst;

import lombok.NonNull;

import java.util.Comparator;

/**
 * 二分搜索树的一般实现:该实现不包含重复元素,不支持null元素
 * <p>
 * 二叉树递归技巧：返回当前根节点，并把null视作空树，Node代表子树
 * <p>
 * 标准库中处理集合对象比较问题的方式：
 * 若显式定义比较器则使用比较器，若未定义比较器则元素本身必须为Comparable否则强转异常
 */
public class BaseBST<E> extends AbstractBST<E> {

    private int size;
    private Node root;

    public BaseBST() {
    }

    public BaseBST(Comparator<E> comparator) {
        super(comparator);
    }

    public static <T> BaseBST of(@NonNull T... elements) {
        BaseBST bst = new BaseBST();
        for (T element : elements) {
            bst.add(element);
        }
        return bst;
    }

    @Override
    protected BST.Node<E> root() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * @param e
     * @return false if e already exists
     */
    @Override
    public boolean add(@NonNull E e) {
        Boolean modified = false;
        root = add(root, e, modified);
        return modified;
    }

    public boolean addNoRecursive(@NonNull E e) {
        Node cur = root;
        while (cur != null) {
            if (compare(e, cur.e) == 0) {
                return false;
            } else if (compare(e, cur.e) > 0) {
                Node right = cur.right;
                if (right == null) {
                    cur.right = new Node(e);
                    size++;
                    return true;
                }
                cur = right;
            } else {
                Node left = cur.left;
                if (left == null) {
                    cur.left = new Node(e);
                    size++;
                    return true;
                }
                cur = left;
            }
        }
        return false;
    }

    /**
     * 优化：空节点视作空树
     * 递归实现插入节点
     * 返回当前子树的根节点
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
        if (compare(e, node.e) < 0) {
            node.left = add(node.left, e, modified);
        } else if (compare(e, node.e) > 0) {
            node.right = add(node.right, e, modified);
        }
        return node;
    }

    /**
     * 用被删节点左子树的最大值或右子树的最小值替换被删节点
     *
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        if (o == null || !(o instanceof Comparable)) return false;
        if (size == 0) return false;
        Boolean removed = false;
        root = remove(root, o, removed);
        return removed;
    }

    private Node remove(Node node, Object o, Boolean removed) {
        if (node == null) return null;
        if (compare((E) o, node.e) > 0) {
            node.right = remove(node.right, o, removed);
            return node;
        } else if (compare((E) o, node.e) < 0) {
            node.left = remove(node.left, o, removed);
            return node;
        } else {
            removed = true;
            if (node.right == null) {
                Node left = node.left;
                node.left = null;
                size--;
                return left;
            } else {
                Node rightMin = (Node) minimum(node.right);
                rightMin.right = removeMin(node.right);
                rightMin.left = node.left;
                node.right = node.left = null;
                return rightMin;
            }
        }
    }

    @Override
    public E removeMin() {
        E ret = minimum();
        if (ret == null) return null;
        root = removeMin(root);
        return ret;
    }

    /**
     * 返回当前子树的根节点
     *
     * @param node
     * @return
     */
    protected Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            size--;
            node.right = null;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    @Override
    public E removeMax() {
        if (size == 0) return null;
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    protected Node removeMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    private class Node implements BST.Node<E> {

        Node left;
        E e;
        Node right;

        public Node(E e) {
            this.left = null;
            this.e = e;
            this.right = null;
        }

        @Override
        public Node left() {
            return left;
        }

        @Override
        public E value() {
            return e;
        }

        @Override
        public Node right() {
            return right;
        }
    }
}
