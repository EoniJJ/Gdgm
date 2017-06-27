package com.example.arron.gdgm.utils;

import android.widget.Toast;

import com.example.arron.gdgm.GdgmApplication;

/**
 * Created by Arron on 2016/7/28 0028.
 * Toast工具类
 * 确保客户端只存在一个Toast对象，防止多次点击时Toast无限弹出
 */
public class ToastUtil {
    private static Toast toast = null;

    private ToastUtil() {

    }

    /**
     * 显示Toast
     *
     * @param message 文本消息
     */
    public static void show( String message) {
        show(message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     *
     * @param message  文本消息
     * @param duration 显示时长
     */
    public static void show(String message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(GdgmApplication.getContext(), message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        toast.show();
    }
}
