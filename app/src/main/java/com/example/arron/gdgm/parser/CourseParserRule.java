package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.model.CourseModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arron on 2017/4/14.
 */

public class CourseParserRule extends BaseParserRule<List<CourseModel>> {
    @Override
    public List<CourseModel> parseRule(String html) {
        List<CourseModel> courseModels = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements trs = document.select("table#Table6.blacktab tr");
        if (trs.isEmpty()) {
            return courseModels;
        }
        String trHtml = trs.first().html();
        if (trHtml.contains("时间") && trHtml.contains("星期一")) {
            trs.remove(trs.first());
        }
        if (trs.isEmpty()) {
            return courseModels;
        }
        trHtml = trs.first().html();
        if (trHtml.contains("早晨")) {
            trs.remove(trs.first());
        }
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            if (tds.isEmpty()) {
                continue;
            }
            if (tds.first().text().contains("上午") || tds.first().text().contains("下午") || tds.first().text().contains("晚上")) {
                tds.remove(tds.first());
            }
            if (tds.isEmpty()) {
                continue;
            }
            tds.remove(tds.first());
            if (tds.size() < CourseModel.Day.values().length) {
                continue;
            }
            for (int i = 0; i < tds.size(); i++) {
                CourseModel courseModel = new CourseModel();
                courseModel.setDay(CourseModel.Day.values()[i]);
                String temp = tds.get(i).toString().replace("<br>", "~").replace("&nbsp;", "`");
                String courseResult = Jsoup.parse(temp).text();
                if (!courseResult.equals("`")) {
                    String[] courseInfo = courseResult.split("~");
                    for (int i1 = 0; i1 < courseInfo.length; i1++) {
                        switch (i1) {
                            case 0:
                                courseModel.setCourseName(courseInfo[i1]);
                                break;
                            case 1:
                                String weeksAndSection = courseInfo[i1];
                                String weeks = null;
                                int firstSection = 0;
                                int lastSection = 0;
                                try {
                                    weeks = weeksAndSection.substring(0, weeksAndSection.indexOf("("));
                                    firstSection = Integer.valueOf(weeksAndSection.substring(weeksAndSection.indexOf("(") + 1, weeksAndSection.indexOf("(") + 2));
                                    lastSection = Integer.valueOf(weeksAndSection.substring(weeksAndSection.lastIndexOf(")") - 1, weeksAndSection.lastIndexOf(")")));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                courseModel.setWeeks(weeks);
                                courseModel.setFirstSection(firstSection);
                                courseModel.setLastSection(lastSection);
                                break;
                            case 2:
                                courseModel.setCourseTeacher(courseInfo[i1]);
                                break;
                            case 3:
                                courseModel.setClassroom(courseInfo[i1]);
                                break;
                        }
                    }
                    courseModels.add(courseModel);
                }
            }
        }
        return courseModels;
    }
}
