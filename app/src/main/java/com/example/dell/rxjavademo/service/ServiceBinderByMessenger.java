package com.example.dell.rxjavademo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.dell.rxjavademo.utils.BaseUtils;

/** 
 * description: 
 * autour: TMM
 * date: 2018/7/2 14:36 
 * update: 2018/7/2
 * version:
 *

 1.服务实现一个 Handler，由其接收来自客户端的每个调用的回调

 2.Handler 用于创建 Messenger 对象（对 Handler 的引用）

 3.Messenger 创建一个 IBinder，服务通过 onBind() 使其返回客户端

 4.客户端使用 IBinder 将 Messenger（引用服务的 Handler）实例化，然后使用Messenger将 Message 对象发送给服务
 
 5.服务在其 Handler 中（在 handleMessage() 方法中）接收每个 Message

 *
*/

public class ServiceBinderByMessenger  extends Service {

    public static final int MSG_SAY_HELLO = 1;
    private static final String TAG ="wzj" ;

    /**
     * 用于接收从客户端传递过来的数据
     */
    class  Iconhandler  extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                  case MSG_SAY_HELLO:
                      BaseUtils.toast("收到了来自Activity 的消息");

                      /**回复客户端*/
                      Messenger client = msg.replyTo;
                      Message replyMsg = Message.obtain(null,MSG_SAY_HELLO);
                      Bundle bundle=new Bundle();
                      bundle.putString("reply","ok~,I had receiver message from you! ");
                      replyMsg.setData(bundle);
                      //向客户端发送消息
                      try {
                          client.send(replyMsg);
                      } catch (RemoteException e) {
                          e.printStackTrace();
                      }

                      break;
                      default:
            }
        }
    }


    /**
     * 创建Messenger并传入Handler实例对象
     */
    final Messenger mMessenger = new Messenger(new Iconhandler());


    /**
     * 当绑定Service时,该方法被调用,将通过mMessenger返回一个实现
     * IBinder接口的实例对象
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
