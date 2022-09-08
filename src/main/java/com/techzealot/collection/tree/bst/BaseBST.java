package com.techzealot.collection.tree.bst;

import lombok.NonNull;

import java.text.MessageFormat;
import java.util.Arrays;
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
            bst.put(element);
        }
        return bst;
    }

    /**
     * 除根节点外所有节点的parent设置正确
     */
    private void validate() {

    }

    private void validate(Node node) {
        if (node == null) return;
        Node left = node.left;
        Node right = node.right;
        if (left != null) {
            checkEquals(left.parent, node);
        }
        if (right != null) {
            checkEquals(right.parent, node);
        }
    }

    private void checkEquals(Node n1, Node n2) {
        if (n1 != n2) {
            throw new IllegalStateException(MessageFormat.format("{0} != {}", n1, n2));
        }
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
     */
    @Override
    public void put(@NonNull E e) {
        root = put(root, e);
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
    private Node put(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }
        if (compare(e, node.e) < 0) {
            Node left = put(node.left, e);
            node.left = left;
            left.parent = node;
        } else if (compare(e, node.e) > 0) {
            Node right = put(node.right, e);
            node.right = right;
            right.parent = node;
        } else {
            node.e = e;
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
            Node right = remove(node.right, o, removed);
            node.right = right;
            right.parent = node;
            return node;
        } else if (compare((E) o, node.e) < 0) {
            Node left = remove(node.left, o, removed);
            node.left = left;
            left.parent = node;
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
                if (node.left.parent != null) {
                    node.left.parent = rightMin.left;
                }
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
        //存储父节点引用,只有在求前驱和后继时有用，若不需要则无需此字段
        Node parent;
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

        @Override
        public String toString() {
            return Arrays.toString(new Object[]{parent.e, e, left.e, right.e});
        }
    }
}
