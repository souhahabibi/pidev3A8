package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import services.ServiceProduit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClientController {
    @FXML
    private ListView<tn.esprit.entites.Produit> listView0;

    private final ServiceProduit serviceProduit = new ServiceProduit();
    @FXML
    private Button buttonReturn;

    @FXML
    void initialize() {

        try {
            List<tn.esprit.entites.Produit> produits = serviceProduit.recuperer();
            if (!produits.isEmpty()) {
                ObservableList<tn.esprit.entites.Produit> observableList = FXCollections.observableList(produits);
                listView0.setItems(observableList);
                listView0.setCellFactory(param -> new ListCell<>() {
                    @Override
                    protected void updateItem(tn.esprit.entites.Produit produit, boolean empty) {
                        super.updateItem(produit, empty);
                        if (empty || produit == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Créer un HBox pour afficher l'image, le nom et la description du produit
                            HBox hBox = new HBox();
                            hBox.setSpacing(10);

                            // Créer une ImageView pour afficher l'image
                            ImageView imageView = new ImageView(new Image(produit.getImage()));
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(150);

                            // Créer un VBox pour afficher le nom et la description du produit sur des lignes séparées
                            VBox vBox = new VBox();
                            Label nomLabel = new Label("Nom: ");
                            Label descriptionLabel = new Label("Description: ");
                            Label nomValueLabel = new Label(produit.getNom());
                            Label descriptionValueLabel = new Label(produit.getDescription());
                            nomLabel.setStyle("-fx-font-weight: bold;");
                            descriptionLabel.setStyle("-fx-font-weight: bold;");
                            vBox.getChildren().addAll(nomLabel, nomValueLabel, descriptionLabel, descriptionValueLabel);

                            // Mettre le VBox au centre de l'image
                            StackPane.setAlignment(vBox, javafx.geometry.Pos.CENTER);

                            // Ajouter les éléments au HBox
                            hBox.getChildren().addAll(imageView, vBox);

                            // Définir le contenu de la cellule comme le HBox
                            setGraphic(hBox);
                        }
                    }
                });
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Erreur lors de la récupération des produits : " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void naviguezVersAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Acceuil.fxml"));
            buttonReturn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
