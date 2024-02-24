package com.esprit.models;

public class Exercice {


    private  int idE;
    private  String nom ;
    private String image ;

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Exercice () {}
    public Exercice(int idE, String nom, String image) {
        this.idE = idE;
        this.nom = nom;
        this.image = image;
    }


    @Override
    public String toString() {
        return "Exercice{" +
                "idE=" + idE +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                '}';
    }


}
