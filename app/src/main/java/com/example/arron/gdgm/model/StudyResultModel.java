package com.example.arron.gdgm.model;

/**
 * 学习成绩model
 * Created by Arron on 2017/4/13.
 */

public class StudyResultModel {
    private String year;//学年
    private String semester;//学期
    private String courseCode;//课程代码
    private String courseName;//课程名称
    private String courseNature;//课程性质
    private String cursorAttribution;//课程归属
    private String credits;//学分
    private String gradePoint;//绩点
    private String cursorResult;//课程成绩
    private String minorTag;//辅修标记
    private String retestResult;//补考成绩
    private String resetResult;//重修成绩
    private String collegeName;//学院名称
    private String remark;//备注
    private String resetTag;//重修标记

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

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNature() {
        return courseNature;
    }

    public void setCourseNature(String courseNature) {
        this.courseNature = courseNature;
    }

    public String getCursorAttribution() {
        return cursorAttribution;
    }

    public void setCursorAttribution(String cursorAttribution) {
        this.cursorAttribution = cursorAttribution;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(String gradePoint) {
        this.gradePoint = gradePoint;
    }

    public String getCursorResult() {
        return cursorResult;
    }

    public void setCursorResult(String cursorResult) {
        this.cursorResult = cursorResult;
    }

    public String getMinorTag() {
        return minorTag;
    }

    public void setMinorTag(String minorTag) {
        this.minorTag = minorTag;
    }

    public String getRetestResult() {
        return retestResult;
    }

    public void setRetestResult(String retestResult) {
        this.retestResult = retestResult;
    }

    public String getResetResult() {
        return resetResult;
    }

    public void setResetResult(String resetResult) {
        this.resetResult = resetResult;
    }

    public String getResetTag() {
        return resetTag;
    }

    public void setResetTag(String resetTag) {
        this.resetTag = resetTag;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "StudyResultModel{" +
                "year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseNature='" + courseNature + '\'' +
                ", cursorAttribution='" + cursorAttribution + '\'' +
                ", credits='" + credits + '\'' +
                ", gradePoint='" + gradePoint + '\'' +
                ", cursorResult='" + cursorResult + '\'' +
                ", minorTag='" + minorTag + '\'' +
                ", retestResult='" + retestResult + '\'' +
                ", resetResult='" + resetResult + '\'' +
                ", resetTag='" + resetTag + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
