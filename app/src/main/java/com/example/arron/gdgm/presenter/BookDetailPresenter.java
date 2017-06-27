package com.example.arron.gdgm.presenter;

import com.example.arron.gdgm.base.BasePresenterImpl;
import com.example.arron.gdgm.contract.BookDetailContract;
import com.example.arron.gdgm.network.NetworkCallback;
import com.example.arron.gdgm.network.NetworkManager;
import com.example.arron.gdgm.parser.BookDetailParserRule;
import com.example.arron.gdgm.utils.CommonUtils;

import java.util.List;

/**
 * Created by Arron on 2017/4/25.
 */

public class BookDetailPresenter extends BasePresenterImpl<BookDetailContract.View>
        implements BookDetailContract.Presenter ,NetworkCallback<String> {

    private BookDetailParserRule bookDetailParserRule;

    @Override
    protected void init() {
        bookDetailParserRule = new BookDetailParserRule();
    }

    @Override
    public void getBookDetail(String url) {
        if (CommonUtils.isDemo) {
            String html = CommonUtils.readAssetsHtml(getView().getContext(), "book_detail.html");
            List<BookDetailParserRule.Item> lists = bookDetailParserRule.parseRule(html);
            getView().showBookDetails(lists);
            return;
        }
        NetworkManager.get(url, this);
    }

    @Override
    public void onSuccess(String url, String s) {
        if (!isAttachView()) {
            return;
        }
        List<BookDetailParserRule.Item> lists = bookDetailParserRule.parseRule(s);
        getView().showBookDetails(lists);
    }

    @Override
    public void onFailed(String url, String failureText) {

    }

    @Override
    public void onException(Throwable throwable) {

    }
}
