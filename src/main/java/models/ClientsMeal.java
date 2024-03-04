package models;

public class ClientsMeal  {
    private int clientId;
    private int mealId;

    public ClientsMeal(int clientId, int mealId) {
        this.clientId = clientId;
        this.mealId = mealId;
    }

    public int getUserId() {
        return clientId;
    }

    public void setUserId(int clientId) {
        this.clientId = clientId;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    @Override
    public String toString() {
        return "ClientsMeal{" +
                "clientId=" + clientId +
                ", mealId=" + mealId +
                '}';
    }
}
