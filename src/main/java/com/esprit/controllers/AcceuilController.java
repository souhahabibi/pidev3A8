package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AcceuilController {

    private final CoursService cs = new CoursService();

    @FXML
    private TextField nomTF;
    @FXML
    private TextField descriptionTF;
    @FXML
    private RadioButton rButton1, rButton2, rButton3;
    @FXML
    private Button btnAjouter, btnAjouter1, btnAfficher, btnAfficher2;
    @FXML
    private ListView<Cours> listView;

    private Cours cours;
    private String imagePath;
    private String niveau;

    @FXML
    void NaviguerVerAjouter(ActionEvent event) {
        navigateTo("/Ajouter.fxml");
    }

    @FXML
    void NaviguerVerAjouter1(ActionEvent event) {
        navigateTo("/AjouterExercice.fxml");
    }

    @FXML
    void NaviguerVersAfficher(ActionEvent event) {
        navigateTo("/Afficher.fxml");
    }

    @FXML
    void NaviguerVersAfficher2(ActionEvent event) {
        navigateTo("/AfficherExercice.fxml");
    }

    private void navigateTo(String resource) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            btnAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void choisirImage(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                imagePath = file.getPath().replace("\\", "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AjoutertNiveau() {
        if (rButton1.isSelected()) {
            niveau = rButton1.getText();
        } else if (rButton2.isSelected()) {
            niveau = rButton2.getText();
        } else if (rButton3.isSelected()) {
            niveau = rButton3.getText();
        }
    }

    @FXML
    void AjouterCours(ActionEvent event) {
        if (isValidInput()) {
            Cours nouveauCours = new Cours(imagePath, nomTF.getText(), descriptionTF.getText(), niveau);
            if (!cs.coursExiste(nouveauCours)) {
                cs.ajouter(nouveauCours);
            } else {
                showAlert("Un cours avec les mêmes informations existe déjà.");
            }
        } else {
            showMissingFieldsAlert();
        }
    }

    private boolean isValidInput() {
        return imagePath != null && !nomTF.getText().isEmpty() && !descriptionTF.getText().isEmpty()
                && (rButton1.isSelected() || rButton2.isSelected() || rButton3.isSelected()) && nomTF.getText().matches("[a-zA-Z]*");
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showMissingFieldsAlert() {
        StringBuilder message = new StringBuilder("Veuillez remplir les champs suivants avant d'ajouter le cours :");
        if (imagePath == null) message.append("\n- Choisissez une image");
        if (nomTF.getText().isEmpty()) message.append("\n- Entrez un nom de cours");
        if (descriptionTF.getText().isEmpty()) message.append("\n- Entrez une description");
        if (!(rButton1.isSelected() || rButton2.isSelected() || rButton3.isSelected())) message.append("\n- Sélectionnez un niveau");
        if (!nomTF.getText().matches("[a-zA-Z]*")) message.append("\n- Le nom du cours doit contenir uniquement des lettres");

        showAlert(message.toString());
    }


    private void navigateTo2(String resource) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            btnAfficher2.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void NaviviguerVerAfficherCours(ActionEvent event) {
        navigateTo2("/Afficher.fxml");
    }


}
