package com.zzj.gdgm.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzj.gdgm.R;
import com.zzj.gdgm.bean.GradeExam;

/**
 * Created by J。 on 2016/6/5.
 */
public class GradeExamHolder extends RecyclerView.ViewHolder {
    private TextView gradeScoreName;
    private TextView gradeScore;
    private TextView gradeScoreYear;
    private TextView gradeScoreSemester;
    private TextView gradeExamDate;
    private TextView gradeScoreCardNumber;
    private TextView gradeListenScore;
    private TextView gradeReadScore;
    private TextView gradeWriteScore;
    private TextView gradeSynthesizeScore;
    public GradeExamHolder(View itemView) {
        super(itemView);
        gradeScoreName = (TextView) itemView.findViewById(R.id.textView_gradeScoreName);
        gradeScore = (TextView) itemView.findViewById(R.id.textView_gradeScore);
        gradeScoreYear = (TextView) itemView.findViewById(R.id.textView_gradeScoreYear);
        gradeScoreSemester = (TextView) itemView.findViewById(R.id.textView_gradeScoreSemester);
        gradeExamDate = (TextView) itemView.findViewById(R.id.textView_gradeExamDate);
        gradeScoreCardNumber = (TextView) itemView.findViewById(R.id.textView_gradeScoreCardNumber);
        gradeListenScore = (TextView) itemView.findViewById(R.id.textView_gradeListenScore);
        gradeReadScore = (TextView) itemView.findViewById(R.id.textView_gradeReadScore);
        gradeWriteScore = (TextView) itemView.findViewById(R.id.textView_gradeWriteScore);
        gradeSynthesizeScore = (TextView) itemView.findViewById(R.id.textView_gradeSynthesizeScore);
    }

    public TextView getGradeScoreName() {
        return gradeScoreName;
    }

    public TextView getGradeScore() {
        return gradeScore;
    }

    public TextView getGradeScoreYear() {
        return gradeScoreYear;
    }

    public TextView getGradeScoreSemester() {
        return gradeScoreSemester;
    }

    public TextView getGradeExamDate() {
        return gradeExamDate;
    }

    public TextView getGradeScoreCardNumber() {
        return gradeScoreCardNumber;
    }

    public TextView getGradeListenScore() {
        return gradeListenScore;
    }

    public TextView getGradeReadScore() {
        return gradeReadScore;
    }

    public TextView getGradeWriteScore() {
        return gradeWriteScore;
    }

    public TextView getGradeSynthesizeScore() {
        return gradeSynthesizeScore;
    }
}
