package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import models.Competition;
import models.Organisateur;
import models.Reservation;
import services.CompetitionService;
import services.OrganisateurService;
import services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HomePageController {
    private final OrganisateurService os = new OrganisateurService();
    private final CompetitionService cs = new CompetitionService();
    private final ReservationService rs = new ReservationService();
    @FXML
    private Button buttonO_COMPETITION;
    @FXML
    private Button buttonC_SCORES;

    @FXML
    private Button buttonO_DELETE;

    @FXML
    private ListView<Competition> listViewC;

    @FXML
    private Button buttonC_DELETE;

    @FXML
    private Button buttonO_MODIFY;

    @FXML
    private Button buttonO_ADD;

    @FXML
    private ListView<Organisateur> listViewO;

    @FXML
    private Button buttonC_MODIFY;

    @FXML
    void initialize() {
        try {
            List<Organisateur> Organisateurs = os.recuperer();
            ObservableList<Organisateur> observableList = FXCollections.observableList(Organisateurs);
            listViewO.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            List<Competition> Competitions = cs.recuperer();
            ObservableList<Competition> observableList = FXCollections.observableList(Competitions);
            listViewC.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void deleteCompetition()
    {
        if(listViewC.getSelectionModel().getSelectedItem()!=null) {
            Competition newValue = listViewC.getSelectionModel().getSelectedItem();
            try {
                cs.supprimer(newValue.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        initialize();
        buttonC_DELETE.setDisable(true);
        buttonC_MODIFY.setDisable(true);
        buttonC_SCORES.setDisable(true);
    }
    @FXML
    void isEnabled()
    {
        if(listViewO.getSelectionModel().getSelectedItem()!=null) {
            buttonO_MODIFY.setDisable(false);
            buttonO_DELETE.setDisable(false);
            buttonO_COMPETITION.setDisable(false);
        }
        if(listViewC.getSelectionModel().getSelectedItem()!=null) {
            buttonC_MODIFY.setDisable(false);
            buttonC_DELETE.setDisable(false);
            buttonC_SCORES.setDisable(false);
        }
    }
    @FXML
    void deleteOrganisateur()
    {
        if(listViewO.getSelectionModel().getSelectedItem()!=null) {
            Organisateur newValue = listViewO.getSelectionModel().getSelectedItem();
            try {
                os.supprimer(newValue.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        initialize();
        buttonO_DELETE.setDisable(true);
        buttonO_MODIFY.setDisable(true);
        buttonO_COMPETITION.setDisable(true);
    }
    @FXML
    void naviguezVersADDORGANISATEUR(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterOrganisateur.fxml"));
            buttonO_ADD.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersCLIENT_AFFICHER_COMPETITION(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));
            buttonO_ADD.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYORGANISATEUR(ActionEvent event) {

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyOrganisateur.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifyOrganisateurController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            Organisateur selectedOrganisateur = listViewO.getSelectionModel().getSelectedItem();

            // Check if an item is actually selected
            if (selectedOrganisateur != null) {
                // If an item is selected, pass it to the controller of the next scene
                controller.setOrganisateur(selectedOrganisateur);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            buttonO_MODIFY.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersADDCOMPETITION(ActionEvent event) {

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCompetition.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterCompetitionController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            Organisateur selectedOrganisateur = listViewO.getSelectionModel().getSelectedItem();

            // Check if an item is actually selected
            if (selectedOrganisateur != null) {
                // If an item is selected, pass it to the controller of the next scene
                controller.setOrganisateur(selectedOrganisateur);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            buttonO_MODIFY.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYCOMPETITION(ActionEvent event) {

        ObservableList<Organisateur> observableList = null;
        try {
            List<Organisateur> Organisateurs = os.recuperer();
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

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifyCompetitionController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            Competition selectedCompetition = listViewC.getSelectionModel().getSelectedItem();
            // Check if an item is actually selected
            if (selectedCompetition != null) {
                // If an item is selected, pass it to the controller of the next scene
                controller.setCompetition(selectedCompetition,observableList);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            buttonO_MODIFY.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersSCORES(ActionEvent event) {
        ObservableList<Reservation> observableList = null;
        try {
            List<Reservation> Reservations = rs.getReservations(listViewC.getSelectionModel().getSelectedItem().getId());
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
            buttonO_MODIFY.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
