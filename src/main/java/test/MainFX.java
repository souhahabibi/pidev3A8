package test;

import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    public static int loggedInID;
    public static int RestpasswordID;
    @Override
    public void start(Stage primaryStage) throws Exception {
       // Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FillDietForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("EspRAT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
