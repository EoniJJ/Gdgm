package com.example.arron.gdgm.presenter;

import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BasePresenterImpl;
import com.example.arron.gdgm.contract.NewsContract;
import com.example.arron.gdgm.model.NewsModel;
import com.example.arron.gdgm.network.NetworkCallback;
import com.example.arron.gdgm.network.NetworkManager;
import com.example.arron.gdgm.parser.NewsParserRule;
import com.example.arron.gdgm.utils.ApiManager;
import com.example.arron.gdgm.utils.CommonUtils;

import java.util.List;

/**
 * Created by Arron on 2017/4/18.
 */

public class NewsPresenter extends BasePresenterImpl<NewsContract.View> implements NewsContract.Presenter, NetworkCallback<String> {

    private NewsParserRule newsParserRule;

    @Override
    public void getNews(int page) {
        if (CommonUtils.isDemo) {
            if (page > 1) {
                return;
            }
            String html = CommonUtils.readAssetsHtml(getView().getContext(), "news.html");
            List<NewsModel> newsModelList = newsParserRule.parseRule(html);
            getView().showNews(newsModelList);
            return;
        }
        String url = String.format(ApiManager.NEWS, page);
        NetworkManager.get(url, this);
    }


    @Override
    protected void init() {
        newsParserRule = new NewsParserRule();
    }

    @Override
    public void onSuccess(String url, String s) {
        if (!isAttachView()) {
            return;
        }
        List<NewsModel> newsModelList = newsParserRule.parseRule(s);
        getView().showNews(newsModelList);
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
