package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.models.Exercice;
import com.esprit.services.CoursService;
import com.esprit.services.ExerciceService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.util.List;

public class AfficherExerciceController {

    @FXML

    private Button btnAjouter;


    @FXML

    private Button btnModifier;




    private Exercice exercice;
    private String niveau;


    @FXML
    private ListView<Exercice> listView;

    private final ExerciceService es = new ExerciceService();

    @FXML
    void initialize() {
        List<Exercice> exercice = es.afficher();
        ObservableList<Exercice> observableList = FXCollections.observableList(exercice);
        listView.setItems(observableList);

        listView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label nom = new Label("Nom: ");
            private final Text nomLabel= new Text();
            private final HBox nomBox = new HBox(nom, nomLabel);
            private final Label etape = new Label("Etape: ");
            private final Text etapeLabel = new Text();
            private final HBox etapeBox = new HBox(etape, etapeLabel);
            private final VBox vBox = new VBox(nomBox, etapeBox);
            private final HBox hBox = new HBox(35, imageView, vBox);

            {
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                hBox.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 0;" + "-fx-border-insets: 5;");
                nomLabel.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
                etapeLabel.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
                vBox.setStyle("-fx-font-family: 'Comic Sans MS';" + "-fx-font-size: 14px;");
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


    @FXML
    void NaviguerVerAjouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterExercice.fxml"));
            btnAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private Exercice obtenirExerciceSelectionne() {
        Exercice exerciceSelectionne = listView.getSelectionModel().getSelectedItem();
        return exerciceSelectionne;
    }



    @FXML
    void modifierExercice(ActionEvent event) {

        try {
            // Charger la page AjouterCours.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierExercice.fxml"));
            Parent root = loader.load();

            ModifierExerciceController controller = loader.getController();
            // Récupérer l'objet Cours à modifier

            Exercice coursAmodifier = obtenirExerciceSelectionne();
            // Passer l'objet Cours au contrôleur
            controller.setExercice(coursAmodifier);
            // Créer une nouvelle scène avec la page chargée
            Scene scene = new Scene(root);
            // Récupérer la fenêtre principale de l'application
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Assigner la nouvelle scène à la fenêtre principale
            stage.setScene(scene);
            // Afficher la fenêtre principale
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }



    }






    @FXML
    void supprimerExercice(ActionEvent event) {
        Exercice ExerciceASupprimer = obtenirExerciceSelectionne();
        int indexASupprimer = listView.getSelectionModel().getSelectedIndex();
        listView.getItems().remove(indexASupprimer);
        es.supprimer(ExerciceASupprimer);
    }




}
