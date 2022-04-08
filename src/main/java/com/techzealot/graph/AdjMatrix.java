package com.techzealot.graph;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdjMatrix implements Graph {
    private int V;
    private int E;
    private int[][] adj;

    public AdjMatrix(String fileName) {
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
            adj = new int[V][V];
            for (int i = 0; i < E; i++) {
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);
                if (a == b) {
                    throw new IllegalArgumentException("Self loop is detected");
                }
                if (adj[a][b] == 1) {
                    throw new IllegalArgumentException("Parallel edges are detected");
                }
                adj[a][b] = 1;
                adj[b][a] = 1;
            }
        }
    }

    public static void main(String[] args) {
        AdjMatrix am = new AdjMatrix("/graph.txt");
        System.out.println(am);
    }

    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        return E;
    }

    @Override
    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] == 1;
    }

    @Override
    public int degree(int v) {
        validateVertex(v);
        int degree = 0;
        for (int i = 0; i < V; i++) {
            degree += adj[v][i];
        }
        return degree;
    }

    @Override
    public Collection<Integer> adjacent(int v) {
        validateVertex(v);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (adj[v][i] == 1) {
                list.add(i);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d\n", V, E));
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                sb.append(String.format("%d ", adj[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
