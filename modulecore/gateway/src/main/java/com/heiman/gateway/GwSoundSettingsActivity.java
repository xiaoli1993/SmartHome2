package com.heiman.gateway;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.HeimanSet;
import com.heiman.baselibrary.mode.MoreMenu;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;

/**
 * @Author : 肖力
 * @Time :  2017/6/14 11:25
 * @Description :
 * @Modify record :
 */

public class GwSoundSettingsActivity extends GwBaseActivity {
    private SeekBar seekBarAlarmVolume;
    private SeekBar seekBarBeepVolume;
    private FamiliarRecyclerView recyclerDevice;
    private List<MoreMenu> moreMenuList;
    private FamiliarEasyAdapter<MoreMenu> mAdapter;
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
        setContentView(R.layout.gateway_activity_gw_sound_settings);
        initView();
        initMenuData();
        initEven();
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
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
        titleBarTitle.setText(getString(R.string.gateway_Sound_settings));
        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
        seekBarAlarmVolume.setProgress(device.getAlarmlevel());
        seekBarAlarmVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                HeimanSet.PLBean.GwBasicOID gwBasicOID = new HeimanSet.PLBean.GwBasicOID();
                gwBasicOID.setAlarmlevel(seekBar.getProgress());
                String brightness = HeimanCom.setBasic(SmartPlug.mgetSN(), 0, gwBasicOID);
                BaseApplication.getLogger().json(brightness);
                sendData(brightness);
            }
        });
        seekBarBeepVolume.setProgress(device.getSoundlevel());
        seekBarBeepVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                HeimanSet.PLBean.GwBasicOID gwBasicOID = new HeimanSet.PLBean.GwBasicOID();
                gwBasicOID.setSoundlevel(seekBar.getProgress());
                String brightness = HeimanCom.setBasic(SmartPlug.mgetSN(), 0, gwBasicOID);
                BaseApplication.getLogger().json(brightness);
                sendData(brightness);
            }
        });
//        moreMenuAdapter = new MoreMenuAdapter(GwMoreMenuActivity.this, moreMenuList);

        mAdapter = new FamiliarEasyAdapter<MoreMenu>(this, R.layout.item_more_menu, moreMenuList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                MoreMenu moreMenu = moreMenuList.get(position);
                ImageView imageArrowRight = (ImageView) holder.findView(com.heiman.baselibrary.R.id.image_arrow_right);
                TextView tvLeft = (TextView) holder.findView(com.heiman.baselibrary.R.id.tv_Left);
                TextView tvRight = (TextView) holder.findView(com.heiman.baselibrary.R.id.tv_Right);
                tvLeft.setText(moreMenu.getLeftText());
                tvRight.setText(moreMenu.getRightText());
                imageArrowRight.setVisibility(moreMenu.isVRightImage() ? View.VISIBLE : View.GONE);
            }
        };
        recyclerDevice.setAdapter(mAdapter);
        recyclerDevice.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
//                MoreMenu moreMenu = moreMenuList.get(position);
//                BaseApplication.getLogger().i("点击：" + moreMenu.getLeftText());
                Bundle paramBundle = new Bundle();
                paramBundle.putBoolean(Constant.IS_DEVICE, true);
                paramBundle.putString(Constant.DEVICE_MAC, device.getDeviceMac());
                paramBundle.putBoolean(Constant.IS_SUB, false);
                switch (position) {
                    case 0:
//                        openActivity(GwLanguageActivity.class, paramBundle);
                        break;
                }
            }
        });
    }

    private void initMenuData() {
        moreMenuList = new ArrayList<MoreMenu>();
        moreMenuList.add(new MoreMenu("报警音", "110", true));
    }

    private void initView() {
        seekBarAlarmVolume = (SeekBar) findViewById(R.id.seekBar_alarm_volume);
        seekBarBeepVolume = (SeekBar) findViewById(R.id.seekBar_beep_volume);
        recyclerDevice = (FamiliarRecyclerView) findViewById(R.id.recycler_device);
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
        try {
            JSONObject jsonObject = new JSONObject(dataString);
            JSONObject PL = jsonObject.getJSONObject("PL");
            int AL = PL.getInt("AL");
            device.setAlarmlevel(AL);
            DeviceManage.getInstance().addDevice(device);
        } catch (JSONException e) {
//            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(dataString);
            JSONObject PL = jsonObject.getJSONObject("PL");
            int SL = PL.getInt("SL");
            device.setSoundlevel(SL);
            DeviceManage.getInstance().addDevice(device);
        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }
}
