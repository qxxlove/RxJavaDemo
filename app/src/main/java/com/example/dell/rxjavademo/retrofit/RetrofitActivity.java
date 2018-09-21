package com.example.dell.rxjavademo.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.retrofit.Api.Interface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *   Retrofit默认使用okhttp3
 *   相当于ListView,Gridview的适配器一样的功能
 *   ① 学习一个新东西，首先在Github找到官网（官方文档）介绍
 *   ②  retrofit与RxJava的结合
 *         需求：调用登录接口，根据登录接口返回的数据在去调用另一个接口
 *
 */



public class RetrofitActivity extends AppCompatActivity {

    private Interface  api;
    private Button btn_get;
    private Button btn_login;
    private Button btn_rxjava_login;
    private Button button_retrofit_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        initView();
        initData();
        initClick();
    }

    private void initClick() {
        
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**get 请求方式*/
                requestApi(v);
                reQuestUser();
                // 带参的
                reQuestUserParams();
            }
        });

        /**post 请求方式*/
        button_retrofit_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reQuestPostUserParams();
            }
        });

        /**实例： 模拟用户登录并取出用户信息*/
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.saveJson(new BaseResult("","","")).enqueue(new Callback<BaseResult>() {
                    @Override
                    public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                        if (response.isSuccessful()) {
                            String user_id = response.body().getId();
                            api.getUserOneParams(Integer.getInteger(user_id)).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    // 拿到用户信息
                                    User user = response.body();

                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResult> call, Throwable t) {

                    }
                });
            }
        });


        /**上述实例进一步改装： 结合RxJava*/
        btn_rxjava_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             api.saveJsonRxJava(new BaseResult("","","")).flatMap(new Function<BaseResult, ObservableSource<User>>() {

                 @Override
                 public ObservableSource<User> apply(BaseResult baseResult) throws Exception {
                     return api.getUserOneParamsRxJava(Integer.getInteger(baseResult.getId()));
                 }
             }).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(new Subject<User>() {
                         @Override
                         public boolean hasObservers() {
                             return false;
                         }

                         @Override
                         public boolean hasThrowable() {
                             return false;
                         }

                         @Override
                         public boolean hasComplete() {
                             return false;
                         }

                         @Override
                         public Throwable getThrowable() {
                             return null;
                         }

                         @Override
                         protected void subscribeActual(Observer<? super User> observer) {

                         }

                         @Override
                         public void onSubscribe(Disposable d) {

                         }

                         @Override
                         public void onNext(User value) {

                         }

                         @Override
                         public void onError(Throwable e) {

                         }

                         @Override
                         public void onComplete() {

                         }
                     });

            }
        });
    }


    /**
     *   ① Retrofit 构建的时候，为其设置了 Gson 的工厂对象。

         ② 上面可知 call.enqueue(callback)，call 就是 OkHttpCall 对象。

         ③ enqueue 创建的时候会先调 createRawCall

         ④ createRawCall 会先调用 serviceMethod 的 toRequest 方法

         ⑤ 在 toRequest方法中，创建 RequestBuild 对象，并且把设置的业务请求的 api 里的参数对象请求体 Body
           使用 Gson 工厂创建的 FastJsonRequestConverter 来 convert 出一个 RequestBody 设置给 RequestBuild 对象，
           并最终通过构建者模式创建 Request 对象。

         ⑥ 再通过 callFactory 工厂创建一个用于请求的 call，最终交由okhttp 的 enqueue 方法来发起真正的网络请求。
     */
    private void initData() {

        /**实际开发中，Retrofit实例化应该放在 Application中 ，baseUrl 也应该是动态设置的*/
        Retrofit retrofit =  new Retrofit.Builder().baseUrl("") // ip地址
                .addConverterFactory(GsonConverterFactory.create())    // 配置
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        /**create方法创建返回的正是Interface接口的代理对象，
         * 每当代理对象调用目标接口里的方法时，
         * 动态代理对象就会回调InvocationHandler接口的invoke实现方法。
         */
        api = retrofit.create(Interface.class);  // 动态代理

    }

    /**
     * 默认写法
     * enqueue(异步)   execute(同步)
     * @param view
     */
    public  void requestApi (View view) {
        Call<ResponseBody>  call =  api.getUserInfo();
        call.enqueue(new Callback<ResponseBody>() {
            @Override     // onResponse在主线程运行的
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result =  response.body().string();
                    // 实际开发中并不会直接使用ResponseBody ，应该使用我们自己的实体类，所以就有下面的基本写法
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    

    /**
     * 基本写法
     */
    public  void  reQuestUser () {
        Call<User>  call = api.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }


       /**
        * get 方式带参数的
        */
    public  void  reQuestUserParams () {

        // ④ 将参数以map集合的从参数传递
        Map<String,String> mao = new HashMap<>();
        mao.put("","");
        mao.put("", "");
        Call<User> userMapParams = api.getUserMapParams(mao);
        userMapParams.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        // ②
        Call<User>  call = api.getUserParams(3);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });



    }



    /**
     *   post带参数的
     */
    public  void  reQuestPostUserParams () {

        // ① 以表单的形式
        Call<BaseResult>  call1 = api.saveForm(1,"pass");

        // ②以json的形式
        BaseResult user = new BaseResult("","name","pass");
        Call<BaseResult>  call = api.saveJson(user);
        call.enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
               // 最后的结果
                response.body();
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {

            }
        });



    }



    private void initView() {
        btn_get = ((Button) findViewById(R.id.btn_retrofit_get));
        button_retrofit_post = ((Button) findViewById(R.id.btn_retrofit_post));
        btn_login = ((Button) findViewById(R.id.btn_login));
        btn_rxjava_login = ((Button) findViewById(R.id.btn_login_rxJava));
    }





}
