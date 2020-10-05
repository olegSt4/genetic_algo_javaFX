package controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Point;

import java.util.List;

public class DisplayWindowController {

    public void start(Parent root, List<Point> points, String iter, String mutPercent) {
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Genetic Algorithm");
        stage.setScene(scene);
        stage.show();
    }
}
