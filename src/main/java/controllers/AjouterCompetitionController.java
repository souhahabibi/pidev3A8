package controllers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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
import utils.MyDatabase;

import java.io.IOException;
import java.sql.*;
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
    private Connection connection;
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
        }else nomCS.setVisible(false);

        LocalDate today = LocalDate.now();
        if (localDate == null || !localDate.isAfter(today.plusDays(7))) {
            dayCS.setVisible(true);
            isValid = false;
        }else dayCS.setVisible(false);

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
        try {
            sendSMS(nomTF.getText());
            cs.ajouter(new Competition(
                    nomTF.getText()
                    ,DescriptionTA.getText()
                    , videoTF.getText()
                    , sqlDate
                    , Integer.parseInt(capaciteTF.getText())
                    , this.organisateur ));
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
            nomTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void sendSMS(String competitionNom)
    {
        connection = MyDatabase.getInstance().getConnection();
        String sql = "SELECT age FROM personne"; // Adjusted SQL to select phone numbers

        try {
            // Initialize Twilio
            Twilio.init("ACb673e9d11227fdee0c801286ffea1058", "bd78c4713bdc9c2ada3603f1443acec2");

            // Prepare and execute SQL statement
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                /*
                // Loop through all phone numbers
                while (resultSet.next()) {
                    String clientPhoneNumber = resultSet.getString("numero");
                    Message twilioMessage = Message.creator(
                                    new PhoneNumber(clientPhoneNumber), // Client's phone number
                                    new PhoneNumber("+17653054740"), // Your Twilio phone number
                                    "A new competition has been added: " + competitionNom)
                            .create();
                }
                */
                Message twilioMessage = Message.creator(
                                new PhoneNumber("+21623061687"), // Client's phone number
                                new PhoneNumber("+17653054740"), // Your Twilio phone number
                                "A new competition has been added: " + competitionNom)
                        .create();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
