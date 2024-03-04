package services;

import javafx.collections.ObservableList;
import models.Cours;
import models.Exercice;
import models.coupon;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class couponService implements IService<coupon> {

    private Connection connection;

    public couponService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(coupon coupon) throws SQLException {
        String sql = "INSERT INTO coupon (nomSociete,code,valeur,dateExpiration,user_id) VALUES (?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, coupon.getNomSociete());
            statement.setInt(2,coupon.getCode());
            statement.setInt(3, coupon.getValeur());
            statement.setString(4,coupon.getDateExpiration());
            statement.setInt(5, coupon.getUser());
            statement.executeUpdate();
        }
    }

    @Override
    public  void modifier(coupon coupon) throws SQLException {
        String sql = "update coupon set nomSociete = ?, code = ? , valeur = ? ,dateExpiration = ? where code= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, coupon.getNomSociete());
        preparedStatement.setInt(2, coupon.getCode());
        preparedStatement.setInt(3, coupon.getValeur());
        preparedStatement.setString(4, coupon.getDateExpiration());
        preparedStatement.setInt(5, coupon.getCode());


        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int code) throws SQLException {
        String sql = "delete from coupon where code= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, code);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<coupon> recuperer() throws SQLException {
        String sql = "select * from coupon";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<coupon> coupons = new ArrayList<>();
        while (rs.next()) {
            coupon c = new coupon();

            c.setNomSociete(rs.getString("nomSociete"));
            c.setCode(rs.getInt("code"));
            c.setValeur(rs.getInt("valeur"));
            c.setDateExpiration(rs.getString("DateExpiration"));
            c.setUser(Integer.parseInt(rs.getString("user_id")));
            coupons.add(c);
            System.out.println(c);
        }
        return coupons;
    }

    @Override
    public coupon save(coupon entity) {
        return null;
    }

    @Override
    public void update(coupon entity) {

    }

    @Override
    public void supprimer(Exercice exercice) {

    }

    @Override
    public void supprimer(Cours Cours) {

    }

    @Override
    public void insererImage(coupon coupon) {

    }

    @Override
    public void ajoutercom(coupon coupon) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ObservableList<coupon> getAll() {
        return null;
    }

    @Override
    public coupon getById(int id) {
        return null;
    }

    @Override
    public boolean find(coupon coupon) {
        return false;
    }

    @Override
    public coupon findOne(coupon entity) {
        return null;
    }

    @Override
    public void supprimerUser(String nom) throws SQLException {

    }
}
