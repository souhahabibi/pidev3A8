package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Abonnement;
import models.Salle;
import services.AbonnementService;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AbonnementAdminController {
    @FXML
    private VBox abonnementsContainer;

    @FXML
    private Button buttonAAjouter;
    SalleService s = new SalleService();
    AbonnementService a = new AbonnementService();
    private int x;
    @FXML
    public void setAbonnement(int id) {
        x=id;
        try {
            abonnementsContainer.setSpacing(25);
            displayAbonnements(id);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    private void displayAbonnements(int id) throws SQLException {

        List<Abonnement> abonnements = a.select(id); // Adjust this line to match your method for fetching competitions

        for (Abonnement abonnement : abonnements) {
            Pane abonnementEntry = createAbonnementEntry(abonnement);
            abonnementsContainer.getChildren().add(abonnementEntry);
        }
    }
    @FXML
    public Pane createAbonnementEntry(Abonnement abonnement) throws SQLException {
        Pane abonnementPane = new Pane();
        abonnementPane.setPrefSize(812, 230);
        abonnementPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");
        Text descriptionText = new Text(112, 50, "Description : " + abonnement.getDescription());
        descriptionText.setWrappingWidth(443);

        Text montantText = new Text(71, 180, "Montant : " + abonnement.getMontant());
        Text dureeText = new Text(329, 180, "Durée : " + abonnement.getDuree()+"mois");
        Text salleText = new Text(578, 180, "Salle : " +  s.selectSalleNameById(abonnement.getFK_idSalle()));

        ImageView descriptionImageView = new ImageView(new Image("/flaticon/file (1).png"));
        descriptionImageView.setFitHeight(30);
        descriptionImageView.setFitWidth(45);
        descriptionImageView.setLayoutX(17);
        descriptionImageView.setLayoutY(40);

        ImageView montantImageView = new ImageView(new Image("/flaticon/saving.png"));
        montantImageView.setFitHeight(30);
        montantImageView.setFitWidth(45);
        montantImageView.setLayoutX(17);
        montantImageView.setLayoutY(161);

        ImageView dureeImageView = new ImageView(new Image("/flaticon/calendar (1).png"));
        dureeImageView.setFitHeight(37);
        dureeImageView.setFitWidth(44);
        dureeImageView.setLayoutX(279);
        dureeImageView.setLayoutY(161);

        ImageView salleImageView = new ImageView(new Image("/flaticon/gym (1).png"));
        salleImageView.setFitHeight(37);
        salleImageView.setFitWidth(44);
        salleImageView.setLayoutX(525);
        salleImageView.setLayoutY(161);
        Button modifierButton = new Button("Modifier");
        modifierButton.setLayoutX(642);
        modifierButton.setLayoutY(27);
        modifierButton.setPrefSize(159, 31);
        modifierButton.setOnAction(event -> naviguezVersMODIFYAbonnement(null, abonnement));
        modifierButton.getStyleClass().add("login-btn");
        modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView1 = new ImageView(new Image("/flaticon/note.png"));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        modifierButton.setGraphic(imageView1);
        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setLayoutX(642);
        supprimerButton.setLayoutY(100);
        supprimerButton.setPrefSize(159, 31);
        supprimerButton.setOnAction(event -> deleteAbonnement(abonnement));
        supprimerButton.getStyleClass().add("login-btn");
        supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView2 = new ImageView(new Image("/flaticon/removed.png"));
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        supprimerButton.setGraphic(imageView2);
        abonnementPane.getChildren().addAll(descriptionText, montantText, dureeText, salleText, descriptionImageView, montantImageView, dureeImageView, salleImageView, modifierButton, supprimerButton);


        return abonnementPane;
    }
    void deleteAbonnement(Abonnement abonnement){
// Créer une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Confirmer la suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet abonnement ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    a.supprimer(abonnement.getId());
                    // Nettoyer le contenu du conteneur avant de réafficher les salles
                    abonnementsContainer.getChildren().clear();
                    setAbonnement(x);
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la suppression de la salle : " + e.getMessage());
                }
            }
        });
    }
    @FXML
    void naviguezVersMODIFYAbonnement(ActionEvent event, Abonnement abonnement) {


        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAbonnement.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierAbonnementController controller = loader.getController();

            controller.setAbonnement(abonnement);


            // Finally, set the scene's root to switch to the new view
            buttonAAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersAjouterAbbonnement(ActionEvent event) {


        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterAbonnementController controller = loader.getController();

            // If an item is selected, pass it to the controller of the next scene
            controller.setSalleName(x);
            // Finally, set the scene's root to switch to the new view
            buttonAAjouter.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
