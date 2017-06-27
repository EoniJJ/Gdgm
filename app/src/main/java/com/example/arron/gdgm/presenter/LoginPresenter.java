package com.example.arron.gdgm.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BasePresenterImpl;
import com.example.arron.gdgm.contract.LoginContract;
import com.example.arron.gdgm.model.LoginResultModel;
import com.example.arron.gdgm.network.NetworkCallback;
import com.example.arron.gdgm.network.NetworkManager;
import com.example.arron.gdgm.network.parser.BitmapResponseParser;
import com.example.arron.gdgm.parser.LoginParserRule;
import com.example.arron.gdgm.parser.UrlParserRule;
import com.example.arron.gdgm.parser.ViewStateParserRule;
import com.example.arron.gdgm.utils.ApiManager;
import com.example.arron.gdgm.utils.CommonUtils;
import com.example.arron.gdgm.utils.SharedPreferencesManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arron on 2017/4/12.
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter, NetworkCallback<String> {

    public static final String LOGIN_VIEW_STATE_KEY = "ViewState_login";

    @Override
    public void login(String username, String password, String verificationCode) {
        if (CommonUtils.isDemo) {
            getView().loginSuccess("登陆成功");
            return;
        }
        String viewState = SharedPreferencesManager.getInstance().get(LOGIN_VIEW_STATE_KEY, "");
        if (TextUtils.isEmpty(viewState)) {
            getView().loginFailed(getView().getContext().getString(R.string.login_error));
            return;
        }
        login(viewState, username, password, verificationCode);
    }

    @Override
    public void getVerificationCode() {
        if (CommonUtils.isDemo) {
            try {
                getView().showVerificationCode(BitmapFactory.decodeStream(getView().getContext().getAssets().open("VerificationCode.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }


        NetworkManager.get(ApiManager.LOGIN_VERIFICATION_CODE, new BitmapResponseParser(), new NetworkCallback<Bitmap>() {

            @Override
            public void onSuccess(String url, Bitmap bitmap) {
                if (isAttachView()) {
                    getView().showVerificationCode(bitmap);
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
    public void onSuccess(String url, String html) {
        if (!isAttachView()) {
            return;
        }
        if (ApiManager.LOGIN.equals(url)) {
            LoginResultModel loginResultModel = new LoginParserRule().parseRule(html);
            if (loginResultModel.isLoginSuccess()) {
                getView().loginSuccess(loginResultModel.getSuccessText());
                Map<String, String> urlMap = new UrlParserRule().parseRule(html);
                for (String key : urlMap.keySet()) {
                    ApiManager.URL.put(key, urlMap.get(key));
                }
            } else {
                getView().loginFailed(loginResultModel.getFailedText());
            }
        } else if (ApiManager.EDUCATIONAL_HOST.equals(url)) {
            String viewState = new ViewStateParserRule().parseRule(html);
            SharedPreferencesManager.getInstance().put(LOGIN_VIEW_STATE_KEY, viewState);
            getVerificationCode();
        }
    }

    @Override
    public void onFailed(String url, String failureText) {
        if (!isAttachView()) {
            return;
        }
        getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
    }

    @Override
    public void onException(Throwable throwable) {
        getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
    }

    @Override
    protected void init() {
        if (CommonUtils.isDemo) {
            getVerificationCode();
            return;
        }
        NetworkManager.get(ApiManager.EDUCATIONAL_HOST, this);
    }


    private void login(String viewState, String username, String password, String verificationCode) {
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("__VIEWSTATE", viewState);
        requestForm.put("TextBox1", username);
        requestForm.put("TextBox2", password);
        requestForm.put("TextBox3", verificationCode);
        requestForm.put("RadioButtonList1", "学生");
        requestForm.put("Button1", "");
        NetworkManager.post(ApiManager.LOGIN, requestForm, this);
    }

}
