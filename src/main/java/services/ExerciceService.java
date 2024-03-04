package services;


import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.Cours;
import models.Exercice;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciceService implements IService<Exercice>{

    private Connection connection;

    public ExerciceService() {
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Exercice exercice) {
        String req = "INSERT into exercice(id, nom, etape, image) values (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setInt(1, exercice.getId());
            pstmt.setString(2, exercice.getNom());
            pstmt.setString(3, exercice.getEtape());
            pstmt.setString(4, exercice.getImage());
            pstmt.executeUpdate();
            System.out.println("Exercice ajoutée !");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("L'Exercice a été ajouté avec succès !");
            alert.showAndWait();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajoutercom(Exercice exercice) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public ObservableList<Exercice> getAll() {
        return null;
    }

    @Override
    public Exercice getById(int id) {
        return null;
    }

    @Override
    public boolean find(Exercice exercice) {
        return false;
    }

    @Override
    public Exercice findOne(Exercice entity) {
        return null;
    }


    public boolean ExerciceExiste(Exercice nouveauExercice) {
        String req = "SELECT COUNT(*) FROM exercice WHERE idE = ? AND nom = ? AND etape = ? AND image = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, nouveauExercice.getIdE());
            ps.setString(2, nouveauExercice.getNom());
            ps.setString(3, nouveauExercice.getEtape());
            ps.setString(4, nouveauExercice.getImage());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    // Si le nombre d'exercices correspondants est supérieur à 0, l'exercice existe déjà
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // Si aucun exercice existant ne correspond au nouvel exercice, l'exercice n'existe pas
        return false;
    }


    @Override
    public void modifier(Exercice exercice) {
        String req = "UPDATE exercice SET image = ?, nom = ?, id = ?, etape = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(req);
            pstmt.setString(1, exercice.getImage());
            pstmt.setString(2, exercice.getNom());
            pstmt.setInt(3, exercice.getId());
            pstmt.setString(4, exercice.getEtape());
            pstmt.setInt(5, exercice.getId());

            pstmt.executeUpdate();
            System.out.println("modifiée!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Le cours a été modifié avec succès !");
            alert.showAndWait();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public void supprimerUser(String email) throws SQLException {

    }

    @Override
    public List<Exercice> recuperer() throws SQLException {
        return null;
    }

    @Override
    public Exercice save(Exercice entity) {
        return null;
    }

    @Override
    public void update(Exercice entity) {

    }


    @Override
    public void supprimer(Exercice exercice) {

        String req = "DELETE from exercice where id = " + exercice.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Exercice supprmiée !");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("L'Exercice a été supprimer avec succès !");
            alert.showAndWait();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(Cours Cours) {

    }

    @Override
    public void insererImage(Exercice exercice) {

    }


    public List<Exercice> getExerciceByCoursId(int id) {
        // Exercice exercice = null;
        List<Exercice> exercice = new ArrayList<>();

        String req = "SELECT * FROM exercice WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercice;
    }




    public List<Exercice> afficher() {

        List<Exercice> exercice = new ArrayList<>();

        String req = "SELECT * from exercice";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                exercice.add(new Exercice(rs.getInt("id"), rs.getString("nom"), rs.getString("etape"),rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return exercice;
    }


    public List<Exercice> afficher2(int id) {
        List<Exercice> exercice = new ArrayList<>();
        String req = "SELECT * from exercice , cours where cours.id=exercice.id AND cours.id=" + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                exercice.add(new Exercice(rs.getInt("idE"), rs.getString("nom"), rs.getString("etape"),rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return exercice;
    }

    public List<Exercice> afficher3(int courseId) {
        List<Exercice> exercice = new ArrayList<>();

        String req = "SELECT * FROM exercice JOIN cours ON cours.id = exercice.id ORDER BY exercice.id ASC LIMIT 1";


        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                exercice.add(new Exercice( rs.getString("nom"), rs.getString("etape"),rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return exercice;
    }













}