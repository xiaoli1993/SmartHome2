package com.heiman.smarthome.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.smarthome.R;

/**
 * @Author : 肖力
 * @Time :  2017/5/8 16:08
 * @Description :
 * @Modify record :
 */

public class SceneFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scene, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //设置监听
        swipeLayout.setOnRefreshListener(this);
        //设置向下拉多少出现刷新
        swipeLayout.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        swipeLayout.setProgressViewEndTarget(false, 200);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //然刷新控件停留两秒后消失
                    Thread.sleep(2000);
                    handler.post(new Runnable() {//在主线程执行
                        @Override
                        public void run() {
                            //更新数据

                            //停止刷新
                            swipeLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
