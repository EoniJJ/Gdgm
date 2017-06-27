package com.example.arron.gdgm.model;

/**
 * 图书实体类
 */
public class BookModel {
    /**
     * 分类号
     */
    private String classification;
    /**
     * 书名
     */
    private String bookName;
    /**
     * 作者
     */
    private String author;
    /**
     * 详情页url
     */
    private String urlDetail;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }
}
