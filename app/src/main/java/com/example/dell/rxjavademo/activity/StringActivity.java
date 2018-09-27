package com.example.dell.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.rxjavademo.R;


/**
 *
 * String ,StringBuffer ,StringBuilder
 *
 * 参考： https://mp.weixin.qq.com/s/6mNH4re2wDrp49gTPVMMtw
 *
 *    String是不可变的（修改String时，不会在原有的内存地址修改，而是重新指向一个新对象），
 *         String用final修饰，不可继承，String本质上是个final的char[]数组，
 *         所以char[]数组的内存地址不会被修改，而且String 也没有对外暴露修改char[]数组的方法.
 *         不可变性 可以保证 线程安全 以及 字符串串常量池 的实现. 频繁的增删操作是不建议使用String的

      StringBuffer是线程安全的,多线程建议使用这个

      StringBuilder是非线程安全的,单线程使用这个更快

     因为StringBuffer的方法是加了synchronized锁起来了的,而StringBuilder没有.
 *
 */

public class StringActivity extends AppCompatActivity {

    private  String name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string);
        name = "123";
    }
}
