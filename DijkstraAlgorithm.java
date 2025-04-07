package com.airtrafficcontrol;

import java.util.*;

public class DijkstraAlgorithm {
    public List<int[]> getShortestPath(Graph graph, int start) {
        int numAirports = graph.getNumAirports();
        int[] distance = new int[numAirports];
        int[] prev = new int[numAirports];
        boolean[] visited = new boolean[numAirports];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        distance[start] = 0;

        for (int i = 0; i < numAirports - 1; i++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            for (int[] edge : graph.getEdges()) {
                int src = edge[0], dest = edge[1], weight = edge[2];
                if (src == u && !visited[dest] && distance[u] + weight < distance[dest]) {
                    distance[dest] = distance[u] + weight;
                    prev[dest] = u;
                }
            }
        }

        List<int[]> path = new ArrayList<>();
        for (int i = 1; i < numAirports; i++) {
            if (prev[i] != -1) path.add(new int[]{prev[i], i});
        }
        return path;
    }

    private int minDistance(int[] distance, boolean[] visited) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < distance.length; v++) {
            if (!visited[v] && distance[v] < min) {
                min = distance[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}
