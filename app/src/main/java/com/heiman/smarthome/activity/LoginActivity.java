package com.heiman.smarthome.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.mode.UserInfo;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.heiman.widget.button.FButton;
import com.heiman.widget.edittext.ClearEditText;
import com.heiman.widget.swipeback.CloseActivityClass;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.orhanobut.hawk.Hawk;

import org.apache.http.Header;

import java.util.Map;


/**
 * @Author : 肖力
 * @Time :  2017/5/8 10:27
 * @Description : 登录界面
 * @Modify record :
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView logo;
    private ScrollView scrollView;
    private ClearEditText et_mobile;
    private ClearEditText et_password;
    private ImageView iv_show_pwd;
    private FButton btn_login;
    private TextView forget_password;
    private TextView regist;
    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private View service;
    private int height = 0;
    private KProgressHUD hud;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置输入法不弹起
        setContentView(R.layout.activity_login);
        CloseActivityClass.activityList.add(this);
//        StatusBarUtil.setTranslucent(this, 50);
        intiView();
        initListener();

    }

    private void intiView() {
        logo = (ImageView) findViewById(R.id.logo);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        et_mobile = (ClearEditText) findViewById(R.id.et_mobile);
        et_password = (ClearEditText) findViewById(R.id.et_password);
        iv_show_pwd = (ImageView) findViewById(R.id.iv_show_pwd);
        btn_login = (FButton) findViewById(R.id.btn_login);
        forget_password = (TextView) findViewById(R.id.tv_forget_password);
        regist = (TextView) findViewById(R.id.tv_regist);
        service = findViewById(R.id.service);
//        btn_login.setShadowEnabled(true);
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
        btn_login.setEnabled(false);
    }

    private void initListener() {
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
            }
        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PhoneGetBackPasswordActivity.class));
            }
        });
        iv_show_pwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_mobile.getText().toString())) {
                    btn_login.setEnabled(false);
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    btn_login.setEnabled(false);
                    return;
                }
                btn_login.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_mobile.getText().toString())) {
                    btn_login.setEnabled(false);
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    btn_login.setEnabled(false);
                    return;
                }
                btn_login.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    return;
//                if (!s.toString().matches("[A-Za-z0-9]+")) {
//                    String temp = s.toString();
//                    s.delete(temp.length() - 1, temp.length());
//                    et_password.setSelection(s.length());
//                }
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        findViewById(R.id.root).addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getHeight());
                        }
                    }, 0);
                    zoomIn(logo, (oldBottom - bottom) - keyHeight);
                    service.setVisibility(View.INVISIBLE);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.smoothScrollTo(0, scrollView.getHeight());
                        }
                    }, 0);
                    zoomOut(logo, (bottom - oldBottom) - keyHeight);
                    service.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 缩小
     *
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();
    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomOut(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(200);
        mAnimatorSet.start();
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_login, 500)) {
            LogUtil.e("点击过快！");
            return;
        }
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(getString(R.string.Logins))
                        .setCancellable(true).show();
                onLogin(et_mobile.getText().toString().trim(), et_password.getText().toString().trim());
                break;
            case R.id.iv_show_pwd:
                if (et_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_show_pwd.setImageResource(R.drawable.show_password);
                } else {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_show_pwd.setImageResource(R.drawable.hide_password);
                }
                String pwd = et_password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    et_password.setSelection(pwd.length());
                break;
        }
    }


    /**
     * 登录
     *
     * @param ClientName 用户名
     * @param Password   密码
     */

    private void onLogin(final String ClientName, final String Password) {
        HttpManage.getInstance().doLogin(MyApplication.getMyApplication(), ClientName, Password, new HttpManage.ResultCallback<Map<String, String>>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                MyApplication.getLogger().e("Code:" + error.getCode() + SmartHomeUtils.showHttpCode(error.getCode()));
                hud.dismiss();
                XlinkUtils.shortTips(MyApplication.getMyApplication(), SmartHomeUtils.showHttpCode(error.getCode()), 0, 0, 0, false);
            }

            @Override
            public void onSuccess(int i, Map<String, String> stringStringMap) {
                String authKey = stringStringMap.get("authorize");
                String accessToken = stringStringMap.get("access_token");
                int appid = Integer.parseInt(stringStringMap.get("user_id"));
                String refresh_token = stringStringMap.get("refresh_token");

                MyApplication.getMyApplication().setAccessToken(accessToken);
                MyApplication.getMyApplication().setAppid(appid);
                MyApplication.getMyApplication().setAuthKey(authKey);
                MyApplication.getMyApplication().setRefresh_token(refresh_token);

                MyApplication.getLogger().i("Auth", appid + "authKey:" + authKey + "refresh_token:" + refresh_token);
                Hawk.put(Constant.APPID, appid);
                Hawk.put(Constant.AUTHKEY, authKey);

                Hawk.put(Constant.MY_ACCOUNT, ClientName);
                Hawk.put(Constant.MY_PASSWORD, Password);

                Message message = new Message();
                message.what = 1;
                myHandler.sendMessage(message);

            }
        });


    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    HttpManage.getInstance().getUserInfo(MyApplication.getMyApplication(), new HttpManage.ResultCallback<String>() {
                        @Override
                        public void onError(Header[] headers, HttpManage.Error error) {
                            MyApplication.getLogger().e(error.getMsg() + "\t" + error.getCode());
                        }

                        @Override
                        public void onSuccess(int code, String response) {
                            MyApplication.getLogger().json(response);
                            hud.dismiss();
                            Gson gson = new Gson();
                            MyApplication.getMyApplication().setUserInfo(gson.fromJson(response, UserInfo.class));
                            Hawk.put("UserInfo", MyApplication.getMyApplication().getUserInfo());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
