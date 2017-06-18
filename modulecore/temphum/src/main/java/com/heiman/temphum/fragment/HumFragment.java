/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.temphum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.temphum.R;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/15 16:21
 * @Description : 
 * @Modify record :
 */

public class HumFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.temphum_layout_hum, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
