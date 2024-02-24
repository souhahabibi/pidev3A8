package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.*;
import tn.esprit.services.IMealService;
import tn.esprit.services.Impl.MealImpl;
import tn.esprit.services.Impl.UserImpl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CoachMealMangagementController implements Initializable {

    private static CoachMealMangagementController instance;

    public CoachMealMangagementController () { instance = this; }
    public static CoachMealMangagementController getInstance() { return instance; }

    @FXML
    private Button Add_To_Client;

    @FXML
    private TextField Calories;
    @FXML
    private Label CaloriesProp;

    @FXML
    private Button Delete_btn;

    @FXML
    private Button Import_btn;

    @FXML
    private Label MealNameLable;


    @FXML
    private TextField Meal_Name;

    @FXML
    private ComboBox<String> Meal_time;

    @FXML
    private ComboBox<String> usersList;

    @FXML
    private Button add_btn;

    @FXML
    private VBox chosenMealCard;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView mealImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextArea RecipeText;

    File file;

    @FXML
    private ImageView imgMeal;

    MealImpl service = new MealImpl();

    UserImpl userService = new UserImpl();
    private MyListener myListener;

    private List<Meal> meals = new ArrayList<>();

    static Meal chosenMeal;

    static User chosenUser;

    private List<Meal> getData() {
        meals = service.getAll();
        return meals;

    }

    private void setChosenMeal(Meal meal) {

        chosenMeal  = meal;

        MealNameLable.setText(meal.getName());
        CaloriesProp.setText(String.valueOf(meal.getCalories()));
        //idp=meal.getId();

        String path = "C:\\Users\\WIKI\\IdeaProjects\\PidevProject\\src\\main\\resources\\img\\" + meal.getImageUrl();
        //System.out.println(getClass().getResourceAsStream(path));
          Image image = new Image(path);
        mealImg.setImage(image);
//        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
//                "    -fx-background-radius: 30;");
        //System.out.println(product.getQuantity());





    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (MealTime b : MealTime.values()) {
                Meal_time.getItems().add(b.toString());
            }
            for (User u : userService.getAll()){
                usersList.getItems().add(u.getUsername());
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    show();
    }
    public void show() {
        meals.addAll(getData());
        //System.out.println(meals);
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
    @FXML
    void addMeal(ActionEvent event) {
        try {
           // String typeMeal = (String) Meal_time.getValue().toString();

            if (Calories.getText().isEmpty() || Meal_Name.getText().isEmpty() ) {
                Alert alerts = new Alert(Alert.AlertType.WARNING);
                alerts.setTitle("Warning");
                alerts.setHeaderText(null);
                alerts.setContentText("Please fill in the fields!");
                alerts.show();
            } else {
                int caloriesValue = Integer.parseInt(Calories.getText());


                FileInputStream fl = new FileInputStream(file);

                byte[] data = new byte[(int) file.length()];
                String fileName = file.getName();
                String path = fileName;
                fl.read(data);
                fl.close();
                //System.out.println(file);

                Meal m = new Meal(Meal_Name.getText(), path , RecipeText.getText(), Integer.parseInt(Calories.getText()));
                service.save(m);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Regime added successfully!");
                alert.show();
                show();
                Calories.setText("");
            }
        } catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid duration (numeric value)!");
            alert.show();
        }
    }
    @FXML
    void AddToClient(ActionEvent event) {
        try {
            //chosenUser = userService.getAll().stream().filter(user -> user.getUsername().equals(usersList.getValue())).findFirst().orElse(null);
            //UsersMeal usersMeal = new UsersMeal(chosenUser.getId(), chosenMeal.getId());
            //service.saveMealToUser(usersMeal);
            Parent root = FXMLLoader.load(getClass().getResource("/AfficheMealAndClient.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public File importimg(ActionEvent actionEvent) {
        Path to1 = null;
        String m = null;
        String path = "C:\\Users\\WIKI\\IdeaProjects\\PidevProject\\src\\main\\resources\\img";
        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "jpeg", "PNG");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            m = chooser.getSelectedFile().getAbsolutePath();

            file = chooser.getSelectedFile();
            String fileName = file.getName();

            if (chooser.getSelectedFile() != null) {

                try {
                    java.nio.file.Path from = Paths.get(chooser.getSelectedFile().toURI());
                    to1 = Paths.get(path + "\\" + fileName);
                    //           to2 = Paths.get("src\\"+path+"\\"+file.getName()+".png");

                    CopyOption[] options = new CopyOption[]{
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.COPY_ATTRIBUTES
                    };
                    Files.copy(from, to1, options);
                    System.out.println("added");
                    System.out.println(file);

                } catch (IOException ex) {
                    System.out.println();
                }
            }

        }
        System.out.println(file.getPath());
        Image img = new Image(file.getPath());
        imgMeal.setImage(img);
        return file;
    }

    @FXML
    void DeleteMeal(ActionEvent event) {

    }
}

