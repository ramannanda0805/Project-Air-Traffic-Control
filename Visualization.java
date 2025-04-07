package com.airtrafficcontrol;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;

public class Visualization {
    private static final int[][] positions = {
            {100, 100}, {300, 80}, {500, 120}, {250, 300}, {450, 280}
    };
    private final Graph graph;
    private final Canvas canvas;

    public Visualization() {
        graph = new Graph(5);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 20);
        graph.addEdge(1, 2, 30);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 15);
        graph.addEdge(3, 4, 8);

        canvas = new Canvas(600, 400);
    }

    public Pane createContent() {
        Pane root = new Pane();
        root.getChildren().add(canvas);

        Button primBtn = new Button("Run Prim's");
        primBtn.setLayoutX(50);
        primBtn.setLayoutY(350);
        primBtn.setOnAction(e -> drawEdges(new PrimAlgorithm().getMST(graph), Color.GREEN));

        Button dijkstraBtn = new Button("Run Dijkstra");
        dijkstraBtn.setLayoutX(150);
        dijkstraBtn.setLayoutY(350);
        dijkstraBtn.setOnAction(e -> drawEdges(new DijkstraAlgorithm().getShortestPath(graph, 0), Color.BLUE));

        root.getChildren().addAll(primBtn, dijkstraBtn);
        drawGraph();
        return root;
    }

    private void drawGraph() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 600, 400);
        for (int[] edge : graph.getEdges()) {
            gc.strokeLine(positions[edge[0]][0], positions[edge[0]][1], positions[edge[1]][0], positions[edge[1]][1]);
        }
        for (int i = 0; i < positions.length; i++) {
            gc.setFill(Color.RED);
            gc.fillOval(positions[i][0] - 10, positions[i][1] - 10, 20, 20);
        }
    }

    private void drawEdges(List<int[]> edges, Color color) {
        drawGraph();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.setLineWidth(3);
        for (int[] edge : edges) {
            gc.strokeLine(positions[edge[0]][0], positions[edge[0]][1], positions[edge[1]][0], positions[edge[1]][1]);
        }
    }
}
