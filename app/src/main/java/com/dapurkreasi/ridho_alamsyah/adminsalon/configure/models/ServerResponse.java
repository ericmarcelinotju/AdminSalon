package com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models;

import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Promo;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Reservation;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.User;

import java.util.List;


public class ServerResponse {

    private String result;
    private String message;
    private User user;
    private Service[] services;
    private Reservation[] reservations;
    private Promo[] promos;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Service[] getServices(){ return services; }

    public Reservation[] getReservations(){return reservations;}

    public Promo[] getPromo(){return promos;}
}
