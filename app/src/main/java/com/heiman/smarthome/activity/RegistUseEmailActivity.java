package com.heiman.smarthome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;

import org.apache.http.Header;

public class RegistUseEmailActivity extends BaseActivity implements View.OnClickListener, TextWatcher{

    private EditText editEmail;
    private EditText editPassword;
    private EditText editPasswordNextTime;
    private Button btnRegist;
    private TextView txtRegistUsePhone;
    private TextView txtPasswordRemind;
    private TextView txtPasswordNextTimeRemind;

    private final static int MSG_REGIST_FAIL = 10003;
    private final static int MSG_REGIST_SUCCEED = 10004;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGIST_FAIL:
                    dismissHUMProgress();
                    if (msg.obj != null) {
                        Toast.makeText(RegistUseEmailActivity.this, getString(R.string.toast_regist_fail) + ":" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegistUseEmailActivity.this, getString(R.string.toast_regist_fail) + "！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MSG_REGIST_SUCCEED:
                    dismissHUMProgress();
                    Toast.makeText(RegistUseEmailActivity.this, getString(R.string.toast_regist_succeed) + getString(R.string.toast_click_activation_email), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_regist_use_email);
        setContentLayout(R.layout.activity_regist_use_email);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editPasswordNextTime = (EditText) findViewById(R.id.edit_password_next_time);
        btnRegist = (Button) findViewById(R.id.btn_regist);
        txtRegistUsePhone = (TextView) findViewById(R.id.txt_use_phone_regist);
        txtPasswordRemind = (TextView) findViewById(R.id.txt_password_remind);
        txtPasswordNextTimeRemind = (TextView) findViewById(R.id.txt_password_next_time_remind);

        btnRegist.setOnClickListener(this);
        txtRegistUsePhone.setOnClickListener(this);

        editEmail.addTextChangedListener(this);
        editPassword.addTextChangedListener(this);
        editPasswordNextTime.addTextChangedListener(this);

        btnRegist.setEnabled(false);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.regist_use_email));
//        setReturnImage(R.drawable.back_black);
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_regist_use_email, 500)) {
            LogUtil.e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_regist: {
                String user = editEmail.getText().toString();

                if (TextUtils.isEmpty(user) || !SmartHomeUtils.isEmial(user)) {
                    Toast.makeText(RegistUseEmailActivity.this, R.string.input_email_address, Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = editPassword.getText().toString();
                String password2 = editPasswordNextTime.getText().toString();

                if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
                    Toast.makeText(RegistUseEmailActivity.this, R.string.input_correct_format_pwd, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(password2)) {
                    Toast.makeText(RegistUseEmailActivity.this, R.string.input_pwd_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                showHUDProgress();
                regist(user, user, password, "");
            }
            break;
            case R.id.txt_use_phone_regist:
                Intent usePhoneRegistIntent = new Intent(RegistUseEmailActivity.this, RegistActivity.class);
                startActivity(usePhoneRegistIntent);
                finish();
                break;

        }
    }

    private HttpManage.ResultCallback<String> registCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            Message msg = new Message();
            msg.what = MSG_REGIST_FAIL;
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
            mHandler.sendEmptyMessage(MSG_REGIST_SUCCEED);
        }
    };

    private void regist(String user, String name, String pwd, String code) {
        HttpManage.getInstance().registerUser(MyApplication.getMyApplication(), user, name, pwd, code, "zh-cn", registCallback);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String user = editEmail.getText().toString();

        if (TextUtils.isEmpty(user)) {
            btnRegist.setEnabled(false);
            return;
        }
        String password = editPassword.getText().toString();
        String password2 = editPasswordNextTime.getText().toString();

        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
            btnRegist.setEnabled(false);
            return;
        }

        if (TextUtils.isEmpty(password2) || password2.length() < 6 || password2.length() > 16) {
            btnRegist.setEnabled(false);
            return;
        }
        btnRegist.setEnabled(true);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (editPassword.getText() == s) {
            String password = editPassword.getText().toString();
            if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
                txtPasswordRemind.setVisibility(View.VISIBLE);
                return;
            }
            txtPasswordRemind.setVisibility(View.GONE);
        }

        if (editPasswordNextTime.getText() == s) {
            String password = editPasswordNextTime.getText().toString();
            if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16) {
                txtPasswordNextTimeRemind.setVisibility(View.VISIBLE);
                return;
            }
            txtPasswordNextTimeRemind.setVisibility(View.GONE);
        }
    }
}
