package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Cours;
import models.Exercice;
import models.Regime;
import utils.MyDatabase;


import java.sql.*;
import java.util.List;

public class RegimeImpl implements IRegimeService {

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public RegimeImpl() {
        this.con = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Regime regime) throws SQLException {

    }

    @Override
    public void modifier(Regime regime) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public void supprimerUser(String email) throws SQLException {

    }

    @Override
    public List<Regime> recuperer() throws SQLException {
        return null;
    }

    @Override
    public Regime save(Regime entity) {
        String req = "insert into regime (startDate,endDate, Duration ,description,goal, clientId) VALUES (?,?,?,?,?,?)";

        try {
            preparedStatement = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1,Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(2,Date.valueOf(entity.getEndDate()));
            preparedStatement.setInt(3,entity.getDuration());
            preparedStatement.setString(4,entity.getDescription());
            preparedStatement.setString(5,entity.getGoal().toString());
            preparedStatement.setInt(6, entity.getClientId());
            preparedStatement.execute();

            rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                entity.setId(rs.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }

    @Override
    public void update(Regime entity) {
        String req = "update regime set startDate=?,endDate=?,Duration=?,description=?,goal=? where id=?";

        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setDate(1,Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(2,Date.valueOf(entity.getEndDate()));
            preparedStatement.setInt(3,entity.getDuration());
            preparedStatement.setString(4,entity.getDescription());
            preparedStatement.setString(5,entity.getGoal().toString());
            preparedStatement.setInt(6,entity.getId());
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void supprimer(Exercice exercice) {

    }

    @Override
    public void supprimer(Cours Cours) {

    }

    @Override
    public void insererImage(Regime regime) {

    }

    @Override
    public void ajoutercom(Regime regime) {

    }

    @Override
    public void deleteById(int id) {
        String req ="delete from regime where id=?";
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public ObservableList<Regime> getAll() {
        String req = "select * from regime";
        ObservableList<Regime> regimes = FXCollections.observableArrayList();
        try {
            rs = con.createStatement().executeQuery(req);
            while (rs.next()){
                Regime p1 = new Regime(rs.getInt(1)
                        ,rs.getDate(2).toLocalDate()
                        ,rs.getDate(3).toLocalDate()
                        ,rs.getInt(4)
                        ,rs.getString(5),
                        rs.getString(6));
                regimes.add(p1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return regimes;
    }

    @Override
    public Regime getById(int id) {
        String req = "select * from regime where id=?";
        Regime regime = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);
            rs = preparedStatement.executeQuery();
            if ( rs.next()){
                regime = new Regime(rs.getInt(1)
                        ,rs.getDate(2).toLocalDate()
                        ,rs.getDate(3).toLocalDate()
                        ,rs.getInt(4)
                        ,rs.getString(5),
                        rs.getString(6));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return regime;
    }

    @Override
    public boolean find(Regime regime) {
        return false;
    }

    @Override
    public Regime findOne(Regime entity) {
        return null;
    }
}