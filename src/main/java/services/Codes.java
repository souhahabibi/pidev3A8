/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import models.User;
import test.MainFX;
import utils.MyDatabase;

public class Codes {
  
    Connection cnx = MyDatabase.getInstance().getConnection();
    
    
    public String envoyerCode(int id){
        Random r = new Random();
        return ""+r.nextInt(100)+id+r.nextInt(100);
         
       //return ;
    }
     public boolean update(String pwd,int id) throws SQLException {
        Statement st = cnx.createStatement();
        String qry = "UPDATE user SET motDePasse = '"+pwd+"' WHERE id = "+ MainFX.loggedInID;
         
        try {
            if (st.executeUpdate(qry) > 0) {
                return true;
            }
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }
        return false;
    }
      public boolean getUserBy(String email) {
        String requete = "SELECT id,motDePasse FROM user"
                + " WHERE ( email = ? ) ";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                // (aa.hashagePWD(pwdId).equals(pwdBD)) 
               
                    int idUser = rs.getInt(1);
                MainFX.RestpasswordID = idUser;
                    return true;
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
      public boolean codemail(String code,String email) {
          PreparedStatement stmt;
        
        try {
            
           String sql = "UPDATE  user SET mailcode= '"+code+"' WHERE ( email = ? ) ";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, email);
           
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
     public User getByI() {
        String req = "select * from user where id =" + MainFX.RestpasswordID;
        User p = new User();
        try {
            Statement st = cnx.createStatement();
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
            p.setMailcode(rs.getString("mailcode"));
            p.setIs_verified(rs.getBoolean("is_verified"));


            //}  
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return p;
    }
       public boolean Nullcodemail() {
          PreparedStatement stmt;
        
        try {
            
           String sql = "UPDATE  user SET mailcode= 'NULL' WHERE ( id = ? ) ";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, ""+MainFX.RestpasswordID);
           
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
       public boolean Verif() {
          PreparedStatement stmt;
        
        try {
            
           String sql = "UPDATE  user SET is_verifIed=" +true +" WHERE ( id = ? ) ";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1,""+MainFX.RestpasswordID );
           //""+RestpasswordID
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
}  

        
    
      

