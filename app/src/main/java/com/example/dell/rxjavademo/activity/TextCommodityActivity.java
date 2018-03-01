package com.example.dell.rxjavademo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dell.rxjavademo.App;
import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.utils.BaseUtils;

public class TextCommodityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_commodity);
        initView();
    }

    private void initView() {
          findViewById(R.id.text_click).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(TextCommodityActivity.this,TextCommodityActivity.class);
                  startActivity(intent);
              }
          });
          findViewById(R.id.text_click_two).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  BaseUtils.toast(((App) App.getAppContext()).getActivitySize()+"");
              }
          });


    }
}
