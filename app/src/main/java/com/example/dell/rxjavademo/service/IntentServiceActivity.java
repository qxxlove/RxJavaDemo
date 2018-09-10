package com.example.dell.rxjavademo.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.dell.rxjavademo.R;


/**
 * description: IntentService 学习
 * autour: TMM
 * date: 2018/7/3 9:37
 * update: 2018/7/3
 * version:
 *
 * 参考： https://blog.csdn.net/javazejian/article/details/52426425
 *
 * 特点：
 *   它本质是一种特殊的Service,继承自Service并且本身就是一个抽象类
     它可以用于在后台执行耗时的异步任务，当任务完成后会自动停止
     它拥有较高的优先级，不易被系统杀死（继承自Service的缘故），因此比较适合执行一些高优先级的异步任务
     它内部通过HandlerThread和Handler实现异步操作
     创建IntentService时，只需实现onHandleIntent和构造方法，onHandleIntent为异步方法，可以执行耗时操作
 *
 *
*/

public class IntentServiceActivity extends AppCompatActivity implements MyIntentService.UpdateUI{

    private ImageView image;

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

    /** 写法 ① */
    private  Handler handler = new Handler(getMainLooper());
    /** 写法 ②*/
    private Handler.Callback  callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    };
    private Handler handler2 = new Handler(getMainLooper(),callback);

    /** 写法 ③*/
    private   final Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            image.setImageBitmap((Bitmap) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        image = ((ImageView) findViewById(R.id.image_two));

        Intent intent = new Intent(this,MyIntentService.class);
        //循环启动任务
        // 再次我们担心每次去启动service,上一次的任务会不会被覆盖掉，不会
        // 因为： IntentService真正执行异步任务的是HandlerThread+Handler，
        //       每次启动都会把下载图片的任务添加到依附的消息队列中，最后由HandlerThread+Handler去执行
        for (int i=0;i<7;i++) {
            intent.putExtra(MyIntentService.DOWNLOAD_URL,urll[i]);
            intent.putExtra(MyIntentService.INDEX_FLAG,i);
            startService(intent);
        }
        MyIntentService.setUpdateUI(this);
        
    }

    @Override
    public void updateUI(Message message) {
        mUIHandler.sendMessageDelayed(message,message.what * 1000);
    }
}


