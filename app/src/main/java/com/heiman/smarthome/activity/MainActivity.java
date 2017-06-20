package com.heiman.smarthome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.RoomManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.Room;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.devicecommon.ShareDeviceActivity;
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
import com.heiman.widget.imageview.CircleImageView;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.greenrobot.event.EventBus;
import io.xlink.wifi.sdk.XlinkAgent;


public class MainActivity extends GwBaseActivity {

    private AlphaTabsIndicator alphaTabsIndicator;
    private DrawerLayout drawerLayout;
    private RelativeLayout rlLeft;
    private FrameLayout mFrmeLayout;
    private ListView leftListview;
    private CircleImageView imageUserAvatar;
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
    private XlinkDevice selectedGateway;

    public XlinkDevice getSelectedGateway() {
        return selectedGateway;
    }

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
        initDevice();
        startCustomService();
        initWidget();
        setSelect(0);
        initData();
        initEven();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void deviceData(String dataString) {

    }

    private void initDevice() {
        DataSupport.findAllAsync(XlinkDevice.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                List<XlinkDevice> xlinkDeviceList = (List<XlinkDevice>) t;
                for (XlinkDevice xlinkDevice : xlinkDeviceList) {
                    MyApplication.getLogger().i("设备:" + xlinkDevice.getDeviceMac() + xlinkDevice.getAccessAESKey());
                    DeviceManage.getInstance().addDevice(xlinkDevice);
                    setDevice(xlinkDevice);
                    if (xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW) {
                        if (SmartHomeUtils.isEmptyString(xlinkDevice.getAccessAESKey())) {
                            List<String> OID = new ArrayList<String>();
                            OID.add(HeimanCom.COM_GW_OID.GET_AES_KEY);
                            String sb = HeimanCom.getOID(SmartPlug.mgetSN(), 0, OID);
                            BaseApplication.getLogger().json(sb);
                            sendData(sb, false);
                        }
                    }
                }
            }
        });
        DataSupport.findAllAsync(SubDevice.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                List<SubDevice> subDeviceList = (List<SubDevice>) t;
                for (SubDevice subDevice : subDeviceList) {
                    SubDeviceManage.getInstance().addDevice(subDevice);
                }
            }
        });
        HttpManage.getInstance().ckData(MyApplication.getMyApplication(), "", "Room", new HttpManage.ResultCallback<String>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                MyApplication.getLogger().e("msg:" + error.getMsg() + "\tcode:" + error.getCode());
            }

            @Override
            public void onSuccess(int code, String response) {
                MyApplication.getLogger().json(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray list = jsonObject.getJSONArray("list");
                    int iSize = list.length();
                    for (int i = 0; i < iSize; i++) {
                        JSONObject jsonObj = list.getJSONObject(i);
                        Gson gson = new Gson();
                        Room room = gson.fromJson(jsonObj.toString(), Room.class);
                        MyApplication.getLogger().i("room_name:" + room.getRoom_name() + "\tcreator:" + room.getCreator() + "\nURL:" + room.getRoom_bg_url());
                        RoomManage.getInstance().addHome(room);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        startActivityForName("com.heiman.home.HomeListActivity");
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
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }
            }
        });
        initSpinnerGW();
    }

    private void initSpinnerGW() {
        List<XlinkDevice> gateWays = new ArrayList<>();
        List<XlinkDevice> getDevices = DeviceManage.getInstance().getDevices();
        for (int i = 0; i < getDevices.size(); i++) {
            if (getDevices.get(i).getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY || getDevices.get(i).getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW || getDevices.get(i).getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW) {
                gateWays.add(getDevices.get(i));
            }
        }
        spinnerGw.setTextColor(getResources().getColor(R.color.class_V));
        if (!SmartHomeUtils.isEmptyList(gateWays)) {
            spinnerGw.setItems(gateWays);
            spinnerGw.setTag(gateWays);
            selectedGateway = gateWays.get(0);
            device = selectedGateway;

            /**增加测试数据子设备**/
            List<SubDevice> subDeviceList = SubDeviceManage.getInstance().getDevices();
            if (subDeviceList.size() <= 0) {
                SubDevice subDevice = new SubDevice();
                subDevice.setWifiDevice(device);
                subDevice.setDeviceMac(device.getDeviceMac());
                subDevice.setZigbeeMac("aabbcceeffdd");
                subDevice.setId(1231456);
                subDevice.setIndex(1231456);
                subDevice.setDate(new Date());
                subDevice.setAQI("");
                subDevice.setDeviceName("虚拟的温湿度传感器");
                subDevice.setDeviceType(Constant.DEVICE_TYPE.DEVICE_ZIGBEE_THP);
                SubDeviceManage.getInstance().addDevice(subDevice);

                SubDevice subDevice2 = new SubDevice();
                subDevice2.setWifiDevice(device);
                subDevice2.setDeviceMac(device.getDeviceMac());
                subDevice2.setZigbeeMac("aabbccddeeff");
                subDevice2.setId(12314567);
                subDevice2.setIndex(12314567);
                subDevice2.setDate(new Date());
                subDevice2.setAQI("");
                subDevice2.setDeviceName("虚拟的计量插座");
                subDevice2.setDeviceType(Constant.DEVICE_TYPE.DEVICE_ZIGBEE_METRTING_PLUGIN);
                SubDeviceManage.getInstance().addDevice(subDevice2);
            }
            /**增加测试数据子设备**/
        }
        spinnerGw.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<XlinkDevice>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, XlinkDevice item) {
                XlinkUtils.shortTips(MyApplication.getMyApplication(), "点击：" + (item == null ? "null" : item.toString()), getResources().getColor(R.color.class_J), getResources().getColor(R.color.white), 0, false);
                List gateWay = (List) spinnerGw.getTag();
                selectedGateway = (XlinkDevice) gateWay.get(spinnerGw.getSelectedIndex());
                device = selectedGateway;
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

        leftMainList.add(new LeftMain(R.drawable.main_right_news, getString(R.string.news), 0));
        leftMainList.add(new LeftMain(R.drawable.main_right_home, getString(R.string.Family_management), 0));
        leftMainList.add(new LeftMain(R.drawable.main_right_share, getString(R.string.Device_sharing), 0));
        leftMainList.add(new LeftMain(R.drawable.main_right_pod, getString(R.string.Product_manual), 0));
        leftMainList.add(new LeftMain(R.drawable.main_right_settings, getString(R.string.Set_up), 0));


        tvNikeName.setText(MyApplication.getMyApplication().getUserInfo().getNickname());
        if (!SmartHomeUtils.isEmptyString(MyApplication.getMyApplication().getUserInfo().getEmail())) {
            tvDeviceNumber.setText(MyApplication.getMyApplication().getUserInfo().getEmail());
        }
        if (!SmartHomeUtils.isEmptyString(MyApplication.getMyApplication().getUserInfo().getPhone())) {
            tvDeviceNumber.setText(MyApplication.getMyApplication().getUserInfo().getPhone());
        }
        if (!SmartHomeUtils.isEmptyString(MyApplication.getMyApplication().getUserInfo().getAvatar())) {
            Glide.with(MainActivity.this).load(MyApplication.getMyApplication().getUserInfo().getAvatar())
                    .centerCrop()
                    .dontAnimate()
                    .priority(Priority.NORMAL)
                    .placeholder(R.drawable.main_right_image_head)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageUserAvatar);
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
        imageUserAvatar = (CircleImageView) findViewById(R.id.image_user_avatar);
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

    @Override
    public void onClickListener(View v) {

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

    public void onEventMainThread(String event) {
        String msg = "onEventMainThread收到了消息：" + event;
        if (!TextUtils.isEmpty(event) && "get bind devices".equals(event)) {
            initSpinnerGW();
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
