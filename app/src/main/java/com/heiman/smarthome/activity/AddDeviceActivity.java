package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.R;
import com.heiman.smarthome.modle.DeviceType;
import com.heiman.smarthome.view.HeaderAndFooterViewUtil;
import com.heiman.widget.swipeback.CloseActivityClass;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;

/**
 * @Author : 肖力
 * @Time :  2017/6/1 17:30
 * @Description :
 * @Modify record :
 */

public class AddDeviceActivity extends BaseActivity {
    private FamiliarRecyclerView recyclerWifiDevice;
    private FamiliarEasyAdapter<DeviceType> mWifiAdapter;
    private FamiliarRecyclerView recyclerSubDevice;
    private FamiliarEasyAdapter<DeviceType> mSubAdapter;

    private List<DeviceType> wifiList;
    private List<DeviceType> subList;

    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private TextView titleTvRight;
    private ImageView titleBarShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        StatusBarUtil.setTranslucent(this, 50);
        CloseActivityClass.activityList.add(this);
        initView();
        initData();
    }

    private void initView() {
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleTvRight = (TextView) findViewById(R.id.title_tv_Right);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        titleBarTitle.setText(R.string.Select_device);
        titleBarTitle.setTextColor(getResources().getColor(R.color.class_P));
        titleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        wifiList = new ArrayList<DeviceType>();
        subList = new ArrayList<DeviceType>();
        recyclerWifiDevice = (FamiliarRecyclerView) findViewById(R.id.recycler_wifi_device);
        recyclerWifiDevice.setNestedScrollingEnabled(false);
        recyclerWifiDevice.setHasFixedSize(true);
        recyclerWifiDevice.addHeaderView(HeaderAndFooterViewUtil.getHeadView(this, true, 0x00000000, getString(R.string.WiFi_Device)));
        mWifiAdapter = new FamiliarEasyAdapter<DeviceType>(this, R.layout.item_grid_scene, wifiList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                final DeviceType deviceType = wifiList.get(position);
                TextView tvTxt = holder.findView(R.id.tv_txt);
                ImageView imageIcon = holder.findView(R.id.image_icon);
                AVLoadingIndicatorView avi = holder.findView(R.id.avi);
                ImageView imageScene = holder.findView(R.id.image_scene);
                imageScene.setVisibility(View.GONE);
                avi.setVisibility(View.GONE);
                imageIcon.setImageResource(SmartHomeUtils.typeToIcon(false, deviceType.getDeviceType()));
                tvTxt.setText(SmartHomeUtils.typeToNikeName(false, deviceType.getDeviceType()));
            }
        };
        recyclerWifiDevice.setAdapter(mWifiAdapter);
        recyclerWifiDevice.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                final DeviceType deviceType = wifiList.get(position);
                Bundle paramBundle = new Bundle();
                paramBundle.putInt(Constant.TYPE, deviceType.getDeviceType());
                switch (deviceType.getDeviceType()) {
                    case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW:
                        paramBundle.putBoolean(Constant.IS_DEVICE, false);
                        startActivityForName("com.heiman.gateway.LTLinkActivity", paramBundle);
                        break;
                    case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW:
                        paramBundle.putBoolean(Constant.IS_DEVICE, false);
                        startActivityForName("com.heiman.gateway.XlinkScanActivity", paramBundle);
                        break;
                    default:
                        openActivity(ConfigurationWizardActivity.class, paramBundle);
                        break;
                }

            }
        });
        recyclerSubDevice = (FamiliarRecyclerView) findViewById(R.id.recycler_sub_device);
//        recyclerSubDevice.setNestedScrollingEnabled(false);
        recyclerSubDevice.setHasFixedSize(true);
        recyclerSubDevice.addHeaderView(HeaderAndFooterViewUtil.getHeadView(this, true, 0x00000000, getString(R.string.Zigbee_Device)));
        mSubAdapter = new FamiliarEasyAdapter<DeviceType>(this, R.layout.item_grid_scene, subList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                final DeviceType deviceType = subList.get(position);
                TextView tvTxt = holder.findView(R.id.tv_txt);
                ImageView imageIcon = holder.findView(R.id.image_icon);
                AVLoadingIndicatorView avi = holder.findView(R.id.avi);
                ImageView imageScene = holder.findView(R.id.image_scene);
                imageScene.setVisibility(View.GONE);
                avi.setVisibility(View.GONE);
                imageIcon.setImageResource(SmartHomeUtils.typeToIcon(true, deviceType.getDeviceType()));
                tvTxt.setText(SmartHomeUtils.typeToNikeName(true, deviceType.getDeviceType()));
            }
        };
        recyclerSubDevice.setAdapter(mSubAdapter);
        recyclerSubDevice.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                final DeviceType deviceType = subList.get(position);
                Bundle paramBundle = new Bundle();
                paramBundle.putInt(Constant.TYPE, deviceType.getDeviceType());
                openActivity(ConfigurationWizardActivity.class, paramBundle);
            }
        });
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        boolean isSub = bundle.getBoolean(Constant.IS_SUB, false);
        if (isSub) {
            recyclerWifiDevice.setVisibility(View.GONE);
        }
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW, ""));
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW, ""));
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY, ""));
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN, ""));
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN, ""));
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_AIR, ""));
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_GAS, ""));
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_IPC, ""));
        wifiList.add(new DeviceType(false, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_WIFI_RC, ""));


        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN, ""));
        subList.add(new DeviceType(true, DeviceType.ITEM_TYPE_WIFI_DEVICE, Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN, ""));
        mSubAdapter.notifyDataSetChanged();
        mWifiAdapter.notifyDataSetChanged();
    }

}
