package com.heiman.baselibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.heiman.baselibrary.adapter.RoomTopListAdapter;
import com.heiman.widget.popwindow.CustomPopWindow;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/10 16:40
 * @Description :
 * @Modify record :
 */

public class BaseFragment extends Fragment {
    /**
     * 跳转界面
     *
     * @param paramClass
     */
    protected void openActivity(Class<?> paramClass) {
        BaseApplication.getLogger().e(getClass().getSimpleName(), "openActivity：：" + paramClass.getSimpleName());
        openActivity(paramClass, null);
    }

    protected void openActivity(Class<?> paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent(getActivity(), paramClass);
        if (paramBundle != null)
            localIntent.putExtras(paramBundle);
        startActivity(localIntent);
    }

    /**
     * 通过包名跳转
     *
     * @param activityName
     */
    public void startActivityForName(String activityName, Bundle paramBundle) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(getActivity(), clazz);
            if (paramBundle != null)
                intent.putExtras(paramBundle);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void startActivityForName(String activityName) {
        try {
            Class clazz = Class.forName(activityName);
            Intent intent = new Intent(getActivity(), clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private CustomPopWindow mListPopWindow;

    /**
     * 顶部显示房间列表
     *
     * @param context
     * @param view     显示位置
     * @param xOff     显示X偏移量
     * @param yOff     显示Y偏移量
     * @param dataList 显示数据
     */
    public void showPopRoomTopList(Context context, View view, int xOff, int yOff, List<String> dataList) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_room_menu, null);
        //处理popWindow 显示内容
        handleListView(context, contentView, dataList);
        //创建并显示popWindow
        mListPopWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        BaseApplication.getLogger().e("onDismiss");
                    }
                })
                .setFocusable(true)
                .setOutsideTouchable(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                .setAnimationStyle(R.style.CustomPopWindowStyle)
                .create()
                .showAsDropDown(view, xOff, yOff);
    }

    private void dissmissPopWind() {
        if (mListPopWindow != null) {
            mListPopWindow.dissmiss();
        }
    }

    private void handleListView(Context context, View contentView, List<String> dataList) {
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        RoomTopListAdapter adapter = new RoomTopListAdapter();
        adapter.setData(dataList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
