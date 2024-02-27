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
        // Vérifier si les champs requis sont vides
        if (nomTF.getText().isEmpty() || prenomTF.getText().isEmpty() || numeroTF.getText().isEmpty() || typeTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Valider que le nom ne contient que des lettres
        String nom = nomTF.getText();
        if (!nom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Le nom ne doit contenir que des lettres.");
            alert.showAndWait();
            return;
        }

        // Valider que le prénom ne contient que des lettres
        String prenom = prenomTF.getText();
        if (!prenom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Le prénom ne doit contenir que des lettres.");
            alert.showAndWait();
            return;
        }

        // Valider que le numéro est un entier
        String numero = numeroTF.getText();
        try {
            Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Le numéro doit être un entier.");
            alert.showAndWait();
            return;
        }

        // Valider que le numéro a exactement 8 chiffres
        if (numero.length() != 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Le numéro doit contenir exactement 8 chiffres.");
            alert.showAndWait();
            return;
        }

        // Si toutes les validations sont passées, procéder à la modification
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
        // Naviguer vers l'affichage après la modification
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
