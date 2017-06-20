package com.heiman.devicecommon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.devicecommon.fragment.AlarmDiaryFragment;
import com.heiman.devicecommon.fragment.OrdinaryDiaryFragment;
import com.heiman.widget.segmentcontrol.SegmentControl;
import com.jaeger.library.StatusBarUtil;

/**
 * @Author : 肖力
 * @Time :  2017/6/20 14:39
 * @Description :
 * @Modify record :
 */

public class DeviceDiaryActivity extends GwBaseActivity {

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
        setContentView(R.layout.devicecommon_activity_device_diary);
        initView();
        initEven();
        setSelect(0);
        StatusBarUtil.setTranslucent(this, 50);
        XlinkUtils.StatusBarLightMode(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    mTab01 = new AlarmDiaryFragment();
                    transaction.add(R.id.layFragme, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new OrdinaryDiaryFragment();
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
