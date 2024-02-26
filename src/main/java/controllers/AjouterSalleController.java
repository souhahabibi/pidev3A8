package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.Personne;
import models.Salle;
import services.SalleService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterSalleController {
    @FXML
    ImageView imageView;
    @FXML
    private Button buttonCancel;
    private String imagePath;
    @FXML
    private TextField nomTF;

    @FXML
    private TextArea descriptionTA;

    @FXML
    private TextField lieuTF;

    @FXML
    private Text descCS;
    @FXML
    private Text lieuCS;

    @FXML
    private Text nomCS;
    @FXML
    private Button buttonAjouterSalle;
    private final SalleService ps = new SalleService();

    @FXML
    void ajouterSalle(ActionEvent event) {
        boolean isValid = true;

        // Validation du nom
        if (!nomTF.getText().matches("[a-zA-Z]+") || nomTF.getText().isEmpty()) {
            nomCS.setVisible(true);
            isValid = false;
        } else {
            nomCS.setVisible(false);
        }

        // Validation de la description
        if (descriptionTA.getText().isEmpty()) {
            descCS.setVisible(true);
            isValid = false;
        } else {
            descCS.setVisible(false);
        }

        // Validation du lieu
        if (lieuTF.getText().isEmpty()) {
            lieuCS.setVisible(true);
            isValid = false;
        } else {
            lieuCS.setVisible(false);
        }

        // Si l'une des validations a échoué, arrêtez le processus
        if (!isValid) {
            return;
        }

        // Si toutes les validations sont passées, ajoutez la salle
        try {
            ps.ajouter(new Salle(nomTF.getText(), descriptionTA.getText(), lieuTF.getText(), imagePath));
            naviguezVersAccueil(null);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void afficherAlerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void choisirImage(ActionEvent event) {

            try {
                // Créer un FileChooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

                // Ouvrir le FileChooser
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    // Charger l'image
                    Image image = new Image(file.toURI().toString());

                    // Afficher l'image (par exemple dans un ImageView)
                    // Remplacez imageView par l'élément d'interface utilisateur approprié où vous souhaitez afficher l'image

                    imageView.setImage(image);

                    // Enregistrez le chemin d'accès à l'image
                    imagePath = file.getAbsolutePath(); // ou file.toURI().toString() si vous préférez stocker l'URL
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @FXML
    void naviguezVersAccueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));
            buttonCancel.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}