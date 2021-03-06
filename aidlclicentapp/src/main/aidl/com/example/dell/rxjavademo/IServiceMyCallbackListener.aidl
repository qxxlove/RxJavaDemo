// AIDLMyCallbackListener.aidl
package com.example.dell.rxjavademo;

// Declare any non-default types here with import statements

import com.example.dell.rxjavademo.MessageBean;

interface IServiceMyCallbackListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

     // 回复消息的方法
     void onResponse (in MessageBean reviewContent);
}                                                                                       
