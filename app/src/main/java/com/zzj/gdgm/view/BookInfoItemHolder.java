package com.zzj.gdgm.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzj.gdgm.R;

/**
 * Created by J。 on 2016/5/1.
 * 图书item
 */
public class BookInfoItemHolder extends RecyclerView.ViewHolder {
    /**
     * 图书分类号
     */
    private TextView textView_classification;
    /**
     * 图书名字
     */
    private TextView textView_book_name;
    /**
     * 图书作者
     */
    private TextView textView_author;

    public BookInfoItemHolder(View itemView) {
        super(itemView);
        textView_classification = (TextView) itemView.findViewById(R.id.textView_classification);
        textView_book_name = (TextView) itemView.findViewById(R.id.textView_book_name);
        textView_author = (TextView) itemView.findViewById(R.id.textView_author);

    }

    public TextView getTextView_classification() {
        return textView_classification;
    }

    public TextView getTextView_book_name() {
        return textView_book_name;
    }

    public TextView getTextView_author() {
        return textView_author;
    }
}
