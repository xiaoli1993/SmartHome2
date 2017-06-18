package com.heiman.temphum;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.heiman.widget.seekbar.RangeSeekBar;
import com.heiman.widget.togglebutton.ToggleButton;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class TempHumAlarmSettingActivity extends GwBaseActivity implements RangeSeekBar.OnRangeChangedListener, ToggleButton.OnToggleChanged{

    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private RangeSeekBar rangeSettingTemp;
    private TextView txtTempConditionExplain1;
    private TextView txtTempConditionExplain2;
    private RangeSeekBar rangeSettingHum;
    private TextView txtHumConditionExplain1;
    private TextView txtHumConditionExplain2;
    private ToggleButton toggleTempAlarmCondition;
    private LinearLayout llTempAlarmSettingContainer;
    private ToggleButton toggleHumAlarmCondition;
    private LinearLayout llHumAlarmSettingContainer;

    private final float DEFAULT_TEMP_LOW = -10f;
    private final float DEFAULT_TEMP_UP = 70f;
    private final float DEFAULT_HUM_LOW = 0f;
    private final float DEFAULT_HUM_UP = 100f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temphum_activity_temp_hum_alarm_setting);
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        rangeSettingTemp = (RangeSeekBar) findViewById(R.id.range_temp_setting);
        txtTempConditionExplain1 = (TextView) findViewById(R.id.txt_temp_alarm_condition_explain1);
        txtTempConditionExplain2 = (TextView) findViewById(R.id.txt_temp_alarm_condition_explain2);
        rangeSettingHum = (RangeSeekBar) findViewById(R.id.range_hum_setting);
        txtHumConditionExplain1 = (TextView) findViewById(R.id.txt_hum_alarm_condition_explain1);
        txtHumConditionExplain2 = (TextView) findViewById(R.id.txt_hum_alarm_condition_explain2);
        toggleTempAlarmCondition = (ToggleButton) findViewById(R.id.toggle_temp_alarm_condition);
        llTempAlarmSettingContainer = (LinearLayout) findViewById(R.id.ll_temp_alarm_setting_container);
        toggleHumAlarmCondition = (ToggleButton) findViewById(R.id.toggle_hum_alarm_condition);
        llHumAlarmSettingContainer = (LinearLayout) findViewById(R.id.ll_hum_alarm_setting_container);

        titleBarReturn.setOnClickListener(this);

        rangeSettingTemp.setOnRangeChangedListener(this);
        rangeSettingHum.setOnRangeChangedListener(this);

        toggleTempAlarmCondition.setOnToggleChanged(this);
        toggleHumAlarmCondition.setOnToggleChanged(this);

        titleBarMore.setVisibility(View.GONE);
        titleBarShare.setVisibility(View.GONE);

        titleBarTitle.setText(R.string.alarm_condition_setting);

        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
        titleBarReturn.setImageResource(R.drawable.personal_back);

        rangeSettingTemp.setValue(DEFAULT_TEMP_LOW, DEFAULT_TEMP_UP);
        rangeSettingHum.setValue(DEFAULT_HUM_LOW, DEFAULT_HUM_UP);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (subDevice != null) {
            subDevice.setWifiDevice(device);
            float tempLow = UsefullUtill.stringToFloat(subDevice.gettCkLow());
            float tempUp = UsefullUtill.stringToFloat(subDevice.gettCkUp());
            float humLow = UsefullUtill.stringToFloat(subDevice.gethCkLow());
            float humUp = UsefullUtill.stringToFloat(subDevice.gethCkUp());

            if (tempLow >= DEFAULT_TEMP_LOW && tempUp <= DEFAULT_TEMP_UP && tempLow < tempUp) {
                rangeSettingTemp.setValue(tempLow, tempUp);
            }

            if (humLow >= DEFAULT_HUM_LOW && humUp <= DEFAULT_HUM_UP && humLow < humUp) {
                rangeSettingHum.setValue(humLow, humUp);
            }
            //假定为0时都关闭，为1时温度报警开启湿度报警关闭，为2时温度报警关闭湿度报警开启，为3时都开启
            switch (subDevice.gettCkOnoff()) {
                case 0:
                    toggleTempAlarmCondition.setToggleOff();
                    toggleHumAlarmCondition.setToggleOff();
                    llTempAlarmSettingContainer.setVisibility(View.GONE);
                    llHumAlarmSettingContainer.setVisibility(View.GONE);
                    break;
                case 1:
                    toggleTempAlarmCondition.setToggleOn();
                    toggleHumAlarmCondition.setToggleOff();
                    llTempAlarmSettingContainer.setVisibility(View.VISIBLE);
                    llHumAlarmSettingContainer.setVisibility(View.GONE);
                    break;
                case 2:
                    toggleTempAlarmCondition.setToggleOff();
                    toggleHumAlarmCondition.setToggleOn();
                    llTempAlarmSettingContainer.setVisibility(View.GONE);
                    llHumAlarmSettingContainer.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    toggleTempAlarmCondition.setToggleOn();
                    toggleHumAlarmCondition.setToggleOn();
                    llTempAlarmSettingContainer.setVisibility(View.VISIBLE);
                    llHumAlarmSettingContainer.setVisibility(View.VISIBLE);
                    break;
            }
        }

        float[] tempRange = rangeSettingTemp.getCurrentRange();
        txtTempConditionExplain1.setText(getString(R.string.temp_alarm_condition_explain1).replace("temp", (int)tempRange[0] + ""));
        txtTempConditionExplain2.setText(getString(R.string.temp_alarm_condition_explain2).replace("temp", (int)tempRange[1] + ""));

        float[] humRange = rangeSettingHum.getCurrentRange();
        txtHumConditionExplain1.setText(getString(R.string.hum_alarm_condition_explain1).replace("hum", (int)humRange[0] + ""));
        txtHumConditionExplain2.setText(getString(R.string.hum_alarm_condition_explain2).replace("hum", (int)humRange[1] + ""));
    }

    @Override
    public void deviceData(String dataString) {
        LogUtil.e("dataString:" + dataString);
    }

    @Override
    public void onClickListener(View v) {
        if (!UsefullUtill.judgeClick(R.layout.temphum_activity_temp_hum_alarm_setting, 500)) {
            LogUtil.e("点击过快！");
            return;
        }

        if (v.getId() == titleBarReturn.getId()) {
            finish();
            return;
        }

    }

    @Override
    public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
        if (view.getId() == rangeSettingTemp.getId()) {
            LogUtil.e("温度\tmin:" + min + "\tmax:" + max);
            float[] tempRange = rangeSettingTemp.getCurrentRange();
            txtTempConditionExplain1.setText(getString(R.string.temp_alarm_condition_explain1).replace("temp", (int)tempRange[0] + ""));
            txtTempConditionExplain2.setText(getString(R.string.temp_alarm_condition_explain2).replace("temp", (int)tempRange[1] + ""));

            if (isFromUser && subDevice != null) {
                subDevice.settCkLow((int)tempRange[0] + "");
                subDevice.settCkUp((int)tempRange[1] + "");
                SubDeviceManage.getInstance().updateDevice(subDevice);
            }
            return;
        }

        if (view.getId() == rangeSettingHum.getId()) {
            LogUtil.e("湿度\tmin:" + min + "\tmax:" + max);
            float[] humRange = rangeSettingHum.getCurrentRange();
            txtHumConditionExplain1.setText(getString(R.string.hum_alarm_condition_explain1).replace("hum", (int)humRange[0] + ""));
            txtHumConditionExplain2.setText(getString(R.string.hum_alarm_condition_explain2).replace("hum", (int)humRange[1] + ""));

            if (isFromUser && subDevice != null) {
                subDevice.sethCkLow((int)humRange[0] + "");
                subDevice.sethCkUp((int)humRange[1] + "");
                SubDeviceManage.getInstance().updateDevice(subDevice);
            }
            return;
        }
    }

    @Override
    public void onToggle(View view, boolean on) {
        if (subDevice != null) {
            int onoff = toggleTempAlarmCondition.isToggleOn() ? 1 : 0;
            onoff = toggleHumAlarmCondition.isToggleOn() ? onoff + 2 : onoff;
            subDevice.settCkOnoff(onoff);
            SubDeviceManage.getInstance().updateDevice(subDevice);
        }

        if (toggleTempAlarmCondition.getId() == view.getId()) {

            if (on) {
                llTempAlarmSettingContainer.setVisibility(View.VISIBLE);
            } else {
                llTempAlarmSettingContainer.setVisibility(View.GONE);
            }
            return;
        }

        if (toggleHumAlarmCondition.getId() == view.getId()) {

            if (on) {
                llHumAlarmSettingContainer.setVisibility(View.VISIBLE);
            } else {
                llHumAlarmSettingContainer.setVisibility(View.GONE);
            }
            return;
        }
    }
}
