package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Ingredient;
import models.Meal;
import models.ingredientMeal;
import services.IngredientImpl;
import services.MealImpl;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class IngredientAndRecipeController implements Initializable {

    private static IngredientAndRecipeController instance;

    public IngredientAndRecipeController() {
        instance = this;
    }

    private IngredientListener ingredientListener;


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
    private TextField protein;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button updateingred;
    @FXML
    private TextField searchF;

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

        String path = "file:///C:\\Users\\WIKI\\Desktop\\final_Integration\\src\\main\\resources\\img\\" + ingredient.getImgUrl();
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
            ingredientListener = new IngredientListener() {
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
                itemController.setData(ingredients.get(i), ingredientListener);

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
        System.out.println("ingredient"+chosenIngredient.getId()+"meal"+chosenMeal.getId());

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
    void update(ActionEvent event) {
        if (chosenIngredient != null) {
            try {
                // Load createmeal.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateIngredient.fxml"));
                Parent root = loader.load();

                // Get the controller associated with createmeal.fxml
                CreateIngredientController createIngredientController = loader.getController();

                createIngredientController.setIngredientInformation(chosenIngredient);

                // Create a new stage and set the scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                // Show the stage
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        } else {
            // Handle the case where no meal is chosen, show an alert or take appropriate action
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Meal Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a meal to update.");
            alert.showAndWait();
        }
        // Populate the fields with the current meal's information
        //nameIngred.setText(chosenIngredient.getName());
        //Calories.setText(String.valueOf(chosenIngredient.getCalorie()));
        //TotalFat.setText(String.valueOf(chosenIngredient.getTotalFat()));
        //protein.setText(String.valueOf(chosenIngredient.getProtein()));

        // Other update logic can be added here based on your requirements
        //} else {
        // Handle the case where no meal is chosen, show an alert or take appropriate action
        //  Alert alert = new Alert(Alert.AlertType.WARNING);
        //alert.setTitle("No Meal Selected");
        //alert.setHeaderText(null);
        //alert.setContentText("Please select a meal to update.");
        //alert.showAndWait();
        //}

    }

    public void setChosenMeal(Meal meal) {
        IngredientAndRecipeController.chosenMeal = meal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        show();
    }

    public void createIngred(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/CreateIngredient.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void searchIngred(ActionEvent event) {
        String searchQuery = searchF.getText().trim();

        if (!searchQuery.isEmpty()) {
            List<Ingredient> searchResults = ingredients.stream()
                    .filter(ingredient -> ingredient.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                    .collect(Collectors.toList());

            // Update the UI with the search results
            show(searchResults);
        } else {
            // If the search query is empty, show all meals
            show(ingredients);
        }

    }

    public void show(List<Ingredient> displayIngredients) {
        grid.getChildren().clear(); // Clear the existing grid

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < displayIngredients.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Ingredientitem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                IngredientItemController itemingController = fxmlLoader.getController();
                itemingController.setData(displayIngredients.get(i), ingredientListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}