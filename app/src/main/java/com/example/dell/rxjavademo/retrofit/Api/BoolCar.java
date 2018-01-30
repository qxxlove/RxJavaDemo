package com.example.dell.rxjavademo.retrofit.Api;

import com.example.dell.rxjavademo.retrofit.model.LoginBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2017/10/14.
 * 邮箱：123123@163.com
 */

public interface BoolCar  {

    @FormUrlEncoded
    @POST("carshare/user")
    Call<LoginBean> login  (@Field("uname") String phone , @Field("upwd") String pass);

}
