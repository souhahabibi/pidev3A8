package services;


import models.User;
import test.MainFX;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private Connection connection;


    public UserService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO user (nom, email, motDePasse, specialite, numero, recommandation, poids, taille, niveau, role,is_verified) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getMotDePasse());
        preparedStatement.setString(4, user.getSpecialite());
        preparedStatement.setInt(5, user.getNumero());
        preparedStatement.setString(6, user.getRecommandation());
        preparedStatement.setInt(7, user.getPoids());
        preparedStatement.setInt(8, user.getTaille());
        preparedStatement.setString(9, user.getNiveau());
        preparedStatement.setString(10, user.getRole());
        preparedStatement.setBoolean(11, false);


        preparedStatement.executeUpdate();
    }


    @Override
    public void modifier(User user) throws SQLException {
        String sql = "update user set nom = ?, email = ?, motDePasse = ?,specialite = ?,numero = ?,recommandation = ?,poids =? , taille=? , niveau = ?,role=? where email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getMotDePasse());
        preparedStatement.setString(4, user.getSpecialite());
        preparedStatement.setInt(5, user.getNumero());
        preparedStatement.setString(6, user.getRecommandation());
        preparedStatement.setInt(7, user.getPoids());
        preparedStatement.setInt(8, user.getTaille());
        preparedStatement.setString(9, user.getNiveau());
        preparedStatement.setString(10, user.getRole());
        preparedStatement.setString(11,user.getEmail());



        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }


    @Override
    public void supprimerUser (String email) throws SQLException {
        String sql = "delete from user where email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<User> recuperer() throws SQLException {
        String sql = "select * from user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setEmail(rs.getString("email"));
            u.setMotDePasse(rs.getString("MotDePasse"));
            u.setSpecialite(rs.getString("specialite"));
            u.setNumero(rs.getInt("numero"));
            u.setRecommandation(rs.getString("recommandation"));
            u.setPoids(rs.getInt("poids"));
            u.setTaille(rs.getInt("taille"));
            u.setNiveau(rs.getString("niveau"));
            u.setRole(rs.getString("role"));
            users.add(u);
            System.out.println(u.getId());
        }
        return users;
    }

    public void mupdate(User t) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE user SET nom=? ,email=?,motDePasse=? WHERE id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, t.getNom());
            stmt.setString(2, t.getEmail());
            stmt.setString(3, t.getMotDePasse());
            stmt.setInt(4, t.getId());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }
    public User getById() {
        String req = "select * from user where id =" + MainFX.loggedInID;
        User p = new User();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            rs = st.executeQuery(req);
            // while(rs.next()){
            rs.next();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setEmail(rs.getString("email"));
            p.setMotDePasse(rs.getString("motDePasse"));
            p.setRole(rs.getString("role"));
            p.setNumero(rs.getInt("numero"));
            p.setPoids(rs.getInt("poids"));
            p.setRecommandation(rs.getString("recommandation"));
            p.setTaille(rs.getInt("taille"));
            p.setNiveau(rs.getString("niveau"));
            p.setSpecialite(rs.getString("specialite"));
            p.setIs_verified(rs.getBoolean("is_verified"));


            //}
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return p;
    }
    public User login(String email, String motDePasse) {
        String req = "SELECT * FROM user WHERE email = " + "email" + " AND motDePasse = " + "motDePasse";
        User p = new User();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            rs = st.executeQuery(req);

            rs.next();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setEmail(rs.getString("email"));
            p.setMotDePasse(rs.getString("motDePasse"));
            p.setRole(rs.getString("role"));
            p.setNumero(rs.getInt("numero"));
            p.setPoids(rs.getInt("poids"));
            p.setRecommandation(rs.getString("recommandation"));
            p.setTaille(rs.getInt("taille"));
            p.setNiveau(rs.getString("niveau"));
            p.setSpecialite(rs.getString("specialite"));

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return p;
    }

    public boolean getUserBy(String email, String pwdId) {
        String requete = "SELECT id,motDePasse FROM user"
                + " WHERE ( email = ? ) ";
        try {
            PreparedStatement ps = connection.prepareStatement(requete);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String pwdBD = rs.getString(2);
                // (aa.hashagePWD(pwdId).equals(pwdBD))
                if (pwdId.equals(pwdBD)) {
                    int idUser = rs.getInt(1);
                    MainFX.loggedInID = idUser;
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public boolean Password(String password) {
        PreparedStatement stmt;

        try {

            String sql = "UPDATE  user SET motDePasse= '"+password+"' WHERE ( id = ? ) ";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ""+MainFX.RestpasswordID);

            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
    public boolean Nullcodemail() {
        PreparedStatement stmt;

        try {

            String sql = "UPDATE  user SET mailcode= 'NULL' WHERE ( id = ? ) ";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, ""+MainFX.RestpasswordID);

            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
}

