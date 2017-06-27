package com.example.arron.gdgm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.model.StudyResultModel;

/**
 * Created by J。 on 2017/4/22.
 */

public class StudyResultAdapter extends BaseQuickAdapter<StudyResultModel,BaseViewHolder> {
    public StudyResultAdapter() {
        super(R.layout.item_study_result);
    }
    @Override
    protected void convert(BaseViewHolder helper, StudyResultModel item) {
        helper.setText(R.id.tv_scoreName, item.getCourseName()).
                setText(R.id.tv_score, item.getCursorResult()).
                setText(R.id.tv_scoreNumber, item.getCourseCode()).
                setText(R.id.tv_scoreNature, item.getCourseNature()).
                setText(R.id.tv_scoreCredit, item.getCredits()).
                setText(R.id.tv_scoreAveragePoint, item.getGradePoint());
    }
}
