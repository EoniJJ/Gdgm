package com.zzj.gdgm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzj.gdgm.R;
import com.zzj.gdgm.bean.GradeExam;
import com.zzj.gdgm.holder.GradeExamHolder;

import java.util.List;

/**
 * Created by J。 on 2016/6/5.
 */
public class GradeScoreRecyclerAdapter extends RecyclerView.Adapter<GradeExamHolder> {
    private List<GradeExam> gradeExams;
    private LayoutInflater layoutInflater;

    public GradeScoreRecyclerAdapter(Context context, List<GradeExam> gradeExams) {
        this.gradeExams = gradeExams;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GradeExamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GradeExamHolder gradeExamHolder = new GradeExamHolder(layoutInflater.inflate(R.layout.grade_score_item_layout, parent, false));
        return gradeExamHolder;
    }

    @Override
    public void onBindViewHolder(GradeExamHolder holder, int position) {
        GradeExam gradeExam = gradeExams.get(position);
        holder.getGradeScoreYear().setText(gradeExam.getYear());
        holder.getGradeScoreSemester().setText(gradeExam.getSemester());
        holder.getGradeScoreName().setText(gradeExam.getExamName());
        holder.getGradeScoreCardNumber().setText(gradeExam.getCardNumber());
        holder.getGradeExamDate().setText(gradeExam.getExamDate());
        holder.getGradeScore().setText(gradeExam.getScore());
        holder.getGradeListenScore().setText(gradeExam.getListenScore());
        holder.getGradeReadScore().setText(gradeExam.getReadScore());
        holder.getGradeWriteScore().setText(gradeExam.getWriteScore());
        holder.getGradeSynthesizeScore().setText(gradeExam.getSynthesizeScore());
    }

    @Override
    public int getItemCount() {
        return gradeExams.size();
    }
}
