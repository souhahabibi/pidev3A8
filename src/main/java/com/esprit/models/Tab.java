package com.esprit.models;

public class Tab {
    private int table_id;
    private int zone_id;
    private int capacit_t;

    public Tab(int table_id, int zone_id, int capacit_t) {
        this.table_id = table_id;
        this.zone_id = zone_id;
        this.capacit_t = capacit_t;
    }

    public Tab(int zone_id, int capacit_t) {
        this.zone_id = zone_id;
        this.capacit_t = capacit_t;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getZone_id() {
        return zone_id;
    }

    public void setZone_id(int zone_id) {
        this.zone_id = zone_id;
    }

    public int getCapacit_t() {
        return capacit_t;
    }

    public void setCapacit_t(int capacit_t) {
        this.capacit_t = capacit_t;
    }

    @Override
    public String toString() {
        return "Tab{" +
                "table_id=" + table_id +
                ", zone_id=" + zone_id +
                ", capacit_t=" + capacit_t +
                '}';
    }
}
