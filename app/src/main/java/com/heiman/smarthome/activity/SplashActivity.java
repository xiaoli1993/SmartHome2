package com.heiman.smarthome.activity;
/**
 * Copyright ©深圳市海曼科技有限公司
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.mode.UserInfo;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.hawk.Hawk;

import org.apache.http.Header;

import java.util.Map;


/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/2/17 11:37
 * @Description :
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtil.setTranslucent(this, 50);
        setLoginCacheView();

    }

    private void setLoginCacheView() {
        boolean contains = Hawk.contains(Constant.MY_ACCOUNT);
        boolean ispass = Hawk.contains(Constant.MY_PASSWORD);
        MyApplication.getLogger().i("contains:" + contains + "ispass:" + ispass);
        if (contains && ispass) {
            String ClientName = Hawk.get(Constant.MY_ACCOUNT);
            String Password = Hawk.get(Constant.MY_PASSWORD);
            onLogin(ClientName, Password);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    splash2Activity();
                }
            }, 1500);
        }
    }

    private void splash2Activity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    private void onLogin(String ClientName, String Password) {
        HttpManage.getInstance().doLogin(MyApplication.getMyApplication(), ClientName, Password, new HttpManage.ResultCallback<Map<String, String>>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                MyApplication.getLogger().e("Code:" + error.getCode());
                splash2Activity();
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
                            Gson gson = new Gson();
                            MyApplication.getMyApplication().setUserInfo(gson.fromJson(response, UserInfo.class));
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
