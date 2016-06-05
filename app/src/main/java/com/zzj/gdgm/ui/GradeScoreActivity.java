package com.zzj.gdgm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.zzj.gdgm.R;
import com.zzj.gdgm.adapter.GradeScoreRecyclerAdapter;
import com.zzj.gdgm.bean.GradeExam;
import com.zzj.gdgm.support.JsoupService;
import com.zzj.gdgm.support.OkHttpUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by J。 on 2016/6/5.
 */
public class GradeScoreActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView textView;
    private List<GradeExam> gradeExams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView.setText("等级考试查询");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadData();
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

    private void initView() {
        this.toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        this.textView = (TextView) findViewById(R.id.textView_score_header);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView_score);
    }

    private void loadData() {
        Request request = OkHttpUtil.getRequest(OkHttpUtil.getREFERER() + JsoupService.getLinkMap().get("等级考试查询"));
        OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    String content = new String(response.body().bytes(), "gb2312");
                    gradeExams = JsoupService.parseGradeExamScore(content);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GradeScoreRecyclerAdapter gradeScoreRecyclerAdapter = new GradeScoreRecyclerAdapter(GradeScoreActivity.this, gradeExams);
                            recyclerView.setAdapter(gradeScoreRecyclerAdapter);
                        }
                    });
                }
            }
        });
    }
}
