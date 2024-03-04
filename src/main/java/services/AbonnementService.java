package services;

import javafx.collections.ObservableList;
import models.*;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementService implements IService<Abonnement> {
    private Connection connection;
    SalleService ss = new SalleService();
    public AbonnementService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Abonnement abonnement) throws SQLException {
        String sql = "INSERT INTO abonnement(montant,duree,description,FK_idSalle) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, abonnement.getMontant());
            statement.setInt(2, abonnement.getDuree());
            statement.setString(3, abonnement.getDescription());
            statement.setInt(4, abonnement.getFK_idSalle().getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void modifier(Abonnement abonnement) throws SQLException {
            String sql = "update abonnement set montant = ?, duree = ?, description = ?, FK_idSalle = ?  where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, abonnement.getMontant());
            preparedStatement.setInt(2, abonnement.getDuree());
            preparedStatement.setString(3, abonnement.getDescription());
            preparedStatement.setInt(4, abonnement.getFK_idSalle().getId());
            preparedStatement.setInt(5, abonnement.getId());


            preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from abonnement where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimerUser(String email) throws SQLException {

    }

    @Override
    public List<Abonnement> recuperer() throws SQLException {

        String sql = "select * from abonnement";
        Statement statement = connection.createStatement();//connexion entre la bd et java
        ResultSet rs = statement.executeQuery(sql);
        List<Abonnement> abonnements = new ArrayList<>();
        while (rs.next()) {
            Abonnement s = new Abonnement();
            s.setId(rs.getInt("id"));
            s.setMontant(rs.getInt("montant"));
            s.setDuree(rs.getInt("duree"));
            s.setDescription(rs.getString("description"));
            s.setFK_idSalle(ss.getsalle(rs.getInt("FK_idSalle")));


            abonnements.add(s);
        }
        return abonnements;
    }

    @Override
    public Abonnement save(Abonnement entity) {
        return null;
    }

    @Override
    public void update(Abonnement entity) {

    }

    @Override
    public void supprimer(Exercice exercice) {

    }

    @Override
    public void supprimer(Cours Cours) {

    }

    @Override
    public void insererImage(Abonnement abonnement) {

    }

    @Override
    public void ajoutercom(Abonnement abonnement) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ObservableList<Abonnement> getAll() {
        return null;
    }

    @Override
    public Abonnement getById(int id) {
        return null;
    }

    @Override
    public boolean find(Abonnement abonnement) {
        return false;
    }

    @Override
    public Abonnement findOne(Abonnement entity) {
        return null;
    }


    public List<Abonnement>  select(int id) throws SQLException {

        String sql = "SELECT * FROM abonnement WHERE FK_idSalle= ? ";

        List<Abonnement> abonnements = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Abonnement s = new Abonnement();
                    s.setId(rs.getInt("id"));
                    s.setMontant(rs.getInt("montant"));
                    s.setDuree(rs.getInt("duree"));
                    s.setDescription(rs.getString("description"));
                    s.setFK_idSalle(ss.getsalle(rs.getInt("FK_idSalle")));


                    abonnements.add(s);
                }
            }
        }
        return abonnements;
    }
}
