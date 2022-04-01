package com.techzealot.graph.bfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * 联通分量
 */
public class ConnectiveComponent {
    private Graph G;
    private int[] ccids;
    private int ccidCount = 0;


    public ConnectiveComponent(Graph G) {
        this.G = G;
        ccids = new int[G.V()];
        Arrays.fill(ccids, -1);
        for (int v = 0; v < G.V(); v++) {
            if (ccids[v] == -1) {
                bfs(v, ccidCount);
                ccidCount++;
            }
        }
    }

    private void bfs(int v, int ccid) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(v);
        ccids[v] = ccid;
        while (!queue.isEmpty()) {
            Integer head = queue.poll();
            //出队时收集数据
            for (int w : G.adjacent(head)) {
                if (ccids[w] == -1) {
                    queue.offer(w);
                    //入队时进行标记，避免重复入队
                    ccids[w] = ccid;
                }
            }
        }
    }

    public int count() {
        return ccidCount;
    }

    public boolean isConnected(int v, int w) {
        G.validateVertex(v);
        G.validateVertex(w);
        return ccids[v] == ccids[w];
    }

    public List<Integer>[] components() {
        List<Integer>[] components = new ArrayList[ccidCount];
        for (int i = 0; i < ccidCount; i++) {
            components[i] = new ArrayList<>();
        }
        for (int i = 0; i < G.V(); i++) {
            components[ccids[i]].add(i);
        }
        return components;
    }


    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        ConnectiveComponent bfs = new ConnectiveComponent(G);
        System.out.println(bfs.count());
        System.out.println(bfs.isConnected(0,6));
        System.out.println(bfs.isConnected(0,5));
        System.out.println(Arrays.toString(bfs.components()));

    }

}
