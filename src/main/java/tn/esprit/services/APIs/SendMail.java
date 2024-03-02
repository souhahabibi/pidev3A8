package tn.esprit.services.APIs;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendMail {
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        final String from = "compte esprit"; // Change to your email
        final String password = "password"; // Change to your email password

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

