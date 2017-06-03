package com.heiman.smarthome.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.smarthome.R;

/**
 * @Author : 肖力
 * @Time :  2017/5/8 16:07
 * @Description :
 * @Modify record :
 */

public class AutomationFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_automation, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
