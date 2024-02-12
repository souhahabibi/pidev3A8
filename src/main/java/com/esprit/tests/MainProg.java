package com.esprit.tests;

import com.esprit.models.Reservation;
import com.esprit.models.Tab;
import com.esprit.models.Zones;
import com.esprit.services.ReservationService;
import com.esprit.services.TableService;
import com.esprit.services.ZonesService;
import com.esprit.utils.DataSource;

public class MainProg {

    public static void main(String[] args) {

        //******ZONES*****//

        //ZonesService zs = new ZonesService() ;
        //zs.ajouter(new Zones("cafe", "justeboisson",15));
        //zs.modifier(new Zones(1, "resto", "plats",20));
        //zs.supprimer(new Zones(3, "cafe", "justeboisson",15));
        //System.out.println(zs.afficher());


        //******RESERVATION*****//

        //ReservationService rs = new ReservationService();
       // rs.ajouter(new Reservation(4, "cafe","lundi",4));
        //rs.modifier(new Reservation(1, 7, "cafe","mardi",5));
        //rs.supprimer(new Reservation(1, 7, "cafe","mardi",5));
       // System.out.println(rs.afficher());


        //******TABLE*****//

        TableService ts = new TableService();
        //ts.ajouter(new Tab(4,4));
        //ts.modifier(new Tab(1,8,6));
        //ts.supprimer(new Tab(1,8,6));
        //System.out.println(ts.afficher());

    }
}
