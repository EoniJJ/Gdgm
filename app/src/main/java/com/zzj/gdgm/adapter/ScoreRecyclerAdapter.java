package com.zzj.gdgm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zzj.gdgm.R;
import com.zzj.gdgm.bean.CourseInfo;
import com.zzj.gdgm.view.ScoreItemHolder;

import java.util.ArrayList;

/**
 * Created by J。 on 2016/4/25.
 */
public class ScoreRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 及格线
     */
    private static final int PASS_SCORE = 60;
    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<CourseInfo> courseInfoArrayList;

    public ScoreRecyclerAdapter(Context context, ArrayList<CourseInfo> courseInfoArrayList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.courseInfoArrayList = courseInfoArrayList;
    }

    public void setCourseInfoArrayList(ArrayList<CourseInfo> courseInfoArrayList) {
        this.courseInfoArrayList = courseInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ScoreItemHolder scoreItemHolder = new ScoreItemHolder(layoutInflater.inflate(R.layout.score_item_layout, parent, false));
        return scoreItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ScoreItemHolder) holder).getTextView_scoreName().setText(courseInfoArrayList.get(position).getCourse_name());
        ((ScoreItemHolder) holder).getTextView_score().setText(courseInfoArrayList.get(position).getCourse_score());
        ((ScoreItemHolder) holder).getTextView_score().setTextColor(Integer.parseInt(((ScoreItemHolder) holder).getTextView_score().getText().toString()) < PASS_SCORE ? context.getResources().getColor(R.color.score_failed) : context.getResources().getColor(R.color.score_pass));
        ((ScoreItemHolder) holder).getTextView_scoreNumber().setText(courseInfoArrayList.get(position).getCourse_code());
        ((ScoreItemHolder) holder).getTextView_scoreAveragePoint().setText(courseInfoArrayList.get(position).getCourse_average_point());
        ((ScoreItemHolder) holder).getTextView_scoreCredit().setText(courseInfoArrayList.get(position).getCourse_credit());
        ((ScoreItemHolder) holder).getTextView_scoreNature().setText(courseInfoArrayList.get(position).getCourse_nature());
    }

    @Override
    public int getItemCount() {
        return courseInfoArrayList.size();
    }
}
