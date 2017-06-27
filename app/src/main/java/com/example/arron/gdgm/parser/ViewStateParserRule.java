package com.example.arron.gdgm.parser;

import org.jsoup.Jsoup;

/**
 * Created by Arron on 2017/4/12.
 */

public class ViewStateParserRule extends BaseParserRule<String> {
    @Override
    public String parseRule(String html) {
        String __VIEWSTATE = Jsoup.parse(html).select("input[name='__VIEWSTATE']").val();
        return __VIEWSTATE;
    }
}
