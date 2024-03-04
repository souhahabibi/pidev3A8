package services;

import javafx.collections.ObservableList;
import models.Cours;
import models.Exercice;
import models.Personne;
import models.Salle;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleService implements IService<Salle>
{

    private Connection connection;
    public SalleService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override

    public void ajouter(Salle salle) throws SQLException {
        String sql = "INSERT INTO Salle(nom, description, lieu ,image) VALUES (?, ?, ?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, salle.getNom());
            statement.setString(2, salle.getDescription());
            statement.setString(3, salle.getLieu());
            statement.setString(4, salle.getImage());
            statement.executeUpdate();
        }
    }


    @Override
    public void modifier(Salle salle) throws SQLException {

        String sql = "update salle set nom = ?, description = ?, lieu = ?,image=? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, salle.getNom());
        preparedStatement.setString(2, salle.getDescription());
        preparedStatement.setString(3, salle.getLieu());
        preparedStatement.setString(4, salle.getImage());
        preparedStatement.setInt(5, salle.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {

        String sql = "delete from salle where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimerUser(String email) throws SQLException {

    }

    @Override
    public List<Salle> recuperer() throws SQLException {

        String sql = "select * from salle";
        Statement statement = connection.createStatement();//connexion entre la bd et java
        ResultSet rs = statement.executeQuery(sql);
        List<Salle> salles = new ArrayList<>();
        while (rs.next()) {
            Salle s = new Salle();
            s.setId(rs.getInt("id"));
            s.setNom(rs.getString("nom"));
            s.setDescription(rs.getString("description"));
            s.setLieu(rs.getString("lieu"));
            s.setImage(rs.getString("image"));
            salles.add(s);
        }
        return salles;
    }

    @Override
    public Salle save(Salle entity) {
        return null;
    }

    @Override
    public void update(Salle entity) {

    }

    @Override
    public void supprimer(Exercice exercice) {

    }

    @Override
    public void supprimer(Cours Cours) {

    }

    @Override
    public void insererImage(Salle salle) {

    }

    @Override
    public void ajoutercom(Salle salle) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ObservableList<Salle> getAll() {
        return null;
    }

    @Override
    public Salle getById(int id) {
        return null;
    }

    @Override
    public boolean find(Salle salle) {
        return false;
    }

    @Override
    public Salle findOne(Salle entity) {
        return null;
    }

    public String selectSalleNameById(int id) throws SQLException {
        String sql = "SELECT nom FROM salle WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("nom");
            }
        }
        return  null;
    }
public  Salle getsalle(int id){
    Salle salle = null;
    String sql = "SELECT * FROM salle WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            salle = new Salle();
            salle.setId(rs.getInt("id"));
            salle.setNom(rs.getString("nom"));
            salle.setDescription(rs.getString("description"));
            salle.setLieu(rs.getString("lieu"));
            salle.setImage(rs.getString("image"));
        }
    } catch (SQLException e) {
        System.err.println("Erreur lors de la récupération des détails de la salle : " + e.getMessage());
    }
    return salle;
}

}
