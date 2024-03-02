package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Ingredient;
import tn.esprit.entities.Meal;
import tn.esprit.entities.ingredientMeal;
import tn.esprit.services.Impl.IngredientImpl;
import tn.esprit.services.Impl.MealImpl;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class IngredientAndRecipeController implements Initializable {

    private static IngredientAndRecipeController instance;

    public IngredientAndRecipeController() {
        instance = this;
    }


    @FXML
    private Button Add_To_Meal;

    @FXML
    private TextField Calories;

    @FXML
    private Label CaloriesProp;

    @FXML
    private Button Delete_btn;

    @FXML
    private Button Import_btn;

    @FXML
    private Label IngredientNameLable;

    @FXML
    private Button Savebtn;

    @FXML
    private TextArea TotalFat;

    @FXML
    private Button add_btn;

    @FXML
    private VBox chosenMealCard;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView imgMeal;

    @FXML
    private ImageView ingredientImg;

    @FXML
    private TextField nameIngred;

    @FXML
    private TextArea protein;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button updateingred;

    File file;

    IngredientImpl service = new IngredientImpl();

    private IngredientListener myListener;

    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();


    private Ingredient chosenIngredient;
    static Meal chosenMeal;

    MealImpl mealService = new MealImpl();

    public static IngredientAndRecipeController getInstance() {
        if (instance == null) {
            instance = new IngredientAndRecipeController();
        }
        return instance;
    }


    private ObservableList<Ingredient> getData() {
        ingredients = service.getAll();
        return ingredients;

    }

    private void setChosenIngredient(Ingredient ingredient) {

        chosenIngredient = ingredient;

        IngredientNameLable.setText(ingredient.getName());
        CaloriesProp.setText(String.valueOf(ingredient.getCalorie()));
        //idp=meal.getId();

        String path = "C:\\Users\\WIKI\\IdeaProjects\\PidevProject\\src\\main\\resources\\img\\" + ingredient.getImgUrl();
        //System.out.println(getClass().getResourceAsStream(path));
        Image image = new Image(path);
        ingredientImg.setImage(image);
//        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
//                "    -fx-background-radius: 30;");
        //System.out.println(product.getQuantity());


    }

    public void show() {
        ingredients.setAll(getData());
        System.out.println("jhhhhhhhhhhhhhhhhhhhhhhhhh");
        System.out.println(ingredients);

        if (ingredients.size() > 0) {
            setChosenIngredient(ingredients.get(0));
            myListener = new IngredientListener() {
                @Override
                public void onClickListener(Ingredient ingredient) {

                    setChosenIngredient(ingredient);
                }
            };
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < ingredients.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Ingredientitem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                IngredientItemController itemController = fxmlLoader.getController();
                itemController.setData(ingredients.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void DeleteIngredient(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Ingredient deleted successfully!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            service.deleteById(chosenIngredient.getId());



            // Remove the chosen meal from the UI
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null ||
                    GridPane.getRowIndex(node) == GridPane.getRowIndex(chosenMealCard) ||
                    GridPane.getColumnIndex(node) != null ||
                    GridPane.getColumnIndex(node) == GridPane.getColumnIndex(chosenMealCard));

            // Set chosenMeal to null
            chosenIngredient = null;

            // Update the UI
            show();
        }

    }

    @FXML
    void AddToMeal(ActionEvent event) {
        if (chosenIngredient != null && chosenMeal != null) {
            // Create an ingredientMeal instance to associate the chosenIngredient with chosenMeal
            ingredientMeal newAssociation = new ingredientMeal(chosenMeal.getId(), chosenIngredient.getId());

            // Save the association to the database or wherever you store your associations
            service.saveAssociation(newAssociation);

            // Optionally, you can show a confirmation message or perform other actions after saving
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ingredient Added to Meal");
            alert.setHeaderText(null);
            alert.setContentText("The ingredient has been added to the meal.");
            alert.showAndWait();

            // Update the UI to reflect the changes
            show();
        } else {
            // Handle the case where no ingredient or meal is chosen, show an alert or take appropriate action
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Ingredient or Meal Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an Ingredient and a Meal before adding to the Meal.");
            alert.showAndWait();
        }
    }



    @FXML
    void Save(ActionEvent event) {
        if (chosenIngredient != null) {
        // Update the chosen meal with the modified information
        chosenIngredient.setName(nameIngred.getText());
        chosenIngredient.setCalorie(Integer.parseInt(Calories.getText()));
        chosenIngredient.setTotalFat(Integer.parseInt(TotalFat.getText()));
        chosenIngredient.setProtein(Integer.parseInt(protein.getText()));

        // Save the changes to the database or wherever you store your meal data
        service.update(chosenIngredient);

        // Optionally, you can show a confirmation message or perform other actions after saving
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Changes Saved");
        alert.setHeaderText(null);
        alert.setContentText("Meal changes have been saved.");
        alert.showAndWait();

        // Update the UI to reflect the changes
        show();
        nameIngred.setText("");
        Calories.setText("");
        TotalFat.setText("");
        protein.setText("");
    } else {
        // Handle the case where no meal is chosen, show an alert or take appropriate action
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Ingredient Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select an Ingredient to update and save changes.");
        alert.showAndWait();
    }

    }

    @FXML
    void addMeal(ActionEvent event) {
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
                show();
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
    void update(ActionEvent event) {
        if (chosenIngredient != null) {
            // Populate the fields with the current meal's information
            nameIngred.setText(chosenIngredient.getName());
            Calories.setText(String.valueOf(chosenIngredient.getCalorie()));
            TotalFat.setText(String.valueOf(chosenIngredient.getTotalFat()));
            protein.setText(String.valueOf(chosenIngredient.getProtein()));

            // Other update logic can be added here based on your requirements
        } else {
            // Handle the case where no meal is chosen, show an alert or take appropriate action
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Meal Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a meal to update.");
            alert.showAndWait();
        }

    }
    public void setChosenMeal(Meal meal) {
        IngredientAndRecipeController.chosenMeal = meal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show();
    }
}
