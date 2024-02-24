package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tn.esprit.services.Impl.MealImpl;
import tn.esprit.entities.Meal;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MealsController implements Initializable {
    @FXML
    private VBox chosenMealCard;

    @FXML
    private Label mealNameLable;

    @FXML
    private Label mealCalorieLabel;

    @FXML
    private ImageView mealImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    int idp;

    MealImpl service = new MealImpl();

    private Image image;
    private MyListener myListener;
    private List<Meal> meals = new ArrayList<>();


    private List<Meal> getData() {
        meals = service.getAll();
        return meals;

    }

    private void setChosenMeal(Meal meal) {



        mealNameLable.setText(meal.getName());
        mealCalorieLabel.setText(String.valueOf(meal.getRecipe()));
        idp=meal.getId();

        String path = "C:\\Users\\WIKI\\IdeaProjects\\PidevProject\\src\\main\\resources\\img\\" + meal.getImageUrl();
        //System.out.println(getClass().getResourceAsStream(path));
        Image image = new Image(path);
        mealImg.setImage(image);
//        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
//                "    -fx-background-radius: 30;");
        //System.out.println(product.getQuantity());





    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        show();
    }

    public void show() {
        meals.addAll(getData());
        System.out.println(meals);
        if (meals.size() > 0) {
            setChosenMeal(meals.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Meal meal) {

                    setChosenMeal(meal);
                }
            };
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < meals.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                MealController itemController = fxmlLoader.getController();
                System.out.println(meals.get(i));
                itemController.setData(meals.get(i), myListener);

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

    public void afficheDescetIngred(ActionEvent actionEvent) {
    }

    public void addTOlist(ActionEvent actionEvent) {
    }
}
