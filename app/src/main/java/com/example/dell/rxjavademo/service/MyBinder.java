package com.example.dell.rxjavademo.service;

import android.os.Binder;

/**
 * description:
 * autour: TMM
 * date: 2018/1/31 15:44
 * update: 2018/1/31
 * version:
 * 第3步   新建一个继承自Binder的类MyBinder
*/
public class MyBinder extends Binder {

    private  MyService myService;
    private  TestListener testListener;




    public MyBinder(MyService myService) {
        this.myService = myService;
    }

    public TestListener getTestListener() {
        return testListener;
    }

    public void setTestListener(TestListener testListener) {
        this.testListener = testListener;
    }

    /**
     * 中间方法，具体执行逻辑还是service 里面执行逻辑
     * @param c
     */
    public  void  testMethod(String c){
        myService.receiveMessage(c);
        testListener.reviewActivity("service回复activityde 的消息： hello Activity");
    }




    // 自定义接口
    public  interface  TestListener {
           public  void  reviewActivity(String a);
    }


}
