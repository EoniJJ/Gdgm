package com.zzj.gdgm.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzj.gdgm.R;

/**
 * Created by J。 on 2016/4/19.
 * 主界面RecyclerView 的Item
 */
public class SimpleItemHolder extends RecyclerView.ViewHolder {
    /**
     * item标题
     */
    private TextView textView_title;
    /**
     * item背景图片
     */
    private ImageView imageView_titleImage;
    /**
     * item文字内容
     */
    private TextView textView_content;

    public SimpleItemHolder(View itemView) {
        super(itemView);
        this.textView_title = (TextView) itemView.findViewById(R.id.textView_title);
        this.imageView_titleImage = (ImageView) itemView.findViewById(R.id.imageView_titleImage);
        this.textView_content = (TextView) itemView.findViewById(R.id.textView_content);
    }

    public TextView getTextView_title() {
        return textView_title;
    }

    public ImageView getImageView_titleImage() {
        return imageView_titleImage;
    }

    public TextView getTextView_content() {
        return textView_content;
    }
}
