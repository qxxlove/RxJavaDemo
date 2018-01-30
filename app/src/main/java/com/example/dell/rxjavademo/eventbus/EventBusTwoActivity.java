package com.example.dell.rxjavademo.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dell.rxjavademo.R;

import org.greenrobot.eventbus.EventBus;

public class EventBusTwoActivity extends AppCompatActivity {

    private Button btn_publisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_two);
        initView();
        initData();
        initClick();
    }


    private void initClick() {
        btn_publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new FristEvent("我是发布者"));
                EventBusTwoActivity.this.finish();
            }
        });
    }

    private void initData() {

    }

    private void initView() {
        btn_publisher = ((Button) findViewById(R.id.btn_event_publisher));
    }


}
