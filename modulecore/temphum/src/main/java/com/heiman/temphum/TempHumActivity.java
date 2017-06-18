package com.heiman.temphum;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.temphum.fragment.HumFragment;
import com.heiman.temphum.fragment.TempFragment;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.jaeger.library.StatusBarUtil;

public class TempHumActivity extends GwBaseActivity implements RadioGroup.OnCheckedChangeListener{

    private ImageView imgBack;
    private TextView txtTitle;
    private ImageView imgMore;
    private RadioGroup rgpTempHumSelector;
    private Fragment tempFragment;
    private Fragment humFragmet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temphum_activity_temp_hum);
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
        View head = findViewById(R.id.temp_hum_head);
        imgBack = (ImageView) head.findViewById(R.id.title_bar_return);
        txtTitle = (TextView) head.findViewById(R.id.title_bar_title);
        imgMore = (ImageView) head.findViewById(R.id.title_bar_more);
        rgpTempHumSelector = (RadioGroup) findViewById(R.id.rgp_temp_hum_selector);

        txtTitle.setText(R.string.title_temp_hum);
        imgBack.setImageResource(R.drawable.personal_back);
        imgMore.setImageResource(R.drawable.icon_more);

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);

        rgpTempHumSelector.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (subDevice != null) {
            subDevice.setWifiDevice(device);
            txtTitle.setText(subDevice.getDeviceName());
        } else {
            Toast.makeText(this, "没有温湿度检测仪！", Toast.LENGTH_SHORT).show();
        }
        initFragment();
    }

    @Override
    public void deviceData(String dataString) {
        LogUtil.e("dataString:" + dataString);
    }

    @Override
    public void onClickListener(View v) {
        if (!UsefullUtill.judgeClick(R.layout.temphum_activity_temp_hum, 500)) {
            LogUtil.e("点击过快！");
            return;
        }

        if (v.getId() == imgBack.getId()) {
            finish();
            return;
        }

        if (v.getId() == imgMore.getId()) {
            Bundle paramBundle = new Bundle();

            if (subDevice != null) {
                paramBundle.putBoolean(Constant.IS_DEVICE, true);
                paramBundle.putString(Constant.DEVICE_MAC, subDevice.getDeviceMac());
                paramBundle.putBoolean(Constant.IS_SUB, true);
                paramBundle.putString(Constant.ZIGBEE_MAC, subDevice.getZigbeeMac());
            }
            openActivity(TempHumMoreMenuActivity.class, paramBundle);
            return;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);

        if (checkedId == R.id.rdo_temp) {
            LogUtil.e("查看温度信息！");

            if (tempFragment == null) {
                tempFragment = new TempFragment();
                transaction.add(R.id.frame_temp_hum_container, tempFragment);
            } else {
                transaction.show(tempFragment);
            }
            transaction.commit();
            return;
        }

        if (checkedId == R.id.rdo_hum) {
            LogUtil.e("查看湿度信息！");

            if (humFragmet == null) {
                humFragmet = new HumFragment();
                transaction.add(R.id.frame_temp_hum_container, humFragmet);
            } else {
                transaction.show(humFragmet);
            }
            transaction.commit();
            return;
        }
    }

    private void initFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        if (humFragmet == null) {
            humFragmet = new HumFragment();
            transaction.add(R.id.frame_temp_hum_container, humFragmet);
        } else {
            transaction.show(humFragmet);
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {

        if (tempFragment != null) {
            transaction.hide(tempFragment);
        }

        if (humFragmet != null) {
            transaction.hide(humFragmet);
        }
    }
}
