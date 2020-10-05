package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Point;

import java.util.List;

public class DisplayWindowController {
    @FXML
    private AnchorPane anchorPane;

    public void start(Parent root, String startId, List<Point> points, String iter, String mutPercent) {
        Scene scene = new Scene(root);
        showPoints(points, startId);

        Stage stage = new Stage();
        stage.setTitle("Genetic Algorithm");
        stage.setScene(scene);
        stage.show();
    }

    private void showPoints(List<Point> points, String startId) {
        double activeWidth = anchorPane.getPrefWidth();
        double activeHeight = anchorPane.getPrefHeight();

        int id = 1;
        for (Point point : points) {
            Circle circle = new Circle(point.x * activeWidth, point.y * activeHeight, 5);
            if (id == Integer.parseInt(startId)) {
                circle.setRadius(7);
                circle.setFill(Color.RED);
            }
            Label idText = new Label(String.valueOf(id++));
            idText.setLayoutX(point.x * activeWidth + 5);
            idText.setLayoutY(point.y * activeHeight);

            anchorPane.getChildren().addAll(circle, idText);
        }
    }
}
