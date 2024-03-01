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

public class ModifierAbonnementController {
private Abonnement abonnement;


    @FXML
    private Button buttonCancel;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private Button buttonModifier;

    @FXML
    private TextField dureTF;

    @FXML
    private TextField montantTF;
    @FXML
    private Text montantCS;

    @FXML
    private Text dureeCS;

    @FXML
    private Text descriptionCS;
    private final AbonnementService ps = new AbonnementService();
    @FXML
    void naviguezVersAccueil(ActionEvent event) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnementAdmin.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AbonnementAdminController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setAbonnement(this.abonnement.getFK_idSalle());

            // Finally, set the scene's root to switch to the new view
            dureTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void modifierAbonnement(ActionEvent event) {
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
        if (dureTF.getText().isEmpty()) {
            dureeCS.setVisible(true);
            isValid = false;
        } else {
            try {
                int duree = Integer.parseInt(dureTF.getText());
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


        // Si l'une des validations a échoué, arrêtez le processus
        if (!isValid) {
            return;
        }

        // Si toutes les validations sont passées, modifiez l'abonnement
        try {
            int montant = Integer.parseInt(montantTF.getText());
            int duree = Integer.parseInt(dureTF.getText());

            // Modifier l'abonnement si toutes les conditions sont remplies
            abonnement.setMontant(montant);
            abonnement.setDuree(duree);
            abonnement.setDescription(descriptionTF.getText());
           // abonnement.setFK_idSalle(salleCB.getValue().getId());

            ps.modifier(abonnement);
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

    public void setAbonnement(Abonnement selectedAbonnement) {

        this.abonnement=selectedAbonnement;
        montantTF.setText(String.valueOf(selectedAbonnement.getMontant()));
        dureTF.setText(String.valueOf(selectedAbonnement.getDuree()));
        descriptionTF.setText(selectedAbonnement.getDescription());

       // salleCB.setItems(observableList);
    }

}