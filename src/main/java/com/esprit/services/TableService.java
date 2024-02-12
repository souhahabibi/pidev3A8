package com.esprit.services;
import com.esprit.models.Reservation;
import com.esprit.models.Tab;
import com.esprit.models.Zones;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class TableService implements IService<Tab> {

    private Connection connection;

    public TableService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Tab Tab) {
        String req = "INSERT into tables(table_id, zone_id,capacite_t) values ('" + Tab.getTable_id() + "', '" + Tab.getZone_id() + "', '" + Tab.getCapacit_t()  + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("table ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Tab Tab) {
        String req = "UPDATE tables set zone_id = '" + Tab.getZone_id() + "', capacite_t = '" + Tab.getCapacit_t() + "' where table_id = " + Tab.getTable_id() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Table modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Tab Tab) {
        String req = "DELETE from tables where table_id = " + Tab.getTable_id() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("table supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Tab> afficher() {
        List<Tab> Tab = new ArrayList<>();

        String req = "SELECT * from tables";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Tab.add(new Tab(rs.getInt("table_id"), rs.getInt("zone_id"), rs.getInt("capacite_t")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Tab;
    }
}