package com.example.dell.rxjavademo.okhttp3;

/**
 * Created by DELL on 2017/4/8.
 */
public class RequestParam {

    private String key;
    private Object object;

    public RequestParam(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
