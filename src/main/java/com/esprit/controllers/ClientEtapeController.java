package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.models.Exercice;
import com.esprit.services.CoursService;
import com.esprit.services.ExerciceService;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ClientEtapeController {



    private Exercice exercice;


    @FXML
    private ListView<Exercice> listView2;

    private final ExerciceService es = new ExerciceService();

    public static final String ACCOUNT_SID = "ACbe7739e913f726ee5ea8caca45b7843a";
    public static final String AUTH_TOKEN = "4d3f09669e1c0de3cc5f98a5cceead10";




    @FXML
    void initialize(int courseId) {
        List<Exercice> exercice = es.afficher3(courseId);
        ObservableList<Exercice> observableList2 = FXCollections.observableList(exercice);
        listView2.setItems(observableList2);

        listView2.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();

            private final Label etape = new Label("Etape: ");
            private final Text etapeLabel = new Text();
            private final HBox etapeBox = new HBox(etape, etapeLabel);
            private final Button messageButton = new Button("Rappele-Moi");
            private final HBox messages = new HBox(10,messageButton);
            private final VBox vBox = new VBox(10,etapeBox,messages);


            private final HBox hBox = new HBox(35, imageView, vBox);

            {
                imageView.setFitHeight(300);
                imageView.setFitWidth(300);
                hBox.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 0;" + "-fx-border-insets: 5;");
                etapeLabel.setStyle("-fx-text-fill: white;");
                vBox.setStyle("-fx-font-family: 'Comic Sans MS';" + "-fx-font-size: 14px;");

                // Rendre le texte des labels "Nom" et "Etape" en gras
                etape.setStyle("-fx-font-weight: bold;");


                messageButton.setStyle("  -fx-background-color:\n" +
                        "            #a6b5c9,\n" +
                        "            linear-gradient(#303842 0%, #3e5577 20%, #375074 100%),\n" +
                        "            linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%);\n" +
                        "    -fx-background-insets: 0 0 -1 0,0,1;\n" +
                        "    -fx-background-radius: 5,5,4;\n" +
                        "    -fx-padding: 7 30 7 30;\n" +
                        "    -fx-text-fill: #242d35;\n" +
                        "    -fx-font-family: \"Helvetica\";\n" +
                        "    -fx-font-size: 20px;\n" +
                        "    -fx-text-fill: white;" +
                        "-fx-pref-width: 250px;" +
                        "-fx-pref-height: 35px;");


                messageButton.setOnAction(event -> {
                    Exercice selectedExercice= listView2.getSelectionModel().getSelectedItem();
                    String Etape = selectedExercice.getEtape();
                    message(Etape);
                });








            }

            @Override
            protected void updateItem(Exercice item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image("file:" + item.getImage()));
                    etapeLabel.setText(item.getEtape());
                    setGraphic(hBox);
                }
            }
        });






    }



    public void message (String nom) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
                        new PhoneNumber("+21624019297"),
                        new PhoneNumber("+19492696585"),
                        nom)
                .create();

        System.out.println(message.getSid());


    }







}
