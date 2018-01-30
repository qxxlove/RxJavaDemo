package com.example.dell.rxjavademo.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.retrofit.Api.Interface;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  reJava操作符实现监听editext输入的内容，和防止按钮的重复点击事件
 */


public class SearchRxJavaActivity extends AppCompatActivity {

    private  Interface api;
    private EditText edit_input;
    private Button btn_one_one_click;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rx_java);
        initView();
        initData();
        initClick();
    }

    private void initClick() {

         btn_one_one_click.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });
    }

    private void initData() {
        Retrofit retrofit =  new Retrofit.Builder().baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())    // 配置
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(Interface.class);  // 动态代理

        //监听edittext输入的内容必须在UI线程
        RxTextView.textChanges(edit_input).debounce(200, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        // 过滤为空的数据
                        return charSequence.length()>0;
                    }
                })   // 再次建议使用SwithMap
                .flatMap(new Function<CharSequence, ObservableSource<List<String>>>() {

                    @Override
                    public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {
                        //search
                        List<String> list = new ArrayList<String>();
                        list.add("abb");
                        list.add("sss");

                        return Observable.just(list);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                // 执行操作
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });


        // 监听按钮的点击次数    操作符debounce，throttleFirst都可实现此功能
        RxView.clicks(btn_one_one_click).throttleFirst(1,TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(Object value) {
                        // 这是按钮点击
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });




    }

    private void initView() {
        edit_input = ((EditText) findViewById(R.id.input_content));
        btn_one_one_click = ((Button) findViewById(R.id.btn_one_one_click));
    }
}
