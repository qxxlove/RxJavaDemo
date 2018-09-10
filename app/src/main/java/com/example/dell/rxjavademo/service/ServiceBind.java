package com.example.dell.rxjavademo.service;

import android.os.Binder;

/** 
 * description: 
 * autour: TMM
 * date: 2018/6/29 17:31 
 * update: 2018/6/29
 * version:
 *
   1.创建BindService服务端，继承自Service并在类中，
            创建一个实现IBinder 接口的实例对象并提供公共方法给客户端调用
   2.从 onBind() 回调方法返回此 Binder 实例。
   3.在客户端中，从 onServiceConnected() 回调方法接收 Binder，并使用提供的方法调用绑定服务。

 *     Android Binder之应用层总结与分析
 *     https://blog.csdn.net/qian520ao/article/details/78089877
 *     Android Launcher 启动 Activity 的工作过程
 *     https://blog.csdn.net/qian520ao/article/details/78156214
 *
*/
                                                                    
public class ServiceBind  extends Binder {



    private  ServiceBindService serviceBindService ;

    public ServiceBind(ServiceBindService serviceBindService) {
        this.serviceBindService = serviceBindService;
    }

    public   ServiceBindService getService (){
           return  serviceBindService;
    }


}
