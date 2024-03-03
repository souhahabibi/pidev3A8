package com.esprit.controllers;

import com.esprit.models.Cours;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientCommentaireController {

    @FXML
    private TextArea commentaireTR;

    private final CoursService cs = new CoursService();
    private int courseId;

    public void initialize(int courseId) {
        this.courseId = courseId;
    }

    public void ajouter(ActionEvent event) {
        Cours cours = new Cours();
        cours.setId(courseId);
        cours.setCommentaire(commentaireTR.getText());
        cs.ajoutercom(cours);
    }
}




