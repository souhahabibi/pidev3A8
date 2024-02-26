package services;

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


}
