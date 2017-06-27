package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.model.FilterMenuModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J。 on 2017/4/22.
 */

public class StudyResultFilterMenuParserRule extends BaseParserRule<FilterMenuModel> {
    @Override
    public FilterMenuModel parseRule(String html) {
        FilterMenuModel filterMenuModel = new FilterMenuModel();
        List<String> temp = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("select#ddlXN option");
        for (Element element : elements) {
            if (element.text() != null && element.text().length() != 0) {
                temp.add(element.text());
            }
        }
        filterMenuModel.setYear(temp);
        elements = document.select("select#ddlXQ option");
        temp = new ArrayList<>();
        for (Element element : elements) {
            if (element.text() != null && element.text().length() != 0) {
                temp.add(element.text());
            }
        }
        filterMenuModel.setSemester(temp);
        return filterMenuModel;
    }
}
