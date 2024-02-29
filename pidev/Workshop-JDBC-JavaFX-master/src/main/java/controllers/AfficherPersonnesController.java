package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Personne;
import services.PersonneService;

import java.sql.SQLException;
import java.util.List;

public class AfficherPersonnesController {

    private final PersonneService ps = new PersonneService();

    @FXML
    private TableView<Personne> tableView;

    @FXML
    private TableColumn<Personne, String> nomCol;

    @FXML
    private TableColumn<Personne, String> prenomCol;

    @FXML
    private TableColumn<Personne, Integer> ageCol;

    @FXML
    void initialize() {
        try {
            List<Personne> personnes = ps.recuperer();
            ObservableList<Personne> observableList = FXCollections.observableList(personnes);
            tableView.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
