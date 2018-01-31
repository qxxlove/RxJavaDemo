// IMyAidlInterface.aidl
package com.example.dell.rxjavademo;

// Declare any non-default types here with import statements
import com.example.dell.rxjavademo.AIDLMyCallbackListener;

// 切记： AIDL语法：任何时候都要导包，不管在不在同一个包下，否则就会报错
//   每次编写完，通过Biuld---> Make Project 不报错就可以正常使用了


interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

             /**
                 * 自己实现的方法
                 * @param str
                 */
                void testMethod(String str);
               /**
                               * 注册接口，相当于 setTestListener（）
                               * @param str
                               */


                void registerListener(AIDLMyCallbackListener listener);

}
