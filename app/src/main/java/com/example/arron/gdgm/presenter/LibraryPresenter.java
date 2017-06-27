package com.example.arron.gdgm.presenter;

import com.example.arron.gdgm.GdgmApplication;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BasePresenterImpl;
import com.example.arron.gdgm.contract.LibraryContract;
import com.example.arron.gdgm.model.BookModel;
import com.example.arron.gdgm.network.NetworkCallback;
import com.example.arron.gdgm.network.NetworkManager;
import com.example.arron.gdgm.parser.BookListNextPageParserRule;
import com.example.arron.gdgm.parser.BookParserRule;
import com.example.arron.gdgm.parser.LibrarySearchItemRule;
import com.example.arron.gdgm.utils.ApiManager;
import com.example.arron.gdgm.utils.CommonUtils;
import com.example.arron.gdgm.utils.EncoderUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Arron on 2017/4/20.
 */

public class LibraryPresenter extends BasePresenterImpl<LibraryContract.View> implements LibraryContract.Presenter, NetworkCallback<String> {

    private LibrarySearchItemRule librarySearchItemRule;

    private BookParserRule bookParserRule;

    private BookListNextPageParserRule bookListNextPageParserRule;

    private String nextPageUrl;

    @Override
    protected void init() {
        librarySearchItemRule = new LibrarySearchItemRule();
        bookParserRule = new BookParserRule();
        bookListNextPageParserRule = new BookListNextPageParserRule();
    }

    @Override
    public void getSearchItems() {
        if (CommonUtils.isDemo) {
            String html = CommonUtils.readAssetsHtml(getView().getContext(), "library.html");
            Map<String, String> result = librarySearchItemRule.parseRule(html);
            getView().showSearchItems(result);
            return;
        }
        NetworkManager.get(ApiManager.LIBRARY_HOME, new NetworkCallback<String>() {

            @Override
            public void onSuccess(String url, String s) {
                if (!isAttachView()) {
                    return;
                }
                if (ApiManager.LIBRARY_HOME.equals(url)) {
                    Map<String, String> result = librarySearchItemRule.parseRule(s);
                    getView().showSearchItems(result);
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
    public void searchBooks(String searchWord, String searchValue) {
        if (CommonUtils.isDemo) {
            String html = CommonUtils.readAssetsHtml(getView().getContext(), "library.html");
            List<BookModel> bookModels = bookParserRule.parseRule(html);
            getView().showBooksList(bookModels);
            return;
        }

        String url = ApiManager.LIBRARY_HOME + "?keyword=" + searchWord + "&keyvalue=" + searchValue +
                "&submit=" + GdgmApplication.getContext().getString(R.string.search);
        NetworkManager.get(EncoderUtil.encodeUrl(url), this);
    }

    @Override
    public void nextPage() {
        if (CommonUtils.isDemo) {
            return;
        }
        if (nextPageUrl == null || nextPageUrl.length() == 0) {
            getView().addBooks(null);
            return;
        }
        nextPageUrl = EncoderUtil.encodeUrl(nextPageUrl);
        NetworkManager.get(nextPageUrl, new NetworkCallback<String>() {

            @Override
            public void onSuccess(String url, String s) {
                if (!isAttachView()) {
                    return;
                }
                List<BookModel> bookModels = bookParserRule.parseRule(s);
                getView().addBooks(bookModels);
                nextPageUrl = bookListNextPageParserRule.parseRule(s);
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
    public void onSuccess(String url, String s) {
        if (!isAttachView()) {
            return;
        }
        List<BookModel> bookModels = bookParserRule.parseRule(s);
        getView().showBooksList(bookModels);
        nextPageUrl = bookListNextPageParserRule.parseRule(s);
    }

    @Override
    public void onFailed(String url, String failureText) {
        if (!isAttachView()) {
            return;
        }
        getView().showError(failureText);
    }

    @Override
    public void onException(Throwable throwable) {
        getView().showError(GdgmApplication.getContext().getString(R.string.network_error));
    }
}
