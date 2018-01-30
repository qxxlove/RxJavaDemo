package com.example.dell.rxjavademo.retrofit.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.retrofit.Api.BoolCar;
import com.example.dell.rxjavademo.retrofit.model.LoginBean;
import com.example.dell.rxjavademo.utils.BaseUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_phone;
    private EditText edit_pass;
    private Button btn_login;

    private  BoolCar  api ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        initClick();
    }

    private void initClick() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.login("18334787764","123456").enqueue(new Callback<LoginBean>() {
                    @Override
                    public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                         if (response.isSuccessful()){
                             BaseUtils.toast("登陆成功");
                         }
                    }

                    @Override
                    public void onFailure(Call<LoginBean> call, Throwable t) {
                        BaseUtils.toast(t.getMessage());
                    }
                });
            }
        });
    }

    private void initData() {
        Retrofit retrofit =  new Retrofit.Builder().baseUrl("http://139.196.86.35:8080/") // ip地址
                .addConverterFactory(GsonConverterFactory.create())    // 配置
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(BoolCar.class);  // 动态代理
    }

    private void initView() {
        edit_phone = ((EditText) findViewById(R.id.edit_phone));
        edit_pass = ((EditText) findViewById(R.id.edit_pass));
        btn_login = ((Button) findViewById(R.id.btn_login));
    }
}
