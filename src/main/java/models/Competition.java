package models;

import java.sql.Date;

public class Competition {
    private String nom,description,videoURL;
    private Date date;
    private int capacite;
    private int fk_organisateur_id,id;

    public Competition(int id
                        ,String nom
                        ,String description
                        ,String videoURL
                        ,Date date
                        ,int capacite
                        ,int fk_organisateur_id)
    {
        this.id=id;
        this.nom=nom;
        this.description=description;
        this.videoURL=videoURL;
        this.date=date;
        this.capacite=capacite;
        this.fk_organisateur_id=fk_organisateur_id;
    }
    public Competition(String nom
            ,String description
            ,String videoURL
            ,Date date
            ,int capacite
            ,int fk_organisateur_id)
    {
        this.nom=nom;
        this.description=description;
        this.videoURL=videoURL;
        this.date=date;
        this.capacite=capacite;
        this.fk_organisateur_id=fk_organisateur_id;
    }
    public Competition(){}

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getFk_organisateur_id() {
        return fk_organisateur_id;
    }

    public void setFk_organisateur_id(int fk_organisateur_id) {
        this.fk_organisateur_id = fk_organisateur_id;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", date=" + date +
                ", capacite=" + capacite +
                '}';
    }
}
