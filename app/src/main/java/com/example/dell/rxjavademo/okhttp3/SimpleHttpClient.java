package com.example.dell.rxjavademo.okhttp3;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by DELL on 2017/4/8.
 */
public class SimpleHttpClient {

    private Builder builder;

    private SimpleHttpClient(Builder mBuilder) {
        this.builder = mBuilder;
    }

    public Request buildRequest() {
        Request.Builder rbuilder = new Request.Builder();
        if (builder.method == "GET") {
            rbuilder.url(builderGetRequestParam());
            rbuilder.get();
        } else if (builder.method == "POST") {
            try {
                rbuilder.post(buildRequestBody());
                rbuilder.url(builder.url);
            } catch (Exception e) {

            }
        }
        return rbuilder.build();
    }


    public void enqueue(BaseCallBack callBack) {
        OkhttpManager.getManager().request(this, callBack);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String url;
        private String method;
        private List<RequestParam> list;   // 参数集合
        private boolean isJsonPost;

        public Builder() {
            method = "GET";    // 默认是get 方法
        }

        public Builder url(String murl) {
            this.url = murl;
            return this;
        }

        public SimpleHttpClient builder() {
            return new SimpleHttpClient(this);
        }


        public Builder get() {
            method = "GET";
            return this;
        }

        /**
         * form表单形式
         *
         * @return
         */
        public Builder post() {
            method = "POST";
            return this;
        }

        /**
         * json格式
         *
         * @return
         */
        public Builder json() {
            isJsonPost = true;
            return post();
        }

        public Builder addParam(String key, Object value) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(new RequestParam(key, value));
            return this;
        }

    }

    private String builderGetRequestParam() {
        if (builder.list.size() <= 0) {
            return this.builder.url;
        }
        Uri.Builder builder1 = Uri.parse(builder.url).buildUpon();
        for (RequestParam p : builder.list) {
            builder1.appendQueryParameter(p.getKey(), p.getObject() == null ? "" : p.getObject().toString());
        }
        String url = builder.builder().toString();
        return url;

    }


    private RequestBody buildRequestBody() throws JSONException {
        if (builder.isJsonPost) {
            JSONObject jsonObject = new JSONObject();
            for (RequestParam p : builder.list) {
                jsonObject.put(p.getKey(), p.getObject());
            }
            String json = jsonObject.toString();
            Log.d("SimpleRequest json = ", json);
            return RequestBody.create(MediaType.parse("application/json; charset-utf-8"), json);
        }
        FormBody.Builder builder1 = new FormBody.Builder();
        for (RequestParam param : builder.list) {
            builder1.add(param.getKey(), param.getObject() == null ? "" : param.getObject().toString());
        }
        return builder1.build();
    }

}
