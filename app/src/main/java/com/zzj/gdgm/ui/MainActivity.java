package com.zzj.gdgm.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.zzj.gdgm.R;
import com.zzj.gdgm.adapter.MainRecyclerAdapter;
import com.zzj.gdgm.bean.CourseInfo;
import com.zzj.gdgm.support.JsoupService;
import com.zzj.gdgm.support.OkHttpUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录后的主界面
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private Handler handler;
    private MainRecyclerAdapter mainRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                /**
                 * 将内容以Toast显示
                 */
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
        mainRecyclerAdapter = new MainRecyclerAdapter(this, handler);
        mainRecyclerAdapter.setLinkMap(JsoupService.parseMenu(content));
        recyclerView.setAdapter(mainRecyclerAdapter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_main);
    }
}
