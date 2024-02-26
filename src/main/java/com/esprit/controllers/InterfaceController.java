package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class InterfaceController {




    @FXML
    private Button btncoach;

    @FXML
    private Button btnClient;

    @FXML
    void NaviguerVerscoach(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            btncoach.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    @FXML
    void NaviguerVersclient(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));
            btnClient.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



}
