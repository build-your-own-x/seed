package com.techzealot.collection.tree.bst;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractBST<E> implements BST<E> {
    protected final Comparator<E> comparator;

    public AbstractBST() {
        this(null);
    }

    public AbstractBST(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    protected abstract Node<E> root();

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    protected int compare(E o1, E o2) {
        if (this.comparator != null) return this.comparator.compare(o1, o2);
        Comparable x1 = (Comparable) o1;
        Comparable x2 = (Comparable) o2;
        return x1.compareTo(x2);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return contains(root(), o);
    }

    private boolean contains(Node<E> node, Object o) {
        if (node == null) {
            return false;
        }
        if (compare((E) o, node.value()) == 0) {
            return true;
        } else if (compare((E) o, node.value()) < 0) {
            return contains(node.left(), o);
        } else {
            return contains(node.right(), o);
        }
    }

    /**
     * 前序遍历：根节点->左子树->右子树
     * 深度优先遍历
     *
     * @param action
     */
    @Override
    public void preOrder(Consumer<? super E> action) {
        preOrder(root(), action);
    }

    /**
     * @param node
     * @param action
     */
    private void preOrder(Node<E> node, Consumer<? super E> action) {
        if (node == null) return;
        action.accept(node.value());
        preOrder(node.left(), action);
        preOrder(node.right(), action);
    }

    /**
     * 中序遍历：左子树->根节点->右子树
     * 应用：遍历顺序就是从小到大的顺序
     *
     * @param action
     */
    @Override
    public void inOrder(Consumer<? super E> action) {
        inOrder(root(), action);
    }

    private void inOrder(Node<E> node, Consumer<? super E> action) {
        if (node == null) return;
        inOrder(node.left(), action);
        action.accept(node.value());
        inOrder(node.right(), action);
    }

    /**
     * 应用：内存释放
     *
     * @param action
     */
    @Override
    public void postOrder(Consumer<? super E> action) {
        postOrder(root(), action);
    }

    private void postOrder(Node<E> node, Consumer<? super E> action) {
        if (node == null) return;
        postOrder(node.left(), action);
        postOrder(node.right(), action);
        action.accept(node.value());
    }

    /**
     * 层序遍历
     *
     * @param action
     */
    @Override
    public void levelOrder(Consumer<? super E> action) {
        if (root() == null) return;
        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.add(root());
        while (!queue.isEmpty()) {
            Node<E> cur = queue.remove();
            action.accept(cur.value());
            if (cur.left() != null) {
                queue.add(cur.left());
            }
            if (cur.right() != null) {
                queue.add(cur.right());
            }
        }
    }

    @Override
    public E minimum() {
        if (size() == 0) {
            return null;
        }
        return minimum(root()).value();
    }

    protected final Node<E> minimum(Node<E> node) {
        if (node.left() == null) {
            return node;
        }
        return minimum(node.left());
    }


    @Override
    public E maximum() {
        if (size() == 0) return null;
        return maximum(root()).value();
    }

    protected final Node<E> maximum(Node<E> node) {
        if (node.right() == null) return node;
        return maximum(node.right());
    }

    @Override
    public E floor(E e) {
        Node<E> node = floor(root(), e);
        if (node == null) {
            return null;
        }
        return node.value();
    }

    protected Node<E> floor(Node<E> node, E e) {
        if (node == null) return null;
        if (compare(e, node.value()) == 0) {
            return node;
        } else if (compare(e, node.value()) > 0) {
            Node<E> x = floor(node.right(), e);
            if (x == null) {
                return node;
            }
            return x;
        } else {
            return floor(node.left(), e);
        }
    }

    @Override
    public E ceiling(E e) {
        Node<E> node = ceiling(root(), e);
        if (node == null) return null;
        return node.value();
    }

    protected Node<E> ceiling(Node<E> node, E e) {
        if (node == null) return null;
        if (compare(e, node.value()) == 0) {
            return node;
        } else if (compare(e, node.value()) > 0) {
            return ceiling(node.right(), e);
        } else {
            Node<E> x = ceiling(node.left(), e);
            if (x == null) {
                return node;
            }
            return x;
        }
    }

    /**
     * 前序遍历非递归实现 使用栈实现
     *
     * @param action
     */
    public void preOrderNR(Consumer<? super E> action) {
        if (root() == null) return;
        Deque<Node<E>> stack = new ArrayDeque<>();
        stack.push(root());
        while (!stack.isEmpty()) {
            Node<E> current = stack.pop();
            action.accept(current.value());
            if (current.left() != null) {
                stack.push(current.left());
            }
            if (current.right() != null) {
                stack.push(current.right());
            }
        }
    }

    public void inOrderNR(Consumer<? super E> action) {

    }

    public void postOrderNR(Consumer<? super E> action) {

    }

    //对于未存储父节点引用的Node，无法简单求出前驱和后继节点
    @Override
    public E predecessor(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E successor(E e) {
        throw new UnsupportedOperationException();
    }

    /**
     * 按照中序遍历顺序返回(从小到大)
     *
     * @return
     */
    @Override
    public List<E> toList() {
        List<E> list = new ArrayList<>(size());
        inOrder((e) -> {
            list.add(e);
        });
        return list;
    }
}
