package com.example.dell.rxjavademo.service;

import android.os.RemoteException;

import com.example.dell.rxjavademo.AIDLMyCallbackListener;
import com.example.dell.rxjavademo.IMyAidlInterface;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/1/31.
 * 邮箱：123123@163.com
 */

public class AidlMyBind  extends IMyAidlInterface.Stub {

    private  AidlService aidlService;
    private  AIDLMyCallbackListener aidlMyCallbackListener;


    public AidlMyBind(AidlService aidlService) {
        this.aidlService = aidlService;
    }





    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void testMethod(String str) throws RemoteException {
        aidlService.receiveAIDLByActivity(str);
        aidlMyCallbackListener.onResponse("回复给Activity消息： hello Activity");
    }

    @Override
    public void registerListener(AIDLMyCallbackListener listener) throws RemoteException {
          aidlMyCallbackListener = listener;

    }
}
