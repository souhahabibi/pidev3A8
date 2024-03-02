package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import models.Competition;
import models.Organisateur;
import models.Reservation;
import services.CompetitionService;
import services.OrganisateurService;
import services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AdminController {
    OrganisateurService organisateurService = new OrganisateurService();
    CompetitionService competitionService = new CompetitionService();
    ReservationService reservationService = new ReservationService();
    @FXML
    private VBox OrganisateurContainer;
    @FXML
    private VBox CompetitionContainer;
    public void initialize() {
        showlistOrganisateur();
        showlistCompetition();
    }
    public void showlistOrganisateur()
    {
        try {
            OrganisateurContainer.setSpacing(25);
            displayOrganisateurs();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    @FXML
    private void displayOrganisateurs() throws SQLException {
        OrganisateurContainer.getChildren().clear(); // Clear existing competition entries
        List<Organisateur> organisateurs = organisateurService.recuperer(); // Fetch all competitions

        for (Organisateur organisateur : organisateurs) {
            Pane competitionEntry = createOrganisateurEntry(organisateur);
            OrganisateurContainer.getChildren().add(competitionEntry);
        }
    }
    @FXML
    private Pane createOrganisateurEntry(Organisateur organisateur) {
        Pane organisateurPane = new Pane();
        organisateurPane.setLayoutX(38.0);
        organisateurPane.setLayoutY(64.0);
        organisateurPane.setPrefHeight(68.0);
        organisateurPane.setPrefWidth(827.0);
        organisateurPane.setStyle("-fx-background-color: linear-gradient(to bottom right,#891b1b ,#a7473e );");

        Text nomText = new Text("Nom: " + organisateur.getNom());
        nomText.setLayoutX(37.0);
        nomText.setLayoutY(40.0);

        Text numeroText = new Text("Numero: " + organisateur.getNumero());
        numeroText.setLayoutX(301.0);
        numeroText.setLayoutY(40.0);

        // Example for an Edit button
        Button editButton = new Button();
        editButton.setStyle("-fx-background-color:white; ");
        editButton.setLayoutX(575.0);
        editButton.setLayoutY(14.0);
        ImageView editImageView = new ImageView(new Image("flaticon/bouton-modifier.png")); // Ensure the path is correct
        editImageView.setFitHeight(30.0);
        editImageView.setFitWidth(32.0);
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> naviguezVersMODIFYORGANISATEUR(null,organisateur));

        // Example for a Delete button
        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-color:white; ");
        deleteButton.setLayoutX(648.0);
        deleteButton.setLayoutY(14.0);
        ImageView deleteImageView = new ImageView(new Image("flaticon/bouton-supprimer.png")); // Ensure the path is correct
        deleteImageView.setFitHeight(30.0);
        deleteImageView.setFitWidth(32.0);
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteOrganisateur(organisateur));

        // Example for a custom button, e.g., view competitions
        Button customButton = new Button();
        customButton.setStyle("-fx-background-color:white; ");
        customButton.setLayoutX(727.0);
        customButton.setLayoutY(14.0);
        ImageView customImageView = new ImageView(new Image("flaticon/competition.png")); // Ensure the path is correct
        customImageView.setFitHeight(30.0);
        customImageView.setFitWidth(32.0);
        customButton.setGraphic(customImageView);
        customButton.setOnAction(event -> naviguezVersADDCOMPETITION(null,organisateur));

        // Add all components to the pane
        organisateurPane.getChildren().addAll(nomText, numeroText, editButton, deleteButton, customButton);

        return organisateurPane;
    }
    @FXML
    void deleteOrganisateur(Organisateur organisateur)
    {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this organizer?");

        // Show the confirmation dialog and wait for the user's response
        Optional<ButtonType> result = confirmAlert.showAndWait();

        // Check if the OK button was clicked
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose OK, proceed with deletion
            try {
                organisateurService.supprimer(organisateur.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize(); // Re-initialize to reflect changes
        } else {
            // User chose CANCEL or closed the dialog, do not delete
        }
    }
    @FXML
    void deleteCompetition(Competition competition)
    {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this competition?");

        // Show the confirmation dialog and wait for the user's response
        Optional<ButtonType> result = confirmAlert.showAndWait();

        // Check if the OK button was clicked
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose OK, proceed with deletion
            try {
                competitionService.supprimer(competition.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize(); // Re-initialize to reflect changes
        } else {
            // User chose CANCEL or closed the dialog, do not delete
        }
    }
    @FXML
    void naviguezVersADDORGANISATEUR(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterOrganisateur.fxml"));
            OrganisateurContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYORGANISATEUR(ActionEvent event,Organisateur organisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyOrganisateur.fxml"));
            Parent root = loader.load();
            ModifyOrganisateurController controller = loader.getController();
                controller.setOrganisateur(organisateur);
            OrganisateurContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYCOMPETITION(ActionEvent event,Competition competition) {

        ObservableList<Organisateur> observableList = null;
        try {
            List<Organisateur> Organisateurs = organisateurService.recuperer();
            observableList = FXCollections.observableList(Organisateurs);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyCompetition.fxml"));

            Parent root = loader.load();
            ModifyCompetitionController controller = loader.getController();
            controller.setCompetition(competition,observableList);
            CompetitionContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersADDCOMPETITION(ActionEvent event,Organisateur organisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCompetition.fxml"));
            Parent root = loader.load();
            AjouterCompetitionController controller = loader.getController();
                controller.setOrganisateur(organisateur);
            OrganisateurContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersSCORES(ActionEvent event,Competition competition) {
        ObservableList<Reservation> observableList = null;
        try {
            List<Reservation> Reservations = reservationService.getReservations(competition.getId());
            observableList = FXCollections.observableList(Reservations);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyScores.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifyScoresController controller = loader.getController();

            controller.initialize(observableList);


            // Finally, set the scene's root to switch to the new view
            CompetitionContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void showlistCompetition()
    {
        try {
            CompetitionContainer.setSpacing(25);
            displayCompetitions();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    @FXML
    private void displayCompetitions() throws SQLException {
        CompetitionContainer.getChildren().clear(); // Clear existing competition entries
        List<Competition> competitions = competitionService.recuperer(); // Fetch all competitions

        for (Competition competition : competitions) {
            Pane competitionEntry = createCompetitionEntry(competition);
            CompetitionContainer.getChildren().add(competitionEntry);
        }
    }
    @FXML
    public Pane createCompetitionEntry(Competition competition) {
        Pane competitionPane = new Pane();
        competitionPane.setLayoutX(33.0);
        competitionPane.setLayoutY(61.0);
        competitionPane.setPrefHeight(164.0);
        competitionPane.setPrefWidth(816.0);
        competitionPane.setStyle("-fx-background-color: linear-gradient(to bottom right,#891b1b ,#a7473e );");
        Text dateText = new Text("Date: " + competition.getDate().toString());
        dateText.setLayoutX(280.0);
        dateText.setLayoutY(156.0);

        Text nomText = new Text("Nom: " + competition.getNom());
        nomText.setLayoutX(280.0);
        nomText.setLayoutY(30.0);

        Text capaciteText = new Text("Capacite: " + competition.getCapacite());
        capaciteText.setLayoutX(430.0);
        capaciteText.setLayoutY(156.0);

        WebView videoWebView = new WebView();
        videoWebView.setLayoutX(5.0);
        videoWebView.setLayoutY(5.0);
        videoWebView.setPrefHeight(150.0);
        videoWebView.setPrefWidth(270.0);
        // Embed YouTube video using videoID
        String videoURL = "https://www.youtube.com/embed/" + competition.getVideoURL();
        String embedHTML = "<html><body style='margin:0;padding:0;'><iframe width='100%' height='100%' src='" + videoURL + "' frameborder='0' allowfullscreen style='border: 0'></iframe></body></html>";
        videoWebView.getEngine().loadContent(embedHTML);

        TextArea descriptionTextArea = new TextArea(competition.getDescription());
        descriptionTextArea.setLayoutX(280.0);
        descriptionTextArea.setLayoutY(44.0);
        descriptionTextArea.setPrefHeight(89.0);
        descriptionTextArea.setPrefWidth(450.0);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setWrapText(true);

        Button editButton = new Button();
        editButton.setLayoutX(740.0);
        editButton.setLayoutY(20.0);
        ImageView editImageView = new ImageView(new Image("flaticon/bouton-modifier.png"));
        editImageView.setFitHeight(27);
        editImageView.setFitWidth(27);
        editButton.setStyle("-fx-background-color:white; ");
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> naviguezVersMODIFYCOMPETITION(null,competition));

        Button deleteButton = new Button();
        deleteButton.setLayoutX(740.0);
        deleteButton.setLayoutY(60.0);
        ImageView deleteImageView = new ImageView(new Image("flaticon/bouton-supprimer.png"));
        deleteImageView.setFitHeight(27);
        deleteImageView.setFitWidth(27);
        deleteButton.setStyle("-fx-background-color:white; ");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteCompetition(competition));

        Button viewButton = new Button();
        viewButton.setLayoutX(740.0);
        viewButton.setLayoutY(100.0);
        ImageView viewImageView = new ImageView(new Image("flaticon/la-revue.png"));
        viewImageView.setFitHeight(27);
        viewImageView.setFitWidth(27);
        viewButton.setStyle("-fx-background-color:white; ");
        viewButton.setGraphic(viewImageView);
        viewButton.setOnAction(event -> naviguezVersSCORES(null,competition));

        competitionPane.getChildren().addAll(dateText, nomText, capaciteText, videoWebView, descriptionTextArea, editButton, deleteButton, viewButton);

        return competitionPane;
    }
    @FXML
    void naviguezVersCLIENT_AFFICHER_COMPETITION(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));
            CompetitionContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersSTATISTIQUE_COMPETITION(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StatistiqueCompetition.fxml"));
            CompetitionContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
