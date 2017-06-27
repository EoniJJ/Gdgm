package com.arron.networklibrary.entity;

import java.io.File;
import java.util.Map;

/**
 * 请求参数实体类
 * Request body entity class
 * Created by Arron on 2017/1/3 0003.
 */

public class RequestEntity {
    /**
     * 请求参数json串
    * Request body json
    */
    private String requestBodyJson;
    /**
     * 请求文件集合
     * Request file map
     */
    private Map<String, File> requestBodyFile;

    /**
     * 请求参数集合（表单形式）
     * Request form body
     */
    private Map<String, String> formRequestBody;

    /**
     * 请求头集合
     * Request headers
     */
    private Map<String, String> headers;

    /**
     * 请求tag（通常用于取消请求时的标记）
     * Request tag, usually use by cancel request
     */
    private Object tag;

    public String getRequestBodyJson() {
        return requestBodyJson;
    }

    public void setRequestBodyJson(String requestBodyJson) {
        this.requestBodyJson = requestBodyJson;
    }

    public Map<String, File> getRequestBodyFile() {
        return requestBodyFile;
    }

    public void setRequestBodyFile(Map<String, File> requestBodyFile) {
        this.requestBodyFile = requestBodyFile;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Map<String, String> getFormRequestBody() {
        return formRequestBody;
    }

    public void setFormRequestBody(Map<String, String> formRequestBody) {
        this.formRequestBody = formRequestBody;
    }
}
