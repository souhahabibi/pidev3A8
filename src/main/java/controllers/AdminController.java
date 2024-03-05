package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    ServiceProduit s = new ServiceProduit();
    tn.esprit.services.ServiceFournisseur a = new tn.esprit.services.ServiceFournisseur();
    @FXML
    private VBox fournisseurContainer;

    @FXML
    private VBox produitContainer;


    @FXML
    private Button buttonAjouter;
    @FXML
    private  Button buttonstatF;
    @FXML
    public void initialize() {
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
    }
    private void displayFournisseur() throws SQLException {

        List<tn.esprit.entites.Fournisseur> fournisseurs = a.recuperer(); // Adjust this line to match your method for fetching competitions

        for (tn.esprit.entites.Fournisseur salle : fournisseurs) {
            Pane fournisseurEntry = createFournisseurEntry(salle);
            fournisseurContainer.getChildren().add(fournisseurEntry);
        }
    }
    private void displayProduit() throws SQLException {

        List<tn.esprit.entites.Produit> produits = s.recuperer(); // Adjust this line to match your method for fetching competitions

        for (tn.esprit.entites.Produit salle : produits) {
            Pane produitEntry = createProduitEntry(salle);
            produitContainer.getChildren().add(produitEntry);
        }
    }

    @FXML
    public Pane createProduitEntry(tn.esprit.entites.Produit produit) {
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

    private void deleteProduit(tn.esprit.entites.Produit produit) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Confirmer la suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce produit ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (true) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    s.supprimer(produit.getId_produit());
                    // Nettoyer le contenu du conteneur avant de réafficher les salles
                    produitContainer.getChildren().clear();
                    initialize();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la suppression de produit : " + e.getMessage());
                }
            }
        });

    }

    private void naviguezVersMODIFYProduit(Object o, tn.esprit.entites.Produit produit) {
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
    public Pane createFournisseurEntry(tn.esprit.entites.Fournisseur fournisseur) {
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
       ImageView imageView3 = new ImageView(new Image("/flaticon/add (1).png"));
       imageView3.setFitWidth(30);
       imageView3.setFitHeight(30);
      addProduitButton.setGraphic(imageView3);

        fournisseurPane.getChildren().addAll(nomLabel, prenomLabel, numeroLabel, typeLabel, modifierButton, supprimerButton, addProduitButton);

        return fournisseurPane;
    }

    private void addProduitToFournisseur(tn.esprit.entites.Fournisseur fournisseur) {
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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void naviguezVersMODIFYFournisseur(ActionEvent event, tn.esprit.entites.Fournisseur fournisseur) {

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
    void deleteFournisseur(tn.esprit.entites.Fournisseur fournisseur){

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Confirmer la suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette salle ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    a.supprimer(fournisseur.getId_fournisseur());
                    // Nettoyer le contenu du conteneur avant de réafficher les salles
                    fournisseurContainer.getChildren().clear();
                    initialize();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la suppression de fournisseur : " + e.getMessage());
                }
            }
        });
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
    private void downloadProductPDF(Event event, tn.esprit.entites.Produit produit, int espacement) {
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
                PDImageXObject image = PDImageXObject.createFromFile("C:\\Users\\souha\\Desktop\\pidev3A8\\pidev3A8\\src\\main\\resources/logo.png", document);
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
            String outputFile = "C:\\Users\\souha\\Desktop\\pdf\\output.pdf";

            document.save(new File(outputFile));
            System.out.println("Fichier PDF du produit créé avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du fichier PDF : " + e.getMessage());
        }
    }
    @FXML
    void naviguezVersStatFournisseur(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StatFournisseur.fxml"));//charger le fichier FXML
            buttonstatF.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}