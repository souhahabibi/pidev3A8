package tn.esprit.services.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.UsersMeal;
import tn.esprit.services.IIngredientService;
import tn.esprit.services.IMealService;
import tn.esprit.entities.Ingredient;
import tn.esprit.entities.Meal;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealImpl implements IMealService {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    IIngredientService ingredient;

    public MealImpl() {
        this.con = MyDatabase.getInstance().getConnection();
        ingredient = new IngredientImpl();
    }

    public Meal save(Meal entity) {
        String req = "insert into meal (name, image_url, Recipe,Calories) VALUES (?,?,?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getImageUrl());
            preparedStatement.setString(3,entity.getRecipe());
            preparedStatement.setInt(4,entity.getCalories());
            preparedStatement.execute();

            rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                entity.setId(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }

    public void update(Meal entity) {
        String req = "update meal set name=?,Recipe=?,Calories=? where id=?";
        try {
            preparedStatement = con.prepareStatement(req);

            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getRecipe());
            preparedStatement.setInt(3,entity.getCalories());
            preparedStatement.setInt(4,entity.getId());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String req = "delete from meal where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Meal> getAll() {
        String req ="select * from meal";
        ObservableList<Meal> meals = FXCollections.observableArrayList();
        Meal meal = null;
        try {
            rs = con.createStatement().executeQuery(req);

            while (rs.next()){
                meal = new Meal(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5));
                meals.add(meal);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meals;
    }

    public Meal getById(int id) {
        String req = "select * from meal where id=?";
        Meal meal = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            rs = preparedStatement.executeQuery();

            if(rs.next()){
                meal = new Meal(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meal;
    }

    public boolean find(Meal meal) {
        if (meal.getId() !=0 ){
            String req = "select * from meal where id=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setInt(1,meal.getId());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String req = "select * from meal where name=? and Recipe=?";
            try {
                preparedStatement = con.prepareStatement(req);
                preparedStatement.setString(1,meal.getName());
                preparedStatement.setString(2,meal.getRecipe());
                rs = preparedStatement.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    public Meal findOne(Meal meal) {
        String req = "select * from meal where name=? and Recipe=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1,meal.getName());
            preparedStatement.setString(2,meal.getRecipe());

            rs = preparedStatement.executeQuery();
            if(rs.next()){
                meal = getById(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meal;
    }

    @Override
    public Meal addIngredient(int idMeal, int idIngredient) {
       String req = "insert into ingredient_meal (ingredient_id, meal_id) VALUES (?,?)";
        Meal meal = new Meal();
        try {
          preparedStatement =con.prepareStatement(req);
          preparedStatement.setInt(1,idIngredient);
          preparedStatement.setInt(2,idMeal);

            preparedStatement.execute();
       } catch (SQLException throwables) {
           throwables.printStackTrace();
        }
        meal = getById(idMeal);
        meal.getIngredients().add(ingredient.getById(idIngredient));
        return meal;
    }
    public List<Ingredient> getMealIngredients(Meal meal) {
        String req = "select * from ingredients_meal where meal_id=?";
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient ingred = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,meal.getId());

            rs = preparedStatement.executeQuery();
            while (rs.next()){
                ingred = ingredient.getById(rs.getInt(1));
                ingredients.add(ingred);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ingredients;
    }

    public Meal addIngredients(int idMeal, List<Ingredient> ingredients) {
       Meal meal = getById(idMeal);
      meal.getIngredients().addAll(ingredients);
      return meal;
    }


    public UsersMeal saveMealToUser(UsersMeal entity) {
        String req = "insert into usersmeal (userId, mealId) VALUES (?,?)";
        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,entity.getUserId());
            preparedStatement.setInt(2,entity.getMealId());

            preparedStatement.execute();

            rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
               // entity.setId(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }

    public ObservableList<UsersMeal> getAllUsersMeals() {
        String req ="select * from usersmeal";
        ObservableList<UsersMeal> meals = FXCollections.observableArrayList();
        UsersMeal meal = null;
        try {
            rs = con.createStatement().executeQuery(req);

            while (rs.next()){
                meal = new UsersMeal(rs.getInt(1),
                        rs.getInt(2));

                meals.add(meal);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meals;
    }
    public boolean isMealAttributedToUser(UsersMeal usersMeal) {
        String checkReq = "select * from usersmeal where userId=? and mealId=?";
        try {
            preparedStatement = con.prepareStatement(checkReq);
            preparedStatement.setInt(1, usersMeal.getUserId());
            preparedStatement.setInt(2, usersMeal.getMealId());
            rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    // Inside MealImpl class

    public List<Ingredient> getMealIngredients(int mealId) {
        List<Ingredient> ingredients = new ArrayList<>();

        try {
           String query = "SELECT i.id, i.name FROM ingredient i " +
                   "JOIN ingredient_meal im ON i.id = im.ingredient_id " +
                   "WHERE im.meal_id = ?";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, mealId);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
               Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("id"));
                ingredient.setName(rs.getString("name"));
               ingredients.add(ingredient);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        //} finally {
            // Close resources (ResultSet, PreparedStatement, etc.)
            // Handle exceptions and resource closing appropriately
        }

        return ingredients;
    }


}
