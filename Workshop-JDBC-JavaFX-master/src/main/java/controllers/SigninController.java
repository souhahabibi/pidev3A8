package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class SigninController {
    @javafx.fxml.FXML
    private Button SignInbtn;
    @javafx.fxml.FXML
    private Button signUpbtn;

    @javafx.fxml.FXML
    public void naviguezVersSignUp(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/signup.fxml"));
            SignInbtn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    }

