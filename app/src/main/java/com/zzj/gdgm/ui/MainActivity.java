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
                if (msg.obj != null && msg.obj instanceof String) {
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                }
                if (msg.obj != null && msg.obj instanceof Map && msg.arg1 == 1) {
                    final Map<String, Object> map = (Map<String, Object>) msg.obj;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View view = View.inflate(MainActivity.this, R.layout.score_custom_dialog, null);
                    builder.setView(view);
                    builder.setTitle("请选择要查询的学年学期");
                    ArrayAdapter<String> arrayAdapter_year = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, (List<String>) map.get("score_year"));
                    arrayAdapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    final Spinner spinner_year = ((Spinner) view.findViewById(R.id.spinner_year));
                    spinner_year.setAdapter(arrayAdapter_year);
                    //默认选择List集合中倒数第二个
                    if (((List<String>) map.get("score_year")).size() > 1) {
                        spinner_year.setSelection(((List<String>) map.get("score_year")).size() - 2);
                    }
                    ArrayAdapter<String> arrayAdapter_semester = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, (List<String>) map.get("score_semester"));
                    arrayAdapter_semester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    final Spinner spinner_semester = ((Spinner) view.findViewById(R.id.spinner_semester));
                    spinner_semester.setAdapter(arrayAdapter_semester);
                    //默认选择List集合中倒数第三个
                    if (((List<String>) map.get("score_semester")).size() > 2) {
                        spinner_semester.setSelection((((List<String>) map.get("score_semester")).size() - 3));
                    }
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            try {
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("__VIEWSTATE", (String) map.get("__VIEWSTATE"))
                                        .add("__VIEWSTATEGENERATOR", (String) map.get("__VIEWSTATEGENERATOR"))
                                        .add("ddlXN", spinner_year.getSelectedItem().toString())
                                        .add("ddlXQ", spinner_semester.getSelectedItem().toString())
                                        .add("Button1", "按学期查询")
                                        .build();
                                String Referer = OkHttpUtil.getREFERER() + mainRecyclerAdapter.getLinkMap().get("学习成绩查询");
                                Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(Referer);
                                while (matcher.find()) {
                                    Referer = Referer.replaceAll(matcher.group(), URLEncoder.encode(matcher.group(), "gb2312"));
                                }
                                Request request = OkHttpUtil.getRequest(Referer, Referer, requestBody);
                                mainRecyclerAdapter.dialogShow("正在努力读取数据", false);

                                OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                        Log.v(TAG, "学习成绩查询  --> onFailure  --> " + e.getMessage());
                                        mainRecyclerAdapter.getProgressDialog().dismiss();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        if (response.code() == 200) {
                                            String content = new String(response.body().bytes(), "gb2312");
                                            ArrayList<CourseInfo> courseInfoArrayList = JsoupService.parseCourseScore(content);
                                            Intent intent = new Intent();
                                            intent.setClass(MainActivity.this, ScoreActivity.class);
                                            intent.putExtra("score", courseInfoArrayList);
                                            intent.putExtra("year", spinner_year.getSelectedItem().toString());
                                            intent.putExtra("semester", spinner_semester.getSelectedItem().toString());
                                            startActivity(intent);
                                        }
                                        Log.v(TAG, "学习成绩查询  --> onResponse  --> response.code = " + response.code());
                                    }
                                });
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } finally {

                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    mainRecyclerAdapter.getProgressDialog().dismiss();
                    builder.show();
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
