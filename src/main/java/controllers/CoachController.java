package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class CoachController {

    @javafx.fxml.FXML
    private Button Mealbtn;

    @javafx.fxml.FXML
    public void goToMeal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/CoachMealManagement.fxml"));
            Mealbtn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
