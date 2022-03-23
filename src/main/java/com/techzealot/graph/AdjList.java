package com.techzealot.graph;

import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class AdjList implements Graph{
    private int V;
    private int E;
    private LinkedList<Integer> [] adj;

    public AdjList(String fileName) {
        InputStream is = this.getClass().getResourceAsStream(fileName);
        try (Scanner scanner = new Scanner(is)) {
            V = scanner.nextInt();
            if (V < 0) {
                throw new IllegalArgumentException("V must be non-negative");
            }
            E = scanner.nextInt();
            if (E < 0) {
                throw new IllegalArgumentException("E must be non-negative");
            }
            adj = new LinkedList[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new LinkedList<>();
            }
            for (int i = 0; i < E; i++) {
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);
                if (a == b) {
                    throw new IllegalArgumentException("Self loop is detected");
                }
                if (adj[a].contains(b)) {
                    throw new IllegalArgumentException("Parallel edges are detected");
                }
                adj[a].add(b);
                adj[b].add(a);
            }
        }
    }
    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v].contains(w);
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public Collection<Integer> adjacent(int v) {
        validateVertex(v);
        return adj[v];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d\n", V, E));
        for (int v = 0; v < V; v++) {
            sb.append(String.format("%d: ", v));
            for (int w: adj[v]) {
                sb.append(String.format("%d ", w));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjList am = new AdjList("/graph.txt");
        System.out.println(am);
    }
}
