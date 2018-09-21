package com.example.dell.rxjavademo.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.rxjavademo.R;

public class ThreadStudeyActivity extends AppCompatActivity {

    private  Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_studey);
        initData();
    }

    /**
     *
     * 此处为了演示，就不使用线程池了
     * 主要是为了点击查看源码
     *
     *  思考：
     *      在线程中使用Handler时(除了Android主线程)必须把它放在Looper.prepare()和Looper.loop()之间。
     *      否则会抛出RuntimeException异常。 为什么呢？
     *
     *      具体看源码，得结论。
     *
     */
    private void initData() {
        /**以下代码我们以前经常写，不过现在被HandlerThread 代替了*/
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                };
                Looper.loop();
            }
        });
       thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
