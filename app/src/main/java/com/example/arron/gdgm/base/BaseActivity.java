package com.example.arron.gdgm.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.utils.ToastUtil;

/**
 * Created by Arron on 2017/4/12.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ProgressDialog loadingDialog;
    protected ImageView back;
    protected TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new ProgressDialog(this);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        back = (ImageView) findViewById(R.id.iv_back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backClick(v);
                }
            });
        }
        title = (TextView) findViewById(R.id.tv_title);
        if (title != null) {
            title.setText(getTitleName());
        }
        initView();
        if (title != null) {
            title.post(new Runnable() {
                @Override
                public void run() {
                    initData();
                }
            });
        }
    }

    protected void initData() {

    }

    public void setTitleName(String title) {
        if (title != null) {
            this.title.setText(title);
        }
    }

    protected abstract String getTitleName();

    protected void backClick(View v) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String errorText) {
        ToastUtil.show(errorText);
    }

    @Override
    public void showLoadingDialog(String contentText) {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.setMessage(contentText);
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    protected abstract void initView();

}
