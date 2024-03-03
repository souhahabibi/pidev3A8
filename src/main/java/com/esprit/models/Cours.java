package com.esprit.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Cours {

    private int id;

    private  String image ;
    private String nom;

    private String description;

    private  String commentaire;
    private LocalDate planning; // Changer le type de String à LocalDate

    private List<Exercice> exercice;

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

    public Cours(String commentaire) {
        this.commentaire = commentaire;
    }

    public Cours(){

    }

    // Ajouter un constructeur qui prend en paramètre le planning
    public Cours(int id, String image, String nom, String description, String niveau, LocalDate planning) {
        this.id = id;
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.niveau = niveau;
        this.planning = planning;
    }

    public LocalDate getPlanning() {
        return planning;
    }

    public void setPlanning(LocalDate planning) {
        this.planning = planning;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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


    public List<Exercice> getExercice() {
        return exercice;
    }

    public void setExercice(List<Exercice> exercice) {
        this.exercice = exercice;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", niveau='" + niveau + '\'' +
                ", planning=" + planning + // Inclure le planning dans la représentation textuelle
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cours cours = (Cours) o;
        return id == cours.id && Objects.equals(image, cours.image) && Objects.equals(nom, cours.nom) && Objects.equals(description, cours.description) && Objects.equals(niveau, cours.niveau) && Objects.equals(planning, cours.planning); // Prendre en compte le planning dans la comparaison
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, nom, description, niveau, planning); // Prendre en compte le planning dans le calcul du hash
    }


}
