package com.heiman.devicecommon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.devicecommon.R;

import cn.iwgang.familiarrecyclerview.FamiliarRefreshRecyclerView;

/**
 * @Author : 肖力
 * @Time :  2017/6/20 14:44
 * @Description :
 * @Modify record :
 */

public class OrdinaryDiaryFragment extends BaseFragment {
    private FamiliarRefreshRecyclerView recyclerDiary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.devicecommon_fragment_device_diary, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerDiary = (FamiliarRefreshRecyclerView) view.findViewById(R.id.recycler_diary);
    }


}
