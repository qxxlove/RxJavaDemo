package com.example.dell.rxjavademo.rxjava;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.bean.TanslationEntity;
import com.example.dell.rxjavademo.retrofit.Api.ExampleAPI;
import com.example.dell.rxjavademo.utils.BaseUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description: 针对创建操作符，进行实战
 * autour: TMM
 * date: 2018/1/22 15:17 
 * update: 2018/1/22
 * version:
 *  实现：通过轮询请求金山词霸API ,获取返回值
 *
 *
*/

public class RaJavaStudyTwoExampleActivity extends AppCompatActivity {



    private static final String TAG = "Rxjava";

    // 设置变量 = 模拟轮询服务器次数
    private int i = 0 ;


    private TextView text_one_request;
    private TextView text_two_request;
    private TextView text_three_request;
    private TextView text_four_request;
    private TextView text_five_request;
    private TextView text_six_request;
    private EditText editText_name;
    private EditText editText_age;
    private EditText editText_interesting;
    private Button btn_commit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ra_java_study_two_example);
        initView();
        initCLick();

    }

    private void initView() {
        text_one_request = ((TextView) findViewById(R.id.text_rxjava_create_example));
        text_two_request = ((TextView) findViewById(R.id.text_rxjava_map_one));
        text_three_request = ((TextView) findViewById(R.id.text_rxjava_map_two));
        text_four_request = ((TextView) findViewById(R.id.text_rxjava_map_three));
        text_five_request = ((TextView) findViewById(R.id.text_rxjava_map_four));
        text_six_request = ((TextView) findViewById(R.id.text_rxjava_map_five));

        editText_name = ((EditText) findViewById( R.id.edit_input_name));
        editText_age = ((EditText) findViewById( R.id.edit_input_age));
        editText_interesting = ((EditText) findViewById( R.id.edit_input_interesting));
        btn_commit = ((Button) findViewById(R.id.btn_commit));

    }

    private void initCLick() {
        // ①无条件轮询，即不添加任何限制条件
        //此演示是正常运行的，业实现了我们想要的效果
        // 不足：因为这是Demo,并没有考虑回收问题（当Activity 界面已经退出，网络请求还在执行）
        text_one_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRaJava();
                //initLimitRxJava();
            }
        });
        // 有条件的轮询
        //initLimitRxJava();

        //② 使用转换操作符解决网络请求嵌套问题
        // 现在我们模拟用户注册成功即登录也成功的场景，应为我们没有服务，所以就同一个接口调用两次，来实现我们的场景
        // 此功能测试，是正常的
        text_two_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFlatMapRaJava();
            }
        });

        // ③ 使用组合操作符解决从磁盘/ 内存取出数据的效率（时间。流量）
        text_three_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initConcatAndFirstElement();
            }
        });

        // 合并数据源（网络和本地）
        text_four_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMerge();
            }
        });

        //从不同数据源（2个服务器）获取数据，即 合并网络请求的发送
        text_five_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initZip();
            }
        });

        // 如，填写表单时，需要表单里所有信息（姓名、年龄、职业等）都被填写后，才允许点击 "提交" 按钮
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initConbineLatest();
            }
        });


    }


    /**
     * 如，填写表单时，需要表单里所有信息（姓名、年龄、职业等）都被填写后，才允许点击 "提交" 按钮
     */
    private void initConbineLatest() {
        /*
         * 步骤1：为每个EditText设置被观察者，用于发送监听事件
         * 说明：
         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
         * 3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
         **/
        Observable<CharSequence> nameObservable = RxTextView.textChanges(editText_name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(editText_age).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(editText_interesting).skip(1);

        /*
         * 步骤2：通过combineLatest（）合并事件 & 联合判断
         **/
        Observable.combineLatest(nameObservable,ageObservable,jobObservable,new Function3<CharSequence, CharSequence, CharSequence,Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2, @NonNull CharSequence charSequence3) throws Exception {

                /*
                 * 步骤4：规定表单信息输入不能为空
                 **/
                // 1. 姓名信息
                boolean isUserNameValid = !TextUtils.isEmpty(editText_name.getText()) ;
                // 除了设置为空，也可设置长度限制
                // boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) && (name.getText().toString().length() > 2 && name.getText().toString().length() < 9);

                // 2. 年龄信息
                boolean isUserAgeValid = !TextUtils.isEmpty(editText_age.getText());
                // 3. 职业信息
                boolean isUserJobValid = !TextUtils.isEmpty(editText_interesting.getText()) ;

                /*
                 * 步骤5：返回信息 = 联合判断，即3个信息同时已填写，"提交按钮"才可点击
                 **/
                return isUserNameValid && isUserAgeValid && isUserJobValid;
            }

        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean s) throws Exception {
                /*
                 * 步骤6：返回结果 & 设置按钮可点击样式
                 **/
                Log.e(TAG, "提交按钮是否可点击： "+s);
                if (s == true){
                    BaseUtils.toast("可以提交");
                }else{
                    BaseUtils.toast("不可以提交");
                }

            }
        });



    }


    /**
     * 从不同的服务器获取数据展示
     * 以下为了演示，所以采用的同一接口服务，真实开发可切换成不同的服务
     */
    private void initZip() {
        // a. 创建Retrofit 对象
        ExampleAPI exampleAPI =  initRetrofit();
        //b.  采用Observable<...>  的形式 对网络请求进行封装
        final Observable<TanslationEntity> tranlationResult =
                exampleAPI.getTranlationResult().subscribeOn(Schedulers.io());
        final Observable<TanslationEntity> tranlationResultTwo =
                exampleAPI.getTranlationResultTwo().subscribeOn(Schedulers.io());
        // 即2个网络请求异步 & 同时发送
        // c：通过使用Zip（）对两个网络请求进行合并再发送
         Observable.zip(tranlationResult, tranlationResultTwo, new BiFunction<TanslationEntity, TanslationEntity, String>() {
             @Override
             public String apply(TanslationEntity tanslationEntity, TanslationEntity tanslationEntity2) throws Exception {
                 return "第一次翻译："+tanslationEntity.getContent().getOut()+" & "+"第二次翻译"+tanslationEntity2.getContent().getOut();
             }
         }).observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Consumer<String>() {
                     @Override
                     public void accept(String s) throws Exception {
                         // 结合显示2个网络请求的数据结果
                         Log.d(TAG, "最终接收到的数据是：" + s);
                     }
                 }, new Consumer<Throwable>() {
                     @Override
                     public void accept(Throwable throwable) throws Exception {
                         Log.d(TAG, "发生错误");
                     }
                 });

    }


    /**
     * 从网络和本地合并数据源
     */
    String result = "数据源来自 = " ;
    private void initMerge() {

          /*
         * 设置第1个Observable：通过网络获取数据
         * 此处仅作网络请求的模拟
         **/
        Observable<String> internet = Observable.just("网络");
         /*
         * 设置第2个Observable：通过本地文件获取数据
         * 此处仅作本地文件请求的模拟
         **/
        Observable<String> location = Observable.just("本地");

        Observable.merge(internet,location).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "数据源有： "+ value  );
                result += value + "+";
            }

            @Override
            public void onError(Throwable e) {

            }

            // 接收合并事件后，统一展示
            @Override
            public void onComplete() {
                Log.d(TAG, "获取数据完成");
                Log.d(TAG,  result  );
            }
        });

    }


    /**
     * 磁盘缓存问题
     */
    private void initConcatAndFirstElement() {
        // 该下变量用于模拟内存缓存 & 磁盘缓存中的数据
        final String memoryCache = null;
        final String diskCache = "从磁盘缓存中获取数据";

        // 检查内存缓存中是否有数据
        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                   if (memoryCache != null){
                       e.onNext(memoryCache);
                   }else {
                       e.onComplete();
                   }
            }
        });
        // 检查磁盘缓存中是否有数据
        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (diskCache != null){
                    e.onNext(diskCache);
                }else {
                    e.onComplete();
                }
            }
        });

        Observable<String> internet = Observable.just("从网路请求获取");

        //   1 将它们按顺序串联成队列
        Observable.concat(memory,disk,internet)
                // 2. 通过firstElement()，从串联队列中取出并发送第1个有效事件（Next事件），即依次判断检查memory、disk、network
                .firstElement()
        // 即本例的逻辑为：
        // a. firstElement()取出第1个事件 = memory，即先判断内存缓存中有无数据缓存；由于memoryCache = null，即内存缓存中无数据，所以发送结束事件（视为无效事件）
        // b. firstElement()继续取出第2个事件 = disk，即判断磁盘缓存中有无数据缓存：由于diskCache ≠ null，即磁盘缓存中有数据，所以发送Next事件（有效事件）
        // c. 即firstElement()已发出第1个有效事件（disk事件），所以停止判断。

                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG,"最终获取的数据来源 =  "+ s);
            }
        });

    }


    /**
     * 网络嵌套问题
     */
    private void initFlatMapRaJava() {
        // a. 创建Retrofit 对象
        ExampleAPI exampleAPI =  initRetrofit();
        //b.  采用Observable<...>  的形式 对网络请求进行封装
        final Observable<TanslationEntity> tranlationResult =
                exampleAPI.getTranlationResult();
        final Observable<TanslationEntity> tranlationResultTwo =
                exampleAPI.getTranlationResultTwo();
         // c.通过线程切换实现网络请求
        tranlationResult.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<TanslationEntity>() {
                    @Override
                    public void accept(TanslationEntity tanslationEntity) throws Exception {
                        Log.e(TAG, "第1次网络请求成功");
                        Log.e(TAG, tanslationEntity.getContent().getOut());
                        // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                })
        // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
        // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
        // 但对于初始观察者，它则是新的被观察者
                .observeOn(Schedulers.io())
               // 作变换，即作嵌套网络请求
                .flatMap(new Function<TanslationEntity, ObservableSource<TanslationEntity>>() {
                    @Override
                    public ObservableSource<TanslationEntity> apply(TanslationEntity tanslationEntity) throws Exception {
                        // 注册失败处理
                         if (tanslationEntity == null){
                             return  null;
                         }
                        // 将网络请求1转换成网络请求2，即发送网络请求2
                        return tranlationResultTwo;
                    }
                })
                // （初始观察者）切换到主线程 处理网络请求2的结果
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TanslationEntity>() {
                    @Override
                    public void accept(TanslationEntity tanslationEntity) throws Exception {
                        Log.e(TAG, "第2次网络请求成功");
                        Log.e(TAG, tanslationEntity.getContent().getOut());
                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("登录失败");
                    }
                });

    }



    /**
     * 有条件轮询
     */
    private void initLimitRxJava() {
        // a. 创建Retrofit 对象
        ExampleAPI exampleAPI =  initRetrofit();
        //b.  采用Observable<...>  的形式 对网络请求进行封装
        Observable<TanslationEntity> tranlationResult =
                exampleAPI.getTranlationResult();
        // c：发送网络请求 & 通过repeatWhen（）进行轮询
        Log.e(TAG, "i----------"+i);
        tranlationResult.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (i > 3) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });

            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(new Observer<TanslationEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(TanslationEntity result) {
                        // e.接收服务器返回的数据
                        Log.e(TAG, result.getContent().getOut() );
                        i++;
                        Log.e(TAG, "i======"+i);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        Log.d(TAG,  e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }



    /**
     * 无条件轮询
     */
    private void initRaJava() {
          /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         **/
        Observable.interval(3,1, TimeUnit.SECONDS)
                /*
                  * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                  * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                  **/
                .doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                   /*
                  * 步骤3：通过Retrofit发送网络请求
                  **/
                // a. 创建Retrofit 对象
                ExampleAPI exampleAPI =  initRetrofit();
                 //b.  采用Observable<...>  的形式 对网络请求进行封装
                Observable<TanslationEntity> tranlationResult =
                        exampleAPI.getTranlationResult();
                // c.通过线程切换实现网络请求
                tranlationResult.subscribeOn(Schedulers.io())   // （初始被观察者）切换到IO线程进行网络请求1
                        .observeOn(AndroidSchedulers.mainThread())   // （新观察者）切换到主线程 处理网络请求1的结果
                        .subscribe(new Observer<TanslationEntity>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(TanslationEntity value) {
                                Log.e("RxJava",value.getContent().getOut());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("RxJava1",e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("RxJava2",e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }



    /**
     * 初始化Retrofit
     * @return
     */
    private ExampleAPI initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 创建网络请求接口实例
        ExampleAPI exampleAPI = retrofit.create(ExampleAPI.class);
        return  exampleAPI;
    }
}
