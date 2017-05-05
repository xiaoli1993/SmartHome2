package com.heiman.smarthome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.smarthomesdk.http.HttpManage;
import com.heiman.smarthome.smarthomesdk.utils.SmartHomeUtils;
import com.heiman.widget.bottomnavigation.AlphaTabsIndicator;
import com.orhanobut.hawk.Hawk;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private AlphaTabsIndicator alphaTabsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doLogin();
        initDevice();
        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
//        alphaTabsIndicator.setViewPager(mViewPger);

        alphaTabsIndicator.getTabView(0).showNumber(6);
        alphaTabsIndicator.getTabView(1).showNumber(888);
        alphaTabsIndicator.getTabView(2).showNumber(88);
        alphaTabsIndicator.getTabView(3).showPoint();
    }

    private void doLogin() {
        HttpManage.getInstance().doLogin(MyApplication.getMyApplication(), "554674787@qq.com", "xiaoli.", new HttpManage.ResultCallback<Map<String, String>>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {

            }

            @Override
            public void onSuccess(int code, Map<String, String> response) {
                String authKey = (String) response.get("authorize");
                String accessToken = (String) response.get("access_token");
                int appid = Integer.parseInt(response.get("user_id"));
                Hawk.put("appId", appid);
                Hawk.put("authKey", authKey);
//                SharedPreferencesUtil.keepShared("appId", appid);
//                SharedPreferencesUtil.keepShared("authKey", authKey);
                MyApplication.getMyApplication().setAccessToken(accessToken);
                MyApplication.getMyApplication().setAppid(appid);
                MyApplication.getMyApplication().setAuthKey(authKey);
                startActivity(new Intent(MainActivity.this, MainHomeActivity.class));
            }
        });
    }

    private void initDevice() {
        HttpManage.getInstance().getSubscribe(MyApplication.getMyApplication(), new HttpManage.ResultCallback<String>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                MyApplication.getLogger().e("获取订阅设备出错:" + error.getMsg() + "\t" + error.getCode());
            }

            @Override
            public void onSuccess(int code, String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray list = object.getJSONArray("list");
                    int iSize = list.length();
                    for (int i = 0; i < iSize; i++) {
                        JSONObject jsonObj = list.getJSONObject(i);
                        SmartHomeUtils.subscribeToDevice(jsonObj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    int MAX_LIMIT = 0;
    int MAX_Refresh = 100;
    public boolean isRefreshMessage = true;

    private void getMessage(final String CreateDate) {
        try {
            HttpManage.getInstance().GetMessagesID(MyApplication.getMyApplication(), MAX_LIMIT + "", MAX_Refresh + "", SmartHomeUtils.getdeviceid(), CreateDate, new HttpManage.ResultCallback<String>() {
                @Override
                public void onError(Header[] headers, HttpManage.Error error) {
                    MyApplication.getLogger().e("获取消息失败:" + error.getMsg() + "\t" + error.getCode());
                }

                @Override
                public void onSuccess(int code, String response) {
                    MyApplication.getLogger().json(response);
//                    Gson gaon = new Gson();
//                    Messages messages = gaon.fromJson(response, Messages.class);
//                    if (messages.getCount()==0){
//
//                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
