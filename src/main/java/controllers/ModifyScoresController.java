package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import models.Reservation;
import services.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Organisateur;
import services.OrganisateurService;
import javafx.scene.control.ListView;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyScoresController {
    private Connection connection;
    @FXML
    private ListView<Reservation> listviewS;
    @FXML
    private TextField scoreTF;
    private Reservation reservation;
    private final ReservationService ps = new ReservationService();


    private String getClientName(int fkClientId) {
        connection = MyDatabase.getInstance().getConnection();
        String clientName = "";
        String sql = "SELECT prenom FROM personne WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, fkClientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                clientName = resultSet.getString("prenom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it, show an error message)
        }

        return clientName;
    }
    public void refreshListView() {
        try {
            // Assuming you have a method in ReservationService to fetch all reservations
            // or modify it to fetch based on your criteria.
            ObservableList<Reservation> updatedList = FXCollections.observableArrayList(ps.getReservations(reservation.getFk_competition_id()));
            listviewS.setItems(updatedList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to refresh the data: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void UpdateScore() {
        if(listviewS.getSelectionModel().getSelectedItem()!=null) {
            reservation = listviewS.getSelectionModel().getSelectedItem();
            reservation.setScore(Integer.parseInt(scoreTF.getText()));
            try {
                ps.modifier(reservation);
                refreshListView();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
    public void initialize(ObservableList<Reservation> observableList) {

        listviewS.setItems(observableList);

        listviewS.setCellFactory(lv -> new TextFieldListCell<>(new StringConverter<Reservation>() {
            @Override
            public String toString(Reservation reservation) {
                // Fetch the client name using the fk_client_id from the reservation
                String clientName = getClientName(reservation.getFk_client_id());
                return "Score: " + reservation.getScore() + ", Client: " + clientName;
            }

            @Override
            public Reservation fromString(String string) {
                // Not needed for display purposes
                return null;
            }
        }));
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AdminCompetition.fxml"));
            scoreTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void isSelected(MouseEvent mouseEvent) {
        if(listviewS.getSelectionModel().getSelectedItem()!=null)
        {
            scoreTF.setText(String.valueOf(listviewS.getSelectionModel().getSelectedItem().getScore()));
        }
    }
}
