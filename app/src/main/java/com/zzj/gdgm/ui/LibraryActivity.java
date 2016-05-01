package com.zzj.gdgm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zzj.gdgm.R;
import com.zzj.gdgm.adapter.LibraryRecyclerAdapter;
import com.zzj.gdgm.bean.BookInfo;
import com.zzj.gdgm.support.JsoupService;
import com.zzj.gdgm.support.OkHttpUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by J。 on 2016/4/30.
 */
public class LibraryActivity extends AppCompatActivity {

    private static final String TAG = "LibraryActivity";


    private Toolbar toolbar;
    private Spinner spinner_search_type;
    private EditText editTextr_search_value;
    private Button button_search;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LibraryRecyclerAdapter libraryRecyclerAdapter;
    private Map<String, Object> map;
    private boolean onLoadingMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_layout);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        libraryRecyclerAdapter = new LibraryRecyclerAdapter(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(libraryRecyclerAdapter);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                    String[] library_search_values = getResources().getStringArray(R.array.library_search_values);
                    String library_search_value = library_search_values[spinner_search_type.getSelectedItemPosition()];
                    search_book(library_search_value, editTextr_search_value.getText().toString().trim(), button_search.getText().toString());
                }
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //当前可视的item数量
                int childCount = linearLayoutManager.getChildCount();
                //已滚动过的item数量
                int firstVisableItem = linearLayoutManager.findFirstVisibleItemPosition();
                //item总数量
                int itemCount = linearLayoutManager.getItemCount();
                if (!onLoadingMore && map.get("next_page") != null && (childCount + firstVisableItem) >= itemCount) {
                    onLoadingMore = true;
                    swipeRefreshLayout.setRefreshing(true);
                    load_more(OkHttpUtil.getLibrarySearchParentUrl() + map.get("next_page"));
                }
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        spinner_search_type = (Spinner) findViewById(R.id.spinner_search_type);
        editTextr_search_value = (EditText) findViewById(R.id.editText_search_value);
        button_search = (Button) findViewById(R.id.button_search);
        recyclerView = (RecyclerView) findViewById(R.id.library_recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.library_swipreRefreshLayout);
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

    private void search_book(String keyword, String keyvalue, String submit) {
        String url = OkHttpUtil.getLibrarySearch() + "?keyword=" + keyword + "&keyvalue=" + keyvalue + "&submit=" + submit;
        String url_new = OkHttpUtil.encodeUrl(url);
        Request request = OkHttpUtil.getRequest(url_new, OkHttpUtil.getLibraySearchHost(), OkHttpUtil.getLibrarySearchReferer());
        OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(LibraryActivity.this, "搜索失败,请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.code() == 200) {
                        String content = new String(response.body().bytes(), "gb2312");
                        map = JsoupService.parseLibrary(content);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                libraryRecyclerAdapter.setBookInfoList((List<BookInfo>) map.get("book_list"));
                                libraryRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.d(TAG, "onResponse  -->>>" + response.code());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }

    private void load_more(String url) {
        String url_new = OkHttpUtil.encodeUrl(url);
        Request request = OkHttpUtil.getRequest(url_new);
        OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        onLoadingMore = false;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String content = new String(response.body().bytes(), "gb2312");
                    map = JsoupService.parseLibrary(content);
                    libraryRecyclerAdapter.getBookInfoList().addAll((List<BookInfo>) map.get("book_list"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            libraryRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            onLoadingMore = false;
                        }
                    });
                }
            }
        });
    }


}
