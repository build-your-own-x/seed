package com.techzealot.collection.tree;

import java.util.List;
import java.util.function.Consumer;

public interface BST<E> {
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

    E minimum();

    E removeMin();

    E maximum();

    E removeMax();

    E floor(E e);

    E ceiling(E e);

    E predecessor(E e);

    E successor(E e);

    List<E> toList();

    class Node<E> {
        Node<E> left;
        E e;
        Node<E> right;

        public Node(E e) {
            this.left = null;
            this.e = e;
            this.right = null;
        }
    }
}
