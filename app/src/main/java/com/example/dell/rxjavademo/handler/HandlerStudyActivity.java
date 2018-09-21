package com.example.dell.rxjavademo.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * description:  典例：HandlerThread （Looper+Handler+MessageQueue+Thread异步循环机制 ）
 * autour: TMM
 * date: 2018/7/2 18:09
 * update: 2018/7/2
 * version:
 * 参考： https://blog.csdn.net/javazejian/article/details/52426353
 *
 *  特点：
 *     HandlerThread本质上是一个线程类，它继承了Thread；
       HandlerThread有自己的内部Looper对象，可以进行looper循环；
      通过获取HandlerThread的looper对象 传递给 Handler对象，
                              可以在handleMessage方法中执行异步任务。
     创建HandlerThread后必须先调用HandlerThread.start()方法，Thread会先调用run方法，创建Looper对象。
 *
*/


public class HandlerStudyActivity extends AppCompatActivity {



    /**
     * 图片地址集合
     */
    private String urll[]={
            "http://p1.pstatp.com/origin/pgc-image/1530503741133015d35bc77",
            "http://p3.pstatp.com/origin/pgc-image/153050374094353204e7476",
            "http://p1.pstatp.com/origin/pgc-image/1530503740929137d445248",
            "http://p1.pstatp.com/origin/pgc-image/153022528490655454f0324",
            "http://p1.pstatp.com/origin/pgc-image/15302252894037a129c0e33",
            "http://p9.pstatp.com/origin/pgc-image/15302257816470dcda03d62",
            "http://p3.pstatp.com/origin/pgc-image/15302277649079d3a853eb1"
    };
    private ImageView imageView;

    private HandlerThread handlerThread = new HandlerThread("HandlerThread");

    
    private Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            LogUtils.e("次数:"+msg.what);
            ImageModel model = (ImageModel) msg.obj;
            imageView.setImageBitmap(model.bitmap);
        }
    };

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_study);
        imageView =  ((ImageView) findViewById(R.id.image_one));
        
        handlerThread.start();
        //子线程Handler
        //只有当线程创建成功并且Looper对象也创建成功之后才能获得mLooper的值，
        // HandlerThread内部则通过等待唤醒机制解决了同步问题。
        // 因为：handlerThread.getLooper() 是在主线程中获取的，而Looper 对象则是在子线程中创建的（HandlerThread）
        // 为了保证获取到的Looper最想不为空，handlerThread内部采取了等待唤醒机制 （见源码）
        Handler childHandler = new Handler(handlerThread.getLooper(),new ChildCallback());
        for(int i=0;i<7;i++){
            //每个1秒去更新图片
            childHandler.sendEmptyMessageDelayed(i,1000*i);
        }

      /*  childHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });*/

    }

    /**构建循环消息处理机制*/
    class  ChildCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            //在子线程中进行网络请求
            Bitmap bitmap=downloadUrlBitmap(urll[msg.what]);
            ImageModel imageModel=new ImageModel();
            imageModel.bitmap=bitmap;
            imageModel.url=urll[msg.what];
            Message msg1 = new Message();
            msg1.what = msg.what;
            msg1.obj =imageModel;
            //通知主线程去更新UI
            mUIHandler.sendMessage(msg1);
            
            return false;
        }
    }

    /**
     * 下载图片
     * @param urlString
     * @return
     */
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
