package com.heiman.smarthome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.HeimanServer;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.adapter.MainLeftAdapter;
import com.heiman.smarthome.fragment.AutomationFragment;
import com.heiman.smarthome.fragment.HomeFragment;
import com.heiman.smarthome.fragment.RoomFragment;
import com.heiman.smarthome.fragment.SceneFragment;
import com.heiman.smarthome.modle.LeftMain;
import com.heiman.widget.bottomnavigation.AlphaTabsIndicator;
import com.heiman.widget.bottomnavigation.OnTabChangedListner;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.xlink.wifi.sdk.XlinkAgent;


public class MainActivity extends FragmentActivity {

    private AlphaTabsIndicator alphaTabsIndicator;
    private DrawerLayout drawerLayout;
    private RelativeLayout rlLeft;
    private FrameLayout mFrmeLayout;
    private ListView leftListview;
    private ImageView imageUserAvatar;
    private TextView tvNikeName;
    private TextView tvDeviceNumber;
    private LinearLayout llRoomTemp;
    private ImageView imageRoomTemp;
    private TextView tvRoomTemp;
    private LinearLayout llOutdoorTemp;
    private ImageView imageOutdoorTemp;
    private TextView tvOutdoorTemp;
    private MaterialSpinner spinnerGw;

    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;
    private MainLeftAdapter adapter;
    private List<LeftMain> leftMainList;

    public static String timeZone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        MyApplication.getLogger().i("时区1：" + TimeZone.getTimeZone("GMT"));
        String timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
        MyApplication.getLogger().i("时区2：" + calendar.getTime());
        MyApplication.getLogger().i("时区3：" + timeZone);
        return timeZone.substring(0, 3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getLogger().i("时区3：" + timeZone());
        if (!XlinkAgent.getInstance().isConnectedLocal()) {
            XlinkAgent.getInstance().start();
        }
        if (!XlinkAgent.getInstance().isConnectedOuterNet()) {
            XlinkAgent.getInstance().login(MyApplication.getMyApplication().getAppid(), MyApplication.getMyApplication().getAuthKey());
        }
        startCustomService();
        initWidget();
        initDevice();
        setSelect(0);
        initData();
        initEven();
    }

    private void initEven() {
        adapter = new MainLeftAdapter(this, leftMainList);
        leftListview.setAdapter(adapter);
        leftListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        Bundle paramBundle = new Bundle();
//                        paramBundle.putString(Constant.DEVICE_MAC, "abc123456");
//                        paramBundle.putBoolean(Constant.IS_SUB, false);
//                        startActivityForName("com.heiman.gateway.GwActivity", paramBundle);
//                        startActivity(new Intent(MainActivity.this, DeviceListActivity.class));
                        Intent informationIntent = new Intent(MainActivity.this, InformationActivity.class);
                        startActivity(informationIntent);
                        break;
                    case 1:
//                        startActivity(new Intent(MainActivity.this, ShareDeviceActivity.class));
                        break;
                    case 2:
//                        feedbackeAgent.startDefaultThreadActivity();
                        Intent shareDeviceIntent = new Intent(MainActivity.this, ShareDeviceActivity.class);

                        if (spinnerGw.getTag() == null || !(spinnerGw.getTag() instanceof List)) {
                            Toast.makeText(MainActivity.this, R.string.toast_you_do_not_have_gateways, Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            List gateWay = (List) spinnerGw.getTag();
                            XlinkDevice select = (XlinkDevice) gateWay.get(spinnerGw.getSelectedIndex());
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(Constant.IS_DEVICE, true);
                            bundle.putString(Constant.DEVICE_MAC, select.getDeviceMac());
                            shareDeviceIntent.putExtras(bundle);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, R.string.toast_you_do_not_have_gateways, Toast.LENGTH_LONG).show();

                            if (e != null) {
                                BaseApplication.getLogger().e(e.getMessage());
                            }
                            return;
                        }

                        startActivity(shareDeviceIntent);
                        break;
                    case 3:

                        break;
                    case 4:
//                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                }
            }
        });
        List<XlinkDevice> gateWays = new ArrayList<>();
        List<XlinkDevice> getDevices = DeviceManage.getInstance().getDevices();
        for (int i = 0; i < getDevices.size(); i++) {
            if (getDevices.get(i).getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY) {
                gateWays.add(getDevices.get(i));
            }
        }
        spinnerGw.setTextColor(getResources().getColor(R.color.class_V));
        if (!SmartHomeUtils.isEmptyList(gateWays)) {
            spinnerGw.setItems(gateWays);
            spinnerGw.setTag(gateWays);
        }
        spinnerGw.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<XlinkDevice>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, XlinkDevice item) {
                XlinkUtils.shortTips(MyApplication.getMyApplication(), "点击：" + (item == null ? "null" : item.toString()), getResources().getColor(R.color.class_J), getResources().getColor(R.color.white), 0, false);
            }
        });
    }

    // 启动服务
    private void startCustomService() {
        Intent intent = new Intent(this, HeimanServer.class);
        startService(intent);
    }

    private void initData() {
        leftMainList = new ArrayList<LeftMain>();

        leftMainList.add(new LeftMain(R.drawable.main_right_news, "消息", 0));
        leftMainList.add(new LeftMain(R.drawable.main_right_home, "家庭管理", 0));
        leftMainList.add(new LeftMain(R.drawable.main_right_share, "设备分享", 0));
        leftMainList.add(new LeftMain(R.drawable.main_right_pod, "产品手册", 0));
        leftMainList.add(new LeftMain(R.drawable.main_right_settings, "设置", 0));


        tvNikeName.setText(MyApplication.getMyApplication().getUserInfo().getNickname());
        if (!SmartHomeUtils.isEmptyString(MyApplication.getMyApplication().getUserInfo().getEmail())) {
            tvDeviceNumber.setText(MyApplication.getMyApplication().getUserInfo().getEmail());
        }
        if (!SmartHomeUtils.isEmptyString(MyApplication.getMyApplication().getUserInfo().getPhone())) {
            tvDeviceNumber.setText(MyApplication.getMyApplication().getUserInfo().getPhone());
        }
    }

    public void openDrawers() {
        drawerLayout.openDrawer(rlLeft);
    }

    private void initWidget() {
        alphaTabsIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        rlLeft = (RelativeLayout) findViewById(R.id.rl_left);
        mFrmeLayout = (FrameLayout) findViewById(R.id.mFrmeLayout);
        leftListview = (ListView) findViewById(R.id.left_listview);
        imageUserAvatar = (ImageView) findViewById(R.id.image_user_avatar);
        tvNikeName = (TextView) findViewById(R.id.tv_NikeName);
        tvDeviceNumber = (TextView) findViewById(R.id.tv_device_number);
        llRoomTemp = (LinearLayout) findViewById(R.id.ll_room_temp);
        imageRoomTemp = (ImageView) findViewById(R.id.image_room_temp);
        tvRoomTemp = (TextView) findViewById(R.id.tv_room_temp);
        llOutdoorTemp = (LinearLayout) findViewById(R.id.ll_outdoor_temp);
        imageOutdoorTemp = (ImageView) findViewById(R.id.image_outdoor_temp);
        tvOutdoorTemp = (TextView) findViewById(R.id.tv_outdoor_temp);
        spinnerGw = (MaterialSpinner) findViewById(R.id.spinner_gw);

        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, getResources().getColor(R.color.white));
        alphaTabsIndicator.getTabView(0).showNumber(6);
        alphaTabsIndicator.getTabView(1).showNumber(888);
        alphaTabsIndicator.getTabView(2).showNumber(88);
        alphaTabsIndicator.getTabView(3).showPoint();
        alphaTabsIndicator.setOnTabChangedListner(new OnTabChangedListner() {
            @Override
            public void onTabSelected(int tabNum) {
                MyApplication.getLogger().i("点击第" + tabNum + "个");
                setSelect(tabNum);
            }
        });

        imageUserAvatar.setOnClickListener(onClickListener);

    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new HomeFragment();
                    transaction.add(R.id.mFrmeLayout, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new SceneFragment();
                    transaction.add(R.id.mFrmeLayout, mTab02);
                } else {
                    transaction.show(mTab02);

                }
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new AutomationFragment();
                    transaction.add(R.id.mFrmeLayout, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new RoomFragment();
                    transaction.add(R.id.mFrmeLayout, mTab04);
                } else {
                    transaction.show(mTab04);
                }
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }
    }

    private void initDevice() {
        HttpManage.getInstance().getSubscribe(MyApplication.getMyApplication(), new HttpManage.ResultCallback<String>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                MyApplication.getLogger().e("获取订阅设备出错:" + error.getMsg() + "\t" + error.getCode());
            }

            @Override
            public void onSuccess(int code, String response) {
                MyApplication.getLogger().json(response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray list = object.getJSONArray("list");
                    int iSize = list.length();
                    for (int i = 0; i < iSize; i++) {
                        JSONObject jsonObj = list.getJSONObject(i);
                        SmartHomeUtils.subscribeToDevice(jsonObj.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 通过包名跳转
     *
     * @param activityName
     */
    public void startActivityForName(String activityName, Bundle paramBundle) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(this, clazz);
            if (paramBundle != null)
                intent.putExtras(paramBundle);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void startActivityForName(String activityName) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_user_avatar:
                    Intent userCenterIntent = new Intent(MainActivity.this, UserCenterActivity.class);
                    startActivity(userCenterIntent);
                    break;
            }
        }
    };
}
