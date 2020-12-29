package com.example.collegebustrack;

public class Student {


    private String name,adm,branch,email,busid,password,sem,uid;

    public Student(){}


    public Student(String uid,String name, String adm, String branch, String email , String busid, String password, String sem) {
        this.uid=uid;
        this.name = name;
        this.adm = adm;
        this.branch = branch;
        this.email = email;
        this.busid = busid;
        this.password = password;
        this.sem= sem;
    }

    public Student(String password,String adm){
        this.password = password;
        this.adm = adm;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getAdm() {
        return adm;
    }

    public String getBranch() {
        return branch;
    }

    public String getEmail() {
        return email;
    }

    public String getBusid() {
        return busid;
    }

    public String getPassword() {
        return password;
    }
    public String getSem() {
        return sem;
    }

}