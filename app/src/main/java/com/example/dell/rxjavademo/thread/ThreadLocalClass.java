package com.example.dell.rxjavademo.thread;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * description:  ThreadLocal
 * autour: TMM
 * date: 2018/7/3 11:50
 * update: 2018/7/3
 * version:
 *    参考：https://blog.csdn.net/seu_calvin/article/details/52119959
 *          https://blog.csdn.net/singwhatiwanna/article/details/48350919
 *
 *   原理： ThreadLocal为不同线程维护了不同的变量副本，
 *              是一种空间换时间的策略，提供了一种简单的多线程的实现方式
 *    使用场景：
 *    （1）ThreadLocal适用于某些数据以线程为作用域并且不同线程具有不同数据副本的场景。

           比如对于Handler来说，它需要获取当前线程的Looper，很显然Looper的作用域就是线程并且不同线程具有不同的Looper，
                                这个时候通过ThreadLocal就可以轻松实现Looper在线程中的存取。

      （2）ThreadLocal的另一个使用场景是复杂逻辑下的对象传递。

           比如监听器的传递，有时一个线程中的任务过于复杂，又需要监听器贯穿整个线程的执行过程，
                            这时就可以使用ThreadLocal，这样就可以让监听器作为线程内的全局对象而存在，
                            在线程内部只要通过get方法就可以获取到监听器。每个监听器对象都在自己的线程内部存储。
 *
*/
public class ThreadLocalClass extends AppCompatActivity {


    private  ThreadLocal<Boolean>  threadLocal = new ThreadLocal<>();




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //在主线程、子线程1、子线程2中去设置访问它的值
        threadLocal.set(true);
        System.out.println("Main " + threadLocal.get());

        new Thread("Thread#1"){
            @Override
            public void run() {
                threadLocal.set(false);
                System.out.println("Thread#1 " + threadLocal.get());
            }
        }.start();

        new Thread("Thread#2"){
            @Override
            public void run() {
                System.out.println("Thread#2 " + threadLocal.get());
            }
        }.start();


        // 结果： 不同的线程访问同一个ThreadLocal获取到的值是不一样的

    }
}
