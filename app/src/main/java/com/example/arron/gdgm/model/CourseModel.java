package com.example.arron.gdgm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Arron on 2017/4/14.
 */

public class CourseModel implements Parcelable {

    public enum Day {
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;

        public static int indexOf(Day day) {
            Day[] days = Day.values();
            for (int i = 0; i < days.length; i++) {
                if (days[i] == day) {
                    return i;
                }
            }
            return -1;
        }

        private static final String[] DAY_CN = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

        public static String valueOf(Day day) {
            int position = indexOf(day);
            if (position != -1) {
                return DAY_CN[position];
            } else {
                return "";
            }
        }
    }


    private String courseName;//课程名称
    private String courseTeacher;//课程教师
    private String classroom;//教室
    private String weeks;//周数
    private int firstSection;//节数
    private int lastSection;//节数
    private Day day;//周几

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public int getFirstSection() {
        return firstSection;
    }

    public void setFirstSection(int firstSection) {
        this.firstSection = firstSection;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public int getLastSection() {
        return lastSection;
    }

    public void setLastSection(int lastSection) {
        this.lastSection = lastSection;
    }

    @Override
    public String toString() {
        return "CourseModel{" +
                "courseName='" + courseName + '\'' +
                ", courseTeacher='" + courseTeacher + '\'' +
                ", classroom='" + classroom + '\'' +
                ", weeks='" + weeks + '\'' +
                ", firstSection=" + firstSection +
                ", lastSection=" + lastSection +
                ", day=" + day +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.courseName);
        dest.writeString(this.courseTeacher);
        dest.writeString(this.classroom);
        dest.writeString(this.weeks);
        dest.writeInt(this.firstSection);
        dest.writeInt(this.lastSection);
        dest.writeInt(this.day == null ? -1 : this.day.ordinal());
    }

    public CourseModel() {
    }

    protected CourseModel(Parcel in) {
        this.courseName = in.readString();
        this.courseTeacher = in.readString();
        this.classroom = in.readString();
        this.weeks = in.readString();
        this.firstSection = in.readInt();
        this.lastSection = in.readInt();
        int tmpDay = in.readInt();
        this.day = tmpDay == -1 ? null : Day.values()[tmpDay];
    }

    public static final Parcelable.Creator<CourseModel> CREATOR = new Parcelable.Creator<CourseModel>() {
        @Override
        public CourseModel createFromParcel(Parcel source) {
            return new CourseModel(source);
        }

        @Override
        public CourseModel[] newArray(int size) {
            return new CourseModel[size];
        }
    };
}
