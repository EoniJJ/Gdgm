package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.model.LevelGradeModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arron on 2017/4/14.
 */

public class LevelGradeParserRule extends BaseParserRule<List<LevelGradeModel>> {
    @Override
    public List<LevelGradeModel> parseRule(String html) {
        List<LevelGradeModel> levelGradeModels = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table#DataGrid1.datelist tr:not([class=datelisthead])");
        for (Element element : elements) {
            LevelGradeModel levelGradeModel = new LevelGradeModel();
            Elements tds = element.select("td");
            for (int i = 0; i < tds.size(); i++) {
                switch (i) {
                    case 0:
                        levelGradeModel.setYear(tds.get(i).text());
                        break;
                    case 1:
                        levelGradeModel.setSemester(tds.get(i).text());
                        break;
                    case 2:
                        levelGradeModel.setExamName(tds.get(i).text());
                        break;
                    case 3:
                        levelGradeModel.setCardNumber(tds.get(i).text());
                        break;
                    case 4:
                        levelGradeModel.setExamDate(tds.get(i).text());
                        break;
                    case 5:
                        levelGradeModel.setScore(tds.get(i).text());
                        break;
                    case 6:
                        levelGradeModel.setListenScore(tds.get(i).text());
                        break;
                    case 7:
                        levelGradeModel.setReadScore(tds.get(i).text());
                        break;
                    case 8:
                        levelGradeModel.setWriteScore(tds.get(i).text());
                        break;
                    case 9:
                        levelGradeModel.setSynthesizeScore(tds.get(i).text());
                        break;
                }
            }
            levelGradeModels.add(levelGradeModel);
        }
        return levelGradeModels;
    }
}
