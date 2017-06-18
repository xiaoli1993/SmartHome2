package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.adapter.ExecAdapter;
import com.heiman.smarthome.adapter.LaunchConditionAdapter;
import com.heiman.smarthome.view.DeviceActionDecoration;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceExecActivity extends BaseActivity implements View.OnClickListener{

    private TextView txtTitleRight;
    private XlinkDevice device;
    private SwipeMenuRecyclerView recyclerDeviceExec;
    private ExecAdapter execAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_device_exec);
        txtTitleRight = (TextView) findViewById(R.id.title_tv_Right);
        recyclerDeviceExec = (SwipeMenuRecyclerView) findViewById(R.id.recycler_device_exec);

        txtTitleRight.setOnClickListener(this);

        txtTitleRight.setVisibility(View.VISIBLE);
        txtTitleRight.setText(R.string.device_exec_submit);
        txtTitleRight.setTextColor(getResources().getColor(R.color.btn_regist_color));

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.device_name_plug));
        setReturnImage(R.drawable.personal_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            device = UsefullUtill.getModelFromJSONStr(bundle.getString("device"), XlinkDevice.class);

            if (device != null) {
                setTitle(device.getDeviceName());
                initDeviceExec();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_device_exec, 500)) {
            MyApplication.getLogger().e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.title_tv_Right:
                LogUtil.e("点击提交！");
                break;
            case R.id.txt_device_exec:
                if (v.getTag() != null) {
                    String selectedExec = v.getTag().toString();
                    LogUtil.e("选中了：" + selectedExec);
                    execAdapter.setSelectedExec(selectedExec);
                }
                break;
        }
    }

    private void initDeviceExec() {
        List<String> deviceExecs = new ArrayList<>();
        switch (device.getDeviceType()) {
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:  //智能灯泡
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.rgb_execs));
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT: //温控器
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.thermostat_execs));
                break;
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY: //网关
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW:
            case Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW:
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.gateway_execs));
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS: //门磁、红外
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.doors_and_pir_execs));
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM: //声光报警
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.sound_and_light_alarm_execs));
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF: //开关
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.onoff_execs));
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN: //计量插座
            case Constant.DEVICE_TYPE.DEVICE_WIFI_METRTING_PLUGIN:
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.metrting_plugin_execs));
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN: //USB插座、智能插座
            case Constant.DEVICE_TYPE.DEVICE_WIFI_PLUGIN:
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.plugin_execs));
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC: //红外转发、红外转发遥控器
            case Constant.DEVICE_TYPE.DEVICE_WIFI_RC:

                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY: //继电器模块
                deviceExecs = Arrays.asList(getResources().getStringArray(R.array.relay_execs));
                break;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        execAdapter = new ExecAdapter(this, deviceExecs, this);
        recyclerDeviceExec.addItemDecoration(new DeviceActionDecoration());
        recyclerDeviceExec.setAdapter(execAdapter);
        recyclerDeviceExec.setLayoutManager(linearLayoutManager);
    }
}
