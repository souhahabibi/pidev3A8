package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.*;
import java.util.stream.Collectors;

public class CoachMealMangagementController implements Initializable {

    private static CoachMealMangagementController instance;

    public CoachMealMangagementController() {
        instance = this;
    }

    public static CoachMealMangagementController getInstance() {
        return instance;
    }

    @FXML
    private Button addIngred;

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

    @FXML
    private Button updatemeal;

    @FXML
    private Button createmeal;

    @FXML
    private Button Savebtn;

    @FXML
    private TextField searchField;


    MealImpl service = new MealImpl();

    UserImpl userService = new UserImpl();
    private MyListener myListener;

    private ObservableList<Meal> meals = FXCollections.observableArrayList();


    private Meal chosenMeal;

    static User chosenUser;

    private ObservableList<Meal> getData() {
        meals = service.getAll();
        return (ObservableList<Meal>) meals;

    }

    private void setChosenMeal(Meal meal) {

        chosenMeal = meal;

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
            System.out.println("test");
            usersList.getItems().clear();

            // Populate the usersList ComboBox with usernames
            List<String> usernames = userService.getAll().stream()
                    .map(User::getUsername)
                    .collect(Collectors.toList());

            // Add unique usernames to the ComboBox
            Set<String> uniqueUsernames = new LinkedHashSet<>(usernames);
            usersList.getItems().addAll(uniqueUsernames);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchMeal();
            });
            for (MealTime b : MealTime.values()) {
                Meal_time.getItems().add(b.toString());
            }


        } catch (Exception e) {
            System.out.println("error");
        }
        show();
    }
    private void searchMeal() {
        String searchQuery = searchField.getText().trim();

        if (!searchQuery.isEmpty()) {
            List<Meal> searchResults = meals.stream()
                    .filter(meal -> meal.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                    .collect(Collectors.toList());

            // Update the UI with the search results
            show(searchResults);
        } else {
            // If the search query is empty, show all meals
            show(meals);
        }
    }
    public void show() {
        meals.setAll(getData());
        System.out.println(meals);
        //System.out.println(meals);
        if (meals.size() > 0) {
            //setChosenMeal(meals.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Meal meal) {

                    setChosenMeal(meal);
                    // Set chosenMeal in the IngredientAndRecipeController
                    IngredientAndRecipeController.getInstance().setChosenMeal(meal);
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

    //@FXML
    //void addMeal(ActionEvent event) {
      //  try {
            // Validate the calories input
        //    if (!validateCalories()) {
          //      return; // Stop execution if calories are not valid
            //}

            // Continue with the rest of your code
            //if (Calories.getText().isEmpty() || Meal_Name.getText().isEmpty()) {
              //  Alert alerts = new Alert(Alert.AlertType.WARNING);
                //alerts.setTitle("Warning");
                //alerts.setHeaderText(null);
                //alerts.setContentText("Please fill in the fields!");
                //alerts.show();
            //} else {
              //  int caloriesValue = Integer.parseInt(Calories.getText());

                //FileInputStream fl = new FileInputStream(file);

                //byte[] data = new byte[(int) file.length()];
                //String fileName = file.getName();
                //String path = fileName;
                //fl.read(data);
                //fl.close();
                //System.out.println(file);

                //Meal m = new Meal(Meal_Name.getText(), path, RecipeText.getText(), Integer.parseInt(Calories.getText()));
                //service.save(m);

                //Alert alert = new Alert(Alert.AlertType.INFORMATION);
                //alert.setTitle("Information Dialog");
                //alert.setHeaderText(null);
                //alert.setContentText("Meal added successfully!");
                //alert.show();
                //show();
                //Calories.setText("");
                //Meal_Name.setText("");
                //RecipeText.setText("");
            //}
        //} catch (IOException e) {
            // Handle IOException
          //  e.printStackTrace();
        //}
    //}

    //private boolean validateCalories() {
      //  try {
            // Attempt to parse calories as an integer
        //    Integer.parseInt(Calories.getText());
          //  return true; // Calories are a valid number
        //} catch (NumberFormatException e) {
            // Show an alert if calories are not a valid number
          //  Alert alert = new Alert(Alert.AlertType.ERROR);
           // alert.setTitle("Invalid Calories");
           // alert.setHeaderText(null);
           // alert.setContentText("Please enter a valid number for Calories.");
           // alert.showAndWait();
           // return false; // Calories are not a valid number
        //}
    //}


    @FXML
    void AddToClient(ActionEvent event) {
        try {
            chosenUser = userService.getAll().stream().filter(user -> user.getUsername().equals(usersList.getValue())).findFirst().orElse(null);
            UsersMeal usersMeal = new UsersMeal(chosenUser.getId(), chosenMeal.getId());

            // Check if the meal is already attributed to the user
            if (!service.isMealAttributedToUser(usersMeal)) {
                // If it doesn't exist, proceed with saving the record
                service.saveMealToUser(usersMeal);

                // Load AfficheMealAndClient.fxml
                Parent root = FXMLLoader.load(getClass().getResource("/AfficheMealAndClient.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // Show a warning or take appropriate action since the combination already exists
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("This meal is already attributed to the selected user.");
                alert.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    //public File importimg(ActionEvent actionEvent) {
      //  Path to1 = null;
       // String m = null;
        //String path = "C:\\Users\\WIKI\\IdeaProjects\\PidevProject\\src\\main\\resources\\img";
        //JFileChooser chooser = new JFileChooser();

        //FileNameExtensionFilter filter = new FileNameExtensionFilter(
          //      "JPG & PNG Images", "jpg", "jpeg", "PNG");
        //chooser.setFileFilter(filter);
        //int returnVal = chooser.showOpenDialog(null);
        //if (returnVal == JFileChooser.APPROVE_OPTION) {
          //  m = chooser.getSelectedFile().getAbsolutePath();

            //file = chooser.getSelectedFile();
            //String fileName = file.getName();

            //if (chooser.getSelectedFile() != null) {

              //  try {
                //    java.nio.file.Path from = Paths.get(chooser.getSelectedFile().toURI());
                  //  to1 = Paths.get(path + "\\" + fileName);
                    //           to2 = Paths.get("src\\"+path+"\\"+file.getName()+".png");

                   // CopyOption[] options = new CopyOption[]{
                     //       StandardCopyOption.REPLACE_EXISTING,
                       //     StandardCopyOption.COPY_ATTRIBUTES
                   // };
                   // Files.copy(from, to1, options);
                   // System.out.println("added");
                   // System.out.println(file);

               // } catch (IOException ex) {
                 //   System.out.println();
               // }
           // }

       // }
       // System.out.println(file.getPath());
       // Image img = new Image(file.getPath());
       // imgMeal.setImage(img);
       // return file;
   // }

       @FXML
       void create(ActionEvent event) {
           try {
               Parent root = FXMLLoader.load(getClass().getResource("/CreateMeal.fxml"));
               Stage stage = new Stage();
               stage.setScene(new Scene(root));
               stage.show();
           } catch (IOException e) {
               e.printStackTrace();
               System.err.println(e.getMessage());
           }
       }
    @FXML
    void DeleteMeal(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Meal deleted successfully!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            service.deleteById(chosenMeal.getId());



            // Remove the chosen meal from the UI
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null ||
                    GridPane.getRowIndex(node) == GridPane.getRowIndex(chosenMealCard) ||
                    GridPane.getColumnIndex(node) != null ||
                    GridPane.getColumnIndex(node) == GridPane.getColumnIndex(chosenMealCard));

            // Set chosenMeal to null
            chosenMeal = null;

            // Update the UI
            show();
        }
    }

    public void update(ActionEvent actionEvent) {
        if (chosenMeal != null) {
            try {
                // Load createmeal.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateMeal.fxml"));
                Parent root = loader.load();

                // Get the controller associated with createmeal.fxml
                CreateMealController createMealController = loader.getController();

                // Set the fields with existing meal information
                createMealController.setMealInformation(chosenMeal);

                // Create a new stage and set the scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                // Show the stage
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        } else {
            // Handle the case where no meal is chosen, show an alert or take appropriate action
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Meal Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a meal to update.");
            alert.showAndWait();
        }
    }


//    public void Save(ActionEvent actionEvent) {
//        if (chosenMeal != null) {
//            // Update the chosen meal with the modified information
//            chosenMeal.setName(Meal_Name.getText());
//            chosenMeal.setCalories(Integer.parseInt(Calories.getText()));
//            chosenMeal.setRecipe(RecipeText.getText());
//
//            // Save the changes to the database or wherever you store your meal data
//            service.update(chosenMeal);
//
//            // Optionally, you can show a confirmation message or perform other actions after saving
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Changes Saved");
//            alert.setHeaderText(null);
//            alert.setContentText("Meal changes have been saved.");
//            alert.showAndWait();
//
//            // Update the UI to reflect the changes
//            show();
//            Calories.setText("");
//            Meal_Name.setText("");
//            RecipeText.setText("");
//        } else {
//            // Handle the case where no meal is chosen, show an alert or take appropriate action
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("No Meal Selected");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select a meal to update and save changes.");
//            alert.showAndWait();
//        }
   // }

    public void AddIngredient(ActionEvent actionEvent) {
        try {
            //chosenUser = userService.getAll().stream().filter(user -> user.getUsername().equals(usersList.getValue())).findFirst().orElse(null);
            //UsersMeal usersMeal = new UsersMeal(chosenUser.getId(), chosenMeal.getId());
            // service.saveMealToUser(usersMeal);
            Parent root = FXMLLoader.load(getClass().getResource("/IngredientAndRecipe.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void searchMeal(ActionEvent event) {
        String searchQuery = searchField.getText().trim();

        if (!searchQuery.isEmpty()) {
            List<Meal> searchResults = meals.stream()
                    .filter(meal -> meal.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                    .collect(Collectors.toList());

            // Update the UI with the search results
            show(searchResults);
        } else {
            // If the search query is empty, show all meals
            show(meals);
        }
    }

    public void show(List<Meal> displayMeals) {
        grid.getChildren().clear(); // Clear the existing grid

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < displayMeals.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                MealController itemController = fxmlLoader.getController();
                itemController.setData(displayMeals.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}