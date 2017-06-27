package com.example.arron.gdgm.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arron on 2017/4/20.
 */

public class LibrarySearchItemRule extends BaseParserRule<Map<String,String>> {

    @Override
    public Map<String, String> parseRule(String html) {
        Map<String, String> map = new HashMap<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#jiansuo select option");
        for (Element element : elements) {
            String key = element.val();
            String value = element.text();
            map.put(key, value);
        }
        return map;
    }
}
