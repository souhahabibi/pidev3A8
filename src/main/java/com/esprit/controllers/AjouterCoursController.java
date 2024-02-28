package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;


public class AjouterCoursController {


    private Connection connection;
    private final CoursService cs = new CoursService();



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
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCours.fxml"));
            descriptionTF.getScene().setRoot(root);
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


    @FXML
    void modifierC(ActionEvent event) {
        // Récupérer les éléments string dans les textfields

        String path = imagePath;
        String nom = nomTF.getText();
        String description = descriptionTF.getText();
        String niveau = ((RadioButton) sport.getSelectedToggle()).getText();
        // Modifier les attributs de l'objet Cours avec les éléments string
        cours.setImage(path);
        cours.setNom(nom);
        cours.setDescription(description);
        cours.setNiveau(niveau);
        // Appeler la méthode modifier du service en lui passant l'objet Cours modifié
        cs.modifier(cours);

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
    void AjoutertNiveau () {


        if(rButton1.isSelected()) {
         niveau = rButton1.getText();
        }
        else if(rButton2.isSelected()) {
          niveau = rButton2.getText();
        }
        else if(rButton3.isSelected()) {
            niveau = rButton3.getText();
        }



    }
    @FXML
    void AjouterCours(ActionEvent event) {


        if (imagePath != null) {
            cs.ajouter(new Cours(imagePath, nomTF.getText(), descriptionTF.getText(), niveau));
        } else {
            // Afficher une boîte de dialogue d'alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez choisir une image avant d'ajouter le cours.");
            alert.showAndWait();
        }




    }







    }











