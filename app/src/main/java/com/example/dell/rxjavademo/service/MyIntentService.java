package com.example.dell.rxjavademo.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/** 
 * description: 
 * autour: TMM
 * date: 2018/7/3 9:25 
 * update: 2018/7/3
 * version: 
*/

public class MyIntentService extends IntentService {

    private  static   UpdateUI updateUI ;
    public static final String DOWNLOAD_URL="download_url";
    public static final String INDEX_FLAG="index_flag";

   
    
    public static void setUpdateUI(UpdateUI updateUIInterface){
        updateUI = updateUIInterface;
    }


    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * 必须实现的构造方法
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }


    /**
     * 必须实现异步任务的方法
     * @param intent intent Activity传递过来的Intent,数据封装在intent中
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("MyIntentService","onHandleIntent()");
        //在子线程中进行网络请求
        Bitmap bitmap=downloadUrlBitmap(intent.getStringExtra(DOWNLOAD_URL));
        Message msg1 = new Message();
        msg1.what = intent.getIntExtra(INDEX_FLAG,0);
        msg1.obj =bitmap;
        //通知主线程去更新UI
        if(updateUI!=null){
            updateUI.updateUI(msg1);
        }
    }


    /**
     * 以下生活时期方法 仅为测试
     */
    @Override
    public void onCreate() {
        Log.i("MyIntentService","onCreate()");
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.i("MyIntentService","onStart()");

        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i("MyIntentService","onStartCommand()");

        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MyIntentService","onBind()");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("MyIntentService","onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("MyIntentService","onDestroy()");
        super.onDestroy();
    }

    public interface UpdateUI{
        void updateUI(Message message);
    }

    private Bitmap downloadUrlBitmap(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        Bitmap bitmap=null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bitmap= BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


}



    /*  运行日志
        07-03 09:54:45.886 27784-27784/? I/MyIntentService: onCreate()
        07-03 09:54:45.891 27784-27784/? I/MyIntentService: onStartCommand()
        07-03 09:54:45.891 27784-27784/? I/MyIntentService: onStart()
        07-03 09:54:45.891 27784-27784/? I/MyIntentService: onStartCommand()
        07-03 09:54:45.892 27784-27784/? I/MyIntentService: onStart()
        07-03 09:54:45.892 27784-27784/? I/MyIntentService: onStartCommand()
        07-03 09:54:45.892 27784-27784/? I/MyIntentService: onStart()
        07-03 09:54:45.892 27784-27887/? I/MyIntentService: onHandleIntent()
        07-03 09:54:45.895 27784-27784/? I/MyIntentService: onStartCommand()
        07-03 09:54:45.895 27784-27784/? I/MyIntentService: onStart()
        07-03 09:54:45.896 27784-27784/? I/MyIntentService: onStartCommand()
        07-03 09:54:45.896 27784-27784/? I/MyIntentService: onStart()
        07-03 09:54:45.896 27784-27784/? I/MyIntentService: onStartCommand()
        07-03 09:54:45.897 27784-27784/? I/MyIntentService: onStart()
        07-03 09:54:45.897 27784-27784/? I/MyIntentService: onStartCommand()
        07-03 09:54:45.897 27784-27784/? I/MyIntentService: onStart()

        07-03 09:54:46.009 27784-27887/? I/MyIntentService: onHandleIntent()
        07-03 09:54:46.176 27784-27887/? I/MyIntentService: onHandleIntent()
        07-03 09:54:46.294 27784-27887/? I/MyIntentService: onHandleIntent()
        07-03 09:54:46.432 27784-27887/? I/MyIntentService: onHandleIntent()
        07-03 09:54:46.633 27784-27887/? I/MyIntentService: onHandleIntent()
        07-03 09:54:46.822 27784-27887/? I/MyIntentService: onHandleIntent()

        07-03 09:54:47.381 27784-27784/? I/MyIntentService: onDestroy()*/