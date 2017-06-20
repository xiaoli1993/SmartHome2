package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.smarthome.R;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * @Author : 肖力
 * @Time :  2017/6/13 8:57
 * @Description :
 * @Modify record :
 */

public class AlarmActivity extends GwBaseActivity {
    private ImageView imageDeviceAlarm;
    private TextView tvDeviceAlarm;
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        StatusBarUtil.setTranslucent(this, 50);
        initView();
        initEven();
    }

    private void initEven() {
        titleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarMore.setVisibility(View.GONE);
        titleBarShare.setVisibility(View.GONE);
        titleBarTitle.setText(R.string.alarm_title);
        titleBar.setBackgroundColor(getResources().getColor(com.heiman.gateway.R.color.white));
    }

    private void initView() {
        imageDeviceAlarm = (ImageView) findViewById(R.id.image_device_alarm);
        tvDeviceAlarm = (TextView) findViewById(R.id.tv_device_alarm);
        findViewById(R.id.image_btn_box_del).setOnClickListener(this);
        titleBar = (FrameLayout) findViewById(com.heiman.gateway.R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(com.heiman.gateway.R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(com.heiman.gateway.R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(com.heiman.gateway.R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(com.heiman.gateway.R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(com.heiman.gateway.R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(com.heiman.gateway.R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(com.heiman.gateway.R.id.title_bar_share);

        Bundle bundle = this.getIntent().getExtras();
        String Content = bundle.getString("Content");
        BaseApplication.getLogger().i("isSub:"+isSub);
        if (isSub) {
            tvDeviceAlarm.setText(subDevice.getRoomID() + subDevice.getDeviceName() + "\n" + "报警状态:" + Content);
        } else {
            tvDeviceAlarm.setText(device.getRoomID() + device.getDeviceName() + "\n" + "报警状态:" + Content);
        }

    }


    @Override
    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.image_btn_box_del:
                //TODO implement
                finish();
                break;
        }
    }

    @Override
    public void deviceData(String dataString) {

    }
}
