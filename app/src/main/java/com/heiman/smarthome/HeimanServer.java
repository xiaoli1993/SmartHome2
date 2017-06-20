package com.heiman.smarthome;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.AESKey;
import com.heiman.baselibrary.mode.DataDevice;
import com.heiman.baselibrary.mode.DeviceSS;
import com.heiman.baselibrary.mode.HeimanSet;
import com.heiman.baselibrary.mode.Messages;
import com.heiman.baselibrary.mode.RefreshToken;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.mode.ZigbeeNewDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.datacom.aes.AES128Utils;
import com.heiman.smarthome.modle.DeviceMessages;
import com.heiman.smarthome.modle.FcmNotification;
import com.heiman.utils.Time;
import com.heiman.utils.UsefullUtill;
import com.orhanobut.hawk.Hawk;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindCallback;
import org.litepal.crud.callback.SaveCallback;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author : 肖力
 * @Time :  2017/6/1 8:39
 * @Description :
 * @Modify record :
 */

public class HeimanServer extends Service {
    private IBinder binder = new HeimanServer.LoPongBinder();
    private static Context context;
    private boolean isRegisterBroadcast = false;

    private final Timer timer = new Timer();
    private TimerTask task;

    private final Timer messageTimer = new Timer();
    private TimerTask messageTask;

    private boolean isRunTask = true;

    public class LoPongBinder extends Binder {
        // 返回本地服务
        HeimanServer getService() {
            return HeimanServer.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRegisterBroadcast = true;
        MyApplication.getLogger().i("注册广播");
        registerReceiver(mBroadcastReceiver, SmartHomeUtils.regFilter());
        return START_REDELIVER_INTENT;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    try {
                        HttpManage.getInstance().onRefreshs(HeimanServer.this, new HttpManage.ResultCallback<String>() {
                            @Override
                            public void onError(Header[] headers, HttpManage.Error error) {
                                MyApplication.getLogger().e("失败" + error.getMsg() + "s:" + error.getCode());
                            }

                            @Override
                            public void onSuccess(int code, String response) {
                                Gson gson = new Gson();
                                RefreshToken refreshToken = gson.fromJson(response, RefreshToken.class);
                                String accessToken = refreshToken.getAccess_token();
                                String refresh_token = refreshToken.getRefresh_token();
                                MyApplication.getMyApplication().setAccessToken(accessToken);
                                MyApplication.getMyApplication().setRefresh_token(refresh_token);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_MESSAGE_TIMER:
                    boolean isFirstAdd = Hawk.contains(MyApplication.getMyApplication().getUserInfo().getId() + "_isFirstAdd");
                    if (isRefreshMessage) {
                        if (!isFirstAdd) {
                            MAX_Refresh = 500;
                            MAX_LIMIT = 0;
                            isStart = false;
                            DataSupport.where("userid = ?", MyApplication.getMyApplication().getUserInfo().getId() + "").maxAsync(DataDevice.class, "createDate", String.class).listen(new FindCallback() {
                                @Override
                                public <T> void onFinish(T t) {
                                    createDate = (String) t;
                                    MyApplication.getLogger().i("createDate:\t" + createDate);
                                    if (SmartHomeUtils.isEmptyString(createDate)) {
                                        queryCreate = "$gte";
                                        getMessage(createDate, isStart, queryCreate);
                                    } else {
                                        DataSupport.where("userid = ?", MyApplication.getMyApplication().getUserInfo().getId() + "").minAsync(DataDevice.class, "createDate", String.class).listen(new FindCallback() {
                                            @Override
                                            public <T> void onFinish(T t) {
                                                MyApplication.getLogger().i("createDate:\t" + createDate);
                                                createDate = (String) t;
                                                queryCreate = "$lt";
                                                getMessage(createDate, isStart, queryCreate);
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            MAX_Refresh = 100;
                            MAX_LIMIT = 0;
                            queryCreate = "$gte";
                            isStart = true;
                            DataSupport.select("createDate").where("userid = ?", MyApplication.getMyApplication().getUserInfo().getId() + "").maxAsync(DataDevice.class, "createDate", String.class).listen(new FindCallback() {
                                @Override
                                public <T> void onFinish(T t) {
                                    createDate = (String) t;
                                    getMessage(createDate, isStart, queryCreate);
                                }
                            });
                        }

                    }

                    break;
            }
        }
    };

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            String macs = intent.getStringExtra(Constant.DEVICE_MAC);
            // 收到pipe包
            if (action.equals(Constant.BROADCAST_RECVPIPE)) {
                String data = intent.getStringExtra(Constant.DATA);
                analysisData(macs, data);
            } else if (action.equals(Constant.BROADCAST_RECVPIPE_SYNC)) {
                String data = intent.getStringExtra(Constant.DATA);
                analysisData(macs, data);
            } else if (action.equals(Constant.BROADCAST_DEVICE_CHANGED)) {

            } else if (action.equals(Constant.BROADCAST_CONNENCT_SUCCESS)) {

            } else if (action.equals(Constant.BROADCAST_CONNENCT_FAIL)) {

            } else if (action.equals(Constant.BROADCAST_SEND_OVERTIME)) {

            } else if (action.equals(Constant.BROADCAST_SEND_SUCCESS)) {

            }
        }

        /**
         * 解析获取的数据
         * @param macs  MAC地址
         * @param dataString 数据
         */
        private void analysisData(String macs, String dataString) {
            BaseApplication.getLogger().json(dataString);
            XlinkDevice xlinkDevice = DeviceManage.getInstance().getDevice(macs);
            Gson gson = new Gson();
            aesKeyAnalysisData(dataString, xlinkDevice, gson);
            if (xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW || xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW) {
                gwAnalysisData(dataString, xlinkDevice, gson);
            }
        }
    };

    /**
     * 解密AESKEY
     *
     * @param dataString  数据
     * @param xlinkDevice 设备
     * @param gson
     */
    private void aesKeyAnalysisData(String dataString, XlinkDevice xlinkDevice, Gson gson) {
        try {
            BaseApplication.getLogger().i("进入解密aesKeyb:");
            AESKey aesKey = gson.fromJson(dataString, AESKey.class);
            BaseApplication.getLogger().i("aesKeyb:" + aesKey.getPL().getAESkey().getKey());
            String aesKeyb = AES128Utils.HmDecrypt(aesKey.getPL().getAESkey().getKey(), Constant.ZIGBEE_H1GW_NEW_KEY);
            BaseApplication.getLogger().i("aesKeyb:" + Arrays.toString(aesKeyb.getBytes()) + "\t秘钥字符串：" + aesKeyb
                    + "\n" + UsefullUtill.bytesToHxString(aesKeyb.getBytes())

            );
            xlinkDevice.setAesKey(aesKeyb);
            DeviceManage.getInstance().addDevice(xlinkDevice);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * 网关 数据处理
     *
     * @param dataString
     * @param xlinkDevice
     * @param gson
     */
    private void gwAnalysisData(String dataString, XlinkDevice xlinkDevice, Gson gson) {
        try {
            ZigbeeNewDevice zigbeeNewDevice = gson.fromJson(dataString, ZigbeeNewDevice.class);
            SubDevice subDevice = new SubDevice();
            subDevice.setDeviceMac(xlinkDevice.getDeviceMac());
            List<ZigbeeNewDevice.PLBean.ZigbeeListBean.SubDeviceBean> subDeviceBeanList = zigbeeNewDevice.getPL().getZigbeeList().getSubDevice();
            for (int i = 0; i < subDeviceBeanList.size(); i++) {
                subDevice.setZigbeeMac(subDeviceBeanList.get(i).getDeviceMac());
                subDevice.setDeviceName(subDeviceBeanList.get(i).getDeviceName());
                subDevice.setRoomID(subDeviceBeanList.get(i).getRoomID());
                subDevice.setIndex(subDeviceBeanList.get(i).getIndex());
                subDevice.setDeviceType(subDeviceBeanList.get(i).getDeviceType());
                SubDeviceManage.getInstance().addDevice(subDevice);
            }
            BaseApplication.getLogger().i("设备添加完成");
            MyApplication.getMyApplication().sendBroadcast(new Intent(Constant.DATA_SUBDEVICE));
            BaseApplication.getLogger().i("发送广播");
            return;
        } catch (Exception e) {

        }
        try {
            DeviceSS zigbeeNewDevice = gson.fromJson(dataString, DeviceSS.class);
            List<DeviceSS.PLBean.OIDBean.DEVBean> subDeviceBeanList = zigbeeNewDevice.getPL().getOID().getDEV();
            for (int i = 0; i < subDeviceBeanList.size(); i++) {
                DeviceSS.PLBean.OIDBean.DEVBean.SSBean ssBean = subDeviceBeanList.get(i).getSS();
                SubDevice subDevice = SubDeviceManage.getInstance().getDevice(xlinkDevice.getDeviceMac(), subDeviceBeanList.get(i).getZX());
                SmartHomeUtils.typeSetSS(subDevice, ssBean);
            }
            MyApplication.getMyApplication().sendBroadcast(new Intent(Constant.DATA_SUBDEVICE));
            return;
        } catch (Exception e) {

        }
        try {
            JSONObject jsonObject = new JSONObject(dataString);
            JSONObject PL = jsonObject.getJSONObject("PL");
            BaseApplication.getLogger().i(PL.toString());
            JSONObject OID = PL.getJSONObject(HeimanCom.COM_GW_OID.GW_BASIC_INFORMATION);
            HeimanSet.PLBean.GwBasicOID gwBasicOID = gson.fromJson(OID.toString(), HeimanSet.PLBean.GwBasicOID.class);
            xlinkDevice.setArmtype(gwBasicOID.getArmtype());
            xlinkDevice.setAlarmlevel(gwBasicOID.getAlarmlevel());
            xlinkDevice.setSoundlevel(gwBasicOID.getSoundlevel());
            xlinkDevice.setBetimer(gwBasicOID.getBetimer());
            xlinkDevice.setGwlanguage(gwBasicOID.getGwlanguage());
            xlinkDevice.setGwlightlevel(gwBasicOID.getGwlightlevel());
            xlinkDevice.setGwlightonoff(gwBasicOID.getGwlightonoff());
            xlinkDevice.setLgtimer(gwBasicOID.getLgtimer());
            DeviceManage.getInstance().addDevice(xlinkDevice);
            return;
        } catch (Exception e) {

        }
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        task = new TimerTask() {
            @Override
            public void run() {
                if (isRunTask) {
                    mHandler.sendEmptyMessage(1);
                }
            }
        };
        timer.schedule(task, 1000 * 60 * 60, 1000 * 60 * 60);

        messageTask = new TimerTask() {
            @Override
            public void run() {
                if (DeviceManage.listDev.size() > 0) {
                    if (isRunTask) {
                        mHandler.sendEmptyMessage(GET_MESSAGE_TIMER);
                    }
                }
            }
        };
        messageTimer.schedule(messageTask, 500, 1000 * 10);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterBroadcast) {
            unregisterReceiver(mBroadcastReceiver);
        }
        timer.cancel();
        messageTimer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    private static final int GET_MESSAGE_TIMER = 5;
    private String createDate;
    private String queryCreate = "$gte";
    private boolean isStart = true;

    int MAX_LIMIT = 0;
    int MAX_Refresh = 100;
    public boolean isRefreshMessage = true;

    private List<DataDevice> datalist = new ArrayList<DataDevice>();

    private void getMessage(final String CreateDate, final boolean isStart, String queryCreate) {
        MyApplication.getLogger().i("进入MAX_LIMIT:" + MAX_LIMIT + "\t" + "MAX_Refresh:" + MAX_Refresh + "\t" + "CreateDate:" + CreateDate + "\tisStart:" + isStart);
        try {
            JSONArray deviceid = SmartHomeUtils.getdeviceid();

            if (deviceid.length() > 0) {
                HttpManage.getInstance().getMessagesID(MyApplication.getMyApplication(), MAX_LIMIT + "", MAX_Refresh + "", deviceid, queryCreate, CreateDate, new HttpManage.ResultCallback<String>() {
                    @Override
                    public void onError(Header[] headers, HttpManage.Error error) {
                        MyApplication.getLogger().e("获取消息失败:" + error.getMsg() + "\t" + error.getCode());
                        try {
                            if (!SmartHomeUtils.isEmptyList(datalist)) {
                                DataSupport.saveAllAsync(datalist).listen(new SaveCallback() {
                                    @Override
                                    public void onFinish(boolean success) {
                                        isRefreshMessage = true;
                                    }
                                });
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onSuccess(int code, String response) {
                        final Gson gson = new Gson();
                        Messages messages = gson.fromJson(response, Messages.class);
                        if (isStart) {
                            MAX_LIMIT += MAX_Refresh;
                            MAX_Refresh = 30;
                        } else {
                            MAX_LIMIT += MAX_Refresh;
//                            if (messages.getCount() > 10000) {
                            MAX_Refresh = 500;
//                            } else {
//                                MAX_Refresh = messages.getCount();
//                            }
                        }
                        final List<Messages.ListBean> listsize = messages.getList();
                        final int iSize = listsize.size();
                        if (iSize == 0) {
                            isRefreshMessage = true;
                            Hawk.put(MyApplication.getMyApplication().getUserInfo().getId() + "_isFirstAdd", true);
                            return;
                        }
                        isRefreshMessage = false;
                        final Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < iSize; i++) {
                                    DataDevice datadevice = new DataDevice();
                                    datadevice.setMessageID(listsize.get(i).getId());
                                    datadevice.setMessageType(listsize.get(i).getType());
                                    datadevice.setNotifyType(listsize.get(i).getNotify_type());
                                    datadevice.setPush(listsize.get(i).isIs_push());
                                    datadevice.setRead(listsize.get(i).isIs_read());
                                    datadevice.setAlertName(listsize.get(i).getAlert_name());
                                    datadevice.setAlertValue(listsize.get(i).getAlert_value());
                                    datadevice.setCreateDate(listsize.get(i).getCreate_date());
                                    datadevice.setUserid(MyApplication.getMyApplication().getUserInfo().getId() + "");
                                    datadevice.setUserName(MyApplication.getMyApplication().getUserInfo().getNickname());
                                    datadevice.setDeviceId(listsize.get(i).getFrom() + "");
                                    Messages.ListBean listBean = listsize.get(i);
                                    boolean isFirst = Hawk.contains(MyApplication.getMyApplication().getUserInfo() + "_isFirstAdd");
                                    if (!SmartHomeUtils.isEmptyString(listBean.getAlert_name()))
                                        switch (listBean.getAlert_name()) {
                                            case "gcm notification":
                                                FcmNotification fcmNotification = gson.fromJson(listBean.getContent(), FcmNotification.class);
                                                try {
                                                    Date date = Time.stringToDate(fcmNotification.getNotification().getBody_loc_args().get(0), "yyyy-MM-dd HH:mm:ss");
                                                    datadevice.setDate(date);
                                                    datadevice.setYear(Time.dateToString(date, "yyyy"));
                                                    datadevice.setMonth(Time.dateToString(date, "MM"));
                                                    datadevice.setDay(Time.dateToString(date, "dd"));
                                                    datadevice.setHH(Time.dateToString(date, "HH"));
                                                    datadevice.setMm(Time.dateToString(date, "mm"));
                                                    datadevice.setSs(Time.dateToString(date, "ss"));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                datadevice.setBodyLocKey(fcmNotification.getNotification().getBody_loc_key());
                                                try {
                                                    datadevice.setSubMac(fcmNotification.getNotification().getBody_loc_args().get(1));
                                                } catch (Exception e) {

                                                }
                                                try {
                                                    datadevice.setData(fcmNotification.getNotification().getBody_loc_args().get(2));
                                                } catch (Exception e) {

                                                }
                                                break;
                                            case "message_notice":
                                                DeviceMessages deviceMessages = gson.fromJson(listBean.getContent(), DeviceMessages.class);
                                                try {
                                                    Date date = Time.stringToDate(deviceMessages.getTM(), "yyyy-MM-dd HH:mm:ss");
                                                    datadevice.setDate(date);
                                                    datadevice.setYear(Time.dateToString(date, "yyyy"));
                                                    datadevice.setMonth(Time.dateToString(date, "MM"));
                                                    datadevice.setDay(Time.dateToString(date, "dd"));
                                                    datadevice.setHH(Time.dateToString(date, "HH"));
                                                    datadevice.setMm(Time.dateToString(date, "mm"));
                                                    datadevice.setSs(Time.dateToString(date, "ss"));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                break;
                                        }
                                    if (isFirst) {
                                        if (!SmartHomeUtils.isEmptyString(CreateDate)) {
                                            if (CreateDate.equals(listsize.get(i).getCreate_date())) {
                                                if (!SmartHomeUtils.isEmptyList(datalist)) {
                                                    DataSupport.saveAllAsync(datalist).listen(new SaveCallback() {
                                                        @Override
                                                        public void onFinish(boolean success) {
                                                            isRefreshMessage = true;
                                                        }
                                                    });
                                                }
                                                return;
                                            } else {
                                                if (!SmartHomeUtils.isEmptyString(listBean.getAlert_name()))
                                                    if (!listBean.getAlert_name().equals("apn notification")) {
                                                        datalist.add(datadevice);
                                                    }
                                            }
                                        }
                                    } else {
                                        if (!SmartHomeUtils.isEmptyString(listBean.getAlert_name()))
                                            if (!listBean.getAlert_name().equals("apn notification")) {
                                                datalist.add(datadevice);
                                            }
                                    }
                                }
                                if (!SmartHomeUtils.isEmptyList(datalist)) {
                                    DataSupport.saveAllAsync(datalist).listen(new SaveCallback() {
                                        @Override
                                        public void onFinish(boolean success) {
                                            isRefreshMessage = true;
                                        }
                                    });
                                }
                            }
                        });
                        thread.start();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
