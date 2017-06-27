package com.example.arron.gdgm.parser;

import com.example.arron.gdgm.model.NewsModel;
import com.example.arron.gdgm.utils.ApiManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arron on 2017/4/14.
 */

public class NewsParserRule extends BaseParserRule<List<NewsModel>> {
    @Override
    public List<NewsModel> parseRule(String html) {
        List<NewsModel> newsModels = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table tbody tr td a");
        for (Element element : elements) {
            NewsModel newsModel = new NewsModel();
            if (element.select("font[color='#000000']").isEmpty()) {
                continue;
            }
            newsModel.setNewsName(element.select("font[color='#000000']").first().ownText());
            if (!element.select("font font").isEmpty()) {
                newsModel.setDate(element.select("font font").text());
            }
            newsModel.setDetailUrl(ApiManager.HOST + "/" + element.attr("href"));
            newsModels.add(newsModel);
        }
        return newsModels;
    }
}
