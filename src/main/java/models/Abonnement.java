package models;

public class Abonnement {
    private int id;
   private int montant;
    private int duree;
    private  String description;
    private Salle FK_idSalle;

   public Abonnement(int id,int montant,int duree ,String description , Salle FK_idSalle){
       this.id=id;
       this.montant=montant;
       this.duree=duree;
       this.description= description;
       this.FK_idSalle=FK_idSalle;
   }

    public Abonnement(int montant,int duree ,String description , Salle FK_idSalle){

        this.montant=montant;
        this.duree=duree;
        this.description= description;
        this.FK_idSalle=FK_idSalle;
    }
    public Abonnement(){

    }

    public int getId() {
        return id;
    }

    public int getMontant() {
        return montant;
    }

    public int getDuree() {
        return duree;
    }

    public String getDescription() {
        return description;
    }

    public Salle getFK_idSalle() {
        return FK_idSalle;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFK_idSalle(Salle FK_idSalle) {
        this.FK_idSalle = FK_idSalle;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + id +
                ", montant=" + montant +
                ", duree=" + duree +
                ", description='" + description + '\'' +
                ", FK_idSalle=" + FK_idSalle +
                '}';
    }
}

