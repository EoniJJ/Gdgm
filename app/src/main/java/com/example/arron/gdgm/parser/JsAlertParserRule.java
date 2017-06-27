package com.example.arron.gdgm.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by J。 on 2017/4/22.
 */

public class JsAlertParserRule extends BaseParserRule<String> {
    @Override
    public String parseRule(String html) {
        Document document = Jsoup.parse(html);
        Elements jsElements = document.select("script");
        for (Element element : jsElements) {
            String text = element.data();
            if (text.contains("alert(")) {
                String result = text.substring(text.indexOf("alert('") + "alert('".length(), text.lastIndexOf("')"));
                return result;
            }
        }
        return "";
    }
}
