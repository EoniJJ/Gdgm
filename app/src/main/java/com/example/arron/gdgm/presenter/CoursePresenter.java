package com.example.arron.gdgm.presenter;

import com.arron.networklibrary.entity.RequestEntity;
import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BasePresenterImpl;
import com.example.arron.gdgm.contract.CourseContract;
import com.example.arron.gdgm.model.CourseModel;
import com.example.arron.gdgm.model.FilterMenuModel;
import com.example.arron.gdgm.network.NetworkCallback;
import com.example.arron.gdgm.network.NetworkManager;
import com.example.arron.gdgm.parser.CourseFilterMenuParserRule;
import com.example.arron.gdgm.parser.CourseParserRule;
import com.example.arron.gdgm.parser.ViewStateParserRule;
import com.example.arron.gdgm.utils.ApiManager;
import com.example.arron.gdgm.utils.CommonUtils;
import com.example.arron.gdgm.utils.SharedPreferencesManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Arron on 2017/4/18.
 */

public class CoursePresenter extends BasePresenterImpl<CourseContract.View> implements CourseContract.Presenter, NetworkCallback<String> {


    private CourseParserRule courseParserRule;
    private CourseFilterMenuParserRule courseFilterMenuParserRule;
    public static final String COURSE_VIEW_STATE_KEY = "ViewState_course";

    @Override
    protected void init() {
        courseParserRule = new CourseParserRule();
        courseFilterMenuParserRule = new CourseFilterMenuParserRule();
    }

    @Override
    public void getCourses() {
        if (CommonUtils.isDemo) {
            String html = CommonUtils.readAssetsHtml(getView().getContext(), "course.html");
            FilterMenuModel filterMenuModel = courseFilterMenuParserRule.parseRule(html);
            getView().showFilterMenu(filterMenuModel);
            List<CourseModel> courseModelList = courseParserRule.parseRule(html);
            getView().showCourses(courseModelList);
            return;
        }


        String url = ApiManager.URL.get("班级课表查询");
        if (url == null || url.length() == 0) {
            getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
            return;
        }
        RequestEntity requestEntity = new RequestEntity();
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", ApiManager.EDUCATIONAL_HOST);
        requestEntity.setHeaders(headers);
        NetworkManager.get(url, requestEntity, new NetworkCallback<String>() {
            @Override
            public void onSuccess(String url, String html) {
                String viewState = new ViewStateParserRule().parseRule(html);
                SharedPreferencesManager.getInstance().put(COURSE_VIEW_STATE_KEY, viewState);
                FilterMenuModel filterMenuModel = courseFilterMenuParserRule.parseRule(html);
                if (isAttachView()) {
                    getView().showFilterMenu(filterMenuModel);
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
    public void getCourses(String year, String semester) {
        String url = ApiManager.URL.get("班级课表查询");
        if (url == null || url.length() == 0) {
            getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
            return;
        }
        String state = SharedPreferencesManager.getInstance().get(COURSE_VIEW_STATE_KEY, "");
        if (state.length() == 0) {
            getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
            return;
        }
        RequestEntity requestEntity = new RequestEntity();
        Map<String, String> requestForm = new HashMap<>();
        requestForm.put("__VIEWSTATE", state);
        requestForm.put("xn", year);
        requestForm.put("xq", semester);
        requestEntity.setFormRequestBody(requestForm);
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", ApiManager.EDUCATIONAL_HOST);
        requestEntity.setHeaders(headers);
        NetworkManager.post(url, requestEntity, this);
    }


    @Override
    public void onSuccess(String url, String s) {
        if (!isAttachView()) {
            return;
        }
        String viewState = new ViewStateParserRule().parseRule(s);
        SharedPreferencesManager.getInstance().put(COURSE_VIEW_STATE_KEY, viewState);

        List<CourseModel> courseModelList = courseParserRule.parseRule(s);
        getView().showCourses(courseModelList);
    }

    @Override
    public void onFailed(String url, String failureText) {
        getView().showError(failureText);
    }

    @Override
    public void onException(Throwable throwable) {
        getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
    }
}
