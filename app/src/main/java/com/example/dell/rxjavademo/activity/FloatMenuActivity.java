package com.example.dell.rxjavademo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.service.FloatView.ServiceFloatView;

/**
 * 参考： https://blog.csdn.net/feiduclear_up/article/details/49080587
 *       实际项目，还需要完善
 *
 *       8.0 直接崩溃，需要看原因
 *
 *    ①  Dialog  机制
 *
 *        
 *
 *
 *
 */


public class FloatMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_menu);
        initView();
        initClick();
    }

    private void initClick() {
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
        findViewById(R.id.text_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog(FloatMenuActivity.this);
        dialog.setContentView(R.layout.dialog_content_layout);
        dialog.show();
        //取消对话框
        // dialog.cancel();
    }

    private void initView() {

    }


}
