package com.esprit.services;
import com.esprit.models.Cours;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;


public class CoursService implements IService<Cours> {

    private Connection connection;

    private Cours cours;

    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Cours cours) {
        String req = "INSERT INTO cours (image, nom, description, niveau, commentaire) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, cours.getImage());
            preparedStatement.setString(2, cours.getNom());
            preparedStatement.setString(3, cours.getDescription());
            preparedStatement.setString(4, cours.getNiveau());
            preparedStatement.setString(5, "");

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cours ajoutée !");
            } else {
                System.out.println("Erreur lors de l'ajout du cours !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public Cours getCoursById(int id) {
        String req = "SELECT planning, nom FROM cours WHERE id = ?";
        Cours cours = null;

        try {
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cours = new Cours();
                Date date = rs.getDate("planning");
                if (date != null) {
                    cours.setPlanning(((java.sql.Date) date).toLocalDate());
                }
                cours.setNom(rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cours;
    }








    @Override
    public void ajoutercom(Cours cours) {
        String req = "UPDATE cours SET commentaire = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setString(1, cours.getCommentaire());
            pstmt.setInt(2, cours.getId());
            pstmt.executeUpdate();
            System.out.println("Commentaire ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    @Override
    public void modifier(Cours Cours) {
        String req = "UPDATE Cours SET image = ?, nom = ?, description = ?, niveau = ? WHERE id = ?;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setString(1, Cours.getImage());
            pstmt.setString(2, Cours.getNom());
            pstmt.setString(3, Cours.getDescription());
            pstmt.setString(4, Cours.getNiveau());
            pstmt.setInt(5, Cours.getId());
            pstmt.executeUpdate();
            System.out.println("Cours modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    @Override
    public void supprimer(Cours Cours) {
        String req = "DELETE * from Cours where id = " + Cours.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public boolean coursExiste(Cours nouveauCours) {
        String req = "SELECT COUNT(*) FROM cours WHERE image = ? AND nom = ? AND description = ? AND niveau = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, nouveauCours.getImage());
            ps.setString(2, nouveauCours.getNom());
            ps.setString(3, nouveauCours.getDescription());
            ps.setString(4, nouveauCours.getNiveau());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    // Si le nombre de cours correspondants est supérieur à 0, le cours existe déjà
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // Si aucun cours existant ne correspond au nouveau cours, le cours n'existe pas
        return false;
    }

    @Override
    public void insererImage(Cours cours) {
        String req = "INSERT into cours(image, nom, description, niveau) values ('" + cours.getImage() + "','" + cours.getNom() + "','" + cours.getDescription() + "','" + cours.getNiveau() + "')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


        @Override
    public List<Cours> afficher() {
        List<Cours> Cours = new ArrayList<>();

        String req = "SELECT * from Cours";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Cours.add(new Cours(rs.getInt("id"), rs.getString("image"), rs.getString("nom"), rs.getString("description"), rs.getString("niveau")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Cours;
    }


    public Integer getCoursId() {
        Integer id = null;
        String req = "SELECT C.id FROM cours C INNER JOIN exercice E ON C.id = E.id";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return id;

}}
