package com.example.arron.gdgm.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.utils.ToastUtil;

/**
 * Created by Arron on 2017/4/17.
 */

public abstract class BaseFragment extends Fragment implements BaseView{
    private ProgressDialog loadingDialog;
    protected TextView title;
    protected ImageView back;
    private TextView more;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new ProgressDialog(getContext());
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(resourceViewId(), container, false);
        back = (ImageView) view.findViewById(R.id.iv_back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backClick(v);
                }
            });
        }
        title = (TextView) view.findViewById(R.id.tv_title);
        if (title != null) {
            title.setText(getTitleName());
        }
        more = (TextView) view.findViewById(R.id.tv_more);
        if (more != null) {
            setMoreButton(more);
        }
        initView(view);
        view.post(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
        return view;
    }

    protected void backClick(View v) {

    }

    protected abstract String getTitleName();

    protected void setMoreButton(TextView tvMore) {

    }

    protected abstract @LayoutRes int resourceViewId();

    protected abstract void initView(View view);

    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
