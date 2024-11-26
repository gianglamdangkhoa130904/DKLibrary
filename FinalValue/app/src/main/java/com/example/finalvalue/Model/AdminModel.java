package com.example.finalvalue.Model;

public class AdminModel {
    public int admin_ID;
    public String admin_username;
    public String admin_password;
    public String admin_role;

    public AdminModel(String admin_username, String admin_password, String admin_role) {
        this.admin_username = admin_username;
        this.admin_password = admin_password;
        this.admin_role = admin_role;
    }

    public AdminModel(int admin_ID, String admin_username, String admin_password, String admin_role) {
        this.admin_ID = admin_ID;
        this.admin_username = admin_username;
        this.admin_password = admin_password;
        this.admin_role = admin_role;
    }
}
