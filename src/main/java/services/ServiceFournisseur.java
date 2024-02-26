package tn.esprit.services;

import tn.esprit.entites.Fournisseur;
import tn.esprit.entites.Produit;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFournisseur implements tn.esprit.services.IService<Fournisseur> {

    private Connection connection;
    // Constructeur de la classe ServiceFournisseur
    public ServiceFournisseur() {
        // Initialisation de la connexion
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Fournisseur fournisseur) throws SQLException {
        if (fournisseur.getNom() == null || fournisseur.getPrenom() == null || fournisseur.getType() == null) {
            throw new IllegalArgumentException("Le nom, le prénom et le type du fournisseur ne peuvent pas être null.");
        }

        String sql = "INSERT INTO fournisseur (nom, prenom, numero, type) VALUES (?, ?, ?, ?)";
        // PreparedStatement permet de créer des requêtes SQL
        PreparedStatement statement = connection.prepareStatement(sql);
        // Attribution des valeurs aux paramètres de la requête
        // statement permet d exécuter des requêtes SQL
        statement.setString(1, fournisseur.getNom());
        statement.setString(2, fournisseur.getPrenom());
        statement.setInt(3, fournisseur.getNumero());
        statement.setString(4, fournisseur.getType());
        // Exécution de la requête SQL
        statement.executeUpdate();
    }


    @Override
    public void modifier(Fournisseur fournisseur) throws SQLException {
        String sql = "UPDATE fournisseur SET nom = ?, prenom = ?, numero = ?, type = ? WHERE id_fournisseur = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, fournisseur.getNom());
        preparedStatement.setString(2, fournisseur.getPrenom());
        preparedStatement.setInt(3, fournisseur.getNumero());
        preparedStatement.setString(4, fournisseur.getType());
        preparedStatement.setInt(5, fournisseur.getId_fournisseur());
        preparedStatement.executeUpdate();
    }


    @Override
    public void supprimer(int id_fournisseur) throws SQLException {
        String sql = "DELETE FROM fournisseur WHERE id_fournisseur = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id_fournisseur);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Fournisseur> afficher() throws SQLException {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String sql = "SELECT * FROM fournisseur";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Fournisseur f = new Fournisseur();
            f.setId_fournisseur(rs.getInt("id_fournisseur"));
            f.setNom(rs.getString("nom"));
            f.setPrenom(rs.getString("prenom"));
            f.setNumero(rs.getInt("numero"));
            f.setType(rs.getString("type"));
            fournisseurs.add(f);
        }
        return fournisseurs;
    }

    @Override
    public List<Fournisseur> recuperer() throws SQLException {
        String sql = "SELECT * FROM fournisseur";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Fournisseur> fournisseurs = new ArrayList<>();
        while (rs.next()) {
            Fournisseur o = new Fournisseur();
            o.setId_fournisseur(rs.getInt("id_fournisseur"));
            o.setNom(rs.getString("nom"));
            o.setPrenom(rs.getString("prenom"));
            o.setNumero(rs.getInt("numero"));
            o.setType(rs.getString("type"));
            fournisseurs.add(o);
        }
        return fournisseurs;
    }

    // Ajoutez cette méthode à votre classe ServiceFournisseur
    public String recupererNomFournisseurParId(int id) throws SQLException {
        String nomFournisseur = null;
        String sql = "SELECT nom FROM fournisseur WHERE id_fournisseur = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            nomFournisseur = rs.getString("nom");
        }
        return nomFournisseur;
    }


}
