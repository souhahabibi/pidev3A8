package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.Competition;
import models.Organisateur;
import services.CompetitionService;
import services.OrganisateurService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class ModifyCompetitionController {
    private final CompetitionService ps = new CompetitionService();
    private Competition competition;
    @FXML
    private TextField videoTF;
    @FXML
    private TextArea DescriptionTA;
    @FXML
    private TextField capaciteTF;
    @FXML
    private DatePicker dateDP;
    @FXML
    private TextField nomTF;
    @FXML
    private ChoiceBox<Organisateur> organisateurCB;

    @FXML
    private Text capaciteCS;
    @FXML
    private Text organisateurCS;
    @FXML
    private Text descCS;
    @FXML
    private Text nomCS;
    @FXML
    private Text dateCS;

    @FXML
    public void setCompetition(Competition competition,ObservableList<Organisateur> observableList) {
        this.competition = competition;

        nomTF.setText(competition.getNom());
        DescriptionTA.setText(competition.getDescription());
        videoTF.setText(competition.getVideoURL());
        dateDP.setValue(competition.getDate().toLocalDate());
        capaciteTF.setText(String.valueOf(competition.getCapacite()));

        organisateurCB.setItems(observableList);

    }
    @FXML
    void updateCompetition(ActionEvent event)
    {
        boolean isValid = true;
        LocalDate localDate = dateDP.getValue();


        if (!nomTF.getText().matches("[a-zA-Z]+") || nomTF.equals("")) {
            nomCS.setVisible(true);
            isValid = false;
        }else nomCS.setVisible(false);

        LocalDate today = LocalDate.now();
        if (localDate == null ) {
            dateCS.setVisible(true);
            isValid = false;
        }else dateCS.setVisible(false);

        if(!(organisateurCB.getValue() instanceof Organisateur))
        {
            organisateurCS.setVisible(true);
            isValid=false;
        }else organisateurCS.setVisible(false);

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
        }else descCS.setVisible(false);

        if (!isValid) {
            // If any validation fails, stop the process and show the errors
            return;
        }
        Date sqlDate = Date.valueOf(localDate);
        competition.setNom(nomTF.getText());
        competition.setDate(Date.valueOf(dateDP.getValue()));
        competition.setDescription(DescriptionTA.getText());
        competition.setCapacite(Integer.parseInt(capaciteTF.getText()));
        competition.setVideoURL(videoTF.getText());
        competition.setFk_organisateur_id(organisateurCB.getValue());

        try {
            ps.modifier(competition);
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
