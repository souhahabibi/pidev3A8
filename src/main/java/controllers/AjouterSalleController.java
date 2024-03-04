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
import utils.MyDatabase;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class AjouterSalleController {
    Connection connection;
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
            try {
                sendEmail("cirin.chalghoumi@gmail.com"," annoncement de l'ouverture de notre nouvelle salle"," ouverture d'une nouvelle salle "+nomTF.getText()+" à "+lieuTF +"+ ,merci");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
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
    private void mailerSender(){
        try {
            // Connect to the database and retrieve email addresses
            connection = MyDatabase.getInstance().getConnection();
            String selectQuery = "SELECT email FROM personne"; // Adjust your query accordingly
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and send email to each address
            while (resultSet.next()) {
                String to = resultSet.getString("email");
                String subject = "Your Subject Here"; // Set your email subject
                String text = "Your Email Body Here"; // Set your email body

                sendEmail(to, subject, text);
            }

            // Close database connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException | MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        final String from = "cyrine.chalghoumi@esprit.tn"; // Change to your email
        final String password = "Kuj69132"; // Change to your email password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com"); // Change to your SMTP host
        props.put("mail.smtp.port", "587"); // SMTP port (587 for TLS, 465 for SSL)
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
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
}