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

public class EmailGetBackPasswordActivity extends BaseActivity implements View.OnClickListener, TextWatcher{

    private EditText editEmail;
    private Button btnSubmit;
    private TextView txtGetBackPasswordUsePhone;

    private final static int MSG_GET_BACK_PASSWORD_FAIL = 10003;
    private final static int MSG_GET_BACK_PASSWORD_SUCCEED = 10004;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GET_BACK_PASSWORD_FAIL:
                    dismissHUMProgress();
                    if (msg.obj != null) {
                        Toast.makeText(EmailGetBackPasswordActivity.this, getString(R.string.toast_get_back_password_fail) + ":" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EmailGetBackPasswordActivity.this, getString(R.string.toast_get_back_password_fail) + "！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MSG_GET_BACK_PASSWORD_SUCCEED:
                    dismissHUMProgress();
                    Toast.makeText(EmailGetBackPasswordActivity.this, getString(R.string.toast_get_back_password_succeed) + getString(R.string.toast_click_activation_email), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_email_get_back_password);
        setContentLayout(R.layout.activity_email_get_back_password);
        editEmail = (EditText) findViewById(R.id.edit_email);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        txtGetBackPasswordUsePhone = (TextView) findViewById(R.id.txt_use_phone_get_back_password);

        btnSubmit.setOnClickListener(this);
        txtGetBackPasswordUsePhone.setOnClickListener(this);

        editEmail.addTextChangedListener(this);

        btnSubmit.setEnabled(false);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.email_get_back_password));
//        setReturnImage(R.drawable.back_black);
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_email_get_back_password, 500)) {
            LogUtil.e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_submit:
                String user = editEmail.getText().toString();

                if (TextUtils.isEmpty(user) || !SmartHomeUtils.isEmial(user)) {
                    Toast.makeText(EmailGetBackPasswordActivity.this, R.string.input_email_address, Toast.LENGTH_SHORT).show();
                    return;
                }
                showHUDProgress();
                getBackPassword(user);
                break;
            case R.id.txt_use_phone_get_back_password:
                Intent phoneGetBackPasswordIntent = new Intent(EmailGetBackPasswordActivity.this, PhoneGetBackPasswordActivity.class);
                startActivity(phoneGetBackPasswordIntent);
                finish();
                break;
        }
    }

    private HttpManage.ResultCallback<String> getBackPasswordCallback = new HttpManage.ResultCallback<String>() {
        @Override
        public void onError(Header[] headers, HttpManage.Error error) {
            Message msg = new Message();
            msg.what = MSG_GET_BACK_PASSWORD_FAIL;
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
            mHandler.sendEmptyMessage(MSG_GET_BACK_PASSWORD_SUCCEED);
        }
    };

    private void getBackPassword(String user) {
        HttpManage.getInstance().forgetbyEmailPasswd(MyApplication.getMyApplication(), user, getBackPasswordCallback);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String user = editEmail.getText().toString();

        if (TextUtils.isEmpty(user)) {
            btnSubmit.setEnabled(false);
            return;
        }
        btnSubmit.setEnabled(true);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
