package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Materiel;
import models.Salle;
import services.MaterielService;
import services.SalleService;

import java.sql.SQLException;
import java.util.List;

public class MaterielClientController {
    MaterielService s = new MaterielService();

    @FXML
    private VBox materielsContainer;
@FXML
    public void setMateriel(int id) {

        try {
            materielsContainer.setSpacing(25);
            displayMateriels(id);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    @FXML
    private void displayMateriels(int id) throws SQLException {

        List<Materiel> materiels = s.select(id); // Adjust this line to match your method for fetching competitions

        for (Materiel materiel : materiels) {
            Pane materielEntry = createMaterielEntry(materiel);
            materielsContainer.getChildren().add(materielEntry);
        }
    }
    @FXML
    public Pane createMaterielEntry(Materiel materiel ) {
        Pane materielPane = new Pane();
        materielPane.setPrefSize(912, 300); // Adjusted to match your FXML Pane size
        materielPane.setStyle("-fx-border-color: #8B0000; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");
        Image image = new Image("file:" + materiel.getImage());

        ImageView MaterielImageView = new ImageView();
        // Ensure the path is correct
        MaterielImageView.setImage(image);
        MaterielImageView.setFitHeight(150);
        MaterielImageView.setFitWidth(199);
        MaterielImageView.setLayoutX(14);
        MaterielImageView.setLayoutY(27);

        Text MaterielName = new Text(255, 46, materiel.getNom()); // Adjusted X to align with TextArea
        MaterielName.setFont(new Font("Arial", 21));
        MaterielName.setEffect(new DropShadow());



        Text MaterielQuantite = new Text(255, 142, "quantite: " + materiel.getQuantite()); // Adjusted X to align with the map icon
        MaterielQuantite.setEffect(new DropShadow());


        // Add all components to the pane
        materielPane.getChildren().addAll( MaterielImageView,MaterielName, MaterielQuantite);

        return materielPane;
    }


}
