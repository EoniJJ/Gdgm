package com.example.arron.gdgm.model;

/**
 * 等级考试Model
 * Created by J。 on 2016/6/5.
 */
public class LevelGradeModel {
    private String year;//学年
    private String semester;//学期
    private String examName;//等级考试名称
    private String cardNumber;//准考证号
    private String examDate;//考试日期
    private String score;//分数
    private String listenScore;//听力成绩
    private String readScore;//阅读成绩
    private String writeScore;//写作成绩
    private String synthesizeScore;//综合成绩

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getListenScore() {
        return listenScore;
    }

    public void setListenScore(String listenScore) {
        this.listenScore = listenScore;
    }

    public String getReadScore() {
        return readScore;
    }

    public void setReadScore(String readScore) {
        this.readScore = readScore;
    }

    public String getWriteScore() {
        return writeScore;
    }

    public void setWriteScore(String writeScore) {
        this.writeScore = writeScore;
    }

    public String getSynthesizeScore() {
        return synthesizeScore;
    }

    public void setSynthesizeScore(String synthesizeScore) {
        this.synthesizeScore = synthesizeScore;
    }
}
