package com.zzj.gdgm.bean;

/**
 * Created by J。 on 2016/5/1.
 * 图书实体类
 */
public class BookInfo {
    /**
     * 分类号
     */
    private String classification;
    /**
     * 书名
     */
    private String book_name;
    /**
     * 作者
     */
    private String author;
    /**
     * 详情页url
     */
    private String url_detail;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl_detail() {
        return url_detail;
    }

    public void setUrl_detail(String url_detail) {
        this.url_detail = url_detail;
    }
}
