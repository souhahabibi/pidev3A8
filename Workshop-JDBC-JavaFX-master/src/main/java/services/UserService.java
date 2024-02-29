package services;


import models.User;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private Connection connection;


    public UserService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO user (nom, email, motDePasse, specialite, numero, recommandation, poids, taille, niveau, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getMotDePasse());
        preparedStatement.setString(4, user.getSpecialite());
        preparedStatement.setInt(5, user.getNumero());
        preparedStatement.setString(6, user.getRecommandation());
        preparedStatement.setInt(7, user.getPoids());
        preparedStatement.setInt(8, user.getTaille());
        preparedStatement.setString(9, user.getNiveau());
        preparedStatement.setString(10, user.getRole());

        preparedStatement.executeUpdate();
    }


    @Override
    public void modifier(User user) throws SQLException {
        String sql = "update user set nom = ?, email = ?, motDePasse = ?,specialite = ?,numero = ?,recommandation = ?,poids =? , taille=? , niveau = ?,role=? where email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getMotDePasse());
        preparedStatement.setString(4, user.getSpecialite());
        preparedStatement.setInt(5, user.getNumero());
        preparedStatement.setString(6, user.getRecommandation());
        preparedStatement.setInt(7, user.getPoids());
        preparedStatement.setInt(8, user.getTaille());
        preparedStatement.setString(9, user.getNiveau());
        preparedStatement.setString(10, user.getRole());
        preparedStatement.setString(11,user.getEmail());



        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }


    @Override
    public void supprimerUser (String email) throws SQLException {
        String sql = "delete from user where email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<User> recuperer() throws SQLException {
        String sql = "select * from user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User u = new User();
            u.setNom(rs.getString("nom"));
            u.setEmail(rs.getString("email"));
            u.setMotDePasse(rs.getString("MotDePasse"));
            u.setSpecialite(rs.getString("specialite"));
            u.setNumero(rs.getInt("numero"));
            u.setRecommandation(rs.getString("recommandation"));
            u.setPoids(rs.getInt("poids"));
            u.setTaille(rs.getInt("taille"));
            u.setNiveau(rs.getString("niveau"));
            u.setRole(rs.getString("role"));

            users.add(u);
        }
        return users;
    }


}

