package com.example.dell.rxjavademo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.service.FloatView.ServiceFloatView;

/**
 * 参考： https://blog.csdn.net/feiduclear_up/article/details/49080587
 *       实际项目，还需要完善
 */


public class FloatMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_menu);
        initView();
    }

    private void initView() {
        final Intent service = new Intent(FloatMenuActivity.this, ServiceFloatView.class);
        findViewById(R.id.text_star_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatMenuActivity.this.startService(service);
            }
        });
        findViewById(R.id.text_stop_float).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatMenuActivity.this.stopService(service);
            }
        });
    }
}
