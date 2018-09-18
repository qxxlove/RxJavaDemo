package com.example.dell.rxjavademo.designmode.proty;

import android.util.Log;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/9/18.
 * 邮箱：123123@163.com
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
