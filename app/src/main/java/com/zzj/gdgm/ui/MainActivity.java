package com.zzj.gdgm.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzj.gdgm.R;
import com.zzj.gdgm.adapter.MainRecyclerAdapter;
import com.zzj.gdgm.support.JsoupService;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(this);
        mainRecyclerAdapter.setLinkMap(JsoupService.parseMenu(content));
        recyclerView.setAdapter(mainRecyclerAdapter);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_main);
    }
}
