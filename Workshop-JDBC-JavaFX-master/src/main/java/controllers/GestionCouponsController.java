package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.coupon;
import services.couponService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GestionCouponsController {


    @javafx.fxml.FXML
    private TableColumn <coupon,Integer> codeCol;
    @javafx.fxml.FXML
    private TextField dateTF;
    @javafx.fxml.FXML
    private TableColumn <coupon,Integer> valeurCol;
    @javafx.fxml.FXML
    private TableColumn <coupon,String> nomSocieteCol;
    @javafx.fxml.FXML
    private TextField codeTF;
    @javafx.fxml.FXML
    private TextField NomSocieteTF;
    @javafx.fxml.FXML
    private TableColumn <coupon,String> dateExpirationCol;
    @javafx.fxml.FXML
    private TextField valeurTF;
    @javafx.fxml.FXML
    private TableView <coupon> TableViewCoupon;
    @javafx.fxml.FXML
    private Button Modifierbtn;
    @javafx.fxml.FXML
    private Button Supprimerbtn;
    @javafx.fxml.FXML
    private Button Ajouterbtn;
    @javafx.fxml.FXML
    private Button Clearbtn;
    private final couponService cs = new couponService();
    @FXML
    private TextField idTF;

    @FXML
    void initialize () {
        try {
            List<coupon> coupons = cs.recuperer();
            ObservableList<coupon> observableList = FXCollections.observableList(coupons);
            TableViewCoupon.setItems(observableList);
            nomSocieteCol.setCellValueFactory(new PropertyValueFactory<>("nomSociete"));
            codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
            valeurCol.setCellValueFactory(new PropertyValueFactory<>("valeur"));
            dateExpirationCol.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    @FXML
    public void updateView(Event event) {
        coupon selected = TableViewCoupon.getSelectionModel().getSelectedItem();
        NomSocieteTF.setText(selected.getNomSociete());
        codeTF.setText(String.valueOf(selected.getCode()));
        valeurTF.setText(String.valueOf(selected.getValeur()));
        dateTF.setText(selected.getDateExpiration());

    }


    @FXML
    public void ajouterCoupon(ActionEvent actionEvent) {
        if (NomSocieteTF.getText().isEmpty() || valeurTF.getText().isEmpty() || codeTF.getText().isEmpty()
                || dateTF.getText().isEmpty())  {
            showAlert("Champs manquants","Veuillez remplir tous les champs !");

        } else if (!valeurTF.getText().matches(".*\\d.*")) {
            showAlert("Format du numero invalide", "Format de numero invalide !");
        } else if (Integer.parseInt(codeTF.getText()) < 10000000 || Integer.parseInt(codeTF.getText()) > 99999999) {
            showAlert("Numero hors de la plage valide", "Le numero doit contint 8 chiffres");
        try {
            cs.ajouter(new coupon(NomSocieteTF.getText(),Integer.parseInt(codeTF.getText()), Integer.parseInt(valeurTF.getText()), dateTF.getText()));
            List<coupon> updatedCoupons = cs.recuperer();
            ObservableList<coupon> updatedObservableList = FXCollections.observableList(updatedCoupons);
            TableViewCoupon.setItems(updatedObservableList);
            clearData(actionEvent);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


}

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void clearData(ActionEvent actionEvent) {
        NomSocieteTF.clear();
        codeTF.clear();
        valeurTF.clear();
        dateTF.clear();
    }

    @FXML
    public void modifierCoupon(ActionEvent actionEvent) {
        try {
            // Get the selected coupon directly from the TableView
            coupon selectedCoupon = TableViewCoupon.getSelectionModel().getSelectedItem();

            if (selectedCoupon != null) {
                // Update the selected coupon with the new data
                selectedCoupon.setNomSociete(NomSocieteTF.getText());
                selectedCoupon.setCode(Integer.parseInt(codeTF.getText()));
                selectedCoupon.setValeur(Integer.parseInt(valeurTF.getText()));
                selectedCoupon.setDateExpiration(dateTF.getText());

                // Call the modify method in the service
                cs.modifier(selectedCoupon);

                // Refresh the TableView after modification
                List<coupon> updatedCoupons = cs.recuperer();
                ObservableList<coupon> updatedObservableList = FXCollections.observableList(updatedCoupons);
                TableViewCoupon.setItems(updatedObservableList);

                // Clear the input fields
                clearData(actionEvent);
            } else {
                // No coupon selected, show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select a coupon to modify.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error modifying the coupon: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void supprimerCoupon(ActionEvent actionEvent) {
        try {
            // Get the selected coupon directly from the TableView
            coupon selectedCoupon = TableViewCoupon.getSelectionModel().getSelectedItem();

            if (selectedCoupon != null) {
                // Display a confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation Dialog");
                confirmationAlert.setHeaderText("Confirmation");
                confirmationAlert.setContentText("Are you sure you want to delete this coupon?");

                Optional<ButtonType> result = confirmationAlert.showAndWait();

                // Check user's choice
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // User clicked OK, proceed with deletion
                    cs.supprimer(selectedCoupon.getCode());

                    // Refresh the TableView after deletion
                    List<coupon> updatedCoupons = cs.recuperer();
                    ObservableList<coupon> updatedObservableList = FXCollections.observableList(updatedCoupons);
                    TableViewCoupon.setItems(updatedObservableList);

                    // Clear the input fields
                    clearData(actionEvent);
                }
            } else {
                // No coupon selected, show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select a coupon to delete.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error deleting the coupon: " + e.getMessage());
            alert.showAndWait();
        }
    }
}





