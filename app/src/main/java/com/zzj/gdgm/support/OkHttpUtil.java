package com.zzj.gdgm.support;

import com.zzj.gdgm.cookie.CookiesManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by J。 on 2016/4/23.
 */
public class OkHttpUtil {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(new CookiesManager()).build();
    private static final String HOST = "jw.gdgm.cn";
    private static final String REFERER = "http://jw.gdgm.cn/";
    private static final String URL_CODE = "http://jw.gdgm.cn/CheckCode.aspx";
    private static final String URL_LOGIN = "http://jw.gdgm.cn/default2.aspx";

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static String getUrlLogin() {
        return URL_LOGIN;
    }

    public static String getUrlCode() {
        return URL_CODE;
    }

    public static Request getRequest(String url) {
        Request request = new Request.Builder().url(url).addHeader("Host", HOST).addHeader("Referer", REFERER).build();
        return request;
    }

    public static Request getRequest(String url, RequestBody requestBody) {
        Request request = new Request.Builder().url(url).addHeader("Host", HOST).addHeader("Referer", REFERER).post(requestBody).build();
        return request;
    }
}
