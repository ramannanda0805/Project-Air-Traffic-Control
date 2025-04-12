package com.airtrafficcontrol;

import java.util.*;

public class Graph {

    public static class Edge {
        public String source;
        public String destination;
        public int weight;

        public Edge(String source, String destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();

    public void addCity(String city) {
        adjacencyList.putIfAbsent(city, new ArrayList<>());
    }

    public void connectCities(String a, String b, int weight) {
        addCity(a);
        addCity(b);
        adjacencyList.get(a).add(new Edge(a, b, weight));
        adjacencyList.get(b).add(new Edge(b, a, weight)); // Assuming undirected graph
    }

    public List<Edge> getEdges(String city) {
        return adjacencyList.getOrDefault(city, new ArrayList<>());
    }

    public Map<String, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public Set<String> getAllCities() {
        return adjacencyList.keySet();
    }
}
