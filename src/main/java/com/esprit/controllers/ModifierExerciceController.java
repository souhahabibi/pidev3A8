package com.esprit.controllers;

import com.esprit.models.Exercice;
import com.esprit.services.CoursService;
import com.esprit.services.ExerciceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ModifierExerciceController {


    private final ExerciceService es = new ExerciceService();
    CoursService coursService = new CoursService();

    @FXML
    private TextField nomTF;
    @FXML
    private TextArea etapeTR;

    @FXML
    private  TextField id;

    private Exercice exercice;

    private String imagePath;

    @FXML
    private Button btnAfficher;

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
    void modifierE(ActionEvent event) {
        // Récupérer les éléments string dans les textfields

        String path = imagePath;
        Integer idd = Integer.parseInt(id.getText());
        String nom = nomTF.getText();
        String etape = etapeTR.getText();

        // Modifier les attributs de l'objet Cours avec les éléments string
        exercice.setImage(path);
        exercice.setNom(nom);
        exercice.setId(idd); // Convertir l'entier en chaîne
        exercice.setEtape(etape);
        // Appeler la méthode modifier du service en lui passant l'objet Cours modifié
        es.modifier(exercice);
    }


    public void setExercice(Exercice exercice) {
        // Récupérer les attributs de l'objet Cours

        Integer idd = exercice.getId();
        String nom = exercice.getNom();
        String etape = exercice.getEtape();

        // Remplir les champs de texte avec les attributs

        id.setText(String.valueOf(idd)); // Convertir l'entier en chaîne
        nomTF.setText(nom);
        etapeTR.setText(etape);

        this.exercice = exercice;
    }





    @FXML
    void NaviguerVersAfficherEx(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            btnAfficher.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }




}
