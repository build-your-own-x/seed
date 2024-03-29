package com.techzealot.collection.tree.bst.base;

import com.techzealot.collection.tree.bst.AbstractBSTSet;
import com.techzealot.collection.tree.bst.BSTSet;
import lombok.NonNull;

import java.text.MessageFormat;
import java.util.Comparator;

/**
 * support rank and select
 * max=rank(size-1)
 * min=rank(0)
 * predecessor(e)=select(rank(e)-1)
 * successor(e)=select(rank(e)+1)
 */
public class RankedBSTSet<E> extends AbstractBSTSet<E> {

    private Node root;

    public RankedBSTSet() {
    }

    public RankedBSTSet(Comparator<E> comparator) {
        super(comparator);
    }

    public static <T> RankedBSTSet<T> of(@NonNull T... elements) {
        RankedBSTSet<T> bst = new RankedBSTSet<>();
        for (T element : elements) {
            bst.put(element);
        }
        return bst;
    }

    @Override
    protected Node root() {
        return root;
    }

    private void validate() {
        validate(root);
    }

    private void validate(Node node) {
        if (node == null) return;
        if (node.size != size(node.left) + size(node.right) + 1) {
            throw new IllegalStateException(MessageFormat.format("node :{0} size error", node));
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.size;
    }

    @Override
    public void put(@NonNull E e) {
        root = put(root, e);
        validate();
    }

    private Node put(Node node, E e) {
        if (node == null) {
            return new Node(e);
        }
        if (compare(e, node.e) == 0) {
            node.e = e;
            return node;
        } else if (compare(e, node.e) < 0) {
            node.left = put(node.left, e);
        } else {
            node.right = put(node.right, e);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        if (comparator == null && !(o instanceof Comparable<?>)) return false;
        boolean[] removed = new boolean[]{false};
        root = remove(root, o, removed);
        validate();
        return removed[0];
    }

    private Node remove(Node node, Object o, boolean[] removed) {
        if (node == null) return null;
        if (compare((E) o, node.e) == 0) {
            removed[0] = true;
            if (node.right == null) {
                Node left = node.left;
                node.left = null;
                return left;
            } else {
                Node rightMin = (Node) minimum(node.right);
                node.e = rightMin.e;
                node.right = removeMin(node.right);
                return node;
            }
        } else if (compare((E) o, node.e) < 0) {
            node.left = remove(node.left, o, removed);
        } else {
            node.right = remove(node.right, o, removed);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public E removeMin() {
        E minimum = minimum();
        root = removeMin(root);
        validate();
        return minimum;
    }

    private Node removeMin(Node node) {
        if (node == null) return null;
        if (node.left == null) {
            Node right = node.right;
            node.right = null;
            return right;
        }
        node.left = removeMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public E removeMax() {
        E maximum = maximum();
        root = removeMax(root);
        validate();
        return maximum;
    }

    private Node removeMax(Node node) {
        if (node == null) return null;
        if (node.right == null) {
            Node left = node.left;
            node.left = null;
            return left;
        }
        node.right = removeMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public int rank(E e) {
        if (!contains(e)) return -2;
        return rank(root, e);
    }

    /**
     * number of nodes less than given e:比给定e小的节点个数
     * 类似于数组索引，从0开始,e不一定存在于树中
     *
     * @param node
     * @param e
     * @return
     */
    private int rank(Node node, E e) {
        if (node == null) return 0;
        if (compare(e, node.e) > 0) {
            return size(node.left) + 1 + rank(node.right, e);
        } else if (compare(e, node.e) < 0) {
            return rank(node.left, e);
        } else {
            return size(node.left);
        }
    }

    public E select(int k) {
        if (k < 0 || k >= size()) return null;
        return select(root, k);
    }

    private E select(Node node, int k) {
        if (node == null) return null;
        if (k == size(node.left)) {
            return node.e;
        } else if (k < size(node.left)) {
            return select(node.left, k);
        } else {
            return select(node.right, k - (size(node.left) + 1));
        }
    }

    @Override
    public E minimum() {
        return select(0);
    }

    @Override
    public E maximum() {
        return select(size() - 1);
    }

    @Override
    public E predecessor(E e) {
        return select(rank(e) - 1);
    }

    @Override
    public E successor(E e) {
        return select(rank(e) + 1);
    }

    private class Node implements BSTSet.Node<E> {
        /**
         * 以该节点作为根节点的子树节点个数
         */
        int size;
        Node left;
        E e;
        Node right;

        public Node(E e) {
            this.left = null;
            this.e = e;
            this.right = null;
            this.size = 1;
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
