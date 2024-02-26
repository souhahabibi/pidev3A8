package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClientAfficherCompetitionsController {
    CompetitionService competitionService = new CompetitionService();

    @FXML
    private VBox competitionsContainer; // Ensure this matches the fx:id of the VBox in your FXML
    @FXML
    private TextField searchTF;
    @FXML
    private boolean sortbydays=false;
    @FXML
    private boolean sortbycapacity=false;
    @FXML
    private boolean sortorder=false;
    @FXML
    private boolean selection=false;   //false = ongoing //true = finished
    @FXML
    private ToggleButton orderTB;

    @FXML
    private ToggleButton selectionTB;
    @FXML
    private CheckBox dayCB;
    @FXML
    private CheckBox capacityCB;

    @FXML
    public void setSelectionClicked() throws SQLException {
        selection= !selection;
        if (selection) {
            selectionTB.setText("Finished");
        } else {
            selectionTB.setText("Ongoing");
        }
        displayCompetitions();
    }
    @FXML
    public void setSortbydaysClicked() throws SQLException {
        sortbydays= !sortbydays;
        if(sortbydays)
        {
            sortbycapacity=false;
            capacityCB.setSelected(false);
        }
        displayCompetitions();
    }
    @FXML
    public void setSortbycapacityClicked() throws SQLException {
        sortbycapacity= !sortbycapacity;
        if(sortbycapacity)
        {
            sortbydays=false;
            dayCB.setSelected(false);
        }
        displayCompetitions();
    }
    @FXML
    public void setSortorderClicked() throws SQLException {
        sortorder= !sortorder;
        if ((sortorder)) {
            orderTB.setText("Descending");
        } else {
            orderTB.setText("Ascending");
        }
        displayCompetitions();
    }
    @FXML
    public void initialize() {
        searchTF.setStyle("-fx-text-fill: black; -fx-background-color: white");
        orderTB.setDisable(true);
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
        List<Competition> competitions = competitionService.recuperer();
        LocalDate today = LocalDate.now();

        // Filter competitions based on search criteria
        String searchText = searchTF.getText().toLowerCase();
        List<Competition> filteredCompetitions = competitions.stream()
                .filter(c -> c.getNom().toLowerCase().contains(searchText))
                .collect(Collectors.toList());


        if (selection) {
            // Shows only finished competitions (before today)
            filteredCompetitions = filteredCompetitions.stream()
                    .filter(c -> {
                        LocalDate competitionDate = c.getDate().toLocalDate();
                        return competitionDate.isBefore(today);
                    })
                    .collect(Collectors.toList());
        } else {
            // Shows only ongoing (upcoming, including today) competitions
            filteredCompetitions = filteredCompetitions.stream()
                    .filter(c -> {
                        LocalDate competitionDate = c.getDate().toLocalDate();
                        return !competitionDate.isBefore(today); // Includes today and any day after
                    })
                    .collect(Collectors.toList());
        }

        if(sortbydays ||sortbycapacity)
            orderTB.setDisable(false);
        else
            orderTB.setDisable(true);

        if(sortbydays)
        {
            filteredCompetitions.sort(Comparator.comparing(Competition::getDate));
            if(sortorder)
                filteredCompetitions.sort(Comparator.comparing(Competition::getDate).reversed());
        }
        if(sortbycapacity)
        {
            filteredCompetitions.sort(Comparator.comparingInt(Competition::getCapacite));
            if(sortorder)
                filteredCompetitions.sort(Comparator.comparingInt(Competition::getCapacite).reversed());
        }
        if(!filteredCompetitions.isEmpty())
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
       competitionPane.setPrefSize(820, 157);
       competitionPane.setStyle("-fx-border-color: gray; -fx-border-radius: 2; -fx-border-width: 4; -fx-background-color: rgba(0,0,0,0.6);");

       ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/gymimage.png"))); // Adjust the path as necessary
       imageView.setFitHeight(154);
       imageView.setFitWidth(820);
       imageView.setOpacity(0.74);
       imageView.setEffect(new GaussianBlur(13.07));

       TextArea description = new TextArea(competition.getDescription());
       description.setLayoutX(3);
       description.setLayoutY(67);
       description.setPrefSize(810, 79);
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
       viewButton.setLayoutX(700);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewCompetition.fxml"));
            Parent root = loader.load();
            ViewCompetitionController controller = loader.getController();
            Competition selectedCompetition = competition;

            controller.setCompetition(selectedCompetition,7);
            competitionsContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
