package com.esprit.services;
import com.esprit.models.Reservation;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class ReservationService implements IService<Reservation> {

    private Connection connection;

    public ReservationService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation Reservation) {
        String req = "INSERT into reservation(id_C, zone,date,table_id) values ('" + Reservation.getId_C() + "', '" + Reservation.getZone() + "', '" + Reservation.getDate() + "','"+ Reservation.getTable_id() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Reservation ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}