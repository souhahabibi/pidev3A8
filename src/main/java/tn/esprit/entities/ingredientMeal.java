package tn.esprit.entities;

public class ingredientMeal {
    private int idMeal;
    private int idIngredient;

    public ingredientMeal(int idMeal, int idIngredient) {
        this.idMeal = idMeal;
        this.idIngredient = idIngredient;
    }

    public int getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(int idMeal) {
        this.idMeal = idMeal;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    @Override
    public String toString() {
        return "ingredientMeal{" +
                "idMeal=" + idMeal +
                ", idIngredient=" + idIngredient +
                '}';
    }
}
