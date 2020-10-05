package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Point;

import java.util.List;

public class ChoseCityWindowController {
    @FXML
    private AnchorPane anchorPane;

    public void start(Parent root, List<Point> points, String iter, String mutPercent, FXMLLoader dispWinLoader) {
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Genetic algorithm");
        stage.setScene(scene);
        stage.show();
    }
}
