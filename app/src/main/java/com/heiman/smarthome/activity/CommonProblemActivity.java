package com.heiman.smarthome.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;

public class CommonProblemActivity extends BaseActivity implements View.OnClickListener{

    private WebView webCommonProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_common_problem);
        setContentLayout(R.layout.activity_common_problem);
        webCommonProblem = (WebView) findViewById(R.id.web_common_problem);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.txt_common_problem));
        try {
            webCommonProblem.getSettings().setJavaScriptEnabled(true);
            webCommonProblem.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webCommonProblem.loadUrl("http://baidu.com");
            webCommonProblem.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {

    }
}
