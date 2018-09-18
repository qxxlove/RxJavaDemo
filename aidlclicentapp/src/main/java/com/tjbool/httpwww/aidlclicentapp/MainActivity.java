package com.tjbool.httpwww.aidlclicentapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dell.rxjavademo.IServiceAidlInterface;
import com.example.dell.rxjavademo.IServiceMyCallbackListener;
import com.example.dell.rxjavademo.MessageBean;

public class MainActivity extends AppCompatActivity {

    private IServiceAidlInterface iServiceAidlInterface;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("com.example.dell.rxjavademo");//service的action
        intent.setPackage("com.example.dell.rxjavademo");//aidl文件夹里面aidl文件的包名
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 消息推送
     */
    IServiceMyCallbackListener.Stub iServiceMyCallbackListener = new IServiceMyCallbackListener.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onResponse(MessageBean reviewContent) throws RemoteException {
            //该方法运行在Binder线程池中，是非ui线程
            Log.e("client3",reviewContent.getContent());
        }
    };


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //得到该对象之后，我们就可以用来进行进程间的方法调用和传输啦。
           // iMyAidlInterface = .Stub.asInterface(service);
            iServiceAidlInterface = IServiceAidlInterface.Stub.asInterface(service);
            try {
                Log.e("client1",iServiceAidlInterface.getDemand().getContent());
                /** 数据流向 in 标识*/
                MessageBean messageBean = new MessageBean();
                messageBean.setContent("B 说：我就不敬礼");
                iServiceAidlInterface.setDemandIn(messageBean);

                /** 数据流向 inOut 标识*/
                MessageBean messageBean1 = new MessageBean();
                messageBean1.setContent("有什么事吗？");
                iServiceAidlInterface.setDemandInOut(messageBean1);
                // FIXME: 2018/9/12  这块可能有问题
                Log.e("client2",iServiceAidlInterface.getDemand().getContent());


                iServiceAidlInterface.registerListener(iServiceMyCallbackListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

}

 /*      asInterface 作用(点进去就可以看到源代码)：
               如果是同一进程，那么就返回Stub对象本身(obj.queryLocalInterface(DESCRIPTOR))，
               否则如果是跨进程则返回Stub的代理内部类Proxy。
               也就是说这个asInterface方法返回的是一个远程接口具备的能力（有什么方法可以调用），
               在我们项目里，asInterface的能力自己写了什么方法，这里就可以调用了。
*/


//   报错： 应该是AIDL 写的有问题
// Error:Execution failed for task ':app:compileDebugAidl'.
//           > java.io.IOException: com.android.ide.common.process.ProcessException:
//                   Error while executing process E:\AndroidStudio\sdk\build-tools\27.0.2\aidl.exe with arguments
//                     {-pE:\AndroidStudio\sdk\platforms\android-27\framework.aidl -oG:
//                    \github\SpareTimeApp\app\build\generated\source\aidl\debug -IG:\github\SpareTimeApp\app\src\debug\aidl -IG:\github\SpareTimeApp\app\src\main\aidl -IC:\Users\SEELE\.gradle\caches\transforms-1\files-1.1\support-media-compat-27.1.1.aar\0766e7ef625c19b0c77741089ade0ae5\aidl -IC:\Users\SEELE\.gradle\caches\transforms-1\files-1.1\support-compat-27.1.1.aar\3ded92a52e24929de06f48a7eb111ff6\aidl -dC:\Users\SEELE\AppData\Local\Temp\aidl6231245694744117891.d G:\github\SpareTimeApp\app\src\main\aidl\com\tjbool\httpwww\sparetimeapp\IServiceAidlInterface.aidl}


/*
09-11 17:27:15.546 15592-15592/com.tjbool.httpwww.aidlclicentapp E/AndroidRuntime: FATAL EXCEPTION: main
        Process: com.tjbool.httpwww.aidlclicentapp, PID: 15592
        java.lang.SecurityException: Binder invocation to an incorrect interface
at android.os.Parcel.readException(Parcel.java:1546)
        at android.os.Parcel.readException(Parcel.java:1499)
        at com.tjbool.httpwww.aidlclicentapp.IMyAidlInterface$Stub$Proxy.getDemand(IMyAidlInterface.java:179)
        at com.tjbool.httpwww.aidlclicentapp.MainActivity$1.onServiceConnected(MainActivity.java:37)
        at android.app.LoadedApk$ServiceDispatcher.doConnected(LoadedApk.java:1208)
        at android.app.LoadedApk$ServiceDispatcher$RunConnection.run(LoadedApk.java:1225)
        at android.os.Handler.handleCallback(Handler.java:739)
        at android.os.Handler.dispatchMessage(Handler.java:95)
        at android.os.Looper.loop(Looper.java:135)
        at android.app.ActivityThread.main(ActivityThread.java:5254)
        at java.lang.reflect.Method.invoke(Native Method)
        at java.lang.reflect.Method.invoke(Method.java:372)
        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:902)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:697)
*/

