package tn.esprit.test;

import tn.esprit.entities.Meal;
import tn.esprit.services.Impl.MealImpl;
import tn.esprit.services.Impl.RegimeImpl;

import java.sql.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {
        //RegimeImpl service = new RegimeImpl();
        // Press Alt+Entrée with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        String str="2016-04-31";
        Date date=Date.valueOf(str);
        //Regime regime = new Regime(3, date.toLocalDate(), date.toLocalDate(),1,"slah", But.Gaining_weight.toString());
        //service.save(regime);
        //System.out.println(service.getAll());
        //service.update(regime);
        //System.out.println(service.getById(3));
        //service.deleteById(1);
        MealImpl service = new MealImpl();
        Meal m = new Meal("Meal_Name.getText()", "path" , "RecipeText.getText()", 4);
        //service.save(m);


    }
}