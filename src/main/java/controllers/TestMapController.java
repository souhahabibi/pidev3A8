package controllers;

import com.twilio.Twilio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.web.WebView;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TestMapController {

    @FXML
    private WebView webMap;
   public void initialize() {



   }
   public void setWeb(String search)
   { // Example search query
       String googleMapsURL = "https://www.google.com/maps/search/" + search;

       webMap.getEngine().load(googleMapsURL);
       Twilio.init("AC898ac81c6ea24c35f79b08c18e6abbdb", "0fbf8e7bd5a9bbaa3b34000c81ab18fd");
       Message twilioMessage = Message.creator(
                       new PhoneNumber("+21652299117"),
                       new PhoneNumber("+19898502390"), // Twilio phone number
                       "secondtry")
               .create();

  }
    @FXML
    void naviguezVersClient(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));//charger le fichier FXML
            webMap.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
