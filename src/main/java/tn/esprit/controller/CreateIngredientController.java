package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.entities.Ingredient;
import tn.esprit.entities.Meal;
import tn.esprit.services.Impl.IngredientImpl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;

public class CreateIngredientController {

    private static CreateIngredientController instance;
    public CreateIngredientController() {
        instance = this;
    }

    public static CreateIngredientController getInstance() {
        if (instance == null) {
            instance = new CreateIngredientController();
        }
        return instance;
    }

    File file;
    @FXML
    private TextField Calories;

    @FXML
    private Button Import_btn;

    @FXML
    private Button Savebtn;

    @FXML
    private TextField TotalFat;

    @FXML
    private Button createIngredient;

    @FXML
    private ImageView imgMeal;

    @FXML
    private TextField nameIngred;

    @FXML
    private TextField protein;

    IngredientImpl service = new IngredientImpl();
    private Ingredient chosenIngredient;


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

    @FXML
    public void createIngrd(ActionEvent event) {
        try {
            // Validate the input fields
            if (!validateFields()) {
                return; // Stop execution if any field is not valid
            }

            // Continue with the rest of your code
            if (Calories.getText().isEmpty() || TotalFat.getText().isEmpty() || protein.getText().isEmpty() || nameIngred.getText().isEmpty()) {
                Alert alerts = new Alert(Alert.AlertType.WARNING);
                alerts.setTitle("Warning");
                alerts.setHeaderText(null);
                alerts.setContentText("Please fill in the fields!");
                alerts.show();
            } else {
                int caloriesValue = Integer.parseInt(Calories.getText());
                int totalFatValue = Integer.parseInt(TotalFat.getText());
                int proteinValue = Integer.parseInt(protein.getText());

                FileInputStream fl = new FileInputStream(file);

                byte[] data = new byte[(int) file.length()];
                String fileName = file.getName();
                String path = fileName;
                fl.read(data);
                fl.close();
                //System.out.println(file);

                Ingredient m = new Ingredient(nameIngred.getText(), caloriesValue, totalFatValue, proteinValue, path);
                service.save(m);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Regime added successfully!");
                alert.show();
                IngredientAndRecipeController.getInstance().show();
                Calories.setText("");
                TotalFat.setText("");
                protein.setText("");
                nameIngred.setText("");
            }
        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid numeric values for the fields!");
            alert.show();
        }
    }
    private boolean validateFields() {
        try {
            // Attempt to parse Calories, TotalFat, and Protein as integers
            Integer.parseInt(Calories.getText());
            Integer.parseInt(TotalFat.getText());
            Integer.parseInt(protein.getText());
            return true; // All fields are valid numbers
        } catch (NumberFormatException e) {
            // Show an alert if any field is not a valid number
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid numeric values for all fields.");
            alert.showAndWait();
            return false; // At least one field is not a valid number
        }
    }


    public void setIngredientInformation(Ingredient chosenIngredient) {
        this.chosenIngredient=chosenIngredient;
        nameIngred.setText(chosenIngredient.getName());
        Calories.setText(String.valueOf(chosenIngredient.getCalorie()));
        TotalFat.setText(String.valueOf(chosenIngredient.getTotalFat()));
        protein.setText(String.valueOf(chosenIngredient.getProtein()));
    }

}
