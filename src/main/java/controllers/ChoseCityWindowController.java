package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Point;

import java.io.IOException;
import java.util.ArrayList;

public class ChoseCityWindowController {
    @FXML
    private AnchorPane anchorPane;

    private FXMLLoader dispWinLoader;
    private String iter;
    private String mutPercent;

    public void start(Parent root, ArrayList<Point> points, String iter, String mutPercent, FXMLLoader dispWinLoader) {
        this.dispWinLoader = dispWinLoader;
        this.iter = iter;
        this.mutPercent = mutPercent;

        Scene scene = new Scene(root);
        showPoints(points);

        Stage stage = new Stage();
        stage.setTitle("Genetic algorithm");
        stage.getIcons().add(new Image("/genetical.png"));
        stage.setScene(scene);
        stage.show();
    }

    private void showPoints(ArrayList<Point> points) {
        double activeWidth = anchorPane.getPrefWidth();
        double activeHeight = anchorPane.getPrefHeight();

        for (Point point : points) {
            Circle circle = new Circle(point.x * activeWidth, point.y * activeHeight, 5);
            circle.setId(String.valueOf(point.id));

            circle.setOnMouseEntered(event -> {
                circle.setStroke(Color.BLUE);
                circle.setStrokeWidth(4);
            });

            circle.setOnMouseExited(event -> circle.setStrokeWidth(0));

            circle.setOnMouseClicked(event -> {
                Stage currentStage = (Stage) circle.getScene().getWindow();
                currentStage.close();

                try {
                    Parent root = dispWinLoader.load();
                    ((DisplayWindowController) dispWinLoader.getController()).start(root, circle.getId(), points, iter, mutPercent);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Label idText = new Label(String.valueOf(point.id));
            idText.setLayoutX(point.x * activeWidth + 5);
            idText.setLayoutY(point.y * activeHeight);

            anchorPane.getChildren().addAll(circle, idText);
        }
    }
}
