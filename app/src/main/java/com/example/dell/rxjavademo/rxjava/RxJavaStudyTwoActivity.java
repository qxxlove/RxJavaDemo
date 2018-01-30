package com.example.dell.rxjavademo.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dell.rxjavademo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * description:  RxJava 操作符
 * autour: TMM
 * date: 2018/1/22 11:26
 * update: 2018/1/22
 * version:
 */

public class RxJavaStudyTwoActivity extends AppCompatActivity {

    private TextView text_one;
    public static final String TAG = "RXJAVA";

    private List<String> list = new ArrayList<>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_study_two);
        initView();


        // 创建操作符的使用
        //①最基本的使用，不考虑任何封装，，实用等
        // initRaJava();
        //② 快速创建 被观察者 ， just(最多只能发送十个参数)（经验之谈，没试过）
        //  initJustRxJava();
        //③快速创建 被观察者，  fromArray(必须传一个数组，集合是不可以的)， 支持10个以上参数，因为数组无限大
        // 此方法可以用来遍历数组
        //  initFromArrayRajava();
        // ④ 快速创建 ，    fromIterable(必须传递一个List集合)
        //   initFromIterableRaJava();
        // ⑤ 延时创建 又分为：
        // 一：定时创建（在x 秒之后），在一个固定的时间点
        // 二： 周期性创建，每隔x秒，开始创建
        //    initDaterRxJava();  // 动态创建
        //    initTimerRxJava();  // 延时创建
        //     initIntervalRaJava(); // 周期创建
        //     initIntervalRangeRaJava(); // 限制条件更多，创建
        //    initRangeRaJava();    // 等同于IntervalRange ,区别在于没有延迟设置
        //    initRangeLongRaJava();   // 等同于 Range ,就是支持数据类型不同Long

        initClick();

        // 转换操作符的使用
        // ①  最简单的Map 转换操作符
        //  initMapRaJava();
        // ②  FlatMap
        //  initFlatMapRaJava();
        //  ③ ConcatMap
        //  initConcatMapRaJava();
        // ④  Buffer
        //  initBufferRaJava();



        // 组合操作符的使用
        // ①  concat(1<= 组合被观察者的个数（<= 4）) / concatArray  (组合被观察者的个数（> 4）)
        //initConcatRxJava();
        //initConcatArrayRxJava();
        // ② merge (1<= 组合被观察者的个数（<= 4）)  /  mergeArray (concatArray  (组合被观察者的个数（> 4）)
        // 同上的去区别： concat 是串行执行   merge 是 按时间线 并行 执行
       // initMergeRxJava();
       // initMergeArrayRxJava();
        // ③ concatDelayError / mergeDelayError  同理
          // 从名字上理解就是对onError （）事件作出延迟处理，场景：等其他事件发送完毕之后
         //initNotUseConcatErrorRxJava();
         //initConcatErrorRxJava();
        // ④ zip  合并多个被观察者
          initZipRaJava();
         // ⑤ combineLatest  合并多个被观察者
         // 与Zip 的区别： Zip（） = 按个数合并，即1对1合并；CombineLatest（） = 按时间合并，即在同一个时间点上合并
          initCombineLatestRxJava();
         //  ⑥ combineLatestDelayError（）
         // 作用类似于concatDelayError（） / mergeDelayError（） ，即错误处理，此处不作过多描述
         // ⑦ reduce ()    把被观察者的需要发送的事件聚成一个发送
         // 聚合的本质 ：   聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
             initReduceRaJava ();
          // ⑧ collect()    将被观察者Observable发送的数据事件收集到一个数据结构里
             initCollectRaJava();
           // ⑨ startWith() /startArrayWith() 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
            initStartWithRaJava();
           // ⑩ count     统计被观察者发布事件的个数
             initCountRxJava();

        // 功能性操作符
         // ①  subscribe                        功能操作符之   订阅即连接观察者和被观察者
             initSubscribe();
         // ②  subscribeOn() 和 observeOn ()    功能操作符之 线程切换操作符
           // 通过 上述 subscribe 的例子，我们知道观察者和被观察者的发生都是在主线程中完成的，但是我们实际开发中，要比这复杂的很多
           // 我们也必须要遵从的原则"在主线程中更新UI,在子线程中执行耗时操作，所以就出现了subscribeOn 和 observeOn 操作符"
             initSubscribeOnAndOberveOn();
           // ③ delay()                         功能操作符之 延迟操作符
              initDelay();
           // ④ do 操作符                       功能操作符之 把控发送某个事件的生命周期
               initDo();
           // ⑤  错误操作符     发生错误我们做的一些处理
               initOnErrorReturn();
               initOnErrorResuemNext();
            // ⑥ retry 重试操作符
               initRetry();

    }

    /**
     * 重试操作符
     */
    private void initRetry() {
       // 1. retry（）
                // 作用：出现错误时，让被观察者重新发送数据
                // 注：若一直错误，则一直重新发送

                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })
                        .retry() // 遇到错误时，让被观察者重新发射数据（若一直错误，则一直重新发送
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });


       // 2. retry（long time）
                // 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
                // 参数 = 重试次数
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })
                        .retry(3) // 设置重试次数 = 3次
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });

        // 3. retry（Predicate predicate）
                // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
                // 参数 = 判断逻辑
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })
                        // 拦截错误后，判断是否需要重新发送请求
                        .retry(new Predicate<Throwable>() {
                            @Override
                            public boolean test(@NonNull Throwable throwable) throws Exception {
                                // 捕获异常
                                Log.e(TAG, "retry错误: "+throwable.toString());

                                //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                                //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                                return true;
                            }
                        })
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });

        //  4. retry（new BiPredicate<Integer, Throwable>）
                // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
                // 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })

                        // 拦截错误后，判断是否需要重新发送请求
                        .retry(new BiPredicate<Integer, Throwable>() {
                            @Override
                            public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
                                // 捕获异常
                                Log.e(TAG, "异常错误 =  "+throwable.toString());

                                // 获取当前重试次数
                                Log.e(TAG, "当前重试次数 =  "+integer);

                                //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                                //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                                return true;
                            }
                        })
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });


            //  5. retry（long time,Predicate predicate）
                // 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
                // 参数 = 设置重试次数 & 判断逻辑
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new Exception("发生错误了"));
                        e.onNext(3);
                    }
                })
                        // 拦截错误后，判断是否需要重新发送请求
                        .retry(3, new Predicate<Throwable>() {
                            @Override
                            public boolean test(@NonNull Throwable throwable) throws Exception {
                                // 捕获异常
                                Log.e(TAG, "retry错误: "+throwable.toString());

                                //返回false = 不重新重新发送数据 & 调用观察者的onError（）结束
                                //返回true = 重新发送请求（最多重新发送3次）
                                return true;
                            }
                        })
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "接收到了事件"+ value  );
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "对Error事件作出响应");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "对Complete事件作出响应");
                            }
                        });


    }



    /**
     * OnErrorResuemNext / onExceptionResumeNext 的使用
     * 注：
          onErrorResumeNext（）拦截的错误 = Throwable；若需拦截Exception请用onExceptionResumeNext（）
          若onErrorResumeNext（）拦截的错误 = Exception，则会将错误传递给观察者的onError方法

          onExceptionResumeNext（）拦截的错误 = Exception；若需拦截Throwable请用onErrorResumeNext（）
          若onExceptionResumeNext（）拦截的错误 = Throwable，则会将错误传递给观察者的onError方法
     */
    private void initOnErrorResuemNext() {
        // onErrorResumeNext
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(@NonNull Throwable throwable) throws Exception {

                        // 1. 捕捉错误异常
                        Log.e(TAG, "在onErrorReturn处理了错误: "+throwable.toString() );

                        // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                        return Observable.just(11,22);

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

        // onExceptionResumeNext
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
            }
        })
                .onExceptionResumeNext(new Observable<Integer>() {
                    @Override
                    protected void subscribeActual(Observer<? super Integer> observer) {
                        observer.onNext(11);
                        observer.onNext(22);
                        observer.onComplete();
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


    }



    /**
     * 功能操作符之 OnErrorReturn
     *   遇到错误时，发送1个特殊事件 & 正常终止
         可捕获在它之前发生的异常
     */
    private void initOnErrorReturn() {
          Observable.create(new ObservableOnSubscribe<String>() {
              @Override
              public void subscribe(ObservableEmitter<String> e) throws Exception {
                  e.onNext("1");
                  e.onNext("2");
                  e.onNext("3");
                  e.onError(new Throwable("模拟网络请求出错"));
              }
          }).onErrorReturn(new Function<Throwable, String>() {
                               @Override
                               public String apply(Throwable throwable) throws Exception {
                                   // 捕捉错误异常
                                   Log.e(TAG, "在onErrorReturn处理了错误: "+throwable.toString() );

                                   return "666666";
                                   // 发生错误事件后，发送一个"666"事件，最终正常结束

                               }
                           }
          ).subscribe(new Observer<String>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(String value) {
                  Log.d(TAG, "接收到了事件"+ value  );
              }

              @Override
              public void onError(Throwable e) {
                  Log.d(TAG, "对Error事件作出响应");
              }

              @Override
              public void onComplete() {
                  Log.d(TAG, "对onComplete事件作出响应");
              }
          });

    }



    /**
     * 功能操作符之发送某个事件的生命周期
     */
    private void initDo() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Throwable("发生错误了"));
            }
        })
                // 1. 当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        Log.d(TAG, "doOnEach: " + integerNotification.getValue());
                    }
                })
                // 2. 执行Next事件前调用
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doOnNext: " + integer);
                    }
                })
                // 3. 执行Next事件后调用
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doAfterNext: " + integer);
                    }
                })
                // 4. Observable正常发送事件完毕后调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doOnComplete: ");
                    }
                })
                // 5. Observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "doOnError: " + throwable.getMessage());
                    }
                })
                // 6. 观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.e(TAG, "doOnSubscribe: ");
                    }
                })
                // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doAfterTerminate: ");
                    }
                })
                // 8. 最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doFinally: ");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


    }



    /**
     * 功能操作符之延迟操作符
     */
    private void initDelay() {
       Observable.just("1","2")
               .delay(3,TimeUnit.SECONDS)
               .subscribe(new Consumer<String>() {
                   @Override
                   public void accept(String s) throws Exception {

                   }
               });
    }



    /**
     * 功能操作符之线程切换操作符
     */
    private void initSubscribeOnAndOberveOn() {
                // 使用说明
                // Observable.subscribeOn（Schedulers.io()）：指定被观察者 发送事件的线程（传入RxJava内置的线程类型）
                // Observable.observeOn（AndroidSchedulers.mainThread()）：指定观察者 接收 & 响应事件的线程（传入RxJava内置的线程类型）


               // 注意： 1. 若Observable.subscribeOn（）多次指定被观察者 生产事件的线程，则只有第一次指定有效，其余的指定线程无效
               //        2.若Observable.observeOn（）多次指定观察者 接收 & 响应事件的线程，则每次指定均有效，即每指定一次，就会进行一次线程的切换
        Observable<Integer> observable = Observable.just(1);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()) // 第一次指定观察者线程 = 主线程
                .doOnNext(new Consumer<Integer>() { // 生产事件
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "第一次观察者Observer的工作线程是： " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.newThread()); // 第二次指定观察者线程 = 新的工作线程
                //.subscribe(observer); // 生产事件


        // 注：
        // 1. 整体方法调用顺序：观察者.onSubscribe（）> 被观察者.subscribe（）> 观察者.doOnNext（）>观察者.onNext（）>观察者.onComplete()
        // 2. 观察者.onSubscribe（）固定在主线程进行


    }



    /**
     * 功能操作符之 Subscribe 使用
     */
    private void initSubscribe() {
        //  分步骤的完整调用
         // ① 创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
            }
        });

        // ② 创建观察者
        Observer<String>  o = new Observer<String>() {
            // 通过复写对应方法来 响应 被观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "对Next事件"+ value +"作出响应"  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对onComplete事件作出响应");
            }
        };

        // ③ 订阅关系    前者 = 被观察者（observable）；后者 = 观察者（observer 或 subscriber）
          observable.subscribe(o);

       // 链式调用
        // 1. 创建被观察者 & 生产事件
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
            }
            // 2. 通过通过订阅（subscribe）连接观察者和被观察者
            // 3. 创建观察者 & 定义响应事件的行为
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "对Next事件"+ value +"作出响应"  );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }



    /**
     * count
     * 被观察者发布事件的个数
     */
    private void initCountRxJava() {
        Observable.just(1,2,3,4)
                .count()
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e(TAG, "发送的事件数量 =  "+aLong);
            }
        });

    }



    /**
     * startWith / startWithArray 追加操作符
     * 事件执行的顺序自行验证
     */
    private void initStartWithRaJava() {
     //    在一个被观察者发送事件前，追加发送一些数据
      Observable.just(1,2,3)
              .startWith(4)   // 追加单个数据 = startWith()
              .startWithArray(5,6,7)    // 追加多个数据 = startWithArray()
              .subscribe(new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) throws Exception {
              Log.d(TAG, "接收到了事件"+ integer  );
          }
      }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
              Log.d(TAG, "对Error事件作出响应");
          }
      });

      //  在一个被观察者发送事件前，追加发送被观察者 & 发送数据
        Observable.just(4, 5, 6)
                .startWith(Observable.just(1, 2, 3))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });



    }



    /**
     * Collect 组合操作符
     */
    private void initCollectRaJava() {
        Observable.just(1,1,3,4,2,4)
                // collect(第一个参数：创建容器，第二个参数：手机数据)
                .collect(new Callable<ArrayList<Integer>>() {
                    // 1. 创建数据结构（容器），用于收集被观察者发送的数据
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<Integer>();
                    }
                    // 2. 对发送的数据进行收集
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        // 参数说明：list = 容器，integer = 后者数据
                        integers.add(integer);
                        // 对发送的数据进行收集
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                Log.e(TAG, "本次发送的数据是： "+integers);
                // 最后的结果是一个Integer类型的数组
            }
        });
        
        
    }



    /**
     * Reduce 聚合操作符
     * 具体结果可看打印出的数据
     */
    private void initReduceRaJava() {
      Observable.just(1,2,3,4)
              // 在该复写方法中复写聚合的逻辑
              .reduce(new BiFunction<Integer, Integer, Integer>() {
                  @Override
                  public Integer apply(Integer integer, Integer integer2) throws Exception {
                      Log.e(TAG, "本次计算的数据是： "+integer +" 乘 "+ integer2);
                      return integer * integer2;
                      // 本次聚合的逻辑是：全部数据相乘起来
                      // 原理：第1次取前2个数据相乘，之后每次获取到的数据 = 返回的数据x原始下1个数据每
                  }
              }).subscribe(new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) throws Exception {
              Log.e(TAG, "最终计算的结果是： "+integer);
          }
      });

    }



    /**
     * CombineLatest 合并操作符
     */
    private void initCombineLatestRxJava() {
         Observable.combineLatest(Observable.just(1L, 2L, 3L), Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), new BiFunction<Long, Long, Long>() {
             @Override
             public Long apply(Long aLong, Long aLong2) throws Exception {
                 // aLong = 第1个Observable发送的最新（最后）1个数据
                 // aLong2 = 第2个Observable发送的每1个数据
                 Log.e(TAG, "合并的数据是： "+ aLong + " + "+ aLong2);
                 // 合并的逻辑 = 相加
                 // 即第1个Observable发送的最后1个数据 与 第2个Observable发送的每1个数据进行相加
                 return aLong+aLong2;
             }
         }).subscribe(new Observer<Long>() {
             @Override
             public void onSubscribe(Disposable d) {

             }

             @Override
             public void onNext(Long value) {
                 Log.e(TAG, "合并的结果是： "+value);
             }

             @Override
             public void onError(Throwable e) {

             }

             @Override
             public void onComplete() {

             }
         });

    }



    /**
     * Zip 合并操作符
     *
     *     注意：
     *     尽管被观察者2的事件D没有事件与其合并，但还是会继续发送
           若在被观察者1 & 被观察者2的事件序列最后发送onComplete()事件，
           则被观察者2的事件D也不会发送，可自行测试
     *
     */
    private void initZipRaJava() {
          Observable<Integer> observable1=  Observable.create(new ObservableOnSubscribe<Integer>() {
              @Override
              public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                   Log.d(TAG, "被观察者1发送了事件1");
                     e.onNext(1);
                  // 为了方便展示效果，所以在发送事件后加入2s的延迟
                   Thread.sleep(1000);
                  Log.d(TAG, "被观察者1发送了事件2");
                   e.onNext(2);
                  // 为了方便展示效果，所以在发送事件后加入2s的延迟
                   Thread.sleep(1000);
                  Log.d(TAG, "被观察者1发送了事件3");
                   e.onNext(3);

              }
          }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作;

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "被观察者2发送了事件A");
                e.onNext("A");
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
                Thread.sleep(1000);
                Log.d(TAG, "被观察者2发送了事件B");
                e.onNext("B");
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
                Thread.sleep(1000);
                Log.d(TAG, "被观察者2发送了事件C");
                e.onNext("C");
                Thread.sleep(1000);
                Log.d(TAG, "被观察者2发送了事件D");
                e.onNext("D");
            }
        }).subscribeOn(Schedulers.newThread());// 设置被观察者2在工作线程2中工作
        // 假设不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送;

        // 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer+","+s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }



    /**
     * 没使用ConcatError/MergeError 操作符的结果
     */
    private void initNotUseConcatErrorRxJava() {
           Observable.concat(Observable.create(new ObservableOnSubscribe<Integer>() {
               @Override
               public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext(3);
                   // 发送Error事件，因为无使用concatDelayError，所以第2个Observable将不会发送事件
                    e.onError(new NullPointerException());
                    e.onComplete();
               }
           }),Observable.just(4,5,6)).subscribe(new Observer<Integer>() {
               @Override
               public void onSubscribe(Disposable d) {

               }

               @Override
               public void onNext(Integer value) {
                   Log.d(TAG, "接收到了事件"+ value  );
                   // 结果是  1，2 ， 3 和 onError 事件
               }

               @Override
               public void onError(Throwable e) {
                   Log.d(TAG, "对Error事件作出响应");
               }

               @Override
               public void onComplete() {

               }
           });
    }



    /**
     * 使用ConcatDelayError/MergeDelayError 操作符的结果
     */
    private void initConcatErrorRxJava() {
        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new NullPointerException());
                e.onComplete();
            }
        }),Observable.just(4,5,6)).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件"+ value  );
                // 结果是  1，2 ， 3 ,4,5,6 和 onError 事件
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {

            }
        });
    }



    /**
     * 组合操作符 MergeArray （并行执行）
     */
    private void initMergeArrayRxJava() {
        Observable.mergeArray(Observable.intervalRange(1,3,2,1,TimeUnit.SECONDS),
                // 从1开始发送、共发送3个数据、第1次事件延迟发送时间 = 2s、间隔时间 = 1s
                // .....
                Observable.intervalRange(1,3,2,1,TimeUnit.SECONDS),
                Observable.intervalRange(1,3,2,1,TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    /**
     * 组合操作符 Merge （并行执行）
     */
    private void initMergeRxJava() {
        // merge（）：组合多个被观察者（＜4个）一起发送数据
        // 注：合并后按照时间线并行执行
        Observable.merge(Observable.intervalRange(1,3,2,1,TimeUnit.SECONDS),
                // 从1开始发送、共发送3个数据、第1次事件延迟发送时间 = 2s、间隔时间 = 1s
                // .....
                Observable.intervalRange(1,3,2,1,TimeUnit.SECONDS),
                Observable.intervalRange(1,3,2,1,TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    /**
     * 组合操作符Concat （串行执行）
     */
    private void initConcatArrayRxJava() {
        // concatArray（）：组合多个被观察者一起发送数据（可＞4个）
        // 注：串行执行
        Observable.concatArray(Observable.just(1), Observable.just(2), Observable.just(3), Observable.just(4), Observable.just(5))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    /**
     * 组合操作符ConcatArray   （串行执行）
     */
    private void initConcatRxJava() {
        // concat（）：组合多个被观察者（≤4个）一起发送数据
        // 注：串行执行,个数超过4个就会报错
        Observable.concat(Observable.just(1), Observable.just(2), Observable.just(3), Observable.just(4))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }




    /**
     * 缓存发布的事件
     * 具体原理：可参照图：Buffer缓存操作符
     */
    private void initBufferRaJava() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1)
                // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量(每次往后移动的个数)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> value) {
                        //
                        Log.d(TAG, " 缓存区里的事件数量 = " + value.size());
                        for (Integer v : value) {
                            Log.d(TAG, " 事件 = " + v);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


    }



    /**
     * ConcatMap 等同于 FlatMap
     * 应用场景:  区别在于  有序的将被观察发送的整个事件进行转变。
     * 注：新合并生成的事件序列顺序是有序的，即 严格按照旧序列发送事件的顺序
     */
    private void initConcatMapRaJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                for (int j = 0; j < 3; j++) {
                    list.add("我是事件" + integer + "即将被拆分为事件" + i);
                    // 通过concatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", s);
            }
        });

    }



    /**
     * FlatMap 转换操作符
     * 应用场景：  无序的将被观察者发送的整个事件进行转变
     * 注：新合并生成的事件序列顺序是无序的，即 与旧序列发送事件的顺序无关
     */
    private void initFlatMapRaJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);

            }
            // 采用flatMap（）变换操作符
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                for (int j = 0; j < 3; j++) {
                    list.add("我是事件" + integer + "即将被拆分为事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", s);
            }
        });


    }



         /**
     * Map 转换操作符
     * 应用场景： 数据类型转换
     */
    private void initMapRaJava() {
        // 1. 被观察者发送事件 = 参数为整型 = 1、2、3
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
            // 2. 使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用Map转换操作符，将事件" + integer + "的参数，从整形转换成字符串类型" + integer;
            }
        }).subscribe(new Consumer<String>() {
            // 3. 观察者接收事件时，是接收到变换后的事件 = 字符串类型
            @Override
            public void accept(String integer) throws Exception {
                Log.e("TAG", integer);
            }
        });

    }




    private void initClick() {
        text_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RxJavaStudyTwoActivity.this, RaJavaStudyTwoExampleActivity.class);
                startActivity(intent);
            }
        });

    }



    private void initView() {
        text_one = ((TextView) findViewById(R.id.text_rxjava_create_one));

    }




    private void initRangeLongRaJava() {
        Observable.rangeLong(3, 10).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



    private void initRangeRaJava() {
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 注：若设置为负数，则会抛出异常
        // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件
        Observable.range(3, 10).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }




    /**
     * 同 Interval 一样，不同在于：可以指定事件的个数
     */
    private void initIntervalRangeRaJava() {
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间数字；
        // 参数5 = 时间单位
        // 该例子发送的事件序列特点：
        // 1. 从3开始，一共发送10个事件；
        // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增1，无限个）
        Observable.intervalRange(3, 10, 1, 2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }



    /**
     * 每隔指定时间 就发送 事件
     * interval默认在computation调度器上执行
     * 也可自定义指定线程调度器（第3个参数）：interval(long,TimeUnit,Scheduler)
     */
    private void initIntervalRaJava() {
        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        // 该例子发送的事件序列特点：延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
        Observable.interval(3, 1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



    /**
     * 注：timer操作符默认运行在一个新线程上
     * 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)
     */
    private void initTimerRxJava() {
        // 场景：一般用于检测
        //延迟指定时间后，发送1个数值0（Long类型）
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }



    /**
     * defer 作用: 直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件
     * 重点理解： 动态创建
     */
    Integer i = 10;
    private void initDaterRxJava() {
        // 通过defer 定义被观察者，
        // 注意此时被观察者对象还没有创建
        final Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        // 进行赋值
        i = 15;
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d("RxJava", "接收到的整数是" + value);
                //因为是在订阅时才创建，所以i值会取第2次的赋值,结果就是15
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



    private void initFromIterableRaJava() {
        List<String> list = new ArrayList<>();
        list.add("I");
        list.add("LOVE");
        list.add("YOU");
        Observable.fromIterable(list).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }



    private void initFromArrayRajava() {
        // 切记： 若直接传递一个list集合进去，否则会直接把list当做一个数据元素发送
        Integer[] items = {1, 2, 3, 4, 3, 5};
        Observable.fromArray(items).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



    private void initJustRxJava() {
        // 1. 创建时传入整型1、2、3、4
        // 在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)
        Observable.just(1, 2, 3, 4).subscribe(new Observer<Integer>() {
            // 同下面的逻辑一样
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



    private void initRaJava() {
        // 创建Observale（被观察者（主题））对象
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 在重写的subscribe()方法做一些事情
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                // ......
            }

            // 通过订阅（subscribe）来实现被观察者和观察者之间的联系
        }).subscribe(new Observer<Integer>() {
            // 创建观察者 并 做一些事情
            @Override
            public void onSubscribe(Disposable d) {
                // 默认先调用此方法
            }

            @Override
            public void onNext(Integer value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

}


/**
 *
 * 下列方法一般用于测试使用

 <-- empty()  -->
 // 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
 Observable observable1=Observable.empty();
 // 即观察者接收后会直接调用onCompleted（）

 <-- error()  -->
 // 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
 // 可自定义异常
 Observable observable2=Observable.error(new RuntimeException())
 // 即观察者接收后会直接调用onError（）

 <-- never()  -->
 // 该方法创建的被观察者对象发送事件的特点：不发送任何事件
 Observable observable3=Observable.never();
 // 即观察者接收后什么都不调用


   subscribe() 内部实现（源码分析）
 public Subscription subscribe(Subscriber subscriber) {
 subscriber.onStart();
 // 在观察者 subscriber抽象类复写的方法 onSubscribe.call(subscriber)，用于初始化工作
 // 通过该调用，从而回调观察者中的对应方法从而响应被观察者生产的事件
 // 从而实现被观察者调用了观察者的回调方法 & 由被观察者向观察者的事件传递，即观察者模式
 // 同时也看出：Observable只是生产事件，真正的发送事件是在它被订阅的时候，即当 subscribe() 方法执行时
 }

 delay()  延迟操作符
 // 1. 指定延迟时间
 // 参数1 = 时间；参数2 = 时间单位
 delay(long delay,TimeUnit unit)

 // 2. 指定延迟时间 & 调度器
 // 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器
 delay(long delay,TimeUnit unit,mScheduler scheduler)

 // 3. 指定延迟时间  & 错误延迟
 // 错误延迟，即：若存在Error事件，则如常执行，执行后再抛出错误异常
 // 参数1 = 时间；参数2 = 时间单位；参数3 = 错误延迟参数
 delay(long delay,TimeUnit unit,boolean delayError)

 // 4. 指定延迟时间 & 调度器 & 错误延迟
 // 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器；参数4 = 错误延迟参数
 delay(long delay,TimeUnit unit,mScheduler scheduler,boolean delayError): 指定延迟多长时间并添加调度器，错误通知可以设置是否延迟


  retry 重试操作符
 <-- 1. retry（） -->
 // 作用：出现错误时，让被观察者重新发送数据
 // 注：若一直错误，则一直重新发送

 <-- 2. retry（long time） -->
 // 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
 // 参数 = 重试次数

 <-- 3. retry（Predicate predicate） -->
 // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
 // 参数 = 判断逻辑

 <--  4. retry（new BiPredicate<Integer, Throwable>） -->
 // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
 // 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）

 <-- 5. retry（long time,Predicate predicate） -->
 // 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
 // 参数 = 设置重试次数 & 判断逻辑



 *
 */
