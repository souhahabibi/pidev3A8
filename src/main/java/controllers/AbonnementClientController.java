package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Abonnement;
import models.Materiel;
import services.AbonnementService;
import services.MaterielService;
import javafx.scene.control.CheckBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AbonnementClientController {
    AbonnementService as = new AbonnementService();
    int x;
    @FXML
    private CheckBox montantCB;
    @FXML
    private CheckBox dureeCB;
    boolean d=false;
    boolean m=false;
    @FXML
    private VBox abonnementsContainer;
    @FXML
    public void setAbonnement(int id) {

        try {
            abonnementsContainer.setSpacing(25);
            displayAbonnements(id);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    public void triduree() throws SQLException {
    d=!d;
    if(d){
        m=false;
        montantCB.setSelected(false);
    }
    displayAbonnements(x);
    }
    public void triMontant() throws SQLException {
        m=!m;
        if(m){
            d=false;
            dureeCB.setSelected(false);
        }
        displayAbonnements(x);
    }
    @FXML
    void naviguezVersClient(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));//charger le fichier FXML
            abonnementsContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    private void displayAbonnements(int id) throws SQLException {
        x=id;
        List<Abonnement> abonnements = as.select(id); // Fetch abonnements from the database

        // Sort the list based on montant if m is true, or based on duree if d is true
        if (m) {
            Collections.sort(abonnements, Comparator.comparingInt(Abonnement::getMontant));
        } else if (d) {
            Collections.sort(abonnements, Comparator.comparingInt(Abonnement::getDuree));
        }

        // Clear the existing entries
        abonnementsContainer.getChildren().clear();

        // Display the sorted abonnements
        for (Abonnement abonnement : abonnements) {
            Pane abonnementEntry = createAbonnementEntry(abonnement);
            abonnementsContainer.getChildren().add(abonnementEntry);
        }
    }

    @FXML
    public Pane createAbonnementEntry(Abonnement abonnement ) {
        Pane abonnementPane = new Pane();
        abonnementPane.setPrefSize(612, 120); // Adjusted to match your FXML Pane size
        abonnementPane.setStyle("-fx-border-color: #8B0000; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

        /*// Chemin de l'image
        String imagePath = "C:/Users/Cyrinechalghoumi/Downloads/s3.png";

// Création de l'objet Image
        Image image = new Image("file:" + imagePath);

// Création de l'objet ImageView pour afficher l'image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(732); // Ajustez la largeur selon vos besoins
        imageView.setFitHeight(120); // Ajustez la hauteur selon vos besoins
        imageView.setOpacity(0.2); // Définir l'opacité de l'image

// Ajout de l'ImageView au Pane
        abonnementPane.getChildren().add(imageView);*/
        ImageView imageView3 = new ImageView();
        imageView3.setLayoutX(10);
        imageView3.setLayoutY(18);
        imageView3.setFitWidth(38);
        imageView3.setFitHeight(39);
        imageView3.setPickOnBounds(true);

        // Set the image to the image view
        Image image2 = new Image("file:///Users/Cyrinechalghoumi/Downloads/file (1).png");
        imageView3.setImage(image2);

        ImageView imageView = new ImageView();
        imageView.setLayoutX(60);
        imageView.setLayoutY(80);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageView.setPickOnBounds(true);

        // Set the image to the image view
        Image image1 = new Image("file:///Users/Cyrinechalghoumi/Downloads/calendar (1).png");
        imageView.setImage(image1);

        ImageView imageView1 = new ImageView();
        imageView1.setLayoutX(350);
        imageView1.setLayoutY(80);
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        imageView1.setPickOnBounds(true);

        // Set the image to the image view
        Image image = new Image("file:///Users/Cyrinechalghoumi/Downloads/saving.png");
        imageView1.setImage(image);

        TextArea Description = new TextArea("description : " +abonnement.getDescription());
        Description.setLayoutX(45);
        Description.setLayoutY(10);
        Description.setPrefHeight(91);
        Description.setPrefWidth(612);
        Description.setEditable(false);
        Description.setWrapText(true);
        Description.setFocusTraversable(false);
        Description.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        Description.setFont(new Font("Arial", 15));
        Description.setEffect(new DropShadow());



        Text montant = new Text(105, 102, "montant: " + abonnement.getMontant()); // Adjusted X to align with the map icon
        montant.setEffect(new DropShadow());
        Text duree = new Text(400, 102, "duree: " + abonnement.getDuree() +"  months"); // Adjusted X to align with the map icon
        duree.setEffect(new DropShadow());

        // Add all components to the pane
        abonnementPane.getChildren().addAll( Description,montant, duree,imageView3,imageView, imageView1);

        return abonnementPane;
    }
}
