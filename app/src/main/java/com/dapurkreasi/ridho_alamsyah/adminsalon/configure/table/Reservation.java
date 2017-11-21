package com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table;

import java.util.Date;

/**
 * Created by Jefry on 11/19/2017.
 */

public class Reservation extends Table{

    private int id_reservation;
    private String user_id;
    private int id_service;
    private Date date_reservation;
    private String reservation_status;

    //----------------------------------------------
    private String nama_service;
    private String name;

    public String getNamaService() {
        return nama_service;
    }

    public void setNama_service(String nama_service) {
        this.nama_service = nama_service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public void setReservation_status(String reservation_status) {
        this.reservation_status = reservation_status;
    }

    public int getId() {
        return id_reservation;
    }

    public String getUser() {
        return user_id;
    }

    public int getService() {
        return id_service;
    }

    public Date getDate() {
        return date_reservation;
    }

    public String getStatus() {
        return reservation_status;
    }
}
