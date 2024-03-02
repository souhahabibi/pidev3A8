package test;

import services.ServiceProduit;
import tn.esprit.entites.Fournisseur;
import tn.esprit.entites.Fournisseur;
import tn.esprit.entites.Produit;
import tn.esprit.services.ServiceFournisseur;



import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {


//        System.out.println(MyDataBase.getInstance());
//        System.out.println(MyDataBase.getInstance());

        ServiceFournisseur sp = new ServiceFournisseur();

        // try {
        // Ajout d'un fournisseur sans spécifier l'ID
        //  sp.ajouter(new Fournisseur("akram", "louati", 20185787,"vetement"));
        // } catch (SQLException e) {
        //   System.out.println(e.getMessage());
        // }

        try {
            // Modification du fournisseur avec l'ID correct
            sp.modifier(new Fournisseur(11,"akram", "louati", 20185787,"vetement"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            sp.supprimer(11);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(sp.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Création de l'objet service pour les produits
        ServiceProduit serviceProduit = new ServiceProduit();

        // Ajout d'un produit
        Produit nouveauProduit = new Produit();
        nouveauProduit.setNom("lolla");
        nouveauProduit.setQuantite(10);
        nouveauProduit.setCout(20.5f);
        // Saisie de la date d'expiration
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez la date d'expiration du produit (format dd/MM/yyyy) : ");
        String dateExpirationStr = scanner.nextLine();
        try {
            Date dateExpiration = dateFormat.parse(dateExpirationStr);
            nouveauProduit.setDate_expiration(dateExpiration);
        } catch (ParseException e) {
            System.out.println("Format de date invalide, veuillez entrer la date au format dd/MM/yyyy");
            return;
        }
        //nouveauProduit.setId_fournisseur(5); // ID du fournisseur auquel ce produit appartient
        serviceProduit.ajouter(nouveauProduit);
        // Modification d'un produit existant
       // Produit produitAModifier = serviceProduit.afficher().get(0); // par exemple, le premier produit
       // produitAModifier.setNom("proteine");
       // serviceProduit.modifier(produitAModifier);
        // Suppression d'un produit
        //int idProduitASupprimer = 1; // ID du produit à supprimer
       // serviceProduit.supprimer(idProduitASupprimer);
        // Affichage des produits
        List<Produit> produits = serviceProduit.afficher();
        for (Produit produit : produits) {
            System.out.println(produit);
        }

    }}