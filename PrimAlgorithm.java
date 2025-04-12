package com.airtrafficcontrol;

import java.util.*;

public class PrimAlgorithm {

    public static List<Graph.Edge> primMST(Graph graph, String startCity) {
        List<Graph.Edge> mst = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Graph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        visited.add(startCity);
        pq.addAll(graph.getEdges(startCity));

        while (!pq.isEmpty()) {
            Graph.Edge edge = pq.poll();
            if (!visited.contains(edge.destination)) {
                mst.add(edge);
                visited.add(edge.destination);
                for (Graph.Edge next : graph.getEdges(edge.destination)) {
                    if (!visited.contains(next.destination)) {
                        pq.add(next);
                    }
                }
            }
        }

        return mst;
    }
}
