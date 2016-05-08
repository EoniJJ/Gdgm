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
 * 图书查询Activity
 */
public class LibraryActivity extends AppCompatActivity {

    private static final String TAG = "LibraryActivity";


    private Toolbar toolbar;
    /**
     * 检索词类型Spinner
     */
    private Spinner spinner_search_type;
    /**
     * 检索关键字
     */
    private EditText editTextr_search_value;
    private Button button_search;
    private RecyclerView recyclerView;
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    private LibraryRecyclerAdapter libraryRecyclerAdapter;

    /**
     * Map集合，key为信息种类  book_list —> 对应图书信息，value为存放图书信息的集合  next_page ->对应下一页 ，若存在下一页，则该值不为空。
     */
    private Map<String, Object> map;

    /**
     * 是否正在加载更多
     */
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
                //如果没有在加载则执行
                if (!swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                    //获取检索词种类所对应的全部value转成数组对象
                    String[] library_search_values = getResources().getStringArray(R.array.library_search_values);
                    //所选择的检索词种类
                    String library_search_value = library_search_values[spinner_search_type.getSelectedItemPosition()];
                    //将检索词种类，检索关键字，以及按钮的Text(value)作为参数传给查询图书方法
                    search_book(library_search_value, editTextr_search_value.getText().toString().trim(), button_search.getText().toString());
                }
            }
        });
        //recyclerview添加滚动监听器
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
                //如果当前没有在进行触底加载  并且 map中的下一页不为空(即存在下一页)  并且 已滚动的item数量+当前可视的item数量>=item总数量(证明已到达底部)
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

    /**
     * 查询图书的方法
     *
     * @param keyword  检索词种类
     * @param keyvalue 检索词关键字
     * @param submit   按钮value
     */
    private void search_book(String keyword, String keyvalue, String submit) {
        //将所有参数转为url
        String url = OkHttpUtil.getLibrarySearch() + "?keyword=" + keyword + "&keyvalue=" + keyvalue + "&submit=" + submit;
        //对url中的中文进行编码
        String url_new = OkHttpUtil.encodeUrl(url);
        //获得request对象
        Request request = OkHttpUtil.getRequest(url_new, OkHttpUtil.getLibraySearchHost(), OkHttpUtil.getLibrarySearchReferer());
        OkHttpUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(LibraryActivity.this, getResources().getString(R.string.seach_book_error), Toast.LENGTH_SHORT).show();
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
                                //将数据传递给适配器
                                libraryRecyclerAdapter.setBookInfoList((List<BookInfo>) map.get("book_list"));
                                //更新数据 更新Ui
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
                            //刷新动画结束
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }

    /**
     * 触底加载
     *
     * @param url 要加载的目标Url
     */
    private void load_more(String url) {
        //将url中的中文进行编码
        String url_new = OkHttpUtil.encodeUrl(url);
        //获得request对象
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
                    //将数据添加到适配器中的集合
                    libraryRecyclerAdapter.getBookInfoList().addAll((List<BookInfo>) map.get("book_list"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //适配器更新ui
                            libraryRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //加载动画关闭
                            swipeRefreshLayout.setRefreshing(false);
                            //触底加载结束
                            onLoadingMore = false;
                        }
                    });
                }
            }
        });
    }


}
