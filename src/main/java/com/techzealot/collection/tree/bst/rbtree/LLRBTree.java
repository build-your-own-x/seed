package com.techzealot.collection.tree.bst.rbtree;

import com.techzealot.collection.tree.bst.AbstractBSTMap;
import com.techzealot.collection.tree.bst.BSTMap;
import lombok.NonNull;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * 每个红黑树都有一个对应的2-3-4树，但是不一定有对应的2-3树(无法直接表示4节点)，但可以用2-3树组织相同的数据，并可以转化为一个左偏红黑树
 * 左偏红黑树:LLRB-23,等价于2-3树，无4节点
 * 比传统红黑树多两个限制:
 * 1.黑节点最多有一个红孩子
 * 2.红孩子必须为左孩子
 * <p>
 * 插入删除过程中需要保持的不变量:
 * 1.红色节点不相邻
 * 2.黑高相等
 * 红黑树有三种基本操作:左旋,右旋,颜色翻转
 * 1.左旋,右旋不会改变红黑树需要的不变量
 * 2.颜色翻转不会改变黑高相等但可能会产生红节点相邻的情况
 * <p>
 * 添加删除的核心思路：
 * 1.先确保添加或删除一个红节点
 * 2.通过回溯修复不符合要求的节点
 */
public class LLRBTree<K, V> extends AbstractBSTMap<K, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    @Override
    protected Node root() {
        return root;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    @Override
    public V get(@NonNull Object key) {
        Node p = get(root, key);
        return p == null ? null : p.value;
    }

    private Node get(Node node, Object key) {
        if (node == null) return null;
        int cmp = compare(node.key, key);
        if (cmp > 0) {
            return get(node.right, key);
        } else if (cmp < 0) {
            return get(node.left, key);
        } else {
            return node;
        }
    }

    @Override
    public V put(@NonNull K key, V value) {
        Object[] oldValue = new Object[]{null};
        root = put(root, key, value, oldValue);
        root.color = BLACK;
        return (V) oldValue[0];
    }

    private Node put(Node node, K key, V value, Object[] oldValue) {
        if (node == null) {
            return new Node(key, value);
        }
        int cmp = compare(node.key, key);
        if (cmp > 0) {
            node.right = put(node.right, key, value, oldValue);
        } else if (cmp < 0) {
            node.left = put(node.left, key, value, oldValue);
        } else {
            oldValue[0] = node.value;
            node.value = value;
        }
        return balance(node);
    }

    private Node balance(@NonNull Node h) {
        //fix-up any right-leaning links
        if (!isRed(h.left) && isRed(h.right)) {
            h = rotateLeft(h);
        }
        //isRed(n)==true==>n!=null,因此node.left.left不会NPE
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        updateSize(h);
        return h;
    }

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    /**
     * 核心思想:
     * 1.保证删除的节点是红色
     * 2.使用前驱或后继节点替换要删除节点
     * 手段:
     * 1.从上向下搜索时通过左移和右移红色保证经过路径上的节点都为临时4节点或3节点
     * 2.删除非叶子节点需要使用前驱和后继替换删除节点
     * 3.回溯时修复红色相邻情况
     *
     * @param key
     * @return
     */
    @Override
    public V remove(@NonNull Object key) {
        if (!containsKey(key)) return null;
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        Object[] oldValue = new Object[]{null};
        root = remove(root, key, oldValue);
        return (V) oldValue[0];
    }

    private Node remove(Node h, Object key, Object[] oldValue) {
        assert get(root, key) != null;
        if (compare(key, h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = remove(h.left, key, oldValue);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (compare(key, h.key) == 0 && h.right == null) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            if (compare(key, h.key) == 0) {
                oldValue[0] = h.value;
                Node x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                h.right = removeMin(h.right);
            } else {
                h.right = remove(h.right, key, oldValue);
            }
        }
        return balance(h);
    }

    private Node min(@NonNull Node node) {
        Node p = node;
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    /**
     * 删除最小节点的核心思想:
     * 向左传递红色(从上往下迭代时通过调整保证最左侧路径上的节点均为3、4节点,3、4节点可以轻松安全删除)
     * 1.保证删除节点为红节点
     * 2.将红色传递至左侧:向父节点借红色(rbb颜色翻转为brr)，再将右侧红色移动至左侧(左旋)，同时需要考虑修复右侧红色相邻的情况(右旋->颜色翻转)
     */
    @Override
    public void removeMin() {
        if (isEmpty()) throw new NoSuchElementException();
        //如果根节点的左右孩子都不是红色需要将根节点设为红色，以确保一定满足moveRedLeft大的前置条件
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = removeMin(root);
        //保证根节点为黑色
        if (!isEmpty()) {
            root.color = BLACK;
        }
        assert check();
    }

    private Node removeMin(Node h) {
        //删除最左节点
        if (h.left == null) return null;
        //下一个是2节点
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = removeMin(h.left);
        return balance(h);
    }

    /**
     * 将node.left或node.left.left变为红色
     * 此时左斜线必为rbb|rb[nil]组合
     * 反证法:若为bbb或bb[nil]则此时必不是第一次moveRedLeft,又知moveRedLeft之后node.left必为red,即下一次moveRedLeft时当前节点为red,矛盾
     *
     * @param h
     * @return
     */
    private Node moveRedLeft(Node h) {
        assert h != null;
        assert h.left != null;
        assert isRed(h) && !isRed(h.right) && !isRed(h.left) && !isRed(h.left.left);
        flipColors(h);
        //解决父节点传递红色造成的红色相邻问题
        //why right.left?
        //balance只能修复红节点右倾且左子节点不为红色的情况
        //右倾且左子结点红色必须在变换时自行处理
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    /**
     * 核心思想:从上到下调整根节点到最大值路径上全为3、4节点
     */
    @Override
    public void removeMax() {
        if (isEmpty()) throw new NoSuchElementException();
        //如果根节点的左右孩子都不是红色需要将根节点设为红色，以确保一定满足moveRedRight大的前置条件
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = removeMax(root);
        //保证根节点为黑色
        if (!isEmpty()) {
            root.color = BLACK;
        }
        assert check();
    }

    private Node removeMax(Node h) {
        //红色右移
        if (isRed(h.left)) {
            h = rotateRight(h);
        }
        if (h.right == null) return null;
        //下一个是2节点
        if (!isRed(h.right) && !isRed(h.right.left)) {
            h = moveRedRight(h);
        }
        h.right = removeMax(h.right);
        return balance(h);
    }

    private Node moveRedRight(Node h) {
        assert h != null && h.right != null;
        assert isRed(h) && !isRed(h.left) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        //why left.left?
        //balance只能修复一种情况,因此另一侧的红色相邻需要手动修复
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private boolean check() {
        return false;
    }

    private boolean isBst() {
        return false;
    }

    private boolean isBalanced() {
        return false;
    }

    /**
     * 等价于添加在3节点中间需要先左旋
     * 必须满足前置条件才能调用
     * --  node                     x
     * -- /   \     左旋转         /  \
     * --T1   x   --------->   node   T3
     * --    / \              /   \
     * --   T2 T3            T1   T2
     *
     * @param node
     * @return
     */
    private Node rotateLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        updateSize(node);
        return x;
    }

    /**
     * 右旋 等价于添加在3节点左侧需要先右旋
     * 必须满足前置条件才能调用
     * --    node                   x
     * --   /   \     右旋转       /  \
     * --  x    T2   ------->   y   node
     * -- / \                       /  \
     * --y  T1                     T1  T2
     *
     * @param node
     * @return
     */
    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        updateSize(node);
        return x;
    }

    /**
     * 等价于添加在3节点右侧需执行临时4节点上溢
     * 必须满足当前节点与子节点反色且两个子节点同色
     * 颜色翻转 保证黑高不变
     *
     * @param node
     */
    private void flipColors(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    private void updateSize(Node node) {
        if (node == null) return;
        node.size = size(node.left) + size(node.right) + 1;
    }

    private class Node implements BSTMap.Entry<K, V> {
        Node left;
        K key;
        V value;
        Node right;
        boolean color;
        int size;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            //由2-3树添加时先融合策略决定,增加红节点不改变黑高
            this.color = RED;
            this.size = 1;
        }

        @Override
        public Node left() {
            return left;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }

        @Override
        public Node right() {
            return right;
        }
    }
}
