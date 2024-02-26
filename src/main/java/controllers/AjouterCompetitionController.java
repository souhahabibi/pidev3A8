package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Competition;
import models.Organisateur;
import services.CompetitionService;
import services.OrganisateurService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterCompetitionController {
    private final CompetitionService cs = new CompetitionService();
    private Organisateur organisateur;
    @FXML
    private TextField videoTF;

    @FXML
    private TextField organisateurTF;

    @FXML
    private TextArea DescriptionTA;

    @FXML
    private TextField capaciteTF;

    @FXML
    private DatePicker dateDP;
    @FXML
    private TextField nomTF;
    /////////////////////////////////////CS
    @FXML
    private Text dayCS;
    @FXML
    private Text capaciteCS;
    @FXML
    private Text descCS;
    @FXML
    private Text nomCS;
 ////////////////////////////////////////////////////////////////////////////////////////


    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;

        // Update UI components with the Organisateur's data
        organisateurTF.setText(organisateur.getNom());
        // Set other fields as needed
    }
    @FXML
    void ajouterCompetition(ActionEvent event) {

        boolean isValid = true;
        LocalDate localDate = dateDP.getValue();


        if (!nomTF.getText().matches("[a-zA-Z]+") || nomTF.equals("")) {
            nomCS.setVisible(true);
            isValid = false;
        }

        LocalDate today = LocalDate.now();
        if (localDate == null || !localDate.isAfter(today.plusDays(7))) {
            dayCS.setVisible(true);
            isValid = false;
        }

        try {
            int capacite = Integer.parseInt(capaciteTF.getText());
            if (capacite < 1 || capacite > 100) {
                capaciteCS.setVisible(true);
                isValid = false;
            } else {
                capaciteCS.setVisible(false);
            }
        } catch (NumberFormatException e) {
            capaciteCS.setVisible(true);
            isValid = false;
        }

        if (DescriptionTA.getText().length() > 200 || DescriptionTA.getText().length() < 40) {
            descCS.setVisible(true);
            isValid = false;
        }

        if (!isValid) {
            // If any validation fails, stop the process and show the errors
            return;
        }
        Date sqlDate = Date.valueOf(localDate);
        try {
            cs.ajouter(new Competition(
                    nomTF.getText()
                    ,DescriptionTA.getText()
                    , videoTF.getText()
                    , sqlDate
                    , Integer.parseInt(capaciteTF.getText())
                    , organisateur.getId() ));
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
            Parent root = FXMLLoader.load(getClass().getResource("/AdminCompetition.fxml"));
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
