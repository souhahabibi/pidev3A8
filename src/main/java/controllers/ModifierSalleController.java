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
import models.Salle;
import services.SalleService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ModifierSalleController {
    private Salle salle;

    @FXML
    private Button buttonSModifier;

    @FXML
    private Button buttonSCancel;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField nomTF;

    @FXML
    private TextArea descriptionTA;
    private String imagePath;
    @FXML
    private TextField lieuTF;
    @FXML
    private Text descCS;
    @FXML
    private Text lieuCS;

    @FXML
    private Text nomCS;
    private final SalleService ps = new SalleService();
    @FXML
    void naviguezVersAccueil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/admin.fxml"));
            buttonSCancel.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
void modifierSalle(ActionEvent event){
        boolean isValid = true;
// Validation du nom
        if (nomTF.getText().isEmpty() || !nomTF.getText().matches("[a-zA-Z]+")) {
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

// Si toutes les validations sont passées, modifiez la salle
        salle.setLieu(lieuTF.getText());
        salle.setNom(nomTF.getText());
        salle.setDescription(descriptionTA.getText());
        Image image = new Image("file:" + salle.getImage());
        imageView.setImage(image);
        salle.setImage(imagePath);
        try {
            ps.modifier(salle);
            naviguezVersAccueil(null);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

}

    public void setSalle(Salle selectedSalle) {

        this.salle=selectedSalle;
        nomTF.setText(selectedSalle.getNom());
        descriptionTA.setText(selectedSalle.getDescription());
        lieuTF.setText(selectedSalle.getLieu());
        Image image = new Image("file:" + selectedSalle.getImage());
        imageView.setImage(image);
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

}
