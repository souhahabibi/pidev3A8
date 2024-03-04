package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.coupon;
import services.couponService;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterCouponController {
    @javafx.fxml.FXML
    private VBox listeCoupons;
    @javafx.fxml.FXML
    private TextField codeCouponTF;
    @javafx.fxml.FXML
    private TextField NomSocieteTF;
    @javafx.fxml.FXML
    private Button btnModifier;
    @javafx.fxml.FXML
    private Button btnAjouter1;
    @javafx.fxml.FXML
    private TextField valeurCouponTF;
    @javafx.fxml.FXML
    private TextField dateExpirationTF;
    @javafx.fxml.FXML
    private Button btnAjouter;
    @javafx.fxml.FXML
    private Button btnSupprimer;
    private final couponService cs = new couponService();
    @javafx.fxml.FXML
    public void ajouterCoupon(ActionEvent actionEvent) {
        try {
            cs.ajouter(new coupon(NomSocieteTF.getText(),Integer.parseInt(codeCouponTF.getText()), Integer.parseInt(valeurCouponTF.getText()), dateExpirationTF.getText()));
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void naviguerVersAfficherCoupon(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherCoupon.fxml"));
            btnAjouter1.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void naviguerVersModifierCoupon(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/modifierCoupon.fxml"));
            btnAjouter1.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void naviguerVersSupprimerCoupon(ActionEvent actionEvent) {
    }

    public void SupprimerCoupon(ActionEvent actionEvent) {
    }
        private void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }
}
