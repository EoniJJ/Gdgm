package com.zzj.gdgm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.zzj.gdgm.R;
import com.zzj.gdgm.adapter.PersonInfoAdapter;
import com.zzj.gdgm.support.JsoupService;
import com.zzj.gdgm.support.OkHttpUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by J。 on 2016/4/29.
 * 个人信息Activity
 */
public class PersonInfoActivity extends AppCompatActivity {

    private static final String TAG = "PersonInfoActivity";

    private Toolbar toolbar;
    private ListView listView;
    private Map<String, String> map;
    private PersonInfoAdapter personInfoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_layout);
        initView();
        personInfoAdapter = new PersonInfoAdapter(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }


    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        listView = (ListView) findViewById(R.id.person_info_listview);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取Request对象
        final Request request = OkHttpUtil.getRequest(OkHttpUtil.getREFERER() + JsoupService.getLinkMap().get("个人信息"));
        OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PersonInfoActivity.this, "获取失败，请检擦网络", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = new String(response.body().bytes(), "gb2312");
                map = JsoupService.parsePersonInfo(content);
                //将数据传递到适配器
                personInfoAdapter.setMap(map);
                //更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(personInfoAdapter);
                    }
                });
            }
        });
    }
}
