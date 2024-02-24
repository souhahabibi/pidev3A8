package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.services.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class AfficherController {


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

            private final VBox vBox = new VBox(nomBox, descriptionBox, niveauBox);
            private final HBox hBox = new HBox(35, imageView, vBox);

            {
                imageView.setFitHeight(50); // ajuster la hauteur comme vous le souhaitez
                imageView.setFitWidth(50); // ajuster la largeur comme vous le souhaitez
                // changer la couleur de fond du HBox en vert clair
                hBox.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 0;" + "-fx-border-insets: 5;");
                // changer la couleur du texte en blanc
                nomLabel.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
                descriptionLabel.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
                niveauLabel.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
                // changer la police du texte en Comic Sans MS
                vBox.setStyle("-fx-font-family: 'Comic Sans MS';" + "-fx-font-size: 14px;");
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
