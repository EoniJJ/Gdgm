package com.zzj.gdgm.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzj.gdgm.R;
import com.zzj.gdgm.support.OkHttpUtil;
import com.zzj.gdgm.ui.CourseActivity;
import com.zzj.gdgm.view.SimpleItemHolder;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by J。 on 2016/4/18.
 * MainActivity的Recycler适配器
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "MainRecyclerAdapter";
    private Context context;
    private LayoutInflater layoutInflater;
    private Map<String, String> linkMap;
    private Handler handler;

    public MainRecyclerAdapter(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
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
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("正在读取数据....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                for (String key : linkMap.keySet()) {
                    Log.d(TAG, "linkMap --> key = " + key + " --> value = " + linkMap.get(key));
                }
                Request request = OkHttpUtil.getRequest(OkHttpUtil.getREFERER() + linkMap.get("班级课表查询"));
                OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = Message.obtain();
                        message.obj = "获取数据失败";
                        handler.sendMessage(message);
                        Log.v(TAG, "班级课表查询  onFailure -->  = " + e.getMessage());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message message = Message.obtain();
                        try {
                            if (response.code() == 200) {
                                String content = new String(response.body().bytes(), "gb2312");
                                if (content != null) {
                                    Intent intent = new Intent();
                                    intent.setClass(context, CourseActivity.class);
                                    intent.putExtra("content", content);
                                    context.startActivity(intent);
                                }
                            } else {
                                message.obj = "获取数据失败";
                                handler.sendMessage(message);
                            }
                            Log.v(TAG, "班级课表查询  onResponse -->  statuscode = " + response.code());
                        } catch (IOException e) {
                            message.obj = "获取数据失败";
                            handler.sendMessage(message);
                            e.printStackTrace();
                        } finally {
                            progressDialog.dismiss();
                        }
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
