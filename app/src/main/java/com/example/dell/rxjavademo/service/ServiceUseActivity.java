package com.example.dell.rxjavademo.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dell.rxjavademo.AIDLMyCallbackListener;
import com.example.dell.rxjavademo.IMyAidlInterface;
import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.handler.HandlerStudyActivity;
import com.example.dell.rxjavademo.utils.BaseUtils;

/**
 * description:  Service ；练习
 * autour: TMM
 * date: 2018/1/31 15:12
 * update: 2018/1/31
 * version:
 *
 *  第1步 ： 新建一个继承自Service的类MyService，然后在AndroidManifest.xml里注册这个Service

    第2步 ：  Activity里面使用bindService方式启动MyService，也就是绑定了MyService

        （到这里实现了绑定，Activity与Service通信的话继续下面的步骤）

    第3步   新建一个继承自Binder的类MyBinder

    第4步 ：在MyService里实例化一个MyBinder对象mBinder，并在onBind回调方法里面返回这个mBinder对象

   第5步 ：第2步中bindService方法需要一个ServiceConnection类型的参数，
           在ServiceConnection里可以取到一个IBinder对象，就是第4步onBinder返回的mBinder对象
         （也就是在Activity里面拿到了Service里面的mBinder对象）

   第6步：在Activity里面拿到mBinder之后就可以调用这个binder里面的方法了（也就是可以给Service发消息了），
          需要什么方法在MyBinder类里面定义实现就行了。
          如果需要Service给Activity发消息的话，通过这个binder注册一个自定义回调即可。
 *
 *
*/


public class ServiceUseActivity extends AppCompatActivity  {

    private  MyBinder myBinder;
    private  AidlMyBind aidlMyBind;

    private IMyAidlInterface iMyAidlInterface;

    /**普通的service连接*/
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, final IBinder service) {
               myBinder = ((MyBinder) service);
               myBinder.setTestListener(new MyBinder.TestListener() {
                   @Override
                   public void reviewActivity(String a) {
                       BaseUtils.toast(a);
                   }
               });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    /**跨进程的service连接*/
    ServiceConnection serviceConnectionAIDL = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, final IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service) ;

            try {
                iMyAidlInterface.registerListener(new AIDLMyCallbackListener() {
                    @Override
                    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

                    }

                    @Override
                    public void onResponse(String reviewContent) throws RemoteException {
                        Log.e("TAG", "receive message from service: "+reviewContent);
                        BaseUtils.toast(reviewContent);
                    }

                    @Override
                    public IBinder asBinder() {
                        return null;
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private TextView text_send;
    private TextView text_review;
    private TextView text_send_aidl;
    private TextView text_start_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_use);
        initView();
        initService();
        initAIDLService();
    }

    private void initView() {
        text_send = ((TextView) findViewById(R.id.text_activity_send));
        text_review = ((TextView) findViewById(R.id.text_service_review));
        text_send_aidl = ((TextView) findViewById(R.id.text_activity_send_aidl));
        text_start_service =  ((TextView) findViewById(R.id.text_service_activity));

        text_start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ServiceUseActivity.this,ServiceTwoActivity.class));
            }
        });

        findViewById(R.id.text_handler_thread_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceUseActivity.this,HandlerStudyActivity.class));

            }
        });
        findViewById(R.id.text_intent_service_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceUseActivity.this,IntentServiceActivity.class));
            }
        });

    }


    private void initAIDLService() {
        // 第2步 ： 启动服务
        Intent intent = new Intent(ServiceUseActivity.this,AidlService.class);
        bindService(intent,serviceConnectionAIDL,BIND_AUTO_CREATE);

        text_send_aidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iMyAidlInterface.testMethod("接收到来自Activity的消息: hi  service");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /**
     * Binder Service 服务
     */
    private void initService() {
        // 第2步 ： 启动服务
        Intent intent = new Intent(ServiceUseActivity.this,MyService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

        text_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.testMethod("hi service");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      
    }
}
