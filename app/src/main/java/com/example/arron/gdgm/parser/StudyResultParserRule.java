package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.model.StudyResultModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arron on 2017/4/13.
 */

public class StudyResultParserRule extends BaseParserRule<List<StudyResultModel>> {
    @Override
    public List<StudyResultModel> parseRule(String html) {
        List<StudyResultModel> studyResultModels = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table#Datagrid1.datelist tr:not([class=datelisthead])");
        for (Element element : elements) {
            StudyResultModel studyResultModel = new StudyResultModel();
            Elements tds = element.select("td");
            for (int i = 0; i < tds.size(); i++) {
                String text = tds.get(i).text();
                switch (i) {
                    case 0:
                        studyResultModel.setYear(text);
                        break;
                    case 1:
                        studyResultModel.setSemester(text);
                        break;
                    case 2:
                        studyResultModel.setCourseCode(text);
                        break;
                    case 3:
                        studyResultModel.setCourseName(text);
                        break;
                    case 4:
                        studyResultModel.setCourseNature(text);
                        break;
                    case 5:
                        studyResultModel.setCursorAttribution(text);
                        break;
                    case 6:
                        studyResultModel.setCredits(text);
                        break;
                    case 7:
                        studyResultModel.setGradePoint(text);
                        break;
                    case 8:
                        studyResultModel.setCursorResult(text);
                        break;
                    case 9:
                        studyResultModel.setMinorTag(text);
                        break;
                    case 10:
                        studyResultModel.setRetestResult(text);
                        break;
                    case 11:
                        studyResultModel.setResetResult(text);
                        break;
                    case 12:
                        studyResultModel.setCollegeName(text);
                        break;
                    case 13:
                        studyResultModel.setRemark(text);
                        break;
                    case 14:
                        studyResultModel.setResetTag(text);
                        break;
                }
            }
            studyResultModels.add(studyResultModel);
        }
        return studyResultModels;
    }
}
