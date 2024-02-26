package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.services.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AcceuilController {

    private Connection connection;
    private final CoursService cs = new CoursService();

    Set<Cours> coursSet = new HashSet<>();

    @FXML
    private Label label;
    @FXML
    private RadioButton rButton1, rButton2, rButton3;


    @FXML
    private TextField nomTF;
    @FXML
    private TextField descriptionTF;


    private Cours cours;
    private String niveau;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAjouter1;


    @FXML
    private Button btnAfficher;

    @FXML
    private Button btnAfficher2;

    @FXML
    private ListView<Cours> listView;

    List<Cours> cour = cs.afficher();




    @FXML
    void NaviguerVerAjouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Ajouter.fxml"));
            btnAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void NaviguerVerAjouter1 (ActionEvent event){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterExercice.fxml"));
            btnAjouter1.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }



    }

    @FXML
    void NaviguerVersAfficher(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            btnAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void NaviguerVersAfficher2(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherExercice.fxml"));
            btnAfficher.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void setCours(Cours cours) {
        // Récupérer les attributs de l'objet Cours

        String nom = cours.getNom();
        String description = cours.getDescription();
        String niveau = cours.getNiveau();
        // Remplir les champs de texte avec les attributs
        nomTF.setText(nom);
        descriptionTF.setText(description);


        this.cours = cours;

    }


    // Variable pour stocker le chemin de l'image
    private String imagePath;

    @FXML
    void choisirImage(ActionEvent event) {
        try {
            // Créer un FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

            // Ouvrir le FileChooser
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                // Obtenir le chemin de l'image
                imagePath = file.getPath().replace("\\", "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private ToggleGroup sport; // assign this to the group of radio buttons in the FXML file

    // assign this to the label in the FXML file
    @FXML
    void AjoutertNiveau() {


        if (rButton1.isSelected()) {
            niveau = rButton1.getText();
        } else if (rButton2.isSelected()) {
            niveau = rButton2.getText();
        } else if (rButton3.isSelected()) {
            niveau = rButton3.getText();
        }


    }

    @FXML
    void AjouterCours(ActionEvent event) {
        // Vérifier si tous les champs sont remplis et qu'un bouton radio est sélectionné
        if (imagePath != null && !nomTF.getText().isEmpty() && !descriptionTF.getText().isEmpty() && (rButton1.isSelected() || rButton2.isSelected() || rButton3.isSelected())) {
            // Récupérer le niveau à partir du bouton radio sélectionné
            String niveau = rButton1.isSelected() ? rButton1.getText() : rButton2.isSelected() ? rButton2.getText() : rButton3.getText();
            // Créer un nouveau cours
            Cours nouveauCours = new Cours(imagePath, nomTF.getText(), descriptionTF.getText(), niveau);
            // Vérifier si le cours existe déjà
            if (!cs.coursExiste(nouveauCours)) {
                // Si le cours n'existe pas, l'ajouter
                cs.ajouter(nouveauCours);
            } else {
                // Si le cours existe déjà, afficher une boîte de dialogue d'alerte
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("Un cours avec les mêmes informations existe déjà.");
                alert.showAndWait();
            }
        } else {
            // Afficher une boîte de dialogue d'alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs et choisir une image avant d'ajouter le cours.");
            alert.showAndWait();
        }
    }





    @FXML
    void NaviviguerVerAfficherCours(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            btnAfficher2.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }





}
