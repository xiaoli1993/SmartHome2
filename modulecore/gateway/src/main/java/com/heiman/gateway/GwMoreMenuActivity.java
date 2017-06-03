package com.heiman.gateway;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.mode.MoreMenu;
import com.jaeger.library.StatusBarUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

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
        initMenuData();
        initEven();
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);
    }

    private void initMenuData() {
        moreMenuList = new ArrayList<MoreMenu>();
        moreMenuList.add(new MoreMenu(getString(R.string.Novice_guide), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Common_problem), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Product_manual), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Owned_room), "我房间", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Modify_name), "智能网关", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Device_sharing), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Check_firmware_upgrade), "", true));
        moreMenuList.add(new MoreMenu(getString(R.string.Delete_device), "", true));
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

    @Override
    public void onClickListener(View v) {

    }

    @Override
    public void deviceData(String dataString) {

    }
}
