package com.esprit.models;

public class Zones {

    private int zone_id;
    private String nom;
    private String description;
    private int capacity;

    public Zones(int zone_id, String nom, String description, int capacity) {
        this.zone_id = zone_id;
        this.nom = nom;
        this.description = description;
        this.capacity = capacity;
    }

    public Zones(String nom, String description, int capacity) {
        this.nom = nom;
        this.description = description;
        this.capacity = capacity;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Zones{" +
                "zone_id=" + zone_id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
