package com.example.dell.rxjavademo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/** 
 * description: 直接启动Service
 * autour: TMM
 * date: 2018/6/29 15:40 
 * update: 2018/6/29
 * version:
 *
 * 思考： 为什么stratCommand ()  为什么会执行多次？
 *   
 * 参考： https://blog.csdn.net/javazejian/article/details/52709857
 *
 *
*/

public class ServiceStartService extends Service {


    /**
     *
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序
     * （在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        Log.i("ServiceStartService:","onCreate()");
        super.onCreate();

    }


    /**
     *  每次通过startService()方法启动Service时都会被回调。(启动几次就执行多次)
     * @param intent 启动时，启动组件传递过来的Intent，如Activity可利用Intent封装所需要的参数并传递给Service
     * @param flags  表示启动请求时是否有额外数据，可选值有 0，START_FLAG_REDELIVERY，START_FLAG_RETRY，
     *               它们具体含义如下：
     *                              0代表没有，
                                   START_FLAG_REDELIVERY
                                  这个值代表了onStartCommand方法的返回值为 START_REDELIVER_INTENT，
                                  而且在 上一次服务被杀死前 会去调用stopSelf方法停止服务。
                                 其中 START_REDELIVER_INTENT 意味着当Service因内存不足而被系统kill后，则会重建服务，
                                 并通过传递给服务的最后一个 Intent 调用 onStartCommand()，此时Intent时有值的。
                                 START_FLAG_RETRY
                                 该flag代表当onStartCommand调用后一直没有返回值时，会尝试重新去调用onStartCommand()。
     * @param startId  指明当前服务的唯一ID，与stopSelfResult (int startId)配合使用，
     *                 stopSelfResult 可以更安全地根据ID停止服务。
     * @return
     *     他的返回值才是我们需要注意的
     *
     *     START_STICKY  （把你杀死，尝试去救你）
         当Service因内存不足而被系统kill后，一段时间后内存再次空闲时，
          系统将会尝试重新创建此Service，
          一旦创建成功后将回调onStartCommand方法，但其中的Intent将是null，除非有挂起的Intent，如pendingintent，
         这个状态下比较适用于不执行命令、但无限期运行并等待作业的媒体播放器或类似服务。

          START_NOT_STICKY （把你杀死，不管了）
        当Service因内存不足而被系统kill后，即使系统内存再次空闲时，系统也不会尝试重新创建此Service。
          除非程序中再次调用startService启动此Service，这是最安全的选项，
         可以避免在不必要时以及应用能够轻松重启所有未完成的作业时运行服务。

          START_REDELIVER_INTENT （把你杀死，有把救活了）
        当Service因内存不足而被系统kill后，则会重建服务，
         并通过传递给服务的最后一个 Intent 调用 onStartCommand()，任何挂起 Intent均依次传递。
         与START_STICKY不同的是，其中的传递的Intent将是非空，是最后一次调用startService中的intent。
         这个值适用于主动执行应该立即恢复的作业（例如下载文件）的服务。

     *
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceStartService:","onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        Log.i("ServiceStartService:","onDestroy()");

        super.onDestroy();

    }

    /**
     * 绑定服务时才会调用
     * 必须要实现的方法 
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
