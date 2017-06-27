package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.model.FilterMenuModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J。 on 2017/4/23.
 */

public class CourseFilterMenuParserRule extends BaseParserRule<FilterMenuModel> {
    @Override
    public FilterMenuModel parseRule(String html) {
        FilterMenuModel filterMenuModel = new FilterMenuModel();
        Document document = Jsoup.parse(html);
        List<String> years = new ArrayList<>();
        filterMenuModel.setYear(years);
        List<String> semesters = new ArrayList<>();
        filterMenuModel.setSemester(semesters);
        Elements elementYears = document.select("table#Table1 select#xn option");
        for (Element elementYear : elementYears) {
            years.add(elementYear.text());
        }
        Elements elementsSemesters = document.select("table#Table1 select#xq option");
        for (Element elementSemester : elementsSemesters) {
            semesters.add(elementSemester.text());
        }
        return filterMenuModel;
    }
}
