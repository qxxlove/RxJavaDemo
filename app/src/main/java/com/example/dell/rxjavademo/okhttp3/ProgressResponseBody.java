package com.example.dell.rxjavademo.okhttp3;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by DELL on 2017/4/7.
 */
public class ProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private ProgressListener progressListener;     // 进度监听器

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        this.responseBody = responseBody;
        this.progressListener = listener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(getSource(responseBody.source()));

        }
        return bufferedSource;
    }

    private Source getSource(Source source) {
        return new ForwardingSource(source) {
            long totalSize = 0;
            long sum = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                if (totalSize == 0) {
                    totalSize = contentLength();
                }
                long len = super.read(sink, byteCount);

                if (len == -1) {    // 代表下载完成
                    len = 0;
                }
                sum += len;
                int progress = (int) ((sum * 1.0f / totalSize) * 100);
                if (len == -1) {
                    progressListener.onDone(totalSize);
                } else {
                    progressListener.onProgress(progress);
                }
                return len;
            }
        };
    }
}
