package com.example.dell.rxjavademo.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dell.rxjavademo.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
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
 * 参考：这是一个RaJava系列
 *      https://www.jianshu.com/p/ceb48ed8719d
 *
 *
 */

public class RxJavaStudyTwoActivity extends AppCompatActivity {

    private TextView text_one;
    public static final String TAG = "RXJAVA";

    private List<String> list = new ArrayList<>();

    private Subscription mSubscription;
    private TextView text_two;
    private TextView textThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_study_two);
        initView();


        // 创建操作符的使用
        //①最基本的使用，不考虑任何封装，，实用等
        initRaJava();
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
            initTimerRxJava();  // 延时创建
          //  initIntervalRaJava(); // 周期创建
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
        //initZipRaJava();
        // ⑤ combineLatest  合并多个被观察者
        // 与Zip 的区别： Zip（） = 按个数合并，即1对1合并；CombineLatest（） = 按时间合并，即在同一个时间点上合并
        //initCombineLatestRxJava();
        //  ⑥ combineLatestDelayError（）
        // 作用类似于concatDelayError（） / mergeDelayError（） ，即错误处理，此处不作过多描述
        // ⑦ reduce ()    把被观察者的需要发送的事件聚成一个发送
        // 聚合的本质 ：   聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
        //initReduceRaJava();
        // ⑧ collect()    将被观察者Observable发送的数据事件收集到一个数据结构里
        //initCollectRaJava();
        // ⑨ startWith() /startArrayWith() 在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
        //initStartWithRaJava();
        // ⑩ count     统计被观察者发布事件的个数
        //initCountRxJava();

        // 功能性操作符
        // ①  subscribe                        功能操作符之   订阅即连接观察者和被观察者
        //initSubscribe();
        // ②  subscribeOn() 和 observeOn ()    功能操作符之 线程切换操作符
        // 通过 上述 subscribe 的例子，我们知道观察者和被观察者的发生都是在主线程中完成的，但是我们实际开发中，要比这复杂的很多
        // 我们也必须要遵从的原则"在主线程中更新UI,在子线程中执行耗时操作，所以就出现了subscribeOn 和 observeOn 操作符"
       // initSubscribeOnAndOberveOn();
        // ③ delay()                         功能操作符之 延迟操作符
        //initDelay();
        // ④ do 操作符                       功能操作符之 把控发送某个事件的生命周期
        //initDo();
        // ⑤  错误操作符     发生错误我们做的一些处理
        //initOnErrorReturn();
        //initOnErrorResuemNext();
        // ⑥ retry 重试操作符
        //initRetry();
        // ⑦ retryUntil()   具体使用类似于retry（Predicate predicate），唯一区别：返回 true 则不重新发送数据事件。此处不作过多描述
        // ⑧ retryWhen()    遇到错误时，将发生的错误传递给一个新的被观察者（Observable），
        // 并决定是否需要重新订阅原始被观察者（Observable）& 发送事件
        //initRetryWhen();
        // ⑨ repeat()  重复发送（无条件的）/ repeatWhen() 重复发送（有条件的）
        //initRepeat();
        //initRepeatWhen();


        // 过滤性操作符
        // 指定条件的过滤事件
        //①  filter
        //initFilterRaJava();
        // ② ofType
        //initOfTypeRaJava();
        // ③ skip / skipLast
        //initSkipRaJava();
        // ④ distinct  / distinctUntilChanged     前者; 过滤事件序列中重复的事件 ， 后者： 过滤事件序列中连续重复的事件
        //initDistinctRaJava();


        //根据 指定事件数量 过滤事件
        // ① take / takeLast       通过设置指定事件的数量，只发送指定数量的事件 （两者的区别在于前后）
        //initTakeRaJava();


        // 指定时间 过滤事件
        // ① throttleFirst / throttleLast       在某段时间内，只发送该段时间的第一个事件/ 最后一个事件
        //initThrottleRaJava();
        // ② sample  与上述 throttleLast  同理（一样的用法）
        // ③ throttleWithTimeout  / debounce
        //initThrottleWithTimeoutRaJava();


        // 指定事件的位置，过滤事件
        // ① firstElement / lastElement    仅取第一个元素/ 最后一个元素
        //initFirstElementRaJava();
        // ② elementAt     获取指定位置的元素（通过索引，支持越界）
        //initElementAtRaJava();
        // ③ elementAtError       在elementAt的基础上，索引越界即报异常
        //initElementAtErrorRaJava();

        // 条件/布尔操作符
         // ① all    判断发送的数据是否都满足条件
         // initAllRxjava();
        //② takeWhile  判断发送的数据是否都满足条件（同上区别： 满足才发送，不满足则不发送）
        //  initTakeWithRxjava();
         // ③ skipWhile  判断发送的数据是否都满足条件（同上区别： 发送的是不满足的数据）
        //  initSkipWhileRxjava();
          // ④ takeUntil  执行到满足当前条件，则停止发送数据
          initTakeUntilRxJava();
           // 该判断条件也可以是Observable
         // initTakeUntilTwoRxJava();
          // ⑤ skipUntil  执行到满足当前条件，则发送之后的数据
        //   initSkipUntilRxJava();
          // ⑥ sequenceEqual   比较两个Observable 发送的数据是否相等
          // initSequenceEqualRxJava();
          // ⑦ contains     发送的数据是否包含某个具体的值
         //  initContainsRxJava();
          // ⑧ isEmpty     发送的数据是否为空
        //   initIsEmpty();
          // ⑨ amb       当需要发送多个Observable ，只发送先发送数据的Observable，其余的责备抛弃
        //   initAmbRxJava();
          // ⑩ defaultIfEmpty   在不发送任何有效事件下，仅发送onComplete事件，可以发送一个默认值
         //  initDefaultEmptyRxJava();



           //背压策略  被观察者发送事件过快，观察者处理不过来。
           // initFlowable();
           // initFlowableLimit();
           // initFlowableLimitAsync();
            // RaJava2.0 内部也帮我们实现了背压的方法：
            //  .onBackpressureBuffer() // 添加背压策略封装好的方法，此处选择Buffer模式，即缓存区大小无限制
            //  .onBackpressureDrop()   onBackpressureLatest()      // 自行了解
    }

    /**
     * 背压处理： 异步处理
     *    在异步中：
     *     反向控制的原理是：通过RxJava内部固定调用被观察者线程中的request(n)  从而
     *                      反向控制被观察者的发送事件速度
     *
     */
    private void initFlowableLimitAsync() {
        // 被观察者：一共需要发送500个事件，但真正开始发送事件的前提 = FlowableEmitter.requested()返回值 ≠ 0
        // 观察者：每次接收事件数量 = 48（点击按钮）
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "观察者可接收事件数量 = " + emitter.requested());
                boolean flag; //设置标记位控制

                // 被观察者一共需要发送500个事件
                for (int i = 0; i < 500; i++) {
                    flag = false;
                   // 若requested() == 0则不发送
                    while (emitter.requested() == 0) {
                        if (!flag) {
                            Log.d(TAG, "不再发送");
                            flag = true; }
                    }
                    // requested() ≠ 0 才发送
                    Log.d(TAG, "发送了事件" + i + "，观察者可接收事件数量 = " + emitter.requested());
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                // 步骤2：创建观察者 =  Subscriber & 建立订阅关系
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                        // 相同点：Subscription参数具备Disposable参数的作用，即Disposable.dispose()切断连接,
                        // 同样的调用Subscription.cancel()切断连接
                        // 不同点：Subscription增加了void request(long n)



                        Log.d(TAG, "onSubscribe");
                        // 可通过点击事件动态控制
                        mSubscription = s;
                        // 初始状态 = 不接收事件；通过点击按钮接收事件
                    }

                    @Override
                    public void onNext(Integer s) {
                        Log.d(TAG, "接收到了事件" + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError" + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });


    }
    /**
     *  背压处理： 根据  观察者响应式拉取  控制   被观察者的发布事件速度
     *  在同步中 requested  具有叠加性 ，因为没有缓存区。
     *             s.request(10); // 第1次设置观察者每次能接受10个事件
                   s.request(20); // 第2次设置观察者每次能接受20个事件
                           实时更新性  emitter.requested()的值
                           异常：
                          ①当FlowableEmitter.requested()减到0时，则代表观察者已经不可接收事件
                          此时被观察者若继续发送事件，则会抛出MissingBackpressureException异常
                          ② 如观察者可接收事件数量 = 1，当被观察者发送第2个事件时，就会抛出异常
                              （意思就是我只接收一个，你却给我发两个甚至更多）

                   

     */
    private void initFlowableLimit() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                // 调用emitter.requested()获取当前观察者需要接收的事件数量
                long n = emitter.requested();
                Log.d(TAG, "观察者可接收事件" + n);
                
                // 根据emitter.requested()的值，即当前观察者需要接收的事件数量来发送事件
                for (int i = 0; i < n; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(String.valueOf(i));
                }



            }
        }, BackpressureStrategy.ERROR)
                // 步骤2：创建观察者 =  Subscriber & 建立订阅关系
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                        // 相同点：Subscription参数具备Disposable参数的作用，即Disposable.dispose()切断连接,
                        // 同样的调用Subscription.cancel()切断连接
                        // 不同点：Subscription增加了void request(long n)



                        Log.d(TAG, "onSubscribe");
                        // 可通过点击事件动态控制
                        s.request(3);
                        // 作用：决定观察者能够接收多少个事件
                        // 如设置了s.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                        // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "接收到了事件" + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError" + t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    /**
     * 背压处理 之观察者响应式拉取
     */
    private void initFlowable() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "发送事件 1");
                emitter.onNext("1"); Log.d(TAG, "发送事件 2");
                emitter.onNext("2"); Log.d(TAG, "发送事件 3");
                emitter.onNext("3"); Log.d(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io()) // 设置被观察者在io线程中进行
                .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中进行
                // 步骤2：创建观察者 =  Subscriber & 建立订阅关系
                .subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                // 相同点：Subscription参数具备Disposable参数的作用，即Disposable.dispose()切断连接,
                // 同样的调用Subscription.cancel()切断连接
                // 不同点：Subscription增加了void request(long n)



                Log.d(TAG, "onSubscribe");
                s.request(3);
                // 作用：决定观察者能够接收多少个事件
                // 如设置了s.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                // 官方默认推荐使用Long.MAX_VALUE，即s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "接收到了事件" + s);
            }

            @Override
            public void onError(Throwable t) {
                Log.d(TAG, "onError" + t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });

    }

    /**
     * 场景：在不发送任何有效事件下，仅发送onComplete事件，可以发送一个默认值
     */
    private void initDefaultEmptyRxJava() {
       Observable.create(new ObservableOnSubscribe<Integer>() {
           @Override
           public void subscribe(ObservableEmitter<Integer> e) throws Exception {
               //不发送任何有效事件
               e.onNext(null);
               e.onNext(null);
               e.onNext(null);

               // 只发送Complete事件
               e.onComplete();

           }
           // 发送一个默认值
       }).defaultIfEmpty(10)
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

           }

           @Override
           public void onComplete() {

           }
       });


        // 结果是：   10
    }


    /**
     * 场景：   当需要发送多个Observable ，只发送先发送数据的Observable，其余的责备抛弃
     *
     */
    private void initAmbRxJava() {
        // 设置2个需要发送的Observable & 放入到集合中
       List<Observable<Integer>> list = new ArrayList<>();
        // 第1个Observable延迟1秒发射数据
        list.add(Observable.just(1,2,3).delay(1,TimeUnit.SECONDS));
        // 第2个Observable正常发送数据
        list.add(Observable.just(4,5,6));

        // 一共需要发送2个Observable的数据
        // 但由于使用了amba（）,所以仅发送先发送数据的Observable
        // 即第二个（因为第1个延时了）
        Observable.amb(list).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "接收到了事件 "+integer);
            }
        });

        // 输出结果： 4.5.6

    }


    /**
     * 场景： 判断发送的数据是否为空
     */
    private void initIsEmpty() {
         Observable.just(1,3,4,5,3).isEmpty().subscribe(new Consumer<Boolean>() {
             @Override
             public void accept(Boolean aBoolean) throws Exception {
                 Log.d(TAG,"结果是"+aBoolean);
             }
         });
         // 输出的结果： false
         // 若为空，返回 true；否则，返回 false
    }


    /**
     * 场景：发送的数据是否包含某个具体的值
     */
    private void initContainsRxJava() {
       Observable.just(1,2,4,5,30)
               .contains(2)
               .subscribe(new Consumer<Boolean>() {
           @Override
           public void accept(Boolean aBoolean) throws Exception {
               Log.d(TAG,"结果是"+aBoolean);
           }
       });

    }

    /**
     * 场景：比较两个Observable 发送的数据是否相等
     */
    private void initSequenceEqualRxJava() {
         Observable.sequenceEqual(Observable.just(1,3,4),Observable.just(1,3,4))
                 .subscribe(new Consumer<Boolean>() {
             @Override
             public void accept(Boolean aBoolean) throws Exception {
                 Log.d(TAG,"两个是否相同"+aBoolean);
             }
         });

    }


    /**
     * 场景： 判断条件事Observable
     * skipUntil  条件操作符
     */
    private void initSkipUntilRxJava() {
        // （原始）第1个Observable：每隔1s发送1个数据 = 从0开始，每次递增1
         Observable.interval(1,TimeUnit.SECONDS)
                 // 第2个Observable：延迟5s后开始发送1个Long型数据
                 .skipUntil(Observable.timer(5,TimeUnit.SECONDS))
                 .subscribe(new Observer<Long>() {
             @Override
             public void onSubscribe(Disposable d) {
                 Log.d(TAG, "开始采用subscribe连接");
             }

             @Override
             public void onNext(Long value) {
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

        //5s后（ skipUntil（） 传入的Observable开始发送数据），（原始）第1个Observable的数据才开始发送
        // 输出结果：  开始采用subscribe连接
        // 接收到了事件4，
        // 接收到了事件5
        // 接收到了事件6
        // 接收到了事件7
        //接收到了事件 ....


    }

    /**
     * 场景： 判断条件是Observable
     * takeUntil  条件操作符
     */
    private void initTakeUntilTwoRxJava() {
        // （原始）第1个Observable：每隔1s发送1个数据 = 从0开始，每次递增1
        Observable.interval(1,TimeUnit.SECONDS)
                // 第2个Observable：延迟5s后开始发送1个Long型数据
                .takeUntil(Observable.timer(5,TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Long value) {
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

        // 输出结果： 当第 5s 时，第2个 Observable 开始发送数据，于是（原始）第1个 Observable 停止发送数据
        // 开始采用subscribe连接
        //        接收到了事件0
        //        接收到了事件1
        //        接收到了事件2
        //        接收到了事件3
        //        接收到了事件4
        //对Complete事件作出响应

    }

    private  boolean   isOk ;
    /**
     * 场景： 执行到满足条件事，就停止发送数据
     * takeUntil  条件操作符
     */
    private void initTakeUntilRxJava() {
         Observable.interval(1,TimeUnit.SECONDS).takeUntil(new Predicate<Long>() {
             @Override
             public boolean test(Long aLong) throws Exception {
                 return isOk == true;
                 // 返回true时，就停止发送事件
                 // 当发送的数据满足>3时，就停止发送Observable的数据
             }
         }).subscribe(new Consumer<Long>() {
             @Override
             public void accept(Long aLong) throws Exception {
                 Log.d(TAG,"发送了事件 "+ aLong);
             }
         });
         // 输出结果： 0.1.2.3.4 （4 > 3 则停止发送数据）
    }

    /**
     * 场景： 判断发射的数据是否都满足条件
     * skipWhile 条件操作符  (发送不满足数据)
     */
    private void initSkipWhileRxjava() {
       Observable.interval(1,TimeUnit.SECONDS).skipWhile(new Predicate<Long>() {
           @Override
           public boolean test(Long aLong) throws Exception {
               // 直到判断条件不成立 = false = 发射的数据≥5，才开始发送数据
               return aLong<5;
           }
       }).subscribe(new Consumer<Long>() {
           @Override
           public void accept(Long aLong) throws Exception {
               Log.d(TAG,"发送了事件 "+ aLong);
           }
       });
        // 输出结果: 5,6,7,8,9,10......(依次往后)
    }

    /**
     * 场景： 判断发射的数据是否都满足条件
     * takeWhile 条件操作符 (发送满足数据)
     */
    private void initTakeWithRxjava() {
        // 1. 每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1,TimeUnit.SECONDS)
                // 2. 通过takeWhile传入一个判断条件
                .takeWhile(new Predicate<Long>() {
            @Override
            public boolean test(Long aLong) throws Exception {
                // 当发送的数据满足<3时，才发送Observable的数据
                return aLong < 3;
            }
        }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.d(TAG,"发送了事件 "+ aLong);
            }
        });
       // 最后的结果：0,1，2
    }

    /**
     * 场景：判断发射的数据是否都满足条件
     * all  条件操作符
     */
    private void initAllRxjava() {
        Observable.just(1,2,3,4,5,8)
                .all(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer <= 10;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                 Log.d(TAG,"输出结果："+aBoolean);
            }
        });
       // 最后的结果肯定是true
    }

    /**
     * 场景： 在 elementAt 的基础上，报错
     */
    private void initElementAtErrorRaJava() {
        Observable.just(1, 2, 4, 5, 6)
                .elementAt(10) // 索引为10 的元素
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "获取到的事件元素是： " + integer);
                        // 结果: 报错，终止
                    }
                });

    }


    /**
     * 场景： 获取指定位置的元素
     * 索引： 从0 开始
     */
    private void initElementAtRaJava() {
        // ①   获取指定位置的元素
        Observable.just(1, 2, 4, 5, 6)
                .elementAt(2) // 索引为2 的元素
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "获取到的事件元素是： " + integer);
                        // 结果是： 4
                    }
                });

        // ② 若指定的索引大于事件的个数，则指定默认的元素
        Observable.just(1, 3, 4, 5, 6)
                .elementAt(6, 10)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "获取到的事件元素是： " + integer);
                        // 结果是： 10
                    }
                });
    }


    /**
     * 场景： 筛选出特定位置的事件
     */
    private void initFirstElementRaJava() {
        // 获取第一个元素
        Observable.just(1, 3, 4, 5, 6)
                .firstElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "获取到的第一个事件是： " + integer);
                    }
                });

        // 获取最后一个元素
        Observable.just(1, 3, 4, 5, 6)
                .lastElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "获取到的第后一个事件是： " + integer);
                    }
                });

    }


    /**
     * 注意：
     * 发送数据事件时，若2次发送事件的间隔＜指定时间，就会丢弃前一次的数据，直到指定时间内都没有新数据发射时才会发送后一次的数据
     */
    private void initThrottleWithTimeoutRaJava() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 隔段事件发送时间
                e.onNext(1);
                Thread.sleep(500);
                e.onNext(2); // 1和2之间的间隔小于指定时间1s，所以前1次数据（1）会被抛弃，2会被保留
                Thread.sleep(1500);  // 因为2和3之间的间隔大于指定时间1s，所以之前被保留的2事件将发出
                e.onNext(3);
                Thread.sleep(1500);  // 因为3和4之间的间隔大于指定时间1s，所以3事件将发出
                e.onNext(4);
                Thread.sleep(500); // 因为4和5之间的间隔小于指定时间1s，所以前1次数据（4）会被抛弃，5会被保留
                e.onNext(5);
                Thread.sleep(500); // 因为5和6之间的间隔小于指定时间1s，所以前1次数据（5）会被抛弃，6会被保留
                e.onNext(6);
                Thread.sleep(1500); // 因为6和Complete实践之间的间隔大于指定时间1s，所以之前被保留的6事件将发出

                e.onComplete();
            }
        }).throttleWithTimeout(1, TimeUnit.SECONDS)//每1秒中采用数据
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
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
     * 场景： 特定时间内的第一个事件/ 最后一个事件
     * 以下结果，自行测试。重点： 明白原理，可结合图片
     */
    private void initThrottleRaJava() {
        // 在某段时间内，只发送该段时间内第1次事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 隔段事件发送时间
                e.onNext(1);
                Thread.sleep(500);

                e.onNext(2);
                Thread.sleep(400);

                e.onNext(3);
                Thread.sleep(300);

                e.onNext(4);
                Thread.sleep(300);

                e.onNext(5);
                Thread.sleep(300);

                e.onNext(6);
                Thread.sleep(400);

                e.onNext(7);
                Thread.sleep(300);
                e.onNext(8);

                Thread.sleep(300);
                e.onNext(9);

                Thread.sleep(300);
                e.onComplete();
            }
        }).throttleFirst(1, TimeUnit.SECONDS)//每1秒中采用数据
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
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


        // 在某段时间内，只发送该段时间内最后1次事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 隔段事件发送时间
                e.onNext(1);
                Thread.sleep(500);

                e.onNext(2);
                Thread.sleep(400);

                e.onNext(3);
                Thread.sleep(300);

                e.onNext(4);
                Thread.sleep(300);

                e.onNext(5);
                Thread.sleep(300);

                e.onNext(6);
                Thread.sleep(400);

                e.onNext(7);
                Thread.sleep(300);
                e.onNext(8);

                Thread.sleep(300);
                e.onNext(9);

                Thread.sleep(300);
                e.onComplete();
            }
        }).throttleLast(1, TimeUnit.SECONDS)//每1秒中采用数据
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
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
     * 场景： 设置指定数量的事件发送
     */
    private void initTakeRaJava() {
        //指定观察者对多能接收的事件个数
        Observable.just(1, 3, 4, 5, 6, 5)
                .take(2)  // 只能接收2 个
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "过滤后得到的事件是：" + value);
                        // 结果是： 1,3
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 指定观察者只能接收被观察者的最后几个事件
        Observable.just(1, 2, 3, 4, 5)
                .takeLast(2)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "过滤后得到的事件是：" + value);
                        // 结果是： 4,5
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
     * 过滤重复的事件
     */
    private void initDistinctRaJava() {
        // 使用1：过滤事件序列中重复的事件
        Observable.just(1, 2, 4, 5, 4, 1)
                .distinct()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "不重复的整型事件元素是： " + value);
                        // 结果是： 1,2,4,5
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 使用2：过滤事件序列中 连续重复的事件
        // 下面序列中，连续重复的事件 = 4,4,5,5
        Observable.just(1, 2, 3, 1, 2, 3, 4, 4, 5, 5)
                .distinctUntilChanged()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "不连续重复的整型事件元素是： " + value);
                        // 结果是： 1,2,3,1,2,3,4,5
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
     * 跳过指定的某个事件
     */
    private void initSkipRaJava() {
        // 使用1 ： 根据顺序跳过数据项
        Observable.just(1, 2, 3, 4, 5)
                .skip(1)       //跳过数据项的第一项
                .skipLast(2)   //跳过数据项的最后两项
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "获取到的整型事件元素是： " + value);
                        // 结果是： 2，3  过滤掉了 1,4,5
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 使用2：根据时间跳过数据项
        // 发送事件特点：发送数据0-5，每隔1s发送一次，每次递增1；第1次发送延迟1s
        Observable.intervalRange(0, 5, 1, 1, TimeUnit.SECONDS)
                .skip(1, TimeUnit.SECONDS)      // 跳过第一秒发送的数据
                .skipLast(1, TimeUnit.SECONDS)  // 跳过最后一秒发送的数据
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "获取到的整型事件元素是： " + value);
                        // 结果是： 1，2，3  ，过滤掉了 0 ， 4
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
     * 使用场景： 过滤特定的数据类型的事件（筛选出自己想要的数据）
     */
    private void initOfTypeRaJava() {
        Observable.just(1, "asdf", 3, "1", "我要")
                .ofType(Integer.class)   // 筛选出 整型数据
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "获取到的整型事件元素是： " + value);   // 结果肯定是： 1 ，3
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
     * 使用场景： 过滤特定的条件的事件
     */
    private void initFilterRaJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onNext(5);

            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                // 根据test()的返回值 对被观察者发送的事件进行过滤 & 筛选
                // a. 返回true，则继续发送
                // b. 返回false，则不发送（即过滤）
                return integer > 3;
                // 过滤掉 <= 3 的事件
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "过滤后得到的事件是：" + value);
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
     * 有条件的重复发送被观察者事件
     * <p>
     * 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable），
     * 以此决定是否重新订阅 & 发送原来的 Observable
     * <p>
     * 若新被观察者（Observable）返回1个Complete / Error事件，则不重新订阅 & 发送原来的 Observable
     * 若新被观察者（Observable）返回其余事件时，则重新订阅 & 发送原来的 Observable
     */
    private void initRepeatWhen() {
        Observable.just(1, 2, 4).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，这里我们使用的是flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable
                // 此处有2种情况：
                // 1. 若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                // 2. 若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 情况1：若新被观察者（Observable）返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable
                        return Observable.empty();
                        // Observable.empty() = 发送Complete事件，但不会回调观察者的onComplete（）

                        // return Observable.error(new Throwable("不再重新订阅事件"));
                        // 返回Error事件 = 回调onError（）事件，并接收传过去的错误信息。

                        // 情况2：若新被观察者（Observable）返回其余事件，则重新订阅 & 发送原来的 Observable
                        // return Observable.just(1);
                        // 仅仅是作为1个触发重新订阅被观察者的通知，发送的是什么数据并不重要，只要不是Complete（） /  Error（）事件
                    }
                });

            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应：" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });


    }


    /**
     * 重复发送被观察者事件
     */
    private void initRepeat() {
        // 不传入参数 = 重复发送次数 = 无限次
        // repeat（）；
        // 传入参数 = 重复发送次数有限
        // repeatWhen（Integer int ）；

        // 注：
        // 1. 接收到.onCompleted()事件后，触发重新订阅 & 发送
        // 2. 默认运行在一个新的线程上

        // 具体使用
        Observable.just(1, 2, 3, 4)
                .repeat(3) // 重复创建次数 =- 3次
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
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
     * 重试操作符之retryWhen（）
     */
    private void initRetryWhen() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 遇到error事件才会回调
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                        // 返回Observable<?> = 新的被观察者 Observable（任意类型）
                        // 此处有两种情况：
                        // 1. 若 新的被观察者 Observable发送的事件 = Error事件，那么 原始Observable则不重新发送事件：
                        // 2. 若 新的被观察者 Observable发送的事件 = Next事件 ，那么原始的Observable则重新发送事件：
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

                                // 1. 若返回的Observable发送的事件 = Error事件，则原始的Observable不重新发送事件
                                // 该异常错误信息可在观察者中的onError（）中获得
                                return Observable.error(new Throwable("retryWhen终止啦"));

                                // 2. 若返回的Observable发送的事件 = Next事件，则原始的Observable重新发送事件（若持续遇到错误，则持续重试）
                                // return Observable.just(1);
                            }
                        });

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应" + e.toString());
                        // 获取异常错误信息
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


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
                        Log.d(TAG, "接收到了事件" + value);
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
                        Log.d(TAG, "接收到了事件" + value);
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
                        Log.e(TAG, "retry错误: " + throwable.toString());

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
                        Log.d(TAG, "接收到了事件" + value);
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
                        Log.e(TAG, "异常错误 =  " + throwable.toString());

                        // 获取当前重试次数
                        Log.e(TAG, "当前重试次数 =  " + integer);

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
                        Log.d(TAG, "接收到了事件" + value);
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
                        Log.e(TAG, "retry错误: " + throwable.toString());

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
                        Log.d(TAG, "接收到了事件" + value);
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
     * onErrorResumeNext（）拦截的错误 = Throwable；若需拦截Exception请用onExceptionResumeNext（）
     * 若onErrorResumeNext（）拦截的错误 = Exception，则会将错误传递给观察者的onError方法
     * <p>
     * onExceptionResumeNext（）拦截的错误 = Exception；若需拦截Throwable请用onErrorResumeNext（）
     * 若onExceptionResumeNext（）拦截的错误 = Throwable，则会将错误传递给观察者的onError方法
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
                        Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());

                        // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                        return Observable.just(11, 22);

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
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
                        Log.d(TAG, "接收到了事件" + value);
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
     * 遇到错误时，发送1个特殊事件 & 正常终止
     * 可捕获在它之前发生的异常
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
                                 Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());

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
                Log.d(TAG, "接收到了事件" + value);
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
                        Log.d(TAG, "接收到了事件" + value);
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
        Observable.just("1", "2")
                .delay(3, TimeUnit.SECONDS)
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
        Observer<String> o = new Observer<String>() {
            // 通过复写对应方法来 响应 被观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
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
                Log.d(TAG, "对Next事件" + value + "作出响应");
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
        Observable.just(1, 2, 3, 4)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "发送的事件数量 =  " + aLong);
                    }
                });

    }


    /**
     * startWith / startWithArray 追加操作符
     * 事件执行的顺序自行验证
     */
    private void initStartWithRaJava() {
        //    在一个被观察者发送事件前，追加发送一些数据
        Observable.just(1, 2, 3)
                .startWith(4)   // 追加单个数据 = startWith()
                .startWithArray(5, 6, 7)    // 追加多个数据 = startWithArray()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "接收到了事件" + integer);
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
                        Log.d(TAG, "接收到了事件" + value);
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
        Observable.just(1, 1, 3, 4, 2, 4)
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
                Log.e(TAG, "本次发送的数据是： " + integers);
                // 最后的结果是一个Integer类型的数组
            }
        });


    }


    /**
     * Reduce 聚合操作符
     * 具体结果可看打印出的数据
     */
    private void initReduceRaJava() {
        Observable.just(1, 2, 3, 4)
                // 在该复写方法中复写聚合的逻辑
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.e(TAG, "本次计算的数据是： " + integer + " 乘 " + integer2);
                        return integer * integer2;
                        // 本次聚合的逻辑是：全部数据相乘起来
                        // 原理：第1次取前2个数据相乘，之后每次获取到的数据 = 返回的数据x原始下1个数据每
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "最终计算的结果是： " + integer);
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
                Log.e(TAG, "合并的数据是： " + aLong + " + " + aLong2);
                // 合并的逻辑 = 相加
                // 即第1个Observable发送的最后1个数据 与 第2个Observable发送的每1个数据进行相加
                return aLong + aLong2;
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {
                Log.e(TAG, "合并的结果是： " + value);
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
     * <p>
     * 注意：
     * 尽管被观察者2的事件D没有事件与其合并，但还是会继续发送
     * 若在被观察者1 & 被观察者2的事件序列最后发送onComplete()事件，
     * 则被观察者2的事件D也不会发送，可自行测试
     */
    private void initZipRaJava() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
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
                return integer + "," + s;
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
        }), Observable.just(4, 5, 6)).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
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
        }), Observable.just(4, 5, 6)).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
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
        Observable.mergeArray(Observable.intervalRange(1, 3, 2, 1, TimeUnit.SECONDS),
                // 从1开始发送、共发送3个数据、第1次事件延迟发送时间 = 2s、间隔时间 = 1s
                // .....
                Observable.intervalRange(1, 3, 2, 1, TimeUnit.SECONDS),
                Observable.intervalRange(1, 3, 2, 1, TimeUnit.SECONDS))
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
        Observable.merge(Observable.intervalRange(1, 3, 2, 1, TimeUnit.SECONDS),
                // 从1开始发送、共发送3个数据、第1次事件延迟发送时间 = 2s、间隔时间 = 1s
                // .....
                Observable.intervalRange(1, 3, 2, 1, TimeUnit.SECONDS),
                Observable.intervalRange(1, 3, 2, 1, TimeUnit.SECONDS))
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
                    list.add("我是事件" + integer + "即将被拆分为事件" + j);
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

        text_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubscription.request(48);
            }
        });
        textThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RxJavaStudyTwoActivity.this, RxJavaThreeActivity.class);
                startActivity(intent);
            }
        });

    }


    private void initView() {
        text_one = ((TextView) findViewById(R.id.text_rxjava_create_one));
        text_two = ((TextView) findViewById(R.id.text_rxjava_create_two));
        textThree = ((TextView) findViewById(R.id.text_rxjava_create_three));
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
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
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
 * 下列方法一般用于测试使用
 * <p>
 * <-- empty()  -->
 * // 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
 * Observable observable1=Observable.empty();
 * // 即观察者接收后会直接调用onCompleted（）
 * <p>
 * <-- error()  -->
 * // 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
 * // 可自定义异常
 * Observable observable2=Observable.error(new RuntimeException())
 * // 即观察者接收后会直接调用onError（）
 * <p>
 * <-- never()  -->
 * // 该方法创建的被观察者对象发送事件的特点：不发送任何事件
 * Observable observable3=Observable.never();
 * // 即观察者接收后什么都不调用
 * <p>
 * <p>
 * subscribe() 内部实现（源码分析）
 * public Subscription subscribe(Subscriber subscriber) {
 * subscriber.onStart();
 * // 在观察者 subscriber抽象类复写的方法 onSubscribe.call(subscriber)，用于初始化工作
 * // 通过该调用，从而回调观察者中的对应方法从而响应被观察者生产的事件
 * // 从而实现被观察者调用了观察者的回调方法 & 由被观察者向观察者的事件传递，即观察者模式
 * // 同时也看出：Observable只是生产事件，真正的发送事件是在它被订阅的时候，即当 subscribe() 方法执行时
 * }
 * <p>
 * delay()  延迟操作符
 * // 1. 指定延迟时间
 * // 参数1 = 时间；参数2 = 时间单位
 * delay(long delay,TimeUnit unit)
 * <p>
 * // 2. 指定延迟时间 & 调度器
 * // 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器
 * delay(long delay,TimeUnit unit,mScheduler scheduler)
 * <p>
 * // 3. 指定延迟时间  & 错误延迟
 * // 错误延迟，即：若存在Error事件，则如常执行，执行后再抛出错误异常
 * // 参数1 = 时间；参数2 = 时间单位；参数3 = 错误延迟参数
 * delay(long delay,TimeUnit unit,boolean delayError)
 * <p>
 * // 4. 指定延迟时间 & 调度器 & 错误延迟
 * // 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器；参数4 = 错误延迟参数
 * delay(long delay,TimeUnit unit,mScheduler scheduler,boolean delayError): 指定延迟多长时间并添加调度器，错误通知可以设置是否延迟
 * <p>
 * <p>
 * retry 重试操作符
 * <-- 1. retry（） -->
 * // 作用：出现错误时，让被观察者重新发送数据
 * // 注：若一直错误，则一直重新发送
 * <p>
 * <-- 2. retry（long time） -->
 * // 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
 * // 参数 = 重试次数
 * <p>
 * <-- 3. retry（Predicate predicate） -->
 * // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
 * // 参数 = 判断逻辑
 * <p>
 * <--  4. retry（new BiPredicate<Integer, Throwable>） -->
 * // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
 * // 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）
 * <p>
 * <-- 5. retry（long time,Predicate predicate） -->
 * // 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
 * // 参数 = 设置重试次数 & 判断逻辑
 */
