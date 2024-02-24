package tn.esprit.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Meal {
    private int id;
    private String name;
    private String imageUrl;
    private String Recipe;
    private MealTime mealTime;

    private int Calories;

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    private ObservableList<Regime> regimes;
    private ObservableList<User> favoritedBy;


    private ObservableList<Ingredient> ingredients;
    public Meal(int id, String name, String imageUrl, String Recipe, String mealTime) {
        regimes =FXCollections.observableArrayList();
        ingredients=FXCollections.observableArrayList();
        favoritedBy =FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.Recipe = Recipe;
        this.mealTime= MealTime.valueOf(mealTime.toString());
    }

    public Meal(String name, String imageUrl, String Recipe,String mealTime) {
        regimes =FXCollections.observableArrayList();
        ingredients=FXCollections.observableArrayList();
        favoritedBy =FXCollections.observableArrayList();
        this.name = name;
        this.imageUrl = imageUrl;
        this.Recipe = Recipe;
        this.mealTime= MealTime.valueOf(mealTime.toString());

    }
    public Meal(String name, String imageUrl, String Recipe,int Calories) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.Recipe = Recipe;
        this.Calories = Calories;

    }

    public Meal(int id,String name, String imageUrl, String Recipe,int Calories) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.Recipe = Recipe;
        this.Calories = Calories;

    }




    public Meal() {
        regimes =FXCollections.observableArrayList();
        ingredients=FXCollections.observableArrayList();
        favoritedBy =FXCollections.observableArrayList();
    }
    public ObservableList<Regime> getRegimes(){
        return regimes;
    }
    public void setRegimes(ObservableList<Regime> regimes){
        this.regimes=regimes;
    }
    public ObservableList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ObservableList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ObservableList<User> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(ObservableList<User> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecipe() {
        return Recipe;
    }

    public void setRecipe(String Recipe) {
        this.Recipe = Recipe;
    }

    public MealTime getMealTime() {
        return mealTime;
    }

    public void setMealTime(MealTime mealTime) {
        this.mealTime = mealTime;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;

        Meal meal = (Meal) o;

        if (getName() != null ? !getName().equals(meal.getName()) : meal.getName() != null) return false;
        if (getImageUrl() != null ? !getImageUrl().equals(meal.getImageUrl()) : meal.getImageUrl() != null)
            return false;
        return getRecipe() != null ? getRecipe().equals(meal.getRecipe()) : meal.getRecipe() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getImageUrl() != null ? getImageUrl().hashCode() : 0);
        result = 31 * result + (getRecipe() != null ? getRecipe().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "\nMeal{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ",\n Recipe='" + Recipe +
                '}';
    }
}
