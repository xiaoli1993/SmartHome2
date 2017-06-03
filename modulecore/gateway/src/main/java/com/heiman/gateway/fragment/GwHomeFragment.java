package com.heiman.gateway.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.gateway.R;


/**
 * @Author : 肖力
 * @Time :  2017/5/9 13:57
 * @Description :
 * @Modify record :
 */
public class GwHomeFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gateway_fragment_home, container, false);
        return view;
    }

}
