package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.models.Exercice;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;

public class ModifierController {

    private Connection connection;
    private final CoursService cs = new CoursService();



    @FXML
    private Label label;
    @FXML
    private RadioButton rButton1, rButton2, rButton3;
    @FXML
    private ToggleGroup sport;

    @FXML
    private ImageView imgC;

    private String imagePath;

    @FXML
    private TextField nomTF;
    @FXML
    private TextField descriptionTF;

    @FXML
    private TextArea etapeTR;

    private  Cours cours;
    private Exercice exercice;
    private String niveau;

    @FXML
    private Button btnAfficher;

    @FXML
    private TextField idTF;

 private  String imagePath2;
    @FXML
    private ListView<Cours> listView;

    private Cours obtenirCoursSelectionne() {
        Cours coursSelectionne = listView.getSelectionModel().getSelectedItem();
        return coursSelectionne;
    }


    @FXML
    void NaviguerVersAfficher(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
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

        // Définir le bouton radio correspondant au niveau comme étant sélectionné
        if (niveau.equals(rButton1.getText())) {
            rButton1.setSelected(true);
        } else if (niveau.equals(rButton2.getText())) {
            rButton2.setSelected(true);
        } else if (niveau.equals(rButton3.getText())) {
            rButton3.setSelected(true);
        }

        this.cours = cours;
    }




    @FXML
    void modifierC(ActionEvent event) {
        // Récupérer les éléments string dans les textfields

        String path = (imagePath2 != null && !imagePath2.isEmpty()) ? imagePath2 : cours.getImage();
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











    // assign this to the group of radio buttons in the FXML file
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


    public void setExercice(Exercice exercice) {
        // Récupérer les attributs de l'objet Cours
        Integer id = exercice.getId();
        String nom = exercice.getNom();
        String etape = exercice.getEtape();




        // Remplir les champs de texte avec les attributs

        idTF.setText(String.valueOf(id));


        nomTF.setText(nom);
        etapeTR.setText(nom);



        this.exercice = exercice;
    }








}
