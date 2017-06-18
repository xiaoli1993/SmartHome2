package com.heiman.gateway.fragment;

import android.os.Bundle;
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
public class GwHomeFragment extends BaseFragment implements View.OnClickListener {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gateway_fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseApplication.getLogger().i("我卡再这里11");
        initView(view);
        BaseApplication.getLogger().i("我卡再这里12");
        initData();
        BaseApplication.getLogger().i("我卡再这里13");
        initEven();
        BaseApplication.getLogger().i("我卡再这里14");
    }

    private void initEven() {
        mAdapter = new FamiliarEasyAdapter<SubDevice>(getActivity(), R.layout.gateway_item_sub_device_list, subDeviceList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                final SubDevice subDevice = subDeviceList.get(position);

                ImageView imageDevice = (ImageView) holder.findView(R.id.image_device);
                ImageView imgNew = (ImageView) holder.findView(R.id.img_new);
                TextView deviceState = (TextView) holder.findView(R.id.device_state);
                TextView deviceName = (TextView) holder.findView(R.id.device_name);
                TextView deviceId = (TextView) holder.findView(R.id.device_id);
                TextView deviceOffline = (TextView) holder.findView(R.id.device_offline);
                ImageView lowbattery = (ImageView) holder.findView(R.id.lowbattery);

                if (position == 0) {
                    deviceName.setText(((GwActivity) getActivity()).device.getDeviceName());
                    imageDevice.setImageResource(SmartHomeUtils.typeToIcon(false, ((GwActivity) getActivity()).device.getDeviceType()));
                    deviceState.setText("总共有" + (subDeviceList.size() - 1) + "个子设备");
                } else {
                    deviceName.setText(subDevice.getDeviceName());
                    imageDevice.setImageResource(SmartHomeUtils.typeToIcon(true, subDevice.getDeviceType()));
                }

            }
        };
        recyclerDevice.setAdapter(mAdapter);
        recyclerDevice.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                final SubDevice subDevice = subDeviceList.get(position);
                if (position == 0) {
                    Bundle paramBundle = new Bundle();
                    paramBundle.putBoolean(Constant.IS_DEVICE, true);
                    paramBundle.putString(Constant.DEVICE_MAC, ((GwActivity) getActivity()).device.getDeviceMac());
                    paramBundle.putBoolean(Constant.IS_SUB, false);
                    openActivity(GwControlActivity.class, paramBundle);
                } else {
                    switch (subDevice.getDeviceType()) {

                    }
                }
            }
        });
    }

    private void initData() {
        subDeviceList = SubDeviceManage.getInstance().getDevices();
        subDeviceList.add(0, new SubDevice());
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
        }

    }
}
