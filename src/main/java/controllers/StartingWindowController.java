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

public class StartingWindowController extends Application {
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

        addValidationToTextField(iterations, "[1-9]|[1-2][0-9]|30");
        addValidationToTextField(citiesAmount, "[1-3][0-9]|40");
        addValidationToTextField(mutationPercent, "[1-9]|10");
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

    private void addValidationToTextField(TextField textField, String pattern) {
        textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!textField.getText().matches(pattern)) {
                    textField.setText("");
                }
            }
        });
    }
}
