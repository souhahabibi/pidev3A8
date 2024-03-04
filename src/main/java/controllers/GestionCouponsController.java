package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.User;
import models.coupon;
import services.UserService;
import services.couponService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GestionCouponsController {


    @javafx.fxml.FXML
    private TableColumn<coupon, Integer> codeCol;
    @javafx.fxml.FXML
    private TextField dateTF;
    @javafx.fxml.FXML
    private TableColumn<coupon, Integer> valeurCol;
    @javafx.fxml.FXML
    private TableColumn<coupon, String> nomSocieteCol;
    @javafx.fxml.FXML
    private TextField codeTF;
    @javafx.fxml.FXML
    private TextField NomSocieteTF;
    @javafx.fxml.FXML
    private TableColumn<coupon, String> dateExpirationCol;
    @javafx.fxml.FXML
    private TextField valeurTF;
    @javafx.fxml.FXML
    private TableView<coupon> TableViewCoupon;
    @javafx.fxml.FXML
    private Button Modifierbtn;
    @javafx.fxml.FXML
    private Button Supprimerbtn;
    @javafx.fxml.FXML
    private Button Ajouterbtn;
    @javafx.fxml.FXML
    private Button Clearbtn;
    @FXML
    private TableColumn<coupon, Integer> uservalue;

    @FXML
    private ComboBox<User> user;
    private final couponService cs = new couponService();
    @FXML
    private TextField idTF;
    @FXML
    private Button retourBtn;
    private final UserService userService = new UserService();

    @FXML
    void initialize() {
        try {
            List<coupon> coupons = cs.recuperer();
            ObservableList<coupon> observableList = FXCollections.observableList(coupons);
            TableViewCoupon.setItems(observableList);
            nomSocieteCol.setCellValueFactory(new PropertyValueFactory<>("nomSociete"));
            codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
            valeurCol.setCellValueFactory(new PropertyValueFactory<>("valeur"));
            dateExpirationCol.setCellValueFactory(new PropertyValueFactory<>("dateExpiration"));
            uservalue.setCellValueFactory(new PropertyValueFactory<>("user"));
            // Populate the ComboBox with the list of users
            List<User> users = userService.recuperer();
            ObservableList<User> userObservableList = FXCollections.observableList(users);
            user.setConverter(new StringConverter<User>() {
                @Override
                public String toString(User user) {
                    return user == null ? null : user.getNom() ;
                }

                @Override
                public User fromString(String string) {
                    // This method is not used in this example
                    return null;
                }
            });

            user.setItems(userObservableList);


            // Set up the uservalue TableColumn in the TableView


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
        User selectedUser = user.getValue();
        try {
            // Validate input fields before proceeding
            String errorMessage = validateInput();
            if (errorMessage.isEmpty()) {
                cs.ajouter(new coupon(NomSocieteTF.getText(), Integer.parseInt(codeTF.getText()), Integer.parseInt(valeurTF.getText()), dateTF.getText(), selectedUser.getId()));
                List<coupon> updatedCoupons = cs.recuperer();
                ObservableList<coupon> updatedObservableList = FXCollections.observableList(updatedCoupons);
                TableViewCoupon.setItems(updatedObservableList);
                clearData(actionEvent);
            } else {
                showAlert("Invalid Input", errorMessage);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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

                // Update the user value based on the selected user from the ComboBox
                User selectedUser = user.getValue();
                if (selectedUser != null) {
                    selectedCoupon.setUser(selectedUser.getId());
                }

                // Validate input fields before proceeding
                String errorMessage = validateInput();
                if (errorMessage.isEmpty()) {
                    // Call the modify method in the service
                    cs.modifier(selectedCoupon);

                    // Refresh the TableView after modification
                    List<coupon> updatedCoupons = cs.recuperer();
                    ObservableList<coupon> updatedObservableList = FXCollections.observableList(updatedCoupons);
                    TableViewCoupon.setItems(updatedObservableList);

                    // Clear the input fields
                    clearData(actionEvent);
                } else {
                    showAlert("Invalid Input", "Please enter valid data in all fields.");
                }
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

    @FXML
    void getusers(ActionEvent event) {
        User selectedUser = user.getValue();
        if (selectedUser != null) {
            System.out.println("Selected User: " + selectedUser.getNom());
        }
    }

    @FXML
    void goTohome(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/afficherUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    private String validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        if (!isValidNomSociete()) {
            errorMessage.append("Invalid Nom Societe. It should contain only letters.\n");
        }

        if (!isValidCode()) {
            errorMessage.append("Invalid Code. It should be a number between 0 and 100.\n");
        }

        if (!isValidValeur()) {
            errorMessage.append("Invalid Valeur. It should be a number between 0 and 100.\n");
        }

        if (!isValidDateExpiration()) {
            errorMessage.append("Invalid Date Expiration. It should be in the format YYYYMMDD.\n");
        }

        return errorMessage.toString();
    }
    private boolean isValidNomSociete() {
        return !NomSocieteTF.getText().isEmpty() && NomSocieteTF.getText().matches("[a-zA-Z]+");
    }

    private boolean isValidCode() {
        String codeText = codeTF.getText();
        return !codeText.isEmpty() && codeText.matches("\\d+") ;
    }

    private boolean isValidValeur() {
        String valeurText = valeurTF.getText();
        return !valeurText.isEmpty() && valeurText.matches("\\d+") && Integer.parseInt(valeurText) >= 0 && Integer.parseInt(valeurText) <= 100;
    }

    private boolean isValidDateExpiration() {
        String dateText = dateTF.getText();
        return !dateText.isEmpty() && dateText.matches("\\d{8}");
    }
}





