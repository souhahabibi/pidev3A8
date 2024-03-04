package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class GSalleController {
    @FXML
    private Button buttonClient;

    @FXML

    void naviguezVersAdmin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));//charger le fichier FXML
            buttonClient.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersClient(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));//charger le fichier FXML
            buttonClient.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
