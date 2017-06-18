package com.heiman.gateway;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.AESKey;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.datacom.aes.AES128Utils;
import com.heiman.utils.UsefullUtill;
import com.longthink.api.LTLink;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.xlink.wifi.sdk.XDevice;
import io.xlink.wifi.sdk.XlinkAgent;
import io.xlink.wifi.sdk.XlinkCode;
import io.xlink.wifi.sdk.listener.ScanDeviceListener;
import io.xlink.wifi.sdk.listener.SetDeviceAccessKeyListener;

/**
 * @Author : 肖力
 * @Time :  2017/6/10 8:24
 * @Description :
 * @Modify record :
 */

public class XlinkScanActivity extends GwBaseActivity {
    private Timer timethread = new Timer();
    private boolean isAdd = false;
    private String gwMac = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltllink);
        timethread.schedule(task, 1000, 3000); // 启动timer
    }

    TimerTask task = new TimerTask() {
        public void run() {
            if (!isAdd) {
                if (SmartHomeUtils.isEmptyString(gwMac)) {
                    scanDevice();
                }
            }
        }
    };

    private void scanDevice() {
        XlinkAgent.getInstance().scanDeviceByProductId(Constant.ZIGBEE_H2GW_PRODUCTID, new ScanDeviceListener() {
            @Override
            public void onGotDeviceByScan(XDevice xDevice) {
                BaseApplication.getLogger().d("no!smartLink" + xDevice.getMacAddress() + "\tkey:" + xDevice.getAccessKey() + "\tsubkey:" + xDevice.getSubKey());
                LTLink.getInstance().stopLink();
                if (timethread != null) {
                    timethread.cancel();
                    timethread = null;
                }
                dismissHUMProgress();
                BaseApplication.getLogger().d("no!smartLink1" + xDevice.getVersion());

                HttpManage.getInstance().registerDevice(BaseApplication.getMyApplication(), xDevice.getProductId(), xDevice.getMacAddress(), xDevice.getMacAddress(), new HttpManage.ResultCallback<String>() {

                    @Override
                    public void onError(Header[] headers, HttpManage.Error error) {
                        BaseApplication.getLogger().d("no!smartLink3\t" + SmartHomeUtils.showHttpCode(error.getCode()));
                    }

                    @Override
                    public void onSuccess(int code, String response) {
                        BaseApplication.getLogger().d("no!smartLink3\t" + response);
                    }
                });
                BaseApplication.getLogger().d("no!smartLink4");
                XlinkAgent.getInstance().initDevice(xDevice);
                XlinkDevice xlinkDevice = new XlinkDevice();
                BaseApplication.getLogger().d("no!smartLink5");
                xlinkDevice.setxDevice(XlinkAgent.deviceToJson(xDevice).toString());
                BaseApplication.getLogger().json(XlinkAgent.deviceToJson(xDevice).toString());
                xlinkDevice.setDeviceType(Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW);
                xlinkDevice.setDeviceMac(xDevice.getMacAddress());
                xlinkDevice.setDeviceName(xDevice.getMacAddress());
                xlinkDevice.setDeviceId(xDevice.getDeviceId());
                xlinkDevice.setDate(new Date());
                xlinkDevice.setProductId(xDevice.getProductId());
                xlinkDevice.setAccessKey(xDevice.getAccessKey() + "");
                xlinkDevice.setDeviceState(0);
                BaseApplication.getLogger().d("no!smartLink6");
                setDevice(xlinkDevice);
                XlinkAgent.getInstance().initDevice(xDevice);
                isDevice = true;
                if (xDevice.getVersion() == 1) {
                    BaseApplication.getLogger().d("no!smartLink7");
                    try {
                        connectDevice();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    DeviceManage.getInstance().addDevice(xlinkDevice);
                    isAdd = true;
                } else if (xDevice.getVersion() >= 2) {
                    BaseApplication.getLogger().d("no!smartLink8");
                    if (xDevice.getAccessKey() > 0) {
                        BaseApplication.getLogger().d("no!smartLink9");
                        boolean isconnect = false;
                        try {
                            isconnect = connectDevice();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DeviceManage.getInstance().addDevice(xlinkDevice);
                        isAdd = true;
                        if (isconnect) {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    //execute the task
                                    List<String> OID = new ArrayList<String>();
                                    OID.add(HeimanCom.COM_GW_OID.GET_AES_KEY);
                                    String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 0, OID);
                                    BaseApplication.getLogger().json(sb);
                                    sendData(sb, false);
                                }
                            }, 1000);
                        }
                    } else {
                        BaseApplication.getLogger().d("no!smartLink10:" + "没设置密码");
                        int codes = Constant.passwrods;
                        int code = XlinkAgent.getInstance().setDeviceAccessKey(
                                xDevice, codes,
                                new SetDeviceAccessKeyListener() {
                                    @Override
                                    public void onSetLocalDeviceAccessKey(XDevice xdevice, int code, int msgId) {
                                        BaseApplication.getLogger().i("code:" + code);
                                        switch (code) {
                                            case XlinkCode.SUCCEED:
                                                device.setAccessKey(xdevice.getAccessKey() + "");
                                                device.setxDevice(XlinkAgent.deviceToJson(xdevice).toString());
                                                BaseApplication.getLogger().i("pwd:" + xdevice.getAccessKey());
                                                DeviceManage.getInstance().addDevice(device);
                                                isAdd = true;
                                                boolean isconnect = false;
                                                try {
                                                    isconnect = connectDevice();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                if (isconnect) {
                                                    new Handler().postDelayed(new Runnable() {
                                                        public void run() {
                                                            //execute the task
                                                            List<String> OID = new ArrayList<String>();
                                                            OID.add(HeimanCom.COM_GW_OID.GET_AES_KEY);
                                                            String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 0, OID);
                                                            BaseApplication.getLogger().json(sb);
                                                            sendData(sb, false);
                                                        }
                                                    }, 1000);
                                                }
                                                break;
                                            default:
                                                break;
                                        }

                                    }
                                });
                        BaseApplication.getLogger().i("code:" + code + "\t" + codes);

                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timethread != null) {// 停止timer
            timethread.cancel();
            timethread = null;
        }
    }

    @Override
    public void onClickListener(View v) {

    }

    @Override
    public void deviceData(String dataString) {
        BaseApplication.getLogger().json(dataString);
        Gson gson = new Gson();
        try {
            AESKey aesKey = gson.fromJson(dataString, AESKey.class);
            BaseApplication.getLogger().i("aesKeyb:" + aesKey.getPL().getAESkey().getKey());
            String aesKeyb = AES128Utils.HmDecrypt(aesKey.getPL().getAESkey().getKey(), Constant.ZIGBEE_H1GW_NEW_KEY);
            BaseApplication.getLogger().i("aesKeyb:" + Arrays.toString(aesKeyb.getBytes())
                    + "\t秘钥字符串：" + aesKeyb
                    + "\n" + UsefullUtill.bytesToHxString(aesKeyb.getBytes()));
            device.setAesKey(aesKeyb);
            DeviceManage.getInstance().addDevice(device);
            finish();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
