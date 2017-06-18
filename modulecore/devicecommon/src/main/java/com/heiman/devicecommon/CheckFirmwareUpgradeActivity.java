package com.heiman.devicecommon;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.heiman.widget.progressbar.RoundProgressBar;
import com.jaeger.library.StatusBarUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckFirmwareUpgradeActivity extends GwBaseActivity {

    private ImageView imgBack;
    private TextView txtTitle;
    private ImageView imgUpdate;
    private TextView txtFirmware;
    private TextView txtCurrentFirmware;
    private TextView txtUpdateContent;
    private LinearLayout llUpdateWarningContainer;
    private TextView txtUpdateWarning;
    private Button btnConfirm;
    private Button btnUpdateNow;
    private RoundProgressBar rbarUpdateProgress;
    private ImageView imgUpdateSucceed;

    private int sn;

    private final static int UPDATE_FIRMWARE_FAIL_TIME = 120 * 1000; //认为120秒没有收到设备端发送的更新固件完成信息就是更新固件失败

    private final static int MSG_INIT_DEVICE_INFO = 10000;
    private final static int MSG_GET_LAST_FIRMWARE_VERSION_INFO_FAIL = 10001;
    private final static int MSG_GET_LAST_FIRMWARE_VERSION_INFO_SUCCEED = 10002;
    private final static int MSG_UPDATE_UPDATEPROGRESSBAR = 10003;
    private final static int MSG_UPDATE_FAIL = 10004;
    private final static int MSG_UPDATE_SUCCEED = 10005;
    private final static int MSG_SHOW_UPDATE_SUCCEED_IMG = 10006;//显示更新完成后的圆形打勾图片
    private final static int MSG_AUTO_BACK = 10007; //自动回到上一个界面
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT_DEVICE_INFO:
                    if (device != null) {
                        txtTitle.setText(device.getDeviceName());
                        getLastFirmwareVersion(device.getProductId(), device.getDeviceId() + "");
                    } else {
                        mHandler.sendEmptyMessageDelayed(MSG_INIT_DEVICE_INFO, 100);
                    }
                    break;
                case MSG_GET_LAST_FIRMWARE_VERSION_INFO_FAIL:
                    currentFirmwareLast();
                    break;
                case MSG_GET_LAST_FIRMWARE_VERSION_INFO_SUCCEED:

                    if (msg.obj != null) {
                        String description = "";
                        String current ="";
                        String newest = "";
                        try {
                            JSONObject jsonObject = new JSONObject(msg.obj.toString());

                            if (jsonObject.has("description")) {
                                description = jsonObject.getString("description");
                            }

                            if (jsonObject.has("current")) {
                                current = jsonObject.getString("current");
                            }

                            if (jsonObject.has("newest")) {
                                newest = jsonObject.getString("newest");
                            }
                        } catch (JSONException e) {
                            LogUtil.e(e.getMessage());
                        }

                        if (!current.equals(newest)) {
                            needUpdateFirmware(description, current, newest);
                            return;
                        }
                        device.setHardwareVer(current);
                        currentFirmwareLast();
                    } else {
                        currentFirmwareLast();
                    }
                    break;
                case MSG_UPDATE_UPDATEPROGRESSBAR:
                    int progress = rbarUpdateProgress.getProgress();
                    if (progress < 99) {
                        progress++;
                        rbarUpdateProgress.setProgress(progress);
                        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_UPDATEPROGRESSBAR, 1000);
                    }
                    break;
                case MSG_UPDATE_FAIL:
                    Toast.makeText(CheckFirmwareUpgradeActivity.this, R.string.update_firmware_fail, Toast.LENGTH_LONG).show();
                    break;
                case MSG_UPDATE_SUCCEED:
                    mHandler.removeMessages(MSG_UPDATE_FAIL);
                    rbarUpdateProgress.setProgress(100);
                    mHandler.sendEmptyMessageDelayed(MSG_SHOW_UPDATE_SUCCEED_IMG, 200); //显示更新完成后的圆形打勾图片
                    mHandler.sendEmptyMessageDelayed(MSG_AUTO_BACK, 3000); //三秒后自动回到上个界面
                    Toast.makeText(CheckFirmwareUpgradeActivity.this, R.string.update_firmware_succeed, Toast.LENGTH_LONG).show();
                    break;
                case MSG_SHOW_UPDATE_SUCCEED_IMG:
                    rbarUpdateProgress.setVisibility(View.GONE);
                    imgUpdateSucceed.setVisibility(View.VISIBLE);
                    break;
                case MSG_AUTO_BACK:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devicecommon_activity_check_firmware_upgrade);
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
        imgBack = (ImageView) findViewById(R.id.title_bar_return);
        txtTitle = (TextView) findViewById(R.id.title_bar_title);
        imgUpdate = (ImageView) findViewById(R.id.img_update);
        txtFirmware = (TextView) findViewById(R.id.txt_firmware);
        txtCurrentFirmware = (TextView) findViewById(R.id.txt_current_firmware);
        txtUpdateContent = (TextView) findViewById(R.id.txt_update_content);
        llUpdateWarningContainer = (LinearLayout) findViewById(R.id.ll_update_warning_container);
        txtUpdateWarning = (TextView) findViewById(R.id.txt_update_warning);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnUpdateNow = (Button) findViewById(R.id.btn_update_now);
        rbarUpdateProgress = (RoundProgressBar) findViewById(R.id.rbar_update_progress);
        imgUpdateSucceed = (ImageView) findViewById(R.id.img_update_succeed);

        imgBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnUpdateNow.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(MSG_INIT_DEVICE_INFO);
    }

    @Override
    public void deviceData(String dataString) {
        LogUtil.e("dataString:" + dataString);
    }

    @Override
    public void onClickListener(View v) {
        if (!UsefullUtill.judgeClick(R.layout.devicecommon_activity_check_firmware_upgrade, 500)) {
            LogUtil.e("点击过快！");
            return;
        }

        if (v.getId() == imgBack.getId()) {
            finish();
            return;
        }

        if (v.getId() == btnConfirm.getId()) {
            finish();
            return;
        }

        if (v.getId() == btnUpdateNow.getId()) {
            hidenUpdateBtn();
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_UPDATEPROGRESSBAR, 1000);
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_FAIL, UPDATE_FIRMWARE_FAIL_TIME);
            /**发送升级命令给设备**/
            sn = SmartPlug.mgetSN();
            sendData(HeimanCom.upData(sn, 1, 1 ,2));
            return;
        }
    }

    private HttpManage.ResultCallback<String> getLastfirmwareVersionCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            Message msg = new Message();
            msg.what = MSG_GET_LAST_FIRMWARE_VERSION_INFO_FAIL;
            if (error != null) {
                String errStr = SmartHomeUtils.showHttpCode(error.getCode());
                LogUtil.e("Code:" + error.getCode() + errStr);
                msg.obj = errStr;
            }
            mHandler.sendMessage(msg);
        }

        @Override
        public void onSuccess(int code, String response) {
            LogUtil.e("code:" + code + "\tresponse:" + response);
            Message msg = new Message();
            msg.what = MSG_GET_LAST_FIRMWARE_VERSION_INFO_SUCCEED;
            msg.obj = response;
            mHandler.sendMessage(msg);
        }
    };

    private void getLastFirmwareVersion(String product_id, String device_id) {
        LogUtil.e("product_id:" + product_id + "\tdevice_id:" + device_id + "\tAccess-Token:" + BaseApplication.getMyApplication().getAccessToken());
        HttpManage.getInstance().onVersion(BaseApplication.getMyApplication(), product_id, device_id, getLastfirmwareVersionCallback);
    }

    /**
     * 当当前的固件版本是最新的固件版本时
     */
    private void currentFirmwareLast() {
        txtFirmware.setVisibility(View.VISIBLE);
        btnConfirm.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(device.getHardwareVer())) {
            txtCurrentFirmware.setVisibility(View.VISIBLE);
            txtCurrentFirmware.setText(getString(R.string.current_version) + device.getHardwareVer());
        }
    }

    /**
     * 当需要更新是
     * @param description 更新内容
     * @param current 当前版本
     * @param newest 最新版本
     */
    private void needUpdateFirmware(String description, String current, String newest) {

        for (int i = 1; i < 10; i ++) {
            description = description.replace(i + "" , "\n" + i);
        }
        txtFirmware.setVisibility(View.VISIBLE);
        txtFirmware.setText(getString(R.string.upgrade_remind) + " " + current + "->" + newest);
        txtUpdateContent.setVisibility(View.VISIBLE);
        txtUpdateContent.setText(getString(R.string.update_content) + description);
        llUpdateWarningContainer.setVisibility(View.VISIBLE);
        txtUpdateWarning.setVisibility(View.VISIBLE);
        btnUpdateNow.setVisibility(View.VISIBLE);
    }

    private void hidenUpdateBtn() {
        ScaleAnimation disscaleAnimation = new ScaleAnimation(btnUpdateNow.getScaleX(), 0, btnUpdateNow.getScaleY(), 0, btnUpdateNow.getPivotX(), btnUpdateNow.getPivotY());
        disscaleAnimation.setDuration(100);
        disscaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                btnUpdateNow.setVisibility(View.GONE);
                showUpdateProgressBar();
            }
        });
        btnUpdateNow.startAnimation(disscaleAnimation);
    }

    private void showUpdateProgressBar() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, rbarUpdateProgress.getScaleX(), 0, rbarUpdateProgress.getScaleY(), rbarUpdateProgress.getPivotX(), rbarUpdateProgress.getPivotY());
        scaleAnimation.setDuration(100);
        rbarUpdateProgress.startAnimation(scaleAnimation);
        rbarUpdateProgress.setVisibility(View.VISIBLE);
    }
}
