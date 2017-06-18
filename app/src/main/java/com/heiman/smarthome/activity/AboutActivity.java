package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.smarthome.BuildConfig;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtCheckAppVersion;
    private TextView txtPolicy;
    private TextView txtAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about);
        setContentLayout(R.layout.activity_about);
        txtCheckAppVersion = (TextView) findViewById(R.id.txt_check_app_version);
        txtPolicy = (TextView) findViewById(R.id.txt_use_policy);
        txtAppVersion = (TextView) findViewById(R.id.txt_app_version);

        txtCheckAppVersion.setOnClickListener(this);
        txtPolicy.setOnClickListener(this);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.txt_about));
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtAppVersion.setText(getString(R.string.txt_app_version) + BuildConfig.VERSION_NAME);
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_about, 500)) {
            LogUtil.e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.txt_check_app_version:
                Toast.makeText(AboutActivity.this, R.string.txt_check_app_version, Toast.LENGTH_LONG).show();
                startActivityForName("com.heiman.devicecommon.ChoiceRoomActivity");
                break;
            case R.id.txt_use_policy:
                Toast.makeText(AboutActivity.this, R.string.txt_use_policy, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
