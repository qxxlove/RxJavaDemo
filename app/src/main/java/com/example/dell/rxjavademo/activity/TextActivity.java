package com.example.dell.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.view.CustomImageSpan;

public class TextActivity extends AppCompatActivity {

    private TextView text_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        initView();
        initData();
    }

    private void initData() {
        SpannableString spannableString = new SpannableString("呵呵，图文混排");
        // 此时默认的对齐方式是底部对齐 （DynamicDrawableSpan.ALIGN_BOTTOM）
        ImageSpan imageSpan = new ImageSpan(this,R.mipmap.ic_launcher);
         //DynamicDrawableSpan.ALIGN_BASELINE 表示与文字内容的基线对齐 (此方式并不能满足我们的开发要求)
        ImageSpan imageSpan1 = new ImageSpan(this, R.mipmap.ic_launcher, DynamicDrawableSpan.ALIGN_BASELINE);
        // 我们自定义的ImageSpan
        CustomImageSpan customImageSpan = new CustomImageSpan(this,R.mipmap.ic_launcher,2);

        /**
         * 参数说明：
         * setSpan 插入内容的时候，起始位置不替换，会替换起始位置到终止位置之间的内容，
         * 包括终止位置  ；
         * SPAN_INCLUSIVE_EXCLUSIVE 用来控制 是否同步 设置  新插入的内容 与 start/end  （插入的是图片的话，随意）
         * 字体的样式，可随意设置
         */
        spannableString.setSpan(customImageSpan,2,3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        text_image.setText(spannableString);
    }

    private void initView() {
        text_image = ((TextView) findViewById(R.id.text_and_image_mixed));

    }
}
