package tn.esprit.services.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Meal;
import tn.esprit.entities.User;
import tn.esprit.services.IUserservice;
import tn.esprit.utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserImpl implements IUserservice {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    public UserImpl() {
        this.con = MyDatabase.getInstance().getConnection();
    }
    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public ObservableList<User> getAll() {
        String req ="select * from user";
        ObservableList<User> users = FXCollections.observableArrayList();
        User user = null;
        try {
            rs = con.createStatement().executeQuery(req);

            while (rs.next()){
                user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"));
                System.out.println(user);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }


    public User getById(int id) {
        String req = "select * from user where id=?";
        User user = null;
        try {
            preparedStatement = con.prepareStatement(req);
            preparedStatement.setInt(1,id);

            rs = preparedStatement.executeQuery();

            if(rs.next()){
                user = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
