package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by J。 on 2017/4/21.
 */

public class BookListNextPageParserRule extends BaseParserRule<String> {
    @Override
    public String parseRule(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#right_m td.title a");
        for (Element element : elements) {
            if (element.text().contains("下页")) {
                return GdgmApplication.getContext().getString(R.string.library_home_url)+"/chaxun/" + element.attr("href");
            }
        }
        return null;
    }
}
