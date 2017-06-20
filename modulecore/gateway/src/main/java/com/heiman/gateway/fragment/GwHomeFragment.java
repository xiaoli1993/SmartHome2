package com.heiman.gateway.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.BaseFragment;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.SensorSet;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.THPSet;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.gateway.GwActivity;
import com.heiman.gateway.GwControlActivity;
import com.heiman.gateway.R;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;


/**
 * @Author : 肖力
 * @Time :  2017/5/9 13:57
 * @Description :
 * @Modify record :
 */
public class GwHomeFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeLayout;
    private LinearLayout btnHome;
    private TextView tvHome;
    private LinearLayout btnAway;
    private TextView tvAway;
    private LinearLayout btnDisarm;
    private TextView tvDisarm;
    private FamiliarRecyclerView recyclerDevice;
    private List<SubDevice> subDeviceList;
    private FamiliarEasyAdapter<SubDevice> mAdapter;


    private LinearLayout llGw;
    private ImageView imageDevice;
    private ImageView imgNew;
    private TextView deviceState;
    private TextView deviceName;
    private TextView deviceId;
    private TextView deviceOffline;
    private ImageView imageArrowRight;
    private ImageView lowbattery;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gateway_fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEven();
        initData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.DATA_SUBDEVICE);
        try {
            isRegisterBroadcast = true;
            activity.registerReceiver(mBroadcastReceiver, myIntentFilter);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getGwData();
    }

    private boolean isRegisterBroadcast = false;

    @Override
    public void onDestroy() {
        if (isRegisterBroadcast) {
            getActivity().unregisterReceiver(mBroadcastReceiver);
        }
        super.onDestroy();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.DATA_SUBDEVICE)) {
                BaseApplication.getLogger().i("接收广播");
                initData();
            }
        }
    };

    private void initEven() {
        subDeviceList = SubDeviceManage.getInstance().getDevices();
        mAdapter = new FamiliarEasyAdapter<SubDevice>(getActivity(), R.layout.gateway_item_sub_device_list, subDeviceList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                final SubDevice subDevice = subDeviceList.get(position);
                BaseApplication.getLogger().i("这里有设备:" + subDevice.getDeviceName() + subDevice.getIndex());
                ImageView imageDevice = (ImageView) holder.findView(R.id.image_device);
                ImageView imgNew = (ImageView) holder.findView(R.id.img_new);
                TextView deviceState = (TextView) holder.findView(R.id.device_state);
                TextView deviceName = (TextView) holder.findView(R.id.device_name);
                TextView deviceId = (TextView) holder.findView(R.id.device_id);
                TextView deviceOffline = (TextView) holder.findView(R.id.device_offline);
                ImageView lowbattery = (ImageView) holder.findView(R.id.lowbattery);

                deviceName.setText(subDevice.getDeviceName());
                imageDevice.setImageResource(SmartHomeUtils.typeToIcon(true, subDevice.getDeviceType()));

            }
        };
        recyclerDevice.setAdapter(mAdapter);
        recyclerDevice.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                SubDevice subDevice = subDeviceList.get(position);
                onClickDevice(subDevice);
            }
        });

        //改变加载显示的颜色
        swipeLayout.setColorSchemeColors(Color.RED, Color.RED);
//        //设置背景颜色
//        swipeRefreshLayout.setBackgroundColor(Color.YELLOW);
        //设置初始时的大小
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //设置监听
        swipeLayout.setOnRefreshListener(this);
        //设置向下拉多少出现刷新
        swipeLayout.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        swipeLayout.setProgressViewEndTarget(false, 200);

        deviceName.setText(((GwActivity) getActivity()).device.getDeviceName());
        imageDevice.setImageResource(SmartHomeUtils.typeToIcon(false, ((GwActivity) getActivity()).device.getDeviceType()));
        deviceState.setText("总共有" + (subDeviceList.size() - 1) + "个子设备");

    }

    private void onClickDevice(SubDevice subDevice) {
        switch (subDevice.getDeviceType()) {
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RGB:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_DOORS:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_WATER:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PIR:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SMOKE:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_GAS:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_CO:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOS:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SW:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_PLUGIN:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ONE_ONOFF:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_TWO_ONOFF:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THREE_ONOFF:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RC:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_RELAY:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_SOUND_AND_LIGHT_ALARM:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_ILLUMINANCE:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_AIR:
                break;
            case Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THERMOSTAT:
                break;
        }
    }

    private void initData() {
        subDeviceList.clear();
        subDeviceList = SubDeviceManage.getInstance().getDevices();
        mAdapter.notifyDataSetChanged();
        deviceName.setText(((GwActivity) getActivity()).device.getDeviceName());
        imageDevice.setImageResource(SmartHomeUtils.typeToIcon(false, ((GwActivity) getActivity()).device.getDeviceType()));
        deviceState.setText("总共有" + (subDeviceList.size() - 1) + "个子设备");
        BaseApplication.getLogger().i("刷新");
    }

    private void initView(View view) {

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        btnHome = (LinearLayout) view.findViewById(R.id.btn_home);
        view.findViewById(R.id.image_home).setOnClickListener(this);
        tvHome = (TextView) view.findViewById(R.id.tv_home);
        btnAway = (LinearLayout) view.findViewById(R.id.btn_away);
        view.findViewById(R.id.image_away).setOnClickListener(this);
        tvAway = (TextView) view.findViewById(R.id.tv_away);
        btnDisarm = (LinearLayout) view.findViewById(R.id.btn_disarm);
        view.findViewById(R.id.image_disarm).setOnClickListener(this);
        tvDisarm = (TextView) view.findViewById(R.id.tv_disarm);
        recyclerDevice = (FamiliarRecyclerView) view.findViewById(R.id.recycler_device);
        view.findViewById(R.id.image_Left).setOnClickListener(this);


        view.findViewById(R.id.ll_gw).setOnClickListener(this);
        imageDevice = (ImageView) view.findViewById(R.id.image_device);
        imgNew = (ImageView) view.findViewById(R.id.img_new);
        deviceState = (TextView) view.findViewById(R.id.device_state);
        deviceName = (TextView) view.findViewById(R.id.device_name);
        deviceId = (TextView) view.findViewById(R.id.device_id);
        deviceOffline = (TextView) view.findViewById(R.id.device_offline);
        imageArrowRight = (ImageView) view.findViewById(R.id.image_arrow_right);
        lowbattery = (ImageView) view.findViewById(R.id.lowbattery);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.image_home) {//TODO implement
//            List<String> OID = new ArrayList<String>();
//            OID.add(HeimanCom.COM_GW_OID.GW_SUB_SS);
            String oid = HeimanCom.setJoinSub(SmartPlug.mgetSN(), 0, 1);
            BaseApplication.getLogger().json(oid);
            ((GwActivity) getActivity()).sendData(oid);
        } else if (i == R.id.image_away) {//TODO implement
            List<SubDevice> subDeviceList = SubDeviceManage.getInstance().getDevices();
            for (int x = 0; x < subDeviceList.size(); x++) {
                SubDevice subDevice = subDeviceList.get(x);
                List<String> OID = new ArrayList<String>();
                String SUBOID = HeimanCom.COM_GW_OID.SET_SUB.replace("y1", SmartHomeUtils.typeToY1(subDevice.getDeviceType()) + "")
                        .replace("y2", SmartHomeUtils.typeToY2(subDevice.getDeviceType()) + "")
                        .replace("index", subDevice.getIndex() + "");
                OID.add(SUBOID);
                String oid = HeimanCom.getOID(SmartPlug.mgetSN(), 0, OID);
                BaseApplication.getLogger().json(oid);
                ((GwActivity) getActivity()).sendData(oid);
            }
        } else if (i == R.id.image_disarm) {//TODO implement
            List<SubDevice> subDeviceList = SubDeviceManage.getInstance().getDevices();
            for (int x = 0; x < subDeviceList.size(); x++) {
                SubDevice subDevice = subDeviceList.get(x);
                BaseApplication.getLogger().i("DEVICE:" + subDevice.getZigbeeMac());
                if (subDevice.getDeviceType() == 17 || subDevice.getDeviceType() == 19) {
                    SensorSet sensorSet = new SensorSet();
                    sensorSet.setEh(13);
                    sensorSet.setEm(12);
                    sensorSet.setSh(12);
                    sensorSet.setSm(12);
                    sensorSet.setWf(255);
                    String oid = HeimanCom.setSubData(SmartPlug.mgetSN(), 0, subDevice.getDeviceType(), subDevice.getIndex() + "", HeimanCom.getSerSorSeting(sensorSet));
                    BaseApplication.getLogger().json(oid);
                    ((GwActivity) getActivity()).sendData(oid);
                } else if (subDevice.getDeviceType() == 21) {
                    THPSet thpSet = new THPSet();
                    thpSet.setH_cklow("50");
                    thpSet.setH_ckup("12");
                    thpSet.setH_ckvalid(1);
                    thpSet.setT_cklow("13");
                    thpSet.setT_ckup("12");
                    thpSet.setT_ckvalid(0);
                    String oid = HeimanCom.setSubData(SmartPlug.mgetSN(), 0, subDevice.getDeviceType(), subDevice.getIndex() + "", HeimanCom.getTHPSeting(thpSet));
                    BaseApplication.getLogger().json(oid);
                    ((GwActivity) getActivity()).sendData(oid);
                }
            }
        } else if (i == R.id.image_Left) {
            Bundle paramBundle = new Bundle();
            paramBundle.putBoolean(Constant.IS_SUB, true);
            startActivityForName("com.heiman.smarthome.activity.AddDeviceActivity", paramBundle);
        } else if (i == R.id.ll_gw) {
            Bundle paramBundle = new Bundle();
            paramBundle.putBoolean(Constant.IS_DEVICE, true);
            paramBundle.putString(Constant.DEVICE_MAC, ((GwActivity) getActivity()).device.getDeviceMac());
            paramBundle.putBoolean(Constant.IS_SUB, false);
            openActivity(GwControlActivity.class, paramBundle);
        }

    }


    private void getGwData() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                List<String> OID = new ArrayList<String>();
                OID.add(HeimanCom.COM_GW_OID.GW_BASIC_INFORMATION);
//        OID.add(HeimanCom.COM_GW_OID.GW_SUB);
//        OID.add(HeimanCom.COM_GW_OID.GW_SUB_SS);
                String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 1, OID);
                BaseApplication.getLogger().json(sb);
                ((GwActivity) getActivity()).sendData(sb);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                List<String> OID = new ArrayList<String>();
                OID.add(HeimanCom.COM_GW_OID.GW_SUB);
//        OID.add(HeimanCom.COM_GW_OID.GW_SUB_SS);
                String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 1, OID);
                BaseApplication.getLogger().json(sb);
                ((GwActivity) getActivity()).sendData(sb);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                List<String> OID = new ArrayList<String>();
                OID.add(HeimanCom.COM_GW_OID.GW_SUB_SS);
                String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 1, OID);
                BaseApplication.getLogger().json(sb);
                ((GwActivity) getActivity()).sendData(sb);
            }
        }, 1000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //然刷新控件停留两秒后消失
                    Thread.sleep(2000);
                    handler.post(new Runnable() {//在主线程执行
                        @Override
                        public void run() {
                            //更新数据
                            getGwData();
                            //停止刷新
                            swipeLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
