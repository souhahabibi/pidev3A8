package models;

public class Salle {
    private int id;
    private String nom,image;
    private  String description;
    private String lieu;

    public Salle(int id,String nom,String description ,String lieu,String image ){

        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.image=image;
    }
    public Salle(String nom,String description ,String lieu,String image){

        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.image=image;

    }
    public Salle(){

    }

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

    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Salle{" +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
