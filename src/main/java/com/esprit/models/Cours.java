package com.esprit.models;

import java.sql.Blob;

public class Cours {

    private int id;

    private  String image ;
    private String nom;

    private String description;


    private String niveau;


    public Cours(int id,String image, String nom, String description, String niveau) {
        this.id = id;
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.niveau = niveau;
    }

    public Cours(String image, String nom, String description) {
        this.image = image;
        this.nom = nom;
        this.description = description;
    }


    public Cours(String image, String nom, String description, String niveau) {
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.niveau = niveau;
    }

 public Cours(){

 }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }


    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }
}
