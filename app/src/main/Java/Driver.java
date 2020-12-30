package com.example.collegebustrack;

public class Driver {
    private String id,name, bus, phone, email, password;

    public Driver() {
    }

    public Driver(String id, String name, String bus, String phone, String email, String password) {
        this.id = id;
        this.name = name;
        this.bus = bus;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public Driver(String password,String phone){
        this.password=password;
        this.phone=phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBus() {
        return bus;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}



