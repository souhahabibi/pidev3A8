package com.esprit.services;

import com.esprit.models.Cours;

import java.util.List;

public interface IService<T> {

    public void ajouter(T t);

    void ajoutercom(T t);

    public void modifier(T t);
    public void supprimer(T t);

    public void insererImage(T t);
    public List<T> afficher();




}
