package com.example.arron.gdgm.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Arron on 2017/4/21.
 */

public class EncoderUtil {
    /**
     * 将Url中的中文进行编码
     *
     * @param url 要进行编码的Url
     * @return 编码后的url
     */
    public static String encodeUrl(String url) {
        return encodeUrl(url, "gb2312");
    }

    public static String encodeUrl(String url,String enc) {
        String new_url = url;
        Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(new_url);
        while (matcher.find()) {
            try {
                new_url = new_url.replaceAll(matcher.group(), URLEncoder.encode(matcher.group(), enc));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return new_url;
    }
}
