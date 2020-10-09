package controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Point;
import supporters.PointsListGenerator;

import java.io.IOException;
import java.util.ArrayList;

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

        iterations.setPromptText("1...30");
        citiesAmount.setPromptText("10...40");
        mutationPercent.setPromptText("1...10");
    }

    public static void main(String[] argv) {
        Application.launch(argv);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/startingWindow.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Genetic algorithm");
        primaryStage.getIcons().add(new Image("/genetical.png"));
        primaryStage.show();
    }

    private void setButtonOnClickedHandler() {
        continueButton.setOnAction(event -> {
            if (iterations.getText().equals("") || citiesAmount.getText().equals("") || mutationPercent.getText().equals("")) return;

            Stage currentStage = (Stage) continueButton.getScene().getWindow();
            currentStage.close();

            ArrayList<Point> points = PointsListGenerator.getArrayList(Integer.parseInt(citiesAmount.getText()));

            FXMLLoader choseCityWinloader = new FXMLLoader();
            choseCityWinloader.setLocation(getClass().getResource("/choseCityWindow.fxml"));
            FXMLLoader displayWinloader = new FXMLLoader();
            displayWinloader.setLocation(getClass().getResource("/displayWindow.fxml"));

            try {
                Parent choseCityWinParent = choseCityWinloader.load();
                String iter = iterations.getText();
                String mutPercent = mutationPercent.getText();

                ((ChoseCityWindowController) choseCityWinloader.getController()).start(choseCityWinParent, points, iter, mutPercent, displayWinloader);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
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
