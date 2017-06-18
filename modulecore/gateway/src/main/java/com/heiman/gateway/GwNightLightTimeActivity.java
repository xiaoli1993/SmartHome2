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
 * @Time :  2017/6/14 13:38
 * @Description :
 * @Modify record :
 */

public class GwNightLightTimeActivity extends GwBaseActivity {

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
        initMenuData(device.getLgtimer());
        initEven();
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
    }


    private void initMenuData(int second) {
        moreMenuList.clear();
        if (second == 60) {
            moreMenuList.add(new MoreMenu("1分钟", "", true));
        } else {
            moreMenuList.add(new MoreMenu("1分钟", "", false));
        }
        if (second == 120) {
            moreMenuList.add(new MoreMenu("2分钟", "", true));
        } else {
            moreMenuList.add(new MoreMenu("2分钟", "", false));
        }
        if (second == 180) {
            moreMenuList.add(new MoreMenu("3分钟", "", true));
        } else {
            moreMenuList.add(new MoreMenu("3分钟", "", false));
        }
        if (second == 240) {
            moreMenuList.add(new MoreMenu("4分钟", "", true));
        } else {
            moreMenuList.add(new MoreMenu("4分钟", "", false));
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
        titleBarTitle.setText(getString(R.string.gateway_Night_light_time));
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
                String language = "";
                switch (position) {
                    case 0:
                        gwBasicOID.setLgtimer(60);
                        break;
                    case 1:
                        gwBasicOID.setLgtimer(120);
                        break;
                    case 2:
                        gwBasicOID.setLgtimer(180);
                        break;
                    case 3:
                        gwBasicOID.setLgtimer(240);
                        break;
                }
                language = HeimanCom.setBasic(SmartPlug.mgetSN(), 0, gwBasicOID);
                BaseApplication.getLogger().json(language);
                sendData(language);
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
            int LT = PL.getInt("LT");
            device.setLgtimer(LT);
            DeviceManage.getInstance().addDevice(device);
            initMenuData(LT);
            dismissHUMProgress();

        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }
}

