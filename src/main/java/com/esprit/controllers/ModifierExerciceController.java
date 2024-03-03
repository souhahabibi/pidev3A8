package com.esprit.controllers;

import com.esprit.models.Exercice;
import com.esprit.models.SharedModel;
import com.esprit.services.CoursService;
import com.esprit.services.ExerciceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

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

    private String imagePath2;


    @FXML
    private Button btnAfficher;

    @FXML
    private ImageView imgC;


            /*
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
    }*/


    @FXML
    File choisirImage(ActionEvent event) {
        File file = null;
        Path to1 = null;
        String m = null;
        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "jpeg", "PNG");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            m = chooser.getSelectedFile().getAbsolutePath();
            String imagePath2;
            file = chooser.getSelectedFile();
            String fileName = file.getName();
            String imagePath = file.getPath().replace("\\", "/");

            if (chooser.getSelectedFile() != null) {

                try {
                    java.nio.file.Path from = Paths.get(chooser.getSelectedFile().toURI());
                    to1 = Paths.get(imagePath + "\\" + fileName);

                    CopyOption[] options = new CopyOption[]{
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.COPY_ATTRIBUTES
                    };
                    Files.copy(from, to1, options);
                    System.out.println("added");
                    System.out.println(file);

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
        imagePath2=file.getPath();

        this.imagePath2=imagePath2;
        Image img = new Image(file.toURI().toString());
        imgC.setImage(img);
        return file;
    }











    @FXML
    void modifierE(ActionEvent event) {
        // Récupérer les éléments string dans les textfields

        int id = SharedModel.getId();

        String path = (imagePath2 != null && !imagePath2.isEmpty()) ? imagePath2 : exercice.getImage();

        String nom = nomTF.getText();
        String etape = etapeTR.getText();

        // Modifier les attributs de l'objet Cours avec les éléments string
        exercice.setImage(path);
        exercice.setNom(nom);
        exercice.setEtape(etape);
        // Appeler la méthode modifier du service en lui passant l'objet Cours modifié
        es.modifier(exercice);
    }


    public void setExercice(Exercice exercice) {
        // Récupérer les attributs de l'objet Cours

        int id = SharedModel.getId();

        String nom = exercice.getNom();
        String etape = exercice.getEtape();

        // Remplir les champs de texte avec les attributs

        nomTF.setText(nom);
        etapeTR.setText(etape);

        this.exercice = exercice;
    }









    @FXML
    void NaviguezAfficher(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherExercice.fxml"));
            btnAfficher.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }








}
