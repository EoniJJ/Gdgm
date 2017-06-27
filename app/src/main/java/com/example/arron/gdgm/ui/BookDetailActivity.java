package com.example.arron.gdgm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BaseActivity;
import com.example.arron.gdgm.contract.BookDetailContract;
import com.example.arron.gdgm.parser.BookDetailParserRule;
import com.example.arron.gdgm.presenter.BookDetailPresenter;
import com.example.arron.gdgm.utils.ConvertUtils;

import java.util.List;

/**
 * Created by Arron on 2017/4/25.
 */

public class BookDetailActivity extends BaseActivity implements BookDetailContract.View {
    private LinearLayout llContent;
    private BookDetailContract.Presenter presenter;
    private String bookDetailUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        presenter = new BookDetailPresenter();
        presenter.attachView(this);
        bookDetailUrl = initIntentData();
    }

    @Override
    protected void initData() {
        super.initData();
        showLoadingDialog(getString(R.string.loading));
        presenter.getBookDetail(bookDetailUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.book_detail);
    }

    @Override
    protected void backClick(View v) {
        super.backClick(v);
        finish();
    }

    @Override
    protected void initView() {
        llContent = (LinearLayout) findViewById(R.id.ll_content);
    }

    @Override
    public void showBookDetails(List<BookDetailParserRule.Item> items) {
        hideLoadingDialog();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, ConvertUtils.dp2px(5), 0, ConvertUtils.dp2px(5));
        for (BookDetailParserRule.Item item : items) {
            TextView textView = new TextView(getContext());
            textView.setText(item.key + "\t" + item.value);
            textView.setTextSize(16);
            textView.setTextColor(getResources().getColor(R.color.newsTextColor));
            textView.setPadding(0, ConvertUtils.dp2px(5), 0, ConvertUtils.dp2px(5));
            llContent.addView(textView, layoutParams);
        }
    }


    private String initIntentData() {
        String url = getIntent().getStringExtra("url");
        return url;
    }

    public static void start(Context context, String bookDetailUrl) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra("url", bookDetailUrl);
        context.startActivity(intent);

    }

}
