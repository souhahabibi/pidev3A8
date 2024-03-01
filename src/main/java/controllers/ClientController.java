package controllers;

import atlantafx.base.controls.CalendarSkin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.Materiel;
import models.Salle;
import services.SalleService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClientController {
    @FXML
    private TextField rechercheTF;
    SalleService s = new SalleService();

    @FXML
    private VBox sallesContainer;


    @FXML
    public void initialize() {
        try {
            sallesContainer.setSpacing(25);
            displaySalles();
          /*  rechercheTF.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    updateSallesDisplay(newValue.toLowerCase()); // Mettre à jour l'affichage des salles avec le nouveau texte de recherche
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer l'exception, éventuellement afficher un message d'erreur à l'utilisateur
                }
            });*/

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }

    }
    @FXML
    private void displaySalles() throws SQLException {

        List<Salle> salles = s.recuperer(); // Adjust this line to match your method for fetching competitions

        for (Salle salle : salles) {
            Pane salleEntry = createSalleEntry(salle);
            sallesContainer.getChildren().add(salleEntry);
        }
    }
    @FXML
    public void chercher() throws SQLException {
        updateSallesDisplay(rechercheTF.getText().toLowerCase());
    }
    private void updateSallesDisplay(String recherche) throws SQLException {
        sallesContainer.getChildren().clear(); // Effacer les salles actuellement affichées
        List<Salle> salles = s.recuperer(); // Récupérer toutes les salles depuis la base de données
        for (Salle salle : salles) {
            // Vérifier si le nom de la salle contient le texte de recherche
            if (salle.getNom().toLowerCase().contains(recherche)) {
                Pane salleEntry = createSalleEntry(salle); // Créer une entrée pour la salle
                sallesContainer.getChildren().add(salleEntry); // Ajouter l'entrée à la liste des salles affichées
            }
        }
    }

    public Pane createSalleEntry(Salle salle) {
        Pane sallePane = new Pane();
        sallePane.setPrefSize(912, 217); // Adjusted to match your FXML Pane size
        sallePane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");
        Image image = new Image("file:" + salle.getImage());

       ImageView salleImageView = new ImageView();
       // Ensure the path is correct
        salleImageView.setImage(image);
        salleImageView.setFitHeight(166);
       salleImageView.setFitWidth(179);
        salleImageView.setLayoutX(14);
       salleImageView.setLayoutY(27);

        Text salleName = new Text(300, 38,"nom de la salle :"+ salle.getNom()); // Adjusted X to align with TextArea
        salleName.setFont(new Font("Arial", 21));
        salleName.setEffect(new DropShadow());
        salleName.setUnderline(true);

        TextArea salleDescription = new TextArea("description : " +salle.getDescription());
        salleDescription.setLayoutX(215);
        salleDescription.setLayoutY(60);
        salleDescription.setPrefHeight(91);
        salleDescription.setPrefWidth(512);
        salleDescription.setEditable(false);
        salleDescription.setWrapText(true);
        salleDescription.setFocusTraversable(false);
        salleDescription.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        salleDescription.setFont(new Font("Arial", 17));
        salleDescription.setEffect(new DropShadow());

        Text salleLieu = new Text(600, 172, "Lieu: " + salle.getLieu()); // Adjusted X to align with the map icon
        salleLieu.setFont(new Font("Arial", 14));
        salleLieu.setEffect(new DropShadow());
        salleLieu.setOnMouseClicked(event->naviguezVersWeb(salle.getLieu()));

        // Assuming you want to add buttons for materials and subscriptions similar to your FXML example
        Button materielsButton = new Button("Matériels");
        materielsButton.setLayoutX(735);
        materielsButton.setLayoutY(30);
        materielsButton.setPrefSize(149, 49);
        materielsButton.setOnAction(event -> handleViewButtonAction(salle.getId()));
        // Set the button graphic if desired, assuming you have an image for it
        ImageView imageView1 = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/dumbbell.png"));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        materielsButton.setGraphic(imageView1);

        Button abonnementsButton = new Button("Abonnements");
        abonnementsButton.setLayoutX(735);
        abonnementsButton.setLayoutY(95);
        abonnementsButton.setPrefSize(149, 49);
        abonnementsButton.setOnAction(event->handleViewButtonActionAbonnement(salle.getId()));
        // Create an image view with the subscription image
        ImageView imageView = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/subscription.png"));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        abonnementsButton.setGraphic(imageView);
        // Set the button graphic if desired, assuming you have an image for it


        ImageView imageView2 = new ImageView();
        imageView2.setLayoutX(550);
        imageView2.setLayoutY(140);
        imageView2.setFitWidth(38);
        imageView2.setFitHeight(39);
        imageView2.setPickOnBounds(true);

        // Set the image to the image view
        Image image1 = new Image("file:///Users/Cyrinechalghoumi/Downloads/map.png");
        imageView2.setImage(image1);


        ImageView imageView3 = new ImageView();
        imageView3.setLayoutX(230);
        imageView3.setLayoutY(140);
        imageView3.setFitWidth(38);
        imageView3.setFitHeight(39);
        imageView3.setPickOnBounds(true);

        // Set the image to the image view
        Image image2 = new Image("file:///Users/Cyrinechalghoumi/Downloads/schedule (1).png");
        imageView3.setImage(image2);
        Text text = new Text();
        text.setLayoutX(277);
        text.setLayoutY(154);
        text.setText("horaire :                                  lundi->vendredi 8h->21h        samedi->dimanche :9h->13h30");
        text.setWrappingWidth(229.2109375);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setFont(new Font("Arial", 14));
        text.setEffect(new DropShadow());
        // Add all components to the pane
        sallePane.getChildren().addAll( salleImageView,salleName, salleDescription, salleLieu, materielsButton, abonnementsButton,text,imageView2,imageView3);

        return sallePane;
    }

    void naviguezVersMateriel(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MaterielClient.fxml"));//charger le fichier FXML
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void handleViewButtonAction(int id) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MaterielClient.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            MaterielClientController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setMateriel(id);

            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void handleViewButtonActionAbonnement(int id) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnementClient.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AbonnementClientController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setAbonnement(id);

            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void naviguezVersWeb(String search) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TestMap.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            TestMapController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setWeb(search);

            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
