package com.airtrafficcontrol;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Visualization visualization = new Visualization();
        Scene scene = new Scene(visualization.createContent(), 800, 600);
        
        stage.setTitle("Air Traffic Control Visualization");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
