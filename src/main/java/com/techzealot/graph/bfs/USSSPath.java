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
 * unweighted single source shortest path
 * 可用于求解无权图的最短路径
 */
public class USSSPath {
    private Graph G;
    private final int s;
    private int[] pre;
    private int[] dists;


    public USSSPath(Graph G, int s) {
        this.G = G;
        this.s = s;
        pre = new int[G.V()];
        dists = new int[G.V()];
        Arrays.fill(pre, -1);
        Arrays.fill(dists, -1);
        //无需处理其他联通分量
        bfs(s);
        System.out.println(Arrays.toString(dists));
    }

    private void bfs(int s) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(s);
        pre[s] = s;
        dists[s] = 0;
        while (!queue.isEmpty()) {
            Integer head = queue.poll();
            for (int w : G.adjacent(head)) {
                if (pre[w] == -1) {
                    queue.offer(w);
                    pre[w] = head;
                    dists[w] = dists[head] + 1;
                }
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
        while (current != s) {
            path.add(current);
            current = pre[current];
        }
        path.add(s);
        Collections.reverse(path);
        return path;
    }

    public int distance(int t) {
        G.validateVertex(t);
        return dists[t];
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        USSSPath bfs = new USSSPath(G, 0);
        System.out.println(bfs.isConnectedTo(6));
        System.out.println(bfs.path(6));
        System.out.println(bfs.distance(6));
        System.out.println(bfs.isConnectedTo(5));
        System.out.println(bfs.path(5));
        System.out.println(bfs.distance(5));
    }

}
