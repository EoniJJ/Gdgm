package com.example.arron.gdgm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.model.BookModel;

/**
 * Created by Arron on 2017/4/21.
 */

public class BooksAdapter extends BaseQuickAdapter<BookModel,BaseViewHolder> {
    public BooksAdapter() {
        super(R.layout.item_book);
    }
    @Override
    protected void convert(BaseViewHolder helper, BookModel item) {
        helper.setText(R.id.tv_book_isbn, item.getClassification()).
                setText(R.id.tv_book_name, item.getBookName()).
                setText(R.id.tv_book_author, item.getAuthor());
    }
}
