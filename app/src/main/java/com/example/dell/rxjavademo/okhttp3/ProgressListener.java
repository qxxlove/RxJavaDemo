package com.example.dell.rxjavademo.okhttp3;

/**
 * Created by DELL on 2017/4/7.
 */
public interface ProgressListener {

    public void onProgress(int progress);

    public void onDone(long totalSize);   // 下载完成

}
