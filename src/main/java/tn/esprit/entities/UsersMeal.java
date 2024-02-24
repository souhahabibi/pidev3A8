package tn.esprit.entities;

public class UsersMeal {
    private int userId;
    private int mealId;

    public UsersMeal(int userId, int mealId) {
        this.userId = userId;
        this.mealId = mealId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    @Override
    public String toString() {
        return "UsersMeal{" +
                "userId=" + userId +
                ", mealId=" + mealId +
                '}';
    }
}
