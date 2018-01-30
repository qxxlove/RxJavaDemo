package com.example.dell.rxjavademo.retrofit.Api;

import com.example.dell.rxjavademo.bean.Qbbean;
import com.example.dell.rxjavademo.retrofit.BaseResult;
import com.example.dell.rxjavademo.retrofit.User;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * description:
 * autour: TMM
 * date: 2017/10/19 9:54
 * update: 2017/10/19
 * version:
 *
 * 总结

    @Path：所有在网址中的参数（URL的问号前面），如：
    http://102.10.10.132/api/Accounts/{accountId}
    @Query：URL问号后面的参数，如：
    http://102.10.10.132/api/Comments?access_token={access_token}
    @QueryMap：相当于多个@Query
    @Field：用于POST请求，提交单个数据
    @Body：相当于多个@Field，以对象的形式提交

*/

public interface Interface {

    @GET("user/info")
    Call<ResponseBody> getUserInfo();
    @GET("user/info")
     Call<User> getUser();


    @GET("user/info?id=3")    // 此方式实际开发中不推荐，参数写死，不利于开发，所以就有下面的方式
    Call<User> getUserParam();
    @GET("user/info")
     Call<User> getUserParams(@Query("id") int user_id);

    // 另一种写法，直接将参数作为url的一部分，使用占位符进行占位，如{id}，{}占位符，id 就是参数名，再用path声明，参数类型
    @GET("user/{id}")
    Call<User> getUserOneParams(@Path("id") int user_id);
    @GET("qbbean/{id}")
    Call<Qbbean> getUserOneParamsQ(@Path("id") int user_id);

    // 传递一个map对象
    @GET("user/info")
    Call<User> getUserMapParams(@QueryMap Map<String,String> params);




    // 此方式传递参数，服务端将会收到 BaseResult里面的 变量名（如：id,name,pass）就是key，而 value就是代码中传递的值
    // 如果用post 传递多个参数呢，并不是BaseResult一个对象，那该如何传递
    @POST("user/new")
    Call<BaseResult>  saveJson (@Body BaseResult user);
    @POST("user/new")
    Call<User> savaJsonUser (@Body BaseResult user);


    // 头信息
    @Headers({"******"})  // 更改 ,可添加多个，一般用于验证信息，拦截，具体还需查阅资料，在学习


    @FormUrlEncoded
    @POST("user/new")
    Call<BaseResult>  saveForm (@Field("id") int user_id ,@Field("username") String name );




    @POST("user/new")
    Observable<BaseResult> saveJsonRxJava (@Body BaseResult user);
    @GET("user/{id}")
    Observable<User> getUserOneParamsRxJava(@Path("id") int user_id);


}
