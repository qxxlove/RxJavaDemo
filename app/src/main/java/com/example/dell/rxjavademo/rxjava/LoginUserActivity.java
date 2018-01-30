package com.example.dell.rxjavademo.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.bean.Qbbean;
import com.example.dell.rxjavademo.retrofit.Api.Interface;
import com.example.dell.rxjavademo.retrofit.BaseResult;
import com.example.dell.rxjavademo.retrofit.User;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUserActivity extends AppCompatActivity {

    private Button btn_click;
    private Interface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        initView();
        initData();
        initClick();
    }

    private void initClick() {
          btn_click.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //①
                  jianDanXieFa();
                  //由①演变出来②
                  Observable.just(1).map(new Function<Integer, String>() {
                      @Override
                      public String apply(Integer integer) throws Exception {
                          //doSomeString
                          return null;
                      }
                  });

                  // ③
                  // 登录
                  // 获取用户订单
                  Observable.just(getBaseResult()).flatMap(new Function<BaseResult, ObservableSource<User>>() {
                      @Override
                      public ObservableSource<User> apply(BaseResult baseResult) throws Exception {
                          User user = api.savaJsonUser(baseResult).execute().body();    // 请求网络之后返回的数据，保存到User类
                          return Observable.just(user);
                      }
                  }).flatMap(new Function<User, ObservableSource<Qbbean>>() {

                      @Override
                      public ObservableSource<Qbbean> apply(User user) throws Exception {
                          Qbbean qbbean = api.getUserOneParamsQ(user.getId()).execute().body();  // 再次转换，请求网络需要的参数，user的getId,保存到Qbbean类中

                          return Observable.just(qbbean);
                      }//还可以再次转换flatMap，最后subscribe
                  }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                          .subscribe(new Consumer<Qbbean>() {
                              @Override
                              public void accept(Qbbean qbbean) throws Exception {
                                  // 处理最后的结果
                              }
                          });
              }
          });
    }

    // 用户登录输入的信息
   public BaseResult getBaseResult (){
       BaseResult baseResult = new BaseResult("1","qxx","123123");
       return  baseResult;
   }

    private void initData() {
        Retrofit retrofit =  new Retrofit.Builder().baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())    // 配置
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(Interface.class);  // 动态代理

    }

    private void initView() {
        btn_click = ((Button) findViewById(R.id.btn_click));
    }


    public void  jianDanXieFa(){
         // map : 操作符
        // 需求： 把1转换成String
        Observable<Integer> observable = Observable.just(1);

        //Integer(输入值), String （目标值）
        Observable<String> observable1 = observable.map(new Function<Integer, String>() {

            @Override
            public String apply(Integer integer) throws Exception {
                return 1 + "";
            }
        });




        observable.subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer integer) throws Exception {

            }
        });

        observable1.subscribe(new Consumer<String>(){
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

}
