package com.example.dell.rxjavademo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dell.rxjavademo.R;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 强软弱虚 四种引用
 *
 * 参考： https://mp.weixin.qq.com/s/h5MzWRsfRTrrH4z3QIrSzQ
 *
 * 源码： Android API 26 Platform -----> android.jar------->java.lang.ref
 *
 *  记住： 我们创建的所有对象，都是通过引用间接访问的，并不能直接访问
 *
 */


public class RefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ref2);

        initStrongRef();
        initSoftRef();
        initSoftRefReal();
        initWeakRef();
        initPhantomRef();

    }

    /**
     * 虚引用是所有引用类型中最弱的一个。
     * 一个持有虚引用的对象，和没有引用几乎是一样的，
     * 随时都可能被垃圾回收器回收。
     * 当试图通过虚引用的get()方法取得强引用时，总是会失败。
     * 并且，虚引用必须和引用队列一起使用，它的作用在于跟踪垃圾回收过程。
     * 当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，
     * 就会在垃圾回收后，销毁这个对象，奖这个虚引用加入引用队列。
     *
     * 开发中几乎没用到过
     */
    private void initPhantomRef() {
        
    }

    /**
     * 弱引用
     *
     *   说明：
     *      随时可能会被垃圾回收器回收，不一定要等到虚拟机内存不足时才强制回收。要获取对象时，同样可以调用get方法。
     *
            如果一个对象只具有弱引用，那么在垃圾回收器线程扫描的过程中，
           一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。
           不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。

           弱引用也可以和一个引用队列（ReferenceQueue）联合使用，
          如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中。
          这点同弱引用一致。

        
     *
     *
     */
    private void initWeakRef() {
        /**以下代码会造成内存泄露，解决办法 使用静态类型
         *
         *  那么，为什么会造成内存泄露呢？
               这种情况就是由于android的特殊机制造成的：
                     当一个android主线程被创建的时候，同时会有一个Looper对象被创建，
                     而这个Looper对象会实现一个MessageQueue(消息队列)，
                    当我们创建一个handler对象时，而handler的作用就是放入和取出消息从这个消息队列中，
                    每当我们通过handler将一个msg放入消息队列时，这个msg就会持有一个handler对象的引用。

              因此当Activity被结束后，这个msg在被取出来之前，这msg会继续存活，
             但是这个msg持有handler的引用，而handler在Activity中创建，会持有Activity的引用，
             因而当Activity结束后，Activity对象并不能够被gc回收，因而出现内存泄漏。

             简单来说：
                 Activity在被结束之后，MessageQueue并不会随之被结束，
                 如果这个消息队列中存在msg，则导致持有handler的引用，
                 但是又由于Activity被结束了，msg无法被处理，
                从而导致永久持有handler对象，handler永久持有Activity对象，
                于是发生内存泄漏。但是为什么为static类型就会解决这个问题呢？

                因为在java中所有非静态的对象都会持有当前类的强引用，而静态对象则只会持有当前类的弱引用。
                声明为静态后，handler将会持有一个Activity的弱引用，
               而弱引用会很容易被gc回收，这样就能解决Activity结束后，gc却无法回收的情况。
               Activity 一被回收，而他里面所有的东西也被回收
         *
         */
       /* Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };*/
        /**将上面的代码替换成下面的*/
        MyHandler handler = new MyHandler(this);


    }

    /**
     * 因为在java中所有非静态的对象都会持有当前类的强引用，而静态对象则只会持有当前类的弱引用。
     */
    private static class MyHandler extends Handler{
        WeakReference<RefActivity> weakReference;
        MyHandler(RefActivity activity) {
            weakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                default:
            }
        }
    }



    /**
     * 实际开发介绍
     *
     *    好处：
     *       通过软引用的get()方法，取得drawable对象实例的强引用，发现对象被未回收。
     *       在GC在内存充足的情况下，不会回收软引用对象。此时view的背景显示.

            实际情况中,我们会获取很多图片.然后可能给很多个view展示,
            这种情况下很容易内存吃紧导致oom,内存吃紧，系统开始会GC。
           次GC后，drawables.get()不再返回Drawable对象，而是返回null，
          这时屏幕上背景图不显示，说明在系统内存紧张的情况下，软引用被回收。

         使用软引用以后，在OutOfMemory异常发生之前，
        这些缓存的图片资源的内存空间可以被释放掉的，从而避免内存达到上限，避免Crash发生。

        注意：
          在垃圾回收器对这个Java对象回收前，
          SoftReference类所提供的get方法会返回Java对象的强引用，
         一旦垃圾线程回收该Java对象之后，get方法将返回null。
         所以在获取软引用对象的代码中，一定要判断是否为null，以免出现NullPointerException异常导致应用崩溃。

     */
    private void initSoftRefReal() {
        View view = findViewById(R.id.text_one_ref);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        Drawable drawable = new BitmapDrawable(bitmap);
        SoftReference<Drawable> drawableSoftReference = new SoftReference<Drawable>(drawable);
        if(drawableSoftReference.get() != null) {
           // view.setBackgroundResource();
        }
    }


    /**
     * 软引用
     *
     *   说明：
     *     当虚拟机内存不足时，将会回收它指向的对象；需要获取对象时，可以调用get方法。

           可以通过java.lang.ref.SoftReference使用软引用。一个持有软引用的对象，
           不会被JVM很快回收，JVM会根据当前堆的使用情况来判断何时回收。当堆的使用率临近阈值时，才会回收软引用的对象。

        应用场景：
           例如从网络上获取图片，然后将获取的图片显示的同时，通过软引用缓存起来。
           当下次再去网络上获取图片时，首先会检查要获取的图片缓存中是否存在，若存在，直接取出来，不需要再去网络上获取。

         特点：
           如果一个对象只具有软引用，那么如果内存空间足够，垃圾回收器就不会回收它；
           如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。


           ②软引用可用来实现内存敏感的高速缓存。
          软引用可以和一个引用队列（ReferenceQueue）联合使用，
          如果软引用所引用的对象被垃圾回收，Java虚拟机就会把这个软引用加入到与之关联的引用队列中。
     */
    private void initSoftRef() {
        Object aRef = new  Object();
        SoftReference aSoftRef = new SoftReference(aRef);
        Object anotherRef = (Object)aSoftRef.get();

        
        /**②
         *  那么当这个 SoftReference 所软引用的aMyOhject被垃圾收集器回收的同时，
         *  ref所强引用的SoftReference对象被列入ReferenceQueue。

           也就是说，ReferenceQueue中保存的对象是Reference对象，而且是已经失去了它所软引用的对象的Reference对象。
           另外从ReferenceQueue这个名字也可以看出，它是一个队列，
           当我们调用它的poll()方法的时候，如果这个队列中不是空队列，那么将返回队列前面的那个Reference对象。

           在任何时候，我们都可以调用ReferenceQueue的poll()方法来检查是否有它所关心的非强可及对象被回收。
           如果队列为空，将返回一个null,否则该方法返回队列中前面的一个Reference对象。
           利用这个方法，我们可以检查哪个SoftReference所软引用的对象已经被回收。
           于是我们可以把这些失去所软引用的对象的SoftReference对象清除掉。*/
        ReferenceQueue queue = new  ReferenceQueue();
        SoftReference  ref = new  SoftReference(anotherRef, queue);

        SoftReference mref = null;
        while ((mref = ((SoftReference) queue.poll())) != null) {
            // 清除ref
        }

    }



    
    /**
     * 强引用
     *     说明：
     *     强引用是使用最普遍的引用。如果一个对象具有强引用，那垃圾回收器绝不会回收它。
     *     当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，
     *     也不会靠随意回收具有强引用的对象来解决内存不足的问题。

           通过引用，可以对堆中的对象进行操作。
           在某个函数中，当创建了一个对象，该对象被分配在堆中，通过这个对象的引用才能对这个对象进行操作。

           特点：
           强引用可以直接访问目标对象。
           强引用所指向的对象在任何时候都不会被系统回收。JVM宁愿抛出OOM异常，也不会回收强引用所指向的对象。
           强引用可能导致内存泄露。
     *
     */
    private void initStrongRef() {
        /**直接new出来的对象，就是强引用*/
        String str = new String("strong");

    }


}
