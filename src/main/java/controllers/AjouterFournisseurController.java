package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.Fournisseur;
import tn.esprit.services.ServiceFournisseur;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterFournisseurController {
    private final ServiceFournisseur serviceFournisseur = new ServiceFournisseur();

    // Déclaration des champs de saisie pour le nom, prénom, type et numéro
    @FXML
    private TextField nomTF;
    @FXML
    private TextField prenomTF;
    @FXML
    private TextField numeroTF;
    @FXML
    private TextField typeTF;

    // Méthode appelée lors du clic sur le bouton "ADD"
    @FXML
    public void ajouterFournisseurAction(ActionEvent event) {
        // Vérifier si les champs requis sont vides
        if (nomTF.getText().isEmpty() || prenomTF.getText().isEmpty() || numeroTF.getText().isEmpty() || typeTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        // Valider que le numéro a exactement 8 chiffres
        String numero = numeroTF.getText();
        if (!numero.matches("\\d{8}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Le numéro doit contenir exactement 8 chiffres.");
            alert.showAndWait();
            return;
        }

        try {
            // Créer un objet Fournisseur à partir des données saisies
            Fournisseur fournisseur = new Fournisseur(
                    nomTF.getText(),
                    prenomTF.getText(), // Assurez-vous de définir le prénom ici
                    Integer.parseInt(numeroTF.getText()),
                    typeTF.getText()
            );

            // Ajouter le fournisseur à la base de données
            serviceFournisseur.ajouter(fournisseur);

            // Charger le fichier afficherFournisseur.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFournisseurs.fxml"));

            // Afficher la nouvelle scène avec les données du fournisseur ajouté
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Le numéro doit être un entier.");
            alert.showAndWait();
        } catch (SQLException | IOException e) {
            e.printStackTrace(); // Gérer les exceptions correctement
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    // Méthode appelée lors du clic sur le bouton "Cancel"
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            // Charger le fichier afficherFournisseurs.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFournisseurs.fxml"));
            // Changer la scène actuelle pour afficher la nouvelle
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
    @FXML
    void effacerChampsAction(ActionEvent event) {
        // Effacer le contenu des champs
        nomTF.clear();
        prenomTF.clear();
        numeroTF.clear();
        typeTF.clear();
    }

}
