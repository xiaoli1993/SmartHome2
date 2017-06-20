package com.heiman.gateway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.HeimanSet.PLBean.GwBasicOID;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.ZigbeeNewDevice;
import com.heiman.gateway.fragment.GwAutomationFragment;
import com.heiman.gateway.fragment.GwHomeFragment;
import com.heiman.widget.segmentcontrol.SegmentControl;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/9 13:57
 * @Description :
 * @Modify record :
 */

public class GwActivity extends GwBaseActivity {
    private FrameLayout layFragme;
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private SegmentControl titleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private Fragment mTab01;
    private Fragment mTab02;
    public static GwActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.gateway_activity_gw);
        initView();
        initEven();
        setSelect(0);
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    private void initEven() {
        titleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle paramBundle = new Bundle();
                paramBundle.putBoolean(Constant.IS_DEVICE, true);
                paramBundle.putString(Constant.DEVICE_MAC, device.getDeviceMac());
                paramBundle.putBoolean(Constant.IS_SUB, false);
                openActivity(GwMoreMenuActivity.class, paramBundle);
            }
        });
        titleBarShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClickTopShareImg(v);
            }
        });
        titleBarTitle.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                setSelect(index);
            }
        });
    }

    private void initView() {
        layFragme = (FrameLayout) findViewById(R.id.layFragme);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (SegmentControl) findViewById(R.id.title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
    }

    @Override
    public void deviceData(String dataString) {
        BaseApplication.getLogger().json(dataString);
        Gson gson = new Gson();
        try {
            ZigbeeNewDevice zigbeeNewDevice = gson.fromJson(dataString, ZigbeeNewDevice.class);
            SubDevice subDevice = new SubDevice();
            subDevice.setDeviceMac(device.getDeviceMac());
            List<ZigbeeNewDevice.PLBean.ZigbeeListBean.SubDeviceBean> subDeviceBeanList = zigbeeNewDevice.getPL().getZigbeeList().getSubDevice();
            for (int i = 0; i < subDeviceBeanList.size(); i++) {
                subDevice.setZigbeeMac(subDeviceBeanList.get(i).getDeviceMac());
                subDevice.setDeviceName(subDeviceBeanList.get(i).getDeviceName());
                subDevice.setRoomID(subDeviceBeanList.get(i).getRoomID());
                subDevice.setIndex(subDeviceBeanList.get(i).getIndex());
                subDevice.setDeviceType(subDeviceBeanList.get(i).getDeviceType());
                SubDeviceManage.getInstance().addDevice(subDevice);
            }
        } catch (Exception e) {

        }
        try {
            JSONObject jsonObject = new JSONObject(dataString);
            JSONObject PL = jsonObject.getJSONObject("PL");
            BaseApplication.getLogger().i(PL.toString());
            JSONObject OID = PL.getJSONObject(HeimanCom.COM_GW_OID.GW_BASIC_INFORMATION);
            GwBasicOID gwBasicOID = gson.fromJson(OID.toString(), GwBasicOID.class);
            device.setArmtype(gwBasicOID.getArmtype());
            device.setAlarmlevel(gwBasicOID.getAlarmlevel());
            device.setSoundlevel(gwBasicOID.getSoundlevel());
            device.setBetimer(gwBasicOID.getBetimer());
            device.setGwlanguage(gwBasicOID.getGwlanguage());
            device.setGwlightlevel(gwBasicOID.getGwlightlevel());
            device.setGwlightonoff(gwBasicOID.getGwlightonoff());
            device.setLgtimer(gwBasicOID.getLgtimer());
            DeviceManage.getInstance().addDevice(device);
        } catch (Exception e) {

        }

    }

    @Override
    public void onClickListener(View v) {

    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new GwHomeFragment();
                    transaction.add(R.id.layFragme, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new GwAutomationFragment();
                    transaction.add(R.id.layFragme, mTab02);
                } else {
                    transaction.show(mTab02);

                }
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
    }

}
