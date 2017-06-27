package com.example.arron.gdgm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.adapter.NewsAdapter;
import com.example.arron.gdgm.base.BaseFragment;
import com.example.arron.gdgm.contract.NewsContract;
import com.example.arron.gdgm.model.NewsModel;
import com.example.arron.gdgm.presenter.NewsPresenter;

import java.util.List;

/**
 * Created by Arron on 2017/4/17.
 */

public class NewsFragment extends BaseFragment implements NewsContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rlNews;
    private NewsAdapter newsAdapter;
    private NewsContract.Presenter newsPresenter;
    private SwipeRefreshLayout newsSwipeRefreshLayout;

    private int page = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsAdapter = new NewsAdapter();
        newsAdapter.setOnItemClickListener(this);
        newsAdapter.setEnableLoadMore(true);
        newsAdapter.setOnLoadMoreListener(this);
        newsPresenter = new NewsPresenter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        newsPresenter.attachView(this);
        return view;
    }


    @Override
    protected String getTitleName() {
        return getString(R.string.news);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view) {
        back.setVisibility(View.GONE);
        rlNews = (RecyclerView) view.findViewById(R.id.rl_news);
        rlNews.setLayoutManager(new LinearLayoutManager(getContext()));
        rlNews.setAdapter(newsAdapter);
        newsSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.news_swipeRefreshLayout);
        newsSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        newsSwipeRefreshLayout.setRefreshing(true);
        newsPresenter.getNews(page);
    }

    @Override
    public void showNews(List<NewsModel> newsModelList) {
        if (newsAdapter.isLoading()) {
            newsAdapter.loadMoreComplete();
        }
        if (newsSwipeRefreshLayout.isRefreshing()) {
            newsSwipeRefreshLayout.setRefreshing(false);
        }
        if (newsAdapter == null) {
            return;
        }
        if (page > 1) {
            addNews(newsModelList);
        } else {
            newsAdapter.setNewData(newsModelList);
        }
    }

    @Override
    public void addNew(NewsModel newsModel) {
        if (newsAdapter.getData() == null) {
            return;
        }
        newsAdapter.addData(newsModel);
    }

    @Override
    public void addNews(List<NewsModel> newsModelList) {
        if (newsAdapter.getData() == null) {
            return;
        }
        newsAdapter.addData(newsModelList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsPresenter.detachView();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NewsModel newsModel = newsAdapter.getData().get(position);
        String url = newsModel.getDetailUrl();
        String title = newsModel.getNewsName();
        WebActivity.start(getContext(),title, url);
    }

    @Override
    public void onLoadMoreRequested() {
        if (newsSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        newsPresenter.getNews(++page);
    }

    @Override
    public void onRefresh() {
        if (newsAdapter.isLoading()) {
            return;
        }
        page = 1;
        newsPresenter.getNews(page);
    }

    @Override
    public void showError(String errorText) {
        super.showError(errorText);
        if (newsAdapter.isLoading()) {
            newsAdapter.loadMoreFail();
        }
        if (newsSwipeRefreshLayout.isRefreshing()) {
            newsSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
