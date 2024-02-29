package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.models.Exercice;
import com.esprit.services.CoursService;
import com.esprit.services.ExerciceService;
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

public class ClientExerciceController {

    private Exercice exercice;


    @FXML
    private ListView<Exercice> listView2;

    private final ExerciceService es = new ExerciceService();

    private final CoursService cs = new CoursService();





    @FXML
    void initialize() {

        int courseId = cs.getCoursId();
        List<Exercice> exercice = es.afficher2(courseId);
    ObservableList<Exercice> observableList2 = FXCollections.observableList(exercice);
        listView2.setItems(observableList2);

        listView2.setCellFactory(param -> new ListCell<>() {
        private final ImageView imageView = new ImageView();
        private final Label nom = new Label("Nom: ");
        private final Text nomLabel= new Text();
        private final HBox nomBox = new HBox(nom, nomLabel);
        //private final Label etape = new Label("Etape: ");
        private final Text etapeLabel = new Text();
        //private final HBox etapeBox = new HBox(etape, etapeLabel);
        private final VBox vBox = new VBox(10,nomBox);


        private final HBox hBox = new HBox(35, imageView, vBox);

        {
            imageView.setFitHeight(300);
            imageView.setFitWidth(300);
            hBox.setStyle("-fx-border-style: solid inside;"
                    + "-fx-border-width: 0;" + "-fx-border-insets: 5;");
            nomLabel.setStyle("-fx-text-fill: white;");
            etapeLabel.setStyle("-fx-text-fill: white;");
            vBox.setStyle("-fx-font-family: 'Comic Sans MS';" + "-fx-font-size: 14px;");

            // Rendre le texte des labels "Nom" et "Etape" en gras
            nom.setStyle("-fx-font-weight: bold;");
            //etape.setStyle("-fx-font-weight: bold;");
        }

        @Override
        protected void updateItem(Exercice item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                imageView.setImage(new Image("file:" + item.getImage()));
                nomLabel.setText(item.getNom());
                etapeLabel.setText(item.getEtape());
                setGraphic(hBox);
            }
        }
    });






}


}
