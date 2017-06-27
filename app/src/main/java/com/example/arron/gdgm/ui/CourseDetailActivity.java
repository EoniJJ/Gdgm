package com.example.arron.gdgm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BaseActivity;
import com.example.arron.gdgm.model.CourseModel;

/**
 * Created by Arron on 2017/4/25.
 */

public class CourseDetailActivity extends BaseActivity {
    private TextView tvCourseName;
    private TextView tvClassroom;
    private TextView tvWeeks;
    private TextView tvSection;
    private TextView tvTeacher;
    private CourseModel courseModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        courseModel = initIntentData();
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.course_detail);
    }

    @Override
    protected void initView() {
        tvCourseName = (TextView) findViewById(R.id.tv_courseName);
        tvClassroom = (TextView) findViewById(R.id.tv_classroom);
        tvWeeks = (TextView) findViewById(R.id.tv_weeks);
        tvSection = (TextView) findViewById(R.id.tv_section);
        tvTeacher = (TextView) findViewById(R.id.tv_teacher);
    }

    @Override
    protected void backClick(View v) {
        super.backClick(v);
        finish();
    }

    @Override
    protected void initData() {
        if (courseModel == null) {
            return;
        }
        showDataToView(courseModel);
    }

    private void showDataToView(CourseModel courseModel) {
        tvCourseName.setText(courseModel.getCourseName());
        tvClassroom.setText(courseModel.getClassroom());
        tvWeeks.setText(courseModel.getWeeks());
        tvTeacher.setText(courseModel.getCourseTeacher());
        tvSection.setText(CourseModel.Day.valueOf(courseModel.getDay()) + " 第" + courseModel.getFirstSection() + "-" +
                courseModel.getLastSection() + "节");
    }

    /**
     * 跳转到{@link CourseDetailActivity}
     * @param context   {@link Context}
     * @param courseModel {@link CourseModel}
     */
    public static void start(Context context, CourseModel courseModel) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra("course", courseModel);
        context.startActivity(intent);
    }

    private CourseModel initIntentData() {
        CourseModel courseModel = getIntent().getParcelableExtra("course");
        return courseModel;
    }
}
