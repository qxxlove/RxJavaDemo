package com.example.dell.rxjavademo.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.retrofit.Api.Interface;
import com.example.dell.rxjavademo.retrofit.User;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 不能再子线程中更新ui
 * 不能再主线程中执行耗时操作
 */
public class ControlThreadActivity extends AppCompatActivity {

    private TextView text_name;
    private Interface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_thread);
        initView();
        initData();
        initClick();
    }

    public  void  click (View view) {
        //主线程执行
        Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) throws Exception {
                //请求网络
                User user =   api.getUserOneParams(1).execute().body();

                e.onNext(user);
            }    //.subscribeOn(Schedulers.io())不加这句话代码实在主线程中执行的，加上则切换到子线程
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User value) {
                        Log.d("ControlThreadActivity", value + "");
                        text_name.setText(value.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //代码出错不会执行onNext，只会执行onError
                        Log.e("error", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initClick() {

    }

    private void initData() {
        // 请求网络
        Retrofit retrofit =  new Retrofit.Builder().baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())    // 配置
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(Interface.class);  // 动态代理

    }

    private void initView() {
        text_name = ((TextView) findViewById(R.id.text));
    }
}
