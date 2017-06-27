package com.example.arron.gdgm.contract;

import com.example.arron.gdgm.base.BasePresenter;
import com.example.arron.gdgm.base.BaseView;

/**
 * Created by J。 on 2017/4/22.
 */

public class ChangePasswordContract {
    public interface View extends BaseView {
        void changeResult(String message);

        void initComplete();
    }

    public interface Presenter extends BasePresenter<View> {
        void changePassword(String oldPassword, String newPassword, String newPasswordAgain);
    }
}
