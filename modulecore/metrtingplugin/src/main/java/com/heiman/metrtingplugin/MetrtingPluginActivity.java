package com.heiman.metrtingplugin;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.devicecommon.TimerActivity;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;
import java.util.Date;

public class MetrtingPluginActivity extends GwBaseActivity {

    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private TextView txtCurrentPowerInfo;
    private ImageButton imgBtnPowerOnOff;
    private TextView txtPowerState;
    private TextView txtPowerOnOff;
    private TextView txtTiming;
    private TextView txtCountdownTime;
    private TextView txtElect;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metrtingplugin_activity_metrting_plugin);
        StatusBarUtil.setTranslucent(this, 100);
        XlinkUtils.StatusBarLightMode(this);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        txtCurrentPowerInfo = (TextView) findViewById(R.id.txt_current_power_info);
        imgBtnPowerOnOff = (ImageButton) findViewById(R.id.img_btn_metrting_plugin_on_off);
        txtPowerState = (TextView) findViewById(R.id.txt_metrting_plugin_state);
        txtPowerOnOff = (TextView) findViewById(R.id.txt_metrting_plugin_power_onoff);
        txtTiming = (TextView) findViewById(R.id.txt_metrting_plugin_timing);
        txtCountdownTime = (TextView) findViewById(R.id.txt_metrting_plugin_countdown_time);
        txtElect = (TextView) findViewById(R.id.txt_metrting_plugin_elect);

        titleBarReturn.setOnClickListener(this);
        titleBarMore.setOnClickListener(this);
        imgBtnPowerOnOff.setOnClickListener(this);
        txtPowerOnOff.setOnClickListener(this);
        txtTiming.setOnClickListener(this);
        txtCountdownTime.setOnClickListener(this);
        txtElect.setOnClickListener(this);

        titleBarMore.setVisibility(View.VISIBLE);
        titleBarShare.setVisibility(View.GONE);

        titleBarTitle.setText(R.string.metrting_plugin_title);

        titleBar.setBackgroundColor(getResources().getColor(R.color.metrting_plugin_bg_color2));
        titleBarTitle.setTextColor(getResources().getColor(R.color.white));
        titleBarReturn.setImageResource(R.drawable.personal_back);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (subDevice != null) {
            subDevice.setWifiDevice(device);
            titleBarTitle.setText(subDevice.getDeviceName());
        }
    }

    @Override
    public void deviceData(String dataString) {
        LogUtil.e("dataString:" + dataString);
    }

    @Override
    public void onClickListener(View v) {
        if (!UsefullUtill.judgeClick(R.layout.metrtingplugin_activity_metrting_plugin, 500)) {
            LogUtil.e("点击过快！");
            return;
        }

        if (v.getId() == titleBarReturn.getId()) {
            finish();
            return;
        }

        if (v.getId() == titleBarMore.getId()) {
            LogUtil.e("点击更多图标");
            return;
        }

        if (v.getId() == imgBtnPowerOnOff.getId()) {
            LogUtil.e("点击开关大图标！");
            Toast.makeText(MetrtingPluginActivity.this, "点击开关大图标！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (v.getId() == txtPowerOnOff.getId()) {
            LogUtil.e("点击开关小图标！");
            Toast.makeText(MetrtingPluginActivity.this, "点击开关小图标！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (v.getId() == txtTiming.getId()) {
            LogUtil.e("点击定时图标！");
            openActivity(TimerActivity.class);
            return;
        }

        if (v.getId() == txtCountdownTime.getId()) {
            LogUtil.e("点击倒计时图标!");
            showSetTimeDelay();
            return;
        }

        if (v.getId() == txtElect.getId()) {
            LogUtil.e("点击电量统计图标!");
            openActivity(PowerConsumptionStatisticsActivity.class);
            return;
        }
    }

    private void showSetTimeDelay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                try {
                    LogUtil.e("h:" + date.getHours() + "\tm:" + date.getMinutes() );
                    String str = "";
                    str = date.getHours() == 0 ? date.getMinutes() == 0 ? str : date.getMinutes() + getString(R.string.picker_minite) + str : date.getHours() + getString(R.string.picker_hour) + date.getMinutes() + getString(R.string.picker_minite) + str;
                    LogUtil.e("time delay:" + str);
                } catch (Exception e) {
                    BaseApplication.getLogger().e(e.getMessage());
                }
            }
        }).setType(new boolean[]{false,false,false,true,true,false})
                .setDate(calendar)
                .build();
        pvTime.show();
    }
}
