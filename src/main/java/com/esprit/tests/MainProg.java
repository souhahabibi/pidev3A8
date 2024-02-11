package com.esprit.tests;

import com.esprit.models.Reservation;
import com.esprit.models.Zones;
import com.esprit.services.ReservationService;
import com.esprit.services.ZonesService;
import com.esprit.utils.DataSource;

public class MainProg {

    public static void main(String[] args) {
        //******ZONES*****//
        //ZonesService zs = new ZonesService();
   //zs.ajouter(new Zones("cafe", "justeboisson",15));

        //zs.modifier(new Zones(1, "resto", "plats",20));

        //zs.supprimer(new Zones(3, "cafe", "justeboisson",15));
        //System.out.println(zs.afficher());
        //******RESERVATION*****//

        ReservationService rs = new ReservationService();
        rs.ajouter(new Reservation(4, "cafe","lundi",4));
    }
}
