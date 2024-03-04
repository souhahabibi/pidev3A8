package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.IRegimeService;
import services.RegimeImpl;
import models.Goal;
import models.Regime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FillDietFormController implements Initializable {


    @FXML
    private TextArea desc_textArea;

    @FXML
    private TextField duration;

    @FXML
    private DatePicker endDate;

    @FXML
    private Button send_btn;

    @FXML
    private Button seeForm;

    @FXML
    private Button seeBMI;

    @FXML
    private DatePicker startDate;

    IRegimeService regime = new RegimeImpl();
    @FXML
    private ChoiceBox goal;
    private Regime pushedRegime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (Goal b : Goal.values()) {
                goal.getItems().add(b.toString());
            }
        } catch (Exception e) {
            System.out.println("error");
        }

    }


    @FXML
    void pushRegime(ActionEvent event) {
        try {
            String typeBut = (String) goal.getValue();

            if (duration.getText().isEmpty() || desc_textArea.getText().isEmpty() || ((String) goal.getValue() == null)) {
                Alert alerts = new Alert(Alert.AlertType.WARNING);
                alerts.setTitle("Warning");
                alerts.setHeaderText(null);
                alerts.setContentText("Please fill in the fields!");
                alerts.show();
            } else if (endDate.getValue().isBefore(startDate.getValue())) {
                Alert alerts = new Alert(Alert.AlertType.WARNING);
                alerts.setTitle("Warning");
                alerts.setHeaderText(null);
                alerts.setContentText("End date must be after start date");
                alerts.show();
            } else {
                int durationValue = Integer.parseInt(duration.getText());
                int clientId = 1;
                pushedRegime = new Regime(startDate.getValue(), endDate.getValue(), durationValue, desc_textArea.getText(), typeBut, clientId);
                regime.save(pushedRegime);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Regime added successfully!");
                alert.show();

                startDate.setValue(null);
                endDate.setValue(null);
                duration.setText("");
                desc_textArea.setText("");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid duration (numeric value)!");
            alert.show();
        }
    }


    public void ViewForm(ActionEvent actionEvent) {
        try {
            // Check if a regime has been pushed
            if (!isRegimePushed()) {
                // Display an error alert if no regime has been pushed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please push a regime before viewing the form!");
                alert.show();
                return;  // Exit the function if no regime has been pushed
            }

            // Load the form only if a regime has been pushed
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherRegime.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
    private boolean isRegimePushed() {
        // Check if the pushedRegime variable is not null
        return pushedRegime != null;
    }
    @FXML
    void BMI(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/BMI.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

}