package com.example.arron.gdgm.model;

/**
 * Created by Arron on 2017/4/14.
 */

public class NewsModel {
    private String newsName;//新闻名称
    private String date;//时间
    private String detailUrl;//详情url

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "newsName='" + newsName + '\'' +
                ", date='" + date + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                '}';
    }
}
