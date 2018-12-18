package com.example.dell.rxjavademo.designmode.proty;

import android.util.Log;

/**
 * 目标对象
 * 
 */

public class Jessen   implements FindGrilInterface {

    public static final String  TAG= "Jessen";

    @Override
    public void findGril(String name, int age) {
        Log.e(TAG,name+"说：愿意做我的女朋友。");
    }


    @Override
    public void buyPhone(String name,String phoneName) {
        Log.e(TAG,name+"帮我买了一个"+phoneName);
    }
}
