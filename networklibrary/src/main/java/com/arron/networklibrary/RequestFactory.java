package com.arron.networklibrary;

import com.arron.networklibrary.request.IRequestManager;
import com.arron.networklibrary.request.OkHttpRequestManager;

/**
 * 请求工具工厂类
 * Created by Arron on 2017/1/3 0003.
 */

public class RequestFactory {

    /**
     * 返回一个IRequestManager对象，这个对象是IRequestManager的实现类
     */
    public static IRequestManager getRequestManager() {
        return OkHttpRequestManager.getInstance();
    }
}
