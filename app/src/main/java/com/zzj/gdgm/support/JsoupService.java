package com.zzj.gdgm.support;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J。 on 2016/4/18.
 * 封装好的Jsoup工具类
 */
public class JsoupService {
    private static final String TAG = "JsoupService";

    /**
     * 判断是否登录成功
     *
     * @param content 网页html源码
     * @return 若返回值为null，则表示登陆失败。
     */
    public static String isLogin(String content) {
        Document document = Jsoup.parse(content, "gb2312");
        Elements elements = document.select("span#xhxm");
        try {
            Element element = elements.get(0);
            return element.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取登录失败后的提示信息
     *
     * @param content 网页html源码
     * @return 返回登录失败后的提示信息
     */
    public static String getLoginErrorMessage(String content) {
        Document document = Jsoup.parse(content);
        Elements elements = document.select("script");
        try {
            Element element = elements.get(1);
            Log.d(TAG, "  element.data -->>>" + element.data().split("'")[1]);
            return element.data().split("'")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析menu菜单以获得子菜单的url
     *
     * @param content 网页html源码
     * @return 返回一个Map集合类, key为子菜单的title，value为url
     */
    public static Map<String, String> parseMenu(String content) {
        Map<String, String> map = new HashMap<>();
        Document document = Jsoup.parse(content);
        Elements elements = document.select("ul.nav a[target=zhuti]");
        for (Element element : elements) {
            map.put(element.text(), element.attr("href"));
        }
        return map;
    }

    /**
     * 获取并解析课程表
     *
     * @param content 网页Html源码
     * @return 返回一个存储课表的二维String数组
     */
    public static String[][] getCourse(String content) {
        Document document = Jsoup.parse(content);
        Elements elements = document.select("table#Table6.blacktab tr");
        //移除无效的数据
        elements.remove(0);
        elements.remove(0);
        elements.remove(1);
        elements.remove(2);
        elements.remove(3);
        elements.remove(4);
        elements.remove(5);
        String[][] course = new String[6][7];
        for (int i = 0; i < elements.size(); i++) {
            Elements elements1 = elements.get(i).select("td");
            if (i == 0 || i == 2 || i == 4) {
                elements1.remove(0);
                elements1.remove(0);
            } else {
                elements1.remove(0);
            }
            for (int j = 0; j < elements1.size(); j++) {
                String text = elements1.get(j).text().replace("<br>", "\n").replace("&nbsp;", "");
                course[i][j] = text;
            }
        }
        return course;
    }
}
