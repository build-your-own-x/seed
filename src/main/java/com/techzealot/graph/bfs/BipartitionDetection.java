package com.techzealot.graph.bfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * 二分图检测
 */
public class BipartitionDetection {
    public static final int UNDEFINED = -1;
    public static final int BLUE = 0;
    public static final int GREEN = 1;
    private Graph G;
    //0 blue 1 green
    private int[] colors;

    private boolean bipartite = true;

    public BipartitionDetection(Graph G) {
        this.G = G;
        colors = new int[G.V()];
        Arrays.fill(colors, UNDEFINED);
        //处理非连通图
        for (int v = 0; v < G.V(); v++) {
            if (colors[v] == -1) {
                if (!bfs(v)) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        BipartitionDetection bfs = new BipartitionDetection(G);
        System.out.println(bfs.bipartite());
        System.out.println(bfs.blues());
        System.out.println(bfs.greens());

        Graph G1 = new AdjTreeSet("/graph-bipartite.txt");
        BipartitionDetection bfs1 = new BipartitionDetection(G1);
        System.out.println(bfs1.bipartite());
        System.out.println(bfs1.blues());
        System.out.println(bfs1.greens());
    }

    private boolean bfs(int v) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(v);
        colors[v] = BLUE;
        while (!queue.isEmpty()) {
            Integer head = queue.poll();
            //出队时收集数据
            for (int w : G.adjacent(head)) {
                if (colors[w] == -1) {
                    queue.offer(w);
                    //入队时进行标记，避免重复入队
                    colors[w] = (BLUE + GREEN) - colors[head];
                } else if (colors[w] == colors[head]) {
                    bipartite = false;
                    return false;
                }
            }
        }
        return true;
    }

    public boolean bipartite() {
        return bipartite;
    }

    public List<Integer> blues() {
        return getColors(BLUE);
    }

    public List<Integer> greens() {
        return getColors(GREEN);
    }

    private List<Integer> getColors(int color) {
        if (!bipartite) {
            return Collections.emptyList();
        }
        List<Integer> selected = new ArrayList<>();
        for (int i = 0; i < G.V(); i++) {
            if (colors[i] == color) {
                selected.add(i);
            }
        }
        return selected;
    }

}
