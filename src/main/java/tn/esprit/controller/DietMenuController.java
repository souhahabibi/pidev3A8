package tn.esprit.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Meal;
import tn.esprit.entities.Regime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DietMenuController implements Initializable {


    @FXML
    private Button Fill;

    @FXML
    private AnchorPane content;

    @FXML
    private Button exit;

    //@FXML
   // private ImageView img_com;

    @FXML
    private Button meals;

    @FXML
    private Button select;

    @FXML
    private VBox v;




    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


    public void FillForm(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/FillDietForm.fxml"));
        content.getChildren().setAll(pane);
    }

    public void SelectMeal(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/market.fxml"));
        content.getChildren().setAll(pane);
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void afficheMeal(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/resources/market.fxml"));
        content.getChildren().setAll(pane);
    }
}

