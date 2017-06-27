package com.example.arron.gdgm.contract;

import com.example.arron.gdgm.base.BasePresenter;
import com.example.arron.gdgm.base.BaseView;
import com.example.arron.gdgm.model.NewsModel;

import java.util.List;

/**
 * Created by Arron on 2017/4/18.
 */

public class NewsContract {

    public interface View extends BaseView {
        void showNews(List<NewsModel> newsModelList);

        void addNew(NewsModel newsModel);

        void addNews(List<NewsModel> newsModelList);
    }

    public interface Presenter extends BasePresenter<View> {
        void getNews(int page);
    }
}
