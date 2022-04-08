package com.techzealot.graph.bfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 队列实现广度优先遍历(层序遍历)
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

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        Bfs bfs = new Bfs(G);
        System.out.println(bfs.getOrder());
    }

    /**
     * 深度优先与广度优先的逻辑出了容器特性不同，其他完全一致
     *
     * @param v
     */
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

}
