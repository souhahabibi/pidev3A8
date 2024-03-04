package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import java.util.Properties;
import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import services.Codes;
import services.UserService;
import test.MainFX;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|tn)$";

    UserService uService = new UserService();

    private final String[] Role = {"Admin", "Client", "coach"};
    private final String[] niveau = {"Debutant", "Intermediaire", "Professionnel"};


    @FXML
    private Button Addbtn;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField motDePasseTF;

    @FXML
    private TextField SpecialiteTF;

    @FXML
    private TextField numeroTF;

    @FXML
    private ComboBox<String> RecommandationTF;

    @FXML
    private TextField PoidsTF;

    @FXML
    private TextField TailleTF;

    @FXML
    private ComboBox<String> NiveauTF;

    @FXML
    private ComboBox<String> RoleTF;

    @FXML
    private Button cancelbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RoleTF.setItems(FXCollections.observableArrayList(Role));
        NiveauTF.setItems(FXCollections.observableArrayList(niveau));
        RecommandationTF.setItems(FXCollections.observableArrayList("oui", "non"));

    }

    @FXML
    public void testSaisis() {
        if (nomTF.getText().isEmpty() || emailTF.getText().isEmpty() || numeroTF.getText().isEmpty()
                || TailleTF.getText().isEmpty() || motDePasseTF.getText().isEmpty() || PoidsTF.getText().isEmpty()) {

            showAlert("Champs manquants", "Veuillez remplir tous les champs !");
        } else if (!emailTF.getText().matches(emailRegex)) {
            showAlert("Format email incorrect", "Veuillez saisir un email valide !");
        } else if (!numeroTF.getText().matches(".*\\d.*")) {
            showAlert("Format du numero invalide", "Format de numero invalide !");
        } else if (Integer.parseInt(numeroTF.getText()) < 10000000 || Integer.parseInt(numeroTF.getText()) > 99999999) {
            showAlert("Numero hors de la plage valide", "Le numero doit contenir 8 chiffres");
        } else if (motDePasseTF.getText().length() < 8 || !motDePasseTF.getText().matches(".*[A-Z].*") || !motDePasseTF.getText().matches(".*\\d.*")) {
            showAlert("Attention", "Le mot de passe doit avoir au moins 8 caractères, une lettre majuscule et un chiffre.");
        } else {
            ajouterUser();
        }
    }

    @FXML
    void ajouterUser() {
        try {
            String password = DigestUtils.md5Hex(motDePasseTF.getText());

            User newUser = new User(
                    nomTF.getText(),
                    emailTF.getText(),
                    password,
                    SpecialiteTF.getText(),
                    numeroTF.getText(),
                    RecommandationTF.getValue(),
                    PoidsTF.getText(),
                    TailleTF.getText(),
                    NiveauTF.getValue(),
                    RoleTF.getValue()
            );
            uService.ajouter(newUser);
            sendEmail();
            // Navigation vers la page d'affichage après ajout
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            RoleTF.getScene().setRoot(root);
        } catch (SQLException e) {
            showAlert("Erreur", e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void naviguezVersAffichage(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficherUser.fxml"));
            RoleTF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void sendEmail() {
        String to = emailTF.getText();
        String from = "yosri.selmi369@gmail.com";
        String host = "smtp.gmail.com";
        final String username = "yosri.selmi369@gmail.com";
        final String password = "oepayqjkgtaxnwlc";

        //setup mail server
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Codes code = new Codes();
            code.getUserBy(to);
            Integer tel = code.getByI().getNumero();
            System.out.println(tel);

            String verification = code.envoyerCode(MainFX.RestpasswordID);
            code.codemail(verification, to);
            System.out.println(MainFX.RestpasswordID);
            System.out.println(verification);
            //create mail
            MimeMessage m = new MimeMessage(session);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            m.setSubject("Verifier your account");
            m.setText("To verifier youre account  entre this code:  " + verification);

            Transport.send(m);


            System.out.println("Message sent!");

        } catch (MessagingException e) {
            e.printStackTrace();


        }

    }
}
