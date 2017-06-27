package com.example.arron.gdgm.presenter;

import com.arron.networklibrary.entity.RequestEntity;
import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BasePresenterImpl;
import com.example.arron.gdgm.contract.ChangePasswordContract;
import com.example.arron.gdgm.network.NetworkCallback;
import com.example.arron.gdgm.network.NetworkManager;
import com.example.arron.gdgm.parser.JsAlertParserRule;
import com.example.arron.gdgm.parser.ViewStateParserRule;
import com.example.arron.gdgm.utils.ApiManager;
import com.example.arron.gdgm.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by J。 on 2017/4/22.
 */

public class ChangePasswordPresenter extends BasePresenterImpl<ChangePasswordContract.View>
        implements ChangePasswordContract.Presenter ,NetworkCallback<String>{

    private String viewState;
    private ViewStateParserRule viewStateParserRule;
    private JsAlertParserRule jsAlertParserRule;
    @Override
    protected void init() {
        viewStateParserRule = new ViewStateParserRule();
        jsAlertParserRule = new JsAlertParserRule();
        String url = ApiManager.URL.get("密码修改");
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", ApiManager.EDUCATIONAL_HOST);
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setHeaders(headers);
        if (url == null || url.length() == 0) {
            getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
            return;
        }
        NetworkManager.get(url, requestEntity, new NetworkCallback<String>() {

            @Override
            public void onSuccess(String url, String s) {
                viewState = viewStateParserRule.parseRule(s);
                if (isAttachView()) {
                    getView().initComplete();
                }
            }

            @Override
            public void onFailed(String url, String failureText) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }
    @Override
    public void changePassword(String oldPassword, String newPassword, String newPasswordAgain) {
        if (CommonUtils.isDemo) {
            getView().showError("Demo不支持修改密码");
            return;
        }

        String url = ApiManager.URL.get("密码修改");
        if (url == null || url.length() == 0 || viewState == null || viewState.length() == 0) {
            getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
            return;
        }
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("__VIEWSTATE", viewState);
        requestForm.put("TextBox2", oldPassword);
        requestForm.put("TextBox3", newPassword);
        requestForm.put("TextBox4", newPasswordAgain);
        requestForm.put("Button1", "修改");
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", ApiManager.EDUCATIONAL_HOST);
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setFormRequestBody(requestForm);
        requestEntity.setHeaders(headers);
        NetworkManager.post(url,requestEntity,this);
    }

    @Override
    public void onSuccess(String url, String s) {
        if (!isAttachView()) {
            return;
        }
        String result = jsAlertParserRule.parseRule(s);
        getView().changeResult(result);
    }

    @Override
    public void onFailed(String url, String failureText) {

    }

    @Override
    public void onException(Throwable throwable) {

    }
}
