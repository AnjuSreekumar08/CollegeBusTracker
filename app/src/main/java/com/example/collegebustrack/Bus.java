package com.example.collegebustrack;

public class Bus {

    private String busnumber, busid, busroute;

    public Bus() {
    }

    public Bus(String busnumber, String busid, String busroute) {
        this.busnumber = busnumber;
        this.busid = busid;
        this.busroute = busroute;

    }



    public String getBusnumber() {
        return busnumber;
    }

    public String getBusid() {
        return busid;
    }

    public String getBusroute() {
        return busroute;
    }

}

