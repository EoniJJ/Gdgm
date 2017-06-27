package com.example.arron.gdgm.presenter;

import com.arron.networklibrary.entity.RequestEntity;
import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BasePresenterImpl;
import com.example.arron.gdgm.contract.LevelGradeContract;
import com.example.arron.gdgm.model.LevelGradeModel;
import com.example.arron.gdgm.network.NetworkCallback;
import com.example.arron.gdgm.network.NetworkManager;
import com.example.arron.gdgm.parser.LevelGradeParserRule;
import com.example.arron.gdgm.utils.ApiManager;
import com.example.arron.gdgm.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J。 on 2017/4/23.
 */

public class LevelGradePresenter extends BasePresenterImpl<LevelGradeContract.View> implements LevelGradeContract.Presenter ,NetworkCallback<String>{

    private LevelGradeParserRule levelGradeParserRule;

    @Override
    protected void init() {
        levelGradeParserRule = new LevelGradeParserRule();
    }

    @Override
    public void getLevelGradeResult() {
        if (CommonUtils.isDemo) {
            String s = CommonUtils.readAssetsHtml(getView().getContext(), "level_grade.html");
            List<LevelGradeModel> levelGradeModelList = levelGradeParserRule.parseRule(s);
            getView().showLevelGrade(levelGradeModelList);
            return;
        }
        String url = ApiManager.URL.get("等级考试查询");
        if (url == null || url.length() == 0) {
            getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
            return;
        }
        RequestEntity requestEntity = new RequestEntity();
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", ApiManager.EDUCATIONAL_HOST);
        requestEntity.setHeaders(headers);
        NetworkManager.get(url,requestEntity,this);
    }

    @Override
    public void onSuccess(String url, String s) {
        if (!isAttachView()) {
            return;
        }
        List<LevelGradeModel> levelGradeModelList = levelGradeParserRule.parseRule(s);
        getView().showLevelGrade(levelGradeModelList);
    }

    @Override
    public void onFailed(String url, String failureText) {

    }

    @Override
    public void onException(Throwable throwable) {

    }
}
