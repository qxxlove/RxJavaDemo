package com.example.dell.rxjavademo.bean;

import com.google.gson.Gson;

/**
 * Created by DELL on 2017/3/30.
 */
public class User {

   private  String name ;

    public User(String name) {
        this.name = name;
    }

    //默认打印的是一个user的地址，但这处理为json数据
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
