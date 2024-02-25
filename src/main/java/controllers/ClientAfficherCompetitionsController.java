package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import models.Competition;
import models.Organisateur;
import services.CompetitionService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ClientAfficherCompetitionsController {
    CompetitionService competitionService = new CompetitionService();

    @FXML
    private VBox competitionsContainer; // Ensure this matches the fx:id of the VBox in your FXML
    @FXML
    private TextField searchTF;
    @FXML
    public void initialize() {
        searchTF.setStyle("-fx-text-fill: black; -fx-background-color: white");
        showlist();
    }
    public void showlist()
    {
        try {
            competitionsContainer.setSpacing(25);
            displayCompetitions();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    @FXML
    private void displayCompetitions() throws SQLException {
        competitionsContainer.getChildren().clear(); // Clear existing competition entries
        List<Competition> competitions = competitionService.recuperer(); // Fetch all competitions

        // Filter competitions based on search criteria
        String searchText = searchTF.getText().toLowerCase();
        List<Competition> filteredCompetitions = competitions.stream()
                .filter(c -> c.getNom().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        for (Competition competition : filteredCompetitions) {
            Pane competitionEntry = createCompetitionEntry(competition);
            competitionsContainer.getChildren().add(competitionEntry);
        }
    }
   @FXML
    private Pane createCompetitionEntry(Competition competition) {
       Pane competitionPane = new Pane();
       competitionPane.setLayoutX(200);
       competitionPane.setLayoutY(0);
       competitionPane.setPrefSize(850, 157);
       competitionPane.setStyle("-fx-border-color: gray; -fx-border-radius: 2; -fx-border-width: 4; -fx-background-color: rgba(0,0,0,0.6);");

       ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/gymimage.png"))); // Adjust the path as necessary
       imageView.setFitHeight(154);
       imageView.setFitWidth(850);
       imageView.setOpacity(0.74);
       imageView.setEffect(new GaussianBlur(13.07));

       TextArea description = new TextArea(competition.getDescription());
       description.setLayoutX(3);
       description.setLayoutY(67);
       description.setPrefSize(840, 79);
       description.setEditable(false);
       description.setWrapText(true);
       description.setFocusTraversable(false);
       description.setStyle("-fx-border-color: #0085ff transparent; -fx-background-color: transparent; -fx-border-width: 0.5;");
       description.setEffect(new DropShadow());

       Text competitionName = new Text(14, 52, competition.getNom());
       competitionName.setFont(new Font("Arial", 40));
       competitionName.setEffect(new DropShadow());

       Text date = new Text(326, 62, "Date: " + competition.getDate().toString());
       date.setEffect(new DropShadow());

       Line separatorOne = new Line(-100, 0, 50, 0);
       separatorOne.setLayoutX(420);
       separatorOne.setLayoutY(66);
       separatorOne.setStroke(Color.web("#0085ff"));
       separatorOne.setStrokeWidth(2);
       separatorOne.setEffect(new Glow(1.0));

       Text reservations = new Text(494, 62,"");
       reservations.setText(String.valueOf(competition.getCapacite()));
       reservations.setEffect(new DropShadow());

       Line separatorTwo = new Line(-50, 0, 0, 0);
       separatorTwo.setLayoutX(530);
       separatorTwo.setLayoutY(66);
       separatorTwo.setStroke(Color.web("#0085ff"));
       separatorTwo.setStrokeWidth(2);
       separatorTwo.setEffect(new Glow(1.0));

       Button viewButton = new Button("VIEW");
       viewButton.setLayoutX(721);
       viewButton.setLayoutY(18);
       viewButton.setPrefSize(101, 38);
       viewButton.setStyle("-fx-background-color: linear-gradient(to bottom right,#891b1b ,#a7473e );");
       viewButton.getStyleClass().add("login-btn");
       viewButton.setOnAction(event -> handleViewButtonAction(competition));
       // Add all components to the pane
       competitionPane.getChildren().addAll(imageView, description, competitionName, date, separatorOne, reservations, separatorTwo, viewButton);

       competitionPane.setEffect(new Glow(0.08));

       return competitionPane;
   }

    private void handleViewButtonAction(Competition competition) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewCompetition.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ViewCompetitionController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            Competition selectedCompetition = competition;

            controller.setCompetition(selectedCompetition,7);

            // Finally, set the scene's root to switch to the new view
            competitionsContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
