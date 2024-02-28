package com.esprit.tests;

import com.esprit.models.Cours;
import com.esprit.services.CoursService;

public class MainProg {

    public static void main(String[] args) {

        //******Cours*****//

        CoursService zs = new CoursService() ;
        zs.ajouter(new Cours("photo","jambe","exercice","normale"));
        //zs.modifier(new Cours());
        //zs.supprimer(new Cours());
        //System.out.println(zs.afficher());



    }
}
