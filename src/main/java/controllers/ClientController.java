package controllers;

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
import models.Competition;
import services.CompetitionService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import models.Salle;
import services.SalleService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.*;
import services.ServiceProduit;

import java.awt.image.BufferedImage;
import java.util.Collections;
//QR code
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.image.WritableImage;

public class ClientController {
    private ServiceProduit p = new ServiceProduit();
    private ObservableList<tn.esprit.entites.Produit> produitsAchetes = FXCollections.observableArrayList();

    @FXML
    private VBox Produit;
    @FXML
    private Button clientRegime;


    @FXML
    private TextField rechercheTFP;
    @FXML
    private Button buttonReturn;
    @FXML
    private Button clientMeal;

    CompetitionService competitionService = new CompetitionService();
    @FXML
    private TextField rechercheTF;
    SalleService s = new SalleService();

    @FXML
    private VBox sallesContainer;
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
        try {
            sallesContainer.setSpacing(25);
            displaySalles();
          /*  rechercheTF.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    updateSallesDisplay(newValue.toLowerCase()); // Mettre à jour l'affichage des salles avec le nouveau texte de recherche
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Gérer l'exception, éventuellement afficher un message d'erreur à l'utilisateur
                }
            });*/

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
        try {
            Produit.setSpacing(25);
            displayProduits();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
       competitionPane.setStyle("-fx-border-color: grey; -fx-border-radius: 2; -fx-border-width: 4; -fx-background-color: rgba(0,0,0,0.6);");

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

       Text competitionName = new Text(14, 40, competition.getNom());
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
    @FXML
    private void displaySalles() throws SQLException {

        List<Salle> salles = s.recuperer(); // Adjust this line to match your method for fetching competitions

        for (Salle salle : salles) {
            Pane salleEntry = createSalleEntry(salle);
            sallesContainer.getChildren().add(salleEntry);
        }
    }
    @FXML
    public void chercher() throws SQLException {
        updateSallesDisplay(rechercheTF.getText().toLowerCase());
    }
    private void updateSallesDisplay(String recherche) throws SQLException {
        sallesContainer.getChildren().clear(); // Effacer les salles actuellement affichées
        List<Salle> salles = s.recuperer(); // Récupérer toutes les salles depuis la base de données
        for (Salle salle : salles) {
            // Vérifier si le nom de la salle contient le texte de recherche
            if (salle.getNom().toLowerCase().contains(recherche)) {
                Pane salleEntry = createSalleEntry(salle); // Créer une entrée pour la salle
                sallesContainer.getChildren().add(salleEntry); // Ajouter l'entrée à la liste des salles affichées
            }
        }
    }

    public Pane createSalleEntry(Salle salle) {
        Pane sallePane = new Pane();
        sallePane.setPrefSize(912, 217); // Adjusted to match your FXML Pane size
        sallePane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");
        Image image = new Image("file:" + salle.getImage());

        ImageView salleImageView = new ImageView();
        // Ensure the path is correct
        salleImageView.setImage(image);
        salleImageView.setFitHeight(166);
        salleImageView.setFitWidth(179);
        salleImageView.setLayoutX(14);
        salleImageView.setLayoutY(27);

        Text salleName = new Text(300, 38,"nom de la salle :"+ salle.getNom()); // Adjusted X to align with TextArea
        salleName.setFont(new Font("Arial", 21));
        salleName.setEffect(new DropShadow());
        salleName.setUnderline(true);

        TextArea salleDescription = new TextArea("description : " +salle.getDescription());
        salleDescription.setLayoutX(215);
        salleDescription.setLayoutY(60);
        salleDescription.setPrefHeight(91);
        salleDescription.setPrefWidth(512);
        salleDescription.setEditable(false);
        salleDescription.setWrapText(true);
        salleDescription.setFocusTraversable(false);
        salleDescription.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        salleDescription.setFont(new Font("Arial", 17));
        salleDescription.setEffect(new DropShadow());

        Text salleLieu = new Text(600, 172, "Lieu: " + salle.getLieu()); // Adjusted X to align with the map icon
        salleLieu.setFont(new Font("Arial", 14));
        salleLieu.setEffect(new DropShadow());
        salleLieu.setOnMouseClicked(event->naviguezVersWeb(salle.getLieu()));

        // Assuming you want to add buttons for materials and subscriptions similar to your FXML example
        Button materielsButton = new Button("Matériels");
        materielsButton.setLayoutX(735);
        materielsButton.setLayoutY(30);
        materielsButton.setPrefSize(149, 49);
        materielsButton.setOnAction(event -> handleViewButtonAction(salle.getId()));
        // Set the button graphic if desired, assuming you have an image for it
        ImageView imageView1 = new ImageView(new Image("/flaticon/dumbbell.png"));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        materielsButton.setGraphic(imageView1);

        Button abonnementsButton = new Button("Abonnements");
        abonnementsButton.setLayoutX(735);
        abonnementsButton.setLayoutY(95);
        abonnementsButton.setPrefSize(149, 49);
        abonnementsButton.setOnAction(event->handleViewButtonActionAbonnement(salle.getId()));
        // Create an image view with the subscription image
        ImageView imageView = new ImageView(new Image("/flaticon/subscription.png"));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        abonnementsButton.setGraphic(imageView);
        // Set the button graphic if desired, assuming you have an image for it


        ImageView imageView2 = new ImageView();
        imageView2.setLayoutX(550);
        imageView2.setLayoutY(140);
        imageView2.setFitWidth(38);
        imageView2.setFitHeight(39);
        imageView2.setPickOnBounds(true);

        // Set the image to the image view
        Image image1 = new Image("/flaticon/map.png");
        imageView2.setImage(image1);


        ImageView imageView3 = new ImageView();
        imageView3.setLayoutX(230);
        imageView3.setLayoutY(140);
        imageView3.setFitWidth(38);
        imageView3.setFitHeight(39);
        imageView3.setPickOnBounds(true);

        // Set the image to the image view
        Image image2 = new Image("/flaticon/schedule (1).png");
        imageView3.setImage(image2);
        Text text = new Text();
        text.setLayoutX(277);
        text.setLayoutY(154);
        text.setText("horaire :                                  lundi->vendredi 8h->21h        samedi->dimanche :9h->13h30");
        text.setWrappingWidth(229.2109375);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setFont(new Font("Arial", 14));
        text.setEffect(new DropShadow());
        // Add all components to the pane
        sallePane.getChildren().addAll( salleImageView,salleName, salleDescription, salleLieu, materielsButton, abonnementsButton,text,imageView2,imageView3);

        return sallePane;
    }

    void naviguezVersMateriel(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MaterielClient.fxml"));//charger le fichier FXML
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void handleViewButtonAction(int id) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MaterielClient.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            MaterielClientController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setMateriel(id);

            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void handleViewButtonActionAbonnement(int id) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnementClient.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AbonnementClientController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setAbonnement(id);

            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void naviguezVersWeb(String search) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TestMap.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            TestMapController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setWeb(search);

            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    private void displayProduits() throws SQLException {
        List<tn.esprit.entites.Produit> produits = p.recuperer();

        for (tn.esprit.entites.Produit produit : produits) {
            Pane produitEntry = createProduitEntry(produit);
            Produit.getChildren().add(produitEntry);
        }
    }

    public Pane createProduitEntry(tn.esprit.entites.Produit produit) {
        Pane produitPane = new Pane();
        produitPane.setId("produitPane" + produit.getId_produit());
        produitPane.setPrefSize(912, 217);
        produitPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");
//qr
        //QR
        Text produitText = new Text("Double clic pour générer le QR code");
        produitText.setLayoutX(500);
        produitText.setLayoutY(200);
        produitPane.setOnMouseClicked(event -> {
            generateQRCode(produit);
        });
        ImageView imageView = new ImageView(new Image(produit.getImage()));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setLayoutX(14);
        imageView.setLayoutY(27);

        Text produitName = new Text(300, 38, "NOM :" + produit.getNom());
        produitName.setFont(new Font("Arial", 21));
        produitName.setEffect(new DropShadow());
        produitName.setUnderline(true);

        TextArea produitDescription = new TextArea("DESCRIPTION : " + produit.getDescription());
        produitDescription.setLayoutX(215);
        produitDescription.setLayoutY(60);
        produitDescription.setPrefHeight(91);
        produitDescription.setPrefWidth(512);
        produitDescription.setEditable(false);
        produitDescription.setWrapText(true);
        produitDescription.setFocusTraversable(false);
        produitDescription.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        produitDescription.setFont(new Font("Arial", 17));
        produitDescription.setEffect(new DropShadow());

        TextArea produitCout = new TextArea("PRIX(DT) : " + produit.getCout());
        produitCout.setLayoutX(215);
        produitCout.setLayoutY(90);
        produitCout.setPrefHeight(91);
        produitCout.setPrefWidth(512);
        produitCout.setEditable(false);
        produitCout.setWrapText(true);
        produitCout.setFocusTraversable(false);
        produitCout.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        produitCout.setFont(new Font("Arial", 17));
        produitCout.setEffect(new DropShadow());


        produitPane.getChildren().addAll(imageView, produitName, produitDescription, produitCout,produitText);

        return produitPane;
    }

    private void acheterProduit(tn.esprit.entites.Produit produit) {
        if (!produitsAchetes.contains(produit)) {
            ajouterAuPanier(produit);
            System.out.println("Produit acheté : " + produit.getNom());
            showAlert(Alert.AlertType.INFORMATION, "Produit ajouté", "Le produit a été ajouté au panier !");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Ce produit est déjà ajouté au panier !");
        }
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();

    }








    public void ajouterAuPanier(tn.esprit.entites.Produit produit) {
        produitsAchetes.add(produit);
    }

    @FXML
    public void chercherp() throws SQLException {
        updateProduitDisplay(rechercheTFP.getText().toLowerCase());
    }

    private void updateProduitDisplay(String recherche) throws SQLException {
        Produit.getChildren().clear();
        List<tn.esprit.entites.Produit> produits = p.recuperer();
        for (tn.esprit.entites.Produit produit : produits ) {
            if (produit.getNom().toLowerCase().contains(recherche)) {
                Pane salleEntry = createProduitEntry(produit);
                Produit.getChildren().add(salleEntry);
            }
        }
    }

    @FXML
    private void trierProduits(ActionEvent event) {
        try {
            List<tn.esprit.entites.Produit> produits = p.recuperer();
            Collections.sort(produits, Comparator.comparingDouble(tn.esprit.entites.Produit::getCout).reversed());
            afficherProduits(produits);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherProduits(List<tn.esprit.entites.Produit> produits) {
        Produit.getChildren().clear();
        for (tn.esprit.entites.Produit produit : produits) {
            Pane produitEntry = createProduitEntry(produit);
            Produit.getChildren().add(produitEntry);
        }
    }
    @FXML
    void naviguezVersAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AcceuilC.fxml"));
            buttonReturn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void generateQRCode(tn.esprit.entites.Produit produit) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String information = "NOM: " + produit.getNom() + "\n" + "Description : " + produit.getDescription() + "\n" + "Prix : " + produit.getCout() + "\n";
        int width = 100;
        int height = 100;

        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(information, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    bufferedImage.setRGB(j, i, byteMatrix.get(j, i) ?  0x000000 : 0xFFFFFF);
                }
            }

            // Convert BufferedImage to JavaFX Image
            WritableImage writableImage = new WritableImage(width, height);
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixelWriter.setArgb(x, y, bufferedImage.getRGB(x, y));
                }
            }

            // Create ImageView for the QR code image
            ImageView imageViewQRCode = new ImageView(writableImage);
            imageViewQRCode.setLayoutX(600);
            imageViewQRCode.setLayoutY(65);
            // Find the product pane corresponding to the clicked product
            Pane productPane = (Pane) Produit.getChildren().stream()
                    .filter(node -> node.getId().equals("produitPane" + produit.getId_produit()))
                    .findFirst().orElse(null);

            // Add the QR code image to the product pane
            if (productPane != null) {
                productPane.getChildren().add(imageViewQRCode);
            }

        } catch (WriterException ex) {
            ex.printStackTrace();
        }
    }

    public void GoToMealClient(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/market.fxml"));
            clientMeal.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void GoToRegimeClient(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FillDietForm.fxml"));
            clientMeal.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
