package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import services.ServiceProduit;
import tn.esprit.services.ServiceFournisseur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StatFournisseurController {
    @FXML
    private Button ButtonRetour;
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    @FXML
    private PieChart pieChart;

    public void initialize() {
        pieChart.setTitle("Répartition des produits par fournisseur");

        tn.esprit.services.ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
        ServiceProduit produitservice = new ServiceProduit();
        try {
            ServiceFournisseur serviceFournisseurs = new ServiceFournisseur();
            List<tn.esprit.entites.Fournisseur> fournisseurList = serviceFournisseurs.recuperer();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (tn.esprit.entites.Fournisseur fournisseur : fournisseurList) {
                int quantites = produitservice.recupererSommeQuantitesMateriels(fournisseur.getId_fournisseur());
                pieChartData.add(new PieChart.Data(fournisseur.getNom(), quantites));
            }

            pieChart.setData(pieChartData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void naviguezVersRetour(ActionEvent event) {//Event=représente l'événement déclenché avec l'élément graphique associé.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));//charger le fichier FXML
            ButtonRetour.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }
}
