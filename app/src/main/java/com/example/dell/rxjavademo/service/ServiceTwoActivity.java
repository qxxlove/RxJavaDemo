package com.example.dell.rxjavademo.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.utils.BaseUtils;


/**
 * description:
 * autour: TMM
 * date: 2018/7/2 16:55
 * update: 2018/7/2
 * version:
 *
 * 服务转场：
 *      绑定服务（bind）可启动服务(start)
 *      启动服务不可绑定服务 ,
 *      当前启动服务并不会转为绑定服务，但是还是会与宿主绑定，
       只是即使宿主解除绑定后，服务依然按启动服务的生命周期在后台运行
*/

public class ServiceTwoActivity extends AppCompatActivity {

    
    private  ServiceBindService serviceBindService ;
    

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ServiceTwoActivity:","绑定成功");
            ServiceBind serviceBind =  (ServiceBind) service;
            serviceBindService = serviceBind.getService();
        }

        /**
         * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
         * 例如内存的资源不足时这个方法才被自动调用。
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    /**与服务端交互的Messenger*/
    private Messenger  messenger;
    private  boolean isBound ;

    ServiceConnection  serviceConnectionMessenger = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
             messenger = new Messenger(service);
             isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
             messenger = null;
             isBound = false;
        }
    };

    /**
     * 用于接收服务器返回的信息
     */
    private Messenger mRecevierReplyMsg= new Messenger(new ReceiverReplyMsgHandler());

    private static class ReceiverReplyMsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //接收服务端回复
                case ServiceBinderByMessenger.MSG_SAY_HELLO:
                    BaseUtils.toast("receiver message from service:" +msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_two);

        initView();
    }

    private void initView() {
        findViewById(R.id.text_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseUtils.toast("点击");
                startService(new Intent(ServiceTwoActivity.this,ServiceStartService.class));
            }
        });

        
        findViewById(R.id.text_stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 停止服务（stopService/自身调用stopSelf()）
                stopService(new Intent(ServiceTwoActivity.this,ServiceStartService.class));
            }
        });

        

        findViewById(R.id.text_bind_start_service).setOnClickListener(new View.OnClickListener() {
            @Override                                                                                                     
            public void onClick(View v) {
                Intent intent = new Intent(ServiceTwoActivity.this,ServiceBindService.class);
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.text_unbind_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceBindService != null){
                    serviceBindService = null;
                    unbindService(serviceConnection);
                }

            }
        });

        findViewById(R.id.text_bind_messenger_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceTwoActivity.this,ServiceBinderByMessenger.class);
                bindService(intent,serviceConnectionMessenger,BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.text_bind_messenger_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sayHello();
            }
        });

        findViewById(R.id.text_unbind_messenger_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound){
                    unbindService(serviceConnectionMessenger);
                    isBound = false;
                }

            }
        });


        findViewById(R.id.text_start_service_foreground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ServiceTwoActivity.this,ForegroundService.class);
                    intent.putExtra("cmd",0);//0,开启前台服务,1,关闭前台服务
                    startService(intent);
            }
        });
        findViewById(R.id.text_start_service_foreground_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceTwoActivity.this,ForegroundService.class);
                intent.putExtra("cmd",1);//0,开启前台服务,1,关闭前台服务
                startService(intent);
            }
        });



    }


    public void sayHello() {
        if (!isBound) {return;}
        // 创建与服务交互的消息实体Message
        Message msg = Message.obtain(null, ServiceBinderByMessenger.MSG_SAY_HELLO, 0, 0);
        //把接收服务器端的回复的Messenger通过Message的replyTo参数传递给服务端
        msg.replyTo=mRecevierReplyMsg;
        try {
            //发送消息
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
