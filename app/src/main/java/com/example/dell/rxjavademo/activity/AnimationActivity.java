package com.example.dell.rxjavademo.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.dell.rxjavademo.R;


/**
 * description:  Android 动画的例子之帧动画
 * autour: TMM
 * date: 2018/1/30 15:47
 * update: 2018/1/30
 * version:
*/
public class AnimationActivity extends AppCompatActivity {

    private ImageView image_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        initView();
        initData();
    }

    private void initData() {
        image_animation.setBackgroundResource(R.drawable.animation_list_image);

        AnimationDrawable rocketAnimation = (AnimationDrawable) image_animation.getBackground();
        rocketAnimation.start();

    }

    private void initView() {
        image_animation = ((ImageView) findViewById(R.id.image_frame_animation));

    }
}
