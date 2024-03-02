package tn.esprit.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Regime;
import tn.esprit.services.IRegimeService;
import tn.esprit.services.Impl.RegimeImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AfficherRegimeController implements Initializable {

    @FXML
    private AnchorPane bp;

    @FXML
    private HBox hbLabels;

    @FXML
    private VBox pnItems;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private ScrollPane scrollPane;

    private static AfficherRegimeController instance;

    public AfficherRegimeController() {
        instance = this;
    }

    public static AfficherRegimeController getInstance() {
        return instance;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public VBox getPnItems() {
        return pnItems;
    }

    public void setPnItems(VBox pnItems) {
        this.pnItems = pnItems;
    }

    public HBox getHbLabels() {
        return hbLabels;
    }

    public Pane getPnlOverview() {
        return pnlOverview;
    }

    public void setPnlOverview(Pane pnlOverview) {
        this.pnlOverview = pnlOverview;
    }

    public AnchorPane getBp() {
        return bp;
    }

    public void setHbLabels(HBox hbLabels) {
        this.hbLabels = hbLabels;
    }
    IRegimeService regimeService= new RegimeImpl();

    Regime RegimeInstance;

    public void loadAndGet(Regime regime) {
        RegimeInstance = regime;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheRegimeItem.fxml"));
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AfficheRegimeItemController.getInstance().getDescription().setText((String.valueOf(regime.getDescription())));
        AfficheRegimeItemController.getInstance().getDuration().setText(String.valueOf(regime.getDuration()));
        AfficheRegimeItemController.getInstance().getGoal().setText(regime.getGoal().toString());
        AfficheRegimeItemController.getInstance().getStartDate().setText(regime.getStartDate().toString());
        AfficheRegimeItemController.getInstance().getEndDate().setText(regime.getEndDate().toString());
        pnItems.getChildren().add(root);
    }










    public void modifier(ActionEvent actionEvent) {
    }

    public void supprimer() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Regime r : regimeService.getAll()) {
            loadAndGet(r);
        }
    }
}
