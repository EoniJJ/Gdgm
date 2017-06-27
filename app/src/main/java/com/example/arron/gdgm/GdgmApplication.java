package com.example.arron.gdgm;

import android.app.Application;
import android.content.Context;

/**
 * Created by Arron on 2017/4/13.
 */

public class GdgmApplication extends Application {
    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = base;
    }

    public static Context getContext() {
        return context;
    }
}
