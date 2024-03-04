package models;

import javafx.scene.control.Tooltip;

public class coupon {
    private int id;

    private String nomSociete;
    private int code;
    private int valeur;
    private String dateExpiration;
    private int user;


    public coupon(int id ,String nomSociete,int code, int valeur, String dateExpiration,int user) {
        this.id=id;
        this.nomSociete = nomSociete;
        this.code = code;
        this.valeur = valeur;
        this.dateExpiration = dateExpiration;
        this.user=user;
    }

    public coupon() {

    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public coupon(String nomSociete, int code, int valeur, String dateExpiration) {
        this.nomSociete = nomSociete;
        this.code = code;
        this.valeur = valeur;
        this.dateExpiration = dateExpiration;
    }

    public coupon(String nomSociete,int code, int valeur, String dateExpiration, int user) {
        this.nomSociete = nomSociete;
        this.code = code;
        this.valeur = valeur;
        this.dateExpiration = dateExpiration;
        this.user=user;
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

   
   

