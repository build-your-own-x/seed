package com.techzealot.graph.dfs;

import com.techzealot.graph.AdjTreeSet;
import com.techzealot.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 联通分量问题
 */
public class ConnectiveComponent {
    private Graph G;
    /**
     * 可以借用该数组标记联通分量分组,优于使用List[]
     * 1.联通分量个数
     * 2.所属联通分量,可以快速判断两个顶点是否在同一个联通分量即是否连通
     * 3.获取每个联通分量
     */
    private int[] visited;

    private int cccount = 0;


    public ConnectiveComponent(Graph G) {
        this.G = G;
        visited = new int[G.V()];
        Arrays.fill(visited, -1);
        //处理非连通图
        for (int v = 0; v < G.V(); v++) {
            if (visited[v] == -1) {
                dfs(v, cccount);
                cccount++;
            }
        }
    }

    private void dfs(int v, int ccid) {
        visited[v] = ccid;
        for (int w : G.adjacent(v)) {
            if (visited[w] == -1) {
                dfs(w, ccid);
            }
        }
    }

    /**
     * 联通分量个数
     * @return
     */
    public int count() {
        System.out.println(Arrays.toString(visited));
        return cccount;
    }

    /**
     * 判断两个顶点是否连通
     * @param v
     * @param w
     * @return
     */
    public boolean isConnective(int v, int w) {
        G.validateVertex(v);
        G.validateVertex(w);
        return visited[v] == visited[w];
    }

    /**
     * 获取联通分量列表
     * @return
     */
    public List<Integer>[] components() {
        List<Integer>[] res = new ArrayList[cccount];
        for (int i = 0; i < cccount; i++) {
            res[i] = new ArrayList<>();
        }
        for (int v = 0; v < G.V(); v++) {
            res[visited[v]].add(v);
        }
        return res;
    }

    public static void main(String[] args) {
        Graph G = new AdjTreeSet("/graph.txt");
        ConnectiveComponent dfs = new ConnectiveComponent(G);
        System.out.println(dfs.count());
        System.out.println(dfs.isConnective(1, 3));
        System.out.println(dfs.isConnective(1, 5));
        System.out.println(Arrays.toString(dfs.components()));
    }

}
