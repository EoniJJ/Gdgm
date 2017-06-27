package com.example.arron.gdgm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.model.NewsModel;

/**
 * Created by Arron on 2017/4/17.
 */
public class NewsAdapter extends BaseQuickAdapter<NewsModel, BaseViewHolder> {

    public NewsAdapter() {
        super(R.layout.item_new);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsModel item) {
        helper.setText(R.id.tv_new, item.getNewsName()).setText(R.id.tv_date, item.getDate());
    }
}
