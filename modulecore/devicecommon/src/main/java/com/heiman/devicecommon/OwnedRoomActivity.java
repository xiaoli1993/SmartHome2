package com.heiman.devicecommon;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.RoomManage;
import com.heiman.baselibrary.mode.HeimanSet;
import com.heiman.baselibrary.mode.MoreMenu;
import com.heiman.baselibrary.mode.Room;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;

/**
 * @Author : 肖力
 * @Time :  2017/6/14 14:29
 * @Description :
 * @Modify record :
 */

public class OwnedRoomActivity extends GwBaseActivity {

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
        initMenuData(device.getRoomID());
        initEven();
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
    }


    private void initMenuData(String roomid) {
        moreMenuList.clear();
//        RoomDevice
        boolean isAddRoom = false;

        List<Room> roomList = RoomManage.getInstance().getHome();
        for (Room room : roomList) {
            if (!isSub) {
                if (!SmartHomeUtils.isEmptyString(device.getRoomID())) {
                    if (device.getRoomID().equals(room.getObjectId())) {
                        MoreMenu moreMenu = new MoreMenu(room.getRoom_name(), "", true);
                        moreMenu.setRoomID(room.getObjectId());
                        moreMenuList.add(moreMenu);
                        isAddRoom = true;
                    } else {
                        MoreMenu moreMenu = new MoreMenu(room.getRoom_name(), "", false);
                        moreMenu.setRoomID(room.getObjectId());
                        moreMenuList.add(moreMenu);
                    }
                } else {
                    MoreMenu moreMenu = new MoreMenu(room.getRoom_name(), "", false);
                    moreMenu.setRoomID(room.getObjectId());
                    moreMenuList.add(moreMenu);
                }
            } else {
                if (!SmartHomeUtils.isEmptyString(device.getRoomID())) {
                    if (subDevice.getRoomID().equals(room.getObjectId())) {
                        MoreMenu moreMenu = new MoreMenu(room.getRoom_name(), "", true);
                        moreMenu.setRoomID(room.getObjectId());
                        moreMenuList.add(moreMenu);
                        isAddRoom = true;
                    } else {
                        MoreMenu moreMenu = new MoreMenu(room.getRoom_name(), "", false);
                        moreMenu.setRoomID(room.getObjectId());
                        moreMenuList.add(moreMenu);
                    }
                } else {
                    moreMenuList.add(new MoreMenu(room.getRoom_name(), "", false));
                }
            }
        }
        if (!isAddRoom) {
            moreMenuList.add(0, new MoreMenu(getString(R.string.Default_room), "", true));
        } else {
            moreMenuList.add(0, new MoreMenu(getString(R.string.Default_room), "", false));
        }
        try {
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {

        }
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
        titleBarTitle.setText(getString(R.string.gateway_Device_language));
        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
//        moreMenuAdapter = new MoreMenuAdapter(GwMoreMenuActivity.this, moreMenuList);

        mAdapter = new FamiliarEasyAdapter<MoreMenu>(this, R.layout.item_more_menu, moreMenuList) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                MoreMenu moreMenu = moreMenuList.get(position);
                ImageView imageArrowRight = (ImageView) holder.findView(R.id.image_arrow_right);
                TextView tvLeft = (TextView) holder.findView(R.id.tv_Left);
                TextView tvRight = (TextView) holder.findView(R.id.tv_Right);
                tvLeft.setText(moreMenu.getLeftText());
                tvRight.setText(moreMenu.getRightText());
                imageArrowRight.setImageResource(R.drawable.common_check_icon_selected);
                imageArrowRight.setVisibility(moreMenu.isVRightImage() ? View.VISIBLE : View.GONE);
            }
        };
        recyclerDevice.setAdapter(mAdapter);
        recyclerDevice.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                MoreMenu moreMenu = moreMenuList.get(position);
                String roomid = "";
                if (!isSub) {
                    HeimanSet.PLBean.GwBasicOID gwBasicOID = new HeimanSet.PLBean.GwBasicOID();
                    gwBasicOID.setRoomID(moreMenu.getRoomID());
                    roomid = HeimanCom.setBasic(SmartPlug.mgetSN(), 0, gwBasicOID);
                } else {
                    roomid = HeimanCom.setRoomID(SmartPlug.mgetSN(), 0, subDevice.getIndex(), moreMenu.getRoomID());
                }
                BaseApplication.getLogger().json(roomid);
                sendData(roomid);
                showHUDProgress(getString(R.string.gateway_Send_in));
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
        try {
            JSONObject jsonObject = new JSONObject(dataString);
            JSONObject PL = jsonObject.getJSONObject("PL");
            if (!isSub) {
                String RD = PL.getString("RD");
                device.setRoomID(RD);
                DeviceManage.getInstance().addDevice(device);
                initMenuData(RD);
            } else {
                JSONObject OID = PL.getJSONObject(HeimanCom.COM_GW_OID.SUB_ROOM_ID.replace("{index}", subDevice.getIndex() + ""));
                String roomID = OID.getString("roomID");
                subDevice.setRoomID(roomID);
                initMenuData(roomID);
            }
            dismissHUMProgress();
        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }
}