package com.example.arron.gdgm.network;

/**
 * Created by Arron on 2017/4/11.
 */

public interface NetworkCallback<T> {

    /**
     * 服务器成功返回数据方法
     *
     * @param url      请求url
     * @param t 服务器响应内容
     */
    void onSuccess(String url, T t);

    /**
     * 服务器返回失败方法（参数不合法..参数缺失..等等）
     *
     * @param url         请求url
     * @param failureText 失败文本
     */
    void onFailed(String url,String failureText);



    /**
     * 请求发生异常
     *
     * @param throwable {@link Throwable}
     */
    void onException(Throwable throwable);
}
