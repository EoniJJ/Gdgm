package com.example.arron.gdgm.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BaseActivity;
import com.example.arron.gdgm.contract.LoginContract;
import com.example.arron.gdgm.presenter.LoginPresenter;
import com.example.arron.gdgm.utils.ImageManager;
import com.example.arron.gdgm.utils.ToastUtil;

/**
 * Created by Arron on 2017/4/12.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {
    private LoginContract.Presenter presenter;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etVerificationCode;
    private ImageView ivVerificationCode;
    private Button btnLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.login);
    }

    @Override
    protected void backClick(View v) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etVerificationCode = (EditText) findViewById(R.id.et_verificationCode);
        ivVerificationCode = (ImageView) findViewById(R.id.iv_verificationCode);
        ivVerificationCode.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void loginSuccess(String successText) {
        hideLoadingDialog();
        MainActivity.start(this);
        finish();
    }

    @Override
    public void loginFailed(String failedText) {
        hideLoadingDialog();
        ToastUtil.show(failedText);
        presenter.getVerificationCode();
    }


    @Override
    public void showVerificationCode(Bitmap bitmap) {
        ImageManager.getInstance().loadImage(this, bitmap, ivVerificationCode);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_login) {
            showLoadingDialog(getString(R.string.logining));
            presenter.login(etUsername.getText().toString(), etPassword.getText().toString(),
                    etVerificationCode.getText().toString());
        } else if (viewId == R.id.iv_verificationCode) {
            presenter.getVerificationCode();
        }
    }
}
