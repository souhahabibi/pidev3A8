package services;


import models.Ingredient;
import models.Meal;

import java.util.List;

public interface IMealService extends IService<Meal> {
    public Meal addIngredient(int idMeal,int idIngredient);

    public Meal addIngredients(int idMeal, List<Ingredient> ingredients);

    public List<Ingredient> getMealIngredients(Meal meal);


}