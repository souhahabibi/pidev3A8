package controllers;

import controllers.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Meal;

import java.io.File;


public class MealController {

    @FXML
    private Label priceLable;


    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(meal);
    }

    private Meal meal;
    private MyListener myListener;

    public void setData(Meal meal, MyListener myListener) {
        this.meal = meal;
        this.myListener = myListener;
        nameLabel.setText(meal.getName());
        priceLable.setText(String.valueOf( meal.getCalories()));
        String path = "file:///C:\\Users\\WIKI\\Desktop\\final_Integration\\src\\main\\resources\\img\\" + meal.getImageUrl();
        System.out.println(getClass().getResourceAsStream(path));
        System.out.println(path);
        Image image = new Image(path);
        img.setImage(image);

    }


}