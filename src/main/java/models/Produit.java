package tn.esprit.entites;

import java.util.Date;


    public class Produit {
        private int id_produit;
        private String nom;
        private int quantite;
        private float cout;
        private Date date_expiration;
        private int id_fournisseur; // Clé étrangère
        private String description;
        private String image;

        public Produit() {
        }

        public Produit(int id_produit, String nom, int quantite, float cout, Date date_expiration, int id_fournisseur, String description, String image) {
            this.id_produit = id_produit;
            this.nom = nom;
            this.quantite = quantite;
            this.cout = cout;
            this.date_expiration = date_expiration;
            this.id_fournisseur = id_fournisseur;
            this.description = description;
            this.image = image;
        }

        public int getId_produit() {
            return id_produit;
        }

        public void setId_produit(int id_produit) {
            this.id_produit = id_produit;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public int getQuantite() {
            return quantite;
        }

        public void setQuantite(int quantite) {
            this.quantite = quantite;
        }

        public float getCout() {
            return cout;
        }

        public void setCout(float cout) {
            this.cout = cout;
        }

        public Date getDate_expiration() {
            return date_expiration;
        }

        public void setDate_expiration(Date date_expiration) {
            this.date_expiration = date_expiration;
        }

        public int getId_fournisseur() {
            return id_fournisseur;
        }

        public void setId_fournisseur(int id_fournisseur) {
            this.id_fournisseur = id_fournisseur;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "Produit{" +
                    "id_produit=" + id_produit +
                    ", nom='" + nom + '\'' +
                    ", quantite=" + quantite +
                    ", cout=" + cout +
                    ", date_expiration=" + date_expiration +
                    ", id_fournisseur=" + id_fournisseur +
                    ", description='" + description + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
}
