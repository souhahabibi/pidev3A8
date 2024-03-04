package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import models.Ingredient;
import models.Meal;
import services.MealImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RecipeController implements Initializable {

    @FXML
    private TextArea recipe;

    @FXML
    private ListView<String> ingredList;

    MealImpl mealService=new MealImpl();

    public void initializeData(int mealId) {
        // Fetch meal details from the database or your data source based on mealId
        Meal meal = mealService.getById(mealId);



        // Set the recipe text in the TextArea
        recipe.setText(meal.getRecipe());

        List<Ingredient> ingredients = mealService.getMealIngredients(meal);


        List<String> ingredientNames = ingredients.stream().map(Ingredient::getName).collect(Collectors.toList());
        ingredList.setItems(FXCollections.observableArrayList(ingredientNames));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}