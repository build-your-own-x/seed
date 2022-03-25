package com.techzealot.graph.dfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 单源路径问题,可获取遍历起点到任意一点的一条路径
 */
public class SingleSourcePath {
    private final int s;
    private final Graph G;
    /**
     * 优化:可以使用pre[x]==-1,判断是否访问过,可以省略visited数组
     */
    private final int[] pre;


    public SingleSourcePath(Graph G, int s) {
        this.G = G;
        G.validateVertex(s);
        this.s = s;
        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        //可以认为根节点的父节点为其本身
        dfs(s, s);
    }

    private void dfs(int v, int parent) {
        pre[v] = parent;
        for (int w : G.adjacent(v)) {
            if (pre[w] == -1) {
                dfs(w, v);
            }
        }
    }

    public boolean isConnectedTo(int t) {
        G.validateVertex(t);
        return pre[t] != -1;
    }

    public Iterable<Integer> path(int t) {
        List<Integer> path = new ArrayList<>();
        if (!isConnectedTo(t)) {
            return path;
        }
        int current = t;
        //当current==s时,需要将起点也加入,为保证逻辑一致性,可以看出设定起点的父节点为自身比较合理
        do {
            path.add(current);
            current = pre[current];
        }
        while (current != s);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        SingleSourcePath dfs = new SingleSourcePath(G, 0);
        System.out.println(dfs.isConnectedTo(6));
        System.out.println("0->6:" + dfs.path(6));
        System.out.println(dfs.isConnectedTo(5));
        System.out.println("0->5:" + dfs.path(5));
    }

}
