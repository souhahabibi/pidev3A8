package controllers;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import models.Materiel;
import models.Salle;
import services.MaterielService;
import services.SalleService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterMaterielController {
    @FXML
    ImageView imageView;
    private String imagePath;

    @FXML
    private TextField nom_TF;

    @FXML
    private ChoiceBox<Salle> salleCB;

    @FXML
    private TextField age_TF;

    @FXML
    private TextField prix_TF;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonAjouter;

    @FXML
    private TextField quantite_TF;
    @FXML
    private Text quantiteCS;

    @FXML
    private Text ageCS;
    @FXML
    private Text salleCS;
    @FXML
    private Text prixCS;
    @FXML
    private Text nomCS;


    private final MaterielService ps = new MaterielService();
    private Materiel materiel;
    @FXML
    void ajouterMateriel(ActionEvent event) {
        boolean isValid = true;

        // Validation du nom
        if (nom_TF.getText().isEmpty()) {
            nomCS.setVisible(true);
            isValid = false;
        } else {
            nomCS.setVisible(false);
        }

        // Validation de l'âge
        if (age_TF.getText().isEmpty()) {
            ageCS.setVisible(true);
            isValid = false;
        } else {
            try {
                int age = Integer.parseInt(age_TF.getText());
                if (age <= 0) {
                    ageCS.setVisible(true);
                    isValid = false;
                } else {
                    ageCS.setVisible(false);
                }
            } catch (NumberFormatException e) {
                ageCS.setVisible(true);
                isValid = false;
            }
        }

        // Validation de la quantité
        if (quantite_TF.getText().isEmpty()) {
            quantiteCS.setVisible(true);
            isValid = false;
        } else {
            try {
                int quantite = Integer.parseInt(quantite_TF.getText());
                if (quantite <= 0) {
                    quantiteCS.setVisible(true);
                    isValid = false;
                } else {
                    quantiteCS.setVisible(false);
                }
            } catch (NumberFormatException e) {
                quantiteCS.setVisible(true);
                isValid = false;
            }
        }

        // Validation du prix
        if (prix_TF.getText().isEmpty()) {
            prixCS.setVisible(true);
            isValid = false;
        } else {
            try {
                int prix = Integer.parseInt(prix_TF.getText());
                if (prix <= 0) {
                    prixCS.setVisible(true);
                    isValid = false;
                } else {
                    prixCS.setVisible(false);
                }
            } catch (NumberFormatException e) {
                prixCS.setVisible(true);
                isValid = false;
            }
        }

        // Validation de la salle
        if (salleCB.getValue() == null) {
            salleCS.setVisible(true);
            isValid = false;
        } else {
            salleCS.setVisible(false);
        }

        // Si l'une des validations a échoué, arrêtez le processus
        if (!isValid) {
            return;
        }

        try {
            int age = Integer.parseInt(age_TF.getText());
            int quantite = Integer.parseInt(quantite_TF.getText());
            int prix = Integer.parseInt(prix_TF.getText());

            ps.ajouter(new Materiel(
                    nom_TF.getText(),
                    age,
                    quantite,
                    prix,
                    salleCB.getValue().getId(),
                    imagePath
            ));
            naviguezVersAccueil(null);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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

    public void setMateriel(ObservableList<Salle> observableList) {
        salleCB.setItems(observableList);
    }
}
