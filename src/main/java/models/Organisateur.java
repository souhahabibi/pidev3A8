package models;

public class Organisateur {
    private int id;
    private String nom,numero;

    public Organisateur(int id,String nom,String numero)
    {
        this.id=id;
        this.nom=nom;
        this.numero=numero;
    }
    public Organisateur(String nom,String numero)
    {
        this.nom=nom;
        this.numero=numero;
    }
    public Organisateur(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "nom='" + nom + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
