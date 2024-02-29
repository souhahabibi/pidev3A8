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
    ServiceProduit p = new ServiceProduit();
    private ObservableList<tn.esprit.entites.Produit> produitsAchetes = FXCollections.observableArrayList();
    @FXML
    private VBox Produit;

    @FXML
    private TextField rechercheTF;

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

        List<tn.esprit.entites.Produit> produits = p.recuperer(); // Adjust this line to match your method for fetching competitions

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
        //QR
        produitPane.setOnMouseClicked(event -> {
                generateQRCode(produit);
        });
        // Créer une ImageView pour afficher l'image
        ImageView imageView = new ImageView(new Image(produit.getImage()));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(14);
        imageView.setLayoutY(27);

        Text produitName = new Text(300, 38, "NOM :" + produit.getNom());
        produitName.setFont(new Font("Arial", 21));
        produitName.setEffect(new DropShadow());
        produitName.setUnderline(true);

        //description
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
        //cout
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
        // Appliquer le style CSS au bouton Acheter
        acheterButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #891b1b, #a7473e); " +
                "-fx-background-radius: 5px; " +
                "-fx-cursor: hand; " +
                "-fx-text-fill: #fff; " +
                "-fx-font-size: 14px;");
        //
// Définir un style CSS différent lorsque le bouton est survolé
        acheterButton.setOnMouseEntered(event -> acheterButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #891b1b, #ff0000);"));
        acheterButton.setOnMouseExited(event -> acheterButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #891b1b, #a7473e);"));
        produitPane.getChildren().addAll(imageView, produitName, produitDescription,produitCout,acheterButton);

        return produitPane;


    }

    private void generateQRCode(tn.esprit.entites.Produit produit) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String information = "NOM: " + produit.getNom() + "\n" + "Description : " + produit.getDescription() + "\n" + "Prix : " + produit.getCout() + "\n";
        int width = 100;
        int height = 100;

        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(information, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    bufferedImage.setRGB(j, i, byteMatrix.get(j, i) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            // Convert BufferedImage to JavaFX Image
            WritableImage writableImage = new WritableImage(width, height);
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixelWriter.setArgb(x, y, bufferedImage.getRGB(x, y));
                }
            }

            // Create ImageView for the QR code image
            ImageView imageViewQRCode = new ImageView(writableImage);
            imageViewQRCode.setLayoutX(600);
            imageViewQRCode.setLayoutY(65);
            // Find the product pane corresponding to the clicked product
            Pane productPane = (Pane) Produit.getChildren().stream()
                    .filter(node -> node.getId().equals("produitPane" + produit.getId_produit()))
                    .findFirst().orElse(null);

            // Add the QR code image to the product pane
            if (productPane != null) {
                productPane.getChildren().add(imageViewQRCode);
            }

        } catch (WriterException ex) {
            ex.printStackTrace();
        }
    }


    private void acheterProduit(tn.esprit.entites.Produit produit) {
        if (!produitsAchetes.contains(produit)) {
            ajouterAuPanier(produit);
        } else {
            // Afficher une boîte de dialogue d'alerte pour indiquer que le produit est déjà ajouté au panier
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Ce produit est déjà ajouté au panier !");
            alert.showAndWait();
        }
    }

    @FXML
    private void ouvrirPanier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/acheter.fxml"));
            Parent root = loader.load();

            AcheterController controller = loader.getController(); // Utilisez AcheterController ici
            controller.initData(produitsAchetes);

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
        Produit.getChildren().clear(); // Effacer les salles actuellement affichées
        List<tn.esprit.entites.Produit> produits = p.recuperer(); // Récupérer toutes les salles depuis la base de données
        for (tn.esprit.entites.Produit produit : produits ) {
            // Vérifier si le nom de la salle contient le texte de recherche
            if (produit.getNom().toLowerCase().contains(recherche)) {
                Pane salleEntry = createProduitEntry(produit); // Créer une entrée pour la salle
                Produit.getChildren().add(salleEntry); // Ajouter l'entrée à la liste des salles affichées
            }
        }
    }
    @FXML
    private void trierProduits(ActionEvent event) {
        try {
            List<tn.esprit.entites.Produit> produits = p.recuperer(); // Récupérer la liste des produits
            // Trier les produits par coût décroissant en utilisant un Comparator
            Collections.sort(produits, Comparator.comparingDouble(tn.esprit.entites.Produit::getCout).reversed());
            // Mettre à jour l'affichage des produits triés
            afficherProduits(produits);
        } catch (SQLException e) {
            e.printStackTrace(); // ou tout autre gestion appropriée de l'exception
        }
    }

    private void afficherProduits(List<tn.esprit.entites.Produit> produits) {
        Produit.getChildren().clear(); // Effacer les produits affichés actuellement
        for (tn.esprit.entites.Produit produit : produits) {
            Pane produitEntry = createProduitEntry(produit); // Créer une entrée pour le produit
            Produit.getChildren().add(produitEntry); // Ajouter l'entrée à la liste des produits affichés
        }
    }


}
