package com.heiman.gateway;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.HeimanSet;
import com.heiman.baselibrary.mode.MoreMenu;
import com.heiman.devicecommon.CheckFirmwareUpgradeActivity;
import com.heiman.devicecommon.OwnedRoomActivity;
import com.heiman.devicecommon.ShareDeviceActivity;
import com.heiman.utils.LogUtil;
import com.heiman.widget.dialog.MDDialog;
import com.heiman.widget.dialog.MLAlertDialog;
import com.heiman.widget.edittext.ClearEditText;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;

/**
 * @Author : 肖力
 * @Time :  2017/5/24 15:49
 * @Description :
 * @Modify record :
 */

public class GwMoreMenuActivity extends GwBaseActivity {
    private FamiliarRecyclerView recyclerDevice;
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;


    //    private MoreMenuAdapter moreMenuAdapter;
    private List<MoreMenu> moreMenuList;

    private FamiliarEasyAdapter<MoreMenu> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_menu);
        initView();
        initEven();
        initMenuData();
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
    }

    private void initMenuData() {
        moreMenuList.clear();
        moreMenuList.add(new MoreMenu(getString(R.string.Novice_guide), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Common_problem), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Product_manual), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Owned_room), "我房间", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Modify_name), device.getDeviceName(), true));
        moreMenuList.add(new MoreMenu(getString(R.string.Device_sharing), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Check_firmware_upgrade), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Delete_device), "", true));
        mAdapter.notifyDataSetChanged();
    }


    private void initEven() {
        titleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarMore.setVisibility(View.GONE);
        titleBarShare.setVisibility(View.GONE);
        titleBarTitle.setText(R.string.more);
        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
//        moreMenuAdapter = new MoreMenuAdapter(GwMoreMenuActivity.this, moreMenuList);

        mAdapter = new FamiliarEasyAdapter<MoreMenu>(this, R.layout.item_more_menu, moreMenuList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                MoreMenu moreMenu = moreMenuList.get(position);
                ImageView imageArrowRight = (ImageView) holder.findView(com.heiman.baselibrary.R.id.image_arrow_right);
                TextView tvLeft = (TextView) holder.findView(com.heiman.baselibrary.R.id.tv_Left);
                TextView tvRight = (TextView) holder.findView(com.heiman.baselibrary.R.id.tv_Right);
                tvLeft.setText(moreMenu.getLeftText());
                tvRight.setText(moreMenu.getRightText());
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
                        LogUtil.e(getString(R.string.Novice_guide));
                        break;
                    case 1:
                        LogUtil.e(getString(R.string.Common_problem));
                        break;
                    case 2:
                        LogUtil.e(getString(R.string.Product_manual));
                        break;
                    case 3:
                        LogUtil.e(getString(R.string.Owned_room));
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(Constant.IS_DEVICE, true);
                        bundle.putString(Constant.DEVICE_MAC, device.getDeviceMac());
                        openActivity(OwnedRoomActivity.class, bundle);
                        break;
                    case 4:
                        LogUtil.e(getString(R.string.Modify_name));
                        showChangeNameDialog(moreMenu.getLeftText(), "", moreMenu.getRightText(), new MDDialog.OnMultiClickListener() {
                            @Override
                            public void onClick(View clickedView, View contentView) {
                                ClearEditText et = (ClearEditText) contentView.findViewById(R.id.edit0);
                                final String deviceName = et.getText().toString();
                                String deviceNameJson = HeimanCom.setDeviceName(SmartPlug.mgetSN(), 0, deviceName);
                                BaseApplication.getLogger().json(deviceNameJson);
                                showHUDProgress(getString(R.string.gateway_Send_in));
                                sendData(deviceNameJson);
                            }
                        });
                        break;
                    case 5: {
                        LogUtil.e(getString(R.string.Device_sharing));
                        Bundle bundleShare = new Bundle();
                        bundleShare.putBoolean(Constant.IS_DEVICE, true);
                        bundleShare.putString(Constant.DEVICE_MAC, device.getDeviceMac());
                        openActivity(ShareDeviceActivity.class, bundleShare);
                        break;
                    }
                    case 6: {
                        LogUtil.e(getString(R.string.Check_firmware_upgrade));
                        Bundle bundleUp = new Bundle();
                        bundleUp.putBoolean(Constant.IS_DEVICE, true);
                        bundleUp.putString(Constant.DEVICE_MAC, device.getDeviceMac());
                        openActivity(CheckFirmwareUpgradeActivity.class, bundleUp);
                        break;
                    }
                    case 7:
                        LogUtil.e(getString(R.string.Delete_device));
                        MLAlertDialog.Builder builder = new MLAlertDialog.Builder(GwMoreMenuActivity.this);
                        builder.setTitle(device.getDeviceName());
                        builder.setMessage(getString(R.string.gateway_Make_sure_the_device_is_deleted));
                        builder.setPositiveButton(getString(R.string.ok_button), new MLAlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showHUDProgress(getString(R.string.gateway_Deleting));
                                HttpManage.getInstance().unsubscribe(GwMoreMenuActivity.this, device.getDeviceId(), new HttpManage.ResultCallback<String>() {
                                    @Override
                                    public void onError(Header[] headers, HttpManage.Error error) {
                                        XlinkUtils.shortTips(BaseApplication.getMyApplication(), getString(R.string.gateway_Delete_failed), getResources().getColor(com.heiman.baselibrary.R.color.class_J), getResources().getColor(com.heiman.baselibrary.R.color.white), 0, false);
                                    }

                                    @Override
                                    public void onSuccess(int code, String response) {
                                        DeviceManage.getInstance().removeDevice(device.getDeviceMac());
                                        XlinkUtils.shortTips(BaseApplication.getMyApplication(), getString(R.string.gateway_Delete_successfully), 0, 0, 0, false);
                                        finish();
                                        GwActivity.instance.finish();
                                    }
                                });
                            }
                        });
                        builder.setNegativeButton(getString(R.string.cancel), new MLAlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.create();
//                        builder.createCenter();
                        builder.show();
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
        moreMenuList = new ArrayList<MoreMenu>();
    }

    @Override
    public void onClickListener(View v) {

    }

    @Override
    public void deviceData(String dataString) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(dataString);
            JSONObject PL = jsonObject.getJSONObject("PL");
//            JSONObject OID = PL.getJSONObject(HeimanCom.COM_GW_OID.GW_NAME);
            HeimanSet.PLBean.DeviceNameOID deviceNameOID = gson.fromJson(PL.toString(), HeimanSet.PLBean.DeviceNameOID.class);
            device.setDeviceName(deviceNameOID.getName());
            DeviceManage.getInstance().addDevice(device);
            dismissHUMProgress();
            HttpManage.getInstance().setDevicename(GwMoreMenuActivity.this, device.getProductId(), device.getDeviceId(), deviceNameOID.getName(), new HttpManage.ResultCallback<String>() {
                @Override
                public void onError(Header[] headers, HttpManage.Error error) {

                }

                @Override
                public void onSuccess(int code, String response) {

                }
            });
            initMenuData();
        } catch (Exception e) {

        }

    }
}
