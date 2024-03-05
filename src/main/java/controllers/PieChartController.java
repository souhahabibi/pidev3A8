package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Salle;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


import javafx.fxml.FXML;

import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import models.Materiel;
import services.MaterielService;
import services.SalleService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PieChartController {
    int x;
    @FXML
    private PieChart pieChart;
    private final SalleService ss = new SalleService();

    @FXML
    private Text textStat;
    @FXML
    private Text textStat1;
    @FXML
    private Button ButtonRetour;
    @FXML
    public void initialize() {

    }
    @FXML
    public void chart() {

        SalleService salle = new SalleService();
        MaterielService materielService = new MaterielService();
        try {
            // Récupérer les matériels de la salle actuelle
            List<Materiel> materiels = materielService.select(ss.getsalle(x).getId());

            // Créer une liste de segments de diagramme circulaire
            ObservableList<Data> pieChartData = FXCollections.observableArrayList();
            // Calculer le total des prix pour obtenir les pourcentages
            double totalPrix = 0;
            for (Materiel materiel : materiels) {
                totalPrix += (materiel.getPrix()/materiel.getQuantite());
            }
            // Créer une instance de DescriptiveStatistics pour calculer les statistiques descriptives des prix
            DescriptiveStatistics stats = new DescriptiveStatistics();

            // Ajouter chaque matériel avec son prix au diagramme circulaire et au DescriptiveStatistics
            for (Materiel materiel : materiels) {
                double prixUnitaire = materiel.getPrix() / materiel.getQuantite();
                double pourcentage = (prixUnitaire / totalPrix) * 100;
                String detail = String.format("%.2f", pourcentage) + "% ( " + prixUnitaire + " dt)"; // Format du détail

                Data data = new Data(null, prixUnitaire);
                data.setName(materiel.getNom() + " " + detail); // Ajouter le détail
                pieChartData.add(data);

                // Ajouter le prix unitaire au DescriptiveStatistics
                stats.addValue(prixUnitaire);
            }



            // Définir les données du diagramme circulaire
            pieChart.setData(pieChartData);

            // Ajouter un titre au PieChart
            pieChart.setTitle("Répartition des prix par matériel");

            // Calculer les statistiques descriptives
            double mean = stats.getMean();
            double stdDev = stats.getStandardDeviation();
            System.out.println("Moyenne des prix: " + mean);
            System.out.println("Écart type des prix: " + stdDev);
            textStat.setText("Moyenne des prix: " + mean+"       ");

            textStat1.setText("Écart type des prix: " + stdDev);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSalleName(int id) {
        x=id;
        chart();
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

