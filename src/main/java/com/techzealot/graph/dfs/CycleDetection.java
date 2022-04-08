package com.techzealot.graph.dfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

/**
 * 无向图环检测 环:当前节点有两个已遍历邻接点
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
                if (dfs(v, v)) {
                    hasCycle = true;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        CycleDetection dfs = new CycleDetection(G);
        System.out.println(dfs.hasCycle());
        Graph G1 = new AdjTreeSet("/graph-cycle.txt");
        CycleDetection dfs1 = new CycleDetection(G1);
        System.out.println(dfs1.hasCycle());
    }

    private boolean dfs(int v, int parent) {
        visited[v] = true;
        for (int w : G.adjacent(v)) {
            if (!visited[w]) {
                if (dfs(w, v)) {
                    return true;
                }
            } else if (w != parent) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

}
