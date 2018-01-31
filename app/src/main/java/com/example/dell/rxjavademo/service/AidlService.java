package com.example.dell.rxjavademo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.dell.rxjavademo.utils.BaseUtils;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/1/31.
 * 邮箱：123123@163.com
 */

public class AidlService extends Service {

    private  AidlMyBind aidlMyBind = new AidlMyBind(this);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return aidlMyBind;
    }

    public  void    receiveAIDLByActivity (String a){
        BaseUtils.toast(a);
    }

}
