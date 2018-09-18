// IServiceAidlInterface.aidl
package com.example.dell.rxjavademo;

// Declare any non-default types here with import statements
// 参考：	Android 深入浅出AIDL（一）
//  https://blog.csdn.net/qian520ao/article/details/78072250

import com.example.dell.rxjavademo.IServiceMyCallbackListener;
import com.example.dell.rxjavademo.MessageBean;


interface IServiceAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

               MessageBean getDemand();

               void setDemandIn(in MessageBean msg);//客户端->服务端

               //out和inout都需要重写MessageBean的readFromParcel方法
               void setDemandOut(out MessageBean msg);//服务端->客户端

               void setDemandInOut(inout MessageBean msg);//客户端<->服务端

               void registerListener(IServiceMyCallbackListener listener);
               void unregisterListener(IServiceMyCallbackListener listener);

}


   

  //   方法内如果有传输载体(MessageBean)可以理解为携带参数，就必须指明定向tag(in,out,inout)
  //  in : 客户端数据对象流向服务端，并且服务端对该数据对象的修改不会影响到客户端。
  //  out : 数据对象由服务端流向客户端，（客户端传递的数据对象此时服务端收到的对象内容为空，服务端可以对该数据对象修改，并传给客户端）
  // inout : 以上两种数据流向的结合体。（但是不建议用此tag,会增加开销）

   /*   该篇文章内容不多，但是处处皆是精华，尤其是以下3条建议，以防引起惨案。
      1. xxx.aidl 中不能存在同方法名不同参数的方法。
      2. xxx.aidl 中实体类必须要有指定的tag。
      3. 在Android Studio里写完aidl文件还需要在build.gradle文件中android{}方法内添加aidl路径。
*/