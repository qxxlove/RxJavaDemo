package com.example.dell.rxjavademo.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxJavaThreeActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_three);

        initFlatMap();
        initCompose();

    }

    /**
     *不理解FlatMap，结果为什么是？
     */
    private void initFlatMap() {

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

                list.add("我是事件" + integer + "即将被拆分为事件" + 0);


               /* for (int j = 0; j < 3; j++) {
                    list.add("我是事件" + integer + "即将被拆分为事件" + j);
                    // 通过flatMap中将 被观察者 生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }*/
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("TAG", s);
            }
        });
/*
        05-10 14:48:48.448 8306-8306/com.example.dell.rxjavademo E/TAG: 我是事件1即将被拆分为事件0

        05-10 14:48:48.448 8306-8306/com.example.dell.rxjavademo E/TAG: 我是事件1即将被拆分为事件0
        05-10 14:48:48.448 8306-8306/com.example.dell.rxjavademo E/TAG: 我是事件2即将被拆分为事件0
        
        05-10 14:48:48.448 8306-8306/com.example.dell.rxjavademo E/TAG: 我是事件1即将被拆分为事件0
        05-10 14:48:48.448 8306-8306/com.example.dell.rxjavademo E/TAG: 我是事件2即将被拆分为事件0
        05-10 14:48:48.448 8306-8306/com.example.dell.rxjavademo E/TAG: 我是事件3即将被拆分为事件0*/
    }

    private void initCompose() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);

            }
        }).compose(RxUtils.<Integer>rxSchedulerHelper())
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
}
