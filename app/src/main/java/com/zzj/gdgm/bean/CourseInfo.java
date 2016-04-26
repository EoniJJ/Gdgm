package com.zzj.gdgm.bean;

import java.io.Serializable;

/**
 * Created by J。 on 2016/4/26.
 * 课程成绩信息
 */
public class CourseInfo implements Serializable{
    private static final long serialVersionUID = -435247096032449017L;
    /**
     * 学年
     */
    private String year ;
    /**
     * 学期
     */
    private String semester;
    /**
     * 课程代码
     */
    private String course_code;
    /**
     * 课程名字
     */
    private String course_name;
    /**
     * 课程性质
     */
    private String course_nature;
    /**
     * 课程归属
     */
    private String course_belong;
    /**
     * 课程学分
     */
    private String course_credit;
    /**
     * 课程绩点
     */
    private String course_average_point;
    /**
     * 课程分数
     */
    private String course_score;
    /**
     * 辅修标记
     */
    private String course_aid_remark;
    /**
     * 补考成绩
     */
    private String course_make_up_score;
    /**
     * 重修成绩
     */
    private String course_rebuild;
    /**
     * 学院名称
     */
    private String course_college;
    /**
     * 备注
     */
    private String remark;
    /**
     * 重修标记
     */
    private String course_rebuild_mark;

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

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_nature() {
        return course_nature;
    }

    public void setCourse_nature(String course_nature) {
        this.course_nature = course_nature;
    }

    public String getCourse_belong() {
        return course_belong;
    }

    public void setCourse_belong(String course_belong) {
        this.course_belong = course_belong;
    }

    public String getCourse_credit() {
        return course_credit;
    }

    public void setCourse_credit(String course_credit) {
        this.course_credit = course_credit;
    }

    public String getCourse_average_point() {
        return course_average_point;
    }

    public void setCourse_average_point(String course_average_point) {
        this.course_average_point = course_average_point;
    }

    public String getCourse_score() {
        return course_score;
    }

    public void setCourse_score(String course_score) {
        this.course_score = course_score;
    }

    public String getCourse_aid_remark() {
        return course_aid_remark;
    }

    public void setCourse_aid_remark(String course_aid_remark) {
        this.course_aid_remark = course_aid_remark;
    }

    public String getCourse_make_up_score() {
        return course_make_up_score;
    }

    public void setCourse_make_up_score(String course_make_up_score) {
        this.course_make_up_score = course_make_up_score;
    }

    public String getCourse_rebuild() {
        return course_rebuild;
    }

    public void setCourse_rebuild(String course_rebuild) {
        this.course_rebuild = course_rebuild;
    }

    public String getCourse_college() {
        return course_college;
    }

    public void setCourse_college(String course_college) {
        this.course_college = course_college;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCourse_rebuild_mark() {
        return course_rebuild_mark;
    }

    public void setCourse_rebuild_mark(String course_rebuild_mark) {
        this.course_rebuild_mark = course_rebuild_mark;
    }
}
