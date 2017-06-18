package com.heiman.smarthome.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.baselibrary.Constant;
import com.heiman.smarthome.R;
import com.heiman.smarthome.activity.AddDeviceActivity;
import com.heiman.smarthome.activity.MainActivity;
import com.heiman.smarthome.activity.RoomMoreActivity;
import com.heiman.widget.button.FButton;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * @Author : 肖力
 * @Time :  2017/5/8 16:07
 * @Description :
 * @Modify record :
 */

public class RoomFragment extends BaseFragment implements View.OnClickListener {
    private FButton btnBottom;
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidget(view);
    }

    private void initWidget(View view) {
        btnBottom = (FButton) view.findViewById(R.id.btn_bottom);
        titleBar = (FrameLayout) view.findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) view.findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) view.findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) view.findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) view.findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) view.findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) view.findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) view.findViewById(R.id.title_bar_share);


        titleBarReturn.setImageResource(R.mipmap.home_menu);
        titleBarMore.setImageResource(R.mipmap.home_add);
        titleBarTitle.setVisibility(View.VISIBLE);
        titleBarTitleSpinner.setVisibility(View.GONE);
        titleBarMore.setVisibility(View.VISIBLE);
        titleBarTitle.setText(getResources().getString(R.string.Room));
        titleBarTitle.setTextColor(getResources().getColor(R.color.white));
//        btnBottom.setShadowEnabled(true);
        btnBottom.setOnClickListener(this);
        titleBarReturn.setOnClickListener(this);
        titleBarMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bottom:
                Bundle paramBundle = new Bundle();
                paramBundle.putBoolean(Constant.IS_SUB, false);
                openActivity(AddDeviceActivity.class, paramBundle);
                break;
            case R.id.title_bar_more:

                openActivity(RoomMoreActivity.class);
                break;
            case R.id.title_bar_return:
                ((MainActivity) getActivity()).openDrawers();
                break;

        }
    }
}
