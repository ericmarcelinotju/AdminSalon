package com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table;

/**
 * Created by Ridho_Alamsyah on 20/06/2017.
 */

public class User extends Table{

    private String name;
    private String email;
    private String unique_id;
    private String password;
    private String phone;
    private String old_password;
    private String new_password;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

}
