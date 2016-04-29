package com.zzj.gdgm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzj.gdgm.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by J。 on 2016/4/29.
 */
public class PersonInfoAdapter extends BaseAdapter {
    private Map<String, String> map;
    private Context context;
    private List<String> list_title;
    private List<String> list_content;

    public PersonInfoAdapter(Context context) {
        this.context = context;
        list_title = new ArrayList<>();
        list_content = new ArrayList<>();
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            list_title.add(key);
            list_content.add(map.get(key));
        }
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = View.inflate(context, R.layout.person_info_item, null);
        }
        TextView textView_title = (TextView) view.findViewById(R.id.person_info_title);
        textView_title.setText(list_title.get(position));
        TextView textView_content = (TextView) view.findViewById(R.id.person_info_content);
        textView_content.setText(list_content.get(position));
        return view;
    }
}
