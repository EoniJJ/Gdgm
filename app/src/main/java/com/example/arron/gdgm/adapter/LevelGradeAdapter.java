package com.example.arron.gdgm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.model.LevelGradeModel;

/**
 * Created by J。 on 2017/4/23.
 */

public class LevelGradeAdapter extends BaseQuickAdapter<LevelGradeModel,BaseViewHolder> {
    public LevelGradeAdapter() {
        super(R.layout.item_grade_result);
    }
    @Override
    protected void convert(BaseViewHolder helper, LevelGradeModel item) {
        helper.setText(R.id.textView_gradeScoreName, item.getExamName()).
                setText(R.id.textView_gradeScore, item.getScore()).
                setText(R.id.textView_gradeScoreYear, item.getYear()).
                setText(R.id.textView_gradeScoreSemester, item.getSemester()).
                setText(R.id.textView_gradeScoreCardNumber, item.getCardNumber()).
                setText(R.id.textView_gradeExamDate, item.getExamDate()).
                setText(R.id.textView_gradeListenScore, item.getListenScore()).
                setText(R.id.textView_gradeReadScore, item.getReadScore()).
                setText(R.id.textView_gradeWriteScore, item.getWriteScore()).
                setText(R.id.textView_gradeSynthesizeScore, item.getSynthesizeScore());
    }
}
