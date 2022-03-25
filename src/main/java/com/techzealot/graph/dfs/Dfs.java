package com.techzealot.graph.dfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归实现深度优先遍历
 */
public class Dfs {
    private Graph G;
    private boolean[] visited;

    private List<Integer> pre = new ArrayList<>();
    private List<Integer> post = new ArrayList<>();


    public Dfs(Graph G) {
        this.G = G;
        visited = new boolean[G.V()];
        //处理非连通图
        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        visited[v] = true;
        pre.add(v);
        for (int w : G.adjacent(v)) {
            if (!visited[w]) {
                dfs(w);
            }
        }
        post.add(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        Dfs dfs = new Dfs(G);
        System.out.println(dfs.pre());
        System.out.println(dfs.post());
    }

}
