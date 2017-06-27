package com.example.arron.gdgm.utils;

import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arron on 2017/4/13.
 */

public final class ApiManager {
    public static final String LOGIN = GdgmApplication.getContext().getString(R.string.login_url);
    public static final String EDUCATIONAL_HOST = GdgmApplication.getContext().getString(R.string.educational_host_url);
    public static final String LOGIN_VERIFICATION_CODE = GdgmApplication.getContext().getString(R.string.verification_code_url);
    public static final String NEWS = GdgmApplication.getContext().getString(R.string.news_url);
    public static final String HOST = GdgmApplication.getContext().getString(R.string.host);
    public static final String LIBRARY_HOME = GdgmApplication.getContext().getString(R.string.library_url);
    public static final Map<String, String> URL = new HashMap<>();
}
