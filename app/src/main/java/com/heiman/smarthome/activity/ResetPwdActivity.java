package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.smarthome.MyApplication;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;

import org.apache.http.Header;

public class ResetPwdActivity extends BaseActivity implements View.OnClickListener{

    private EditText editOldPwd;
    private EditText editNewPwd;
    private Button btnComplete;
    private EditText editNewPwdNextTime;

    private static final int MSG_RESET_PWD_FAIL = 10000;
    private static final int MSG_RESET_PWD_SUCCEED = 10001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RESET_PWD_FAIL:
                    dismissHUMProgress();
                    String str = msg.getData().getString("str");
                    Toast.makeText(ResetPwdActivity.this, str, Toast.LENGTH_LONG).show();
                    break;
                case MSG_RESET_PWD_SUCCEED:
                    dismissHUMProgress();
                    Toast.makeText(ResetPwdActivity.this, R.string.reset_pwd_succeed, Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_reset_pwd);
        editOldPwd = (EditText) findViewById(R.id.edit_old_pwd);
        editNewPwd = (EditText) findViewById(R.id.edit_new_pwd);
        btnComplete = (Button) findViewById(R.id.btn_complete);
        editNewPwdNextTime = (EditText) findViewById(R.id.edit_new_pwd_next_time);

        btnComplete.setOnClickListener(this);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.reset_pwd));
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_reset_pwd, 500)) {
            LogUtil.e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_complete:
                String oldPwd = editOldPwd.getText().toString();
                String newPwd = editNewPwd.getText().toString();
                String newPwdNext = editNewPwdNextTime.getText().toString();
                if (TextUtils.isEmpty(newPwd) || newPwd.length() < 6 || newPwd.length() > 16) {
                    Toast.makeText(ResetPwdActivity.this, R.string.input_correct_format_pwd, Toast.LENGTH_LONG).show();
                    editOldPwd.setText("");
                    editNewPwd.setText("");
                    editNewPwdNextTime.setText("");
                    return;
                }

                if (TextUtils.isEmpty(newPwdNext) || !newPwd.equals(newPwdNext)) {
                    Toast.makeText(ResetPwdActivity.this, R.string.input_pwd_err, Toast.LENGTH_LONG).show();
                    editOldPwd.setText("");
                    editNewPwd.setText("");
                    editNewPwdNextTime.setText("");
                    return;
                }
                showHUDProgress();
                HttpManage.getInstance().resetPassword(MyApplication.getMyApplication(), newPwd, oldPwd, resetPwdCallback);
                break;
        }
    }

    private HttpManage.ResultCallback<String> resetPwdCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            if (error != null) {
                LogUtil.e("Code:" + error.getCode() + SmartHomeUtils.showHttpCode(error.getCode()));
                Message msg = new Message();
                msg.what = MSG_RESET_PWD_FAIL;
                Bundle bundle = new Bundle();
                bundle.putString("str", getString(R.string.reset_pwd_fail) + SmartHomeUtils.showHttpCode(error.getCode()));
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }

        @Override
        public void onSuccess(int code, String response) {
            LogUtil.e("code:" + code + "\tresponse:" + response);
            mHandler.sendEmptyMessage(MSG_RESET_PWD_SUCCEED);
        }
    };
}
