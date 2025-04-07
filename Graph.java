package com.airtrafficcontrol;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int numAirports;
    private final List<int[]> edges;

    public Graph(int numAirports) {
        this.numAirports = numAirports;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int src, int dest, int weight) {
        edges.add(new int[]{src, dest, weight});
    }

    public List<int[]> getEdges() {
        return edges;
    }

    public int getNumAirports() {
        return numAirports;
    }
}
