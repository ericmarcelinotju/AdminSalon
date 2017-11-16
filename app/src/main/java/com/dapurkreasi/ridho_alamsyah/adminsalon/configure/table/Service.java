package com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table;

/**
 * Created by Eric on 17/11/2017.
 */

public class Service extends Table{

    private int id_service;
    private String nama_service;
    private String jenis_service;
    private String waktu_service;
    private int harga_service;

    public int getId() {
        return id_service;
    }

    public void setId(int id) {
        this.id_service = id;
    }

    public String getName() {
        return nama_service;
    }

    public void setName(String name) {
        this.nama_service = name;
    }

    public String getType() {
        return jenis_service;
    }

    public void setType(String type) {
        this.jenis_service = type;
    }

    public String getDuration() {
        return waktu_service;
    }

    public void setDuration(String duration) {
        this.waktu_service = duration;
    }

    public int getPrice() {
        return harga_service;
    }

    public void setPrice(int price) {
        this.harga_service = price;
    }
}
