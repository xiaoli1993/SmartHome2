/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.temphum.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.baselibrary.mode.DataDevice;
import com.heiman.temphum.R;
import com.heiman.temphum.TempHumActivity;
import com.heiman.utils.LogUtil;
import com.heiman.widget.linechart.LineChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/15 16:20
 * @Description : 
 * @Modify record :
 */

public class TempFragment extends BaseFragment {

    private TempHumActivity mActivity;
    private TextView txtCurrentTemp;
    private LinearLayout llEverydayTempRecordContainer;
    private LineChart lineChartTodayTemp;

    private final int MSG_INIT_DATA = 10000;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT_DATA:
                    initData();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temphum_layout_temp, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtCurrentTemp = (TextView) view.findViewById(R.id.txt_current_temp_info);
        llEverydayTempRecordContainer = (LinearLayout) view.findViewById(R.id.ll_everyday_temp_container);
        lineChartTodayTemp = (LineChart) view.findViewById(R.id.line_chart_today_temp);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(MSG_INIT_DATA);
    }

    private void initData(){
        if (mActivity  == null) {
            FragmentActivity activity = getActivity();

            if (activity != null && activity instanceof TempHumActivity) {
                mActivity = (TempHumActivity) activity;
            }
        }

        if (mActivity == null) {
            LogUtil.e("mActivity is null!");
            return;
        }

        List<DataDevice> dataDeviceList = new ArrayList<>();
        if (mActivity.subDevice != null) {
            txtCurrentTemp.setText(TextUtils.isEmpty(mActivity.subDevice.getTemp()) ? 0 + "" : mActivity.subDevice.getTemp());
            //当子设备不是空的时候获取设备温湿度记录
        }

        for (int i = 30; i > 0; i--) {
            int temp = -10 + (int) (Math.random() * 70);
            int hum = (int) (Math.random() * 100);
            dataDeviceList.add(madeTestData("2017", "6", i + "", temp + "", hum + ""));
        }

        for (int i = 0; i < dataDeviceList.size(); i++) {
            DataDevice dataDevice = dataDeviceList.get(i);
            View view = LayoutInflater.from(mActivity).inflate(R.layout.temphum_item_temp_hum, llEverydayTempRecordContainer,false);
            TextView txtDate = (TextView) view.findViewById(R.id.txt_date);
            ImageView imgIcon = (ImageView) view.findViewById(R.id.img_icon);
            TextView txtData = (TextView) view.findViewById(R.id.txt_data);

            txtDate.setText(dataDevice.getMonth() + "-" + dataDevice.getDay());
            txtData.setText(dataDevice.getTemp());

            llEverydayTempRecordContainer.addView(view);
        }

        HashMap<String, Integer> hashMap = new HashMap<>();
        List<String> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.getTime().getHours();
        hashMap.put(hours + "",  -10 + (int) (Math.random() * 70));
        keyList.add(hours + "");
        for (int i = hours + 1; i > hours || i < hours; i++) {
            if (i == 24) {
                i = 0;
            }
            keyList.add(i + "");
            hashMap.put(i + "", -10 + (int) (Math.random() * 70));
        }
        keyList.add("现在");
        hashMap.put("现在",  -10 + (int) (Math.random() * 70));
        lineChartTodayTemp.setMinValue(-10);
        lineChartTodayTemp.setData(hashMap, keyList);
    }

    /**
     * 制造测试数据以便展示
     */
    private DataDevice madeTestData(String yyyy, String MM, String dd, String temp, String hum) {
        DataDevice dataDevice = new DataDevice();
        dataDevice.setYear(yyyy);
        dataDevice.setMonth(MM);
        dataDevice.setDay(dd);
        dataDevice.setTemp(temp);
        dataDevice.setHumidity(hum);
        return dataDevice;
    }
}
