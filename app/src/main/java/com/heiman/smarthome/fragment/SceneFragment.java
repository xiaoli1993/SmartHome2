package com.heiman.smarthome.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.BaseFragment;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.manage.SceneManage;
import com.heiman.baselibrary.mode.Scene;
import com.heiman.baselibrary.mode.UserInfo;
import com.heiman.baselibrary.mode.scene.PLBean;
import com.heiman.baselibrary.mode.scene.pLBean.SceneBean;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.activity.MainActivity;
import com.heiman.smarthome.activity.SceneManaActivity;
import com.heiman.smarthome.adapter.SceneAdapter;
import com.heiman.smarthome.view.SceneDecoration;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/8 16:08
 * @Description :
 * @Modify record : 张泽晋 2017/6/6 完善
 */

public class SceneFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private SwipeRefreshLayout swipeLayout;
    private ImageView titleBarMore;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private SwipeMenuRecyclerView recyclerScene;
    private SceneAdapter sceneAdapter;
    private MainActivity mainActivity;

    private static final int MSG_GET_MAINACTITY = 10000;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GET_MAINACTITY:{
                    FragmentActivity activity = getActivity();

                    if (activity != null && activity instanceof MainActivity) {
                        mainActivity = (MainActivity) activity;
                        LogUtil.e("get mainActivity succeed!");
                        String jsonCmd = HeimanCom.getScene(SmartPlug.mgetSN(), 1);
                        LogUtil.e("gsonCmd:" + jsonCmd);
                        mainActivity.sendData(jsonCmd);
                        initScene();
                    } else {
                        LogUtil.e("get mainActivity fail!");
                        mHandler.sendEmptyMessageDelayed(MSG_GET_MAINACTITY, 100);
                    }
                    break;
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scene, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        titleBarReturn = (ImageView) view.findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) view.findViewById(R.id.title_bar_title);
        titleBarMore = (ImageView) view.findViewById(R.id.title_bar_more);
        recyclerScene = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_device);

        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //设置监听
        swipeLayout.setOnRefreshListener(this);
        //设置向下拉多少出现刷新
        swipeLayout.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        swipeLayout.setProgressViewEndTarget(false, 200);

        titleBarReturn.setImageResource(R.mipmap.home_menu);
        titleBarMore.setImageResource(R.mipmap.home_add);


        titleBarReturn.setVisibility(View.VISIBLE);
        titleBarMore.setVisibility(View.VISIBLE);
        titleBarTitle.setText(R.string.Scene);

        titleBarReturn.setOnClickListener(this);
        titleBarMore.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(MSG_GET_MAINACTITY);
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //然刷新控件停留两秒后消失
                    Thread.sleep(2000);
                    mHandler.post(new Runnable() {//在主线程执行
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

    private void initScene() {
        if (mainActivity.getSelectedGateway() == null) {
            Toast.makeText(mainActivity, "没有网关！", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (mainActivity.getSelectedGateway().getDeviceState() == 0) {
//            Toast.makeText(mainActivity, "网关不在线！", Toast.LENGTH_SHORT).show();
//            return;
//        }

        List<Scene> sceneList = SceneManage.getInstance().findAllScene();
        UserInfo userInfo = BaseApplication.getMyApplication().getUserInfo();
        if (sceneList == null || sceneList.size() <= 0) {
            List<String> sceneNames = Arrays.asList(getResources().getStringArray(R.array.default_scenes));

            for (int i = 0; i < sceneNames.size(); i++) {
                Scene scene = new Scene();
                PLBean plBean = new PLBean();
                SceneBean scenOID = new SceneBean();
                scenOID.setName(sceneNames.get(i));
                scene.setSID(userInfo.getAccount());
                scene.setGatewayMac(mainActivity.getSelectedGateway().getDeviceMac());
                scenOID.setSceneID(i);
                plBean.setOID(scenOID);
                scene.setPL(plBean);
                SceneManage.getInstance().addScene(scene);
                sceneList.add(scene);
                SceneManage.getInstance().addScene(scene);
            }
        }

        int spanCount = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        sceneAdapter = new SceneAdapter(getActivity(), sceneList, this);
        recyclerScene.setAdapter(sceneAdapter);
        recyclerScene.setLayoutManager(layoutManager);
        recyclerScene.addItemDecoration(new SceneDecoration());
        recyclerScene.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {

        if (!UsefullUtill.judgeClick(R.layout.fragment_scene, 500)) {
            MyApplication.getLogger().e("点击过快！");
            return;
        }


        switch (v.getId()) {
            case R.id.btn_scene_item_setting: {
                if (v.getTag() != null && v.getTag() instanceof Scene) {
                    Scene scene = (Scene) v.getTag();
                    LogUtil.e("点击--" + scene.getPL().getOID().getName() + "--设置");
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", scene.getId());
                    openActivity(SceneManaActivity.class, bundle);
                }
                break;
            }
            case R.id.img_scene_icon:
            case R.id.txt_scene_name: {

                if (v.getTag() != null && v.getTag() instanceof Scene) {
                    Scene scene = (Scene) v.getTag();
                    Toast.makeText(getActivity(), scene.getPL().getOID().getName(), Toast.LENGTH_SHORT).show();
                    String cmd = "";

                    if (scene.getPL().getOID().getSceneID() == 0) {
                        cmd = HeimanCom.setScene(SmartPlug.mgetSN(), 1, scene);
                    } else {
                        cmd = HeimanCom.actionScene(SmartPlug.mgetSN(), 1, scene.getPL().getOID().getSceneID());
                    }
                    mainActivity.sendData(cmd);
                }
                break;
            }
            case R.id.title_bar_return: {

               if(mainActivity != null) {
                   mainActivity.openDrawers();
               }
                break;
            }
            case R.id.title_bar_more: {
                openActivity(SceneManaActivity.class);
                break;
            }
        }
    }
}
