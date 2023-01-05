package com.techzealot.collection.tree.bst.avl;

import com.techzealot.collection.tree.bst.AbstractBSTMap;
import com.techzealot.collection.tree.bst.BSTMap;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 适用于查询较多,更新和删除较少;查询更新删除无偏重时使用红黑树性能更高
 * 优化:
 * 1.回溯到节点高度不变即可
 * 2.删除时使用更高的子树中的节点取代删除节点会少减少旋转次数
 */
public class AVLTree<K, V> extends AbstractBSTMap<K, V> {

    private Node root;
    private int size;

    @Override
    protected Node root() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(Object key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    private Node getNode(Object key) {
        return (Node) super.getEntry(key);
    }


    /**
     * 1.添加一个节点后可能会导致所有祖先节点失衡
     * 2.只要让高度最低的祖先节点恢复平衡，整棵树就会恢复平衡
     * 3.仅需进行四种旋转中的一种旋转
     *
     * @param key
     * @param value
     * @return
     */
    @Override

    public V put(@NonNull K key, V value) {
        Object[] oldValue = new Object[]{null};
        root = put(root, key, value, oldValue);
        validate();
        @SuppressWarnings("unchecked")
        V old = (V) oldValue[0];
        return old;
    }

    private void validate() {
        if (!isBalanced()) {
            throw new IllegalStateException("not balanced");
        }
        if (!isBST()) {
            throw new IllegalStateException("not bst");
        }
    }

    private Node put(Node node, K key, V value, Object[] oldValue) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = compare(key, node.key);
        if (cmp == 0) {
            oldValue[0] = node.value;
            node.value = value;
        } else if (cmp < 0) {
            node.left = put(node.left, key, value, oldValue);
        } else {
            node.right = put(node.right, key, value, oldValue);
        }
        //更新节点的高度
        updateNodeHeight(node);
        //维护平衡性
        return balanceNode(node);
    }

    /**
     * 六种失衡类型:
     * 添加删除:LL,LR,RR,RL
     * 删除:L,R 子节点平衡因子=0时
     *
     * @param node
     * @return 返回平衡后根节点
     */
    private Node balanceNode(Node node) {
        if (node == null) return null;
        int balanceFactor = nodeBalanceFactor(node);
        //优化:大部分节点是平衡的,此判断写在前面更优
        if (balanceFactor == -1 || balanceFactor == 0 || balanceFactor == 1) {
            return node;
        }
        //分4种情况讨论
        //新插入节点在当前节点左孩子的左子树(LL),左孩子平衡因子=0只会在删除节点时出现
        if (balanceFactor > 1 && nodeBalanceFactor(node.left) >= 0) {
            return rotateRight(node);
        }
        //新插入节点在当前节点左孩子的右子树(LR)
        //先左旋即可转化为LL的形状
        if (balanceFactor > 1 && nodeBalanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        //新插入节点在当前节点右孩子的右子树(RR),右孩子平衡因子=0只会在删除节点时出现
        if (balanceFactor < -1 && nodeBalanceFactor(node.right) <= 0) {
            return rotateLeft(node);
        }
        //新插入节点在当前节点右孩子的左子树(RL)
        //先右旋即可转化为RR的形状
        if (balanceFactor < -1 && nodeBalanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    /**
     * 右旋转y：以y的左孩子x为圆心,旋转y取代T3，同时让T3成为y的左孩子，最后让x取代y的位置成为子树的根节点
     * balanceFactor > 1 && nodeBalanceFactor(node.left) >= 0
     * 对不平衡的节点y进行右旋转操作必须满足以下形状,由于balanceFactor > 1,则x,y,z均不为空
     * 对节点y进行右旋转，返回旋转后新的根节点x
     * ---       y                              x
     * ---      / \                           /   \
     * ---     x   T4     向右旋转 (y)        z     y
     * ---    / \       - - - - - - - ->    / \   / \
     * ---   z   T3                       T1  T2 T3 T4
     * ---  / \
     * ---T1   T2
     *
     * @param y 不平衡的节点
     * @return 以y为根节点的子树右旋转后新的根节点
     */
    private Node rotateRight(@NonNull Node y) {
        Node x = y.left;
        Objects.requireNonNull(x);
        y.left = x.right;
        x.right = y;
        //update height
        updateNodeHeight(y);
        updateNodeHeight(x);
        return x;
    }

    /**
     * 左旋转y：以y的右孩子x为圆心,旋转y取代T2，同时让T2成为y的右孩子，最后让x取代y的位置成为子树的根节点
     * balanceFactor < -1 && nodeBalanceFactor(node.left) <= 0
     * 对不平衡的节点y进行右旋转操作必须满足以下形状,由于balanceFactor < -1,则x,y,z均不为空
     * 对节点y进行左旋转，返回旋转后新的根节点x
     * ---   y                             x
     * --- /  \                          /   \
     * ---T1   x      向左旋转 (y)       y     z
     * ---    / \   - - - - - - - ->   / \   / \
     * ---  T2  z                     T1 T2 T3 T4
     * ---     / \
     * ---    T3 T4
     *
     * @param y 不平衡的节点
     * @return 以y为根节点的子树左旋转后新的根节点
     */
    private Node rotateLeft(@NonNull Node y) {
        Node x = y.right;
        Objects.requireNonNull(x);
        y.right = x.left;
        x.left = y;
        //update height
        updateNodeHeight(y);
        updateNodeHeight(x);
        return x;
    }

    /**
     * 借助二分搜索树中序遍历是递增的性质
     *
     * @return
     */
    public boolean isBST() {
        if (size <= 1) return true;
        List<K> items = new ArrayList<>();
        inOrder(root, items::add);
        int s = items.size();
        for (int i = 1; i < s; i++) {
            if (compare(items.get(i - 1), items.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private void inOrder(Node node, Consumer<K> action) {
        if (node == null) return;
        inOrder(node.left, action);
        action.accept(node.key);
        inOrder(node.right, action);
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        if (node == null) return true;
        int balanceFactor = nodeBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }


    /**
     * 1.删除可能会导致父节点或祖先节点失衡(只有一个节点会失衡)
     * 2.恢复平衡后可能会导致更高层祖先节点失衡(最多需要O(logn)次调整)
     *
     * @param key
     * @return
     * @throws NullPointerException if key is null
     * @throws ClassCastException   if key can not be compared with the keys in the map
     */
    @Override
    public V remove(@NonNull Object key) {
        Object[] value = new Object[]{null};
        root = remove(root, key, value);
        validate();
        @SuppressWarnings("unchecked")
        V v = (V) value[0];
        return v;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public void removeMin() {

    }

    @Override
    public void removeMax() {

    }

    private Node remove(Node node, Object key, Object[] value) {
        if (node == null) return null;
        Node retNode = node;
        int cmp = compare(key, node.key);
        if (cmp > 0) {
            node.right = remove(node.right, key, value);
        } else if (cmp < 0) {
            node.left = remove(node.left, key, value);
        } else {
            value[0] = node.value;
            if (node.left == null) {
                retNode = node.right;
                size--;
                node.right = null;
            } else if (node.right == null) {
                retNode = node.left;
                size--;
                node.left = null;
            } else {
                //优化:优先使用更高的子树中的节点替换删除节点可以减少旋转次数
                if (nodeHeight(node.left) > nodeHeight(node.right)) {
                    Node predecessor = Objects.requireNonNull(maximum(node.left));
                    predecessor.left = remove(node.left, predecessor.key, new Object[]{null});
                    predecessor.right = node.right;
                    node.left = node.right = null;
                    retNode = predecessor;
                } else {
                    Node successor = Objects.requireNonNull(minimum(node.right));
                    successor.left = node.left;
                    successor.right = remove(node.right, successor.key, new Object[]{null});
                    node.left = node.right = null;
                    retNode = successor;
                }
            }
        }
        updateNodeHeight(retNode);
        return balanceNode(retNode);
    }

    private Node maximum(Node node) {
        if (node == null) return null;
        if (node.right == null) {
            return node;
        } else {
            return maximum(node.right);
        }
    }

    private Node minimum(Node node) {
        if (node == null) return null;
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    private int nodeHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private void updateNodeHeight(Node node) {
        if (node == null) return;
        node.height = 1 + Math.max(nodeHeight(node.left), nodeHeight(node.right));
    }

    private int nodeBalanceFactor(Node node) {
        return node == null ? 0 : nodeHeight(node.left) - nodeHeight(node.right);
    }

    private class Node implements BSTMap.Entry<K, V> {
        Node left;
        K key;
        V value;
        Node right;
        int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            //新增节点为叶子结点高度为1
            this.height = 1;
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
