package com.example.dell.rxjavademo.okhttp3;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by DELL on 2017/4/8.
 */
public abstract class BaseCallBack<T> {

    static Type getSuperClass(Class<?> subclass) {
        Type superClass = subclass.getGenericSuperclass();
        if (superClass instanceof Class) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public Type mType;

    public BaseCallBack() {
        mType = getSuperClass(this.getClass());
    }

    public void onSuccess(T t) {
    }

    ;

    public void onFailure(Call call, IOException e) {
    }

    ;

    public void onError(int code) {
    }

    ;
}
