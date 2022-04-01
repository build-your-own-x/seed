package com.techzealot.graph.bfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 环检测
 */
public class CycleDetection {
    private Graph G;
    private boolean[] visited;
    private boolean hasCycle = false;

    public CycleDetection(Graph G) {
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
            int count = 0;
            for (int w : G.adjacent(head)) {
                if (!visited[w]) {
                    queue.offer(w);
                    //入队时进行标记，避免重复入队
                    visited[w] = true;
                } else {
                    count++;
                    //如果当前节点有两个以上已遍历邻接点，说明有环
                    //也可以通过int[] pre记录该节点前一个节点,通过类似DFS方式判定
                    if (count > 1) {
                        hasCycle = true;
                        break;
                    }
                }
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        CycleDetection bfs = new CycleDetection(G);
        System.out.println(bfs.hasCycle());
        Graph G1 = new AdjTreeSet("/graph-cycle.txt");
        CycleDetection bfs1 = new CycleDetection(G1);
        System.out.println(bfs1.hasCycle());
    }

}
