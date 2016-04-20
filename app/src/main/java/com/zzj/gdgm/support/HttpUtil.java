package com.zzj.gdgm.support;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

/**
 * Created by J。 on 2016/4/18.
 */
public class HttpUtil {
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private static final String HOST = "jw.gdgm.cn";
    private static final String REFERER = "http://jw.gdgm.cn/";
    private static final String URL_CODE = "http://jw.gdgm.cn/CheckCode.aspx";
    private static final String URL_LOGIN = "http://jw.gdgm.cn/default2.aspx";

    public static String getREFERER() {
        return REFERER;
    }

    public static String getHOST() {
        return HOST;
    }

    /**
     * 请求参数
     */
    private static String __VIEWSTATE = "dDw3OTkxMjIwNTU7Oz5dolJKJHgqmp4fsn9ciB2avGIU+w==";
    private static String __VIEWSTATEGENERATOR = "92719903";
    //用户名
    private static String TextBox1 = "";
    //密码
    private static String TextBox2 = "";
    //验证码
    private static String TextBox3 = "";
    //身份单选框
    private static String RadioButtonList1 = "";
    //登录按钮
    private static String Button1 = "";

    private static PersistentCookieStore cookieStore;

    static {
        asyncHttpClient.addHeader("Host", HOST);
        asyncHttpClient.addHeader("Referer", REFERER);
    }

    public static void initCookie(Context context) {
        cookieStore = new PersistentCookieStore(context);
        asyncHttpClient.setCookieStore(cookieStore);
    }

    public static void get(String urlString, AsyncHttpResponseHandler res) {
        asyncHttpClient.get(urlString, res);
    }

    public static void get(String urlString, RequestParams params,
                           AsyncHttpResponseHandler res) {
        asyncHttpClient.get(urlString, params, res);
    }

    public static void get(String uString, BinaryHttpResponseHandler bHandler) {
        asyncHttpClient.get(uString, bHandler);
    }

    public static void post(String urlString, AsyncHttpResponseHandler res) {
        asyncHttpClient.post(urlString, res);
    }

    public static void post(String urlString, RequestParams params,
                            AsyncHttpResponseHandler res) {
        asyncHttpClient.post(urlString, params, res);
    }

    public static void post(String uString, BinaryHttpResponseHandler bHandler) {
        asyncHttpClient.post(uString, bHandler);
    }

    public static AsyncHttpClient getClient() {
        return asyncHttpClient;
    }

    public static RequestParams getRequestParams() {
        RequestParams requestParams = new RequestParams();
        requestParams.add("__VIEWSTATE", __VIEWSTATE);
        requestParams.add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR);
        requestParams.add("TextBox1", TextBox1);
        requestParams.add("TextBox2", TextBox2);
        requestParams.add("TextBox3", TextBox3);
        requestParams.add("RadioButtonList1", RadioButtonList1);
        requestParams.add("Button1", Button1);
        return requestParams;
    }

    public static AsyncHttpClient getAsyncHttpClient() {
        return asyncHttpClient;
    }

    public static String getUrlCode() {
        return URL_CODE;
    }

    public static String getUrlLogin() {
        return URL_LOGIN;
    }

    public static String getTextBox1() {
        return TextBox1;
    }

    public static void setTextBox1(String textBox1) {
        TextBox1 = textBox1;
    }

    public static String getTextBox2() {
        return TextBox2;
    }

    public static void setTextBox2(String textBox2) {
        TextBox2 = textBox2;
    }

    public static String getTextBox3() {
        return TextBox3;
    }

    public static void setTextBox3(String textBox3) {
        TextBox3 = textBox3;
    }

    public static String getRadioButtonList1() {
        return RadioButtonList1;
    }

    public static void setRadioButtonList1(String radioButtonList1) {
        RadioButtonList1 = radioButtonList1;
    }
}
