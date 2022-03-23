package com.techzealot.graph;

import java.util.Collection;

public interface Graph {
     default void validateVertex(int v){
         if (v < 0 || v >= V())
             throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V() - 1));
     }

    int V();

    int E();

    boolean hasEdge(int v, int w);

    int degree(int v);

    Collection<Integer> adjacent(int v);
}
