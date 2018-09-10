package com.example.dell.rxjavademo.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/5/10.
 * 邮箱：123123@163.com
 */

public class RxUtils {


    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return  new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
