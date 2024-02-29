package models;

import javafx.scene.control.Tooltip;

public class coupon {
    private int id;

    private String nomSociete;
    private int code;
    private int valeur;
    private String dateExpiration;

    public coupon(int id ,String nomSociete,int code, int valeur, String dateExpiration) {
        this.id=id;
        this.nomSociete = nomSociete;
        this.code = code;
        this.valeur = valeur;
        this.dateExpiration = dateExpiration;
    }

    public coupon() {

    }

    public coupon(String nomSociete, int code, int valeur, String dateExpiration) {
        this.nomSociete = nomSociete;
        this.code = code;
        this.valeur = valeur;
        this.dateExpiration = dateExpiration;
    }




    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

   
   

