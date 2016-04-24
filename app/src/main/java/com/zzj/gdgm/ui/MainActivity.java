package com.zzj.gdgm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zzj.gdgm.R;
import com.zzj.gdgm.adapter.MainRecyclerAdapter;
import com.zzj.gdgm.support.JsoupService;

/**
 * 登录后的主界面
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj != null && msg.obj instanceof String) {
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                }
            }
        };
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(this, handler);
        mainRecyclerAdapter.setLinkMap(JsoupService.parseMenu(content));
        recyclerView.setAdapter(mainRecyclerAdapter);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_main);
    }
}
