package com.example.arron.gdgm.contract;

import com.example.arron.gdgm.base.BasePresenter;
import com.example.arron.gdgm.base.BaseView;
import com.example.arron.gdgm.model.CourseModel;
import com.example.arron.gdgm.model.FilterMenuModel;

import java.util.List;

/**
 * Created by Arron on 2017/4/18.
 */

public class CourseContract {
    public interface View extends BaseView {
        void showCourses(List<CourseModel> courseModelList);

        void showFilterMenu(FilterMenuModel filterMenuModel);
    }

    public interface Presenter extends BasePresenter<View> {
        void getCourses();

        void getCourses(String year, String semester);
    }

}
