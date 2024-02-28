package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {

    private final String URL = "jdbc:mysql://localhost:3306/pidev3a8";
    private final String USER = "root";
    private final String PASSWORD = "";
    private Connection connection;// Objet de connexion à la base de données
    private static MyDatabase instance;// Instance unique de la classe MyDatabase


    private MyDatabase() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Méthode statique pour obtenir l'instance unique de la classe MyDatabase
    public static MyDatabase getInstance() {
        if(instance == null)
            instance = new MyDatabase();// Création d'une nouvelle instance si elle n'existe pas déjà
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }// Renvoie de l'objet de connexion
}
