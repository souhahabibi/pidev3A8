package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Cours;
import models.SharedModel;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import services.CoursService;

import java.io.IOException;
import java.util.List;

public class ClientController2 {

    @FXML
    private ListView<Cours> listView;

    private final CoursService cs = new CoursService();

    @FXML
    private ComboBox<String> languageComboBox;

    public static final String ACCOUNT_SID = "ACbe7739e913f726ee5ea8caca45b7843a";
    public static final String AUTH_TOKEN = "4d3f09669e1c0de3cc5f98a5cceead10";


    @FXML
    void initialize() {

        languageComboBox.getItems().addAll("fr", "en", "ar");
        languageComboBox.getSelectionModel().selectFirst();



        List<Cours> cours = cs.afficher();
        ObservableList<Cours> observableList = FXCollections.observableList(cours);
        listView.setItems(observableList);

        listView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label nomLabel = new Label("Nom: ");
            private final Text nom = new Text();
            private final HBox nomBox = new HBox(nomLabel, nom);

            private final Label descriptionLabel = new Label("Description: ");
            private final Text description = new Text();
            private final HBox descriptionBox = new HBox(descriptionLabel, description);

            private final Label niveauLabel = new Label("Niveau: ");
            private final Text niveau = new Text();
            private final HBox niveauBox = new HBox(niveauLabel, niveau);

            private final Button button = new Button("Voir l'exercice");
            private final Button commentaireButton = new Button("Ajouter commentaire");
            private final Button traduireButton = new Button("Traduire");

            private final Button planningButton = new Button("Planning");


            private final HBox traduireButtons = new HBox(10, traduireButton,planningButton);
            private final VBox vBox = new VBox(10, nomBox, descriptionBox, niveauBox, button, commentaireButton, traduireButtons);
            private final HBox hBox = new HBox(35, imageView, vBox);



            {
                imageView.setFitHeight(300); // ajuster la hauteur comme vous le souhaitez
                imageView.setFitWidth(300); // ajuster la largeur comme vous le souhaitez
                // changer la couleur de fond du HBox en vert clair
                hBox.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 0;" + "-fx-border-insets: 5;");
                // changer la couleur du texte en blanc
                nomLabel.setStyle("-fx-text-fill: white;" + "-fx-font-weight: bold;");
                descriptionLabel.setStyle("-fx-text-fill: white;" + "-fx-font-weight: bold;");
                niveauLabel.setStyle("-fx-text-fill: white;" + "-fx-font-weight: bold;");
                // changer la police du texte en Comic Sans MS
                vBox.setStyle("-fx-font-family: 'Comic Sans MS';" + "-fx-font-size: 14px;");
                button.setStyle("  -fx-background-color:\n" +
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
                        "-fx-pref-width: 200px;" +
                        "-fx-pref-height: 35px;");
                button.setOnAction(event -> {
                    Cours cours = getItem();
                    int courseId = cours.getId();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientExercice.fxml"));

                        // Set a custom controller factory to initialize the controller with the course ID
                        loader.setControllerFactory(controllerClass -> {
                            if (controllerClass == ClientExerciceController.class) {
                                ClientExerciceController exerciceController = new ClientExerciceController();
                                return exerciceController;
                            } else {
                                try {
                                    return controllerClass.newInstance();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                        Parent root = loader.load();

                        // Get the controller for the new scene
                        ClientExerciceController exerciceController = loader.getController();

                        // Pass the course ID to the new scene's controller
                        exerciceController.initialize(courseId);

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });



                commentaireButton.setStyle("  -fx-background-color:\n" +
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


                commentaireButton.setOnAction(event -> {

                    Cours cours = getItem();
                    int courseId = cours.getId();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientCommentaire.fxml"));

                        // Set a custom controller factory to initialize the controller with the course ID
                        loader.setControllerFactory(controllerClass -> {
                            if (controllerClass == ClientExerciceController.class) {
                                ClientExerciceController exerciceController = new ClientExerciceController();
                                return exerciceController;
                            } else {
                                try {
                                    return controllerClass.newInstance();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                        Parent root = loader.load();

                        // Get the controller for the new scene
                        ClientCommentaireController commentaireController = loader.getController();

                        // Pass the course ID to the new scene's controller
                        commentaireController.initialize(courseId);

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                });



                traduireButton.setStyle("  -fx-background-color:\n" +
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





                traduireButton.setOnAction(event -> {
                    Cours selectedCours = listView.getSelectionModel().getSelectedItem();
                    if (selectedCours != null) {
                        String originalText = selectedCours.getNom();
                        String originalText2 = selectedCours.getDescription();
                        String originalText3 = selectedCours.getNiveau();

                        // Replace with the appropriate property
                        String targetLanguage = languageComboBox.getValue();


                        try {
                            String translatedText = translate(originalText, targetLanguage);
                            String translatedText2 = translate(originalText2, targetLanguage);
                            String translatedText3 = translate(originalText3, targetLanguage);

                            // Update the property with the translated text
                            selectedCours.setNom(translatedText);
                            selectedCours.setDescription(translatedText2);
                            selectedCours.setNiveau(translatedText3);
                            // Replace with the appropriate setter method

                            // Refresh the ListView to reflect the changes
                            listView.refresh();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });



                planningButton.setStyle("  -fx-background-color:\n" +
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



                planningButton.setOnAction(event -> {

                    Cours selectedCours = listView.getSelectionModel().getSelectedItem();
                    SharedModel.setId(selectedCours.getId());


                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/Calendar.fxml"));
                        planningButton.getScene().setRoot(root);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }










                });











            }



            @Override
            protected void updateItem(Cours item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image("file:" + item.getImage()));
                    nom.setText(item.getNom());
                    description.setText(item.getDescription());
                    niveau.setText(item.getNiveau());
                    setGraphic(hBox);
                }
            }
        });







    }

    public String translate(String s, String targetLanguage) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("q", s)
                .add("target", targetLanguage)
                .build();

        Request request = new Request.Builder()
                .url("https://google-translate1.p.rapidapi.com/language/translate/v2")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", "98a074271amsh7561f5a201962ddp12b9bdjsn33c8abacbd67")

                .addHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);
            String translatedText = json.getJSONObject("data")
                    .getJSONArray("translations")
                    .getJSONObject(0)
                    .getString("translatedText");

            return translatedText;
        } else {
            System.out.println("Request failed");
        }
        return null;
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