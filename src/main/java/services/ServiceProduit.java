package services;


import javafx.collections.ObservableList;
import models.Cours;
import models.Exercice;
import tn.esprit.entites.Fournisseur;
import tn.esprit.entites.Produit;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements IService<tn.esprit.entites.Produit> {

    private Connection connection;

    public ServiceProduit(){
        connection= MyDatabase.getInstance().getConnection();
    }

    @Override
    public void modifier(Produit produit) throws SQLException {
        String sql = "UPDATE produit SET nom = ?, quantite = ?, cout = ?, date_expiration = ?, id_fournisseur = ?, description = ?, image = ? WHERE id_produit = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, produit.getNom());
        statement.setInt(2, produit.getQuantite());
        statement.setFloat(3, produit.getCout());
        statement.setDate(4, new java.sql.Date(produit.getDate_expiration().getTime()));
        statement.setInt(5, produit.getFournisseur().getId_fournisseur()); // Modification ici
        statement.setString(6, produit.getDescription());
        statement.setString(7, produit.getImage());
        statement.setInt(8, produit.getId_produit());

        statement.executeUpdate();
    }



    @Override
    public void ajouter(Produit produit) throws SQLException {
        String sql = "INSERT INTO produit(nom, quantite, cout, date_expiration, id_fournisseur, description, image) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, produit.getNom());
        statement.setInt(2, produit.getQuantite());
        statement.setFloat(3, produit.getCout());
        statement.setDate(4, new java.sql.Date(produit.getDate_expiration().getTime()));
        statement.setInt(5, produit.getFournisseur().getId_fournisseur()); // Modification ici
        statement.setString(6, produit.getDescription());
        statement.setString(7, produit.getImage());

        statement.executeUpdate();
    }

    @Override
    public void supprimer(int id_produit) throws SQLException {
        String sql= "DELETE FROM produit WHERE id_produit = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_produit);
        statement.executeUpdate();
    }

    @Override
    public void supprimerUser(String email) throws SQLException {

    }

    public List<Produit> afficher() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Produit p = new Produit();
            p.setId_produit(rs.getInt("id_produit"));
            p.setNom(rs.getString("nom"));
            p.setDescription(rs.getString("description"));
            p.setImage(rs.getString("image"));
            p.setQuantite(rs.getInt("quantite"));
            p.setCout(rs.getFloat("cout"));
            p.setDate_expiration(rs.getDate("date_expiration"));

            // Récupération de l'objet Fournisseur
            int idFournisseur = rs.getInt("id_fournisseur");
            Fournisseur fournisseur = retrouverFournisseurParId(idFournisseur);
            p.setFournisseur(fournisseur);

            produits.add(p);
        }
        return produits;
    }

    @Override
    public List<Produit> recuperer() throws SQLException {
        String sql = "SELECT * FROM produit";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Produit> produits = new ArrayList<>();
        while (rs.next()) {
            Produit p = new Produit();
            p.setId_produit(rs.getInt("id_produit"));
            p.setNom(rs.getString("nom"));
            p.setQuantite(rs.getInt("quantite"));
            p.setCout(rs.getFloat("cout"));
            p.setDescription(rs.getString("description"));
            p.setImage(rs.getString("image"));
            p.setDate_expiration(rs.getDate("date_expiration"));

            // Récupération de l'objet Fournisseur
            int idFournisseur = rs.getInt("id_fournisseur");
            Fournisseur fournisseur = retrouverFournisseurParId(idFournisseur);
            p.setFournisseur(fournisseur);

            produits.add(p);
        }
        return produits;
    }

    @Override
    public Produit save(Produit entity) {
        return null;
    }

    @Override
    public void update(Produit entity) {

    }

    @Override
    public void supprimer(Exercice exercice) {

    }

    @Override
    public void supprimer(Cours Cours) {

    }

    @Override
    public void insererImage(Produit produit) {

    }

    @Override
    public void ajoutercom(Produit produit) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ObservableList<Produit> getAll() {
        return null;
    }

    @Override
    public Produit getById(int id) {
        return null;
    }

    @Override
    public boolean find(Produit produit) {
        return false;
    }

    @Override
    public Produit findOne(Produit entity) {
        return null;
    }

    // Méthode utilitaire pour retrouver un fournisseur par son ID
    private Fournisseur retrouverFournisseurParId(int idFournisseur) throws SQLException {
        String sql = "SELECT * FROM fournisseur WHERE id_fournisseur = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idFournisseur);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            tn.esprit.entites.Fournisseur fournisseur = new Fournisseur();
            fournisseur.setId_fournisseur(rs.getInt("id_fournisseur"));
            fournisseur.setNom(rs.getString("nom"));
            fournisseur.setPrenom(rs.getString("prenom"));
            fournisseur.setType(rs.getString("type"));
            fournisseur.setNumero(rs.getInt("numero"));
            return fournisseur;
        }
        return null; // Si aucun fournisseur n'est trouvé avec cet ID
    }

}
