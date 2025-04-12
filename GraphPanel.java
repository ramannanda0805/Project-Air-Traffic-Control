// GraphPanel.java (Final Version with Plane Animation and Reset Button)

package com.airtrafficcontrol;

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.*;

public class GraphPanel extends Pane {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Graph graph;
    private final Image background = new Image("/images/background.png");
    private final Image plane = new Image("/images/plane.png");

    private final Button primButton = new Button("Visualize Prim's");
    private final Button dijkstraButton = new Button("Visualize Dijkstra");
    private final Button resetButton = new Button("Reset");

    private final List<CityNode> cityNodes = new ArrayList<>();

    public GraphPanel() {
        canvas = new Canvas(1536, 864);
        gc = canvas.getGraphicsContext2D();
        graph = new Graph();
        getChildren().add(canvas);

        setupCities();
        drawGraph();

        primButton.setLayoutX(100);
        primButton.setLayoutY(630);
        dijkstraButton.setLayoutX(250);
        dijkstraButton.setLayoutY(630);
        resetButton.setLayoutX(450);
        resetButton.setLayoutY(630);

        primButton.setOnAction(e -> animatePrim());
        dijkstraButton.setOnAction(e -> animateDijkstra());
        resetButton.setOnAction(e -> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawGraph();
        });

        getChildren().addAll(primButton, dijkstraButton, resetButton);
    }

    private void setupCities() {
        cityNodes.add(new CityNode("Delhi", 300, 100));
        cityNodes.add(new CityNode("Mumbai", 400, 400));
        cityNodes.add(new CityNode("Kolkata", 1000, 100));
        cityNodes.add(new CityNode("Bangalore", 700, 500));
        cityNodes.add(new CityNode("Chandigarh", 350, 200));

        for (CityNode node : cityNodes) {
            graph.addCity(node.name);
        }

        connect("Delhi", "Mumbai", 3);
        connect("Delhi", "Kolkata", 8);
        connect("Delhi", "Chandigarh", 2);
        connect("Chandigarh", "Mumbai", 4);
        connect("Mumbai", "Bangalore", 5);
        connect("Bangalore", "Kolkata", 7);
    }

    private void connect(String a, String b, int distance) {
        graph.connectCities(a, b, distance);
    }

    private void drawGraph() {
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        for (CityNode a : cityNodes) {
            for (Graph.Edge edge : graph.getAdjacencyList().get(a.name)) {
                CityNode b = findCity(edge.destination);
                if (b != null) {
                    gc.strokeLine(a.x, a.y, b.x, b.y);
                }
            }
        }

        for (CityNode city : cityNodes) {
            gc.setFill(Color.YELLOW);
            gc.fillOval(city.x - 10, city.y - 10, 20, 20);
            gc.setFill(Color.WHITE);
            gc.setFont(new Font(16));
            gc.fillText(city.name, city.x - 30, city.y - 15);
        }
    }

    private void animatePrim() {
        List<Graph.Edge> mst = PrimAlgorithm.primMST(graph, "Delhi");
        animateEdges(mst);
    }

    private void animateDijkstra() {
        List<Graph.Edge> path = DijkstraAlgorithm.getShortestPaths(graph, "Delhi");
        animateEdges(path);
    }

    private void animateEdges(List<Graph.Edge> edges) {
        SequentialTransition sequence = new SequentialTransition();

        for (Graph.Edge edge : edges) {
            CityNode a = findCity(edge.source);
            CityNode b = findCity(edge.destination);
            if (a == null || b == null) continue;

            Line line = new Line(a.x, a.y, b.x, b.y);
            gc.setStroke(Color.LIME);
            gc.setLineWidth(4);
            gc.strokeLine(a.x, a.y, b.x, b.y);

            javafx.scene.image.ImageView planeView = new javafx.scene.image.ImageView(plane);
            planeView.setFitWidth(80);
            planeView.setFitHeight(80);
            getChildren().add(planeView);

            Path path = new Path();
            path.getElements().add(new MoveTo(a.x, a.y));
            path.getElements().add(new LineTo(b.x, b.y));

            PathTransition transition = new PathTransition();
            transition.setNode(planeView);
            transition.setDuration(Duration.seconds(2));
            transition.setPath(path);
            transition.setOnFinished(e -> getChildren().remove(planeView));
            sequence.getChildren().add(transition);
        }

        sequence.play();
    }

    private CityNode findCity(String name) {
        for (CityNode city : cityNodes) {
            if (city.name.equals(name)) return city;
        }
        return null;
    }

    static class CityNode {
        String name;
        double x, y;

        CityNode(String name, double x, double y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }
    }
}
