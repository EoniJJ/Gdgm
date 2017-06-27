package com.example.arron.gdgm.contract;

import com.example.arron.gdgm.base.BasePresenter;
import com.example.arron.gdgm.base.BaseView;
import com.example.arron.gdgm.model.BookModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Arron on 2017/4/20.
 */

public class LibraryContract {
    public interface View extends BaseView {
        void showSearchItems(Map<String,String> searchItems);

        void showBooksList(List<BookModel> bookModels);

        void addBooks(List<BookModel> bookModels);

    }

    public interface Presenter extends BasePresenter<View> {
        void getSearchItems();

        void searchBooks(String searchWord, String searchValue);

        void nextPage();
    }
}
