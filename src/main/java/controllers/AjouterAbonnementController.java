package controllers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.Abonnement;
import models.Materiel;
import models.Salle;
import services.AbonnementService;
import services.MaterielService;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterAbonnementController {

    @FXML
    private ChoiceBox<Salle> salleCB;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonAjouter;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private TextField montantTF;

    @FXML
    private TextField dureeTF;
    @FXML
    private Text montantCS;

    @FXML
    private Text salleCS;
    @FXML
    private Text dureeCS;

    @FXML
    private Text descriptionCS;


    private final AbonnementService ps = new AbonnementService();
    private Abonnement abonnement;

    @FXML
    void ajouterAbonnement(ActionEvent event) {
        boolean isValid = true;

        // Validation du montant
        if (montantTF.getText().isEmpty()) {
            montantCS.setVisible(true);
            isValid = false;
        } else {
            try {
                int montant = Integer.parseInt(montantTF.getText());
                if (montant <= 0) {
                    montantCS.setVisible(true);
                    isValid = false;
                } else {
                    montantCS.setVisible(false);
                }
            } catch (NumberFormatException e) {
                montantCS.setVisible(true);
                isValid = false;
            }
        }

        // Validation de la durée
        if (dureeTF.getText().isEmpty()) {
            dureeCS.setVisible(true);
            isValid = false;
        } else {
            try {
                int duree = Integer.parseInt(dureeTF.getText());
                if (duree <= 0) {
                    dureeCS.setVisible(true);
                    isValid = false;
                } else {
                    dureeCS.setVisible(false);
                }
            } catch (NumberFormatException e) {
                dureeCS.setVisible(true);
                isValid = false;
            }
        }

        // Validation de la description
        if (descriptionTF.getText().isEmpty()) {
            descriptionCS.setVisible(true);
            isValid = false;
        } else {
            descriptionCS.setVisible(false);
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

        // Si toutes les validations sont passées, ajoutez l'abonnement
        try {
            int montant = Integer.parseInt(montantTF.getText());
            int duree = Integer.parseInt(dureeTF.getText());

            // Ajouter l'abonnement si toutes les conditions sont remplies
            ps.ajouter(new Abonnement(
                    montant,
                    duree,
                    descriptionTF.getText(),
                    salleCB.getValue().getId()
            ));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le montant et la durée doivent être des nombres entiers positifs.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        naviguezVersAccueil(null);
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

    public void setAbonnement(ObservableList<Salle> observableList) {
        salleCB.setItems(observableList);
    }


}