package tn.esprit.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.esprit.entities.Meal;






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
        String path = "C:\\Users\\WIKI\\IdeaProjects\\PidevProject\\src\\main\\resources\\img\\" + meal.getImageUrl();
        System.out.println(getClass().getResourceAsStream(path));
       Image image = new Image(path);
        img.setImage(image);

    }


}
