package com.airtrafficcontrol;

import java.util.*;

public class PrimAlgorithm {
    public List<int[]> getMST(Graph graph) {
        int numAirports = graph.getNumAirports();
        boolean[] visited = new boolean[numAirports];
        int[] parent = new int[numAirports];
        int[] key = new int[numAirports];
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < numAirports - 1; i++) {
            int u = minKey(key, visited);
            visited[u] = true;

            for (int[] edge : graph.getEdges()) {
                int src = edge[0], dest = edge[1], weight = edge[2];
                if (src == u && !visited[dest] && weight < key[dest]) {
                    key[dest] = weight;
                    parent[dest] = u;
                }
            }
        }

        List<int[]> mst = new ArrayList<>();
        for (int i = 1; i < numAirports; i++) {
            mst.add(new int[]{parent[i], i});
        }
        return mst;
    }

    private int minKey(int[] key, boolean[] visited) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < key.length; v++) {
            if (!visited[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}
