package com.esprit.models;

import java.util.Objects;

public class Exercice extends Cours {


    private  int idE;

    private int id ;
    private  String nom ;

    private  String etape ;
    private String image ;

    private  Cours cours;



    public String getEtape() {
        return etape;
    }

    public void setEtape(String etape) {
        this.etape = etape;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }


    public Exercice(int idE, Cours cours, String nom, String etape, String image) {
        this.idE = idE;
        this.cours = cours ;
        this.nom = nom;
        this.etape = etape;
        this.image = image;
    }



    public Exercice(int idE, int id, String nom, String etape, String image) {
        this.idE = idE;
        this.id = id;
        this.nom = nom;
        this.etape = etape;
        this.image = image;
    }


    public Exercice( int id, String nom, String etape, String image) {
        this.idE = idE;
        this.id = id;
        this.nom = nom;
        this.etape = etape;
        this.image = image;
    }


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exercice () {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercice exercice = (Exercice) o;
        return idE == exercice.idE && id == exercice.id && Objects.equals(nom, exercice.nom) && Objects.equals(etape, exercice.etape) && Objects.equals(image, exercice.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idE, id, nom, etape, image);
    }

    public Exercice(int idE, int id, String nom, String image) {
        this.idE = idE;
        this.id =id;
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
