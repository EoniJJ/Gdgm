package com.example.arron.gdgm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BaseActivity;

/**
 * Created by Arron on 2017/4/18.
 */

public class WebActivity extends BaseActivity {
    private String title;
    private String url;
    private WebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseIntentData(getIntent());
        setContentView(R.layout.activity_web);
        loadWeb();
    }

    private void loadWeb() {
        webView.loadUrl(url);
    }

    @Override
    protected String getTitleName() {
        return title;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getBaseIntentData(intent);
        setTitleName(title);
        loadWeb();
    }

    @Override
    protected void backClick(View v) {
        finish();
    }

    @Override
    protected void initView() {
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }


    private void getBaseIntentData(Intent intent) {
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
    }

    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title).putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }
}
