package com.heiman.baselibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.DataDevice;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.datacom.aes.AES128Utils;
import com.heiman.widget.swipeback.CloseActivityClass;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import io.xlink.wifi.sdk.XDevice;
import io.xlink.wifi.sdk.XlinkAgent;
import io.xlink.wifi.sdk.XlinkCode;
import io.xlink.wifi.sdk.listener.ConnectDeviceListener;
import io.xlink.wifi.sdk.listener.GetSubscribeKeyListener;
import io.xlink.wifi.sdk.listener.SendPipeListener;
import io.xlink.wifi.sdk.listener.SetDeviceAccessKeyListener;
import io.xlink.wifi.sdk.listener.SubscribeDeviceListener;

/**
 * @Author : 肖力
 * @Time :  2017/5/6 10:18
 * @Description : 设备基类
 * @Modify record :
 */

public abstract class GwBaseActivity extends FragmentActivity implements View.OnClickListener {

    public XlinkDevice device;//设备信息
    public SubDevice subDevice;//子设备信息
    private static final int ACTIVITY_CREATE = 1;
    private static final int ACTIVITY_START = 2;
    private static final int ACTIVITY_RESUME = 3;
    private static final int ACTIVITY_PAUSE = 4;
    private static final int ACTIVITY_STOP = 5;
    private static final int ACTIVITY_DESTROY = 6;
    public int activityState;
    private KProgressHUD hud = null;
    private boolean isRegisterBroadcast = false;
    private boolean isRun;// 界面是否可见
    private String pipeData = null;//保存数据
    public boolean isDevice;

    @Override
    public void onClick(View v) {
        onClickListener(v);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    public abstract void onClickListener(View v);

    /**
     * 通过包名跳转
     *
     * @param activityName
     */
    public void startActivityForName(String activityName) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过包名跳转
     *
     * @param activityName
     */
    public void startActivityForName(String activityName, Bundle paramBundle) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(this, clazz);
            if (paramBundle != null)
                intent.putExtras(paramBundle);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 跳转界面
     *
     * @param paramClass
     */
    protected void openActivity(Class<?> paramClass) {
        BaseApplication.getLogger().e(getClass().getSimpleName(), "openActivity：：" + paramClass.getSimpleName());
        openActivity(paramClass, null);
    }

    protected void openActivity(Class<?> paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent(this, paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }

    protected void openActivity(String paramString) {
        openActivity(paramString, null);
    }

    protected void openActivity(String paramString, Bundle paramBundle) {
        Intent localIntent = new Intent(paramString);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }

    /**
     * 注册广播
     */
    private void initFilter() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.BROADCAST_RECVPIPE);
        myIntentFilter.addAction(Constant.BROADCAST_DEVICE_CHANGED);
        myIntentFilter.addAction(Constant.BROADCAST_DEVICE_SYNC);
        myIntentFilter.addAction(Constant.BROADCAST_RECVPIPE_SYNC);
        isRegisterBroadcast = true;
        // 注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }


    /**
     * 启动服务
     *
     * @param paramClass
     */
    protected void startService(Class<?> paramClass) {
        Intent localIntent = new Intent(this, paramClass);
        startService(localIntent);
    }

    /**
     * 关闭服务
     *
     * @param paramClass
     */
    protected void stopService(Class<?> paramClass) {
        Intent localIntent = new Intent(this, paramClass);
        stopService(localIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityState = ACTIVITY_CREATE;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        CloseActivityClass.activityList.add(this);
        initDeviceData();
        initFilter();
    }

    /**
     * 初始化设备信息
     */
    private void initDeviceData() {
        Bundle bundle = this.getIntent().getExtras();
//        if (bundle != null) {
        isDevice = bundle.getBoolean(Constant.IS_DEVICE, false);
        if (isDevice) {
            //接收name值
            String mac = bundle.getString(Constant.DEVICE_MAC);
            boolean isSub = bundle.getBoolean(Constant.IS_SUB, false);
            if (isSub) {
                String zigbeemac = bundle.getString(Constant.ZIGBEE_MAC);
                subDevice = SubDeviceManage.getInstance().getDevice(mac, zigbeemac);
            }
            BaseApplication.getLogger().e("mac:" + mac + "isSub:" + isSub);
            device = DeviceManage.getInstance().getDevice(mac);
        }
//        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        activityState = ACTIVITY_START;
    }


    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUME;
        isRun = true;
        if (isDevice) {
            BaseApplication.getMyApplication().setCurrentActivity(this);
            if (device.getDeviceState() == 0 && XlinkUtils.isConnected(this)) {
                BaseApplication.getLogger().i("连接设备");
                try {
                    connectDevice();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                deviceData(pipeData);
                pipeData = null;
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;
        isRun = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ACTIVITY_STOP;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROY;
        if (isRegisterBroadcast) {
            unregisterReceiver(mBroadcastReceiver);
        }
    }


    /**
     * 监听的广播
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            BaseApplication.getLogger().v("接收广播:" + isDevice);
            if (isDevice) {
                String action = intent.getAction();
                String mac = intent.getStringExtra(Constant.DEVICE_MAC);
                BaseApplication.getLogger().v("接收广播:" + mac);
                if (mac == null || !mac.equals(device.getDeviceMac())) {
                    return;
                }
                // 收到pipe包
                if (action.equals(Constant.BROADCAST_RECVPIPE)) {
                    String data = intent.getStringExtra(Constant.DATA);
                    BaseApplication.getLogger().i("收到数据：" + data);
                    try {
                        data = AES128Utils.HmDecrypt(data, device.getAesKey());
                        BaseApplication.getLogger().i("收到数据解密：" + data + "\n" + device.getAesKey());
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                    pipeData = data;
                    if (!isRun) { // 当界面不可见，把pipeData存起来，然后等onResume再更新界面
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        int RC = jsonObject.getInt("RC");
                        if (RC <= 0) {
                            XlinkUtils.shortTips(BaseApplication.getMyApplication(), SmartHomeUtils.showRcCode(RC), getResources().getColor(R.color.class_J), getResources().getColor(R.color.white), 0, false);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    deviceData(data);
                } else if (action.equals(Constant.BROADCAST_RECVPIPE_SYNC)) {
                    String data = intent.getStringExtra(Constant.DATA);
                    BaseApplication.getLogger().i("收到SYNC数据：" + data);
                    try {
                        data = AES128Utils.HmDecrypt(data, device.getAesKey());
                        BaseApplication.getLogger().i("收到SYNC数据：" + data + "\n" + device.getAesKey());
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                    pipeData = data;
                    if (!isRun) { // 当界面不可见，把pipeData存起来，然后等onResume再更新界面
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        int RC = jsonObject.getInt("RC");
                        if (RC <= 0) {
                            XlinkUtils.shortTips(BaseApplication.getMyApplication(), SmartHomeUtils.showRcCode(RC), getResources().getColor(R.color.class_J), getResources().getColor(R.color.white), 0, false);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    deviceData(data);
                } else if (action.equals(Constant.BROADCAST_DEVICE_CHANGED)) {
                    int status = intent.getIntExtra(Constant.STATUS, -1);
                    if (status == XlinkCode.DEVICE_CHANGED_CONNECTING) {
                        BaseApplication.getLogger().i("正在重连设备...");
                    } else if (status == XlinkCode.DEVICE_CHANGED_CONNECT_SUCCEED) {
                        device.setDeviceState(1);
                        DeviceManage.getInstance().updateDevice(device);
                        BaseApplication.getLogger().i("连接设备成功");
                    } else if (status == XlinkCode.DEVICE_CHANGED_OFFLINE) {
                        device.setDeviceState(0);
                        DeviceManage.getInstance().updateDevice(device);
                        BaseApplication.getLogger().e("连接设备失败");
                    }
                } // sync
                else if (action.equals(Constant.BROADCAST_DEVICE_SYNC)) {

                } else if (action.equals(Constant.BROADCAST_EXIT)) {
                    finish();
                }
            }
        }
    };

    public abstract void deviceData(String dataString);

    /**
     * 显示HUM等待
     */
    public void showHUDProgress() {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).show();
        }
    }

    public void showHUDProgress(String message) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setCancellable(true).show();
        }
    }

    public void showHUDProgress(String message, String DetailsLabel) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(message)
                    .setDetailsLabel(DetailsLabel).show();
        }
    }

    public void showHUDProgress(View imageView, String message) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setCustomView(imageView)
                    .setLabel(message).show();
        }
    }

    public void showHUDProgress(int color, String message) {
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(color)
                .setLabel(message)
                .setAnimationSpeed(2).show();
    }

    public void showHUDProgressUpdata(String message) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel(message).show();
        }
    }

    public void showHUDProgressUpdata(String message, String DetailsLabel) {
        if (hud == null) {
            hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                    .setLabel(message)
                    .setDetailsLabel(DetailsLabel).show();
        }
    }

    public void setHUMMAXProgressUpdata(int max) {
        if (hud != null) {
            hud.setMaxProgress(max);
        }
    }

    public void setHUMProgressUpdata(int currentProgress) {
        if (hud != null) {
            hud.setProgress(currentProgress);
        }
    }

    public void setHUMLabel(String label) {
        if (hud != null) {
            hud.setLabel(label);
        }
    }

    public void dismissHUMProgress() {
        if (hud != null) {
            hud.dismiss();
            hud = null;
        }
    }

    /**
     * 连接设备
     */
    public boolean connectDevice() throws JSONException {
        if (device.getDeviceState() != 0) {
            return true;
        }
        int ret = 0;
        //V3版本获取SUBKEY
        if ((device.getxDevice().getVersion() >= 3) && (device.getxDevice().getSubKey() <= 0)) {
            BaseApplication.getLogger().i("V3版本获取SUBKEY:" + device.getxDevice().getMacAddress() + " " + device.getxDevice().getSubKey() + "\t" + device.getxDevice().getAccessKey());
            XlinkAgent.getInstance().getInstance().getDeviceSubscribeKey(device.getxDevice(), device.getxDevice().getAccessKey(), new GetSubscribeKeyListener() {
                @Override
                public void onGetSubscribekey(XDevice xdevice, int code, int subKey) {
                    BaseApplication.getLogger().i("sss:" + xdevice.getAccessKey() + "sce:" + xdevice.getSubKey());
                    device.setxDevice(XlinkAgent.deviceToJson(xdevice).toString());
                    DeviceManage.getInstance().updateDevice(device);
                }
            });
        }
        //订阅设备,V3版本设备开始使用subKey订阅设备。
        XlinkAgent.getInstance().subscribeDevice(device.getxDevice(), device.getxDevice().getSubKey(), new SubscribeDeviceListener() {
            @Override
            public void onSubscribeDevice(XDevice xdevice, int code) {
                BaseApplication.getLogger().i("绑定：" + code);
                if (code == XlinkCode.SUCCEED) {
//                    device.setSubscribe(true);
                }
            }
        });
        ret = XlinkAgent.getInstance().connectDevice(device.getxDevice(), device.getxDevice().getAccessKey(), device.getxDevice().getSubKey(), connectDeviceListener);
        BaseApplication.getLogger().i("返回连接：" + ret + "\tkey:" + device.getxDevice().getAccessKey() + "\tsub:" + device.getxDevice().getSubKey());
        if (ret < 0) {// 调用设备失败
            device.setDeviceState(0);
            switch (ret) {
                case XlinkCode.INVALID_DEVICE_ID:
//                    XlinkUtils.shortTips("无效的设备ID，请先联网激活设备");
                    break;
                case XlinkCode.NO_CONNECT_SERVER:
//                    XlinkUtils.shortTips("连接设备失败，手机未连接服务器");
                    if (XlinkUtils.isConnected(this)) {
                        int appid = Hawk.get(Constant.APPID);
                        String authKey = Hawk.get(Constant.AUTHKEY);
                        XlinkAgent.getInstance().start();
                        XlinkAgent.getInstance().login(appid, authKey);
                    }
                    break;
                case XlinkCode.NETWORD_UNAVAILABLE:
//                    XlinkUtils.shortTips("当前网络不可用,无法连接设备");
                    break;
                case XlinkCode.NO_DEVICE:
//                    XlinkUtils.shortTips("未找到设备");
                    try {
                        XlinkAgent.getInstance().initDevice(device.getxDevice());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                // 重复调用了连接设备接口
                case XlinkCode.ALREADY_EXIST:
//                    XlinkUtils.shortTips("重复调用");
                    break;
                default:
//                    XlinkUtils.shortTips("连接设备" + device.getMacAddress() + "失败:" + ret);
                    break;
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 连接设备回调。该回调在主程序，可直接更改ui
     */
    private ConnectDeviceListener connectDeviceListener = new ConnectDeviceListener() {

        @Override
        public void onConnectDevice(XDevice xDevice, int result) {
            switch (result) {
                // 连接设备成功 设备处于内网
                case XlinkCode.DEVICE_STATE_LOCAL_LINK:
                    // 连接设备成功，成功后
                    device.setDeviceState(2);
                    DeviceManage.getInstance().updateDevice(device);
                    XlinkAgent.getInstance().sendProbe(xDevice);
                    BaseApplication.getLogger().i("局域网通讯中" + xDevice.getMacAddress());
                    break;
                // 连接设备成功 设备处于云端
                case XlinkCode.DEVICE_STATE_OUTER_LINK:
                    device.setDeviceState(1);
                    DeviceManage.getInstance().updateDevice(device);
                    DeviceManage.getInstance().addDevice(device);
                    BaseApplication.getLogger().i("云端通讯中" + xDevice.getMacAddress());
                    break;
                // 设备授权码错误
                case XlinkCode.CONNECT_DEVICE_INVALID_KEY:
                    device.setDeviceState(0);
                    openDevicePassword(xDevice);
                    BaseApplication.getLogger().e("Device:" + xDevice.getMacAddress() + "设备认证失败");
                    break;
                // 设备不在线
                case XlinkCode.CONNECT_DEVICE_OFFLINE:
                    device.setDeviceState(0);
                    BaseApplication.getLogger().e("设备不在线");
                    break;
                // 连接设备超时了，（设备未应答，或者服务器未应答）
                case XlinkCode.CONNECT_DEVICE_TIMEOUT:
                    device.setDeviceState(0);
                    // Log.e(TAG, "Device:" + xDevice.getMacAddress() + "连接设备超时");
                    BaseApplication.getLogger().e("连接设备超时");
                    break;
                case XlinkCode.CONNECT_DEVICE_SERVER_ERROR:
                    device.setDeviceState(0);
                    BaseApplication.getLogger().e("连接设备失败，服务器内部错误");
                    break;
                case XlinkCode.CONNECT_DEVICE_OFFLINE_NO_LOGIN:
                    device.setDeviceState(0);
                    BaseApplication.getLogger().e("连接设备失败，设备未在局域网内，且当前手机只有局域网环境");
                    break;
                default:
                    device.setDeviceState(0);
                    BaseApplication.getLogger().e("连接设备失败，其他错误码:" + result);
                    break;
            }

        }
    };

    /**
     * 密码认证
     *
     * @param xDevice
     */
    public int openDevicePassword(XDevice xDevice) {
        if (xDevice.getVersion() == 1) {
            return setDevicePassword(8888, xDevice);
        } else {
            return setDevicePassword(Constant.passwrods, xDevice);
        }
    }


    private int setDevicePassword(final int password, XDevice xDevice) {
        int code = XlinkAgent.getInstance().setDeviceAccessKey(
                xDevice, password,
                new SetDeviceAccessKeyListener() {
                    @Override
                    public void onSetLocalDeviceAccessKey(XDevice xdevice, int code, int msgId) {
                        BaseApplication.getLogger().i("返回：" + code + "\t" + "pawword:" + password);
                        switch (code) {
                            case XlinkCode.SUCCEED:
                                SUCCEED(xdevice, password);
                                break;
                            default:
                                break;
                        }

                    }
                });
        return code;

    }


    private void SUCCEED(XDevice xd, int pwd) {
        device.setAccessKey(pwd + "");
        device.setxDevice(XlinkAgent.deviceToJson(xd).toString());
        BaseApplication.getLogger().i("pwd:" + pwd);
        DeviceManage.getInstance().addDevice(device);
    }

    public boolean sendData(final String bs) {
        if (device.getDeviceState() == 0) {
            try {
                connectDevice();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
        int ret = 0;
        String aesBs = null;
        try {

            try {
                aesBs = AES128Utils.HmEncrypt(bs, device.getAesKey());
            } catch (Exception e) {
                e.printStackTrace();
                BaseApplication.getLogger().i("加密失败" + device.getAesKey());
                aesBs = bs;
            }
            try {
                String sbe = AES128Utils.HmDecrypt(aesBs, device.getAesKey());
                BaseApplication.getLogger().i("解密数据:" + sbe);
            } catch (Exception e) {
                e.printStackTrace();
                BaseApplication.getLogger().i("解密失败:");
            }

            ret = XlinkAgent.getInstance().sendPipeData(device.getxDevice(), aesBs.getBytes(), pipeListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (ret < 0) {
            device.setDeviceState(0);
            DeviceManage.getInstance().updateDevice(device);
            switch (ret) {
                case XlinkCode.NO_CONNECT_SERVER:
                    BaseApplication.getLogger().e("发送数据失败，手机未连接服务器");
                    break;
                case XlinkCode.NETWORD_UNAVAILABLE:
                    BaseApplication.getLogger().e("当前网络不可用,发送数据失败");
                    break;
                case XlinkCode.NO_DEVICE:
                    BaseApplication.getLogger().e("未找到设备");
                    try {
                        XlinkAgent.getInstance().initDevice(device.getxDevice());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    BaseApplication.getLogger().e("发送数据失败，错误码：" + ret);
                    break;
            }

            return false;
        } else {
            BaseApplication.getLogger().d("发送加密前数据：" + bs + "\t长度：" + bs.length() + "\n发送加密后数据：" + aesBs + "\t长度：" + aesBs.length());
        }
        return true;
    }

    public boolean sendData(final String bs, boolean isAes) {
        if (device.getDeviceState() == 0) {
            try {
                connectDevice();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
        int ret = 0;
        try {
            BaseApplication.getLogger().json(XlinkAgent.deviceToJson(device.getxDevice()).toString());
//            if (device.getxDevice().getDeviceId()==0){
//                ret = XlinkAgent.getInstance().sendLocalPipeData(device.getxDevice(), bs.getBytes(), pipeListener);
//            }
            ret = XlinkAgent.getInstance().sendPipeData(device.getxDevice(), bs.getBytes(), pipeListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (ret < 0) {
            device.setDeviceState(0);
            DeviceManage.getInstance().updateDevice(device);
            switch (ret) {
                case XlinkCode.NO_CONNECT_SERVER:
                    BaseApplication.getLogger().e("发送数据失败，手机未连接服务器");
                    break;
                case XlinkCode.NETWORD_UNAVAILABLE:
                    BaseApplication.getLogger().e("当前网络不可用,发送数据失败");
                    break;
                case XlinkCode.NO_DEVICE:
                    BaseApplication.getLogger().e("未找到设备");
                    try {
                        XlinkAgent.getInstance().initDevice(device.getxDevice());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    BaseApplication.getLogger().e("发送数据失败，错误码：" + ret);
                    break;
            }

            return false;
        } else {
            BaseApplication.getLogger().i("发送数据,msgId:" + ret + " data:" + bs);
        }
        return true;
    }

    private SendPipeListener pipeListener = new SendPipeListener() {

        @Override
        public void onSendLocalPipeData(XDevice xdevice, int code, int messageId) {
            // setDeviceStatus(false);
            BaseApplication.getLogger().json(XlinkAgent.deviceToJson(xdevice).toString());
            switch (code) {
                case XlinkCode.SUCCEED:
                    device.setDeviceState(1);

                    BaseApplication.getLogger().i("发送数据,msgId:" + messageId + "成功");
                    break;
                case XlinkCode.TIMEOUT:
                    BaseApplication.getLogger().e("发送数据,msgId:" + messageId + "超时");

                    break;
                case XlinkCode.SERVER_CODE_UNAUTHORIZED:
                    BaseApplication.getLogger().i("控制设备失败,当前帐号未订阅此设备，请重新订阅");
                    device.setDeviceState(0);
                    try {
                        connectDevice();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case XlinkCode.SERVER_DEVICE_OFFLIEN:
                    BaseApplication.getLogger().e("设备不在线");
                    device.setDeviceState(0);
                    break;
                default:
                    BaseApplication.getLogger().e("控制设备其他错误码:" + code);
                    break;
            }
            DeviceManage.getInstance().updateDevice(device);
        }
    };

    /**
     * 获取设备信息
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param limit  查询长度
     * @param offset 查询范围
     */
    public void getDeviceDiary(String year, String month, String day, int limit, int offset) {
        List<DataDevice> dataDeviceList = DataSupport.where("userid = ? and deviceMac = ? and subMac = ? and year = ? and month = ? and day = ?", BaseApplication.getMyApplication().getUserInfo().getId() + "", device.getDeviceMac(), subDevice.getDeviceMac(), year + "", month + "", day + "").limit(limit).offset(offset).find(DataDevice.class);
    }

    public XlinkDevice getDevice() {
        return device;
    }

    public void setDevice(XlinkDevice device) {
        this.device = device;
    }

    public SubDevice getSubDevice() {
        return subDevice;
    }

    public void setSubDevice(SubDevice subDevice) {
        this.subDevice = subDevice;
    }
}
