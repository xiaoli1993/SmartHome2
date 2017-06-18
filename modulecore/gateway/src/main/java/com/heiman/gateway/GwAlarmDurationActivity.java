package com.heiman.gateway;

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
import com.heiman.baselibrary.mode.HeimanSet;
import com.heiman.baselibrary.mode.MoreMenu;
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
 * @Time :  2017/6/14 11:19
 * @Description :
 * @Modify record :
 */

public class GwAlarmDurationActivity extends GwBaseActivity {

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
        initMenuData(device.getBetimer());
        initEven();
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
    }

    private void initMenuData(int second) {
        moreMenuList.clear();
        if (second == 0) {
            moreMenuList.add(new MoreMenu("关闭", "", true));
        } else {
            moreMenuList.add(new MoreMenu("关闭", "", false));
        }
        if (second == 30) {
            moreMenuList.add(new MoreMenu("30秒", "", true));
        } else {
            moreMenuList.add(new MoreMenu("30秒", "", false));
        }
        if (second == 60) {
            moreMenuList.add(new MoreMenu("60秒", "", true));
        } else {
            moreMenuList.add(new MoreMenu("60秒", "", false));
        }
        if (second == 90) {
            moreMenuList.add(new MoreMenu("90秒", "", true));
        } else {
            moreMenuList.add(new MoreMenu("90秒", "", false));
        }
        if (second == 120) {
            moreMenuList.add(new MoreMenu("120秒", "", true));
        } else {
            moreMenuList.add(new MoreMenu("120秒", "", false));
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
        titleBarTitle.setText(getString(R.string.gateway_alarm_duration));
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
//                MoreMenu moreMenu = moreMenuList.get(position);
//                BaseApplication.getLogger().i("点击：" + moreMenu.getLeftText());
                HeimanSet.PLBean.GwBasicOID gwBasicOID = new HeimanSet.PLBean.GwBasicOID();
                String alarmduration = "";
                switch (position) {
                    case 0:
                        gwBasicOID.setBetimer(0);
                        break;
                    case 1:
                        gwBasicOID.setBetimer(30);
                        break;
                    case 2:
                        gwBasicOID.setBetimer(60);
                        break;
                    case 3:
                        gwBasicOID.setBetimer(90);
                        break;
                    case 4:
                        gwBasicOID.setBetimer(120);
                        break;
                }
                alarmduration = HeimanCom.setBasic(SmartPlug.mgetSN(), 0, gwBasicOID);
                BaseApplication.getLogger().json(alarmduration);
                sendData(alarmduration);
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
            int BT = PL.getInt("BT");
            device.setBetimer(BT);
            DeviceManage.getInstance().addDevice(device);
            initMenuData(BT);
            dismissHUMProgress();

        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }
}
