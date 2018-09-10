package com.tjbool.httpwww.aidlclicentapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private  IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("com.tengxun.aidl");//service的action
        intent.setPackage("qdx.aidlserver");//aidl文件夹里面aidl文件的包名
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //得到该对象之后，我们就可以用来进行进程间的方法调用和传输啦。
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

}
