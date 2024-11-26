package com.example.finalvalue.Model;

import android.graphics.Bitmap;

public class CustomerModel {
    public int id;
    public String name;
    public String email;
    public String date_birth;
    public String username;
    public String password;
    public Bitmap imageCus;
    public CustomerModel(String name, String email, String date_birth, String username, String password, Bitmap imageCus){
        this.name = name;
        this.email = email;
        this.date_birth = date_birth;
        this.username = username;
        this.password = password;
        this.imageCus = imageCus;
    }

    public CustomerModel(int id, String name, String email, String date_birth, String username, String password, Bitmap imageCus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date_birth = date_birth;
        this.username = username;
        this.password = password;
        this.imageCus = imageCus;
    }

    public CustomerModel(CustomerModel a){
        this.id = a.id;
        this.name = a.name;
        this.email = a.email;
        this.date_birth = a.date_birth;
        this.username = a.username;
        this.password = a.password;
    }

}
