package com.heiman.smarthome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.mode.Messages;
import com.heiman.smarthome.smarthomesdk.http.HttpManage;
import com.heiman.smarthome.utils.SmartHomeUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDevice();
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
                    Gson gaon = new Gson();
                    Messages messages = gaon.fromJson(response, Messages.class);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
