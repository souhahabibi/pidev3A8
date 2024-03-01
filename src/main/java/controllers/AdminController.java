package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Abonnement;
import models.Materiel;
import models.Salle;
import services.AbonnementService;
import services.MaterielService;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminController {

    SalleService s = new SalleService();
    AbonnementService a = new AbonnementService();
    @FXML
    private VBox sallesContainer;

    @FXML
    private VBox abonnementsContainer;


    @FXML
    private Button buttonAjouter;
    @FXML
    public void initialize() {
        try {
            sallesContainer.setSpacing(25);// Définition de l'espacement entre les éléments du conteneur
            displaySalles();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }

      /*  try {
            abonnementsContainer.setSpacing(25);
            displayAbonnements();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }*/
    }



    private void displaySalles() throws SQLException {

        List<Salle> salles = s.recuperer(); // Adjust this line to match your method for fetching competitions

        for (Salle salle : salles) {
            Pane salleEntry = createSalleEntry(salle);
            sallesContainer.getChildren().add(salleEntry);
        }
    }

@FXML
    public Pane createSalleEntry(Salle salle) {
            Pane sallePane = new Pane();
            sallePane.setPrefSize(880, 287);
            sallePane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

            Image image = new Image("file:" + salle.getImage());

            ImageView salleImageView = new ImageView();
            salleImageView.setImage(image);
            salleImageView.setFitHeight(150);
            salleImageView.setFitWidth(216);
            salleImageView.setLayoutX(14);
            salleImageView.setLayoutY(12);

            Text salleName = new Text(314, 36, "Nom : " + salle.getNom());
            salleName.setFont(Font.font("Arial",  16));
            salleName.setEffect(new DropShadow());

            Text salleLieu = new Text(314, 83, "Lieu : " + salle.getLieu());
            salleLieu.setFont(Font.font("Arial",  16));
            salleLieu.setEffect(new DropShadow());

            TextArea salleDescription = new TextArea("description : "+salle.getDescription());
            salleDescription.setLayoutX(300);
            salleDescription.setLayoutY(106);
            salleDescription.setPrefHeight(100);
            salleDescription.setPrefWidth(360);
            salleDescription.setEditable(false);
            salleDescription.setWrapText(true);
            salleDescription.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            salleDescription.setFont(Font.font("Arial", 16));
            salleDescription.setEffect(new DropShadow());

            ImageView salleImageView1 = new ImageView(new Image("/flaticon/gym (1).png"));
            salleImageView1.setFitHeight(30);
            salleImageView1.setFitWidth(30);
            salleImageView1.setLayoutX(275);
            salleImageView1.setLayoutY(15);

            ImageView salleImageView2 = new ImageView(new Image("/flaticon/map.png"));
            salleImageView2.setFitHeight(30);
            salleImageView2.setFitWidth(30);
            salleImageView2.setLayoutX(275);
            salleImageView2.setLayoutY(60);

            ImageView salleImageView3 = new ImageView(new Image("/flaticon/file (1).png"));
            salleImageView3.setFitHeight(30);
            salleImageView3.setFitWidth(30);
            salleImageView3.setLayoutX(275);
            salleImageView3.setLayoutY(120);

            Button modifierButton = new Button("Modifier");
            modifierButton.setLayoutX(680);
            modifierButton.setLayoutY(20);
            modifierButton.setPrefSize(159, 31);
            modifierButton.setOnAction(event -> naviguezVersMODIFY(null, salle));
// Ajouter la classe de style et la feuille de style
            modifierButton.getStyleClass().add("login-btn");
            modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
            ImageView imageView1 = new ImageView(new Image("/flaticon/note.png"));
            imageView1.setFitWidth(30);
            imageView1.setFitHeight(30);
            modifierButton.setGraphic(imageView1);
            Button supprimerButton = new Button("Supprimer");
            supprimerButton.setLayoutX(680);
            supprimerButton.setLayoutY(90);
            supprimerButton.setPrefSize(159, 31);
            supprimerButton.setOnAction(event -> deleteSalle(salle));
            supprimerButton.getStyleClass().add("login-btn");
            supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
            ImageView imageView2 = new ImageView(new Image("/flaticon/removed.png"));
            imageView2.setFitWidth(30);
            imageView2.setFitHeight(30);
            supprimerButton.setGraphic(imageView2);
    // Assuming you want to add buttons for materials and subscriptions similar to your FXML example
    Button materielsButton = new Button(" matériels");
    materielsButton.setLayoutX(200);
    materielsButton.setLayoutY(220);
    materielsButton.setPrefSize(209, 49);
    materielsButton.setOnAction(event -> handleViewButtonAction(salle.getId()));
    // Set the button graphic if desired, assuming you have an image for it
    ImageView imageView = new ImageView(new Image("/flaticon/dumbbell.png"));
    imageView.setFitWidth(30);
    imageView.setFitHeight(30);
    materielsButton.setGraphic(imageView);

    Button abonnementsButton = new Button(" abonnements");
    abonnementsButton.setLayoutX(505);
    abonnementsButton.setLayoutY(220);
    abonnementsButton.setPrefSize(209, 49);
    abonnementsButton.setOnAction(event->handleViewButtonActionAbonnement(salle.getId()));
    // Create an image view with the subscription image
    ImageView imageView4 = new ImageView(new Image("/flaticon/subscription.png"));
    imageView4.setFitWidth(30);
    imageView4.setFitHeight(30);
    abonnementsButton.setGraphic(imageView4);
            sallePane.getChildren().addAll(salleImageView,salleImageView2,salleImageView3, salleName, salleLieu, salleDescription,salleImageView1, modifierButton, supprimerButton,materielsButton,abonnementsButton);

            return sallePane;
        }

    private void handleViewButtonAction(int id) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MaterielAdmin.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            MaterielAdminController controller = loader.getController();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnementAdmin.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AbonnementAdminController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setAbonnement(id);

            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }





        @FXML
    void naviguezVersAjouterSalle(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterSalle.fxml"));//charger le fichier FXML
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }



    @FXML
    void deleteSalle(Salle salle){

        try {
            s.supprimer(salle.getId());
            // Nettoyer le contenu du conteneur avant de réafficher les salles
            sallesContainer.getChildren().clear();
            initialize();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la salle : " + e.getMessage());
        }
    }


    @FXML
    void naviguezVersMODIFY(ActionEvent event,Salle salle) {

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSalle.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierSalleController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


                // If an item is selected, pass it to the controller of the next scene
                controller.setSalle(salle);



            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    }


