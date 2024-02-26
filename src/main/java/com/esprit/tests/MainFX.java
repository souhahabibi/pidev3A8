package com.esprit.tests;

import atlantafx.base.theme.NordDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interface.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // primaryStage.setFullScreen(true);

        primaryStage.setTitle("Gérer Cours");
        primaryStage.setScene(scene);

        // Agrandir la fenêtre
        primaryStage.setHeight(600); // Hauteur en pixels
        primaryStage.setWidth(1000); // Largeur en pixels

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
