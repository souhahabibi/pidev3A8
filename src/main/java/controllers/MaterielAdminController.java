package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Materiel;
import models.Salle;
import services.AbonnementService;
import services.MaterielService;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MaterielAdminController {
    SalleService s = new SalleService();
    MaterielService m = new MaterielService();

    @FXML
    private VBox materielsContainer;
    @FXML
    private Button buttonMAjouter;
   private int x;
    @FXML
    public void setMateriel(int id) {
        x=id;
        try {
            materielsContainer.setSpacing(25);
            displayMateriels(id);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    /*@FXML
    public void initialize() {

        try {
            materielsContainer.setSpacing(25);
            displayMateriels(id);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }

    }*/
    private void displayMateriels(int id) throws SQLException {
      //  x=id;
        List<Materiel> materiels = m.select(id);

        for (Materiel materiel : materiels) {
            Pane materielEntry = createMaterielEntry(materiel);
            materielsContainer.getChildren().add(materielEntry);
        }
    }

    @FXML
    public Pane createMaterielEntry(Materiel materiel) throws SQLException {
        Pane materielPane = new Pane();
        materielPane.setPrefSize(762, 207);
        materielPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

        Image image = new Image("file:" + materiel.getImage());
        ImageView materielImageView = new ImageView();
        materielImageView.setImage(image);
        materielImageView.setFitHeight(172);
        materielImageView.setFitWidth(189);
        materielImageView.setLayoutX(23);
        materielImageView.setLayoutY(10);

        Text materielName = new Text(249, 37, "Nom : " + materiel.getNom());
        materielName.setFont(Font.font("Arial", 16));
        materielName.setEffect(new DropShadow());

        Text age = new Text(249, 68, "Age : " + materiel.getAge());
        age.setFont(Font.font("Arial", 16));
        age.setEffect(new DropShadow());

        Text quantite = new Text(249, 102, "Quantité : " + materiel.getQuantite());
        quantite.setFont(Font.font("Arial", 16));
        quantite.setEffect(new DropShadow());

        Text prix = new Text(249, 136, "Prix : " + materiel.getPrix());
        prix.setFont(Font.font("Arial", 16));
        prix.setEffect(new DropShadow());

        Text salle = new Text(249, 170, "Salle : " + s.selectSalleNameById(materiel.getFK_idSalle()));
        salle.setFont(Font.font("Arial", 16));
        salle.setEffect(new DropShadow());

        Button modifierButton = new Button("Modifier");
        modifierButton.setLayoutX(542);
        modifierButton.setLayoutY(37);
        modifierButton.setPrefSize(159, 31);
        modifierButton.setOnAction(event -> naviguezVersMODIFYMateriel(null, materiel));
        modifierButton.getStyleClass().add("login-btn");
        modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView1 = new ImageView(new Image("/flaticon/note.png"));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        modifierButton.setGraphic(imageView1);
        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setLayoutX(542);
        supprimerButton.setLayoutY(96);
        supprimerButton.setPrefSize(159, 31);
        supprimerButton.setOnAction(event -> deleteMateriel(materiel));
        supprimerButton.getStyleClass().add("login-btn");
        supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView2 = new ImageView(new Image("/flaticon/removed.png"));
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        supprimerButton.setGraphic(imageView2);
        materielPane.getChildren().addAll(materielImageView, materielName, age, quantite, prix, salle, modifierButton, supprimerButton);

        return materielPane;

    }
    @FXML
    void deleteMateriel(Materiel materiel){

        try {
            m.supprimer(materiel.getId());
            // Nettoyer le contenu du conteneur avant de réafficher les salles
            materielsContainer.getChildren().clear();
            setMateriel(x);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la salle : " + e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYMateriel(ActionEvent event, Materiel materiel) {


        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMateriel.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierMaterielController controller = loader.getController();

            controller.setMateriel(materiel);


            // Finally, set the scene's root to switch to the new view
            buttonMAjouter.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersAjouterMateriel(ActionEvent event) {
        //ObservableList<Salle> observableList = null;


        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMateriel.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterMaterielController controller = loader.getController();

            // If an item is selected, pass it to the controller of the next scene
            controller.setSalleName(x);
            // Finally, set the scene's root to switch to the new view
            buttonMAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
