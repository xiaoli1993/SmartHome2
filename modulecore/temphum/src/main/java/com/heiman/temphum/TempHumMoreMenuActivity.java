package com.heiman.temphum;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.mode.MoreMenu;
import com.heiman.devicecommon.CheckFirmwareUpgradeActivity;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;

public class TempHumMoreMenuActivity extends GwBaseActivity {

    private FamiliarRecyclerView recyclerDevice;
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;

    private List<MoreMenu> moreMenuList;

    private FamiliarEasyAdapter<MoreMenu> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temphum_activity_temp_hum_more_menu);
        initView();
        initMenuData();
        initEven();
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (subDevice != null) {
            subDevice.setWifiDevice(device);
            titleBarTitle.setText(subDevice.getDeviceName());
        }
        titleBarReturn.setImageResource(R.drawable.personal_back);
    }

    @Override
    public void deviceData(String dataString) {
        LogUtil.e("dataString:" + dataString);
    }

    @Override
    public void onClickListener(View v) {
        if (!UsefullUtill.judgeClick(R.layout.temphum_activity_temp_hum_more_menu, 500)) {
            LogUtil.e("点击过快！");
            return;
        }

        if (v.getId() == titleBarReturn.getId()) {
            finish();
            return;
        }
    }

    private void initMenuData() {
        moreMenuList = new ArrayList<MoreMenu>();
        moreMenuList.add(new MoreMenu(getString(R.string.Common_problem), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Product_manual), "", true));
        if (subDevice != null) {
            moreMenuList.add(new MoreMenu(getString(R.string.Owned_room), subDevice.getRoomID(), true));
        } else {
            moreMenuList.add(new MoreMenu(getString(R.string.Owned_room), "", true));
        }
        if (subDevice != null) {
            moreMenuList.add(new MoreMenu(getString(R.string.Modify_name), subDevice.getDeviceName(), true));
        } else {
            moreMenuList.add(new MoreMenu(getString(R.string.Modify_name), "", true));
        }
        moreMenuList.add(new MoreMenu(getString(R.string.alarm_record), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.alarm_condition_setting), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Delete_device), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Check_firmware_upgrade), "", true));
        if (subDevice != null) {
            moreMenuList.add(new MoreMenu(getString(R.string.device_mac_address), subDevice.getZigbeeMac(), true));
        } else {
            moreMenuList.add(new MoreMenu(getString(R.string.device_mac_address), "", true));
        }

        if (subDevice != null) {
            moreMenuList.add(new MoreMenu(getString(R.string.residual_electricity), subDevice.getBatteryPercent() + "%", true));
        } else {
            moreMenuList.add(new MoreMenu(getString(R.string.residual_electricity), "", true));
        }
    }


    private void initEven() {
        titleBarReturn.setOnClickListener(this);
        titleBarMore.setVisibility(View.GONE);
        titleBarShare.setVisibility(View.GONE);
        titleBarTitle.setText(R.string.more);
        titleBar.setBackgroundColor(getResources().getColor(R.color.white));

        mAdapter = new FamiliarEasyAdapter<MoreMenu>(this, R.layout.item_more_menu, moreMenuList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                MoreMenu moreMenu = moreMenuList.get(position);
                ImageView imageArrowRight = (ImageView) holder.findView(com.heiman.baselibrary.R.id.image_arrow_right);
                TextView tvLeft = (TextView) holder.findView(com.heiman.baselibrary.R.id.tv_Left);
                TextView tvRight = (TextView) holder.findView(com.heiman.baselibrary.R.id.tv_Right);
                tvLeft.setText(moreMenu.getLeftText());
                tvRight.setText(moreMenu.getRightText());
                imageArrowRight.setImageResource(R.drawable.personal_next);
                imageArrowRight.setVisibility(moreMenu.isVRightImage() ? View.VISIBLE : View.GONE);
            }
        };
        recyclerDevice.setAdapter(mAdapter);
        recyclerDevice.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                MoreMenu moreMenu = moreMenuList.get(position);
                BaseApplication.getLogger().i("点击：" + moreMenu.getLeftText());
                switch (position) {
                    case 0:
                        LogUtil.e(getString(R.string.Common_problem));
                        break;
                    case 1:
                        LogUtil.e(getString(R.string.Product_manual));
                        break;
                    case 2:
                        LogUtil.e(getString(R.string.Owned_room));
                        break;
                    case 3:
                        LogUtil.e(getString(R.string.Modify_name));
                        break;
                    case 4:
                        LogUtil.e(getString(R.string.alarm_record));
                        break;
                    case 5: {
                        LogUtil.e(getString(R.string.alarm_condition_setting));
                        Bundle paramBundle = new Bundle();

                        if (subDevice != null) {
                            paramBundle.putBoolean(Constant.IS_DEVICE, true);
                            paramBundle.putString(Constant.DEVICE_MAC, subDevice.getDeviceMac());
                            paramBundle.putBoolean(Constant.IS_SUB, true);
                            paramBundle.putString(Constant.ZIGBEE_MAC, subDevice.getZigbeeMac());
                        }
                        openActivity(TempHumAlarmSettingActivity.class, paramBundle);
                        break;
                    }
                    case 6: {
                        LogUtil.e(getString(R.string.Delete_device));

                        break;
                    }
                    case 7:
                        LogUtil.e(getString(R.string.Check_firmware_upgrade));
                        Bundle paramBundle = new Bundle();

                        if (subDevice != null) {
                            paramBundle.putBoolean(Constant.IS_DEVICE, true);
                            paramBundle.putString(Constant.DEVICE_MAC, subDevice.getDeviceMac());
                            paramBundle.putBoolean(Constant.IS_SUB, true);
                            paramBundle.putString(Constant.ZIGBEE_MAC, subDevice.getZigbeeMac());
                        }
                        openActivity(CheckFirmwareUpgradeActivity.class, paramBundle);
                        break;
                    case 8:
                        LogUtil.e(getString(R.string.device_mac_address));

                        break;
                    case 9:
                        LogUtil.e(getString(R.string.residual_electricity));

                        break;
                }
            }
        });
    }

    private void initView() {
        recyclerDevice = (FamiliarRecyclerView) findViewById(R.id.recycler_device);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
    }
}
