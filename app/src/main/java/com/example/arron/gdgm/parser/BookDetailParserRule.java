package com.example.arron.gdgm.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arron on 2017/4/25.
 */

public class BookDetailParserRule extends BaseParserRule<List<BookDetailParserRule.Item>> {

    @Override
    public List<Item> parseRule(String html) {
        List<Item> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements keys = document.select("table td.VName");
        Elements values = document.select("table td.VValue");
        if (keys.size() != values.size()) {
            return list;
        }
        for (int i = 0; i < keys.size(); i++) {
            Item item = new Item();
            item.key = keys.get(i).text();
            item.value = values.get(i).text();
            list.add(item);
        }
        return list;
    }

    public static class Item {
        public String key;
        public String value;
    }
}
