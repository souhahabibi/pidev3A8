package controllers;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import models.Competition;
import models.Reservation;
import services.CompetitionService;
import services.ReservationService;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewCompetitionController {
    private Connection connection;
    CompetitionService competitionService = new CompetitionService();
    ReservationService reservationService = new ReservationService();
    Competition competition;
    int x=0,y=0;
    boolean isReserved=false;
    @FXML
    private Button joinB;
    @FXML
    private WebView videoWV;
    @FXML
    private Text leader1;
    @FXML
    private Text leader2;
    @FXML
    private Text leader3;
    @FXML
    private Text leader4;
    @FXML
    private Text leader5;
    @FXML
    private Text dateT;

    @FXML
    private Text nomT;

    @FXML
    private TextArea descriptionTA;
    @FXML
    public void setCompetition(Competition competition,int id){
        this.competition=competition;
        x=id;
      nomT.setText(competition.getNom());
      dateT.setText(String.valueOf(competition.getDate()));
      descriptionTA.setText(competition.getDescription());
        String videoURL = "https://www.youtube.com/embed/" + competition.getVideoURL();
        String embedHTML = "<html><body style='margin:0;padding:0;'><iframe width='100%' height='100%' src='" + videoURL + "' frameborder='0' allowfullscreen style='border: 0'></iframe></body></html>";
        videoWV.getEngine().loadContent(embedHTML);


        LocalDate today = LocalDate.now();
        if (competition.getDate().toLocalDate().isBefore(today) || competition.getCapacite()<0 )
        {
           joinB.setDisable(true);
           manageLeaderboard();
        }
        else
        {
            checkclient();
        }
    }
    @FXML
    public void checkclient()  {
        try {
            y=reservationService.checkReservation(x,competition.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(y != 0) {
            joinB.setText("Already Joined");
            isReserved=true;
        }
        else {
            joinB.setText("Join");
            isReserved=false;
        }
    }
    @FXML
    public void joinCompetition()
    {
      if(isReserved)
      {
          try {
              reservationService.supprimer(y);
              this.competition.setCapacite(this.competition.getCapacite()+1);
              competitionService.modifier(competition);
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
          checkclient();
      }
      else {
          try {
              sendEmail("oussema2khemiri5@gmail.com", "Competition Reservation :"+ competition.getNom(), "Your reservation has been Submitted successfully");
              // Show confirmation to the user
          } catch (MessagingException e) {
              e.printStackTrace();
              // Show error message to the user
          }
          try {
              reservationService.ajouter(new Reservation(x,competition.getId(),0));
              this.competition.setCapacite(this.competition.getCapacite()-1);
              competitionService.modifier(competition);
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
          checkclient();
      }
    }
    @FXML
    public void manageLeaderboard()
    {
        try {
            List<Reservation> reservations = reservationService.getReservations(competition.getId());

            Collections.sort(reservations, new Comparator<Reservation>() {
                @Override
                public int compare(Reservation r1, Reservation r2) {
                    return Integer.compare(r2.getScore(), r1.getScore());
                }
            });

            int leaderboardSize = Math.min(reservations.size(), 5);
            Text[] leaderboardTexts = { leader1, leader2, leader3, leader4, leader5 };

            for (int i = 0; i < leaderboardSize; i++) {
                Reservation reservation = reservations.get(i);
                String clientName = getClientName(reservation.getFk_client_id());
                String leaderboardEntry = clientName + " - Score: " + reservation.getScore();
                leaderboardTexts[i].setText(leaderboardEntry);
            }

            // Clear remaining leaderboard spots if there are less than 5 entries
            for (int i = leaderboardSize; i < 5; i++) {
                leaderboardTexts[i].setText("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));
            joinB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private String getClientName(int fkClientId) {
        connection = MyDatabase.getInstance().getConnection();
        String clientFullName = "";
        String sql = "SELECT prenom, nom FROM personne WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, fkClientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom");
                clientFullName = prenom + " " + nom; // Concatenating prenom and nom
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it, show an error message)
        }

        return clientFullName;
    }
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        final String from = "Khemiri.Oussema@esprit.tn"; // Change to your email
        final String password = "azerty1QUERTY13+"; // Change to your email password

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
}
