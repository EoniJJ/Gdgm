package com.zzj.gdgm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zzj.gdgm.R;
import com.zzj.gdgm.support.HttpUtil;
import com.zzj.gdgm.support.JsoupService;
import com.zzj.gdgm.support.MyApplication;
import com.zzj.gdgm.ui.CourseActivity;
import com.zzj.gdgm.view.SimpleItemHolder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by J。 on 2016/4/18.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "MainRecyclerAdapter";
    private Context context;
    private LayoutInflater layoutInflater;
    private Map<String, String> linkMap;

    public MainRecyclerAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public Map<String, String> getLinkMap() {
        return linkMap;
    }

    public void setLinkMap(Map<String, String> linkMap) {
        this.linkMap = linkMap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SimpleItemHolder simpleItemHolder = new SimpleItemHolder(layoutInflater.inflate(R.layout.main_simple_item_layout, parent, false));
        simpleItemHolder.itemView.setOnClickListener(this);
        return simpleItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                ((SimpleItemHolder) holder).getTextView_title().setText("课表查询");
                ((SimpleItemHolder) holder).getTextView_content().setText("课表在手，天下我有，妈妈再也不用担心我缺课啦");
                ((SimpleItemHolder) holder).getImageView_titleImage().setImageResource(R.drawable.test_1);
                holder.itemView.setTag(position);
                break;
            case 1:
                ((SimpleItemHolder) holder).getTextView_title().setText("成绩查询");
                ((SimpleItemHolder) holder).getTextView_content().setText("挂不挂科，点我就知，再也不用怕查不到成绩啦");
                ((SimpleItemHolder) holder).getImageView_titleImage().setImageResource(R.drawable.test_3);
                holder.itemView.setTag(position);
                break;
            case 2:
                ((SimpleItemHolder) holder).getTextView_title().setText("图书查询");
                ((SimpleItemHolder) holder).getTextView_content().setText("图书馆有啥书，一查便知，再也不用白跑一趟啦");
                ((SimpleItemHolder) holder).getImageView_titleImage().setImageResource(R.drawable.test_2);
                holder.itemView.setTag(position);
                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag) {
            case 0:
                for (String key : linkMap.keySet()) {
                    Log.d(TAG, "linkMap --> key = " + key + " --> value = " + linkMap.get(key));
                }
                HttpUtil.get(HttpUtil.getREFERER() + linkMap.get("班级课表查询"), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = null;
                        try {
                            response = new String(responseBody, "gb2312");
                            Intent intent = new Intent();
                            intent.setClass(context, CourseActivity.class);
                            intent.putExtra("response", response);
                            context.startActivity(intent);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } finally {
                            Log.v(TAG, "班级课表查询 onSuccess --> statusCode = " + statusCode);
                            Log.v(TAG, "班级课表查询 onSuccess --> response = " + response);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.v(TAG, "班级课表查询  onFailure --> statusCode = " + statusCode);
                    }
                });

                break;
            case 1:

                break;
            case 2:

                break;

        }
    }
}
