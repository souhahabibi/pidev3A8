package models;

public class Materiel {
 private int id,quantite,prix,age,FK_idSalle;
    private String nom ,image;

 //constructeurs
 public Materiel(int id, String nom, int age, int quantite, int prix,int FK_idSalle,String image){
     this.id=id;
     this.nom = nom;
     this.age = age;
     this.quantite = quantite;
     this.prix = prix;
     this.FK_idSalle=FK_idSalle;
     this.image = image;
 }

    public Materiel(String nom, int age, int quantite, int prix,String image,int FK_idSalle){

        this.nom = nom;
        this.age = age;
        this.quantite = quantite;
        this.prix = prix;
        this.FK_idSalle=FK_idSalle;
        this.image = image;
    }

    public Materiel(){

    }
//getters et setters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
    }

    public int getQuantite() {
        return quantite;
    }

    public int getPrix() {
        return prix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getFK_idSalle() {
        return FK_idSalle;
    }

    public void setFK_idSalle(int FK_idSalle) {
        this.FK_idSalle = FK_idSalle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", prix=" + prix +
                ", age=" + age +
                ", FK_idSalle=" + FK_idSalle +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
