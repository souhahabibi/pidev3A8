package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Personne;
import services.PersonneService;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterPersonneController {
    private final PersonneService ps = new PersonneService();
    @FXML
    private TextField ageTF;
    @FXML
    private TextField nomTF;
    @FXML
    private TextField prenomTF;

    @FXML
    void ajouterPersonne(ActionEvent event) {
        try {
            ps.ajouter(new Personne(Integer.parseInt(ageTF.getText()), nomTF.getText(), prenomTF.getText()));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherPersonnes.fxml"));
            ageTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
