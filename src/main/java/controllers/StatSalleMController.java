package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import models.Materiel;
import models.Salle;
import services.MaterielService;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatSalleMController {
    @FXML
    private Button ButtonRetour;
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public void initialize() {
        // Initialize the chart axes
        xAxis.setLabel("Salle");
        yAxis.setLabel("Nombre de matériaux");
        // Retrieve data from the database
        SalleService salleService = new SalleService();
        MaterielService materielService = new MaterielService();
        try {
            List<Salle> salles = salleService.recuperer();
            // Create data series for the chart
            ObservableList<BarChart.Series<String, Integer>> barChartData = FXCollections.observableArrayList();
            for (Salle salle : salles) {
                // Reset sum for each salle
                int sum = 0;
                int quantites = materielService.recupererSommeQuantitesMateriels(salle.getId());



                // Create a data series for this salle
                BarChart.Series<String, Integer> dataSeries = new BarChart.Series<>();
                dataSeries.setName(salle.getNom());
                dataSeries.getData().add(new BarChart.Data<>("", quantites));

                // Add the data series to the chart
                barChartData.add(dataSeries);
            }

            // Add all data series to the chart
            barChart.getData().addAll(barChartData);
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
