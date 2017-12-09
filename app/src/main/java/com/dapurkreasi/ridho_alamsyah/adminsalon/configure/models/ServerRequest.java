package com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models;

import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Table;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.User;

public class ServerRequest {

    private String operation;
    private Table table;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
