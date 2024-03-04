package controllers;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import services.UserService;
import utils.MyDatabase;

public class LoginController implements Initializable {

    Connection cnx = MyDatabase.getInstance().getConnection();
    ResultSet ResultSet = null;
    @FXML
    private TextField tfemail;
    @FXML
    private PasswordField tfpassword;
    @FXML
    private Button btnlogin;
    private UserService userService;
    @FXML
    private Button register;
    @FXML
    private Button forgetpass;
    @FXML
    private CheckBox rememberme;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userService = new UserService();

    }

    @FXML
    private void btnlogin(ActionEvent event) throws IOException {
        UserService co = new UserService();
        String password = DigestUtils.md5Hex(tfpassword.getText());
        if (co.getUserBy(tfemail.getText(), password)) {
            System.out.println(co.getById().getRole());
            System.out.println(co.getById().isIs_verified());
            if (co.getById().isIs_verified() == true)
            {
                    if (co.getById().getRole().contains("Admin")) {
                        System.out.println("admin");
                        AnchorPane root = FXMLLoader.load(getClass().getResource("/Admin.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setMaximized(true);
                        stage.show();
                    } else if (co.getById().getRole().contains("Client")){
                        AnchorPane root = FXMLLoader.load(getClass().getResource("/Client.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setMaximized(true);
                        stage.show();
                        System.out.println("client");
                    }
                    else {
                        AnchorPane root = FXMLLoader.load(getClass().getResource("/Profile.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setMaximized(true);
                        stage.show();
                        System.out.println("Coach");
                    }

            }else{
                AnchorPane root = FXMLLoader.load(getClass().getResource("/verifaccount.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setWidth(1050);
                stage.setHeight(576);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
                System.out.println("verifie your account");
            }
        } else {
            aleart();
        }

    }

    private void btnsignup(KeyEvent event) {
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/UserAdd.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setWidth(580);
        stage.setHeight(490);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private void restpassword(KeyEvent event) {
    }

    @FXML
    private void forgetpass(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("/resetpassword.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setWidth(1050);
        stage.setHeight(576);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    @FXML
    void rememberme(ActionEvent event) {

    }
    private void aleart() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Wrong password");
        alert.setHeaderText("Results:");
        alert.setContentText("contact us for more information");

        alert.showAndWait();
    }

}