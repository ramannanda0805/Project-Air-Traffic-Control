package com.airtrafficcontrol;

import java.util.*;

public class DijkstraAlgorithm {

    public static List<Graph.Edge> getShortestPaths(Graph graph, String startCity) {
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<Graph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        Set<String> visited = new HashSet<>();

        for (String city : graph.getAllCities()) {
            distance.put(city, Integer.MAX_VALUE);
        }
        distance.put(startCity, 0);
        pq.add(new Graph.Edge(startCity, startCity, 0));

        while (!pq.isEmpty()) {
            Graph.Edge current = pq.poll();
            if (visited.contains(current.destination)) continue;
            visited.add(current.destination);

            for (Graph.Edge neighbor : graph.getEdges(current.destination)) {
                int newDist = distance.get(current.destination) + neighbor.weight;
                if (newDist < distance.get(neighbor.destination)) {
                    distance.put(neighbor.destination, newDist);
                    prev.put(neighbor.destination, current.destination);
                    pq.add(new Graph.Edge(current.destination, neighbor.destination, newDist));
                }
            }
        }

        // Reconstruct paths as edge list (just an example: path to all reachable nodes)
        List<Graph.Edge> result = new ArrayList<>();
        for (String dest : graph.getAllCities()) {
            String source = prev.get(dest);
            if (source != null) {
                int weight = distance.get(dest) - distance.get(source);
                result.add(new Graph.Edge(source, dest, weight));
            }
        }

        return result;
    }
}
