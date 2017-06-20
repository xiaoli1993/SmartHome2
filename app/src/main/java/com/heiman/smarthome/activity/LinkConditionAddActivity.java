package com.heiman.smarthome.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.devicecommon.TimerActivity;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.adapter.DeviceAdapter;
import com.heiman.smarthome.adapter.LaunchConditionAdapter;
import com.heiman.smarthome.view.DeviceActionDecoration;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkConditionAddActivity extends BaseActivity implements View.OnClickListener{

    private TextView titleBarTitle;
    private View viewTopLine;
    private PopupWindow mPopupWindow;
    private EditText editDeviceSearch;
    private SwipeMenuRecyclerView recyclerDevices;
    private RelativeLayout rlTimeContainer;
    private ImageView imgTimingIcon;
    private TextView txtTiming;
    private ImageView imgTimingIconRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_link_condition_add);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        viewTopLine = findViewById(R.id.view_top_line);
        editDeviceSearch = (EditText) findViewById(R.id.edit_device_search);
        recyclerDevices = (SwipeMenuRecyclerView) findViewById(R.id.recycler_intelligent_devices);
        rlTimeContainer = (RelativeLayout) findViewById(R.id.rl_timing_container);
        imgTimingIcon = (ImageView) findViewById(R.id.img_icon_timing);
        txtTiming = (TextView) findViewById(R.id.txt_timing);
        imgTimingIconRight = (ImageView) findViewById(R.id.img_icon_timing_right);

        titleBarTitle.setOnClickListener(this);
        editDeviceSearch.setOnClickListener(this);
        rlTimeContainer.setOnClickListener(this);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.tittle_launch_condition));
        setReturnImage(R.drawable.personal_back);
        titleBarTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.homedrop, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDevices();
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_link_condition_add, 500)) {
            MyApplication.getLogger().e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.title_bar_title:
                showLaunchCondition();
                break;
            case R.id.txt_launch_condition:
                if (v.getTag() != null) {
                    LogUtil.e("点击：" + v.getTag().toString());
                }
                mPopupWindow.dismiss();
                break;
            case R.id.edit_device_search:
                LogUtil.e("点击搜索！");
                break;
            case R.id.txt_device_name:
            case R.id.img_icon_device:
            case R.id.img_icon_right:
                if (v.getTag() instanceof XlinkDevice) {
                    XlinkDevice device = (XlinkDevice) v.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putString("device", UsefullUtill.getJSONStr(device));
                    openActivity(DeviceExecActivity.class, bundle);
                }
                break;
            case R.id.rl_timing_container:
                LogUtil.e("click timing!");
                openActivity(TimerActivity.class);
                break;

        }
    }

    private void showLaunchCondition() {
        View popupView = getLayoutInflater().inflate(R.layout.layout_launch_condition, null);
        SwipeMenuRecyclerView recyclerCondition = (SwipeMenuRecyclerView) popupView.findViewById(R.id.recycler_conditions);
        List<String> conditions = Arrays.asList(getResources().getStringArray(R.array.default_condition));
        LogUtil.e("conditions:" + conditions.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LaunchConditionAdapter launchConditionAdapter = new LaunchConditionAdapter(this, conditions, this);
        recyclerCondition.addItemDecoration(new DeviceActionDecoration());
        recyclerCondition.setAdapter(launchConditionAdapter);
        recyclerCondition.setLayoutManager(linearLayoutManager);

        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPopupWindow.showAsDropDown(viewTopLine);
    }

    private void initDevices() {
        int[] wifiDeviceTypes = new int[] {Constant.DEVICE_TYPE.DEVICE_WIFI_RC, Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY,
                Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW, Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW,
                Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN, Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN,
                Constant.DEVICE_TYPE.DEVICE_WIFI_AIR, Constant.DEVICE_TYPE.DEVICE_WIFI_GAS};
        List<XlinkDevice> devices = new ArrayList<>();
        for (int i = 0; i < wifiDeviceTypes.length; i++) {
            XlinkDevice device = new XlinkDevice();
            device.setDeviceType(wifiDeviceTypes[i]);
            device.setDeviceName("wifi测试设备" + i);
            device.setDeviceMac(("abccee" + Integer.toHexString(0x100000 + (int)(Math.random() * 0xffff))).toUpperCase());
            device.setProductId("58529141178445228e756abd1341ae1c");
            device.setActive_code("b25944a8c9d7d9a7be4abcca87626dd2");
            device.setId(100019047 + i);
            device.setAuthorize_code("215fbf42ab81cc5c");
            device.setAccessKey("899076");
            device.setxDevice(UsefullUtill.getJSONStr(device));
            devices.add(device);
        }
        LogUtil.e("devices:" + devices.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DeviceAdapter deviceAdapter = new DeviceAdapter(this, devices, this);
        recyclerDevices.addItemDecoration(new DeviceActionDecoration());
        recyclerDevices.setAdapter(deviceAdapter);
        recyclerDevices.setLayoutManager(linearLayoutManager);
    }
}
