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
    MaterielService m = new MaterielService();
    AbonnementService a = new AbonnementService();
    @FXML
    private VBox sallesContainer;
    @FXML
    private VBox materielsContainer;
    @FXML
    private VBox abonnementsContainer;
    @FXML
    private Button buttonMAjouter;
    @FXML
    private Button buttonAAjouter;
    @FXML
    public void initialize() {
        try {
            sallesContainer.setSpacing(25);
            displaySalles();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
        try {
            materielsContainer.setSpacing(25);
            displayMateriels();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
        try {
            abonnementsContainer.setSpacing(25);
            displayAbonnements();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }


    private void displayMateriels() throws SQLException {

        List<Materiel> materiels = m.recuperer();

        for (Materiel materiel : materiels) {
            Pane materielEntry = createMaterielEntry(materiel);
            materielsContainer.getChildren().add(materielEntry);
        }
    }
    private void displaySalles() throws SQLException {

        List<Salle> salles = s.recuperer(); // Adjust this line to match your method for fetching competitions

        for (Salle salle : salles) {
            Pane salleEntry = createSalleEntry(salle);
            sallesContainer.getChildren().add(salleEntry);
        }
    }
    private void displayAbonnements() throws SQLException {

        List<Abonnement> abonnements = a.recuperer(); // Adjust this line to match your method for fetching competitions

        for (Abonnement abonnement : abonnements) {
            Pane abonnementEntry = createAbonnementEntry(abonnement);
            abonnementsContainer.getChildren().add(abonnementEntry);
        }
    }
    @FXML
    public Pane createMaterielEntry(Materiel materiel) {
        Pane materielPane = new Pane();
        materielPane.setPrefSize(702, 207);
        materielPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

        Image image = new Image("file:" + materiel.getImage());
        ImageView materielImageView = new ImageView();
        materielImageView.setImage(image);
        materielImageView.setFitHeight(172);
        materielImageView.setFitWidth(189);
        materielImageView.setLayoutX(23);
        materielImageView.setLayoutY(10);

        Text materielName = new Text(249, 37, "Nom : " + materiel.getNom());
        materielName.setFont(Font.font("Arial", 16));
        materielName.setEffect(new DropShadow());

        Text age = new Text(249, 68, "Age : " + materiel.getAge());
        age.setFont(Font.font("Arial", 16));
        age.setEffect(new DropShadow());

        Text quantite = new Text(249, 102, "Quantité : " + materiel.getQuantite());
        quantite.setFont(Font.font("Arial", 16));
        quantite.setEffect(new DropShadow());

        Text prix = new Text(249, 136, "Prix : " + materiel.getPrix());
        prix.setFont(Font.font("Arial", 16));
        prix.setEffect(new DropShadow());

        Text salle = new Text(249, 170, "Salle : " + materiel.getFK_idSalle());
        salle.setFont(Font.font("Arial", 16));
        salle.setEffect(new DropShadow());

        Button modifierButton = new Button("Modifier");
        modifierButton.setLayoutX(542);
        modifierButton.setLayoutY(37);
        modifierButton.setPrefSize(159, 31);
        modifierButton.setOnAction(event -> naviguezVersMODIFYMateriel(null, materiel));
        modifierButton.getStyleClass().add("login-btn");
        modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView1 = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/note.png"));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        modifierButton.setGraphic(imageView1);
        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setLayoutX(542);
        supprimerButton.setLayoutY(126);
        supprimerButton.setPrefSize(159, 31);
        supprimerButton.setOnAction(event -> deleteMateriel(materiel));
        supprimerButton.getStyleClass().add("login-btn");
        supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView2 = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/removed.png"));
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        supprimerButton.setGraphic(imageView2);
        materielPane.getChildren().addAll(materielImageView, materielName, age, quantite, prix, salle, modifierButton, supprimerButton);

        return materielPane;

    }
@FXML
    public Pane createSalleEntry(Salle salle) {
            Pane sallePane = new Pane();
            sallePane.setPrefSize(712, 207);
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

            ImageView salleImageView1 = new ImageView(new Image("file:///C:/Users/Cyrinechalghoumi/Downloads/gym (1).png"));
            salleImageView1.setFitHeight(30);
            salleImageView1.setFitWidth(30);
            salleImageView1.setLayoutX(275);
            salleImageView1.setLayoutY(15);

            ImageView salleImageView2 = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/map.png"));
            salleImageView2.setFitHeight(30);
            salleImageView2.setFitWidth(30);
            salleImageView2.setLayoutX(275);
            salleImageView2.setLayoutY(60);

            ImageView salleImageView3 = new ImageView(new Image("file:///C:/Users/Cyrinechalghoumi/Downloads/file (1).png"));
            salleImageView3.setFitHeight(30);
            salleImageView3.setFitWidth(30);
            salleImageView3.setLayoutX(275);
            salleImageView3.setLayoutY(120);

            Button modifierButton = new Button("Modifier");
            modifierButton.setLayoutX(680);
            modifierButton.setLayoutY(40);
            modifierButton.setPrefSize(159, 31);
            modifierButton.setOnAction(event -> naviguezVersMODIFY(null, salle));
// Ajouter la classe de style et la feuille de style
            modifierButton.getStyleClass().add("login-btn");
            modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
            ImageView imageView1 = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/note.png"));
            imageView1.setFitWidth(30);
            imageView1.setFitHeight(30);
            modifierButton.setGraphic(imageView1);
            Button supprimerButton = new Button("Supprimer");
            supprimerButton.setLayoutX(680);
            supprimerButton.setLayoutY(150);
            supprimerButton.setPrefSize(159, 31);
            supprimerButton.setOnAction(event -> deleteSalle(salle));
            supprimerButton.getStyleClass().add("login-btn");
            supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
            ImageView imageView2 = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/removed.png"));
            imageView2.setFitWidth(30);
            imageView2.setFitHeight(30);
            supprimerButton.setGraphic(imageView2);
            sallePane.getChildren().addAll(salleImageView,salleImageView2,salleImageView3, salleName, salleLieu, salleDescription,salleImageView1, modifierButton, supprimerButton);

            return sallePane;
        }
        @FXML
    public Pane createAbonnementEntry(Abonnement abonnement) {
        Pane abonnementPane = new Pane();
        abonnementPane.setPrefSize(812, 230);
        abonnementPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");
            Text descriptionText = new Text(112, 50, "Description : " + abonnement.getDescription());
            descriptionText.setWrappingWidth(443);

            Text montantText = new Text(71, 180, "Montant : " + abonnement.getMontant());
            Text dureeText = new Text(329, 180, "Durée : " + abonnement.getDuree()+"mois");
            Text salleText = new Text(578, 180, "Salle : " + abonnement.getFK_idSalle());

            ImageView descriptionImageView = new ImageView(new Image("file:///C:/Users/Cyrinechalghoumi/Downloads/file (1).png"));
            descriptionImageView.setFitHeight(30);
            descriptionImageView.setFitWidth(45);
            descriptionImageView.setLayoutX(17);
            descriptionImageView.setLayoutY(40);

            ImageView montantImageView = new ImageView(new Image("file:///C:/Users/Cyrinechalghoumi/Downloads/saving.png"));
            montantImageView.setFitHeight(30);
            montantImageView.setFitWidth(45);
            montantImageView.setLayoutX(17);
            montantImageView.setLayoutY(161);

            ImageView dureeImageView = new ImageView(new Image("file:///C:/Users/Cyrinechalghoumi/Downloads/calendar (1).png"));
            dureeImageView.setFitHeight(37);
            dureeImageView.setFitWidth(44);
            dureeImageView.setLayoutX(279);
            dureeImageView.setLayoutY(161);

            ImageView salleImageView = new ImageView(new Image("file:///C:/Users/Cyrinechalghoumi/Downloads/gym (1).png"));
            salleImageView.setFitHeight(37);
            salleImageView.setFitWidth(44);
            salleImageView.setLayoutX(525);
            salleImageView.setLayoutY(161);
            Button modifierButton = new Button("Modifier");
            modifierButton.setLayoutX(642);
            modifierButton.setLayoutY(27);
            modifierButton.setPrefSize(159, 31);
            modifierButton.setOnAction(event -> naviguezVersMODIFYAbonnement(null, abonnement));
            modifierButton.getStyleClass().add("login-btn");
            modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
            ImageView imageView1 = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/note.png"));
            imageView1.setFitWidth(30);
            imageView1.setFitHeight(30);
            modifierButton.setGraphic(imageView1);
            Button supprimerButton = new Button("Supprimer");
            supprimerButton.setLayoutX(642);
            supprimerButton.setLayoutY(100);
            supprimerButton.setPrefSize(159, 31);
            supprimerButton.setOnAction(event -> deleteAbonnement(abonnement));
            supprimerButton.getStyleClass().add("login-btn");
            supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
            ImageView imageView2 = new ImageView(new Image("file:///Users/Cyrinechalghoumi/Downloads/removed.png"));
            imageView2.setFitWidth(30);
            imageView2.setFitHeight(30);
            supprimerButton.setGraphic(imageView2);
            abonnementPane.getChildren().addAll(descriptionText, montantText, dureeText, salleText, descriptionImageView, montantImageView, dureeImageView, salleImageView, modifierButton, supprimerButton);


        return abonnementPane;
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
    void naviguezVersAjouterMateriel(ActionEvent event) {
        ObservableList<Salle> observableList = null;
        try {
            List<Salle> Salles = s.recuperer();
            observableList = FXCollections.observableList(Salles);
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMateriel.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterMaterielController controller = loader.getController();

            // If an item is selected, pass it to the controller of the next scene
            controller.setMateriel(observableList);
            // Finally, set the scene's root to switch to the new view
            buttonMAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersAjouterAbbonnement(ActionEvent event) {
        ObservableList<Salle> observableList = null;
        try {
            List<Salle> Salles = s.recuperer();
            observableList = FXCollections.observableList(Salles);
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterAbonnementController controller = loader.getController();

            // If an item is selected, pass it to the controller of the next scene
            controller.setAbonnement(observableList);
            // Finally, set the scene's root to switch to the new view
            buttonAAjouter.getScene().setRoot(root);
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
    void deleteMateriel(Materiel materiel){

        try {
            m.supprimer(materiel.getId());
            // Nettoyer le contenu du conteneur avant de réafficher les salles
            materielsContainer.getChildren().clear();
            initialize();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la salle : " + e.getMessage());
        }
    }
    void deleteAbonnement(Abonnement abonnement){

        try {
            a.supprimer(abonnement.getId());
            // Nettoyer le contenu du conteneur avant de réafficher les salles
            abonnementsContainer.getChildren().clear();
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

    @FXML
    void naviguezVersMODIFYMateriel(ActionEvent event,Materiel materiel) {
        ObservableList<Salle> observableList = null;
        try {
            List<Salle> salles = s.recuperer();
            observableList = FXCollections.observableList(salles);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMateriel.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierMaterielController controller = loader.getController();

                controller.setMateriel(materiel,observableList);


            // Finally, set the scene's root to switch to the new view
            buttonMAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYAbonnement(ActionEvent event,Abonnement abonnement) {
        ObservableList<Salle> observableList = null;
        try {
            List<Salle> salles = s.recuperer();
            observableList = FXCollections.observableList(salles);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAbonnement.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierAbonnementController controller = loader.getController();

            controller.setAbonnement(abonnement,observableList);


            // Finally, set the scene's root to switch to the new view
            buttonAAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    }


