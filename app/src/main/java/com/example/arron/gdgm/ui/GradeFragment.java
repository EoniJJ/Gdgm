package com.example.arron.gdgm.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.adapter.StudyResultAdapter;
import com.example.arron.gdgm.base.BaseFragment;
import com.example.arron.gdgm.contract.StudyResultContract;
import com.example.arron.gdgm.model.FilterMenuModel;
import com.example.arron.gdgm.model.StudyResultModel;
import com.example.arron.gdgm.presenter.StudyResultPresenter;

import java.util.List;

/**
 * Created by Arron on 2017/4/17.
 */

public class GradeFragment extends BaseFragment implements StudyResultContract.View, View.OnClickListener {
    private RecyclerView rvGrade;
    private Spinner spYear;
    private Spinner spSemester;
    private TextView btnSearch;
    private StudyResultContract.Presenter presenter;
    private StudyResultAdapter studyResultAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StudyResultPresenter();
        studyResultAdapter = new StudyResultAdapter();
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.grade);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.fragment_grade;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.attachView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    protected void initView(View view) {
        back.setVisibility(View.GONE);
        rvGrade = (RecyclerView) view.findViewById(R.id.rv_grade);
        rvGrade.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGrade.setAdapter(studyResultAdapter);
        spYear = (Spinner) view.findViewById(R.id.sp_year);
        spSemester = (Spinner) view.findViewById(R.id.sp_semester);
        btnSearch = (TextView) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        showLoadingDialog(getString(R.string.loading));
    }

    @Override
    public void showFilterMenu(FilterMenuModel filterMenuModel) {
        hideLoadingDialog();
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, filterMenuModel.getYear());
        spYear.setAdapter(yearAdapter);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, filterMenuModel.getSemester());
        spSemester.setAdapter(semesterAdapter);
    }

    @Override
    public void showStudyResult(List<StudyResultModel> studyResultModelList) {
        hideLoadingDialog();
        studyResultAdapter.setNewData(studyResultModelList);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_search) {
            showLoadingDialog(getString(R.string.loading));
            presenter.getStudyResult((String) spYear.getSelectedItem(), (String) spSemester.getSelectedItem());
        }

    }
}
