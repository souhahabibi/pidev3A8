package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Cours;
import services.CoursService;

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