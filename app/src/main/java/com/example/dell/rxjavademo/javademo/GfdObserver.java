package com.example.dell.rxjavademo.javademo;

/**
 * Created by DELL on 2017/3/29.
 */
public class GfdObserver implements  Observer {

    private  String name ;

    public GfdObserver(String name) {
        this.name = name;
    }



    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void update(String state) {
        System.out.print(this.name+" 收到男朋友最新状态:"+state);

    }
}
