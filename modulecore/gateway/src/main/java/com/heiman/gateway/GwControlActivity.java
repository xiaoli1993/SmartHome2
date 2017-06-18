package com.heiman.gateway;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
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
 * @Time :  2017/6/13 18:37
 * @Description :
 * @Modify record :
 */

public class GwControlActivity extends GwBaseActivity {
    private SeekBar seekBarBrightness;
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
        setContentView(R.layout.gateway_activity_gw_control);
        initView();
        initEven();
        initMenuData();
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> OID = new ArrayList<String>();
        OID.add(HeimanCom.COM_GW_OID.GW_BASIC_INFORMATION);
        String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 0, OID);
        BaseApplication.getLogger().json(sb);
        sendData(sb);
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
        titleBarTitle.setText(device.getDeviceName());
        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
        seekBarBrightness.setProgress(device.getGwlightlevel());
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                HeimanSet.PLBean.GwBasicOID gwBasicOID = new HeimanSet.PLBean.GwBasicOID();
                gwBasicOID.setGwlightlevel(seekBar.getProgress());
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
                        openActivity(GwLanguageActivity.class, paramBundle);
                        break;
                    case 1:
                        openActivity(GwAlarmDurationActivity.class, paramBundle);
                        break;
                    case 2:
                        openActivity(GwSoundSettingsActivity.class, paramBundle);
                        break;
                    case 3:
                        break;
                    case 4:
                        paramBundle.putBoolean(Constant.IS_SUB, true);
                        startActivityForName("com.heiman.smarthome.activity.AddDeviceActivity", paramBundle);
                        break;
                    case 5:
                        openActivity(GwNightLightTimeActivity.class, paramBundle);
                        break;
                }
            }
        });
    }

    private void initMenuData() {
        moreMenuList.clear();
        if (device.getGwlanguage().equals("CN")) {
            moreMenuList.add(new MoreMenu(getString(R.string.gateway_Device_language), getString(R.string.gateway_Chinese), true));
        } else if (device.getGwlanguage().equals("US")) {
            moreMenuList.add(new MoreMenu(getString(R.string.gateway_Device_language), getString(R.string.gateway_English), true));
        } else {
            moreMenuList.add(new MoreMenu(getString(R.string.gateway_Device_language), device.getGwlanguage(), true));
        }
        moreMenuList.add(new MoreMenu(getString(R.string.gateway_alarm_duration), device.getBetimer() + "秒", true));
        moreMenuList.add(new MoreMenu(getString(R.string.gateway_Sound_settings), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.gateway_Operation_diary), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.gateway_Add_sub_device), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.gateway_Night_light_time), "", true));
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        seekBarBrightness = (SeekBar) findViewById(R.id.seekBar_brightness);
        recyclerDevice = (FamiliarRecyclerView) findViewById(R.id.recycler_device);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        moreMenuList = new ArrayList<MoreMenu>();
    }

    @Override
    public void onClickListener(View v) {

    }

    @Override
    public void deviceData(String dataString) {
        try {
            JSONObject jsonObject = new JSONObject(dataString);
            JSONObject PL = jsonObject.getJSONObject("PL");
            int LL = PL.getInt("LL");
            device.setGwlightlevel(LL);
            DeviceManage.getInstance().addDevice(device);
        } catch (JSONException e) {
//            e.printStackTrace();
        }
        try {

            JSONObject jsonObject = new JSONObject(dataString);
            JSONObject PL = jsonObject.getJSONObject("PL");
            BaseApplication.getLogger().i(PL.toString());
            JSONObject OID = PL.getJSONObject(HeimanCom.COM_GW_OID.GW_BASIC_INFORMATION);
            Gson gson = new Gson();
            HeimanSet.PLBean.GwBasicOID gwBasicOID = gson.fromJson(OID.toString(), HeimanSet.PLBean.GwBasicOID.class);
            device.setArmtype(gwBasicOID.getArmtype());
            device.setAlarmlevel(gwBasicOID.getAlarmlevel());
            device.setSoundlevel(gwBasicOID.getSoundlevel());
            device.setBetimer(gwBasicOID.getBetimer());
            device.setGwlanguage(gwBasicOID.getGwlanguage());
            device.setGwlightlevel(gwBasicOID.getGwlightlevel());
            device.setGwlightonoff(gwBasicOID.getGwlightonoff());
            device.setLgtimer(gwBasicOID.getLgtimer());
            DeviceManage.getInstance().addDevice(device);
            initMenuData();
        } catch (Exception e) {

        }
    }
}
