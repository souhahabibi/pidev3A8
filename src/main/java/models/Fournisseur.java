package tn.esprit.entites;

public class Fournisseur {

    private int id_fournisseur;
    private String nom;
    private String prenom;
    private int numero;
    private String type;

    public  Fournisseur() {
    }

    public  Fournisseur(int id_fournisseur, String nom, String prenom, int numero, String type) {
        this.id_fournisseur = id_fournisseur;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.type=type;
    }

    public  Fournisseur(String nom, String prenom, int numero, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.type=type;
    }

    public int getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur(int id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Fournisseur{" +

                ", nom='" + nom + '\'' +


                '}';
    }

    public void setType(String type) {
        this.type = type;
    }
}
