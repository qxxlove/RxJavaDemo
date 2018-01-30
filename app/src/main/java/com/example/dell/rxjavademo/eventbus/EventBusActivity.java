package com.example.dell.rxjavademo.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.rxjavademo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends AppCompatActivity {

    private TextView textView_result;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        EventBus.getDefault().register(this);   // 注册eventbus（订阅者）
        initView();
        initData();
        initClick();
    }

    private void initClick() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventBusActivity.this, EventBusTwoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {

    }

    private void initView() {
        textView_result = ((TextView) findViewById(R.id.text_eventBus_result));
        btn_next = ((Button) findViewById(R.id.button_event));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)      // 必须加约束条件，否则eventbus找不到执行的方法，报异常
    public void onMessageEvent(FristEvent event) {
        String msg = "onEventMainThread收到了消息：" + event.getInfo();
        Log.d("harvic", msg);
        textView_result.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);


    }
}




/*      3.0 之前
    public void onEvent(MessageEvent event) {
        log(event.message);
    }

   public void onEventMainThread(MessageEvent event) {
        textField.setText(event.message);
        }

  public void onEventBackgroundThread(MessageEvent event){
        saveToDisk(event.message);
        }

       3.0  变成了:
     @Subscribe(threadMode = ThreadMode.MainThread) //在ui线程执行
     public void onUserEvent(UserEvent event) {
        }

     @Subscribe(threadMode = ThreadMode.BackgroundThread) //在后台线程执行
     public void onUserEvent(UserEvent event) {
        }

    @Subscribe(threadMode = ThreadMode.Async) //强制在后台执行
    public void onUserEvent(UserEvent event) {
        }

    @Subscribe(threadMode = ThreadMode.PostThread) //默认方式, 在发送线程执行
     public void onUserEvent(UserEvent event) {
        }

        由于官方 文档 还没有更新, 特此记录,


        Sticky事件的使用
之前说的使用方法, 都是需要先注册(register), 再post,才能接受到事件;
如果你使用postSticky发送事件, 那么可以不需要先注册, 也能接受到事件.

首先,你可能需要声明一个方法:

//注意,和之前的方法一样,只是多了一个 sticky = true 的属性.
@Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
public void onEvent(MsgEvent event){
}

其次, 你可以在没有register的情况下:(发送事件)

EventBus.getDefault().postSticky(new MsgEvent("With Sticky"));

之后, 再注册,这样就可以收到刚刚发送的事件了:

EventBus.getDefault().register(this);//注册之后,马上就能收到刚刚postSticky发送的事件

3:参数说明:

/**
 * threadMode 表示方法在什么线程执行   (Android更新UI只能在主线程, 所以如果需要操作UI, 需要设置ThreadMode.MainThread)
 * sticky     表示是否是一个粘性事件   (如果你使用postSticky发送一个事件,那么需要设置为true才能接受到事件)
 * priority   优先级                 (如果有多个对象同时订阅了相同的事件, 那么优先级越高,会优先被调用.)
 * */

/*
@Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 100)
public void onEvent(MsgEvent event){
}
*/



