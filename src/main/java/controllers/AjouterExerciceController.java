package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Exercice;
import models.SharedModel;
import services.CoursService;
import services.ExerciceService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;


public class AjouterExerciceController {

    private final ExerciceService es = new ExerciceService();
    CoursService coursService = new CoursService();




    private String imagePath;

    @FXML
    private Label label;

    private Exercice exercice;

    @FXML
    private TextField nomTF;
    @FXML
    private TextArea etapeTR;

    private String imagePath2;

    @FXML
    private ImageView imgC;


    @FXML
    private  TextField id;


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
    }
*/

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
    void AjouterExercice(ActionEvent event) {

        int id = SharedModel.getId();

        if (imagePath2 != null && !nomTF.getText().isEmpty() && !etapeTR.getText().isEmpty()) {
            // Créer un nouveau exercice
            Exercice nouveauExercice = new Exercice(id, nomTF.getText(), etapeTR.getText(), imagePath2);
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