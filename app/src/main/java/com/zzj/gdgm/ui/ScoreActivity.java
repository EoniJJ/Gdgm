package com.zzj.gdgm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.zzj.gdgm.R;
import com.zzj.gdgm.adapter.ScoreRecyclerAdapter;
import com.zzj.gdgm.bean.CourseInfo;

import java.util.ArrayList;

/**
 * Created by J。 on 2016/4/25.
 */
public class ScoreActivity extends AppCompatActivity {
    private Toolbar toolbar;

    /**
     * 显示学年学期
     */
    private TextView textView_score_header;
    private RecyclerView recyclerView_score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        //获取intent传递过来的List
        ArrayList<CourseInfo> courseInfoArrayList = (ArrayList<CourseInfo>) intent.getSerializableExtra("score");
        String year = intent.getStringExtra("year");
        String semester = intent.getStringExtra("semester");
        textView_score_header.setText(year + "第" + semester + "学期");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_score.setLayoutManager(linearLayoutManager);
        ScoreRecyclerAdapter scoreRecyclerAdapter = new ScoreRecyclerAdapter(this, courseInfoArrayList);
        recyclerView_score.setAdapter(scoreRecyclerAdapter);

    }

    private void initView() {
        this.toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        this.textView_score_header = (TextView) findViewById(R.id.textView_score_header);
        this.recyclerView_score = (RecyclerView) findViewById(R.id.recyclerView_score);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
