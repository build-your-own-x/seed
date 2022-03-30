package com.techzealot.graph.bfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 递归实现深度优先遍历
 */
public class Bfs {
    private Graph G;
    private boolean[] visited;

    private List<Integer> order = new ArrayList<>();


    public Bfs(Graph G) {
        this.G = G;
        visited = new boolean[G.V()];
        //处理非连通图
        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                bfs(v);
            }
        }
    }

    private void bfs(int v) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(v);
        visited[v] = true;
        while (!queue.isEmpty()) {
            Integer head = queue.poll();
            //出队时收集数据
            order.add(head);
            for (int w : G.adjacent(head)) {
                if (!visited[w]) {
                    queue.offer(w);
                    //入队时进行标记，避免重复入队
                    visited[w] = true;
                }
            }
        }
    }

    public Iterable<Integer> getOrder() {
        return order;
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        Bfs dfs = new Bfs(G);
        System.out.println(dfs.getOrder());
    }

}
