package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import models.Materiel;
import models.Salle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import services.AbonnementService;
import services.MaterielService;
import services.SalleService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

        Text salle = new Text(249, 170, "Salle : " + s.selectSalleNameById(materiel.getFK_idSalle().getId()));
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

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Confirmer la suppression");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce matériel ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression, procéder à la suppression
                try {
                    m.supprimer(materiel.getId());
                    // Nettoyer le contenu du conteneur avant de réafficher les salles
                    materielsContainer.getChildren().clear();
                    setMateriel(x);
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la suppression du matériel : " + e.getMessage());
                }
            }
        });
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

    private void drawCell(PDPageContentStream contentStream, float x, float y, float width, float height, String text, PDImageXObject image) throws IOException {
        contentStream.setNonStrokingColor(0, 0, 0);
        contentStream.addRect(x, y, width, height);
        contentStream.stroke();
        // Draw the image if it's not null
        if (image != null) {
            contentStream.drawImage(image, x, y, width, height);
        }
        // Draw the text if it's not null
        if (text != null && !text.isEmpty()) {
            contentStream.beginText();
            // Center-align text horizontally and vertically
            contentStream.newLineAtOffset(x + (width - getTextWidth(text)) / 2, y + height / 2);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    private float getTextWidth(String text) throws IOException {
        return PDType1Font.TIMES_ROMAN.getStringWidth(text) / 1000 * 10; // Font size is 10
    }

    public void downloadProductPDF() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Add the logo at the top of the page
                PDImageXObject logo = PDImageXObject.createFromFile("C:\\Workshop-JDBC-JavaFX-master__2\\src\\main\\resources\\flaticon\\418597714_683613163937422_3465083466212055398_n.png", document);
                contentStream.drawImage(logo, 50, page.getMediaBox().getHeight() - 100, 100, 100);
                // Add the title
                String title = "Liste des Matériels";
                float fontSize = 24;
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * fontSize;
                float titleHeight = PDType1Font.HELVETICA_BOLD.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
                float xTitle = (page.getMediaBox().getWidth() - titleWidth) / 2;
                float yTitle = page.getMediaBox().getHeight() - 50 - titleHeight; // Place the title 50 points from the top
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                contentStream.newLineAtOffset(xTitle, yTitle);
                contentStream.showText(title);
                contentStream.endText();
                // Define the positions for the table
                float margin = 70;
                float yPosition = page.getMediaBox().getHeight() - 200; // Position to start the table
                float rowHeight = 100;
                float cellMargin = 5;
                float yStart = yPosition;
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
                drawCell(contentStream, margin, yPosition, 100, rowHeight, "Image",null);
                drawCell(contentStream, margin + 100, yPosition, 100, rowHeight, "Nom",null);
                drawCell(contentStream, margin + 200, yPosition, 100, rowHeight, "Quantité",null);
                drawCell(contentStream, margin + 300, yPosition, 100, rowHeight, "Coût",null);
                yPosition -= rowHeight;
                // Create the table to display materials' information
                List<Materiel> materiels = new MaterielService().select(x);
                for (Materiel materiel : materiels) {

                    // Draw table headers


                    PDImageXObject image = PDImageXObject.createFromFile(materiel.getImage(), document);
                    drawCell(contentStream, margin, yPosition, 100, rowHeight, "", image); // Placeholder for image
                    drawCell(contentStream, margin + 100, yPosition, 100, rowHeight, materiel.getNom(),null);
                    drawCell(contentStream, margin + 200, yPosition, 100, rowHeight, String.valueOf(materiel.getQuantite()),null);
                    drawCell(contentStream, margin + 300, yPosition, 100, rowHeight, String.valueOf(materiel.getPrix()),null);
                    yPosition -= rowHeight;
                }
            }

            // Specify the full path for the output file
            String outputFile = "C:\\Users\\Cyrinechalghoumi\\Downloads\\output7.pdf";
            document.save(new File(outputFile));
            System.out.println("PDF file created successfully!");
        } catch (IOException | SQLException e) {
            System.err.println("Error creating PDF file: " + e.getMessage());
        }
    }

    @FXML
    void naviguezVersClient(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));//charger le fichier FXML
            materielsContainer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
