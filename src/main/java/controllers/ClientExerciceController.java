package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Exercice;
import services.ExerciceService;

import java.io.IOException;
import java.util.List;

public class ClientExerciceController {

    private Exercice exercice;


    @FXML
    private ListView<Exercice> listView2;

    private final ExerciceService es = new ExerciceService();






    @FXML
    void initialize(int courseId) {

        List<Exercice> exercice = es.afficher2(courseId);
        ObservableList<Exercice> observableList2 = FXCollections.observableList(exercice);
        listView2.setItems(observableList2);

        listView2.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label nom = new Label("Nom: ");
            private final Text nomLabel= new Text();
            private final HBox nomBox = new HBox(nom, nomLabel);
            //private final Label etape = new Label("Etape: ");
            private final Text etapeLabel = new Text();
            //private final HBox etapeBox = new HBox(etape, etapeLabel);
            private final Button button = new Button("Voir les etapes");
            private final VBox vBox = new VBox(10,nomBox,button);


            private final HBox hBox = new HBox(35, imageView, vBox);

            {
                imageView.setFitHeight(300);
                imageView.setFitWidth(300);
                hBox.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 0;" + "-fx-border-insets: 5;");
                nomLabel.setStyle("-fx-text-fill: white;");
                etapeLabel.setStyle("-fx-text-fill: white;");
                vBox.setStyle("-fx-font-family: 'Comic Sans MS';" + "-fx-font-size: 14px;");

                // Rendre le texte des labels "Nom" et "Etape" en gras
                nom.setStyle("-fx-font-weight: bold;");
                //etape.setStyle("-fx-font-weight: bold;");
                button.setStyle("  -fx-background-color:\n" +
                        "            #a6b5c9,\n" +
                        "            linear-gradient(#303842 0%, #3e5577 20%, #375074 100%),\n" +
                        "            linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%);\n" +
                        "    -fx-background-insets: 0 0 -1 0,0,1;\n" +
                        "    -fx-background-radius: 5,5,4;\n" +
                        "    -fx-padding: 7 30 7 30;\n" +
                        "    -fx-text-fill: #242d35;\n" +
                        "    -fx-font-family: \"Helvetica\";\n" +
                        "    -fx-font-size: 20px;\n" +
                        "    -fx-text-fill: white;" +
                        "-fx-pref-width: 200px;" +
                        "-fx-pref-height: 35px;");

                button.setOnAction(event -> {
                    Exercice exercice = getItem();
                    int courseId = exercice.getId();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etapes.fxml"));
                        loader.setControllerFactory(controllerClass -> {
                            if (controllerClass == ClientEtapeController.class) {
                                ClientEtapeController exerciceController = new ClientEtapeController();
                                return exerciceController;
                            } else {
                                try {
                                    return controllerClass.newInstance();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });



                        Parent root = loader.load();

                        // Get the controller for the new scene
                        ClientEtapeController exerciceController = loader.getController();

                        // Pass the selected exercice to the new scene's controller
                        exerciceController.initialize(courseId);

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });









            }

            @Override
            protected void updateItem(Exercice item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image("file:" + item.getImage()));
                    nomLabel.setText(item.getNom());
                    etapeLabel.setText(item.getEtape());
                    setGraphic(hBox);
                }
            }
        });







    }


}
