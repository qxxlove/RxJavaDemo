package com.example.dell.rxjavademo.javademo;

/**
 * Created by DELL on 2017/3/29.
 */
public class Client {

    public  static  void  main (String[] args ) {

        Observer observer = new GfdObserver("范冰冰");
        Observer observer1 = new GfdObserver("柳岩");
        MassageSubject subject = new MassageSubject();
        subject.attach(observer);    // setListener
        subject.attach(observer1);

        subject.change("我在大保健");   //onClick

        subject.dettct(observer);
        subject.change("滚床单");
    }

}
