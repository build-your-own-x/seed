package com.techzealot.collection.tree;

import lombok.NonNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * 二分搜索树:该实现不包含重复元素,不支持null元素
 * <p>
 * 二叉树递归技巧：返回当前根节点，并把null视作空树，Node代表子树
 * <p>
 * 标准库中处理集合对象比较问题的方式：
 * 若定义比较器则使用比较器，若未定义比较器则元素本身必须为Comparable否则强转异常
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
    public boolean add(@NonNull E e) {
        Boolean modified = false;
        root = add(root, e, modified);
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

    public boolean addNoRecursive(@NonNull E e) {
        return true;
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

    /**
     * 用被删节点左子树的最大值或右子树的最小值替换被删节点
     *
     * @param o
     * @return
     */
    public boolean remove(Object o) {
        if (o == null || !(o instanceof Comparable)) return false;
        if (size == 0) return false;
        Boolean removed = false;
        root = remove(root, o, removed);
        return removed;
    }

    private Node remove(Node node, Object o, Boolean removed) {
        if (node == null) return null;
        Comparable x = (Comparable) o;
        if (x.compareTo(node.e) > 0) {
            node.right = remove(node.right, o, removed);
            return node;
        } else if (x.compareTo(node.e) < 0) {
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
                Node rightMin = minimum(node.right);
                rightMin.right = removeMin(node.right);
                rightMin.left = node.left;
                node.right = node.left = null;
                return rightMin;
            }
        }
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
        if (o instanceof Comparable x) {
            if (x.compareTo(node.e) == 0) {
                return true;
            } else if (x.compareTo(node.e) < 0) {
                return contains(node.left, o);
            } else {
                return contains(node.right, o);
            }
        } else {
            return false;
        }
    }

    /**
     * 前序遍历非递归实现 使用栈实现
     *
     * @param action
     */
    public void preOrderNR(Consumer<? super E> action) {
        if (root == null) return;
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            action.accept(current.e);
            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
        }
    }

    public void inOrderNR(Consumer<? super E> action) {

    }

    public void postOrderNR(Consumer<? super E> action) {

    }

    /**
     * 前序遍历：根节点->左子树->右子树
     * 深度优先遍历
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

    /**
     * 层序遍历
     *
     * @param action
     */
    public void levelOrder(Consumer<? super E> action) {
        if (root == null) return;
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            action.accept(cur.e);
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }

    public E minimum() {
        if (size == 0) {
            return null;
        }
        return minimum(root).e;
    }

    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

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
    private Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            size--;
            node.right = null;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    public E maximum() {
        if (size == 0) return null;
        return maximum(root).e;
    }

    private Node maximum(Node node) {
        if (node.right == null) return node;
        return maximum(node.right);
    }

    public E removeMax() {
        if (size == 0) return null;
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    private Node removeMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    //todo
    // predecessor,successor,floor,ceil,rank,select,

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
