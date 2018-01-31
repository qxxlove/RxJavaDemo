package com.example.dell.rxjavademo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.dell.rxjavademo.utils.BaseUtils;

/** 
 * description: Service 使用
 * autour: TMM
 * date: 2018/1/31 15:11 
 * update: 2018/1/31
 * version:
 *
 * 第1步 ： 新建一个继承自Service的类MyService，然后在AndroidManifest.xml里注册这个Service
*/

public class MyService extends Service {

    // 第④步 实例化binder 对象 ，并通过onBInd 返回
    private  MyBinder  myBinder = new MyBinder(this);



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    /**
     * 接收到消息
     */
    public void receiveMessage(String c) {
        BaseUtils.toast("接收到来自Activity的消息："+c);
    }
}
