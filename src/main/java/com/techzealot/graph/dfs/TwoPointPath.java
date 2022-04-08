package com.techzealot.graph.dfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 两点路径问题
 */
public class TwoPointPath {

    private final Graph G;

    private final int s;

    private final int t;
    /**
     * 优化:可以使用pre[x]==-1,判断是否访问过,可以省略visited数组
     */
    private final int[] pre;

    public TwoPointPath(Graph G, int s, int t) {
        this.G = G;
        G.validateVertex(s);
        this.s = s;
        G.validateVertex(t);
        this.t = t;
        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        //可以认为根节点的父节点为其本身
        dfs(s, s);
        //查看节点遍历情况
        System.out.println(Arrays.toString(pre));
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        TwoPointPath path1 = new TwoPointPath(G, 0, 6);
        System.out.println(path1.isConnected());
        System.out.println("0->6:" + path1.path());
        TwoPointPath path2 = new TwoPointPath(G, 0, 5);
        System.out.println(path2.isConnected());
        System.out.println("0->5:" + path2.path());
    }

    private boolean dfs(int v, int parent) {
        pre[v] = parent;
        //如果只需要求两个点的路径,可以提前退出,不必遍历所有联通节点
        if (v == t) {
            return true;
        }
        for (int w : G.adjacent(v)) {
            if (pre[w] == -1) {
                if (dfs(w, v)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isConnected() {
        return pre[t] != -1;
    }

    public Iterable<Integer> path() {
        List<Integer> path = new ArrayList<>();
        if (!isConnected()) {
            return path;
        }
        int current = t;
        //当current==s时,需要将起点也加入,为保证逻辑一致性,可以看出设定起点的父节点为自身比较合理
        while (current != s) {
            path.add(current);
            current = pre[current];
        }
        path.add(s);
        Collections.reverse(path);
        return path;
    }

}
