package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.*;
import services.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import models.Competition;
import models.Organisateur;
import models.Reservation;
import services.CompetitionService;
import services.OrganisateurService;
import services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import services.AbonnementService;
import services.MaterielService;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.ServiceProduit;
import tn.esprit.entites.Fournisseur;
import tn.esprit.entites.Produit;
import services.ServiceFournisseur;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javafx.scene.paint.Color;
import services.Mailservice;
//pdf
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
public class AdminController extends ListCell<tn.esprit.entites.Produit>{
    private final ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
    private final ServiceProduit serviceProduit = new ServiceProduit();


    @FXML

    private Button btnAjouterC;


    @FXML

    private Button btnModifierC;

    @FXML
    private TextField nomTF;
    @FXML
    private TextField descriptionTF;

    @FXML
    private Button regimebtn;

    private Cours cours;
    private String niveau;

    //Exercice//
    @FXML

    private Button btnAjouter2;


    @FXML

    private Button btnModifier2;


    private Exercice exercice;


    @FXML
    private ListView<Exercice> listViewE;

    private final ExerciceService es = new ExerciceService();


    @FXML
    private TabPane tabPane;

    @FXML
    private Tab ExTab;
    ///

    @FXML
    private ListView<Cours> listViewC;

    private final CoursService cs = new CoursService();

    @FXML
    private Button goToMeal;



    @FXML
    private Button buttonF_DELETE;
    @FXML
    private Button buttonF_MODIFY;

    @FXML
    private Button buttonF_ADD;
    @FXML
    private Button buttonP_DELETE;
    @FXML
    private Button buttonP_MODIFY;
    ServiceProduit p = new ServiceProduit();
    services.ServiceFournisseur f = new services.ServiceFournisseur();
    @FXML
    private VBox fournisseurContainer;

    @FXML
    private VBox produitContainer;


    @FXML
    private Button buttonAjouter;
    @FXML
    private Button buttonP_ADD;
    @FXML
    private Button buttonF_PRODUITS;
    @FXML
    private ListView<Fournisseur> listView;
    @FXML
    private ListView<tn.esprit.entites.Produit> listView0;
    @FXML
    private Button buttonReturn;
    SalleService s = new SalleService();
    AbonnementService a = new AbonnementService();
    @FXML
    private VBox sallesContainer;

    @FXML
    private VBox abonnementsContainer;
    private final ImageView imageView = new ImageView();
    private final double IMAGE_SIZE = 100; // Taille de l'image

    public void ImageListCell() {
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);
    }

    @FXML
    private Button buttonAjouter1;
    OrganisateurService organisateurService = new OrganisateurService();
    CompetitionService competitionService = new CompetitionService();
    ReservationService reservationService = new ReservationService();
    @FXML
    private VBox OrganisateurContainer;
    @FXML
    private VBox CompetitionContainer;
    public void initialize() {
        showlistOrganisateur();
        showlistCompetition();
        try {
            sallesContainer.setSpacing(25);// Définition de l'espacement entre les éléments du conteneur
            displaySalles();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
        try {
            fournisseurContainer.setSpacing(25);// Définition de l'espacement entre les éléments du conteneur
            displayFournisseur();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
        try {
            produitContainer.setSpacing(25);// Définition de l'espacement entre les éléments du conteneur
            displayProduit();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }


        ///Cours

        List<Cours> cours = cs.afficher();
        ObservableList<Cours> observableList = FXCollections.observableList(cours);
        listViewC.setItems(observableList);

        listViewC.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label nomLabel = new Label("Nom: ");
            private final Text nom = new Text();
            private final HBox nomBox = new HBox(nomLabel, nom);

            private final Label descriptionLabel = new Label("Description: ");
            private final Text description = new Text();
            private final HBox descriptionBox = new HBox(descriptionLabel, description);

            private final Label niveauLabel = new Label("Niveau: ");
            private final Text niveau = new Text();
            private final HBox niveauBox = new HBox(niveauLabel, niveau);

            private final Button AjouterButton = new Button("Ajouter Exercice");
            private final HBox Ajouter = new HBox(10,AjouterButton);



            private final VBox vBox = new VBox(10,nomBox, descriptionBox, niveauBox,Ajouter);
            private final HBox hBox = new HBox(35, imageView, vBox);

            {
                imageView.setFitHeight(300); // ajuster la hauteur comme vous le souhaitez
                imageView.setFitWidth(300); // ajuster la largeur comme vous le souhaitez
                // changer la couleur de fond du HBox en vert clair
                hBox.setStyle("-fx-border-style: solid inside;"
                        + "-fx-border-width: 0;" + "-fx-border-insets: 5;");
                // changer la couleur du texte en blanc
                nomLabel.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
                descriptionLabel.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
                niveauLabel.setStyle("-fx-text-fill: white;"+"-fx-font-weight: bold;");
                // changer la police du texte en Comic Sans MS
                vBox.setStyle("-fx-font-family: 'Comic Sans MS';" + "-fx-font-size: 14px;");




                AjouterButton.setStyle("-fx-background-color: #a6b5c9,linear-gradient(#303842 0%, #3e5577 20%, #375074 100%),linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%);-fx-background-insets: 0 0 -1 0,0,1;-fx-background-radius: 5,5,4;-fx-padding: 7 30 7 30;-fx-text-fill: #242d35;-fx-font-family: 'Helvetica';-fx-font-size: 20px;-fx-text-fill: white;-fx-pref-width: 250px;-fx-pref-height: 30px;");


                AjouterButton.setOnAction(event -> {
                    Cours selectedCours = listViewC.getSelectionModel().getSelectedItem();
                    SharedModel.setId(selectedCours.getId());

                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/AjouterExercice.fxml"));
                        AjouterButton.getScene().setRoot(root);






                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                });





            }

            @Override
            protected void updateItem(Cours item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image("file:" + item.getImage()));
                    nom.setText(item.getNom());
                    description.setText(item.getDescription());
                    niveau.setText(item.getNiveau());
                    setGraphic(hBox);
                }
            }
        });


///Exercice//
        List<Exercice> exercice = es.afficher();
        ObservableList<Exercice> observableList2 = FXCollections.observableList(exercice);
        listViewE.setItems(observableList2);

        listViewE.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label nom = new Label("Nom: ");
            private final Text nomLabel= new Text();
            private final HBox nomBox = new HBox(nom, nomLabel);
            private final Label etape = new Label("Etape: ");
            private final Text etapeLabel = new Text();
            private final HBox etapeBox = new HBox(etape, etapeLabel);
            private final VBox vBox = new VBox(10,nomBox, etapeBox);


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
                etape.setStyle("-fx-font-weight: bold;");
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


        /////////////////



    }
    private void displayFournisseur() throws SQLException {

        List<Fournisseur> fournisseurs = f.recuperer(); // Adjust this line to match your method for fetching competitions

        for (tn.esprit.entites.Fournisseur salle : fournisseurs) {
            Pane fournisseurEntry = createFournisseurEntry(salle);
            fournisseurContainer.getChildren().add(fournisseurEntry);
        }
    }
    private void displayProduit() throws SQLException {

        List<Produit> produits = p.recuperer(); // Adjust this line to match your method for fetching competitions

        for (tn.esprit.entites.Produit salle : produits) {
            Pane produitEntry = createProduitEntry(salle);
            produitContainer.getChildren().add(produitEntry);
        }
    }
    @FXML
    public Pane createProduitEntry(Produit produit) {
        Pane produitPane = new Pane();
        produitPane.setPrefSize(880, 247);
        produitPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

        Text nomLabel = new Text(314, 36, "Nom : " + produit.getNom());
        nomLabel.setFont(Font.font("Arial", 16));
        nomLabel.setEffect(new DropShadow());

        Text quantiteLabel = new Text(314, 83, "Quantité : " + produit.getQuantite());
        quantiteLabel.setFont(Font.font("Arial", 16));
        quantiteLabel.setEffect(new DropShadow());

        Text coutLabel = new Text(314, 130, "Coût : " + produit.getCout());
        coutLabel.setFont(Font.font("Arial", 16));
        coutLabel.setEffect(new DropShadow());

        Text dateExpirationLabel = new Text(314, 177, "Date d'expiration : " + produit.getDate_expiration());
        dateExpirationLabel.setFont(Font.font("Arial", 16));
        dateExpirationLabel.setEffect(new DropShadow());

        Text fournisseurLabel = new Text(314, 200, "Fournisseur : " + produit.getFournisseur().getNom());
        dateExpirationLabel.setFont(Font.font("Arial", 16));
        dateExpirationLabel.setEffect(new DropShadow());

        Text descriptionLabel = new Text(314, 224, "Description : " + produit.getDescription());
        descriptionLabel.setFont(Font.font("Arial", 16));
        descriptionLabel.setEffect(new DropShadow());

        Button modifierButton = new Button("Modifier");
        modifierButton.setLayoutX(680);
        modifierButton.setLayoutY(20);
        modifierButton.setPrefSize(159, 31);
        modifierButton.setOnAction(event -> naviguezVersMODIFYProduit(null, produit));
        modifierButton.getStyleClass().add("login-btn");
        modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView1 = new ImageView(new Image("/flaticon/note.png"));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        modifierButton.setGraphic(imageView1);
//image produit

        Image image = new Image(produit.getImage());
        ImageView materielImageView = new ImageView();
        materielImageView.setImage(image);
        materielImageView.setFitHeight(172);
        materielImageView.setFitWidth(189);
        materielImageView.setLayoutX(23);
        materielImageView.setLayoutY(10);
//pdf
        //pdf
        Button pdfButton = new Button( "     pdf");
        pdfButton.setLayoutX(680);
        pdfButton.setLayoutY(150);
        pdfButton.setPrefSize(159, 31);
        pdfButton.setOnAction(event -> downloadProductPDF(null, produit,20));
        pdfButton.getStyleClass().add("login-btn");
        pdfButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView5 = new ImageView(new Image("/flaticon/pdf.png"));
        imageView5.setFitWidth(30);
        imageView5.setFitHeight(30);
        pdfButton.setGraphic(imageView5);

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setLayoutX(680);
        supprimerButton.setLayoutY(90);
        supprimerButton.setPrefSize(159, 31);
        supprimerButton.setOnAction(event -> deleteProduit(produit));
        supprimerButton.getStyleClass().add("login-btn");
        supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView2 = new ImageView(new Image("/flaticon/removed.png"));
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        supprimerButton.setGraphic(imageView2);

        produitPane.getChildren().addAll(nomLabel, quantiteLabel, coutLabel, dateExpirationLabel, descriptionLabel, modifierButton, supprimerButton,fournisseurLabel,materielImageView,pdfButton);

        return produitPane;
    }
    private void deleteProduit(Produit produit) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Confirmer la suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (true) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    p.supprimer(produit.getId_produit());
                    // Nettoyer le contenu du conteneur avant de réafficher les salles
                    produitContainer.getChildren().clear();
                    initialize();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la suppression de produit : " + e.getMessage());
                }
            }
        });

    }
    private void naviguezVersMODIFYProduit(Object o, Produit produit) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierProduits.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierProduitController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            // If an item is selected, pass it to the controller of the next scene
            controller.setProduits(produit);



            // Finally, set the scene's root to switch to the new view
            produitContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    public Pane createFournisseurEntry(Fournisseur fournisseur) {
        Pane fournisseurPane = new Pane();
        fournisseurPane.setPrefSize(880, 247);
        fournisseurPane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

        Text nomLabel = new Text(314, 36, "Nom : " + fournisseur.getNom());
        nomLabel.setFont(Font.font("Arial", 16));
        nomLabel.setEffect(new DropShadow());

        Text prenomLabel = new Text(314, 83, "Prénom : " + fournisseur.getPrenom());
        prenomLabel.setFont(Font.font("Arial", 16));
        prenomLabel.setEffect(new DropShadow());

        Text numeroLabel = new Text(314, 130, "Numéro : " + fournisseur.getNumero());
        numeroLabel.setFont(Font.font("Arial", 16));
        numeroLabel.setEffect(new DropShadow());

        Text typeLabel = new Text(314, 177, "Type : " + fournisseur.getType());
        typeLabel.setFont(Font.font("Arial", 16));
        typeLabel.setEffect(new DropShadow());

        Button modifierButton = new Button("Modifier");
        modifierButton.setLayoutX(680);
        modifierButton.setLayoutY(20);
        modifierButton.setPrefSize(159, 31);
        modifierButton.setOnAction(event -> naviguezVersMODIFYFournisseur(null, fournisseur));
        modifierButton.getStyleClass().add("login-btn");
        modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView1 = new ImageView(new Image("/flaticon/note.png"));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        modifierButton.setGraphic(imageView1);



        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setLayoutX(680);
        supprimerButton.setLayoutY(90);
        supprimerButton.setPrefSize(159, 31);
        supprimerButton.setOnAction(event -> deleteFournisseur(fournisseur));
        supprimerButton.getStyleClass().add("login-btn");
        supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView2 = new ImageView(new Image("/flaticon/removed.png"));
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        supprimerButton.setGraphic(imageView2);

        Button addProduitButton = new Button("Add Produit");
        addProduitButton.setLayoutX(680);
        addProduitButton.setLayoutY(160);
        addProduitButton.setPrefSize(159, 31);
        addProduitButton.setOnAction(event -> addProduitToFournisseur(fournisseur));
        addProduitButton.getStyleClass().add("login-btn");
        addProduitButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        //   ImageView imageView3 = new ImageView(new Image("/flaticon/add.png"));
        //  imageView3.setFitWidth(30);
        // imageView3.setFitHeight(30);
        //  addProduitButton.setGraphic(imageView3);

        fournisseurPane.getChildren().addAll(nomLabel, prenomLabel, numeroLabel, typeLabel, modifierButton, supprimerButton, addProduitButton);

        return fournisseurPane;
    }
    @FXML
    void naviguezVersMODIFYFournisseur(ActionEvent event, Fournisseur fournisseur) {

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierFournisseurs.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierFournisseurController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            // If an item is selected, pass it to the controller of the next scene
            controller.setFournisseur(fournisseur);



            // Finally, set the scene's root to switch to the new view
            fournisseurContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void deleteFournisseur(Fournisseur fournisseur){

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Confirmer la suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette salle ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    f.supprimer(fournisseur.getId_fournisseur());
                    // Nettoyer le contenu du conteneur avant de réafficher les salles
                    fournisseurContainer.getChildren().clear();
                    initialize();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la suppression de fournisseur : " + e.getMessage());
                }
            }
        });
    }
    public void showlistOrganisateur()
    {
        try {
            OrganisateurContainer.setSpacing(25);
            displayOrganisateurs();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    @FXML
    private void displayOrganisateurs() throws SQLException {
        OrganisateurContainer.getChildren().clear(); // Clear existing competition entries
        List<Organisateur> organisateurs = organisateurService.recuperer(); // Fetch all competitions

        for (Organisateur organisateur : organisateurs) {
            Pane competitionEntry = createOrganisateurEntry(organisateur);
            OrganisateurContainer.getChildren().add(competitionEntry);
        }
    }
    @FXML
    private Pane createOrganisateurEntry(Organisateur organisateur) {
        Pane organisateurPane = new Pane();
        organisateurPane.setLayoutX(38.0);
        organisateurPane.setLayoutY(64.0);
        organisateurPane.setPrefHeight(68.0);
        organisateurPane.setPrefWidth(827.0);
        organisateurPane.setStyle("-fx-background-color: linear-gradient(to bottom right,#891b1b ,#a7473e );");

        Text nomText = new Text("Nom: " + organisateur.getNom());
        nomText.setLayoutX(37.0);
        nomText.setLayoutY(40.0);

        Text numeroText = new Text("Numero: " + organisateur.getNumero());
        numeroText.setLayoutX(301.0);
        numeroText.setLayoutY(40.0);

        // Example for an Edit button
        Button editButton = new Button();
        editButton.setStyle("-fx-background-color:white; ");
        editButton.setLayoutX(575.0);
        editButton.setLayoutY(14.0);
        ImageView editImageView = new ImageView(new Image("flaticon/bouton-modifier.png")); // Ensure the path is correct
        editImageView.setFitHeight(30.0);
        editImageView.setFitWidth(32.0);
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> naviguezVersMODIFYORGANISATEUR(null,organisateur));

        // Example for a Delete button
        Button deleteButton = new Button();
        deleteButton.setStyle("-fx-background-color:white; ");
        deleteButton.setLayoutX(648.0);
        deleteButton.setLayoutY(14.0);
        ImageView deleteImageView = new ImageView(new Image("flaticon/bouton-supprimer.png")); // Ensure the path is correct
        deleteImageView.setFitHeight(30.0);
        deleteImageView.setFitWidth(32.0);
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteOrganisateur(organisateur));

        // Example for a custom button, e.g., view competitions
        Button customButton = new Button();
        customButton.setStyle("-fx-background-color:white; ");
        customButton.setLayoutX(727.0);
        customButton.setLayoutY(14.0);
        ImageView customImageView = new ImageView(new Image("flaticon/competition.png")); // Ensure the path is correct
        customImageView.setFitHeight(30.0);
        customImageView.setFitWidth(32.0);
        customButton.setGraphic(customImageView);
        customButton.setOnAction(event -> naviguezVersADDCOMPETITION(null,organisateur));

        // Add all components to the pane
        organisateurPane.getChildren().addAll(nomText, numeroText, editButton, deleteButton, customButton);

        return organisateurPane;
    }
    @FXML
    void deleteOrganisateur(Organisateur organisateur)
    {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this organizer?");

        // Show the confirmation dialog and wait for the user's response
        Optional<ButtonType> result = confirmAlert.showAndWait();

        // Check if the OK button was clicked
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose OK, proceed with deletion
            try {
                organisateurService.supprimer(organisateur.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize(); // Re-initialize to reflect changes
        } else {
            // User chose CANCEL or closed the dialog, do not delete
        }
    }
    @FXML
    void deleteCompetition(Competition competition)
    {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to delete this competition?");

        // Show the confirmation dialog and wait for the user's response
        Optional<ButtonType> result = confirmAlert.showAndWait();

        // Check if the OK button was clicked
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose OK, proceed with deletion
            try {
                competitionService.supprimer(competition.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            initialize(); // Re-initialize to reflect changes
        } else {
            // User chose CANCEL or closed the dialog, do not delete
        }
    }
    @FXML
    void naviguezVersADDORGANISATEUR(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterOrganisateur.fxml"));
            OrganisateurContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYORGANISATEUR(ActionEvent event,Organisateur organisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyOrganisateur.fxml"));
            Parent root = loader.load();
            ModifyOrganisateurController controller = loader.getController();
                controller.setOrganisateur(organisateur);
            OrganisateurContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYCOMPETITION(ActionEvent event,Competition competition) {

        ObservableList<Organisateur> observableList = null;
        try {
            List<Organisateur> Organisateurs = organisateurService.recuperer();
            observableList = FXCollections.observableList(Organisateurs);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyCompetition.fxml"));

            Parent root = loader.load();
            ModifyCompetitionController controller = loader.getController();
            controller.setCompetition(competition,observableList);
            CompetitionContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersADDCOMPETITION(ActionEvent event,Organisateur organisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCompetition.fxml"));
            Parent root = loader.load();
            AjouterCompetitionController controller = loader.getController();
                controller.setOrganisateur(organisateur);
            OrganisateurContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersSCORES(ActionEvent event,Competition competition) {
        ObservableList<Reservation> observableList = null;
        try {
            List<Reservation> Reservations = reservationService.getReservations(competition.getId());
            observableList = FXCollections.observableList(Reservations);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyScores.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifyScoresController controller = loader.getController();

            controller.initialize(observableList);


            // Finally, set the scene's root to switch to the new view
            CompetitionContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void showlistCompetition()
    {
        try {
            CompetitionContainer.setSpacing(25);
            displayCompetitions();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, possibly showing an error message to the user
        }
    }
    @FXML
    private void displayCompetitions() throws SQLException {
        CompetitionContainer.getChildren().clear(); // Clear existing competition entries
        List<Competition> competitions = competitionService.recuperer(); // Fetch all competitions

        for (Competition competition : competitions) {
            Pane competitionEntry = createCompetitionEntry(competition);
            CompetitionContainer.getChildren().add(competitionEntry);
        }
    }
    @FXML
    public Pane createCompetitionEntry(Competition competition) {
        Pane competitionPane = new Pane();
        competitionPane.setLayoutX(33.0);
        competitionPane.setLayoutY(61.0);
        competitionPane.setPrefHeight(164.0);
        competitionPane.setPrefWidth(816.0);
        competitionPane.setStyle("-fx-background-color: linear-gradient(to bottom right,#891b1b ,#a7473e );");
        Text dateText = new Text("Date: " + competition.getDate().toString());
        dateText.setLayoutX(280.0);
        dateText.setLayoutY(156.0);

        Text nomText = new Text("Nom: " + competition.getNom());
        nomText.setLayoutX(280.0);
        nomText.setLayoutY(30.0);

        Text capaciteText = new Text("Capacite: " + competition.getCapacite());
        capaciteText.setLayoutX(430.0);
        capaciteText.setLayoutY(156.0);

        WebView videoWebView = new WebView();
        videoWebView.setLayoutX(5.0);
        videoWebView.setLayoutY(5.0);
        videoWebView.setPrefHeight(150.0);
        videoWebView.setPrefWidth(270.0);
        // Embed YouTube video using videoID
        String videoURL = "https://www.youtube.com/embed/" + competition.getVideoURL();
        String embedHTML = "<html><body style='margin:0;padding:0;'><iframe width='100%' height='100%' src='" + videoURL + "' frameborder='0' allowfullscreen style='border: 0'></iframe></body></html>";
        videoWebView.getEngine().loadContent(embedHTML);

        TextArea descriptionTextArea = new TextArea(competition.getDescription());
        descriptionTextArea.setLayoutX(280.0);
        descriptionTextArea.setLayoutY(44.0);
        descriptionTextArea.setPrefHeight(89.0);
        descriptionTextArea.setPrefWidth(450.0);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setWrapText(true);

        Button editButton = new Button();
        editButton.setLayoutX(740.0);
        editButton.setLayoutY(20.0);
        ImageView editImageView = new ImageView(new Image("flaticon/bouton-modifier.png"));
        editImageView.setFitHeight(27);
        editImageView.setFitWidth(27);
        editButton.setStyle("-fx-background-color:white; ");
        editButton.setGraphic(editImageView);
        editButton.setOnAction(event -> naviguezVersMODIFYCOMPETITION(null,competition));

        Button deleteButton = new Button();
        deleteButton.setLayoutX(740.0);
        deleteButton.setLayoutY(60.0);
        ImageView deleteImageView = new ImageView(new Image("flaticon/bouton-supprimer.png"));
        deleteImageView.setFitHeight(27);
        deleteImageView.setFitWidth(27);
        deleteButton.setStyle("-fx-background-color:white; ");
        deleteButton.setGraphic(deleteImageView);
        deleteButton.setOnAction(event -> deleteCompetition(competition));

        Button viewButton = new Button();
        viewButton.setLayoutX(740.0);
        viewButton.setLayoutY(100.0);
        ImageView viewImageView = new ImageView(new Image("flaticon/la-revue.png"));
        viewImageView.setFitHeight(27);
        viewImageView.setFitWidth(27);
        viewButton.setStyle("-fx-background-color:white; ");
        viewButton.setGraphic(viewImageView);
        viewButton.setOnAction(event -> naviguezVersSCORES(null,competition));

        competitionPane.getChildren().addAll(dateText, nomText, capaciteText, videoWebView, descriptionTextArea, editButton, deleteButton, viewButton);

        return competitionPane;
    }
    @FXML
    void naviguezVersCLIENT_AFFICHER_COMPETITION(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Client.fxml"));
            CompetitionContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersSTATISTIQUE_COMPETITION(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StatistiqueCompetition.fxml"));
            CompetitionContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void displaySalles() throws SQLException {

        List<Salle> salles = s.recuperer(); // Adjust this line to match your method for fetching competitions

        for (Salle salle : salles) {
            Pane salleEntry = createSalleEntry(salle);
            sallesContainer.getChildren().add(salleEntry);
        }
    }

    @FXML
    public Pane createSalleEntry(Salle salle) {
        Pane sallePane = new Pane();
        sallePane.setPrefSize(880, 287);
        sallePane.setStyle("-fx-border-color: #666666; -fx-border-radius: 10; -fx-border-width: 1; -fx-background-color: rgba(200,200,200,0.4);-fx-background-radius: 11; ");

        Image image = new Image("file:" + salle.getImage());

        ImageView salleImageView = new ImageView();
        salleImageView.setImage(image);
        salleImageView.setFitHeight(150);
        salleImageView.setFitWidth(216);
        salleImageView.setLayoutX(14);
        salleImageView.setLayoutY(12);

        Text salleName = new Text(314, 36, "Nom : " + salle.getNom());
        salleName.setFont(Font.font("Arial",  16));
        salleName.setEffect(new DropShadow());

        Text salleLieu = new Text(314, 83, "Lieu : " + salle.getLieu());
        salleLieu.setFont(Font.font("Arial",  16));
        salleLieu.setEffect(new DropShadow());

        TextArea salleDescription = new TextArea("description : "+salle.getDescription());
        salleDescription.setLayoutX(300);
        salleDescription.setLayoutY(106);
        salleDescription.setPrefHeight(100);
        salleDescription.setPrefWidth(360);
        salleDescription.setEditable(false);
        salleDescription.setWrapText(true);
        salleDescription.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        salleDescription.setFont(Font.font("Arial", 16));
        salleDescription.setEffect(new DropShadow());

        ImageView salleImageView1 = new ImageView(new Image("/flaticon/gym (1).png"));
        salleImageView1.setFitHeight(30);
        salleImageView1.setFitWidth(30);
        salleImageView1.setLayoutX(275);
        salleImageView1.setLayoutY(15);

        ImageView salleImageView2 = new ImageView(new Image("/flaticon/map.png"));
        salleImageView2.setFitHeight(30);
        salleImageView2.setFitWidth(30);
        salleImageView2.setLayoutX(275);
        salleImageView2.setLayoutY(60);

        ImageView salleImageView3 = new ImageView(new Image("/flaticon/file (1).png"));
        salleImageView3.setFitHeight(30);
        salleImageView3.setFitWidth(30);
        salleImageView3.setLayoutX(275);
        salleImageView3.setLayoutY(120);

        Button modifierButton = new Button("Modifier");
        modifierButton.setLayoutX(680);
        modifierButton.setLayoutY(20);
        modifierButton.setPrefSize(159, 31);
        modifierButton.setOnAction(event -> naviguezVersMODIFY(null, salle));
// Ajouter la classe de style et la feuille de style
        modifierButton.getStyleClass().add("login-btn");
        modifierButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView1 = new ImageView(new Image("/flaticon/note.png"));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        modifierButton.setGraphic(imageView1);
        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setLayoutX(680);
        supprimerButton.setLayoutY(90);
        supprimerButton.setPrefSize(159, 31);
        supprimerButton.setOnAction(event -> deleteSalle(salle));
        supprimerButton.getStyleClass().add("login-btn");
        supprimerButton.getStylesheets().add(getClass().getResource("/design.css").toExternalForm());
        ImageView imageView2 = new ImageView(new Image("/flaticon/removed.png"));
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        supprimerButton.setGraphic(imageView2);
        // Assuming you want to add buttons for materials and subscriptions similar to your FXML example
        Button materielsButton = new Button(" matériels");
        materielsButton.setLayoutX(200);
        materielsButton.setLayoutY(220);
        materielsButton.setPrefSize(209, 49);
        materielsButton.setOnAction(event -> handleViewButtonAction(salle.getId()));
        // Set the button graphic if desired, assuming you have an image for it
        ImageView imageView = new ImageView(new Image("/flaticon/dumbbell.png"));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        materielsButton.setGraphic(imageView);

        Button abonnementsButton = new Button(" abonnements");
        abonnementsButton.setLayoutX(505);
        abonnementsButton.setLayoutY(220);
        abonnementsButton.setPrefSize(209, 49);
        abonnementsButton.setOnAction(event->handleViewButtonActionAbonnement(salle.getId()));
        // Create an image view with the subscription image
        ImageView imageView4 = new ImageView(new Image("/flaticon/subscription.png"));
        imageView4.setFitWidth(30);
        imageView4.setFitHeight(30);
        abonnementsButton.setGraphic(imageView4);
        sallePane.getChildren().addAll(salleImageView,salleImageView2,salleImageView3, salleName, salleLieu, salleDescription,salleImageView1, modifierButton, supprimerButton,materielsButton,abonnementsButton);

        return sallePane;
    }

    private void handleViewButtonAction(int id) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MaterielAdmin.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            MaterielAdminController controller = loader.getController();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AbonnementAdmin.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AbonnementAdminController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            controller.setAbonnement(id);

            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }





    @FXML
    void naviguezVersAjouterSalle(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterSalle.fxml"));//charger le fichier FXML
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }



    @FXML
    void deleteSalle(Salle salle){

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Confirmer la suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette salle ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    s.supprimer(salle.getId());
                    // Nettoyer le contenu du conteneur avant de réafficher les salles
                    sallesContainer.getChildren().clear();
                    initialize();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la suppression de la salle : " + e.getMessage());
                }
            }
        });
    }


    @FXML
    void naviguezVersMODIFY(ActionEvent event,Salle salle) {

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSalle.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierSalleController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            // If an item is selected, pass it to the controller of the next scene
            controller.setSalle(salle);



            // Finally, set the scene's root to switch to the new view
            sallesContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // Méthode utilitaire pour afficher une boîte de dialogue d'alerte
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();

    }

    @FXML
    void naviguezVersADDFournisseur(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterFournisseurs.fxml"));//charger le fichier FXML
            fournisseurContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void downloadProductPDF(Event event, Produit produit, int espacement) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.setNonStrokingColor(0x89, 0x1b, 0x1b); // Couleur du dégradé du bouton

                // Ajout du titre
                String title = "Votre Produit";
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 18;
                float titleHeight = PDType1Font.HELVETICA_BOLD.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 18;
                float xTitle = (page.getMediaBox().getWidth() - titleWidth) / 2;
                float yTitle = page.getMediaBox().getHeight() - titleHeight - 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(xTitle, yTitle);
                contentStream.showText(title);
                contentStream.endText();

                // Ajout de l'image
                PDImageXObject image = PDImageXObject.createFromFile("@flaticon/logo.png", document);
                float imageWidth = 100; // Largeur de l'image
                float imageHeight = 100; // Hauteur de l'image
                float xImage = 50; // Coordonnée x pour placer l'image dans le coin supérieur gauche
                float yImage = page.getMediaBox().getHeight() - imageHeight - 50; // Coordonnée y pour placer l'image dans le coin supérieur gauche
                contentStream.drawImage(image, xImage, yImage, imageWidth, imageHeight);

                // Déplacement du point d'écriture pour les détails du produit
                float xDetails = 200; // Coordonnée x pour les détails du produit
                float yDetails = yTitle - 50; // Coordonnée y pour les détails du produit
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setNonStrokingColor(0x00, 0x00, 0x00); // Couleur du texte noir
                contentStream.newLineAtOffset(xDetails, yDetails);

                // Ajoutez les détails du produit au PDF
                contentStream.showText("Nom du produit : " + produit.getNom());
                contentStream.newLineAtOffset(0, -espacement);
                contentStream.showText("Quantité : " + produit.getQuantite());
                contentStream.newLineAtOffset(0, -espacement);
                contentStream.showText("Coût : " + produit.getCout());

                // Ajoutez d'autres détails du produit selon vos besoins

                contentStream.endText();
            }

            // Spécifiez le chemin complet du fichier de sortie
            String outputFile = "C:\\Users\\ousse\\Desktop\\pdf\\output.pdf";

            document.save(new File(outputFile));
            System.out.println("Fichier PDF du produit créé avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier PDF : " + e.getMessage());
        }
    }
    private void addProduitToFournisseur(Fournisseur fournisseur) {
        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduits.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterProduitController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView


            // If an item is selected, pass it to the controller of the next scene
            controller.setFournisseur(fournisseur);



            // Finally, set the scene's root to switch to the new view
            fournisseurContainer.getScene().setRoot(root);
            // Envoyer un e-mail pour informer de l'ajout du produit
            String destinataire = "souhahbibi1@gmail.com";
            String sujet = "Nouveau produit ajouté";
            String contenu = "Un nouveau produit a été ajouté pour le fournisseur : " + fournisseur.getNom();

            Mailservice.envoyerEmailSansAuthentification(destinataire, sujet, contenu);


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguerVersMeal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/CoachMealManagement.fxml"));
            goToMeal.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguerVersRegime(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterCours.fxml"));
            regimebtn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    //Cours //

    @FXML
    void NaviguerVerAjouterC(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterCours.fxml"));
            btnAjouterC.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private Cours obtenirCoursSelectionne() {
        Cours coursSelectionne = listViewC.getSelectionModel().getSelectedItem();
        return coursSelectionne;
    }


    @FXML
    void modifierCours(ActionEvent event) {

        try {
            // Charger la page AjouterCours.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));
            Parent root = loader.load();
            // Récupérer le contrôleur de la page AjouterCours.fxml
            ModifierController controller = loader.getController();
            // Récupérer l'objet Cours à modifier

            Cours coursAmodifier = obtenirCoursSelectionne();
            // Passer l'objet Cours au contrôleur
            controller.setCours(coursAmodifier);
            // Créer une nouvelle scène avec la page chargée
            Scene scene = new Scene(root);
            // Récupérer la fenêtre principale de l'application
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Assigner la nouvelle scène à la fenêtre principale
            stage.setScene(scene);
            // Afficher la fenêtre principale
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }



    }



    @FXML
    void supprimerCours(ActionEvent event) {
        Cours coursASupprimer = obtenirCoursSelectionne();
        int indexASupprimer = listViewC.getSelectionModel().getSelectedIndex();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression de cours");
        alert.setContentText("Voulez-vous vraiment supprimer ce cours ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            listViewC.getItems().remove(indexASupprimer);
            cs.supprimer(coursASupprimer);
        } else {


        }
    }



    @FXML
    void NaviguerVerAjouterE(ActionEvent event) {
        try {
            // Charger la page AjouterExercice.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterExercice.fxml"));

            TabPane modifierExerciceTabPane = (TabPane) root.lookup("TabPane");
            // Obtenir l'onglet "Exercice" du TabPane
            Tab exerciceTab = modifierExerciceTabPane.getTabs().get(1); // L'index 1 correspond à l'onglet "Exercice"

            // Définir le contenu de l'onglet ExTab avec le contenu de l'onglet "Exercice"
            ExTab.setContent(exerciceTab.getContent());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    private Exercice obtenirExerciceSelectionne() {
        Exercice exerciceSelectionne = listViewE.getSelectionModel().getSelectedItem();
        return exerciceSelectionne;
    }



    @FXML
    void modifierExercice2(ActionEvent event) {
        try {
            // Charger la page ModifierExercice.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierExercice.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la page ModifierExercice.fxml
            ModifierExerciceController controller = loader.getController();
            // Récupérer l'objet Exercice à modifier
            Exercice exerciceAmodifier = obtenirExerciceSelectionne();
            // Passer l'objet Exercice au contrôleur
            controller.setExercice(exerciceAmodifier);

            // Obtenir le TabPane du Parent chargé
            TabPane modifierExerciceTabPane = (TabPane) root.lookup("TabPane");
            // Obtenir l'onglet "Exercice" du TabPane
            Tab exerciceTab = modifierExerciceTabPane.getTabs().get(1); // L'index 1 correspond à l'onglet "Exercice"

            // Définir le contenu de l'onglet ExTab avec le contenu de l'onglet "Exercice"
            ExTab.setContent(exerciceTab.getContent());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }











    @FXML
    void supprimerExercice2(ActionEvent event) {
        Exercice ExerciceASupprimer = obtenirExerciceSelectionne();
        int indexASupprimer = listViewE.getSelectionModel().getSelectedIndex();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression d'exercice");
        alert.setContentText("Voulez-vous vraiment supprimer cet exercice ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            listViewE.getItems().remove(indexASupprimer);
            es.supprimer(ExerciceASupprimer);
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }


    ////




}
