package controllers;
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
import javafx.util.Callback;
import javafx.util.StringConverter;
import services.ServiceProduit;
import tn.esprit.entites.Fournisseur;
import tn.esprit.entites.Produit;
import tn.esprit.services.ServiceFournisseur;
import javafx.collections.ObservableList;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;



public class AfficherFournisseurController extends ListCell<tn.esprit.entites.Produit> {
    private final ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
    private final ServiceProduit serviceProduit = new ServiceProduit();

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
    //@FXML
   // private TableColumn<tn.esprit.entites.Produit, String> imageCol;

    private final ImageView imageView = new ImageView();
    private final double IMAGE_SIZE = 100; // Taille de l'image

    public void ImageListCell() {
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);
    }
    //initialise les listes de fournisseurs et de produits dans les ListView correspondantes lors du chargement de la fenêtre

    @FXML
    void initialize() {
        try {
            // Récupérer les fournisseurs
            List<Fournisseur> fournisseurs = serviceFournisseur.recuperer();
            if (!fournisseurs.isEmpty()) {
                ObservableList<Fournisseur> observableList = FXCollections.observableList(fournisseurs);
                listView.setItems(observableList);
                listView.setCellFactory(param -> new ListCell<Fournisseur>() {
                    @Override
                    protected void updateItem(Fournisseur fournisseur, boolean empty) {
                        super.updateItem(fournisseur, empty);
                        if (empty || fournisseur == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Créer un VBox pour contenir les informations du fournisseur
                            VBox vBox = new VBox();
                            // Créer des labels pour afficher les champs du fournisseur
                            Label nomLabel = new Label("Nom: ");
                            Label prenomLabel = new Label("Prenom: ");
                            Label numLabel = new Label("Numero: ");
                            Label typeLabel = new Label("Type: ");

                            // Appliquer le style en gras aux labels
                            nomLabel.setStyle("-fx-font-weight: bold;");
                            prenomLabel.setStyle("-fx-font-weight: bold;");
                            numLabel.setStyle("-fx-font-weight: bold;");
                            typeLabel.setStyle("-fx-font-weight: bold;");

                            // Créer des labels pour afficher les données du fournisseur
                            Label nomDataLabel = new Label(fournisseur.getNom());
                            Label prenomDataLabel = new Label(fournisseur.getPrenom());
                            Label numDataLabel = new Label(String.valueOf(fournisseur.getNumero()));
                            Label typeDataLabel = new Label(fournisseur.getType());

                            // Créer un HBox pour chaque paire label-donnée
                            HBox nomBox = new HBox(nomLabel, nomDataLabel);
                            HBox prenomBox = new HBox(prenomLabel, prenomDataLabel);
                            HBox numBox = new HBox(numLabel, numDataLabel);
                            HBox typeBox = new HBox(typeLabel, typeDataLabel);

                            // Ajouter les HBox au VBox
                            vBox.getChildren().addAll(nomBox, prenomBox, numBox, typeBox);

// Définir le style de fond pour chaque VBox
                           // vBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #cccccc, #999999);");

// Définir un remplissage autour de chaque cellule pour créer un espace entre les fournisseurs
                            setPadding(new Insets(10)); // Vous pouvez ajuster la valeur selon vos besoins

                            // Définir le contenu de la cellule comme le VBox
                            setGraphic(vBox);

                            // Définir la bordure inférieure rouge
                            // Créer une bordure avec la couleur définie
                            BorderStroke borderStroke = new BorderStroke(
                                    new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                                            new Stop(0, Color.web("#891b1b")),
                                            new Stop(1, Color.web("#a7473e"))),
                                    BorderStrokeStyle.SOLID, null, new BorderWidths(0, 0, 1, 0));

// Appliquer la nouvelle bordure à VBox
                            vBox.setBorder(new Border(borderStroke));
                        }
                    }
                });
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Erreur lors de la récupération des fournisseurs : " + e.getMessage());
        }


        try {
            // Récupérer les produits
            List<Produit> produits = serviceProduit.recuperer();
            if (!produits.isEmpty()) {
                ObservableList<Produit> observableList = FXCollections.observableList(produits);
                listView0.setItems(observableList);
                listView0.setCellFactory(param -> new ListCell<Produit>() {
                    @Override
                    protected void updateItem(Produit produit, boolean empty) {
                        super.updateItem(produit, empty);
                        if (empty || produit == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Créer un HBox pour contenir l'image et les champs du produit
                            HBox hBox = new HBox();

                            // Créer une ImageView pour afficher l'image
                            ImageView imageView = new ImageView(new Image(produit.getImage()));
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(150);

                            // Créer un VBox pour les champs du produit
                            VBox vBox = new VBox();
                            vBox.setSpacing(5); // Espacement entre les champs
                            // Ajouter un espace flexible à droite pour aligner l'image avec le dernier champ
                            HBox.setHgrow(vBox, Priority.ALWAYS);

                            // Créer un label pour chaque champ du produit
                            Label nomFieldLabel = new Label("Nom: ");
                            Label nomValueLabel = new Label(produit.getNom());
                            Label quantiteFieldLabel = new Label("Quantité: ");
                            Label quantiteValueLabel = new Label(String.valueOf(produit.getQuantite()));
                            Label coutFieldLabel = new Label("Coût: ");
                            Label coutValueLabel = new Label(String.valueOf(produit.getCout()));
                            Label dateExpirationFieldLabel = new Label("Date d'expiration: ");
                            Label dateExpirationValueLabel = new Label(String.valueOf(produit.getDate_expiration()));
                            Label idFournisseurFieldLabel = new Label(" Fournisseur: ");

                            // Récupérer le nom du fournisseur à partir de son ID
                            String nomFournisseur = null;
                            try {
                                nomFournisseur = serviceFournisseur.recupererNomFournisseurParId(produit.getId_fournisseur());
                            } catch (SQLException ex) {
                                ex.printStackTrace(); // Gérer l'erreur de récupération du nom du fournisseur
                            }
                            // Créer un label pour afficher le nom du fournisseur
                            Label idFournisseurValueLabel = new Label(nomFournisseur != null ? nomFournisseur : "N/A");

                            Label descriptionFieldLabel = new Label("Description: ");
                            Label descriptionValueLabel = new Label(produit.getDescription());
                            // Appliquer le style en gras aux labels des champs
                            nomFieldLabel.setStyle("-fx-font-weight: bold;");
                            quantiteFieldLabel.setStyle("-fx-font-weight: bold;");
                            coutFieldLabel.setStyle("-fx-font-weight: bold;");
                            dateExpirationFieldLabel.setStyle("-fx-font-weight: bold;");
                            idFournisseurFieldLabel.setStyle("-fx-font-weight: bold;");
                            descriptionFieldLabel.setStyle("-fx-font-weight: bold;");

                            // Ajouter les labels au VBox
                            vBox.getChildren().addAll(
                                    new HBox(nomFieldLabel, nomValueLabel),
                                    new HBox(quantiteFieldLabel, quantiteValueLabel),
                                    new HBox(coutFieldLabel, coutValueLabel),
                                    new HBox(dateExpirationFieldLabel, dateExpirationValueLabel),
                                    new HBox(idFournisseurFieldLabel, idFournisseurValueLabel),
                                    new HBox(descriptionFieldLabel, descriptionValueLabel)
                            );

                            // Ajouter la photo et les champs au HBox
                            hBox.getChildren().addAll(imageView, vBox);

                            // Ajouter un espacement entre l'image et les champs
                            hBox.setSpacing(10);

                            // Créer une bordure avec la couleur définie
                            BorderStroke borderStroke = new BorderStroke(
                                    new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                                            new Stop(0, Color.web("#891b1b")),
                                            new Stop(1, Color.web("#a7473e"))),
                                    BorderStrokeStyle.SOLID, null, new BorderWidths(0, 0, 1, 0));

                            // Appliquer la nouvelle bordure à VBox
                            vBox.setBorder(new Border(borderStroke));

                            // Définir le contenu de la cellule comme le HBox
                            setGraphic(hBox);
                        }
                    }
                });
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Erreur lors de la récupération des produits : " + e.getMessage());
        }
    }


    @FXML
    void isEnabled(Event event) {
        if (listView != null && listView.getSelectionModel().getSelectedItem() != null) {
            buttonF_MODIFY.setDisable(false);
            buttonF_DELETE.setDisable(false);
            buttonF_PRODUITS.setDisable(false);
        }
        if (listView0 != null && listView0.getSelectionModel().getSelectedItem() != null) {
            buttonP_MODIFY.setDisable(false);
            buttonP_DELETE.setDisable(false);
        }
    }

    @FXML
    public void supprimerFournisseur(Fournisseur fournisseur) {
        try {
            if (listView.getSelectionModel().getSelectedItem() != null) {
                Fournisseur newValue = listView.getSelectionModel().getSelectedItem();
                serviceFournisseur.supprimer(newValue.getId_fournisseur());
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
        initialize();
        buttonF_DELETE.setDisable(true);
        buttonF_MODIFY.setDisable(true);
        buttonF_PRODUITS.setDisable(true);
    }

    @FXML
    public void ajouterFournisseur(Fournisseur fournisseur) {
        try {
            // Ajouter le fournisseur à la liste observable
            listView.getItems().add(fournisseur);
            // Appeler la méthode d'ajout dans le service pour l'ajouter à la base de données
            serviceFournisseur.ajouter(fournisseur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void modifierFournisseur(Fournisseur fournisseur) {
        try {
            serviceFournisseur.modifier(fournisseur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteFournisseur() {
        Fournisseur fournisseur = listView.getSelectionModel().getSelectedItem();
        if (fournisseur != null) {
            try {
                serviceFournisseur.supprimer(fournisseur.getId_fournisseur());
                // Supprimer le fournisseur de la liste observable
                listView.getItems().remove(fournisseur);
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    // Gérer la violation de contrainte d'intégrité ici
                    String message = "Supprimer d'abord tous les produits associés à ce fournisseur.";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Violation de contrainte d'intégrité");
                    alert.setContentText(message);
                    alert.showAndWait();
                } else {
                    // Gérer les autres erreurs SQL ici
                    throw new RuntimeException(e);
                }
            }
        }
        // Réinitialiser la sélection
        listView.getSelectionModel().clearSelection();
    }


    @FXML
    void deleteProduit() {
        try {
            if (listView0.getSelectionModel().getSelectedItem() != null) {
                tn.esprit.entites.Produit newValue = listView0.getSelectionModel().getSelectedItem();
                serviceProduit.supprimer(newValue.getId_produit());
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
        initialize();
        buttonP_DELETE.setDisable(true);
        buttonP_MODIFY.setDisable(true);
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
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterFournisseurs.fxml"));
            buttonF_ADD.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void naviguezVersADDProduit(ActionEvent event) {
        // Récupérer le fournisseur sélectionné depuis votre interface utilisateur
        Fournisseur selectedFournisseur = listView.getSelectionModel().getSelectedItem();

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduits.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterProduitController controller = loader.getController();

            // Pass the selected fournisseur to the controller of the next scene
            controller.setFournisseur(selectedFournisseur);

            // Finally, set the scene's root to switch to the new view
            buttonF_PRODUITS.getScene().setRoot(root); // Assuming buttonF_PRODUITS is the "ADD_PRODUIT" button
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYFOURNISSEUR(ActionEvent event) {

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierFournisseurs.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierFournisseurController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            Fournisseur selectedFournisseur = listView.getSelectionModel().getSelectedItem();

            // Check if an item is actually selected
            if (selectedFournisseur != null) {
                // If an item is selected, pass it to the controller of the next scene
                controller.setFournisseur(selectedFournisseur);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            buttonF_MODIFY.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYPRODUIT(ActionEvent event) throws SQLException {

        ObservableList<Fournisseur> observableList = null;
        try {
            List<Fournisseur> fournisseurs = serviceFournisseur.recuperer();
            observableList = FXCollections.observableList(fournisseurs);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierProduits.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierProduitController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            tn.esprit.entites.Produit selectedProduit = listView0.getSelectionModel().getSelectedItem();
            // Check if an item is actually selected
            if (selectedProduit != null) {
                // If an item is selected, pass it to the controller of the next scene
                controller.setProduit(selectedProduit,observableList);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            buttonP_MODIFY.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Acceuil.fxml"));
            buttonReturn.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}