package services;


import tn.esprit.entites.Produit;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements tn.esprit.services.IService<tn.esprit.entites.Produit> {

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
        statement.setInt(5, produit.getId_fournisseur());
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
        statement.setInt(5, produit.getId_fournisseur());
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
            p.setId_fournisseur(rs.getInt("id_fournisseur"));
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
            p.setId_fournisseur(rs.getInt("id_fournisseur"));
            p.setDescription(rs.getString("description"));
            p.setImage(rs.getString("image"));
            java.sql.Date sqlDate = rs.getDate("date_expiration");
            if (sqlDate != null) {
                p.setDate_expiration(new java.util.Date(sqlDate.getTime()));
            }
            produits.add(p);
        }
        return produits;
    }
}
