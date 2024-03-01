package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.*;
import services.AbonnementService;
import services.MaterielService;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import javafx.scene.control.ListCell;
import javafx.scene.shape.Line;


public class AccueilController {

    @FXML
    private Button buttonSSupprimer;
    @FXML
    private Button buttonMModifier;
    @FXML
    private Button buttonMAjouter;

    @FXML
    private ListView<Materiel> listM;

    @FXML
    private Button buttonMSupprimer;

    @FXML
    private Button buttonSModifier;

    @FXML
    private Button buttonSAjouter;

    @FXML
    private ListView<Salle> listS;

    @FXML
    private Button buttonAModifier;

    @FXML
    private Button buttonASupprimer;
    @FXML
    private Button buttonAAjouter;
    @FXML
    private ListView<Abonnement> listA;

    private final SalleService ps = new SalleService();
        private final MaterielService psM = new MaterielService();
    private final AbonnementService psA = new AbonnementService();


@FXML

    void initialize() {
    try {
        List<Salle> salles = ps.recuperer();
        ObservableList<Salle> observableList = FXCollections.observableList(salles);
        //créer une liste observable à partir d'une liste standard de Java.
        //Une liste observable est une liste qui permet de surveiller les modifications qui y sont apportées
        listS.setItems(observableList);//associe la liste observable à un ListView=listS
        //listS.setPadding(new Insets(10, 0, 0, 0));

        //observableList.add(0, new Salle("", "", "", ""));

        listS.setCellFactory(new Callback<ListView<Salle>, ListCell<Salle>>() {
            @Override
            public ListCell<Salle> call(ListView<Salle> param) {
                return new ListCell<Salle>() {
                    private ImageView imageView1 = new ImageView();
                    private Label nomLabel1 = new Label();
                    private Label descriptionLabel1 = new Label();
                    private Label lieuLabel1= new Label();

                    @Override
                    protected void updateItem(Salle salle, boolean empty) {
                        super.updateItem(salle, empty);
                        if (empty || salle == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            // Afficher l'image de la salle
                            Image image = new Image("file:" + salle.getImage());
                            imageView1.setImage(image);
                            imageView1.setFitWidth(50); // Ajustez la taille de l'image selon vos besoins
                            imageView1.setFitHeight(50); // Ajustez la taille de l'image selon vos besoins

                            // Afficher les autres détails de la salle
                            nomLabel1.setText(salle.getNom());
                            descriptionLabel1.setText(salle.getDescription());
                            lieuLabel1.setText(salle.getLieu());
                            // Créer une disposition pour organiser les éléments de la cellule comme une table
                            HBox hbox = new HBox(imageView1, nomLabel1, descriptionLabel1, lieuLabel1);
                            hbox.setSpacing(10); // Ajustez l'espacement entre les éléments

                            setGraphic(hbox);
                            setText(null);
                        }
                    }
                };
            }
        });

    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    try {
        List<Materiel> materiels = psM.recuperer();
        ObservableList<Materiel> observableList= FXCollections.observableList(materiels);
        listM.setItems(observableList);

    //    observableList.add(0, new Materiel("", 0,0,0,0  ,""));

        listM.setCellFactory(new Callback<ListView<Materiel>, ListCell<Materiel>>() {
            @Override
            public ListCell<Materiel> call(ListView<Materiel> param) {
                return new ListCell<Materiel>() {

                    private ImageView imageView = new ImageView();
                    private Label nomLabel = new Label();
                    private Label ageLabel = new Label();
                    private Label quantiteLabel = new Label();
                    private Label prixLabel = new Label();
                    private Label salleLabel = new Label();

                    @Override
                    protected void updateItem(Materiel materiel, boolean empty) {
                        super.updateItem(materiel, empty);
                        if (empty || materiel == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            // Afficher l'image de la salle
                            Image image = new Image("file:" + materiel.getImage());
                            imageView.setImage(image);
                            imageView.setFitWidth(50); // Ajustez la taille de l'image selon vos besoins
                            imageView.setFitHeight(50); // Ajustez la taille de l'image selon vos besoins

                            // Afficher les autres détails de la salle
                            nomLabel.setText(materiel.getNom());
                            ageLabel.setText(String.valueOf(materiel.getAge()));
                            quantiteLabel.setText(String.valueOf(materiel.getQuantite()));
                            prixLabel.setText(String.valueOf(materiel.getPrix()));
                            salleLabel.setText(String.valueOf(materiel.getFK_idSalle()));
                            // Créer une disposition pour organiser les éléments de la cellule comme une table
                            HBox hbox = new HBox(imageView, nomLabel, ageLabel, quantiteLabel,prixLabel,salleLabel);
                            hbox.setSpacing(10); // Ajustez l'espacement entre les éléments

                            setGraphic(hbox);
                            setText(null);
                        }
                    }
                };
            }
        });

    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();//garantit que l'utilisateur voit l'erreur
    }
    try {
        List<Abonnement> abonnements = psA.recuperer();
        ObservableList<Abonnement> observableList= FXCollections.observableList(abonnements);
        listA.setItems(observableList);

    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    }


    @FXML
    public void isEnabled(){

    if(listS.getSelectionModel().getSelectedItem()!=null)
        //Si aucun élément n'est sélectionné, getSelectedItem() renverra null.
        buttonSSupprimer.setDisable(false);//active le bouton de suppression
    if(listM.getSelectionModel().getSelectedItem()!=null)
            buttonMSupprimer.setDisable(false);
        if(listA.getSelectionModel().getSelectedItem()!=null)
            buttonASupprimer.setDisable(false);

    }
    @FXML
     void deleteSalle(){
        if(listS.getSelectionModel().getSelectedItem()!=null){
            Salle salle=listS.getSelectionModel().getSelectedItem();
            try {
                ps.supprimer(salle.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        initialize();

    }

    @FXML
    void deleteMateriel(){
        if(listM.getSelectionModel().getSelectedItem()!=null){
            Materiel materiel=listM.getSelectionModel().getSelectedItem();
            try {
                psM.supprimer(materiel.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        initialize();

    }
    @FXML
    void deleteAbonnement(){
        if(listA.getSelectionModel().getSelectedItem()!=null){
            Abonnement abonnement=listA.getSelectionModel().getSelectedItem();
            try {
                psA.supprimer(abonnement.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        initialize();

    }
    @FXML // indique que la méthode est associée à un élément graphique défini dans un fichier FXML.
    void naviguezVersAjouterSalle(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterSalle.fxml"));//charger le fichier FXML
            listS.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersAjouterMateriel(ActionEvent event) {
        ObservableList<Salle> observableList = null;
        try {
            List<Salle> Salles = ps.recuperer();
            observableList = FXCollections.observableList(Salles);
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMateriel.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterMaterielController controller = loader.getController();

                // If an item is selected, pass it to the controller of the next scene
                //controller.setMateriel(observableList);
            // Finally, set the scene's root to switch to the new view
            buttonMSupprimer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void naviguezVersAjouterAbonnement(ActionEvent event) {
        ObservableList<Salle> observableList = null;
        try {
            List<Salle> Salles = ps.recuperer();
            observableList = FXCollections.observableList(Salles);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            AjouterAbonnementController controller = loader.getController();

            // If an item is selected, pass it to the controller of the next scene
        //    controller.setAbonnement(observableList);
            // Finally, set the scene's root to switch to the new view
            buttonASupprimer.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void naviguezVersMODIFY(ActionEvent event) {

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSalle.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierSalleController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            Salle selectedSalle = listS.getSelectionModel().getSelectedItem();

            // Check if an item is actually selected
            if (selectedSalle != null) {
                // If an item is selected, pass it to the controller of the next scene
                controller.setSalle(selectedSalle);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            buttonSModifier.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
@FXML
    void naviguezVersMODIFYMateriel(ActionEvent event) {
    ObservableList<Salle> observableList = null;
    try {
        List<Salle> salles = ps.recuperer();
        observableList = FXCollections.observableList(salles);
    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMateriel.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierMaterielController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            Materiel selectedMateriel = listM.getSelectionModel().getSelectedItem();

            // Check if an item is actually selected
            if (selectedMateriel != null) {
                // If an item is selected, pass it to the controller of the next scene
            //    controller.setMateriel(selectedMateriel,observableList);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            buttonMModifier.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersMODIFYAbonnement(ActionEvent event) {
        ObservableList<Salle> observableList = null;
        try {
            List<Salle> salles = ps.recuperer();
            observableList = FXCollections.observableList(salles);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        try {
            // Correctly create an FXMLLoader instance pointing to your FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAbonnement.fxml"));

            // Load the FXML and get the root node in one step
            Parent root = loader.load();

            // Now that the FXML is loaded, get the controller
            ModifierAbonnementController controller = loader.getController();

            // Here, you retrieve the selected item from your ListView
            Abonnement selectedAbonnement= listA.getSelectionModel().getSelectedItem();

            // Check if an item is actually selected
            if (selectedAbonnement != null) {
                // If an item is selected, pass it to the controller of the next scene
              //  controller.setAbonnement(selectedAbonnement,observableList);
            } else {
                // Handle the case where no item is selected (optional)
                System.out.println("No item selected");
                return; // Optionally return to avoid changing scenes when no item is selected
            }

            // Finally, set the scene's root to switch to the new view
            buttonAModifier.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
