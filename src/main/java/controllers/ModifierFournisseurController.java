package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.services.ServiceFournisseur;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierFournisseurController {
    private final tn.esprit.services.ServiceFournisseur fs = new ServiceFournisseur();
    private tn.esprit.entites.Fournisseur fournisseur;
    @FXML
    private TextField nomTF;
    @FXML
    private TextField prenomTF;
    @FXML
    private TextField numeroTF;
    @FXML
    private TextField typeTF;

    public void setFournisseur(tn.esprit.entites.Fournisseur fournisseur) {
        this.fournisseur = fournisseur;

        // Update UI components with the Organisateur's data
        nomTF.setText(fournisseur.getNom());
        prenomTF.setText(fournisseur.getPrenom());
        typeTF.setText(fournisseur.getType());
        numeroTF.setText(String.valueOf(fournisseur.getNumero()));

        // Set other fields as needed
    }
    @FXML
    void modifierFournisseur(ActionEvent event)
    {
        fournisseur.setNom(nomTF.getText());
        fournisseur.setPrenom(prenomTF.getText());
        fournisseur.setType(typeTF.getText());
        fournisseur.setNumero(Integer.parseInt(numeroTF.getText()));
        try {
            fs.modifier(fournisseur);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        naviguezVersAffichage(null);
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFournisseurs.fxml"));
            numeroTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
