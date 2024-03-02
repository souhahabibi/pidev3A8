package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.Mailservice;
import services.ServiceProduit;
import tn.esprit.entites.Produit;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
public class AjouterProduitController {
    private final ServiceProduit produitService = new ServiceProduit();

    @FXML
    private TextField nomTF;

    @FXML
    private TextField quantiteTF;

    @FXML
    private TextField coutTF;
    @FXML
    private TextField descriptionTF;

    @FXML
    private DatePicker dateExpirationDP;

    @FXML
    private TextField idFournisseurTF;
    private tn.esprit.entites.Fournisseur fournisseur;
    private String imagePath;
    @FXML
    private Button btnChooseImage;
    @FXML
    private ImageView imageView;
    private tn.esprit.entites.Fournisseur selectedFournisseur;

    @FXML
    void choisirImage(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", ".png", ".jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                imagePath = file.toURI().toString(); // Obtenir l'URI de l'image
                Image image = new Image(imagePath);
                imageView.setImage(image); // Afficher l'image dans l'ImageView
                // Ajuster la taille de l'image affichée
                imageView.setFitWidth(200); // Largeur de l'image
                imageView.setFitHeight(200); // Hauteur de l'image
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void setFournisseur(tn.esprit.entites.Fournisseur fournisseur) {
        this.fournisseur = fournisseur;

        // Update UI components with the Organisateur's data
        idFournisseurTF.setText(String.valueOf(fournisseur.getNom())); // Afficher l'ID du fournisseur
        // Set other fields as needed
    }




    @FXML
    void ajouterProduitAction(ActionEvent event) {
        LocalDate localDate = dateExpirationDP.getValue();
        Date dateExpiration = Date.valueOf(localDate);
        LocalDate now = LocalDate.now();

        try {
            // Vérifier si tous les champs sont remplis
            if (nomTF.getText().isEmpty() || quantiteTF.getText().isEmpty() || coutTF.getText().isEmpty() || dateExpirationDP.getValue() == null || idFournisseurTF.getText().isEmpty() || descriptionTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }

            // Vérifier si tous les champs sont remplis
            if (nomTF.getText().isEmpty() || quantiteTF.getText().isEmpty() || coutTF.getText().isEmpty() || dateExpirationDP.getValue() == null || idFournisseurTF.getText().isEmpty() || descriptionTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }
            // Valider que le nom ne contient que des lettres
            String prenom = nomTF.getText();
            if (!prenom.matches("[a-zA-Z]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Le nom ne doit contenir que des lettres.");
                alert.showAndWait();
                return;
            }
            // Vérifier si la description contient du texte ordinaire ou des nombres entiers
            String description = descriptionTF.getText();
            if (!description.matches(".*[a-zA-Z].*") && !description.matches(".*\\d.*")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("La description doit contenir soit du texte ordinaire, soit des nombres entiers.");
                alert.showAndWait();
                return;
            }

            // Valider que le quantite est un entier
            String numero = quantiteTF.getText();
            try {
                Integer.parseInt(numero);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Le quantite doit être un entier.");
                alert.showAndWait();
                return;
            }
            // Vérifier si la quantité et le coût sont des nombres valides
            int quantite;
            float cout;
            try {
                quantite = Integer.parseInt(quantiteTF.getText());
                cout = Float.parseFloat(coutTF.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir des nombres valides pour la quantité et le coût.");
                alert.showAndWait();
                return;
            }

            // Vérifier si la date d'expiration est valide
            if (localDate.isBefore(now.plusDays(10))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("La date d'expiration doit être au moins 10 jours après la date d'aujourd'hui.");
                alert.showAndWait();
                return;
            }

            // Récupérer le nom du fournisseur correspondant à l'ID
            String nomFournisseur = null;
            // Vous pouvez utiliser votre service pour récupérer le nom du fournisseur ici
            // Exemple : nomFournisseur = serviceFournisseur.getNomFournisseurParID(fournisseur.getId_fournisseur());

            // Créer un objet Produit avec les données saisies
            Produit produit = new Produit(
                    0, // L'id_produit sera généré automatiquement dans la base de données
                    nomTF.getText(),
                    quantite,
                    cout,
                    dateExpiration,
                    fournisseur, // Utiliser l'ID du fournisseur
                    descriptionTF.getText(), // Ajouter la description
                    imagePath
            );

            // Appeler le service pour ajouter le produit
            produitService.ajouter(produit);

            // Charger le fichier AfficherProduits.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFournisseurs.fxml"));
            Parent root = loader.load();

            // Afficher la nouvelle scène avec les données du produit ajouté
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Afficher une alerte en cas d'erreur de format ou de base de données
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout du produit. Veuillez vérifier les données saisies.");
            alert.showAndWait();
        }
    }
}