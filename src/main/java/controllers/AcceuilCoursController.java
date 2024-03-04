package controllers;


import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Cours;
import services.CoursService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class AcceuilCoursController {

    private final CoursService cs = new CoursService();

    @FXML
    private TextField nomTF;
    @FXML
    private TextField descriptionTF;
    @FXML
    private RadioButton rButton1, rButton2, rButton3;
    @FXML
    private Button btnAjouter, btnAjouter1, btnAfficher, btnAfficher2;
    @FXML
    private ListView<Cours> listView;

    File file ;

    @FXML
    private ImageView imgC;


    public static final String ACCOUNT_SID = "ACf98656ce24cceaaa4be85d72a63ecbe8";
    public static final String AUTH_TOKEN = "150155d4627db5404bfed786a0833ea8";

    private Cours cours;
    private String imagePath;
    private String imagePath2;
    private String niveau;

    @FXML
    void NaviguerVerAjouter(ActionEvent event) {
        navigateTo("/Ajouter.fxml");
    }

    @FXML
    void NaviguerVerAjouter1(ActionEvent event) {
        navigateTo("/AjouterExercice.fxml");
    }

    @FXML
    void NaviguerVersAfficher(ActionEvent event) {
        navigateTo("/Afficher.fxml");
    }

    @FXML
    void NaviguerVersAfficher2(ActionEvent event) {
        navigateTo("/AfficherExercice.fxml");
    }

    private void navigateTo(String resource) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            btnAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    @FXML
    void choisirImage(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                imagePath = file.getPath().replace("\\", "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

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
    void AjouterCours(ActionEvent event) {
        String msg="un nouveau cours a éte ajouté";
        if (isValidInput()) {


            Cours nouveauCours = new Cours(imagePath2, nomTF.getText(), descriptionTF.getText(), niveau);
            if (!cs.coursExiste(nouveauCours)) {
                cs.ajouter(nouveauCours);
                message(msg);



            } else {
                showAlert("Un cours avec les mêmes informations existe déjà.");
            }
        } else {
            showMissingFieldsAlert();
        }
    }


    public void message (String nom) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
                        new PhoneNumber("+21624019297"),
                        new PhoneNumber("+15717486711"),
                        nom)
                .create();

        System.out.println(message.getSid());


    }




    private boolean isValidInput() {
        return imagePath2 != null && !nomTF.getText().isEmpty() && !descriptionTF.getText().isEmpty()
                && (rButton1.isSelected() || rButton2.isSelected() || rButton3.isSelected()) && nomTF.getText().matches("[a-zA-Z]*");
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerte");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showMissingFieldsAlert() {
        StringBuilder message = new StringBuilder("Veuillez remplir les champs suivants avant d'ajouter le cours :");
        if (imagePath2 == null) message.append("\n- Choisissez une image");
        if (nomTF.getText().isEmpty()) message.append("\n- Entrez un nom de cours");
        if (descriptionTF.getText().isEmpty()) message.append("\n- Entrez une description");
        if (!(rButton1.isSelected() || rButton2.isSelected() || rButton3.isSelected())) message.append("\n- Sélectionnez un niveau");
        if (!nomTF.getText().matches("[a-zA-Z]*")) message.append("\n- Le nom du cours doit contenir uniquement des lettres");

        showAlert(message.toString());
    }


    private void navigateTo2(String resource) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            btnAfficher2.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void NaviviguerVerAfficherCours(ActionEvent event) {
        navigateTo2("/Afficher.fxml");
    }


}