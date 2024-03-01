package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StatistiqueCompetitionController {
    private Connection connection;
    @FXML
    private BarChart<String, Number> dayofweekBarC;
    @FXML
    private BarChart<String, Number> monthlyBarC;
    @FXML
    private StackedAreaChart<String, Number> monthlyReservationsChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    public void initialize()
    {
        fillReservationsBarChart();
        fillReservationsBarChartMonthly();
        fillMonthlyReservationsChart();
    }
    @FXML
    private void fillReservationsBarChart() {
        Connection connection = MyDatabase.getInstance().getConnection();
        String sql = "SELECT DAYOFWEEK(c.date) AS dayOfWeek, " +
                "COUNT(r.id) / COUNT(DISTINCT c.id) AS avgReservationsPerCompetition " +
                "FROM competition c " +
                "JOIN reservation r ON c.id = r.fk_competition_id " +
                "GROUP BY DAYOFWEEK(c.date)";

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Average Reservations");

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int dayOfWeek = resultSet.getInt("dayOfWeek");
                double avgReservationsPerCompetition = resultSet.getDouble("avgReservationsPerCompetition");

                String dayOfWeekString = mapDayOfWeek(dayOfWeek);
                series.getData().add(new XYChart.Data<>(dayOfWeekString, avgReservationsPerCompetition));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dayofweekBarC.getData().add(series);
    }

    private String mapDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1: return "Sunday";
            case 2: return "Monday";
            case 3: return "Tuesday";
            case 4: return "Wednesday";
            case 5: return "Thursday";
            case 6: return "Friday";
            case 7: return "Saturday";
            default: return "Unknown";
        }
    }
    @FXML
    private void fillReservationsBarChartMonthly() {
        Connection connection = MyDatabase.getInstance().getConnection();
        String sql = "SELECT MONTH(c.date) AS month, " +
                "COUNT(r.id) / COUNT(DISTINCT c.id) AS avgReservationsPerCompetition " +
                "FROM competition c " +
                "JOIN reservation r ON c.id = r.fk_competition_id " +
                "GROUP BY MONTH(c.date)";

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Average Reservations Per Month");

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int month = resultSet.getInt("month");
                double avgReservationsPerCompetition = resultSet.getDouble("avgReservationsPerCompetition");

                String monthString = mapMonth(month);
                series.getData().add(new XYChart.Data<>(monthString, avgReservationsPerCompetition));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        monthlyBarC.getData().add(series);
    }
    private String mapMonth(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        if (month >= 1 && month <= 12) {
            return months[month - 1];
        } else {
            return "Unknown";
        }

    }
    private String mapMonths(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months[month - 1];
    }
    @FXML
    private void fillMonthlyReservationsChart() {
        Connection connection = MyDatabase.getInstance().getConnection();
        // SQL to select organizer name, month of competition, and average reservations
        String sql = "SELECT o.nom AS organizerName, " +
                "MONTH(c.date) AS month, " +
                "COALESCE(AVG(r.count), 0) AS avgReservations " +
                "FROM organisateur o " +
                "JOIN competition c ON o.id = c.fk_organisateur_id " +
                "LEFT JOIN (SELECT fk_competition_id, COUNT(*) AS count FROM reservation GROUP BY fk_competition_id) r ON c.id = r.fk_competition_id " +
                "GROUP BY o.nom, MONTH(c.date) " +
                "ORDER BY o.nom, MONTH(c.date)";

        Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String organizerName = resultSet.getString("organizerName");
                int month = resultSet.getInt("month");
                double avgReservations = resultSet.getDouble("avgReservations");
                String monthString = mapMonth(month);

                // If series for this organizer does not exist, create it and initialize all months to 0
                XYChart.Series<String, Number> series = seriesMap.computeIfAbsent(organizerName, k -> {
                    XYChart.Series<String, Number> newSeries = new XYChart.Series<>();
                    newSeries.setName(organizerName);
                    for (int m = 1; m <= 12; m++) {
                        newSeries.getData().add(new XYChart.Data<>(mapMonth(m), 0));
                    }
                    return newSeries;
                });

                // Update the specific month's data with actual average reservations
                series.getData().set(month - 1, new XYChart.Data<>(monthString, avgReservations));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        monthlyReservationsChart.getData().clear(); // Clear existing data before adding new data
        monthlyReservationsChart.getData().addAll(seriesMap.values());

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(findUpperBoundForChart(seriesMap)); // Dynamically find upper bound
    }

    private double findUpperBoundForChart(Map<String, XYChart.Series<String, Number>> seriesMap) {
        double max = 0;
        for (XYChart.Series<String, Number> series : seriesMap.values()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getYValue().doubleValue() > max) {
                    max = data.getYValue().doubleValue();
                }
            }
        }
        // Add a buffer to the max value for better chart visualization
        return max + 50 - (max % 10);
    }
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AdminCompetition.fxml"));
            monthlyBarC.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
