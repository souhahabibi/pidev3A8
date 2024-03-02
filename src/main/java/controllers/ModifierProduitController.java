package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import services.ServiceProduit;
import tn.esprit.entites.Fournisseur;
import tn.esprit.services.ServiceFournisseur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifierProduitController  {
    private final ServiceProduit produitService = new ServiceProduit();
    private final tn.esprit.services.ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
    private tn.esprit.entites.Produit produit;
    @FXML
    private TextField nomTF;
    @FXML
    private TextField descriptionTF;
    @FXML
    private String imagePath;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField quantiteTF;

    @FXML
    private TextField coutTF;

    @FXML
    private DatePicker dateExpirationDP;

    @FXML
    private TextField idFournisseurTF;
    @FXML
    private ComboBox<Fournisseur> fournisseurCB;

    private Connection connection;




// Maintenant, vous pouvez itérer sur la liste comme suit



    @FXML
    public void setProduit(tn.esprit.entites.Produit produit, ObservableList<tn.esprit.entites.Fournisseur> observableList) throws SQLException {
        this.produit = produit;

        nomTF.setText(produit.getNom());
        quantiteTF.setText(String.valueOf(produit.getQuantite())); // Convertir int en String
        coutTF.setText(String.valueOf(produit.getCout())); // Convertir float en String
        descriptionTF.setText(String.valueOf(produit.getDescription()));
       imageView.setImage(new Image(produit.getImage()));
// Convertir java.sql.Date en LocalDate directement
        java.util.Date utilDate = produit.getDate_expiration();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        LocalDate localDate = sqlDate.toLocalDate();
        dateExpirationDP.setValue(localDate);



        // Utiliser la liste observable pour les fournisseurs
        fournisseurCB.setItems(observableList);

        // Sélectionner le fournisseur associé au produit dans le ComboBox
        Fournisseur fournisseurAssocie = null;
        for (Fournisseur fournisseur : observableList) {
            if (fournisseur.getId_fournisseur() == produit.getFournisseur().getId_fournisseur()) {
                fournisseurAssocie = fournisseur;
                break;
            }
        }
        fournisseurCB.getSelectionModel().select(fournisseurAssocie);

        // Définir le convertisseur pour le ComboBox
        fournisseurCB.setConverter(new StringConverter<Fournisseur>() {
            @Override
            public String toString(Fournisseur object) {
                return object.getNom();
            }

            @Override
            public Fournisseur fromString(String string) {
                return fournisseurCB.getItems().stream().filter(a -> a.getNom().equals(string)).findFirst().orElse(null);
            }
        });
    }

    @FXML
    void modifierProduitAction(ActionEvent event) {
        try {
            // Vérifier que les champs quantiteTF et coutTF contiennent des nombres valides
            int quantite = Integer.parseInt(quantiteTF.getText());
            int cout = Integer.parseInt(coutTF.getText());

            // Mettre à jour les propriétés du produit
            produit.setNom(nomTF.getText());
            produit.setDate_expiration(Date.valueOf(dateExpirationDP.getValue()));
            produit.setQuantite(quantite);
            produit.setCout(cout);
           // produit.setFournisseur(fournisseur);
            produit.setFournisseur(fournisseurCB.getValue());
            produit.setDescription(descriptionTF.getText());

            // Vérifier si une nouvelle image a été sélectionnée
            if (imagePath == null) {
                // Si aucune nouvelle image n'est sélectionnée, utilisez l'image actuelle
                produit.setImage(produit.getImage());
            } else {
                // Sinon, utilisez la nouvelle image sélectionnée
                produit.setImage(imagePath);
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
            // Appeler le service pour modifier le produit
            produitService.modifier(produit);

            // Navigation vers la vue d'affichage des produits
            naviguezVersAffichage(null);
        } catch (NumberFormatException e) {
            // Gérer le cas où les champs quantiteTF et coutTF ne contiennent pas des nombres valides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Les champs Quantité et Coût doivent contenir des nombres valides.");
            alert.showAndWait();
        } catch (SQLException e) {
            // Gérer les erreurs SQL lors de la modification du produit
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
        void naviguezVersAffichage (ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFournisseurs.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}

