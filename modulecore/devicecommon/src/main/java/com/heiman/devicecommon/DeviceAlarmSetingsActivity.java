package com.heiman.devicecommon;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.widget.togglebutton.ToggleButton;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * @Author : 肖力
 * @Time :  2017/6/20 9:33
 * @Description :
 * @Modify record :
 */

public class DeviceAlarmSetingsActivity extends GwBaseActivity {

    private ToggleButton toggleAlarmEnable;
    private TextView tvAlarm;
    private TextView tvWk;
    private TextView tvShTimer;
    private TextView tvEnTimer;
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
        setContentView(R.layout.devicecommon_activity_device_alarm_settings);
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
        titleBarTitle.setText(R.string.devicecommon_alarm_settins);
        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void initView() {
        toggleAlarmEnable = (ToggleButton) findViewById(R.id.toggle_alarm_enable);
        tvAlarm = (TextView) findViewById(R.id.tv_alarm);
        tvWk = (TextView) findViewById(R.id.tv_wk);
        tvShTimer = (TextView) findViewById(R.id.tv_sh_timer);
        tvEnTimer = (TextView) findViewById(R.id.tv_en_timer);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
    }


    @Override
    public void onClickListener(View v) {

    }

    @Override
    public void deviceData(String dataString) {

    }
}
