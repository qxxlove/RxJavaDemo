package com.example.dell.rxjavademo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/** 
 * description: 服务端服务
 * autour: TMM
 * date: 2018/7/3 16:02 
 * update: 2018/7/3
 * version: 
*/

public class RemoteService extends Service {

    public static final String TAG  = "RemoteService";
    private  int count ;
    
    public static final int WHAT_MSG = 0;
    
    /**
     *   我们通常使用List<IServiceMyCallbackListener>来存放监听接口集合
     *   但此处不行
     *   由于跨进程传输客户端的同一对象会在服务端生成不同的对象！
        上面这句话说明跨进程通讯的过程中，这个传递的对象载体并不是像寄快递一样，
         从客户端传给服务端。而是经过中间人狸猫换太子的一些手段传递的。
        不过传递过程中间人(binder)对象都是同一个，所以Android通过这个特性提供RemoteCallbackList，让我们用来存储监听接口集合。
        这个RemoteCallbackList内部自动实现了线程同步的功能，而且它的本质是一个ArrayMap，
        所以我们用它来绑定/解绑时，不需要做额外的线程同步操作。
     */
    private RemoteCallbackList<IServiceMyCallbackListener> demandList = new RemoteCallbackList<>();


    private  Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_MSG:
                    if (demandList != null) {
                        int nums = demandList.beginBroadcast();
                        for (int i = 0; i < nums; i++) {
                            MessageBean messageBean = new MessageBean();
                            messageBean.setContent("我丢");
                            messageBean.setLevel(count);
                            count++;
                            try {
                                demandList.getBroadcastItem(i).onResponse(messageBean);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        demandList.finishBroadcast();
                    }
                    break;
                default:
            }
            handler.sendEmptyMessageDelayed(WHAT_MSG, 3000);//每3s推一次消息
            Log.e(TAG,"每3000发送一条消息");
            return false;
        }
    });

   

    /**Stub内部继承Binder，具有跨进程传输能力*/
    IServiceAidlInterface.Stub iServiceAidlInterface = new IServiceAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            
        }

        @Override
        public MessageBean getDemand() throws RemoteException {
            MessageBean demand = new MessageBean();
            demand.setContent("A 说：首先，看到我要敬礼");
            demand.setLevel(1);
            return demand;
        }

        /**
         * 客户端数据 流向 服务器
         * @param msg
         * @throws RemoteException
         */
        @Override
        public void setDemandIn(MessageBean msg) throws RemoteException {
            Log.e(TAG,  msg.getContent());
        }

        /**
         * 服务端数据 流向 客户端
         * @param msg
         * @throws RemoteException
         */
        @Override
        public void setDemandOut(MessageBean msg) throws RemoteException {
            //msg内容一定为空
            Log.i(TAG, "A:" + msg.getContent());

            msg.setContent("我不想听解释，下班前把所有工作都搞好！");
            msg.setLevel(5);
            Log.i(TAG, "A 说:" + msg.getContent());

        }

        /**
         *   数据互通
         * @param msg
         * @throws RemoteException
         */
        @Override
        public void setDemandInOut(MessageBean msg) throws RemoteException {
            Log.e(TAG, "B 说:" + msg.getContent());

            msg.setContent("把用户交互颜色都改成粉色");
            msg.setLevel(3);
            
        }

        /**
         * 缺少这个方法，就会报下面的错
         * Error:(24, 89) 错误: <匿名com.example.dell.rxjavademo.RemoteService$1>不是抽象的,
         * 并且未覆盖IServiceAidlInterface中的抽象方法unregisterListener(IServiceMyCallbackListener)
         * @param listener
         * @throws RemoteException
         */
        @Override
        public void registerListener(IServiceMyCallbackListener listener) throws RemoteException {
            demandList.register(listener);
            //开启推一次消息
            handler.sendEmptyMessageDelayed(WHAT_MSG, 1000);

        }

        @Override
        public void unregisterListener(IServiceMyCallbackListener listener) throws RemoteException {
             demandList.unregister(listener);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iServiceAidlInterface;
    }
}




 /* E/RemoteService: B 说：我就不敬礼
          E/RemoteService: B 说:有什么事吗？
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息
          E/RemoteService: 每3000发送一条消息*/




 /*E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
         E/client3: 我丢
 */