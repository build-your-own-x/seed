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
 * 两点路径,即时返回
 */
public class TwoPointPath {

    private final int s;
    private final int t;
    private Graph G;
    private int[] pre;


    public TwoPointPath(Graph G, int s, int t) {
        this.G = G;
        this.s = s;
        this.t = t;
        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        //无需处理其他联通分量
        bfs(s, t);
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        TwoPointPath bfs = new TwoPointPath(G, 0, 6);
        System.out.println(bfs.isConnected());
        System.out.println(bfs.path());
        TwoPointPath bfs1 = new TwoPointPath(G, 0, 5);
        System.out.println(bfs1.isConnected());
        System.out.println(bfs1.path());
        TwoPointPath bfs2 = new TwoPointPath(G, 0, 0);
        System.out.println(bfs2.isConnected());
        System.out.println(bfs2.path());
    }

    private void bfs(int v, int t) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(v);
        pre[v] = v;
        if (v == t) {
            return;
        }
        while (!queue.isEmpty()) {
            Integer head = queue.poll();
            for (int w : G.adjacent(head)) {
                if (pre[w] == -1) {
                    queue.offer(w);
                    pre[w] = head;
                    if (w == t) {
                        break;
                    }
                }
            }
        }
    }

    public boolean isConnected() {
        G.validateVertex(t);
        return pre[t] != -1;
    }

    public Iterable<Integer> path() {
        List<Integer> path = new ArrayList<>();
        if (!isConnected()) {
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

}
