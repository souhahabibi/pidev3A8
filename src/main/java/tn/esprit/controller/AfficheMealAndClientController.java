package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import tn.esprit.entities.Meal;
import tn.esprit.entities.User;
import tn.esprit.entities.UsersMeal;
import tn.esprit.services.IMealService;
import tn.esprit.services.Impl.MealImpl;
import tn.esprit.services.Impl.UserImpl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;




public class AfficheMealAndClientController implements Initializable {

        @FXML
        private ListView<String> listView;

        @FXML
        private Button mod;

        @FXML
        private Button supprimer;

        MealImpl service = new MealImpl();

        UserImpl userService = new UserImpl();


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                ObservableList<String> items = FXCollections.observableArrayList();

                for (UsersMeal item : service.getAllUsersMeals()) {
                        User u = userService.getAll().stream().filter(user -> user.getId() == item.getUserId()).findFirst().orElse(null);
                        System.out.println(u);
                        Meal m = service.getAll().stream().filter(meal -> meal.getId() == item.getMealId()).findFirst().orElse(null);


                        if (u != null && m != null) {
                                // Check if the Meal object is not null before accessing its properties
                                String mealName = (m.getName() != null) ? m.getName() : "Unknown Meal";
                                String recipe = (m.getRecipe() != null) ? m.getRecipe() : "Unknown Recipe";

                                String userMealInfo = "Username: " + u.getUsername() + ", Email: " + u.getEmail() +
                                        ", Meal Name: " + mealName + ", Recipe: " + recipe;
                                // User u = userService.getById(item.getUserId());

                                // Meal m = service.getById(item.getMealId());
                                //System.out.println("ddddddddddddddddddddd");
                                // System.out.println(u.getUsername());
                                // listView.getItems().add(u.getUsername());
                                items.add(u.getUsername());

                                items.add(u.getEmail());
                                items.add(m.getName());
                                items.add(m.getRecipe());


                        }

                        listView.setItems(items);

                }
        }
        @FXML
        void modifier(ActionEvent event) {

        }

        @FXML
        void supprimer(ActionEvent event) {

        }
}



