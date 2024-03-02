package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {
    @FXML
    private Button Coach;

    @FXML
    private Button Client;




    @FXML
    void getClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Market.fxml"));
            Parent root = loader.load();
            MealsController mealsController = loader.getController();

            // Get the current stage
            Stage stage = (Stage) Client.getScene().getWindow();
            // Set the new scene to the current stage
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("FXML file not found: " + e.getMessage());
        }
    }

    @FXML
    private void getCoach(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CoachMealManagement.fxml"));
            Parent root = loader.load();
            CoachMealMangagementController coachMealMangagementController = loader.getController();

            // Get the current stage
            Stage stage = (Stage) Coach.getScene().getWindow();
            // Set the new scene to the current stage
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("FXML file not found: " + e.getMessage());
        }
    }
}

