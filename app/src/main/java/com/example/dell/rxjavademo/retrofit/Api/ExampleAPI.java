package com.example.dell.rxjavademo.retrofit.Api;

import com.example.dell.rxjavademo.bean.TanslationEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/1/22.
 * 邮箱：123123@163.com
 */

public interface ExampleAPI  {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<TanslationEntity> getTranlationResult();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<TanslationEntity> getTranlationResultTwo();


}


/*  出现以下错误
    Unable to create call adapter for io.reactivex.Observable<com.example.dell.rxjavademo.bean.TanslationEntity>
        for method ExampleAPI.getTranlationResult
    原因： Retrofit 和 Rxjava 的适配器版本没导正确
           compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
           之前一直使用的 1

        */
