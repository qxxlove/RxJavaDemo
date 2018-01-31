package com.example.dell.rxjavademo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.rxjavademo.activity.AnimationActivity;
import com.example.dell.rxjavademo.activity.GridviewActivity;
import com.example.dell.rxjavademo.eventbus.EventBusActivity;
import com.example.dell.rxjavademo.okhttp3.OkhttpActivity;
import com.example.dell.rxjavademo.retrofit.RetrofitActivity;
import com.example.dell.rxjavademo.retrofit.ui.LoginActivity;
import com.example.dell.rxjavademo.rxjava.ControlThreadActivity;
import com.example.dell.rxjavademo.rxjava.LoginUserActivity;
import com.example.dell.rxjavademo.rxjava.RxJavaStudyTwoActivity;
import com.example.dell.rxjavademo.rxjava.SearchRxJavaActivity;
import com.example.dell.rxjavademo.view.CustomViewActivity;
import com.example.dell.rxjavademo.view.ViewsCoordinateActivity;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 响应式编程，
 * 观察者（Observer）模式（定义了一种 一(主题)对多（观察者） 的依赖模式）
 * ① 在github上查找Api看wiik 通篇阅读，达到形成自己的知识网络，便于快速检索，点位
 * ② 拿到自己想要的东西，深挖（一般浏览搜索的前两页）
 *
 * ③ rxJava的操作符（难点）
 *
 *
 */

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Button btn_thread;
    private Button btn_okhttp3;
    private Button btn_retrofit;
    private Button btn_control_edittext;
    private Button btn_login;
    private Button btn_eventBus;
    private Button btn_view;
    private Button btn_gridview;
    private Button btn_login_actiivyt;
    private Button btn_system_rxjava_study;
    private Button btn_view_coordinate;
    private Button btn_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
    }


    private void initClick() {
        btn_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ControlThreadActivity.class));
            }
        });

        btn_okhttp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,OkhttpActivity.class));

            }
        });

        btn_retrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RetrofitActivity.class));

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginUserActivity.class));

            }
        });
        btn_control_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchRxJavaActivity.class));

            }
        });
        btn_eventBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EventBusActivity.class));

            }
        });
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CustomViewActivity.class));

            }
        });
        btn_gridview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GridviewActivity.class));
            }
        });

        btn_login_actiivyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        btn_system_rxjava_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RxJavaStudyTwoActivity.class));
            }
        });
        btn_view_coordinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewsCoordinateActivity.class));
            }
        });
        btn_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AnimationActivity.class));
            }
        });
    }

    private void initView() {
        text = ((TextView) findViewById(R.id.text));
        btn_thread = ((Button) findViewById(R.id.btn_contorl_thread));
        btn_okhttp3 = ((Button) findViewById(R.id.btn_okhttp));
        btn_retrofit = ((Button) findViewById(R.id.btn_retrofit));
        btn_control_edittext = ((Button) findViewById(R.id.btn_contorl_edittext));
        btn_login = ((Button) findViewById(R.id.btn_rxjava_login));
        btn_eventBus = ((Button) findViewById(R.id.btn_eventbus));
        btn_view = ((Button) findViewById(R.id.btn_view));
        btn_gridview = ((Button) findViewById(R.id.btn_gridview));
        btn_login_actiivyt = ((Button) findViewById(R.id.btn_login));
        btn_system_rxjava_study = ((Button) findViewById(R.id.btn_system_rxjava_study));
        btn_view_coordinate = ((Button) findViewById(R.id.btn_view_coordinate));
        btn_animation = ((Button) findViewById(R.id.btn_animation));
    }

    public void click(View view) {
        Observable<String> observable = getObservable();
        Observer<String> observer = getObserver();

        // ①   observable.subscribe(observer);

        // 简单写法    （从源码得知，observable创建的构造方法的重载）
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                text.append(s);
            }
        });
        /*, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {

              }
          }, new Action() {
              @Override
              public void run() throws Exception {

              }
          }*/

    }


    /**
     * ObservableEmitter(发射器（发射事件）)
     *
     * @return
     */
    public Observable<String> getObservable() {
        // ①   return Observable.just("泡吧","日日日");

        // ③   return Observable.fromArray("泡吧", "日日日");

        // ④    只能绑定一个数据源
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "大保健";
            }
        });
          /*②  create 创建被订阅者（主题）, subscribe  用来关联订阅者 (observe) 和被订阅者 (Observable )
              return Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    e.onNext("大保健");
                    e.onNext("泡吧");
                    e.onComplete();    //与onError()  互斥
                     与下面的方法一一对应
                }
            });*/

    }


    public Observer<String> getObserver() {

        return new Observer<String>() {
            Disposable dd = null;

            @Override
            public void onSubscribe(Disposable d) {
                dd = d;
                Log.i("MainActivity", "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.i("MainActivity", "onNext");

                if (value.equals("泡妞")) {
                    dd.dispose();   // 解除订阅（关系）， 实际开发中有用
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("MainActivity", "onError");

            }

            @Override
            public void onComplete() {
                Log.i("MainActivity", "onComplete");

            }
        };

    }


}
