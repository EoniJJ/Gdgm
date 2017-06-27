package com.example.arron.gdgm.contract;

import com.example.arron.gdgm.base.BasePresenter;
import com.example.arron.gdgm.base.BaseView;
import com.example.arron.gdgm.model.FilterMenuModel;
import com.example.arron.gdgm.model.StudyResultModel;

import java.util.List;

/**
 * Created by Arron on 2017/4/13.
 */

public class StudyResultContract {
    public interface View extends BaseView {
        void showFilterMenu(FilterMenuModel filterMenuModel);

        void showStudyResult(List<StudyResultModel> studyResultModelList);
    }

    public interface Presenter extends BasePresenter<View> {
        void getStudyResult(String year,String semester);
    }
}
