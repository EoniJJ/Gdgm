package com.example.arron.gdgm.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arron.gdgm.BuildConfig;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BaseFragment;
import com.example.arron.gdgm.utils.SharedPreferencesManager;

/**
 * Created by Arron on 2017/4/17.
 */

public class MoreFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvChangePassword;
    private TextView tvGradeTest;
    private TextView tvAbout;
    private TextView tvExit;
    private AlertDialog alertDialog;

    @Override
    protected String getTitleName() {
        return getString(R.string.more);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initView(View view) {
        back.setVisibility(View.GONE);
        tvChangePassword = (TextView) view.findViewById(R.id.tv_change_password);
        tvChangePassword.setOnClickListener(this);
        tvGradeTest = (TextView) view.findViewById(R.id.tv_grade_test);
        tvGradeTest.setOnClickListener(this);
        tvAbout = (TextView) view.findViewById(R.id.tv_about);
        tvAbout.setOnClickListener(this);
        tvExit = (TextView) view.findViewById(R.id.tv_exit);
        tvExit.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_exit) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SharedPreferencesManager.getInstance().clearAll();
            startActivity(intent);
        } else if (viewId == R.id.tv_change_password) {
            ChangePasswordActivity.start(getContext());
        } else if (viewId == R.id.tv_grade_test) {
            LevelGradeActivity.start(getContext());
        } else if (viewId == R.id.tv_about) {
            if (alertDialog == null) {
                alertDialog = new AlertDialog.Builder(getContext()).
                        setMessage(String.format(getString(R.string.aboutGdgm), BuildConfig.VERSION_NAME)).
                        setPositiveButton(R.string.ok, null).
                        create();
            }
            alertDialog.show();
        }
    }
}
