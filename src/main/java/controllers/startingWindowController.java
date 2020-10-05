package controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class startingWindowController extends Application {
    @FXML
    private TextField iterations;

    @FXML
    private TextField citiesAmount;

    @FXML
    private TextField mutationPercent;

    @FXML
    private Button continueButton;

    @FXML
    public void initialize() {
        setButtonOnClickedHandler();
    }

    public static void main(String[] argv) {
        Application.launch(argv);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/startingWindow.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Genetic algorithm");
        primaryStage.show();
    }

    private void setButtonOnClickedHandler() {

    }
}
