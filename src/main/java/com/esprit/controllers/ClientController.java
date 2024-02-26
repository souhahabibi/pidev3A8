package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.services.CoursService;
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

public class ClientController {

    @FXML
    private ListView<Cours> listView;

    private final CoursService cs = new CoursService();

    @FXML
    void initialize() {
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
            private final VBox vBox = new VBox(10, nomBox, descriptionBox, niveauBox, button);
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
                    int id = cours.getId();
                    try {
                        // Charger le fichier FXML pour la nouvelle scène
                        Parent root = FXMLLoader.load(getClass().getResource("/ClientExercice.fxml"));

                        // Créer une nouvelle scène
                        Scene scene = new Scene(root);

                        // Obtenir la fenêtre principale
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        // Définir la nouvelle scène sur la fenêtre principale
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
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
}
