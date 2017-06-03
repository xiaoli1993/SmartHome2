package com.heiman.smarthome.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.UserInfo;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.utils.AsyncBitmapLoader;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;

import java.util.HashMap;
import java.util.List;

public class UserCenterActivity extends BaseActivity {

    private TextView txtNickName;
    private ImageButton imgBtnBack;
    private ImageButton imgBtnHead;
    private TextView txtDeviceTotal;
    private TextView txtRestPwd;
    private TextView txtFeedBack;
    private TextView txtCommonProblem;
    private TextView txtAbout;
    private TextView txtSet;
    private TextView txtExitAccount;
    private UserInfo userInfo = null;
    private List<XlinkDevice> xDevices = null;

    private AsyncBitmapLoader asyncBitmapLoader = AsyncBitmapLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_center);
        setContentLayout(R.layout.activity_user_center);
        showHeadView(false);

        txtNickName = (TextView) findViewById(R.id.txt_nick_name);
        imgBtnBack = (ImageButton) findViewById(R.id.ivbtn_back);
        imgBtnHead = (ImageButton) findViewById(R.id.ivbtn_head);
        txtDeviceTotal = (TextView) findViewById(R.id.txt_device_total);
        txtRestPwd = (TextView) findViewById(R.id.txt_reset_pwd);
        txtFeedBack = (TextView) findViewById(R.id.txt_feed_back);
        txtCommonProblem = (TextView) findViewById(R.id.txt_common_problem);
        txtAbout = (TextView) findViewById(R.id.txt_about);
        txtSet = (TextView) findViewById(R.id.txt_set);
        txtExitAccount = (TextView) findViewById(R.id.txt_exit_account);


        imgBtnBack.setOnClickListener(mOnClickListener);
        imgBtnHead.setOnClickListener(mOnClickListener);
        txtRestPwd.setOnClickListener(mOnClickListener);
        txtFeedBack.setOnClickListener(mOnClickListener);
        txtCommonProblem.setOnClickListener(mOnClickListener);
        txtAbout.setOnClickListener(mOnClickListener);
        txtSet.setOnClickListener(mOnClickListener);
        txtExitAccount.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(MSG_INIT_USERINFO);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!UsefullUtill.judgeClick(R.layout.activity_user_center, 500)) {
                LogUtil.e("点击过快！");
                return;
            }
            switch (v.getId()) {
                case R.id.ivbtn_back:
                    onBackPressed();
                    break;
                case R.id.ivbtn_head:
                    Intent intent = new Intent(UserCenterActivity.this, UserInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.txt_reset_pwd:
                    Intent resetPwd = new Intent(UserCenterActivity.this, ResetPwdActivity.class);
                    startActivity(resetPwd);
                    break;
                case R.id.txt_feed_back:
                    Intent feedBack = new Intent(UserCenterActivity.this, FeedBackActivity.class);
                    startActivity(feedBack);
                    break;
                case R.id.txt_common_problem:
                    Intent commonProblem = new Intent(UserCenterActivity.this, CommonProblemActivity.class);
                    startActivity(commonProblem);
                    break;
                case R.id.txt_about:
                    Intent about = new Intent(UserCenterActivity.this, AboutActivity.class);
                    startActivity(about);
                    break;
                case R.id.txt_set:
                    Intent setting = new Intent(UserCenterActivity.this, SettingActivity.class);
                    startActivity(setting);
                    break;
                case R.id.txt_exit_account:
                    showExitAccoutDialog();
                    break;
                case R.id.exit_account_dialog_btn_cancel:
                    Object o = v.getTag();

                    if (o != null && o instanceof AlertDialog) {
                        AlertDialog dialog = (AlertDialog) o;
                        dialog.dismiss();
                    }
                    break;
                case R.id.exit_account_dialog_btn_confirm:
                    Object object = v.getTag();

                    if (object != null && object instanceof AlertDialog) {
                        AlertDialog dialog = (AlertDialog) object;
                        dialog.dismiss();
                    }
                    break;

            }
        }
    };

    private void showExitAccoutDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_exit_account_dialog, null);
        Button btnConfirm = (Button) view.findViewById(R.id.exit_account_dialog_btn_confirm);
        Button btnCancel = (Button) view.findViewById(R.id.exit_account_dialog_btn_cancel);
        AlertDialog exitAccoutDialog = new AlertDialog.Builder(this, R.style.dialog_exit_account)
                .setView(view)
                .setCancelable(true)
                .create();
        btnCancel.setTag(exitAccoutDialog);
        btnConfirm.setTag(exitAccoutDialog);
        btnCancel.setOnClickListener(mOnClickListener);
        btnConfirm.setOnClickListener(mOnClickListener);
        exitAccoutDialog.setCanceledOnTouchOutside(false);
        exitAccoutDialog.show();
    }

    private final static int MSG_SET_HEADER_PHOTO = 10000;
    private final static int MSG_DOWNLOAD_HEADER_PHOTO_COMPLETE = 10001;
    private final static int MSG_SET_USER_NICKNAME = 10002;
    private final static int MSG_INIT_USERINFO = 10003;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SET_USER_NICKNAME:

                    if (userInfo != null) {
                        String nickName = getString(R.string.txt_nick_name) + userInfo.getNickname();
                        nickName = UsefullUtill.autoSplitText(txtNickName, nickName);
                        LogUtil.e("nickName:" + nickName);
                        txtNickName.setText(nickName);
                    }
                    break;
                case MSG_SET_HEADER_PHOTO:
                    String imgUrl = userInfo.getAvatar();
                    LogUtil.e("imgUrl:" + imgUrl);
                    if (!TextUtils.isEmpty(imgUrl)) {
                        Bitmap bitmap = asyncBitmapLoader.loadBitmap(imgBtnHead, imgUrl, new AsyncBitmapLoader.ImageCallback() {
                            @Override
                            public void imageLoad(View view, Bitmap bitmap) {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("view", view);
                                map.put("bitmap", bitmap);
                                Message msg = new Message();
                                msg.what = MSG_DOWNLOAD_HEADER_PHOTO_COMPLETE;
                                msg.obj = map;
                                mHandler.sendMessage(msg);
                            }
                        });
                        if (bitmap != null) {
                            bitmap = UsefullUtill.toRoundBitmap(bitmap);
                            imgBtnHead.setImageBitmap(bitmap);
                        }
                    }
                    break;
                case MSG_DOWNLOAD_HEADER_PHOTO_COMPLETE:
                    if (msg.obj != null && msg.obj instanceof HashMap) {
                        HashMap map = (HashMap) msg.obj;
                        Object object = map.get("bitmap");
                        if (object != null && object instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) object;
                            bitmap = UsefullUtill.toRoundBitmap(bitmap);
                            imgBtnHead.setImageBitmap(bitmap);
                        }
                    }
                    break;
                case MSG_INIT_USERINFO:{
                    String nickName = getString(R.string.txt_nick_name) + getString(R.string.txt_nick_name_info);
                    String deviceTotal = 0 + getString(R.string.txt_device_total);

                    userInfo = MyApplication.getMyApplication().getUserInfo();

                    if (userInfo != null) {
                        nickName = getString(R.string.txt_nick_name) + userInfo.getNickname();
                        LogUtil.e("nickName:" + nickName);
                        mHandler.sendEmptyMessage(MSG_SET_USER_NICKNAME);
                        xDevices = DeviceManage.getInstance().getDevices();
                        if (xDevices != null) {
                            deviceTotal = xDevices.size() + getString(R.string.txt_device_total);
                            LogUtil.e("deviceTotal:" + deviceTotal);
                        }
                        mHandler.sendEmptyMessage(MSG_SET_HEADER_PHOTO);
                    } else {
                        mHandler.sendEmptyMessageDelayed(MSG_INIT_USERINFO, 100);
                    }
                    txtNickName.setText(nickName);
                    txtDeviceTotal.setText(deviceTotal);
                }
                break;

            }
        }
    };
}
