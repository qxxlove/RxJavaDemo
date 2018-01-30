package com.example.dell.rxjavademo.okhttp3;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by DELL on 2017/4/8.
 */
public class OkhttpManager {

    // 单例模式  ① ② ③
    private static OkhttpManager manager;

    private OkHttpClient httpClient;

    private Handler mHandler;
    private Gson gson;


    // ②
    public OkhttpManager() {
        initOkhttp();
        mHandler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }

    //③
    public static synchronized OkhttpManager getManager() {
        if (manager == null) {
            manager = new OkhttpManager();
        }
        return manager;
    }

    // 初始化okhttp
    private void initOkhttp() {
        httpClient = new OkHttpClient().newBuilder().readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000, TimeUnit.SECONDS).build();
    }


    public void request(SimpleHttpClient client, final BaseCallBack callBack) {

        if (callBack == null) {
            throw new NullPointerException("callback is null");
        }

        httpClient.newCall(client.buildRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailureMessage(callBack, call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    // 如何解析
                    if (callBack.mType == null || callBack.mType == String.class) {
                        sendSuccessMessage(callBack, response);
                    } else {
                        try {
                            sendSuccessMessage(callBack, gson.fromJson(result, callBack.mType));
                        } catch (Exception e) {

                        }
                    }

                    if (response.body() != null) {
                        response.body().close();
                    }


                } else {
                    sendFErrorMessage(callBack, response.code());
                }
            }
        });

    }

    private void sendFailureMessage(final BaseCallBack callBack, final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(call, e);
            }
        });
    }

    private void sendFErrorMessage(final BaseCallBack callBack, final int code) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(code);
            }
        });


    }

    private void sendSuccessMessage(final BaseCallBack callBack, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(obj);
            }
        });


    }

}
