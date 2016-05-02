package com.zzj.gdgm.support;

import android.util.Log;

import com.zzj.gdgm.bean.BookInfo;
import com.zzj.gdgm.bean.CourseInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J。 on 2016/4/18.
 * 封装好的Jsoup工具类
 */
public class JsoupService {
    private static final String TAG = "JsoupService";

    /**
     * 菜单名称和链接的集合,key为title,value为链接
     */
    private static Map<String, String> linkMap;

    /**
     * 判断是否登录成功
     *
     * @param content 网页html源码
     * @return 若返回值为null，则表示登陆失败。
     */
    public static String isLogin(String content) {
        Document document = Jsoup.parse(content, "gb2312");
        Elements elements = document.select("span#xhxm");
        try {
            Element element = elements.get(0);
            return element.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取登录失败后的提示信息
     *
     * @param content 网页html源码
     * @return 返回登录失败后的提示信息
     */
    public static String getLoginErrorMessage(String content) {
        Document document = Jsoup.parse(content);
        Elements elements = document.select("script");
        try {
            Element element = elements.get(1);
            Log.d(TAG, "  element.data -->>>" + element.data().split("'")[1]);
            return element.data().split("'")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取修改密码的信息
     *
     * @param content 网页html源码
     * @return 返回修改密码后的状态信息
     */
    public static String getUpdatePasswordMessage(String content) {
        Document document = Jsoup.parse(content);
        Elements elements = document.select("script");
        try {
            Element element = elements.get(0);
            Log.d(TAG, element.data().split("'")[1]);
            return element.data().split("'")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析menu菜单以获得子菜单的url
     *
     * @param content 网页html源码
     * @return 返回一个Map集合类, key为子菜单的title，value为url
     */
    public static Map<String, String> parseMenu(String content) {
        Map<String, String> map = new HashMap<>();
        Document document = Jsoup.parse(content);
        Elements elements = document.select("ul.nav a[target=zhuti]");
        for (Element element : elements) {
            map.put(element.text(), element.attr("href"));
        }
        linkMap = map;
        return map;
    }

    /**
     * 获取并解析课程表
     *
     * @param content 网页Html源码
     * @return 返回一个存储课表的二维String数组
     */
    public static String[][] getCourse(String content) {
        Document document = Jsoup.parse(content);
        Elements elements = document.select("table#Table6.blacktab tr");
        //移除无效的数据
        elements.remove(0);
        elements.remove(0);
        elements.remove(1);
        elements.remove(2);
        elements.remove(3);
        elements.remove(4);
        elements.remove(5);
        String[][] course = new String[6][7];
        for (int i = 0; i < elements.size(); i++) {
            Elements elements1 = elements.get(i).select("td");
            if (i == 0 || i == 2 || i == 4) {
                elements1.remove(0);
                elements1.remove(0);
            } else {
                elements1.remove(0);
            }
            for (int j = 0; j < elements1.size(); j++) {
                String text = elements1.get(j).text().replace("<br>", "\n").replace("&nbsp;", "");
                course[i][j] = text;
            }
        }
        return course;
    }

    /**
     * 获取学年学期以及请求参数
     *
     * @param content html源代码
     * @return 存储学年学期请求参数的map集合
     */
    public static Map<String, Object> getScoreYear(String content) {
        Map<String, Object> map = new HashMap<>();
        Document document = Jsoup.parse(content);
        Elements elements = document.select("input[name=__VIEWSTATE]");
        map.put("__VIEWSTATE", elements.get(0).val());
        elements = document.select("input[name=__VIEWSTATEGENERATOR]");
        map.put("__VIEWSTATEGENERATOR", elements.get(0).val());
        elements = document.select("select[name=ddlXN] option");
        List<String> score_year = new ArrayList<>();
        for (Element element : elements) {
            score_year.add(element.val());
        }
        map.put("score_year", score_year);
        elements = document.select("select[name=ddlXQ] option");
        List<String> score_semester = new ArrayList<>();
        for (Element element : elements) {
            score_semester.add(element.val());
        }
        map.put("score_semester", score_semester);
        return map;
    }

    /**
     * 将成绩解析为实体
     *
     * @param content html源代码
     * @return 所有课程成绩的list集合
     */
    public static ArrayList<CourseInfo> parseCourseScore(String content) {
        Document document = Jsoup.parse(content);
        Elements elements = document.select("table#Datagrid1.datelist tr");
        //移除无效项
        elements.remove(0);
        ArrayList<CourseInfo> courseInfoArrayList = new ArrayList<>();
        for (Element element : elements) {
            Elements elements1 = element.select("td");
            CourseInfo courseInfo = new CourseInfo();
            /**
             * 赋值学年
             */
            courseInfo.setYear(elements1.get(0).text());
            /**
             * 赋值学期
             */
            courseInfo.setSemester(elements1.get(1).text());
            /**
             * 赋值课程代码
             */
            courseInfo.setCourse_code(elements1.get(2).text());
            /**
             * 赋值课程名称
             */
            courseInfo.setCourse_name(elements1.get(3).text());
            /**
             * 赋值课程性质
             */
            courseInfo.setCourse_nature(elements1.get(4).text());
            /**
             * 赋值课程归属
             */
            courseInfo.setCourse_belong(elements1.get(5).text());
            /**
             * 赋值课程学分
             */
            courseInfo.setCourse_credit(elements1.get(6).text());
            /**
             * 赋值课程绩点
             */
            courseInfo.setCourse_average_point(elements1.get(7).text());
            /**
             * 赋值课程分数
             */
            courseInfo.setCourse_score(elements1.get(8).text());
            /**
             * 赋值辅修标记
             */
            courseInfo.setCourse_aid_remark(elements1.get(9).text());
            /**
             * 赋值补考成绩
             */
            courseInfo.setCourse_make_up_score(elements1.get(10).text());
            /**
             * 赋值重修成绩
             */
            courseInfo.setCourse_rebuild(elements1.get(11).text());
            /**
             * 赋值院系
             */
            courseInfo.setCourse_college(elements1.get(12).text());
            /**
             * 赋值备注
             */
            courseInfo.setRemark(elements1.get(13).text());
            /**
             * 赋值重修标记
             */
            courseInfo.setCourse_rebuild_mark(elements1.get(14).text());

            //添加到集合
            courseInfoArrayList.add(courseInfo);
        }
        return courseInfoArrayList;
    }

    /**
     * 解析个人信息
     *
     * @param content 网页html源代码
     * @return Map集合, key为信息类别, value为个人信息
     */
    public static Map<String, String> parsePersonInfo(String content) {
        Document document = Jsoup.parse(content);
        Map<String, String> map = new HashMap<>();
        Elements elements = document.select("table.formlist td.trbg1");
        Elements elements1 = document.select("table.formlist span");
        map.put(elements.get(0).text(), elements1.get(0).text());
        map.put(elements.get(3).text(), elements1.get(5).text());
        map.put(elements.get(7).text(), elements1.get(9).text());
        map.put(elements.get(9).text(), elements1.get(11).text());
        map.put(elements.get(10).text(), elements1.get(12).text());
        map.put(elements.get(12).text(), elements1.get(14).text());
        map.put(elements.get(15).text(), elements1.get(17).text());
        map.put(elements.get(21).text(), elements1.get(24).text());
        map.put(elements.get(24).text(), elements1.get(27).text());
        map.put(elements.get(28).text(), elements1.get(31).text());
        map.put(elements.get(31).text(), elements1.get(34).text());
        map.put(elements.get(34).text(), elements1.get(37).text());
        map.put(elements.get(36).text(), elements1.get(39).text());
        map.put(elements.get(42).text(), elements1.get(45).text());
        map.put(elements.get(47).text(), elements1.get(51).text());
        map.put(elements.get(49).text(), elements1.get(53).text());
        map.put(elements.get(54).text(), elements1.get(57).text());
        map.put(elements.get(57).text(), elements1.get(59).text());
        map.put(elements.get(60).text(), elements1.get(61).text());
        map.put(elements.get(61).text(), elements1.get(62).text());
        return map;
    }


    public static Map<String, String> getLinkMap() {
        return linkMap;
    }

    /**
     * 解析查询图书信息
     *
     * @param content 网页html源代码
     * @return Map集合，key为信息种类  book_list —> 对应图书信息，value为存放图书信息的集合  next_page ->对应下一页 ，若存在下一页，则该值不为空。
     */
    public static Map<String, Object> parseLibrary(String content) {
        Document document = Jsoup.parse(content);
        Elements elements_classification = document.select("div#right_m td[width=15%]");
        Elements elements_book_name = document.select("div#right_m td[width=45%]");
        Elements elements_author = document.select("div#right_m td:not([align=right])[width=40%]");
        Map<String, Object> map = new HashMap<>();
        //存放图书信息的集合
        List<BookInfo> bookInfoList = new ArrayList<>();
        for (int i = 0; i < elements_classification.size(); i++) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setClassification(elements_classification.get(i).text());
            bookInfo.setBook_name(elements_book_name.get(i).text());
            bookInfo.setAuthor(elements_author.get(i).text());
            bookInfo.setUrl_detail(elements_classification.get(i).select("a").get(0).attr("href"));
            //将图书实体添加到集合
            bookInfoList.add(bookInfo);
        }
        map.put("book_list", bookInfoList);
        Elements elements_next_page = document.select("div#right_m td.title[colspan=3] a");
        for (Element element : elements_next_page) {
            //若存在下一页，将url存入Map集合
            if (element.text().equals("下页")) {
                map.put("next_page", element.attr("href"));
            }
        }
        return map;
    }

    /**
     * 解析图书详情
     *
     * @param content 网页html源代码
     * @return Map集合  key为种类  value为存储该类型数据的集合
     */
    public static Map<String, List<String>> parseBookDetail(String content) {
        Document document = Jsoup.parse(content);
        Elements td_name = document.select("td.VName");
        Elements td_value = document.select("td.VValue");
        Map<String, List<String>> map = new HashMap<>();
        List<String> title = new ArrayList<>();
        List<String> text = new ArrayList<>();
        for (int i = 0; i < td_name.size(); i++) {
            title.add(td_name.get(i).text());
            text.add(td_value.get(i).text());
        }
        map.put("title", title);
        map.put("text", text);
        return map;
    }
}
