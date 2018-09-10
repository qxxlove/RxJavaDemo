package com.example.dell.rxjavademo.handler;

import android.graphics.Bitmap;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/7/2.
 * 邮箱：123123@163.com
 */

public class ImageModel {

    public Bitmap bitmap;
    public String url ;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
