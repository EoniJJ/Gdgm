package com.example.arron.gdgm.presenter;

import com.arron.networklibrary.entity.RequestEntity;
import com.example.arron.gdgm.base.BasePresenterImpl;
import com.example.arron.gdgm.contract.StudyResultContract;
import com.example.arron.gdgm.model.FilterMenuModel;
import com.example.arron.gdgm.model.StudyResultModel;
import com.example.arron.gdgm.network.NetworkCallback;
import com.example.arron.gdgm.network.NetworkManager;
import com.example.arron.gdgm.parser.StudyResultFilterMenuParserRule;
import com.example.arron.gdgm.parser.StudyResultParserRule;
import com.example.arron.gdgm.parser.ViewStateParserRule;
import com.example.arron.gdgm.utils.ApiManager;
import com.example.arron.gdgm.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J。 on 2017/4/22.
 */

public class StudyResultPresenter extends BasePresenterImpl<StudyResultContract.View> implements StudyResultContract.Presenter,NetworkCallback<String> {
    private ViewStateParserRule viewStateParserRule;
    private StudyResultParserRule studyResultParserRule;
    private StudyResultFilterMenuParserRule studyResultFilterMenuParserRule;

    private String viewState;

    @Override
    protected void init() {
        viewStateParserRule = new ViewStateParserRule();
        studyResultParserRule = new StudyResultParserRule();
        studyResultFilterMenuParserRule = new StudyResultFilterMenuParserRule();
        getFilterMenu();
    }

    private void getFilterMenu() {
        if (CommonUtils.isDemo) {
            String html = CommonUtils.readAssetsHtml(getView().getContext(), "grade.html");
            FilterMenuModel filterMenuModel = studyResultFilterMenuParserRule.parseRule(html);
            getView().showFilterMenu(filterMenuModel);
            return;
        }
        String url = ApiManager.URL.get("学习成绩查询");
        RequestEntity requestEntity = new RequestEntity();
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", ApiManager.EDUCATIONAL_HOST);
        requestEntity.setHeaders(headers);
        NetworkManager.get(url, requestEntity, new NetworkCallback<String>() {
            @Override
            public void onSuccess(String url, String html) {
                viewState = viewStateParserRule.parseRule(html);
                if (!isAttachView()) {
                    return;
                }
                FilterMenuModel filterMenuModel = studyResultFilterMenuParserRule.parseRule(html);
                getView().showFilterMenu(filterMenuModel);
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
    public void getStudyResult(String year, String semester) {
        if (CommonUtils.isDemo) {
            String html = CommonUtils.readAssetsHtml(getView().getContext(), "grade.html");
            List<StudyResultModel> studyResultModels = studyResultParserRule.parseRule(html);
            getView().showStudyResult(studyResultModels);
            return;
        }

        String url = ApiManager.URL.get("学习成绩查询");
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("__VIEWSTATE", viewState);
        requestForm.put("ddlXN", year);
        requestForm.put("ddlXQ", semester);
        requestForm.put("Button1", "按学期查询");
        RequestEntity requestEntity = new RequestEntity();
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", ApiManager.EDUCATIONAL_HOST);
        requestEntity.setFormRequestBody(requestForm);
        requestEntity.setHeaders(headers);
        NetworkManager.post(url, requestEntity, this);
    }

    @Override
    public void onSuccess(String url, String s) {
        viewState = viewStateParserRule.parseRule(s);
        if (!isAttachView()) {
            return;
        }
        List<StudyResultModel> studyResultModels = studyResultParserRule.parseRule(s);
        getView().showStudyResult(studyResultModels);
    }

    @Override
    public void onFailed(String url, String failureText) {

    }

    @Override
    public void onException(Throwable throwable) {

    }
}
