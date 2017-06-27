package com.example.arron.gdgm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BaseActivity;
import com.example.arron.gdgm.contract.ChangePasswordContract;
import com.example.arron.gdgm.presenter.ChangePasswordPresenter;
import com.example.arron.gdgm.utils.ToastUtil;

/**
 * Created by J。 on 2017/4/22.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, ChangePasswordContract.View {
    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etNewPasswordAgain;
    private TextView tvChange;
    private ChangePasswordContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        presenter = new ChangePasswordPresenter();
        presenter.attachView(this);
        showLoadingDialog(getString(R.string.loading));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.changePassword);
    }

    @Override
    protected void initView() {
        etOldPassword = (EditText) findViewById(R.id.et_old_password);
        etNewPassword = (EditText) findViewById(R.id.et_new_password);
        etNewPasswordAgain = (EditText) findViewById(R.id.et_new_password_again);
        tvChange = (TextView) findViewById(R.id.tv_change);
        tvChange.setOnClickListener(this);
    }

    @Override
    protected void backClick(View v) {
        finish();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_change) {
            String oldPassword = etOldPassword.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();
            String newPasswordAgain = etNewPasswordAgain.getText().toString().trim();
            showLoadingDialog(getString(R.string.isChanging));
            if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(newPasswordAgain)) {
                ToastUtil.show(getString(R.string.passwordNotNull));
                return;
            }

            presenter.changePassword(oldPassword, newPassword, newPasswordAgain);
        }
    }

    @Override
    public void changeResult(String message) {
        hideLoadingDialog();
        ToastUtil.show(message);
    }

    @Override
    public void initComplete() {
        hideLoadingDialog();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }
}
