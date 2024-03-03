package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.models.Exercice;
import com.esprit.models.SharedModel;
import com.esprit.services.CoursService;
import com.esprit.services.ExerciceService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;



public class AjouterExerciceController {

    private final ExerciceService es = new ExerciceService();
    CoursService coursService = new CoursService();




    private String imagePath;

    @FXML
    private Label label;

private  Exercice exercice;

    @FXML
    private TextField nomTF;
    @FXML
    private TextArea etapeTR;

    @FXML
    private  TextField id;



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
    void AjouterExercice(ActionEvent event) {

        int id = SharedModel.getId();

        if (imagePath != null && !nomTF.getText().isEmpty() && !etapeTR.getText().isEmpty()) {
            // Créer un nouveau exercice
            Exercice nouveauExercice = new Exercice(id, nomTF.getText(), etapeTR.getText(), imagePath);
            // Vérifier si l'exercice existe déjà
            if (!es.ExerciceExiste(nouveauExercice)) {
                // Si l'exercice n'existe pas, l'ajouter
                es.ajouter(nouveauExercice);
            } else {
                // Si l'exercice existe déjà, afficher une boîte de dialogue d'alerte
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText(null);
                alert.setContentText("Un exercice avec les mêmes informations existe déjà.");
                alert.showAndWait();
            }
        } else {
            // Afficher une boîte de dialogue d'alerte
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs et choisir une image avant d'ajouter l'exercice.");
            alert.showAndWait();
        }
    }


    public void setExercice(Exercice exercice) {
        // Récupérer les attributs de l'objet Cours

        Integer id= exercice.getId();
        String nom = exercice.getNom();
        String etape = exercice.getEtape();

        // Remplir les champs de texte avec les attributs
        nomTF.setText(nom);
        etapeTR.setText(etape);


        this.exercice = exercice;

    }




}
