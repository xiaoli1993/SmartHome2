package com.heiman.smarthome;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.utils.Cockroach;
import com.heiman.utils.XlinkUtils;
import com.jiongbull.jlog.Logger;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.LogInterceptor;
import com.orhanobut.hawk.NoEncryption;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import io.xlink.wifi.sdk.XDevice;
import io.xlink.wifi.sdk.XlinkAgent;
import io.xlink.wifi.sdk.XlinkCode;
import io.xlink.wifi.sdk.bean.DataPoint;
import io.xlink.wifi.sdk.bean.EventNotify;
import io.xlink.wifi.sdk.listener.XlinkNetListener;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/3/17 15:03
 * @Description :
 */
public class MyApplication extends BaseApplication implements XlinkNetListener {

    private static MyApplication myApplication;
    // 全局登录的 appId 和auth
    public int appid;
    public String authKey;
    public String AccessToken;
    public String Refresh_token;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        initBuglySDK();
        getLogger().d("--------------------buglaySDKok--------------------");
        initQuit();
        getLogger().d("--------------------Quitok--------------------");
        initXlinkSDK();
        getLogger().d("--------------------XlinkSDKok--------------------");
        initLiteSDK();
        getLogger().d("--------------------LiteSDKok--------------------");
    }

    private void initLiteSDK() {
        LitePal.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();
    }

    /**
     * 云智易SDK
     */
    private void initXlinkSDK() {
        XlinkAgent.init(this);
        XlinkAgent.setCMServer(Constant.IO_URL, Constant.IO_PORT);//正式平台地址
        XlinkAgent.getInstance().addXlinkListener(this);
        //优先内网连接(谨慎使用,如果优先内网,则外网会在内网连接成功或者失败,或者超时后再进行连接,可能会比较慢)
        XlinkAgent.getInstance().setPreInnerServiceMode(true);
        Hawk.init(this)
                .setEncryption(new NoEncryption())
                .setLogInterceptor(new LogInterceptor() {
                    @Override
                    public void onLog(String message) {
                        MyApplication.getLogger().i(message);

                    }
                })
//                .setConverter(new MyConverter())
//                .setParser(new MyParser())
//                .setStorage(HawkBuilder.newSqliteStorage(this))
                .build();
        boolean isappid = Hawk.contains(Constant.SAVE_appId);
        if (isappid) {
            appid = Hawk.get(Constant.SAVE_appId);
        }
        boolean isauthKey = Hawk.contains(Constant.SAVE_authKey);
        if (isauthKey) {
            authKey = Hawk.get(Constant.SAVE_authKey);
        }
    }

    /**
     * 防止闪退
     */
    private void initQuit() {
        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getLogger().d(thread + "\n" + throwable.toString());
                            throwable.printStackTrace();
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }





    /**
     * BUG搜集以及热更新
     */
    private void initBuglySDK() {
        Context context = getApplicationContext();
        // 获取当前包名136326
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);

        strategy.setAppChannel("Heiman");  //设置渠道
        strategy.setAppVersion("V1.0");      //App的版本
        strategy.setAppPackageName("com.heiman.smarthome");  //App的包名

        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        CrashReport.initCrashReport(getApplicationContext(), "a476f93da4", false, strategy);
        Bugly.init(getApplicationContext(), "a476f93da4", false, strategy);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker();
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    /**
     * xlink 回调的onStart
     *
     * @param code 返回码
     */
    @Override
    public void onStart(int code) {
        // TODO Auto-generated method stub
        getLogger().e("onStart code" + code);
        sendBroad(Constant.BROADCAST_ON_START, code);
    }

    /**
     * 回调登录xlink状态
     *
     * @param code 返回码
     */
    @Override
    public void onLogin(int code) {
        // TODO Auto-generated method stub
        getLogger().e("login code" + code);
        sendBroad(Constant.BROADCAST_ON_LOGIN, code);
//        if (code == XlinkCode.SUCCEED) {
//            XlinkUtils.shortTips("云端网络已可用");
//        } else if (code == XlinkCode.CLOUD_CONNECT_NO_NETWORK || XlinkUtils.isConnected()) {
//            // XlinkUtils.shortTips("网络不可用，请检查网络连接");
//
//        } else {
//            XlinkUtils.shortTips("连接到服务器失败，请检查网络连接");
//        }
    }

    @Override
    public void onLocalDisconnect(int code) {
        if (code == XlinkCode.LOCAL_SERVICE_KILL) {
            // 这里是xlink服务被异常终结了（第三方清理软件，或者进入应用管理被强制停止应用/服务）
            // 永不结束的service
            // 除非调用 XlinkAgent.getInstance().stop（）;
            XlinkAgent.getInstance().start();
        }
//        XlinkUtils.shortTips("本地网络已经断开");
    }


    @Override
    public void onDisconnect(int code) {
        if (code == XlinkCode.CLOUD_SERVICE_KILL) {
            // 这里是服务被异常终结了（第三方清理软件，或者进入应用管理被强制停止服务）
            if (appid != 0 && !TextUtils.isEmpty(authKey)) {
                XlinkAgent.getInstance().login(appid, authKey);
            }
        }
//        XlinkUtils.shortTips("正在修复云端连接");
    }

    /**
     * 收到 局域网设备推送的pipe数据
     */
    @Override
    public void onRecvPipeData(short messageId, XDevice xdevice, byte[] data) {
        // TODO Auto-generated method stub
        getLogger().e("onRecvPipeData::device:" + xdevice.toString() + "data:" + data);
        XlinkDevice device = DeviceManage.getInstance().getDevice(xdevice.getMacAddress());
        if (device != null) {
//            // 发送广播，那个activity需要该数据可以监听广播，并获取数据，然后进行响应的处理
//            // TimerManage.getInstance().parseByte(device,data);
            getData(Constant.BROADCAST_RECVPIPE_SYNC, device, data);
        }
    }

    /**
     * 收到设备通过云端服务器推送的pipe数据
     */
    @Override
    public void onRecvPipeSyncData(short messageId, XDevice xdevice, byte[] data) {
        // TODO Auto-generated method stub
        getLogger().e("onRecvPipeSyncData::device:" + xdevice.toString() + "data:"
                + data);
        XlinkDevice device = DeviceManage.getInstance().getDevice(xdevice.getMacAddress());
        if (device != null) {
//            // 发送广播，那个activity需要该数据可以监听广播，并获取数据，然后进行响应的处理
//            // TimerManage.getInstance().parseByte(device,data);
            getData(Constant.BROADCAST_RECVPIPE_SYNC, device, data);
        }
    }

    /**
     */
    public void sendBroad(String action, int code) {
        Intent intent = new Intent(action);
        intent.putExtra(Constant.STATUS, code);
        MyApplication.this.sendBroadcast(intent);
    }

    /**
     */
    public void sendPipeBroad(String action, XlinkDevice device, String data) {
        Intent intent = new Intent(action);
        intent.putExtra(Constant.DEVICE_MAC, device.getDeviceMac());
        if (data != null) {
            intent.putExtra(Constant.DATA, data);
        }
        MyApplication.this.sendBroadcast(intent);
    }

    /**
     * 设备状态改变：掉线/重连/在线
     */
    @Override
    public void onDeviceStateChanged(XDevice xdevice, int state) {
        // TODO Auto-generated method stub

        getLogger().e("onDeviceStateChanged:" + xdevice.getMacAddress() + " state:" + state);
        XlinkDevice device = DeviceManage.getInstance().getDevice(xdevice.getMacAddress());
        if (device != null) {
            JSONObject xdevices = XlinkAgent.getInstance().deviceToJson(xdevice);
            device.setxDevice(xdevices.toString());
            Intent intent = new Intent(Constant.BROADCAST_DEVICE_CHANGED);
            intent.putExtra(Constant.DEVICE_MAC, device.getDeviceMac());
            intent.putExtra(Constant.STATUS, state);
            MyApplication.getMyApplication().sendBroadcast(intent);
        }

    }

    /**
     * 数据断点变化
     *
     * @param xDevice    设备
     * @param dataPionts 数据端点
     * @param channel
     */
    @Override
    public void onDataPointUpdate(XDevice xDevice, List<DataPoint> dataPionts, int channel) {
        getLogger().e("onDataPointUpdate:" + dataPionts.toString());

        XlinkDevice device = DeviceManage.getInstance().getDevice(xDevice.getMacAddress());
        if (device != null) {
            Intent intent = new Intent(Constant.BROADCAST_DATAPOINT_RECV);
            intent.putExtra(Constant.DEVICE_MAC, device.getDeviceMac());
            if (dataPionts != null) {
                intent.putExtra(Constant.DATA, (Serializable) dataPionts);
            }
            MyApplication.this.sendBroadcast(intent);
        }
    }

    @Override
    public void onEventNotify(EventNotify eventNotify) {
        String str = "EventNotify{" +
                "notyfyFlags=" + eventNotify.notyfyFlags +
                ", formId=" + eventNotify.formId +
                ", messageId=" + eventNotify.messageId +
                ", messageType=" + eventNotify.messageType +
                ", notifyData=" + new String(eventNotify.notifyData) +
                '}';
        getLogger().e("onEventNotify:" + str);
        //        int badgeCount = 12;
//        ShortcutBadger.applyCount(this, badgeCount); //for 1.1.4+
//        ShortcutBadger.(getApplicationContext()).count(badgeCount); //for 1.1.3
    }


    private static String start = null;
    private static String end = null;
    private static byte[] datat;

    private static String newdatas, olddatas;

    /**
     * 分包处理
     *
     * @param BROAD
     * @param device
     * @param data
     */
    private static void getData(String BROAD, XlinkDevice device, byte[] data) {
        String res = null;
        try {
            res = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        newdatas = res;
        if (!newdatas.equals(olddatas)) {
            if (data.length < 5) {
                return;
            } else {
                start = XlinkUtils.getBinString(data[0]);
                if (start.equals("10101010")) {
                    end = XlinkUtils.getBinString(data[data.length - 1]);
                    datat = null;
                    if (end.equals("11111111")) {
                        try {
                            byte[] new_bts = Arrays.copyOfRange(data, 3, data.length - 1);
                            String resb = new String(new_bts, "UTF-8");
                            MyApplication.getMyApplication().sendPipeBroad(BROAD, device, resb);
//                            initdatajson(new_bts, device.getMacAddress());
                            MyApplication.getLogger().json(resb);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
//                        e.printStackTrace();
                        }
                    } else {
                        datat = Arrays.copyOfRange(data, 3, data.length);
                    }
                } else {
                    try {
                        byte[] new_bts = Arrays.copyOfRange(data, 0, data.length - 1);
                        byte[] todata = MyApplication.getMyApplication().combineTowBytes(datat, new_bts);
                        if (todata.length != 1 && todata[0] != 1) {
//                            initdatajson(todata, device.getMacAddress());
                            String resb = new String(new_bts, "UTF-8");
                            MyApplication.getMyApplication().sendPipeBroad(BROAD, device, resb);
                            MyApplication.getLogger().json(resb);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
//                    e.printStackTrace();
                    }
                }
            }
            olddatas = newdatas;
        }
    }

    /**
     * 数据截取
     *
     * @param bytes1
     * @param bytes2
     * @return
     */
    public byte[] combineTowBytes(byte[] bytes1, byte[] bytes2) {
        try {
            byte[] bytes3 = new byte[bytes1.length + bytes2.length];
            System.arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
            System.arraycopy(bytes2, 0, bytes3, bytes1.length, bytes2.length);
            return bytes3;
        } catch (Exception e) {
            byte[] k = {1};
            return k;
        }
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String getRefresh_token() {
        return Refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        Refresh_token = refresh_token;
    }
}
