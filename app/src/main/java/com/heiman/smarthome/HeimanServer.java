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
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.RefreshToken;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;

import org.apache.http.Header;
import org.json.JSONException;

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
            if (action.equals(Constant.BROADCAST_DEVICE_SYNC)) {
                String data = intent.getStringExtra(Constant.DATA);
                addSubDevice(macs, data);
            } else if (action.equals(Constant.BROADCAST_RECVPIPE_SYNC)) {
                String data = intent.getStringExtra(Constant.DATA);
                addSubDevice(macs, data);
            } else if (action.equals(Constant.BROADCAST_DEVICE_CHANGED)) {

            } else if (action.equals(Constant.BROADCAST_CONNENCT_SUCCESS)) {

            } else if (action.equals(Constant.BROADCAST_CONNENCT_FAIL)) {

            } else if (action.equals(Constant.BROADCAST_SEND_OVERTIME)) {

            } else if (action.equals(Constant.BROADCAST_SEND_SUCCESS)) {

            }
        }

        private void addSubDevice(String macs, String data) {
            XlinkDevice xlinkDevice = DeviceManage.getInstance().getDevice(macs);
            Gson gason = new Gson();

        }

    };

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterBroadcast) {
            unregisterReceiver(mBroadcastReceiver);
        }
        timer.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
