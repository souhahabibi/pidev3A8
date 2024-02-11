package com.esprit.models;

public class Reservation {

    private int id_R;
    private int  id_C;
    private String zone;
    private String date;
    private int table_id;

    public Reservation(int id_R, int id_C, String zone, String date, int table_id) {
        this.id_R = id_R;
        this.id_C = id_C;
        this.zone = zone;
        this.date = date;
        this.table_id = table_id;
    }

    public Reservation(int id_C, String zone, String date, int table_id) {
        this.id_C = id_C;
        this.zone = zone;
        this.date = date;
        this.table_id = table_id;
    }

    public int getId_R() {
        return id_R;
    }

    public void setId_R(int id_R) {
        this.id_R = id_R;
    }

    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_R=" + id_R +
                ", id_C=" + id_C +
                ", zone='" + zone + '\'' +
                ", date='" + date + '\'' +
                ", table_id='" + table_id + '\'' +
                '}';
    }
}
