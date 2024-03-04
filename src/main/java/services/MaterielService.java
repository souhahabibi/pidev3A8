package services;

import models.Materiel;
import models.Salle;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterielService implements IService<Materiel> {
    private Connection connection;
    SalleService ss = new SalleService();

    public MaterielService() {
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Materiel materiel) throws SQLException {

        String sql = "INSERT INTO Materiel (nom, age, quantite, prix, FK_idSalle, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, materiel.getNom());
            statement.setInt(2, materiel.getAge());
            statement.setInt(3, materiel.getQuantite());
            statement.setDouble(4, materiel.getPrix());
            statement.setInt(5, materiel.getFK_idSalle().getId());
            // Assuming image is a byte array
            statement.setString(6, materiel.getImage());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    }


    @Override
    public void modifier(Materiel materiel) throws SQLException {

        String sql = "update materiel set nom = ?, age = ?, quantite = ?, prix = ?, FK_idSalle = ?,image=? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, materiel.getNom());
        preparedStatement.setInt(2, materiel.getAge());
        preparedStatement.setInt(3, materiel.getQuantite());
        preparedStatement.setInt(4, materiel.getPrix());
        preparedStatement.setInt(5, materiel.getFK_idSalle().getId());
        preparedStatement.setString(6, materiel.getImage());
        preparedStatement.setInt(7, materiel.getId());


        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {

        String sql = "delete from materiel where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Materiel> recuperer() throws SQLException {

        String sql = "select * from materiel";
        Statement statement = connection.createStatement();//connexion entre la bd et java
        ResultSet rs = statement.executeQuery(sql);
        List<Materiel> materiels = new ArrayList<>();
        while (rs.next()) {
            Materiel s = new Materiel();
            s.setId(rs.getInt("id"));
            s.setNom(rs.getString("nom"));
            s.setAge(rs.getInt("age"));
            s.setQuantite(rs.getInt("quantite"));
            s.setPrix(rs.getInt("prix"));
            s.setFK_idSalle(ss.getsalle(rs.getInt("FK_idSalle")));
            s.setImage(rs.getString("image"));


            materiels.add(s);
        }
        return materiels;
    }

    public List<Materiel> select(int id) throws SQLException {

        String sql = "SELECT * FROM materiel WHERE FK_idSalle= ? ";

        List<Materiel> materiels = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Materiel s = new Materiel();
                    s.setId(rs.getInt("id"));
                    s.setNom(rs.getString("nom"));
                    s.setAge(rs.getInt("age"));
                    s.setQuantite(rs.getInt("quantite"));
                    s.setPrix(rs.getInt("prix"));
                    s.setFK_idSalle(ss.getsalle(rs.getInt("FK_idSalle")));
                    s.setImage(rs.getString("image"));
                    materiels.add(s);
                }
            }
        }
        return materiels;
    }
    public int recupererSommeQuantitesMateriels(int idSalle) throws SQLException {
        int sommeQuantites = 0;
        List<Materiel> materiels = select(idSalle);
        for (Materiel materiel : materiels) {
            sommeQuantites += materiel.getQuantite();
        }
        return sommeQuantites;
    }


}
