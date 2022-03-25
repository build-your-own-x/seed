package com.techzealot.graph.dfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 递归实现深度优先遍历
 */
public class StackDfs {
    private Graph G;
    private boolean[] visited;

    private List<Integer> pre = new ArrayList<>();


    public StackDfs(Graph G) {
        this.G = G;
        visited = new boolean[G.V()];
        dfs(0);
        //处理非连通图
        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        //放入时标记,避免重复放入
        visited[v] = true;
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            pre.add(pop);
            for (int w : G.adjacent(pop)) {
                if (!visited[w]) {
                    stack.push(w);
                    visited[w] = true;
                }
            }
        }
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        StackDfs dfs = new StackDfs(G);
        System.out.println(dfs.pre());
    }

}
