package com.heiman.gateway;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.gateway.fragment.GwAutomationFragment;
import com.heiman.gateway.fragment.GwHomeFragment;
import com.heiman.widget.segmentcontrol.SegmentControl;
import com.jaeger.library.StatusBarUtil;

import org.apache.http.Header;

/**
 * @Author : 肖力
 * @Time :  2017/5/9 13:57
 * @Description :
 * @Modify record :
 */

public class GwActivity extends GwBaseActivity {
    private FrameLayout layFragme;
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private SegmentControl titleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;
    private Fragment mTab01;
    private Fragment mTab02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gateway_activity_gw);
        initView();
        initEven();
        setSelect(0);
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);

        HttpManage.getInstance().onVersion(GwActivity.this,device.getProductId(),device.getDeviceId()+"", new HttpManage.ResultCallback<String>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                BaseApplication.getLogger().e("code:"+error.getCode());
            }

            @Override
            public void onSuccess(int code, String response) {
                BaseApplication.getLogger().json(response);
            }
        });

    }

    private void initEven() {
        titleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle paramBundle = new Bundle();
                paramBundle.putBoolean(Constant.IS_DEVICE, true);
                paramBundle.putString(Constant.DEVICE_MAC, device.getDeviceMac());
                paramBundle.putBoolean(Constant.IS_SUB, false);
                openActivity(GwMoreMenuActivity.class, paramBundle);
            }
        });
        titleBarShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onClickTopShareImg(v);
            }
        });
        titleBarTitle.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                setSelect(index);
            }
        });
    }

    private void initView() {
        layFragme = (FrameLayout) findViewById(R.id.layFragme);
        titleBar = (FrameLayout) findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) findViewById(R.id.title_bar_return);
        titleBarTitle = (SegmentControl) findViewById(R.id.title_bar_title);
        titleBarMore = (ImageView) findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) findViewById(R.id.title_bar_share);
    }

    @Override
    public void deviceData(String dataString) {

    }

    @Override
    public void onClickListener(View v) {

    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new GwHomeFragment();
                    transaction.add(R.id.layFragme, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new GwAutomationFragment();
                    transaction.add(R.id.layFragme, mTab02);
                } else {
                    transaction.show(mTab02);

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
    }

}
