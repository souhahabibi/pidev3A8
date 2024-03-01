package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceProduit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.scene.text.Font;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import services.ServiceProduit;
import javafx.scene.text.TextAlignment;
//QR code
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import javax.imageio.ImageIO;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;




public class ClientController {
    private ServiceProduit p = new ServiceProduit();
    private ObservableList<tn.esprit.entites.Produit> produitsAchetes = FXCollections.observableArrayList();

    @FXML
    private VBox Produit;

    @FXML
    private TextField rechercheTF;
    @FXML
    private Button buttonReturn;

    @FXML
    public void initialize() {
        try {
            Produit.setSpacing(25);
            displayProduits();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displayProduits() throws SQLException {
        List<tn.esprit.entites.Produit> produits = p.recuperer();

        for (tn.esprit.entites.Produit produit : produits) {
            Pane produitEntry = createProduitEntry(produit);
            Produit.getChildren().add(produitEntry);
        }
    }

    public Pane createProduitEntry(tn.esprit.entites.Produit produit) {
        Pane produitPane = new Pane();
        produitPane.setId("produitPane" + produit.getId_produit());
        produitPane.setPrefSize(912, 217);
        produitPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

        ImageView imageView = new ImageView(new Image(produit.getImage()));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(14);
        imageView.setLayoutY(27);

        Text produitName = new Text(300, 38, "NOM :" + produit.getNom());
        produitName.setFont(new Font("Arial", 21));
        produitName.setEffect(new DropShadow());
        produitName.setUnderline(true);

        TextArea produitDescription = new TextArea("DESCRIPTION : " + produit.getDescription());
        produitDescription.setLayoutX(215);
        produitDescription.setLayoutY(60);
        produitDescription.setPrefHeight(91);
        produitDescription.setPrefWidth(512);
        produitDescription.setEditable(false);
        produitDescription.setWrapText(true);
        produitDescription.setFocusTraversable(false);
        produitDescription.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        produitDescription.setFont(new Font("Arial", 17));
        produitDescription.setEffect(new DropShadow());

        TextArea produitCout = new TextArea("PRIX(DT) : " + produit.getCout());
        produitCout.setLayoutX(215);
        produitCout.setLayoutY(90);
        produitCout.setPrefHeight(91);
        produitCout.setPrefWidth(512);
        produitCout.setEditable(false);
        produitCout.setWrapText(true);
        produitCout.setFocusTraversable(false);
        produitCout.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        produitCout.setFont(new Font("Arial", 17));
        produitCout.setEffect(new DropShadow());

        Button acheterButton = new Button("Acheter");
        acheterButton.setOnAction(event -> acheterProduit(produit));
        acheterButton.setLayoutX(735);
        acheterButton.setLayoutY(85);
        acheterButton.setPrefSize(149, 49);
        acheterButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #891b1b, #a7473e); " +
                "-fx-background-radius: 5px; " +
                "-fx-cursor: hand; " +
                "-fx-text-fill: #fff; " +
                "-fx-font-size: 14px;");
        acheterButton.setOnMouseEntered(event -> acheterButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #891b1b, #ff0000);"));
        acheterButton.setOnMouseExited(event -> acheterButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #891b1b, #a7473e);"));
        produitPane.getChildren().addAll(imageView, produitName, produitDescription, produitCout, acheterButton);

        return produitPane;
    }

    private void acheterProduit(tn.esprit.entites.Produit produit) {
        if (!produitsAchetes.contains(produit)) {
            ajouterAuPanier(produit);
            System.out.println("Produit acheté : " + produit.getNom());
            showAlert(Alert.AlertType.INFORMATION, "Produit ajouté", "Le produit a été ajouté au panier !");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Ce produit est déjà ajouté au panier !");
        }
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();

    }



    @FXML

    private void ouvrirPanier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/acheter.fxml"));
            Parent root = loader.load();

            AcheterController controller = loader.getController();
            if (controller != null) {
                // Si le contrôleur existe déjà, utilisez-le
                controller.initData(produitsAchetes);
            } else {
                // Si le contrôleur n'existe pas, créez une nouvelle instance
                controller = new AcheterController();
                controller.initData(produitsAchetes);
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Panier");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterAuPanier(tn.esprit.entites.Produit produit) {
        produitsAchetes.add(produit);
    }

    @FXML
    public void chercher() throws SQLException {
        updateProduitDisplay(rechercheTF.getText().toLowerCase());
    }

    private void updateProduitDisplay(String recherche) throws SQLException {
        Produit.getChildren().clear();
        List<tn.esprit.entites.Produit> produits = p.recuperer();
        for (tn.esprit.entites.Produit produit : produits ) {
            if (produit.getNom().toLowerCase().contains(recherche)) {
                Pane salleEntry = createProduitEntry(produit);
                Produit.getChildren().add(salleEntry);
            }
        }
    }

    @FXML
    private void trierProduits(ActionEvent event) {
        try {
            List<tn.esprit.entites.Produit> produits = p.recuperer();
            Collections.sort(produits, Comparator.comparingDouble(tn.esprit.entites.Produit::getCout).reversed());
            afficherProduits(produits);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherProduits(List<tn.esprit.entites.Produit> produits) {
        Produit.getChildren().clear();
        for (tn.esprit.entites.Produit produit : produits) {
            Pane produitEntry = createProduitEntry(produit);
            Produit.getChildren().add(produitEntry);
        }
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
