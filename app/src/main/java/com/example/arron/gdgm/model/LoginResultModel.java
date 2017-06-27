package com.example.arron.gdgm.model;

/**
 * Created by Arron on 2017/4/13.
 */

public class LoginResultModel {
    private boolean loginSuccess;
    private String failedText;
    private String successText;

    public String getSuccessText() {
        return successText;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    public String getFailedText() {
        return failedText;
    }

    public void setFailedText(String failedText) {
        this.failedText = failedText;
    }

    @Override
    public String toString() {
        return "LoginResultModel{" +
                "loginSuccess=" + loginSuccess +
                ", failedText='" + failedText + '\'' +
                '}';
    }
}
