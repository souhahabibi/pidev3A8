package com.esprit.services;
import com.esprit.models.Zones;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class ZonesService implements IService<Zones> {

    private Connection connection;

    public ZonesService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Zones Zones) {
        String req = "INSERT into zones(nom, description,capacity) values ('" + Zones.getNom() + "', '" + Zones.getDescription() + "', '" + Zones.getCapacity() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Zones ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*@Override
    public void modifier(Zones Zones) {
        String req = "UPDATE zones set nom = '" + Zones.getNom() + "', description = '" + Zones.getDescription()+ "', capacity = '" + Zones.getCapacity() + "' where zone_id = " + Zones.getZone_id() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Zones modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void supprimer(Zones Zones) {
        String req = "DELETE from zones where zone_id = " + Zones.getZone_id() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Zone supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Zones> afficher() {
        List<Zones> Zones = new ArrayList<>();

        String req = "SELECT * from zones";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Zones.add(new Zones(rs.getInt("zone_id"), rs.getString("nom"), rs.getString("description"), rs.getInt("capacity")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Zones;
    }*/
}
