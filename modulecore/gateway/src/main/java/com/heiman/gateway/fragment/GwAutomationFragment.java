package com.heiman.gateway.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.gateway.R;


/**
 * @Author : 肖力
 * @Time :  2017/5/9 13:57
 * @Description :
 * @Modify record :
 */
public class GwAutomationFragment extends BaseFragment {
    private FrameLayout titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gateway_fragment_automation, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }


    private void initView(View view) {
        titleBar = (FrameLayout) view.findViewById(R.id.title_bar);
        titleBar.setVisibility(View.GONE);
    }
}
