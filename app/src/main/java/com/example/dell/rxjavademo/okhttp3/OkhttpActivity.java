package com.example.dell.rxjavademo.okhttp3;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.bean.Qbbean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp3之间以流（IO）的形式传输
 * <p/>
 * http 超文本传输协议（无状态的）  1.0 /1.1 / 2.0 版本
 * 什么是url？
 * http://www.mywebsite.com/sj/test;id= 8079? name = qiaoxue&pass= 1022222
 * <p/>
 * schame: 指底层使用的协议，如：http,https,ftp
 * host:  ip地址或者域名
 * port:  http磨人的端口是80，一般是省略的，如果使用其他的端口
 * 如： http:// www.mywebsite.com:8080/
 * <p/>
 * path: 访问资源的路径
 * <p/>
 * <p/>
 * 如何查看一个网络请求的request 和response 相关信息？
 * 可以通过 浏览器输入一个请求，通过开发者工具，刷新，选择NetWork, 就会出现相关的信息
 * request：
 * 三部分： 请求行， http header   body
 * 请求行： 包括请求的种类，请求资源的路径，http协议版本
 * http header : http头部信息
 * body: 发送给服务器的query信息，当使用get的时候，body为空，使用post会有
 * <p/>
 * <p/>
 * response：
 * request line : 协议版本 状态吗，message
 * request header ： request头信息
 * body： 返回的请求资源主体
 * <p/>
 * 浏览器一般都是get请求的，post是不可以的
 * 状态吗：一般由三位数组成，第一位定义了相应的类别
 * 200 ：  请求成功（和服务器成功），第一次握手
 * 404 ：  客户端请求错误，服务端无法识别
 * 工具： postman
 * <p/>
 * okhttp文件下载
 * <p/>
 * 如何封装一个Okhttp工具类？
 * 采用链式编程思想
 * 此demo 还对okhttp进行简单封装
 * <p/>
 * 当一个类，无法实例化时，点击查看源码，然后右键单击有一个 go to 选项
 * <p/>
 * 网络缓存分为：
 * 服务器缓存 ：代理服务器缓存和反向代理服务器缓存
 * 常见的服务器缓存：CDN
 * 客户端缓存 :主要是指浏览器（IE,QQ浏览器），当然好包括OkhttpClient，当客户端第一次请求网络时，服务器返回回复信息，
 * 如果数据正常的话，客户端会缓存到本地的缓存目录，当客户端再次访问同一个地址时，客户端换检测本地有没有缓存
 * ，如果有缓存的话，数据是没有过期的，直接运用缓存的数据，否则再次请求数据
 * <p/>
 * 缓存中重要的参数：
 * ① Cache-control :  是由服务器返回的Response中携带的信息。目的：告诉客户端是从本地读取缓存还是直接从服务摘取信息，他有不同的值，作用也不一样
 * Okhttp常用的：  max-age:   设置最大缓存时间
 * no-cache:
 * max-stale: 指客户端可以接受超出 超时期间 的 响应消息
 * <p/>
 * ② expires  基本上用 Cache-control:max-age =    代替
 * ③ Last_Modified/if-Modified-Since     这个需配合Cache-Control 使用
 * Last-Modified :资源最后的修改时间
 * if-Modified-Since：请求头，表示请求时间
 * 流程： 当本地缓存过期时，服务器会与Last_Modified/if-Modified-Since进行比较，如果资源的最后的修改时间较新，资源被改动过，则响应最新的资源，HTTP 200
 * 若最后的修改时间较旧，则响应  HTTP  304，可继续使用所保存的缓存
 * ④ Etag/if-None-Match                  这个需配合Cache-Control 使用
 * Etag: 表示对应请求资源在服务器中的唯一标识（具体由服务器决定）
 * 流程： 服务器通过Etag来定位资源文件，根据他是否更新的情况返回200或者304
 * <p/>
 * Etag机制比Last-Modified 精确度更高，如果同时设置了两者，Etag优先级更高
 * ⑤  Pragma : 头域用来包含实现特定的指令
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * http://m2.qiushibaike.com/article/list/latest?page=1
 * <p/>
 * 注意： 当前代码缺少权限 ：
 * 网络权限,读写sd卡权限
 * 此代码中 okhttp使用拦截器还需进一步学习，可能有bug，因为未进行测试
 */

public class OkhttpActivity extends AppCompatActivity {

    private Button btn_get_okhttp;
    private Button btn_get;
    private Button btn_post;

    private OkHttpClient httpClient;
    private Button btn_download;
    private ProgressBar pro_bar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                int pro = msg.arg1;
                pro_bar.setProgress(pro);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        initView();
        initData();
        initClick();
    }


    private void initClick() {
        // okhttp入门
        btn_get_okhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ① get请求
                OkHttpClient client = new OkHttpClient();
                //enqueue(队列) 异步的
                String url = "";
                Request request = new Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        if (response.body() != null) {
                            response.body().close();
                        }
                    }
                });
            }
        });

        //okhttp 的get 请求方式
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpRequestGet();
            }
        });

        //okhttp 的post 请求方式
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "";
                String pass = "";

                loginWithFrom(name, pass);   // 以表单的形式提交参数
                loginWithJson(name, pass);   // 以json的格式提交参数
            }
        });


        // 用okhttp实现文件下载，并进度条展示
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadApk();
            }
        });


    }


    private void downLoadApkInterceptor() {
        // ① 对ResponseBody 进行处理  （ProgressResponseBody 继承 ResponseBody）
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                return response.newBuilder().body(new ProgressResponseBody(response.body(), new prg())).build();
            }
        }).build();
        //  ②  进行网络请求
        Request request = new Request.Builder()
                .url("")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                writeFile(response);     // 此因为ProgressResponseBody中已经实现进度计算，所以在writeFile()方法中就没有那么麻烦，但是还是需要读写流进行读写（这步也可在写一个拦截器进行实现）
                // 此时需要把writeFile方法里对进度条的处理删掉，因为ProgressResponseBody 已经处理过
            }

        });
    }

    private void downloadApk() {
        // 以下是两种方式  ① 就是最普通的（直接new OKhttpClient） 实际开发中不推荐，代码耦合度高,代码多
        //   ② 使用网络拦截器实现OKhttpClient的初始化

        // ②使用网络拦截器实现
        downLoadApkInterceptor();

        // ①
        Request request = new Request.Builder()
                .url("")
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                writeFile(response);    // 用读写流的方式实现
                // 应用拦截器和网络拦截器的区别    （可在github 上进行学习）

            }

        });
    }

    /*
    读写流
     */
    private void writeFile(Response response) {
        InputStream is = null;
        FileOutputStream fos = null;

        is = response.body().byteStream();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, "你好");   // 你好 是文件名
        try {
            fos = new FileOutputStream(file);

            byte[] bytes = new byte[1024];
            int len = 0;
            long totalSize = response.body().contentLength();
            long sum = 0;
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes);

                sum += len;
                int progress = (int) ((sum * 1.0f / totalSize) * 100);
                Message message = handler.obtainMessage(1);
                message.arg1 = progress;
                handler.sendMessage(message);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void loginWithJson(String name, String pass) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", name);
            jsonObject.put("pass", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String jsonParams = jsonObject.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);

        Request request = new Request.Builder()
                .url("")
                .post(body)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();

                    // 子线程不能操作UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 可以toast/ 操作加载提示框
                        }
                    });
                }
            }
        });

    }

    private void loginWithFrom(String name, String pass) {

        RequestBody body = new FormBody.Builder().add("userName", name).add("pass", pass).build();
        Request request = new Request.Builder()
                .url("")
                .post(body)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    // 原生的解析
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String name = jsonObject.getString("");
                        // .......
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 子线程不能操作UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 可以toast/ 操作加载提示框
                        }
                    });
                }
            }
        });

    }

    private void httpRequestGet() {
        OkHttpClient okHttpClient = new OkHttpClient();    //  相当于我们的一个浏览器
        final Request request = new Request.Builder()      // 发送（构建）一个请求
                .get()
                .url("http://m2.qiushibaike.com/article/list/latest?page=1").build();
        okHttpClient.newCall(request).enqueue(new Callback() {   // 发送请求，并拿到请求结果
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    getJson(json);

                }
            }
        });

    }

    private void getJson(final String json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Qbbean qbbean = gson.fromJson(json, Qbbean.class);
                //数据展示
            }
        });

    }

    private void initData() {
        httpClient = new OkHttpClient();
    }


    private void initView() {
        btn_get_okhttp = ((Button) findViewById(R.id.btn_get_okhttp));
        btn_get = ((Button) findViewById(R.id.btn_get));
        btn_post = ((Button) findViewById(R.id.btn_post));
        btn_download = ((Button) findViewById(R.id.btn_download));
        pro_bar = ((ProgressBar) findViewById(R.id.pro_ok_activity));
    }


    class prg implements ProgressListener {

        @Override
        public void onProgress(final int progress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pro_bar.setProgress(progress);
                }
            });
        }

        @Override
        public void onDone(long totalSize) {
            // toast(totalSize)

        }
    }



    //自己封装的okhttp
    private void initOkhttp() {
        SimpleHttpClient.newBuilder().url("").get().builder().enqueue(new BaseCallBack<UserBean>() {

            @Override
            public void onSuccess(UserBean userBean) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onError(int code) {

            }
        });


        // post的形式
        SimpleHttpClient.newBuilder().url("").post().addParam("userName", "").addParam("pass", "").builder().enqueue(new BaseCallBack() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onError(int code) {

            }
        });

    }


}
