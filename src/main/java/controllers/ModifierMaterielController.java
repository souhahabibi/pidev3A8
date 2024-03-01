package controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class ModifierMaterielController {
    @FXML
    private ImageView imageView;
    private String imagePath;
    private Materiel materiel;
    @FXML
    private TextField nom_TF;

    @FXML
    private TextField age_TF;

    @FXML
    private TextField prix_TF;

    @FXML
    private Button buttonMModifier;

    @FXML
    private Button buttonCancel;


    @FXML
    private TextField quantite_TF;

    @FXML
    private Text quantiteCS;

    @FXML
    private Text ageCS;

    @FXML
    private Text prixCS;
    @FXML
    private Text nomCS;


    private final MaterielService ps = new MaterielService();
    @FXML
    void naviguezVersAccueil(ActionEvent event) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MaterielAdmin.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            MaterielAdminController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setMateriel(materiel.getFK_idSalle());

            // Finally, set the scene's root to switch to the new view
            buttonMModifier.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void modifierMateriel(ActionEvent event) {
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



        // Si l'une des validations a échoué, arrêtez le processus
        if (!isValid) {
            return;
        }

        // Si toutes les validations sont passées, procéder à la modification
        try {
            int age = Integer.parseInt(age_TF.getText());
            int quantite = Integer.parseInt(quantite_TF.getText());
            int prix = Integer.parseInt(prix_TF.getText());

            // Vérifier si les valeurs numériques sont positives
            if (age <= 0 || quantite <= 0 || prix <= 0) {
                throw new NumberFormatException("Les valeurs numériques doivent être positives.");
            }

            // Procéder à la modification du matériel
            materiel.setNom(nom_TF.getText());
            materiel.setAge(age);
            materiel.setQuantite(quantite);
            materiel.setPrix(prix);

            Image image = new Image("file:" + materiel.getImage());
            imageView.setImage(image);
            materiel.setImage(imagePath);

            ps.modifier(materiel);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Veuillez saisir des valeurs numériques valides et positives.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        naviguezVersAccueil(null);
    }

    @FXML
    public void setMateriel(Materiel selectedMateriel) {

        this.materiel=selectedMateriel;
        nom_TF.setText(selectedMateriel.getNom());
        age_TF.setText(String.valueOf(selectedMateriel.getAge()));
        quantite_TF.setText(String.valueOf(selectedMateriel.getQuantite()));
        prix_TF.setText(String.valueOf(selectedMateriel.getPrix()));
        //salle_TF.setItems(observableList);
    Image image = new Image("file:" + selectedMateriel.getImage());
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
