package tn.esprit.services;

import tn.esprit.entities.Ingredient;
import tn.esprit.entities.Meal;

import java.util.List;

public interface IMealService extends IGenericService<Meal> {
    public Meal addIngredient(int idMeal,int idIngredient);

    public Meal addIngredients(int idMeal, List<Ingredient> ingredients);

    public List<Ingredient> getMealIngredients(Meal meal);


}
