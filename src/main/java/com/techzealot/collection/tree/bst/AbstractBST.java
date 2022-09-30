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
        if (comparator == null && !(o instanceof Comparable<?>)) {
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
     * 中序遍历：左子树->根节点->右子树(升序) 也可以 右子树->根节点->左子树(降序)
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

    @Override
    public Iterator<E> preOrderIterator() {
        return new PreOrderItr();
    }

    @Override
    public Iterator<E> inOrderIterator() {
        return new InOrderItr();
    }

    @Override
    public Iterator<E> postOrderIterator() {
        return new PostOrderItr();
    }

    @Override
    public Iterator<E> levelOrderIterator() {
        return new LevelOrderItr();
    }

    /**
     * @param e
     * @return
     */
    @Override
    public E predecessor(E e) {
        if (e == null) return null;
        Node<E> predecessor = predecessor(root(), e, null);
        return predecessor == null ? null : predecessor.value();
    }

    //由于在计算前驱和后继时需要相关父节点信息,一般Node中无父节点信息,可以在递归时存储需要的信息

    private Node<E> predecessor(Node<E> node, E e, Node<E> lastRightParent) {
        if (node == null) return null;
        if (compare(e, node.value()) > 0) {
            lastRightParent = node;
            return predecessor(node.right(), e, lastRightParent);
        } else if (compare(e, node.value()) < 0) {
            return predecessor(node.left(), e, lastRightParent);
        } else {
            if (node.left() != null) {
                return maximum(node.left());
            }
            return lastRightParent;
        }
    }

    @Override
    public E successor(E e) {
        if (e == null) return null;
        Node<E> successor = successor(root(), e, null);
        return successor == null ? null : successor.value();
    }

    private Node<E> successor(Node<E> node, E e, Node<E> lastLeftNode) {
        if (node == null) return null;
        if (compare(e, node.value()) > 0) {
            return successor(node.right(), e, lastLeftNode);
        } else if (compare(e, node.value()) < 0) {
            lastLeftNode = node;
            return successor(node.left(), e, lastLeftNode);
        } else {
            if (node.right() != null) {
                return minimum(node.right());
            }
            return lastLeftNode;
        }
    }

    /**
     * 按照中序遍历顺序返回(从小到大)
     *
     * @return
     */
    @Override
    public List<E> toList() {
        List<E> list = new ArrayList<>(size());
        inOrder(list::add);
        return list;
    }

    @Override
    public String toString() {
        return new TreePrinter<E>(root()).print();
    }

//    对于各种顺序的迭代器实现此处有两种做法:
//    1.先中序遍历返回得到集合的迭代器 空间复杂度O(n) 不推荐
//    2.使用栈实现 空间复杂度更低
    /*统一迭代器设计:借助双端队列和游标
    (1)确定遍历起始节点
    (2)存储下一次遍历相关数据
    (3)调整游标至下一次要处理的节点*/

    /**
     * 空间复杂度 O(h) h为树高
     */
    private class PreOrderItr implements Iterator<E> {
        private final Deque<Node<E>> stack;
        private Node<E> cur;

        public PreOrderItr() {
            stack = new ArrayDeque<>();
            cur = root();
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || cur != null;
        }

        @Override
        public E next() {
            if (cur != null) {
                stack.push(cur);
            }
            Node<E> node = stack.pop();
            Node<E> right = node.right();
            if (right != null) {
                stack.push(right);
            }
            cur = node.left();
            return node.value();
        }
    }

    /**
     * 空间复杂度 O(h) h为树高
     */
    private class InOrderItr implements Iterator<E> {

        private final Deque<Node<E>> stack;
        private Node<E> cur;

        public InOrderItr() {
            stack = new ArrayDeque<>();
            cur = root();
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || cur != null;
        }

        @Override
        public E next() {
            //当前指针移动至最小节点
            while (cur != null) {
                stack.push(cur);
                cur = cur.left();
            }
            //弹出最小
            Node<E> ret = stack.pop();
            //指针指向右子树根节点
            cur = ret.right();
            return ret.value();
        }
    }

    /**
     * 空间复杂度O(1) 时间复杂度O(1)
     */
    private class LevelOrderItr implements Iterator<E> {

        private final Queue<Node<E>> queue;
        private Node<E> cur;

        public LevelOrderItr() {
            this.queue = new ArrayDeque<>();
            cur = root();
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty() || cur != null;
        }

        @Override
        public E next() {
            if (cur != null) {
                queue.add(cur);
            }
            Node<E> node = queue.remove();
            Node<E> left = node.left();
            if (left != null) {
                queue.add(left);
            }
            cur = node.right();
            return node.value();
        }
    }

    private class PostOrderItr implements Iterator<E> {
        private final Deque<Node<E>> stack;
        private Node<E> cur;
        private Node<E> visited;

        public PostOrderItr() {
            stack = new ArrayDeque<>();
            cur = root();
        }

        @Override
        public boolean hasNext() {
            return cur != null || !stack.isEmpty();
        }

        @Override
        public E next() {
            while (cur != null) {
                stack.push(cur);
                //优先迭代子节点
                if (cur.left() != null) {
                    cur = cur.left();
                } else {
                    cur = cur.right();
                }
            }
            Node<E> node = stack.pop();
            visited = node;
            Node<E> peek = stack.peek();
            if (peek != null && peek.right() != visited) {
                cur = peek.right();
            }
            return node.value();
        }
    }
}
