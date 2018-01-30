package com.example.dell.rxjavademo.retrofit;

/**
 * Created by DELL on 2017/4/16.
 */
public class User {

    private  int id;
    private  String name ;
    private  String pass;

    public User() {
    }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
