package com.techzealot.collection.tree.bst;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * 递归=递归终止条件+递归过程
 *
 * @param <E> 此处可以实现为存储K,V的节点以便同时支持Set和Map,实际设计时可用BSTMap替代BSTSet，无需单独设计BSTSet
 */
public interface BSTSet<E> extends Iterable<E> {

    int size();

    boolean isEmpty();

    /**
     * 对于无则新增有则覆盖的实现来说一般命名为put,表示直接新增语义的一般命名为add
     *
     * @param e
     */
    void put(E e);

    boolean remove(Object o);

    boolean contains(Object o);

    //遍历功能也可以返回不同迭代器来实现，迭代器只能使用非递归方式实现
    void preOrder(Consumer<? super E> action);

    void inOrder(Consumer<? super E> action);

    void postOrder(Consumer<? super E> action);

    void levelOrder(Consumer<? super E> action);

    @Override
    default Iterator<E> iterator() {
        return inOrderIterator();
    }

    Iterator<E> preOrderIterator();

    Iterator<E> inOrderIterator();

    Iterator<E> postOrderIterator();

    Iterator<E> levelOrderIterator();

    E minimum();

    E removeMin();

    E maximum();

    E removeMax();

    E floor(E e);

    E ceiling(E e);

    E predecessor(E e);

    E successor(E e);

    List<E> toList();

    interface Node<E> {
        Node<E> left();

        E value();

        Node<E> right();
    }

    interface Printer {
        String print();
    }

    /**
     * 前序遍历 简单的形式输出
     *
     * @param <E>
     */
    class TreePrinter<E> implements Printer {

        private final Node<E> node;

        public TreePrinter(Node<E> node) {
            this.node = node;
        }

        @Override
        public String print() {
            StringBuilder sb = new StringBuilder();
            if (node != null) {
                toString(node, sb, "");
            }
            return sb.toString();
        }

        private void toString(Node<E> node, StringBuilder sb, String prefix) {
            if (node == null) return;
            sb.append(prefix).append(node.value()).append("\n");
            toString(node.left(), sb, prefix + "[L]");
            toString(node.right(), sb, prefix + "[R]");
        }
    }
}
