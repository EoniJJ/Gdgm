package com.zzj.gdgm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzj.gdgm.R;
import com.zzj.gdgm.bean.BookInfo;
import com.zzj.gdgm.ui.BookDetailActivity;
import com.zzj.gdgm.view.BookInfoItemHolder;

import java.util.List;

/**
 * Created by J。 on 2016/5/1.
 */
public class LibraryRecyclerAdapter extends RecyclerView.Adapter<BookInfoItemHolder> implements View.OnClickListener {

    private List<BookInfo> bookInfoList;
    private LayoutInflater layoutInflater;
    private Context context;

    public LibraryRecyclerAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setBookInfoList(List<BookInfo> bookInfoList) {
        this.bookInfoList = bookInfoList;
    }

    @Override
    public BookInfoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BookInfoItemHolder bookInfoItemHolder = new BookInfoItemHolder(layoutInflater.inflate(R.layout.book_info_item, parent, false));
        bookInfoItemHolder.itemView.setOnClickListener(this);
        return bookInfoItemHolder;
    }

    @Override
    public void onBindViewHolder(BookInfoItemHolder holder, int position) {
        holder.getTextView_classification().setText(bookInfoList.get(position).getClassification());
        holder.getTextView_book_name().setText(bookInfoList.get(position).getBook_name());
        holder.getTextView_author().setText(bookInfoList.get(position).getAuthor());
        holder.itemView.setTag(R.id.book_detail_url, bookInfoList.get(position).getUrl_detail());
        holder.itemView.setTag(R.id.book_name, bookInfoList.get(position).getBook_name());
    }

    @Override
    public int getItemCount() {
        if (bookInfoList != null) {
            return bookInfoList.size();
        } else {
            return 0;
        }
    }

    public List<BookInfo> getBookInfoList() {
        return bookInfoList;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(context, BookDetailActivity.class);
        intent.putExtra("url", (String) v.getTag(R.id.book_detail_url));
        intent.putExtra("book_name", (String) v.getTag(R.id.book_name));
        context.startActivity(intent);
    }
}
