package com.arron.networklibrary.request;

import com.arron.networklibrary.callback.IRequestCallback;
import com.arron.networklibrary.entity.RequestEntity;
import com.arron.networklibrary.entity.ResponseEntity;

/**
 * 请求通用接口
 * Request Interface
 * Created by Arron on 2016/12/30 0030.
 */

public interface IRequestManager {

    /**
     * 请求get方法(异步方法)
     * http get method
     *
     * @param url              请求url
     * @param requestEntity    {@link RequestEntity}
     * @param iRequestCallback {@link IRequestCallback}
     * @see RequestEntity
     * @see IRequestCallback
     */
    void get(String url, RequestEntity requestEntity, IRequestCallback iRequestCallback);

    /**
     * 请求post方法(异步方法)
     * http post method
     *
     * @param url              请求url
     * @param requestEntity    {@link RequestEntity}
     * @param iRequestCallback {@link IRequestCallback}
     * @see RequestEntity
     * @see IRequestCallback
     */
    void post(String url, RequestEntity requestEntity, IRequestCallback iRequestCallback);

    /**
     * 请求put方法
     * http put method
     *
     * @param url              请求url
     * @param requestEntity    {@link RequestEntity}
     * @param iRequestCallback {@link IRequestCallback}
     * @see RequestEntity
     * @see IRequestCallback
     */
    void put(String url, RequestEntity requestEntity, IRequestCallback iRequestCallback);


    /**
     * 请求delete方法
     * http delete method
     *
     * @param url              请求url
     * @param iRequestCallback {@link IRequestCallback}
     * @see IRequestCallback
     */
    void delete(String url, IRequestCallback iRequestCallback);

    /**
     * 取消请求方法
     * cancel request
     *
     * @param tag 请求标识符 request tag
     */
    void cancel(Object tag);
}
