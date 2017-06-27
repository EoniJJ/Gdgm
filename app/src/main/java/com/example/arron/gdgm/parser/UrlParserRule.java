package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.utils.ApiManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arron on 2017/4/13.
 */

public class UrlParserRule extends BaseParserRule<Map<String,String>> {
    @Override
    public Map<String, String> parseRule(String html) {
        Map<String, String> urlMap = new HashMap<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("ul.nav li.top ul.sub li a");
        for (Element element : elements) {
            String value = ApiManager.EDUCATIONAL_HOST + "/" + element.attr("href").toString();
            String key = element.text();
            urlMap.put(key, value);
        }
        return urlMap;
    }
}
