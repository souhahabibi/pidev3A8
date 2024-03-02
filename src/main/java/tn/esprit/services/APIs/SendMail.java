package tn.esprit.services.APIs;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
public class SendMail {
    // Créez une méthode pour envoyer un e-mail sans authentification
    public static void envoyerEmailSansAuthentification(String destinataire, String sujet, String contenu) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Port SMTP pour Gmail avec TLS

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("yosri.selmi369@gmail.com", "oepayqjkgtaxnwlc");
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("yosri.selmi369@gmail.com")); // Remplacez par votre adresse e-mail
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);

            // Envoyer le message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}