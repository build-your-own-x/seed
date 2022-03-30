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
 * 递归实现深度优先遍历
 */
public class SingleSourcePath {

    private Graph G;
    private final int s;
    private int[] pre;


    public SingleSourcePath(Graph G, int s) {
        this.G = G;
        this.s = s;
        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        //无需处理其他联通分量
        bfs(s);
    }

    private void bfs(int v) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(v);
        pre[v] = v;
        while (!queue.isEmpty()) {
            Integer head = queue.poll();
            for (int w : G.adjacent(head)) {
                if (pre[w] == -1) {
                    queue.offer(w);
                    pre[w] = head;
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
        if(!isConnectedTo(t)){
            return path;
        }
        int current = t;
        while (current != s){
            path.add(current);
            current = pre[current];
        }
        path.add(s);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        SingleSourcePath bfs = new SingleSourcePath(G,0);
        System.out.println(bfs.isConnectedTo(6));
        System.out.println(bfs.path(6));
        System.out.println(bfs.isConnectedTo(5));
        System.out.println(bfs.path(5));
    }

}
