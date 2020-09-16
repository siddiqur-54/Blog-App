package com.example.myapplication;

public class Post {
    private String name,ins,date,post;

    public Post() {
    }

    public Post(String name, String ins, String date, String post) {
        this.name = name;
        this.ins = ins;
        this.date = date;
        this.post = post;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
