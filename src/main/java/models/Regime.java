package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.sql.Date;

public class Regime {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private  int Duration;
    private  String description;

    private int clientId;

    private ObservableList<Meal> meals;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    private Goal goal;

    public Regime(int id, LocalDate startDate, LocalDate endDate, int duration, String description, String goal) {
        //meals = FXCollections.observableArrayList();
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        Duration = duration;
        this.description = description;
        this.goal = Goal.valueOf(goal.toString());
    }

    public Regime( LocalDate startDate, LocalDate endDate, int duration, String description, String goal) {
        //meals = FXCollections.observableArrayList();
        this.startDate = startDate;
        this.endDate = endDate;
        Duration = duration;
        this.description = description;
        this.goal = Goal.valueOf(goal.toString());
    }

    public Regime( LocalDate startDate, LocalDate endDate, int duration, String description, String goal, int clientId) {
        //meals = FXCollections.observableArrayList();
        this.startDate = startDate;
        this.endDate = endDate;
        Duration = duration;
        this.description = description;
        this.goal = Goal.valueOf(goal.toString());
        this.clientId = clientId;
    }
    public Regime(LocalDate startDate, LocalDate endDate, int duration, String description) {
        meals = FXCollections.observableArrayList();
        this.startDate = startDate;
        this.endDate = endDate;
        Duration = duration;
        this.description = description;
    }

    public Regime() {
        meals = FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public String toString() {
        return "Regime{" +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", Duration=" + Duration +
                ", description='" + description + '\'' +
                '}';
    }
}