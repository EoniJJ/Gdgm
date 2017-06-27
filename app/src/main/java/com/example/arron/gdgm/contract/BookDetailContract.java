package com.example.arron.gdgm.contract;

import com.example.arron.gdgm.base.BasePresenter;
import com.example.arron.gdgm.base.BaseView;
import com.example.arron.gdgm.parser.BookDetailParserRule;

import java.util.List;

/**
 * Created by Arron on 2017/4/25.
 */

public class BookDetailContract {
    public interface View extends BaseView {
        void showBookDetails(List<BookDetailParserRule.Item> items);
    }

    public interface Presenter extends BasePresenter<View> {
        void getBookDetail(String url);
    }
}
