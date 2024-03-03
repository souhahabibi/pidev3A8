package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Meal;
import tn.esprit.services.APIs.SendMail;
import tn.esprit.services.Impl.MealImpl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class CreateMealController implements Initializable {

    private final String ACCOUNT_SID = "ACf98656ce24cceaaa4be85d72a63ecbe8";
    private final String AUTH_TOKEN = "150155d4627db5404bfed786a0833ea8";


    private static CreateMealController instance;
    public CreateMealController() {
        instance = this;
    }

    public static CreateMealController getInstance() {
        if (instance == null) {
            instance = new CreateMealController();
        }
        return instance;
    }

    private Meal chosenMeal;
    @FXML
    private TextField Calories;

    @FXML
    private Button Import_btn;

    @FXML
    private TextField Meal_Name;

    @FXML
    private TextArea RecipeText;

    @FXML
    private Button Savebtn;

    @FXML
    private Button add_btn;

    @FXML
    private ImageView imgMeal;

    File file;

    MealImpl service = new MealImpl();





    @FXML
    void Save(ActionEvent event) {
        if (chosenMeal != null) {
            try {
                // Validate the calories input
                if (!validateCalories()) {
                    return; // Stop execution if calories are not valid
                }

                // Update the chosen meal with the modified information
                chosenMeal.setName(Meal_Name.getText());
                chosenMeal.setCalories(Integer.parseInt(Calories.getText()));
                chosenMeal.setRecipe(RecipeText.getText());

                // Save the changes to the database or wherever you store your meal data
                service.update(chosenMeal);

                // Optionally, you can show a confirmation message or perform other actions after saving
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Changes Saved");
                alert.setHeaderText(null);
                alert.setContentText("Meal changes have been saved.");
                alert.showAndWait();

                // Update the UI to reflect the changes
                CoachMealMangagementController.getInstance().show();
                Calories.setText("");
                Meal_Name.setText("");
                RecipeText.setText("");
            } catch (NumberFormatException e) {
                // Handle the case where the calories input is not a valid number
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Calories");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid number for Calories.");
                alert.showAndWait();
            }
        } else {
            // Handle the case where no meal is chosen, show an alert or take appropriate action
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Meal Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a meal to update and save changes.");
            alert.showAndWait();
        }
    }



    @FXML
        void addMeal(ActionEvent event) {
            try {
                // Validate the calories input
                if (!validateCalories()) {
                    return; // Stop execution if calories are not valid
                }

                // Continue with the rest of your code
                if (Calories.getText().isEmpty() || Meal_Name.getText().isEmpty()) {
                    Alert alerts = new Alert(Alert.AlertType.WARNING);
                    alerts.setTitle("Warning");
                    alerts.setHeaderText(null);
                    alerts.setContentText("Please fill in the fields!");
                    alerts.show();
                } else {
                    int caloriesValue = Integer.parseInt(Calories.getText());

                    FileInputStream fl = new FileInputStream(file);

                    byte[] data = new byte[(int) file.length()];
                    String fileName = file.getName();
                    String path = fileName;
                    fl.read(data);
                    fl.close();
                    //System.out.println(file);

                    Meal m = new Meal(Meal_Name.getText(), path, RecipeText.getText(), Integer.parseInt(Calories.getText()));
                    service.save(m);
                    String destinataire = "yosri.selmi369@gmail.com";
                    String sujet = "New meal added";
                    String contenu = "new meal added from the coach : ";

                    SendMail.envoyerEmailSansAuthentification(destinataire, sujet, contenu);
                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    Message twilioMessage = Message.creator(
                                    new PhoneNumber("+21624019297"),  // User's phone number
                                    new PhoneNumber("+15717486711"),  // Twilio phone number
                                    "New meal added: " + m.getName())
                            .create();


                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Meal added successfully!");
                    alert.show();
                    CoachMealMangagementController.getInstance().show();
                    Calories.setText("");
                    Meal_Name.setText("");
                    RecipeText.setText("");
                }
            } catch (IOException e) {
                // Handle IOException
                e.printStackTrace();
            }
        }
    private boolean validateCalories() {
        try {
            // Attempt to parse calories as an integer
            Integer.parseInt(Calories.getText());
            return true; // Calories are a valid number
        } catch (NumberFormatException e) {
            // Show an alert if calories are not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Calories");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid number for Calories.");
            alert.showAndWait();
            return false; // Calories are not a valid number
        }
    }

    @FXML
    File importimg(ActionEvent event) {
        Path to1 = null;
        String m = null;
        String path = "C:\\Users\\WIKI\\IdeaProjects\\PidevProject\\src\\main\\resources\\img";
        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "jpeg", "PNG");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            m = chooser.getSelectedFile().getAbsolutePath();

            file = chooser.getSelectedFile();
            String fileName = file.getName();

            if (chooser.getSelectedFile() != null) {

                try {
                    java.nio.file.Path from = Paths.get(chooser.getSelectedFile().toURI());
                    to1 = Paths.get(path + "\\" + fileName);
                    //           to2 = Paths.get("src\\"+path+"\\"+file.getName()+".png");

                    CopyOption[] options = new CopyOption[]{
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.COPY_ATTRIBUTES
                    };
                    Files.copy(from, to1, options);
                    System.out.println("added");
                    System.out.println(file);

                } catch (IOException ex) {
                    System.out.println();
                }
            }

        }
        System.out.println(file.getPath());
        Image img = new Image(file.getPath());
        imgMeal.setImage(img);
        return file;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMealInformation(Meal chosenMeal) {
        this.chosenMeal=chosenMeal;
        Meal_Name.setText(chosenMeal.getName());
        Calories.setText(String.valueOf(chosenMeal.getCalories()));
        RecipeText.setText(chosenMeal.getRecipe());
    }
}



