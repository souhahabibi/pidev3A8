package services;

import javafx.collections.ObservableList;
import models.Cours;
import models.Exercice;
import models.Ingredient;
import models.Meal;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    void supprimer(int id) throws SQLException;

    void supprimerUser(String email) throws SQLException;

    List<T> recuperer() throws SQLException;
    public T save(T entity);

    public void update(T entity);

    void supprimer(Exercice exercice);

    void supprimer(Cours Cours);

    public void insererImage(T t);

    void ajoutercom(T t);

    public void deleteById(int id);

    public ObservableList<T> getAll();

    public T getById(int id);

    public boolean find(T t);

    public T findOne(T entity);



}
