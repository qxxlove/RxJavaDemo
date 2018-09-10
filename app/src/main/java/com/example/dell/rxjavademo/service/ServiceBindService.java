package com.example.dell.rxjavademo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/** 
 * description: bindService 
 * autour: TMM
 * date: 2018/6/29 17:14 
 * update: 2018/6/29
 * version: 
*/

public class ServiceBindService extends Service {

    private  ServiceBind serviceBind  = new ServiceBind(this);


    @Override
    public void onCreate() {
        Log.i("ServiceBindService:","onCreate()");
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        Log.i("ServiceBindService:","onDestroy()");
        super.onDestroy();
    }


    /**
     * 本质返回一个IBinder接口的实现类
     * @param intent
     * @return
     * 三种做法：
     *      ① 直接继承Binder类 （实现IBinder  接口）   特点： 不需要跨进程工作
     *          典例： 后台播放音乐
     *      ② Messenger     (跨进程通信最简单的方法（IPC），串行的方式)，本质：对AIDL 的进一步疯装
     *          
     *      ③ 使用AIDL       
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("ServiceBindService:","onBind()");
        return serviceBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("ServiceBindService:","onUnbind()");
        return super.onUnbind(intent);
    }
    
}

/*
I/ServiceBindService:: onCreate()
        I/ServiceBindService:: onBind()
        I/ServiceTwoActivity:: 绑定成功
                              onUnbind()
                              onDestroy()
*/

