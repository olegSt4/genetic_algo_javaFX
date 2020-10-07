package controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import models.Point;
import models.Route;
import supporters.GeneticAlgorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class DisplayWindowController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label iterLabel;

    @FXML
    private Label minDistLabel;

    @FXML
    private Pane pane;

    @FXML
    private Button startButton;

    private Task<Void> drawRoutes;

    public void start(Parent root, String startId, ArrayList<Point> points, String iterNum, String mutPercent) {
        Scene scene = new Scene(root);

        showPoints(points, startId);
        setStartButtonOnAction(startId, points, iterNum, mutPercent);

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

    private void setStartButtonOnAction(String startId, ArrayList<Point> points, String iterNum, String mutPercent) {
        startButton.setOnAction(event -> {
            pane.getChildren().remove(startButton);

            drawRoutes = createTask(startId, points, iterNum, mutPercent);
            Thread thread = new Thread(drawRoutes);
            thread.setDaemon(true);
            thread.start();
        });
    }

    private Task<Void> createTask(String startId, ArrayList<Point> points, String iterNum, String mutPercent) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    displayResultsOfGeneticAlgo(startId, points, iterNum, mutPercent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            private void displayResultsOfGeneticAlgo(String startId, ArrayList<Point> points, String iterNum, String mutPercent) {
                GeneticAlgorithm genAlg = new GeneticAlgorithm(points, Integer.parseInt(startId), Integer.parseInt(mutPercent),
                        anchorPane.getPrefWidth(), anchorPane.getPrefHeight());

                int iterations = Integer.parseInt(iterNum);
                for (int i = 0; i < iterations; i++) {
                    boolean isLast = i == iterations - 1 ? true : false;

                    Route nextRoute = genAlg.nextPopulationBestRoute();
                    String minDistance = new DecimalFormat("#.##").format(nextRoute.length);
                    String currentIter = String.valueOf(i + 1);
                    Platform.runLater(() -> {
                        drawOneRoute(nextRoute, isLast);
                        iterLabel.setText(currentIter);
                        minDistLabel.setText(minDistance);
                    });
                }
            }
        };
    }

    private void drawOneRoute(Route route, boolean isSpecial) {
        double activeWidth = anchorPane.getPrefWidth();
        double activeHeight = anchorPane.getPrefHeight();

        Path path = new Path();

        MoveTo startPos = new MoveTo();
        startPos.setX(route.citySequence.get(0).x * activeWidth);
        startPos.setY(route.citySequence.get(0).y * activeHeight);

        path.getElements().add(startPos);
        for (int i = 1; i < route.citySequence.size(); i++) {
            LineTo nextPoint = new LineTo();
            nextPoint.setX(route.citySequence.get(i).x * activeWidth);
            nextPoint.setY(route.citySequence.get(i).y * activeHeight);
            path.getElements().add(nextPoint);
        }

        if (!isSpecial) {
            path.setStroke(getRandomColor());
            path.setStrokeWidth(1);

            path.setOnMouseEntered(event -> {
                path.setStrokeWidth(4);
            });

            path.setOnMouseExited(event -> {
                path.setStrokeWidth(1);
            });
        } else {
            path.setStroke(Color.RED);
            path.setStrokeWidth(5);
        }


        anchorPane.getChildren().add(path);
    }

    private Color getRandomColor() {
        Random rand = new Random();
        int choice = rand.nextInt(10);

        switch (choice) {
            case 0 : return Color.BLUE;
            case 1 : return Color.YELLOW;
            case 2 : return Color.GREEN;
            case 3 : return Color.GRAY;
            case 4 : return Color.BLACK;
            case 5 : return Color.ORANGE;
            case 6 : return Color.VIOLET;
            case 7 : return Color.DARKGREEN;
            case 8 : return Color.PINK;
            case 9 : return Color.BROWN;

            default: return Color.BLACK;
        }
    }
}
