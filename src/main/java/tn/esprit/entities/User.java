package tn.esprit.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;

    private ObservableList<Meal> favoriteMeals;

    public User(int id, String username,  String email,String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.favoriteMeals = FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ObservableList<Meal> getFavoriteMeals() {
        return favoriteMeals;
    }

    public void setFavoriteMeals(ObservableList<Meal> favoriteMeals) {
        this.favoriteMeals = favoriteMeals;
    }

    public void addFavoriteMeal(Meal meal) {
        favoriteMeals.add(meal);
    }

    public void removeFavoriteMeal(Meal meal) {
        favoriteMeals.remove(meal);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
