package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.UserService;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierUserController implements Initializable {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";

    UserService uService = new UserService();

    private final String[] Role = {"Admin", "Client", "coach"};
    private final String[] niveau = {"Debutant", "Intermediaire", "Professionnel"};
    private final String[] Recommandation = {"oui", "non"};

    @FXML
    private TextField numeroTF;

    @FXML
    private ComboBox<String> RecommandationTF;

    @FXML
    private ComboBox<String> NiveauTF;

    @FXML
    private TextField emailTF;

    @FXML
    private ComboBox<String> RoleTF;

    @FXML
    private Button Savebtn;

    @FXML
    private TextField motDePasseTF;

    @FXML
    private TextField PoidsTF;

    @FXML
    private Button Cancelbtn;

    @FXML
    private TextField TailleTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField specialiteTF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoleTF.setItems(FXCollections.observableArrayList(Role));
        NiveauTF.setItems(FXCollections.observableArrayList(niveau));
        RecommandationTF.setItems(FXCollections.observableArrayList(Recommandation));
    }

    public void populateFields(User user) {
        // Populate form fields with user's information
        nomTF.setText(user.getNom());
        emailTF.setText(user.getEmail());
        numeroTF.setText(String.valueOf(user.getNumero()));
        motDePasseTF.setText(user.getMotDePasse());
        specialiteTF.setText(user.getSpecialite());
        RecommandationTF.setValue(user.getRecommandation());
        RoleTF.setValue(user.getRole());
        PoidsTF.setText(String.valueOf(user.getPoids()));
        TailleTF.setText(String.valueOf(user.getTaille()));
        NiveauTF.setValue((user.getNiveau()));
    }

    @FXML
    public void testSaisis() {
        if (isAnyFieldEmpty()) {
            showAlert("Champs manquants", null, "Veuillez remplir tous les champs !");
        } else if (!emailTF.getText().matches(emailRegex)) {
            showAlert("Format email incorrect", null, "Veuillez saisir un email valide !");
        } else if (!numeroTF.getText().matches(".*\\d.*") || !isPhoneNumberValid()) {
            showAlert("Format du numéro invalide", null, "Le numéro doit contenir 8 chiffres !");
        } else if (motDePasseTF.getText().length() < 8 || !isPasswordValid()) {
            showAlert("Mot de passe invalide", null, "Le mot de passe doit avoir au moins 8 caractères, une lettre majuscule et un chiffre.");
        }
        else saveUser();
    }
public void saveUser(){
    try {


        uService.modifier(new User(nomTF.getText(),emailTF.getText(),motDePasseTF.getText(),specialiteTF.getText(), numeroTF.getText(), RecommandationTF.getValue(), PoidsTF.getText(), TailleTF.getText(),NiveauTF.getValue(), RoleTF.getValue()));
    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    //}

    try {
        Parent root = FXMLLoader.load(getClass().getResource("/afficherUser.fxml"));
        RoleTF.getScene().setRoot(root);
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }

}


    private boolean isAnyFieldEmpty() {
        return nomTF.getText().isEmpty() || emailTF.getText().isEmpty() || numeroTF.getText().isEmpty()
                || TailleTF.getText().isEmpty() || motDePasseTF.getText().isEmpty() || PoidsTF.getText().isEmpty();
    }

    private boolean isPhoneNumberValid() {
        int phoneNumber = Integer.parseInt(numeroTF.getText());
        return phoneNumber >= 10000000 && phoneNumber <= 99999999;
    }

    private boolean isPasswordValid() {
        return motDePasseTF.getText().matches(".*[A-Z].*") && motDePasseTF.getText().matches(".*\\d.*");
    }

    public void naviguezVersAffichage(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherUser.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            showAlert("Erreur de navigation", null, e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
