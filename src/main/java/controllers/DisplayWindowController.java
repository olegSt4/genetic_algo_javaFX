package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Point;

import java.util.List;

public class DisplayWindowController {
    @FXML
    private AnchorPane anchorPane;

    public void start(Parent root, String startId, List<Point> points, String iter, String mutPercent) {
        Scene scene = new Scene(root);

        Label test = new Label("From point " + startId);
        test.setLayoutX(100);
        test.setLayoutY(100);
        anchorPane.getChildren().add(test);

        Stage stage = new Stage();
        stage.setTitle("Genetic Algorithm");
        stage.setScene(scene);
        stage.show();
    }
}
