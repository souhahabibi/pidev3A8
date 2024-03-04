package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Goal;
import models.Regime;
import services.IRegimeService;
import services.RegimeImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateDietFormController implements Initializable {

    private static UpdateDietFormController instance;
    public UpdateDietFormController() { instance = this; }
    public static UpdateDietFormController getInstance() { return instance; }

    @FXML
    private TextArea desc_textArea;

    @FXML
    private TextField duration;

    @FXML
    private DatePicker endDate;

    @FXML
    private ChoiceBox<String> goal;

    @FXML
    private Button seeForm;

    @FXML
    private Button send_btn;

    @FXML
    private DatePicker startDate;

    IRegimeService service = new RegimeImpl();

    public TextArea getDesc_textArea() {
        return desc_textArea;
    }

    public void setDesc_textArea(TextArea desc_textArea) {
        this.desc_textArea = desc_textArea;
    }

    public TextField getDuration() {
        return duration;
    }

    public void setDuration(TextField duration) {
        this.duration = duration;
    }

    public DatePicker getEndDate() {
        return endDate;
    }

    public void setEndDate(DatePicker endDate) {
        this.endDate = endDate;
    }

    public ChoiceBox<?> getGoal() {
        return goal;
    }

    public void setGoal(ChoiceBox<String> goal) {
        this.goal = goal;
    }

    public DatePicker getStartDate() {
        return startDate;
    }

    public void setStartDate(DatePicker startDate) {
        this.startDate = startDate;
    }

    @FXML
    void ViewForm(ActionEvent event) {

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
                Regime r = AfficherRegimeController.getInstance().RegimeInstance;
                Regime pushedRegime = new Regime(r.getId(), startDate.getValue(), endDate.getValue(), durationValue, desc_textArea.getText(), typeBut);
                service.update(pushedRegime);

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
}