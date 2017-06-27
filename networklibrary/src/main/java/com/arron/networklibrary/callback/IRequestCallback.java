package com.arron.networklibrary.callback;

import com.arron.networklibrary.entity.ResponseEntity;

/**
 * 请求回调接口
 * Http request call back interface
 * Created by Arron on 2017/1/3 0003.
 */

public interface IRequestCallback {
    /**
     * 请求成功回调方法
     * Http request success call back  method
     *
     * @param responseEntity {@link ResponseEntity}
     * @see ResponseEntity
     */
    void onSuccess(ResponseEntity responseEntity);

    /**
     * 请求异常回调方法
     * Http request failure call back method
     *
     * @param throwable {@link Throwable}
     */
    void onFailure(Throwable throwable);
}
