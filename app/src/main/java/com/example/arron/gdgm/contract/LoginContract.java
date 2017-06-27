package com.example.arron.gdgm.contract;

import android.graphics.Bitmap;

import com.example.arron.gdgm.base.BasePresenter;
import com.example.arron.gdgm.base.BaseView;

/**
 * Created by Arron on 2017/4/12.
 */

public class LoginContract {
    public interface View extends BaseView{
        void loginSuccess(String successText);

        void loginFailed(String failedText);

        void showVerificationCode(Bitmap bitmap);
    }

    public interface Presenter extends BasePresenter<View> {
        void login(String username, String password, String verificationCode);

        void getVerificationCode();
    }

}
