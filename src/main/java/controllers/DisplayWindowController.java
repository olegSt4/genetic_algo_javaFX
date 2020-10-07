package controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Point;

import java.util.ArrayList;
import java.util.List;

public class DisplayWindowController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label iter;

    @FXML
    private Label minDist;

    @FXML
    private Pane pane;

    @FXML
    private Button startButton;

    private Task<Void> drawRoutes;

    public void start(Parent root, String startId, ArrayList<Point> points, String iter, String mutPercent) {
        Scene scene = new Scene(root);

        showPoints(points, startId);
        setStartButtonOnAction(startId, points, iter, mutPercent);

        Stage stage = new Stage();
        stage.setTitle("Genetic Algorithm");
        stage.setScene(scene);
        stage.show();
    }

    private void showPoints(ArrayList<Point> points, String startId) {
        double activeWidth = anchorPane.getPrefWidth();
        double activeHeight = anchorPane.getPrefHeight();

        for (Point point : points) {
            Circle circle = new Circle(point.x * activeWidth, point.y * activeHeight, 5);
            if (point.id == Integer.parseInt(startId)) {
                circle.setRadius(7);
                circle.setFill(Color.RED);
            }
            Label idText = new Label(String.valueOf(point.id));
            idText.setLayoutX(point.x * activeWidth + 5);
            idText.setLayoutY(point.y * activeHeight);

            anchorPane.getChildren().addAll(circle, idText);
        }
    }

    private void setStartButtonOnAction(String startId, ArrayList<Point> points, String iter, String mutPercent) {
        startButton.setOnAction(event -> {
            pane.getChildren().remove(startButton);

            drawRoutes = createTask(startId, points, iter, mutPercent);
            Thread thread = new Thread(drawRoutes);
            thread.setDaemon(true);
            thread.start();
        });
    }

    private Task<Void> createTask(String startId, ArrayList<Point> points, String iter, String mutPercent) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    displayResultsOfGeneticAlgo(startId, points, iter, mutPercent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            private void displayResultsOfGeneticAlgo(String startId, ArrayList<Point> points, String iter, String mutPercent) {
                // Get geneticAlgo results and display it
            }

        };
    }
}
