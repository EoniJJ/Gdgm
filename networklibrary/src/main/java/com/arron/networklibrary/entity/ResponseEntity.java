package com.arron.networklibrary.entity;

import java.io.InputStream;

/**
 * 响应实体
 * Http response entity
 * Created by Arron on 2017/1/3 0003.
 */

public class ResponseEntity {
    /**
     * http状态码
     * http status code
     */
    private int code;

    /**
     * http请求url
     * http request url
     */
    private String url;

    /**
     * http响应体
     * http response
     */
    private InputStream response;

    /**
     * http请求成功状态码
     */
    public static final int HTTP_STATUS_OK = 200;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getResponse() {
        return response;
    }

    public void setResponse(InputStream response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "code=" + code +
                ", url='" + url + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
