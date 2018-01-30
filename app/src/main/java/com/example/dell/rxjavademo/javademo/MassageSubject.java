package com.example.dell.rxjavademo.javademo;

/**
 * Created by DELL on 2017/3/29.
 */
public class MassageSubject extends Subject {



    public  void  change (String state) {
        notifyObserver(state);

    }


}
