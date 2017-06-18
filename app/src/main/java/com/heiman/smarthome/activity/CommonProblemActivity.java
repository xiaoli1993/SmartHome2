package com.heiman.smarthome.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.smarthome.R;

public class CommonProblemActivity extends BaseActivity implements View.OnClickListener {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_common_problem);
        setContentLayout(R.layout.activity_common_problem);
        webView = (WebView) findViewById(R.id.web_common_problem);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.txt_common_problem));
        ineve();
    }

    @Override
    public void onClick(View v) {

    }

    @SuppressLint("JavascriptInterface")
    private void ineve() {
        // TODO Auto-generated method stub
        // 允许JavaScript执行
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showHUDProgress(getResources().getString(R.string.loading));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissHUMProgress();
            }
        });


//        if (Utils.isZh(CommonProblem.this)) {
        // 找到Html文件，也可以用网络上的文件
        webView.loadUrl("http://m.heiman.com.cn/appfaq/appfaq.html");
//        } else {
//            webView.loadUrl("http://m.heimantech.com/appfaq/appfaq.html");
//        }
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        WebSettings settings = webView.getSettings();
        // 支持缩放
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(true);
        // 隐藏zoom缩放按钮
        // webSettings.setDisplayZoomControls(false);
        // 自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        // webView.addJavascriptInterface(new Contact(), "contact");
    }


}
