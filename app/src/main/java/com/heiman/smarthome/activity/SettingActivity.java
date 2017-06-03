package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.greendao.SettingTool;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.heiman.widget.togglebutton.ToggleButton;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private ToggleButton toggleSettingShowMessage;
    private ToggleButton toggleSettingVoice;
    private ToggleButton toggleSttingVibration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting);
        setContentLayout(R.layout.activity_setting);
        toggleSettingShowMessage = (ToggleButton) findViewById(R.id.toggle_setting_show_detail_message);
        toggleSettingVoice = (ToggleButton) findViewById(R.id.toggle_setting_voice);
        toggleSttingVibration = (ToggleButton) findViewById(R.id.toggle_setting_vibration);

        toggleSettingShowMessage.setOnToggleChanged(mOnToggleChanged);
        toggleSettingVoice.setOnToggleChanged(mOnToggleChanged);
        toggleSttingVibration.setOnToggleChanged(mOnToggleChanged);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.txt_set));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initToggleButtonState(toggleSettingShowMessage, SettingTool.SMARTHOME_SETTING_SHOW_DETAIL_MESSAGE);
        initToggleButtonState(toggleSettingVoice, SettingTool.SMARTHOME_SETTING_VOICE);
        initToggleButtonState(toggleSttingVibration, SettingTool.SMARTHOME_SETTING_VIBRATION);
    }



    private ToggleButton.OnToggleChanged mOnToggleChanged = new ToggleButton.OnToggleChanged() {
        @Override
        public void onToggle(View view, boolean on) {
            String settingValue = on ? SettingTool.SMARTHOME_SETTING_VALUE_ON : SettingTool.SMARTHOME_SETTING_VALUE_OFF;
            String settingKey = "";
            switch (view.getId()) {
                case R.id.toggle_setting_show_detail_message:
                    LogUtil.e("on:" + on);
                    settingKey = SettingTool.SMARTHOME_SETTING_SHOW_DETAIL_MESSAGE;
                    break;
                case R.id.toggle_setting_voice:
                    LogUtil.e("on:" + on);
                    settingKey = SettingTool.SMARTHOME_SETTING_VOICE;
                    break;
                case R.id.toggle_setting_vibration:
                    LogUtil.e("on:" + on);
                    settingKey = SettingTool.SMARTHOME_SETTING_VIBRATION;
                    break;
            }

            if (!TextUtils.isEmpty(settingKey)) {
                SettingTool.getInstance(MyApplication.getMyApplication()).addSetting(settingKey, settingValue);
            }
        }
    };

    private void initToggleButtonState(ToggleButton toggleButton, String settingKey) {
        SettingTool settingTool = SettingTool.getInstance(MyApplication.getMyApplication());
        if (SettingTool.SMARTHOME_SETTING_VALUE_ON.equals(settingTool.getSettingValue(settingKey, SettingTool.SMARTHOME_SETTING_VALUE_ON))) {
            toggleButton.setToggleOn();
        } else {
            toggleButton.setToggleOff();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
