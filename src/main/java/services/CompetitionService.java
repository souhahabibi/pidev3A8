package services;

import models.Competition;
import models.Organisateur;
import models.Reservation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompetitionService implements IService<Competition> {
    private Connection connection;
    public CompetitionService(){connection = MyDatabase.getInstance().getConnection();}
    @Override
    public void ajouter(Competition competition) throws SQLException {
        String sql = "INSERT INTO competition (nom, description, videoURL, date, capacite, fk_organisateur_id) " +
                "VALUES('" + competition.getNom() + "','" +
                competition.getDescription() + "','" +
                competition.getVideoURL() + "','" +
                new java.sql.Date(competition.getDate().getTime()) + "'," +
                competition.getCapacite() + "," +
                competition.getFk_organisateur_id() + ")";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(Competition competition) throws SQLException {
        String sql = "UPDATE competition SET nom = ?, description = ?, videoURL = ?, date = ?, capacite = ?, fk_organisateur_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, competition.getNom());
        preparedStatement.setString(2, competition.getDescription());
        preparedStatement.setString(3, competition.getVideoURL());
        preparedStatement.setDate(4, new java.sql.Date(competition.getDate().getTime()));
        preparedStatement.setInt(5, competition.getCapacite());
        preparedStatement.setInt(6, competition.getFk_organisateur_id());
        preparedStatement.setInt(7, competition.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from competition where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Competition> recuperer() throws SQLException {
        String sql = "select * from competition";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Competition> competitions = new ArrayList<>();
        while (rs.next()) {
            Competition o = new Competition();
            o.setId(rs.getInt("id"));
            o.setNom(rs.getString("nom"));
            java.sql.Date sqlDate = rs.getDate("date");
            if (sqlDate != null) {
                o.setDate(new java.sql.Date(sqlDate.getTime())); // Assuming setDate accepts java.util.Date
            }
            o.setDescription(rs.getString("description"));
            o.setCapacite(rs.getInt("capacite"));
            o.setVideoURL(rs.getString("videoURL"));
            o.setFk_organisateur_id(rs.getInt("fk_organisateur_id"));

            competitions.add(o);
        }
        return competitions;
    }

}
