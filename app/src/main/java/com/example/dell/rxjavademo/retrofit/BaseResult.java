package com.example.dell.rxjavademo.retrofit;

/**
 * Created by DELL on 2017/4/16.
 */
public class BaseResult {

    private   String id;
    private  String name ;
    private  String pass;

    public BaseResult() {
    }

    public BaseResult(String id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
