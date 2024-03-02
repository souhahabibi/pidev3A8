package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Organisateur;
import services.OrganisateurService;

import java.io.IOException;
import java.sql.SQLException;

public class ModifyOrganisateurController {
    private final OrganisateurService ps = new OrganisateurService();
    private Organisateur organisateur;
    @FXML
    private TextField nomTF;
    @FXML
    private TextField numTF;
    @FXML
    private Text numeroCS;
    @FXML
    private Text numeroCS1;
    @FXML
    private Text nomCS;

    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;

        // Update UI components with the Organisateur's data
        nomTF.setText(organisateur.getNom());
        numTF.setText(organisateur.getNumero());
        // Set other fields as needed
    }
    @FXML
    void updateOrganisateur(ActionEvent event)
    {
        boolean isValid = true;
        if (!nomTF.getText().matches("[a-zA-Z]+") || nomTF.equals("")) {
            nomCS.setVisible(true);
            isValid = false;
        }else nomCS.setVisible(false);

        try {
            int num = Integer.parseInt(numTF.getText());
            if (numTF.getLength()< 8) {
                numeroCS.setVisible(true);
                numeroCS1.setVisible(true);
                isValid = false;
            } else {
                numeroCS.setVisible(false);
                numeroCS1.setVisible(false);
            }
        } catch (NumberFormatException e) {
            numeroCS.setVisible(true);
            numeroCS1.setVisible(true);
            isValid = false;
        }

        if (!isValid) {
            // If any validation fails, stop the process and show the errors
            return;
        }
        organisateur.setNom(nomTF.getText());
        organisateur.setNumero(numTF.getText());

        try {
            ps.modifier(organisateur);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        naviguezVersAffichage(null);
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));
            numTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
