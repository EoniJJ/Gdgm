package com.example.arron.gdgm.contract;

import com.example.arron.gdgm.base.BasePresenter;
import com.example.arron.gdgm.base.BaseView;
import com.example.arron.gdgm.model.LevelGradeModel;

import java.util.List;

/**
 * Created by J。 on 2017/4/23.
 */

public class LevelGradeContract  {
    public interface View extends BaseView {
        void showLevelGrade(List<LevelGradeModel> levelGradeModelList);
    }

    public interface Presenter extends BasePresenter<View> {
        void getLevelGradeResult();
    }
}
