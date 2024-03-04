package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.User;
import services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherUserController {
    @FXML
    private Button coupon;
    @FXML
    private TableColumn<User, Boolean> recommandationCol;
    @FXML
    private TableColumn<User, String> nomCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, Integer> tailleCol;
    @FXML
    private TableColumn<User, String> niveauCol;
    @FXML
    private TableColumn<User, String> roleCol;
    @FXML
    private TableColumn<User, Integer> poidsCol;
    @FXML
    private TableColumn<User, Integer> numeroCol;
    private final UserService us = new UserService();
    @FXML
    private TableView tableviewUser;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;
    @FXML
    private TableColumn specialiteCol;


    @FXML
    void initialize() {
        try {
            List<User> users = us.recuperer();
            ObservableList<User> observableList = FXCollections.observableList(users);
            tableviewUser.setItems(observableList);

            nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            numeroCol.setCellValueFactory(new PropertyValueFactory<>("numero"));
            specialiteCol.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            recommandationCol.setCellValueFactory(new PropertyValueFactory<>("recommandation"));
            poidsCol.setCellValueFactory(new PropertyValueFactory<>("poids"));
            tailleCol.setCellValueFactory(new PropertyValueFactory<>("taille"));
            niveauCol.setCellValueFactory(new PropertyValueFactory<>("niveau"));
            roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

            // Appel de la méthode pour ajouter les boutons à la table
            addButtonsToTable();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void addButtonsToTable() {
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {

                    private final Button modifierButton = new Button("Modifier");

                    {
                        modifierButton.setOnAction((ActionEvent event) -> {
                            User selectedUser = (User) getTableView().getItems().get(getIndex());
                            if (selectedUser != null)
                                naviguezVersModification();
                            else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setContentText("No user Selected");
                                alert.showAndWait();
                            }
                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(modifierButton);
                        }
                    }
                };
            }
        };

        TableColumn<User, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(cellFactory);

        tableviewUser.getColumns().add(actionColumn);
    }

    @FXML
    public void naviguezVersModification() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));
            Parent root = loader.load();
            modifierButton.getScene().setRoot(root);

            ModifierUserController modifierUserController = loader.getController();
            User selectedUser = (User) tableviewUser.getSelectionModel().getSelectedItem();

            try {
                modifierUserController.populateFields(selectedUser);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerUser(ActionEvent event) {
        User selectedService = (User) tableviewUser.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cette conversation ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    String email = selectedService.getEmail();
                    UserService us = new UserService();
                    us.supprimerUser(email);
                    tableviewUser.getItems().remove(selectedService);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    void naviguezVersCoupon(ActionEvent event) throws IOException {

        AnchorPane root = FXMLLoader.load(getClass().getResource("/coupon.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();




    }
}


