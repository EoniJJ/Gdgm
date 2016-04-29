package com.zzj.gdgm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zzj.gdgm.R;
import com.zzj.gdgm.adapter.MainRecyclerAdapter;
import com.zzj.gdgm.support.JsoupService;
import com.zzj.gdgm.support.OkHttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 登录后的主界面
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private Handler handler;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private Toolbar toolbar;
    private TextView textView_drawer_top_name;
    private ListView drawer_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main_layout);
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
        String name = intent.getStringExtra("name");
        String content = intent.getStringExtra("content");
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawerLayout_open_string, R.string.drawerLayout_close_string);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, handler);
        mainRecyclerAdapter.setLinkMap(JsoupService.parseMenu(content));
        recyclerView.setAdapter(mainRecyclerAdapter);
        initDrawerMenu(name);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_DrawerLayout);
        textView_drawer_top_name = (TextView) findViewById(R.id.drawer_top_name);
        drawer_listView = (ListView) findViewById(R.id.drawer_listView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDrawerMenu(String name) {
        textView_drawer_top_name.setText(name);
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("title", "个人信息");
        map.put("icon", R.drawable.information_personal);
        list.add(map);
        map = new HashMap<>();
        map.put("title", "修改密码");
        map.put("icon", R.drawable.change_password);
        list.add(map);
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, list, R.layout.drawer_menu_item, new String[]{"title", "icon"}, new int[]{R.id.drawer_menu_title, R.id.drawer_menu_icon});
        drawer_listView.setAdapter(simpleAdapter);
        drawer_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, PersonInfoActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        intent.setClass(MainActivity.this, UpdatePasswordActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
