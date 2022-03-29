package com.techzealot.graph.dfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 递归实现深度优先遍历
 */
public class BipartitionDetection {
    private Graph G;
    //可借用colors数组表达访问信息，省去该数组
    private boolean[] visited;
    /**
     * -1 未染色 0 蓝色 1 绿色
     */
    private int[] colors;

    private boolean bipartite = true;

    private List<Integer> blues = new ArrayList<>();

    private List<Integer> greens = new ArrayList<>();


    public BipartitionDetection(Graph G) {
        this.G = G;
        visited = new boolean[G.V()];
        colors = new int[G.V()];
        Arrays.fill(colors, -1);
        //处理非连通图
        for (int v = 0; v < G.V(); v++) {
            if (!visited[v]) {
                if (!dfs(v, 0)) {
                    bipartite = false;
                    break;
                }
            }
        }
        if (bipartite) {
            for (int i = 0; i < colors.length; i++) {
                if (colors[i] == 0) {
                    blues.add(i);
                } else {
                    greens.add(i);
                }
            }
        }
    }

    private boolean dfs(int v, int color) {
        visited[v] = true;
        colors[v] = color;
        for (int w : G.adjacent(v)) {
            if (!visited[w]) {
                if (!dfs(w, 1 - color)) {
                    return false;
                }
            } else if (colors[w] == colors[v]) {
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite() {
        return bipartite;
    }

    public List<Integer> blues() {
        return blues;
    }

    public List<Integer> greens() {
        return greens;
    }


    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        BipartitionDetection dfs = new BipartitionDetection(G);
        System.out.println(dfs.isBipartite());
        System.out.println("blues:"+ dfs.blues());
        System.out.println("greens:"+ dfs.greens());
        Graph G1 = new AdjTreeSet("/graph-bipartite.txt");
        BipartitionDetection dfs1 = new BipartitionDetection(G1);
        System.out.println(dfs1.isBipartite());
        System.out.println("blues:"+ dfs1.blues());
        System.out.println("greens:"+ dfs1.greens());
    }

}
