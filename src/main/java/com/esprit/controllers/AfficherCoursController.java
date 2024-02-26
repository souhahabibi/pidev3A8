package com.esprit.controllers;

import com.esprit.services.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.esprit.models.Cours;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AfficherCoursController {

    private final CoursService cs = new CoursService();

    @FXML
    private ListView<Cours> listView;

    @FXML
    private Button btnSupprimer;


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
            private final HBox hBox = new HBox(35,imageView, vBox);

            {
                imageView.setFitHeight(300); // ajuster la hauteur comme vous le souhaitez
                imageView.setFitWidth(300);
              // ajuster la largeur comme vous le souhaitez
                // changer la couleur de fond du HBox en vert clair
                hBox.setStyle("-fx-background-color: lightgreen;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 0;" + "-fx-border-insets: 5;");
                // changer la couleur du texte en blanc
                nomLabel.setStyle("-fx-text-fill: black;");
                descriptionLabel.setStyle("-fx-text-fill: black;");
                niveauLabel.setStyle("-fx-text-fill: black;");
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

        // ajouter la classe CSS au bouton supprimer
        btnSupprimer.getStyleClass().add("button-supprimer");







    }

    private Cours obtenirCoursSelectionne() {
        Cours coursSelectionne = listView.getSelectionModel().getSelectedItem();
        return coursSelectionne;
    }


    @FXML
    void modifierCours(ActionEvent event) {

        try {
            // Charger la page AjouterCours.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCours.fxml"));
            Parent root = loader.load();
            // Récupérer le contrôleur de la page AjouterCours.fxml
            AjouterCoursController controller = loader.getController();
            // Récupérer l'objet Cours à modifier

            Cours coursAmodifier = obtenirCoursSelectionne();
            // Passer l'objet Cours au contrôleur
            controller.setCours(coursAmodifier);
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
    void supprimerCours(ActionEvent event) {
        Cours coursASupprimer = obtenirCoursSelectionne();
        int indexASupprimer = listView.getSelectionModel().getSelectedIndex();
        listView.getItems().remove(indexASupprimer);
        cs.supprimer(coursASupprimer);
    }


    }
