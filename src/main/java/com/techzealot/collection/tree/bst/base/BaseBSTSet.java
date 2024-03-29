package com.techzealot.collection.tree.bst.base;

import com.techzealot.collection.tree.bst.AbstractBSTSet;
import com.techzealot.collection.tree.bst.BSTSet;
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
public class BaseBSTSet<E> extends AbstractBSTSet<E> {

    private int size;
    private Node root;
    private boolean check = true;

    public BaseBSTSet() {
    }

    public BaseBSTSet(Comparator<E> comparator) {
        super(comparator);
    }

    public static <T> BaseBSTSet of(@NonNull T... elements) {
        BaseBSTSet bst = new BaseBSTSet();
        for (T element : elements) {
            bst.put(element);
        }
        bst.validate();
        return bst;
    }

    /**
     * 除根节点外所有节点的parent设置正确
     */
    private void validate() {
        if (check)
            validate(root);
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
        validate(node.left);
        validate(node.right);
    }

    private void checkEquals(Node n1, Node n2) {
        if (n1 != n2) {
            throw new IllegalStateException(MessageFormat.format("{0} != {1}", n1, n2));
        }
    }

    @Override
    protected BSTSet.Node<E> root() {
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
        validate();
    }

    /**
     * 优化：空节点视作空树
     * 递归实现插入节点
     * 返回当前子树的根节点
     * <p>
     * 无法直接通过传递Boolean参数在方法内修改原始变量，可以通过数组来间接实现传递boolean引用的效果
     *
     * @param node
     * @param e    对相同元素建议采用覆盖模式(compare相同不代表对象完全相同)
     * @return
     */
    private Node put(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }
        if (compare(e, node.e) == 0) {
            node.e = e;
        } else if (compare(e, node.e) < 0) {
            node.left = put(node.left, e);
            node.left.parent = node;
        } else {
            node.right = put(node.right, e);
            node.right.parent = node;
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
        if (o == null) return false;
        if (comparator == null && !(o instanceof Comparable)) return false;
        if (size == 0) return false;
        boolean[] removed = new boolean[]{false};
        root = remove(root, o, removed);
        validate();
        return removed[0];
    }

    private Node remove(Node node, Object o, boolean[] removed) {
        if (node == null) return null;
        if (compare((E) o, node.e) > 0) {
            Node right = remove(node.right, o, removed);
            node.right = right;
            if (right != null) {
                right.parent = node;
            }
            return node;
        } else if (compare((E) o, node.e) < 0) {
            Node left = remove(node.left, o, removed);
            node.left = left;
            if (left != null) {
                left.parent = node;
            }
            return node;
        } else {
            removed[0] = true;
            if (node.right == null) {
                Node left = node.left;
                node.left = node.parent = null;
                size--;
                return left;
            } else {
                Node rightMin = (Node) minimum(node.right);
                //此处直接用替换的节点数据替换要删除的节点更简单，无需处理父子关系
                node.e = rightMin.e;
                node.right = removeMin(node.right);
                return node;
            }
        }
    }

    @Override
    public E removeMin() {
        if (size == 0) return null;
        E ret = minimum();
        root = removeMin(root);
        validate();
        return ret;
    }

    /**
     * 返回当前子树的根节点
     *
     * @param node
     * @return
     */
    protected Node removeMin(@NonNull Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            size--;
            node.right = node.parent = null;
            return rightNode;
        }
        Node left = removeMin(node.left);
        node.left = left;
        if (left != null) {
            left.parent = node;
        }
        return node;
    }

    @Override
    public E removeMax() {
        if (size == 0) return null;
        E ret = maximum();
        root = removeMax(root);
        validate();
        return ret;
    }

    protected Node removeMax(@NonNull Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = node.parent = null;
            size--;
            return leftNode;
        }
        Node right = removeMax(node.right);
        node.right = right;
        if (right != null) {
            right.parent = node;
        }
        return node;
    }

    @Override
    public E predecessor(E e) {
        if (e == null) return null;
        Node predecessor = predecessor(root, e);
        return predecessor == null ? null : predecessor.e;
    }

    /**
     * just for test
     *
     * @param e
     * @return
     */
    public E superPredecessor(E e) {
        return super.predecessor(e);
    }

    private Node predecessor(Node node, E e) {
        if (node == null) return null;
        if (compare(e, node.e) > 0) {
            return predecessor(node.right, e);
        } else if (compare(e, node.e) < 0) {
            return predecessor(node.left, e);
        } else {
            //1.当该节点有左子树时,其中序前序为左子树的最大节点
            if (node.left != null) {
                return (Node) maximum(node.left);
            }
            /*
            2.当该节点无左子树时,找到祖先节点中第一个向右分叉的节点
            2.1 当该节点为右孩子时,前驱为父节点
            2.2 当该节点为左孩子时,需要沿着父节点链找到第一个有右孩子的节点
            另一种实现方式:
            Node ret = node.parent;
            while (ret != null && node == ret.left) {
                node = ret;
                ret = ret.parent;
            }
            return ret;
            */
            Node p = node.parent;
            Node child = node;
            while (p != null) {
                if (p.right == child) {
                    return p;
                }
                child = p;
                p = p.parent;
            }
            return null;
        }
    }

    @Override
    public E successor(E e) {
        if (e == null) return null;
        Node successor = successor(root, e);
        return successor == null ? null : successor.e;
    }

    /**
     * just for test
     *
     * @param e
     * @return
     */
    public E superSuccessor(E e) {
        return super.successor(e);
    }

    private Node successor(Node node, E e) {
        if (node == null) return null;
        if (compare(e, node.e) > 0) {
            return successor(node.right, e);
        } else if (compare(e, node.e) < 0) {
            return successor(node.left, e);
        } else {
            if (node.right != null) {
                return (Node) minimum(node.right);
            }
            Node ret = node.parent;
            while (ret != null && node == ret.right) {
                node = ret;
                ret = ret.parent;
            }
            return ret;
        }
    }

    private class Node implements BSTSet.Node<E> {
        //存储父节点引用,在需要从下到上遍历时有用,大部分情况下都不需要,可通过递归或保存遍历路径解决
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
            return Arrays.toString(new Object[]{left == null ? null : left.e, e, right == null ? null : right.e});
        }
    }
}
