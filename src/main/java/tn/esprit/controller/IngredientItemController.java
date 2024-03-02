package tn.esprit.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.esprit.entities.Ingredient;
import tn.esprit.entities.Meal;

public class IngredientItemController {

    @FXML
    private Label priceLable;


    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(ingredient);
    }

    private Ingredient ingredient;
    private IngredientListener myListener;

    public void setData(Ingredient ingredient, IngredientListener myListener) {
        this.ingredient = ingredient;
        this.myListener = myListener;
        nameLabel.setText(ingredient.getName());
        priceLable.setText(String.valueOf( ingredient.getCalorie()));
        String path = "C:\\Users\\WIKI\\IdeaProjects\\PidevProject\\src\\main\\resources\\img\\" + ingredient.getImgUrl();
        System.out.println(getClass().getResourceAsStream(path));
        Image image = new Image(path);
        img.setImage(image);


    }
}
