package com.example.dell.rxjavademo.designmode.reflection;

import android.util.Log;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/12/17.
 * 邮箱：123123@163.com
 */

public class Student {

    private   String name;

    public Student() {
        Log.e("DesignModeActivity","创建了一个Student实例");
    }
    
    /**
     *   有参构造函数
     * @param name
     */
    public Student(String name) {
        Log.e("DesignModeActivity","调用了有参构造函数");
    }


    /**
     *无参数方法
     */
    public void setName1 (){
        Log.e("DesignModeActivity","调用了无参方法：setName1（）");

    }


   

    /**
     *  有参数方法
     * @param str
     */
    public void setName2 (String str){
        Log.e("DesignModeActivity","调用了有参方法setName2（String str）:"+str);

    }
    
}
