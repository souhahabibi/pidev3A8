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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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

        try {
            // Créer un objet Fournisseur à partir des données saisies
            Fournisseur fournisseur = new Fournisseur(
                    nomTF.getText(),
                    prenomTF.getText(),
                    Integer.parseInt(numeroTF.getText()),
                    typeTF.getText()
            );

            // Ajouter le fournisseur à la base de données
            serviceFournisseur.ajouter(fournisseur);

            // Charger le fichier afficherFournisseur.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));

            // Afficher la nouvelle scène avec les données du fournisseur ajouté
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Envoyer un SMS pour indiquer qu'un nouveau fournisseur est ajouté
           Twilio.init("ACc4d86ffa3e9f5a46670d5420b2326baa", "e689c73fe6116076af63d9ec9af5e37e");
            Message twilioMessage = Message.creator(
                 new PhoneNumber("+21653009552"), // Numéro de téléphone du destinataire
                 new PhoneNumber("+15178363873"), // Numéro Twilio
                    "Un nouveau fournisseur est ajouté"
            ).create();
            System.out.println("SMS envoyé avec succès : " + twilioMessage.getSid()); // Pour vérification dans la console

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
            Parent root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));
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
