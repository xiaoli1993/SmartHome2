package com.heiman.gateway;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.AESKey;
import com.heiman.baselibrary.mode.HeimanSet;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.datacom.aes.AES128Utils;
import com.heiman.gateway.modle.SmartLinkS;
import com.heiman.utils.HexDump;
import com.heiman.utils.UsefullUtill;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.longthink.api.LTLink;

import org.apache.http.Header;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
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
 * @Time :  2017/5/10 15:51
 * @Description :
 * @Modify record :
 */

public class LTLinkActivity extends GwBaseActivity {
    private static int RECV_PORT = 8001;

    private TextView tvSsid;
    private Button btnPeizhi;
    private Button btnecho;
    private Button btnechos;
    private boolean isStart = true;
    private RecvThread mReceiver;
    private boolean isAdd = false;
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private Timer timethread = new Timer();
    private SmartLinkS smartLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltllink);
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
//        showContenView(false);
//        showResetView(true);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
        titleBarTitle.setText(getResources().getString(R.string.Home));
        titleBarTitle.setTextColor(getResources().getColor(R.color.class_V));

        tvSsid = (TextView) findViewById(R.id.tv_ssid);
        btnPeizhi = (Button) findViewById(R.id.btn_peizhi);
        btnecho = (Button) findViewById(R.id.btn_eco);
        btnechos = (Button) findViewById(R.id.btn_ecos);
        btnecho.setOnClickListener(this);
        btnechos.setOnClickListener(this);

        tvSsid.setText(SmartHomeUtils.getSSid(LTLinkActivity.this) + "");
        btnPeizhi.setOnClickListener(this);
        getPwdSmartlink().setText("heiman2016");
        getPwdSmartlink().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                LTLink.getInstance().startGuidance(SmartlinkActivity.this);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mReceiver = new RecvThread();
        mReceiver.start();
    }


    private EditText getPwdSmartlink() {
        return (EditText) findViewById(R.id.pwd_smartlink);
    }

    //收数据
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            BaseApplication.getLogger().i("收到数据\\n");
            switch (msg.what) {
                case 3: {

                    DatagramPacket packet = (DatagramPacket) msg.obj;
                    String res = null;
                    try {
                        BaseApplication.getLogger().d("smartLink1:\t" + HexDump.toHexString(packet.getData()));
                        res = new String(packet.getData(), "UTF-8");
                        BaseApplication.getLogger().d("smartLink1:\t" + res);
                        res = res.substring(1, res.indexOf("&"));
                        try {
                            BaseApplication.getLogger().d("smartLink1");
                            Gson gson = new Gson();
                            BaseApplication.getLogger().d("smartLink2" + res);
                            smartLink = gson.fromJson(res, SmartLinkS.class);
                            BaseApplication.getLogger().d("smartLink" + gson.toJson(smartLink).toString());
                            timethread.schedule(task, 1000, 3000); // 启动timer
                        } catch (Exception e) {

                        }
                        BaseApplication.getLogger().d("TEST", "IP:" + packet.getAddress().toString() + "MAC:" + res);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


//                    int  offset=tview.getLineCount()*tview.getLineHeight();
//                    if(offset>tview.getHeight()){
//                        tview.scrollTo(0,offset-tview.getHeight());
//                    }
                }
                break;
                default:
//                    DoLink(msg.what);
                    break;
            }
        }
    };
    TimerTask task = new TimerTask() {
        public void run() {
            if (!isAdd) {
                scanDevice(smartLink);
            }
        }
    };

    private void scanDevice(final SmartLinkS smartLink) {
        XlinkAgent.getInstance().scanDeviceByProductId(Constant.ZIGBEE_H1GW_NEW_PRODUCTID, new ScanDeviceListener() {
            @Override
            public void onGotDeviceByScan(XDevice xDevice) {
                BaseApplication.getLogger().d("no!smartLink" + xDevice.getMacAddress() + "\tkey:" + xDevice.getAccessKey() + "\tsubkey:" + xDevice.getSubKey());
                if (xDevice.getMacAddress().equals(smartLink.getDevice().getMacAddress().toUpperCase())) {
                    LTLink.getInstance().stopLink();
                    mReceiver.runStop();
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
            }
        });
    }

    private class RecvThread extends Thread {
        private boolean threadFlag;
        private DatagramSocket mSocket;

        public RecvThread() {

        }

        public void start() {
            threadFlag = true;

            if (mSocket == null) {
                try {

                    mSocket = new DatagramSocket(RECV_PORT);

                    mSocket.setSoTimeout(1000);

                } catch (SocketException e) {
                    BaseApplication.getLogger().e("TEST", e.getMessage());
                    e.printStackTrace();
                    return;
                }
            }
            super.start();
        }

        public void runStop() {
            threadFlag = false;
        }

        public void run() {
            byte[] buf = new byte[1024];

            while (threadFlag) {
                try {

                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    mSocket.receive(packet);
                    BaseApplication.getLogger().i("OK：" + packet.getData());
                    mHandler.sendMessage(mHandler.obtainMessage(3, packet));

                } catch (SocketTimeoutException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
        }
    }

    @Override
    public void onClickListener(View v) {
        int i = v.getId();
        if (i == R.id.btn_peizhi) {
//            timethread.schedule(task, 0, 500); // 启动timer
            showHUDProgress();
            if (isStart) {
                BaseApplication.getLogger().i("密码:" + getPwdSmartlink().getText().toString());
                LTLink.getInstance().startLink(null, getPwdSmartlink().getText().toString(), null, LTLinkActivity.this);
                isStart = false;
            } else {
                LTLink.getInstance().stopLink();
                isStart = true;
            }
        } else if (i == R.id.btn_eco) {
            List<String> OID = new ArrayList<String>();
            OID.add(HeimanCom.COM_GW_OID.GET_AES_KEY);
            String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 0, OID);
            BaseApplication.getLogger().json(sb);
            sendData(sb, false);
        } else if (i == R.id.btn_ecos) {
//            List<String> OID = new ArrayList<String>();
//            OID.add(HeimanCom.COM_GW_OID.GW_NAME);
//            OID.add(HeimanCom.COM_GW_OID.DEVICE_BASIC_INFORMATION);
//            String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 1, OID);
////            device.setAesKey("1234567890abcdf");
//            sendData(sb);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //execute the task
                    String sb = HeimanCom.setTimeZone(SmartPlug.mgetSN(), 1, "+6.30");
                    BaseApplication.getLogger().json(sb);
//                    sendData(sb);
                    HeimanSet.PLBean.GwBasicOID gwBasicOID = new HeimanSet.PLBean.GwBasicOID();
                    gwBasicOID.setAlarmlevel(10);
                    BaseApplication.getLogger().json(HeimanCom.setBasic(SmartPlug.mgetSN(), 1, gwBasicOID));
                    BaseApplication.getLogger().json(HeimanCom.setDeviceName(SmartPlug.mgetSN(), 1, "92929"));
                    BaseApplication.getLogger().json(HeimanCom.setJoinSub(SmartPlug.mgetSN(), 1, 2));


                }
            }, 1000);
        }
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
    public void deviceData(String dataString) {
        BaseApplication.getLogger().json(dataString);
        Gson gson = new Gson();
        AESKey aesKey = gson.fromJson(dataString, AESKey.class);
        try {
            BaseApplication.getLogger().i("aesKeyb:" + aesKey.getPL().getAesKey());
            String aesKeyb = AES128Utils.HmDecrypt(aesKey.getPL().getAesKey(), Constant.ZIGBEE_H1GW_NEW_KEY);
            BaseApplication.getLogger().i("aesKeyb:" + Arrays.toString(aesKeyb.getBytes()) + "\t秘钥字符串：" + aesKeyb
                    + "\n" + UsefullUtill.bytesToHxString(aesKeyb.getBytes())

            );
            device.setAesKey(aesKeyb);
            DeviceManage.getInstance().addDevice(device);
        } catch (Exception e) {
//            e.printStackTrace();
        }

//        List<String> OID = new ArrayList<String>();
//        OID.add(HeimanCom.COM_GW_OID.GW_NAME);
//        OID.add(HeimanCom.COM_GW_OID.DEVICE_BASIC_INFORMATION);
//        String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 1, OID);
//        BaseApplication.getLogger().json(sb);
//        sendData(sb);

    }
}
