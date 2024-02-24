package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.esprit.services.IRegimeService;
import tn.esprit.services.Impl.RegimeImpl;
import tn.esprit.entities.Goal;
import tn.esprit.entities.Regime;

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
    private DatePicker startDate;

    IRegimeService regime = new RegimeImpl();
    @FXML
    private ChoiceBox goal;

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
                Regime r = new Regime(startDate.getValue(), endDate.getValue(), durationValue, desc_textArea.getText(), typeBut, clientId);
                regime.save(r);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Regime added successfully!");
                alert.show();

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



}
