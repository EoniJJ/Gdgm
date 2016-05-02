package com.zzj.gdgm.support;

import android.app.Application;
import android.content.Context;

/**
 * Created by J。 on 2016/4/19.
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * 静态方法以供全局调用Application的context
     *
     * @return Application的Context对象
     */
    public static Context getContext() {
        return context;
    }
}
