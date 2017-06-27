package com.arron.networklibrary.request;

import android.text.TextUtils;

import com.arron.networklibrary.callback.IRequestCallback;
import com.arron.networklibrary.entity.RequestEntity;
import com.arron.networklibrary.entity.ResponseEntity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp请求实现类
 * Created by Arron on 2017/1/3 0003.
 */

public class OkHttpRequestManager implements IRequestManager {

    /**
     * 连接超时时间   单位：秒
     */
    private static final int CONNECT_TIMEOUT = 10;
    /**
     * 读取超时时间   单位：秒
     */
    private static final int READ_TIMEOUT = 10;
    /**
     * 写入超时时间   单位：秒
     */
    private static final int WRITE_TIMEOUT = 10;


    /**
     * Json MediaType
     */
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * File MediaType
     */
    private static final MediaType FILE = MediaType.parse("application/octet-stream");

    /**
     * 请求client
     */
    private OkHttpClient okHttpClient;


    private OkHttpRequestManager() {
        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).
                writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).
                cookieJar(new OkHttpCookieJar()).
                build();
    }

    public static OkHttpRequestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OkHttpRequestManager INSTANCE = new OkHttpRequestManager();
    }


    /**
     * 异步调用OkHttp的get方法
     *
     * @param url              请求url
     * @param requestEntity    {@link RequestEntity}
     * @param iRequestCallback {@link IRequestCallback}
     */
    @Override
    public void get(final String url, RequestEntity requestEntity, final IRequestCallback iRequestCallback) {
        final ResponseEntity responseEntity = new ResponseEntity();
        Call call = okHttpClient.newCall(getRequestForGetMethod(url, requestEntity));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                iRequestCallback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (call.isCanceled()) {
                    iRequestCallback.onFailure(new IOException("call is canceled"));
                    return;
                }
                responseEntity.setUrl(url);
                responseEntity.setCode(response.code());
                responseEntity.setResponse(response.body().byteStream());
                iRequestCallback.onSuccess(responseEntity);
            }
        });

    }

    /**
     * 异步调用OkHttp的post方法
     *
     * @param url              请求url
     * @param requestEntity    {@link RequestEntity}
     * @param iRequestCallback {@link IRequestCallback}
     */
    @Override
    public void post(final String url, RequestEntity requestEntity, final IRequestCallback iRequestCallback) {
        final ResponseEntity responseEntity = new ResponseEntity();
        Call call = okHttpClient.newCall(getRequest(url, requestEntity));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                iRequestCallback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (call.isCanceled()) {
                    iRequestCallback.onFailure(new IOException("call is canceled"));
                    return;
                }
                responseEntity.setUrl(url);
                responseEntity.setResponse(response.body().byteStream());
                responseEntity.setCode(response.code());
                iRequestCallback.onSuccess(responseEntity);
            }
        });

    }

    @Override
    public void put(String url, RequestEntity requestEntity, IRequestCallback iRequestCallback) {
        throw new UnsupportedOperationException(getClass().getName() + "was not overridden put method");
    }

    @Override
    public void delete(String url, IRequestCallback iRequestCallback) {
        throw new UnsupportedOperationException(getClass().getName() + "was not overridden delete method");
    }

    /**
     * 根据标识符tag取消请求
     *
     * @param tag 请求标识符 request tag
     */
    @Override
    public void cancel(Object tag) {
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }

    /**
     * 根据url和请求实体构建一个post请求对象
     *
     * @param url           请求url
     * @param requestEntity {@link RequestEntity}
     * @return Request {@link Request}
     */
    private Request getRequest(String url, RequestEntity requestEntity) {
        if (requestEntity == null) {
            throw new IllegalStateException("requestEntity can't not null");
        }
        Request.Builder requestBuilder = new Request.Builder();
        RequestBody requestBody = null;
        if (!TextUtils.isEmpty(requestEntity.getRequestBodyJson())) {
            requestBody = RequestBody.create(JSON, requestEntity.getRequestBodyJson());
        } else if (requestEntity.getFormRequestBody() != null || requestEntity.getRequestBodyFile() != null) {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            if (requestEntity.getFormRequestBody() != null) {
                for (String key : requestEntity.getFormRequestBody().keySet()) {
                    builder.addFormDataPart(key, requestEntity.getFormRequestBody().get(key));
                }
            }
            if (requestEntity.getRequestBodyFile() != null) {
                for (String key : requestEntity.getRequestBodyFile().keySet()) {
                    builder.addFormDataPart(key, requestEntity.getRequestBodyFile().get(key).getName(), RequestBody.create(FILE, requestEntity.getRequestBodyFile().get(key)));
                }
            }
            requestBody = builder.build();
        }
        if (requestBody == null) {
            throw new IllegalStateException("RequestEntity no has jsonRequestBody or fileRequest");
        }
        if (requestEntity.getHeaders() != null) {
            Headers headers = Headers.of(requestEntity.getHeaders());
            requestBuilder.headers(headers);
        }
        if (requestEntity.getTag() != null) {
            requestBuilder.tag(requestEntity.getTag());
        }
        Request request = requestBuilder.url(url).post(requestBody).build();
        return request;
    }

    /**
     * 根据url和请求实体构建一个get请求对象
     *
     * @param url           请求url
     * @param requestEntity {@link RequestEntity}
     * @return Request {@link Request}
     */
    private Request getRequestForGetMethod(String url, RequestEntity requestEntity) {
        Request.Builder builder = new Request.Builder();
        if (requestEntity != null) {
            if (requestEntity.getHeaders() != null) {
                Headers headers = Headers.of(requestEntity.getHeaders());
                builder.headers(headers);
            }
            if (requestEntity.getTag() != null) {
                builder.tag(requestEntity.getTag());
            }
        }
        return builder.url(url).build();
    }
}
