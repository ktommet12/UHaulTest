package com.kyletommet.uhaultest.User;

/**
 * Created by Kyle Tommet on 4/14/2017.
 */

public class User {
    private int id;
    private String name, username, phone;

    public User(String name, String username, String phone){
        this.name = name;
        this.username = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }
}
