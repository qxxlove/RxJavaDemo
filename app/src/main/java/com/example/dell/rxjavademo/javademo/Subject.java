package com.example.dell.rxjavademo.javademo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/3/29.
 * 把这个类（主题）当多自己
 */
public abstract class Subject {

    //保存自己的女朋友
    private List<Observer> observers = new ArrayList<>();


    /**
     * 添加女朋友
     * @param observer
     */
    public  void attach (Observer observer) {
        observers.add(observer);
        System.out.print("我新交了一个女朋友"+observer.getName());
    }

    /**
     * 删除女朋友
     * @param observer
     */
    public  void dettct (Observer observer) {
        observers.remove(observer);
        System.out.print("我和他分手了");

    }

    /**
     * 通知所有的女朋友更新状态
     * @param state
     */
    public  void  notifyObserver (String state){
          for (Observer o : observers) {
              o.update(state);
          }
    }


}


