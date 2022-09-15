package com.techzealot.collection.tree.bst;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public interface BST<E> extends Iterable<E> {

    int size();

    boolean isEmpty();

    boolean add(E e);

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

    public class TreePrinter<E> implements Printer {

        private final Node<E> node;

        public TreePrinter(Node<E> node) {
            this.node = node;
        }

        @Override
        public String print() {
            return null;
        }
    }
}
