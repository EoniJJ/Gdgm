package com.example.arron.gdgm.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Arron on 2017/6/27.
 */

public class CommonUtils {
    public static boolean isDemo = true;

    public static String readAssetsHtml(Context context,String htmlFileName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(htmlFileName);
            return ConvertUtils.inputStream2String(inputStream, "gb2312");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeIO(inputStream);
        }
        return "";
    }

}
