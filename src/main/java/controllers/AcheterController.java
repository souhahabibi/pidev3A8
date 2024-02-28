package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.ServiceProduit;
import tn.esprit.entites.Produit;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class AcheterController implements Initializable {
    @FXML
    private ListView<Pane> panierListView;

    private ObservableList<Produit> produits;
    private ServiceProduit serviceProduit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceProduit = new ServiceProduit();
        try {
            afficherProduits();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initData(ObservableList<Produit> produits) {
        this.produits = produits;
    }

    private void afficherProduits() throws SQLException {
        List<Produit> produitsList = serviceProduit.recuperer();
        panierListView.getItems().clear();
        for (Produit produit : produitsList) {
            Pane produitPane = createProduitPane(produit);
            panierListView.getItems().add(produitPane);
        }
    }

    private Pane createProduitPane(Produit produit) {
        Pane produitPane = new Pane();
        produitPane.setPrefSize(200, 100);

        Label nomLabel = new Label("Nom: " + produit.getNom());
        nomLabel.setLayoutX(10);
        nomLabel.setLayoutY(10);

        Label descriptionLabel = new Label("Description: " + produit.getDescription());
        descriptionLabel.setLayoutX(10);
        descriptionLabel.setLayoutY(30);

        Label prixLabel = new Label("Prix: " + produit.getCout());
        prixLabel.setLayoutX(10);
        prixLabel.setLayoutY(50);

        produitPane.getChildren().addAll(nomLabel, descriptionLabel, prixLabel);

        return produitPane;
    }
    }
