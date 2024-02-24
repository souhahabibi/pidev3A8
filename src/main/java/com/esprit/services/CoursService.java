package com.esprit.services;
import com.esprit.models.Cours;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;


public class CoursService implements IService<Cours> {

    private Connection connection;

    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Cours cours) {
        String req = "INSERT into cours(image, nom, description, niveau) values ('" + cours.getImage() + "','" + cours.getNom() + "','" + cours.getDescription() + "','" + cours.getNiveau() + "')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Cours Cours) {
        String req = "UPDATE Cours set image =  '" +  Cours.getImage() + " ', nom = '" +  Cours.getNom() + "', description = '" + Cours.getDescription()+ "', niveau = '" + Cours.getNiveau() + "' where id = " + Cours.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void supprimer(Cours Cours) {
        String req = "DELETE from Cours where id = " + Cours.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insererImage(Cours cours) {
        String req = "INSERT into cours(image, nom, description, niveau) values ('" + cours.getImage() + "','" + cours.getNom() + "','" + cours.getDescription() + "','" + cours.getNiveau() + "')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


        @Override
    public List<Cours> afficher() {
        List<Cours> Cours = new ArrayList<>();

        String req = "SELECT * from Cours";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Cours.add(new Cours(rs.getInt("id"), rs.getString("image"), rs.getString("nom"), rs.getString("description"), rs.getString("niveau")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Cours;
    }


}
