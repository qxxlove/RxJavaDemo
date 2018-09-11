package com.example.dell.rxjavademo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

  
    /**Stub内部继承Binder，具有跨进程传输能力*/
    IServiceAidlInterface.Stub iServiceAidlInterface = new IServiceAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            
        }

        @Override
        public MessageBean getDemand() throws RemoteException {
            MessageBean demand = new MessageBean();
            demand.setContent("首先，看到我要敬礼");
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
            Log.i(TAG, "程序员:" + msg.toString());
        }

        /**
         * 服务端数据 流向 客户端
         * @param msg
         * @throws RemoteException
         */
        @Override
        public void setDemandOut(MessageBean msg) throws RemoteException {
            //msg内容一定为空
            Log.i(TAG, "程序员:" + msg.toString());

            msg.setContent("我不想听解释，下班前把所有工作都搞好！");
            msg.setLevel(5);
        }

        /**
         *   数据互通
         * @param msg
         * @throws RemoteException
         */
        @Override
        public void setDemandInOut(MessageBean msg) throws RemoteException {
            Log.i(TAG, "程序员:" + msg.toString());

            msg.setContent("把用户交互颜色都改成粉色");
            msg.setLevel(3);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iServiceAidlInterface;
    }
}
