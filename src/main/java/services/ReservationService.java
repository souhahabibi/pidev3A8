package services;

import models.Reservation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {

    private Connection connection;

    public ReservationService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        String sql = "insert into reservation (fk_client_id,fk_competition_id,score) " +
                "values('" + reservation.getFk_client_id() + "','"
                + reservation.getFk_competition_id() + "','" + reservation.getScore() + "')" ;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifier(Reservation reservation) throws SQLException {
        String sql = "update reservation set score = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, reservation.getScore());
        preparedStatement.setInt(2, reservation.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from reservation where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Reservation> recuperer() throws SQLException {
        return null;
    }
    public List<Reservation> getReservations(int fk_competition_id)throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        String sql = "SELECT * FROM reservation WHERE fk_competition_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, fk_competition_id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Reservation res = new Reservation();
                    res.setId(rs.getInt("id"));
                    res.setFk_client_id(rs.getInt("fk_client_id"));
                    res.setFk_competition_id(rs.getInt("fk_competition_id"));
                    res.setScore(rs.getInt("score"));
                    reservations.add(res);
                }
            }
        }
        return reservations;
    }
    public int checkReservation(int fk_client_id, int fk_competition_id) throws SQLException {
        String sql = "SELECT id FROM reservation WHERE fk_client_id = ? AND fk_competition_id = ? LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, fk_client_id);
            preparedStatement.setInt(2, fk_competition_id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                // If a matching reservation is found, return its id
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        // If no matching reservation is found, return 0
        return 0;
    }

}
