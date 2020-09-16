package com.example.myapplication;

public class Profile {

    private String name;
    private String ins;

    public Profile() {
    }

    public Profile(String name,String ins) {
        this.name=name;
        this.ins = ins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getIns() {
        return ins;
    }
    public void setIns(String ins) {
        this.ins = ins;
    }

}
