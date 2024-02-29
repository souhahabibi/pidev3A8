package models;

public class User {
    private int id,numero,poids,taille;
    private String nom, email,motDePasse,specialite,niveau,role;

    private String recommandation;

    public User(String nom, String email, String motDePasse, String specialite, String numero, String recommandation, String poids, String taille, String niveau, String role) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.specialite = specialite;
        this.numero = Integer.parseInt(numero);
        this.recommandation = recommandation;
        this.poids = Integer.parseInt(poids);
        this.taille = Integer.parseInt(taille);
        this.niveau = niveau;
        this.role = role;
    }

    public User(int id, int numero, int poids, int taille, String nom, String email, String motDePasse, String specialite, String niveau, String role, String recommandation) {
        this.id = id;
        this.numero = numero;
        this.poids = poids;
        this.taille = taille;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.specialite = specialite;
        this.niveau = niveau;
        this.role = role;
        this.recommandation = recommandation;
    }

    public String getRecommandation() {
        return recommandation;
    }

    public void setRecommandation(String recommandation) {
        this.recommandation = recommandation;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    @Override
    public String toString() {
        return "User{" +
                ", nom='" + nom + '\'' +
                "numero=" + numero +
                ", poids=" + poids +
                ", taille=" + taille +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", specialite='" + specialite + '\'' +
                ", niveau='" + niveau + '\'' +
                ", role='" + role + '\'' +
                ", recommandation=" + recommandation +
                '}';
    }
}