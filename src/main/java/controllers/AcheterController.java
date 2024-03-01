package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import services.ServiceProduit;
import tn.esprit.entites.Produit;
import tn.esprit.services.ServiceFournisseur;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class AcheterController implements Initializable {
    @FXML
    private ListView<Pane> panierListView;
    private ObservableList<Produit> produitsAchetes;
    private final ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
    private ObservableList<Produit> produits;
    private ServiceProduit serviceProduit;



    public AcheterController() {
        this.serviceProduit = new ServiceProduit();
        if (this.produitsAchetes == null) {
            this.produitsAchetes = FXCollections.observableArrayList();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherProduitsAchetes();
    }
    private void afficherProduitsAchetes() {
        try {
            List<Produit> produitsList = serviceProduit.recuperer();
            for (Produit produit : produitsList) {
                Pane produitPane = createProduitPane(produit);
                panierListView.getItems().add(produitPane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Une erreur s'est produite lors de la récupération des produits.");
        }
    }


    private Pane createProduitPane(Produit produit) {
        VBox vBox = new VBox(); // Utiliser un VBox comme conteneur principal
        vBox.setSpacing(10);
        vBox.setPrefSize(500, 400); // Taille préférée du VBox

        // Créer un label pour chaque champ du produit
        Label nomFieldLabel = new Label("Nom: ");
        Label nomValueLabel = new Label(produit.getNom());
        Label quantiteFieldLabel = new Label("Quantité: ");
        Label quantiteValueLabel = new Label(String.valueOf(produit.getQuantite()));
        Label coutFieldLabel = new Label("Coût: ");
        Label coutValueLabel = new Label(String.valueOf(produit.getCout()));
        Label dateExpirationFieldLabel = new Label("Date d'expiration: ");
        Label dateExpirationValueLabel = new Label(String.valueOf(produit.getDate_expiration()));

        // Récupérer le nom du fournisseur
        String nomFournisseur = null;
        try {
            nomFournisseur = serviceFournisseur.recupererNomFournisseurParId(produit.getId_fournisseur());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // Label idFournisseurLabel = new Label("Fournisseur:");
        Label idFournisseurValueLabel = new Label(nomFournisseur != null ? nomFournisseur : "N/A");
        Label descriptionLabel = new Label("Description:");
        Label descriptionValueLabel = new Label(produit.getDescription());

        // Appliquer le style en gras aux labels des champs
        nomFieldLabel.setStyle("-fx-font-weight: bold;");
        quantiteFieldLabel.setStyle("-fx-font-weight: bold;");
        coutFieldLabel.setStyle("-fx-font-weight: bold;");
        dateExpirationFieldLabel.setStyle("-fx-font-weight: bold;");

        // Spécifier des tailles préférées pour les labels
        nomFieldLabel.setPrefWidth(150);
        quantiteFieldLabel.setPrefWidth(150);
        coutFieldLabel.setPrefWidth(150);
        dateExpirationFieldLabel.setPrefWidth(150);

        // Ajouter les labels au VBox
        vBox.getChildren().addAll(
                new HBox(nomFieldLabel, nomValueLabel),
                new HBox(quantiteFieldLabel, quantiteValueLabel),
                new HBox(coutFieldLabel, coutValueLabel),
                new HBox(dateExpirationFieldLabel, dateExpirationValueLabel),
                new HBox(new Label("Fournisseur: "), idFournisseurValueLabel),
                new HBox(descriptionLabel, descriptionValueLabel)
        );

        vBox.setSpacing(10); // Définir un espacement entre les étiquettes

        // Créer un Pane contenant le VBox
        Pane produitPane = new Pane(vBox);
        produitPane.setPadding(new Insets(10)); // Ajouter une marge intérieure
        return produitPane;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();

    }
    public void initData(ObservableList<Produit> produitsAchetes) {
        if (this.produitsAchetes == null) {
            this.produitsAchetes = FXCollections.observableArrayList();
        }
        this.produitsAchetes.setAll(produitsAchetes);
    }


    @FXML
    private void passerCommande(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de commande");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir passer commande ?");

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmationAlert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        confirmationAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeOui) {
                // Code à exécuter si l'utilisateur clique sur "Oui"
                // Supprimer les produits du panier
                produitsAchetes.clear();
                // Vider le panier dans l'interface utilisateur
                panierListView.getItems().clear();
                showAlert(Alert.AlertType.INFORMATION, "Commande passée", "Votre commande a été passée avec succès !");
            }
        });
    }


    private void initialiserAchat() {
        if (produitsAchetes != null) {
            produitsAchetes.clear(); // Vider la liste des produits achetés
        }
        if (panierListView != null) {
            panierListView.getItems().clear(); // Nettoyer la liste des HBox dans le ListView

        }
    }
    // Méthode pour vider la liste et les HBox
   }


