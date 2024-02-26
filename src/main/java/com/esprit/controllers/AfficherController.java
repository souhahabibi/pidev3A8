package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.models.Exercice;
import com.esprit.services.CoursService;
import com.esprit.services.ExerciceService;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AfficherController {


        @FXML

        private Button btnAjouter;


    @FXML

    private Button btnModifier;

    @FXML
    private TextField nomTF;
    @FXML
    private TextField descriptionTF;


    private Cours cours;
    private String niveau;

//Exercice//
@FXML

private Button btnAjouter2;


    @FXML

    private Button btnModifier2;



    private Exercice exercice;


    @FXML
    private ListView<Exercice> listView2;

    private final ExerciceService es = new ExerciceService();


    @FXML
    private TabPane tabPane;

    @FXML
    private Tab ExTab;
    ///

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

            private final VBox vBox = new VBox(10,nomBox, descriptionBox, niveauBox);
            private final HBox hBox = new HBox(35, imageView, vBox);

            {
                imageView.setFitHeight(300); // ajuster la hauteur comme vous le souhaitez
                imageView.setFitWidth(300); // ajuster la largeur comme vous le souhaitez
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


///Exercice//
        List<Exercice> exercice = es.afficher();
        ObservableList<Exercice> observableList2 = FXCollections.observableList(exercice);
        listView2.setItems(observableList2);

        listView2.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label nom = new Label("Nom: ");
            private final Text nomLabel= new Text();
            private final HBox nomBox = new HBox(nom, nomLabel);
            private final Label etape = new Label("Etape: ");
            private final Text etapeLabel = new Text();
            private final HBox etapeBox = new HBox(etape, etapeLabel);
            private final VBox vBox = new VBox(10,nomBox, etapeBox);


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
                etape.setStyle("-fx-font-weight: bold;");
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


        //////




    }


    @FXML
    void NaviguerVerAjouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Ajouter.fxml"));
            btnAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private Cours obtenirCoursSelectionne() {
        Cours coursSelectionne = listView.getSelectionModel().getSelectedItem();
        return coursSelectionne;
    }


    @FXML
    void modifierCours(ActionEvent event) {

        try {
            // Charger la page AjouterCours.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));
            Parent root = loader.load();
            // Récupérer le contrôleur de la page AjouterCours.fxml
            ModifierController controller = loader.getController();
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


    @FXML
    void NaviguerVerAjouter2(ActionEvent event) {
        try {
            // Charger la page AjouterExercice.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterExercice.fxml"));

            TabPane modifierExerciceTabPane = (TabPane) root.lookup("TabPane");
            // Obtenir l'onglet "Exercice" du TabPane
            Tab exerciceTab = modifierExerciceTabPane.getTabs().get(1); // L'index 1 correspond à l'onglet "Exercice"

            // Définir le contenu de l'onglet ExTab avec le contenu de l'onglet "Exercice"
            ExTab.setContent(exerciceTab.getContent());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    private Exercice obtenirExerciceSelectionne() {
        Exercice exerciceSelectionne = listView2.getSelectionModel().getSelectedItem();
        return exerciceSelectionne;
    }



    @FXML
    void modifierExercice2(ActionEvent event) {
        try {
            // Charger la page ModifierExercice.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierExercice.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la page ModifierExercice.fxml
            ModifierExerciceController controller = loader.getController();
            // Récupérer l'objet Exercice à modifier
            Exercice exerciceAmodifier = obtenirExerciceSelectionne();
            // Passer l'objet Exercice au contrôleur
            controller.setExercice(exerciceAmodifier);

            // Obtenir le TabPane du Parent chargé
            TabPane modifierExerciceTabPane = (TabPane) root.lookup("TabPane");
            // Obtenir l'onglet "Exercice" du TabPane
            Tab exerciceTab = modifierExerciceTabPane.getTabs().get(1); // L'index 1 correspond à l'onglet "Exercice"

            // Définir le contenu de l'onglet ExTab avec le contenu de l'onglet "Exercice"
            ExTab.setContent(exerciceTab.getContent());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }











    @FXML
    void supprimerExercice2(ActionEvent event) {
        Exercice ExerciceASupprimer = obtenirExerciceSelectionne();
        int indexASupprimer = listView2.getSelectionModel().getSelectedIndex();
        listView2.getItems().remove(indexASupprimer);
        es.supprimer(ExerciceASupprimer);
    }


}
