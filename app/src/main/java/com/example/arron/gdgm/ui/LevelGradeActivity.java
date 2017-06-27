package com.example.arron.gdgm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.adapter.LevelGradeAdapter;
import com.example.arron.gdgm.base.BaseActivity;
import com.example.arron.gdgm.contract.LevelGradeContract;
import com.example.arron.gdgm.model.LevelGradeModel;
import com.example.arron.gdgm.presenter.LevelGradePresenter;

import java.util.List;

/**
 * Created by J。 on 2017/4/23.
 */

public class LevelGradeActivity extends BaseActivity implements LevelGradeContract.View{
    private RecyclerView rvLevelGrade;
    private LevelGradeContract.Presenter presenter;
    private LevelGradeAdapter levelGradeAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LevelGradePresenter();
        presenter.attachView(this);
        levelGradeAdapter = new LevelGradeAdapter();
        setContentView(R.layout.activity_level_grade);
    }

    @Override
    protected void initData() {
        super.initData();
        showLoadingDialog(getString(R.string.loading));
        presenter.getLevelGradeResult();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void backClick(View v) {
        super.backClick(v);
        finish();
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.level_grade);
    }

    @Override
    protected void initView() {
        rvLevelGrade = (RecyclerView) findViewById(R.id.rv_level_grade);
        rvLevelGrade.setAdapter(levelGradeAdapter);
        rvLevelGrade.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void showLevelGrade(List<LevelGradeModel> levelGradeModelList) {
        hideLoadingDialog();
        levelGradeAdapter.setNewData(levelGradeModelList);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LevelGradeActivity.class);
        context.startActivity(intent);
    }
}
