package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    private Button startButton;

    public void start(Parent root, String startId, ArrayList<Point> points, String iter, String mutPercent) {
        Scene scene = new Scene(root);
        showPoints(points, startId);

        Stage stage = new Stage();
        stage.setTitle("Genetic Algorithm");
        stage.setScene(scene);
        stage.show();
    }

    private void showPoints(ArrayList<Point> points, String startId) {
        double activeWidth = anchorPane.getPrefWidth();
        double activeHeight = anchorPane.getPrefHeight();

        for (int index = 0; index < points.size(); index++) {
            Point nextPoint = points.get(index);
            Circle circle = new Circle(nextPoint.x * activeWidth, nextPoint.y * activeHeight, 5);
            if (index + 1 == Integer.parseInt(startId)) {
                circle.setRadius(7);
                circle.setFill(Color.RED);
            }
            Label idText = new Label(String.valueOf(index + 1));
            idText.setLayoutX(nextPoint.x * activeWidth + 5);
            idText.setLayoutY(nextPoint.y * activeHeight);

            anchorPane.getChildren().addAll(circle, idText);
        }
    }
}
