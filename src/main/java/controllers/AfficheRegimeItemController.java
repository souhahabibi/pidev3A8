package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.Regime;
import services.IRegimeService;
import services.RegimeImpl;

import java.io.IOException;
import java.time.LocalDate;

public class AfficheRegimeItemController {
    @FXML
    private Button btn;

    @FXML
    private Button btndelete;

    @FXML
    private Label description;

    @FXML
    private Label duration;

    @FXML
    private Label endDate;

    @FXML
    private Label goal;

    @FXML
    private HBox itemC;

    public Label getDescription() {
        return description;
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public Label getDuration() {
        return duration;
    }

    public void setDuration(Label duration) {
        this.duration = duration;
    }

    public Label getEndDate() {
        return endDate;
    }

    public void setEndDate(Label endDate) {
        this.endDate = endDate;
    }

    public Label getGoal() {
        return goal;
    }

    public void setGoal(Label goal) {
        this.goal = goal;
    }

    public HBox getItemC() {
        return itemC;
    }

    public void setItemC(HBox itemC) {
        this.itemC = itemC;
    }

    public Label getStartDate() {
        return startDate;
    }

    public void setStartDate(Label startDate) {
        this.startDate = startDate;
    }

    @FXML
    private Label startDate;



    IRegimeService rs =new RegimeImpl();

    private static AfficheRegimeItemController instance;
    public AfficheRegimeItemController() { instance = this; }
    public static AfficheRegimeItemController getInstance() { return instance; }

    public void afficherRegime(){
        AfficherRegimeController.getInstance().getPnItems().getChildren().clear();
        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource("/views/Item.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AfficherRegimeController.getInstance().getPnlOverview().getChildren().setAll(node);

     /*   PlanningController.getInstance().getHbLabels().getChildren().clear();
        PlanningController.getInstance().getSearch().clear();
        PlanningController.getInstance().getTitle().setText("Event Details");
        PlanningController.getInstance().getScrollPane().setContent(node);
        PlanningController.getInstance().getScrollPane().setFitToWidth(true);
*/
    }

    public void afficherEvent(ActionEvent actionEvent) {
        AfficherRegimeController.getInstance().getPnItems().getChildren().clear();
        Node node = null;
        try {
            node = FXMLLoader.load(getClass().getResource("/UpdateDietForm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateDietFormController.getInstance().getDesc_textArea().setText(description.getText());
        UpdateDietFormController.getInstance().getDuration().setText(duration.getText());
        UpdateDietFormController.getInstance().getStartDate().setValue(LocalDate.parse(startDate.getText()));
        UpdateDietFormController.getInstance().getEndDate().setValue(LocalDate.parse(endDate.getText()));
        //UpdateDietFormController.getInstance().getGoal().setValue(goal.getText());

        AfficherRegimeController.getInstance().getPnlOverview().getChildren().setAll(node);
    }


    @FXML
    void deleteRegime(ActionEvent event) {
        int i;
        Regime r = AfficherRegimeController.getInstance().RegimeInstance;

        for (i=0; i<= AfficherRegimeController.getInstance().regimeService.getAll().size();i++){
            if (r.getId() == AfficherRegimeController.getInstance().regimeService.getAll().get(i).getId()){
                AfficherRegimeController.getInstance().regimeService.deleteById(r.getId());
                AfficherRegimeController.getInstance().getPnItems().getChildren().remove(i);
            }
        }



    }
}