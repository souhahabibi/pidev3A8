package services;

import javafx.collections.ObservableList;
import models.Cours;
import models.Exercice;
import models.Organisateur;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganisateurService implements IService<Organisateur> {

    private Connection connection;
    public OrganisateurService(){connection = MyDatabase.getInstance().getConnection();}
    @Override
    public void ajouter(Organisateur organisateur) throws SQLException {
        String sql = "insert into organisateur (nom,numero) " +
                "values('" + organisateur.getNom() + "','" + organisateur.getNumero() + "')" ;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(Organisateur organisateur) throws SQLException {
        String sql = "update organisateur set nom = ?, numero = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, organisateur.getNom());
        preparedStatement.setString(2, organisateur.getNumero());
        preparedStatement.setInt(3, organisateur.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from organisateur where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimerUser(String email) throws SQLException {

    }

    @Override
    public List<Organisateur> recuperer() throws SQLException {
        String sql = "select * from organisateur";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Organisateur> organisateurs = new ArrayList<>();
        while (rs.next()) {
            Organisateur o = new Organisateur();
            o.setId(rs.getInt("id"));
            o.setNom(rs.getString("nom"));
            o.setNumero(rs.getString("numero"));

            organisateurs.add(o);
        }
        return organisateurs;
    }

    @Override
    public Organisateur save(Organisateur entity) {
        return null;
    }

    @Override
    public void update(Organisateur entity) {

    }

    @Override
    public void supprimer(Exercice exercice) {

    }

    @Override
    public void supprimer(Cours Cours) {

    }

    @Override
    public void insererImage(Organisateur organisateur) {

    }

    @Override
    public void ajoutercom(Organisateur organisateur) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ObservableList<Organisateur> getAll() {
        return null;
    }

    @Override
    public Organisateur getById(int id) {
        return null;
    }

    @Override
    public boolean find(Organisateur organisateur) {
        return false;
    }

    @Override
    public Organisateur findOne(Organisateur entity) {
        return null;
    }
}
