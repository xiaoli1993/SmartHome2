package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.manage.SceneManage;
import com.heiman.baselibrary.mode.Scene;
import com.heiman.baselibrary.mode.UserInfo;
import com.heiman.baselibrary.mode.scene.PLBean;
import com.heiman.baselibrary.mode.scene.pLBean.SceneBean;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.adapter.ExecAdapter;
import com.heiman.smarthome.adapter.ExecSceneAdapter;
import com.heiman.smarthome.view.DeviceActionDecoration;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.Arrays;
import java.util.List;

public class LinkAddExecSceneActivity extends BaseActivity implements View.OnClickListener{

    private TextView txtTitleRight;
    private SwipeMenuRecyclerView recyclerExecScene;
    private ExecSceneAdapter execSceneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_link_add_exec_scene);
        txtTitleRight = (TextView) findViewById(R.id.title_tv_Right);
        recyclerExecScene = (SwipeMenuRecyclerView) findViewById(R.id.recycler_scene_exec);

        txtTitleRight.setOnClickListener(this);

        txtTitleRight.setVisibility(View.VISIBLE);
        txtTitleRight.setText(R.string.device_exec_submit);
        txtTitleRight.setTextColor(getResources().getColor(R.color.btn_regist_color));

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.txt_exec_scene));
        setReturnImage(R.drawable.personal_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initScenes();
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_link_add_exec_scene, 500)) {
            MyApplication.getLogger().e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.title_tv_Right:
                LogUtil.e("点击提交！");
                break;
            case R.id.txt_device_exec:
                if (v.getTag() != null && v.getTag() instanceof Scene) {
                    Scene selectedExec = (Scene) v.getTag();
                    LogUtil.e("选中了：" + selectedExec);
                    execSceneAdapter.setSelectedExec(selectedExec);
                }
                break;
        }
    }

    private void initScenes() {
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
                scene.setGatewayMac("");
                scenOID.setSceneID(i);
                plBean.setOID(scenOID);
                scene.setPL(plBean);
                SceneManage.getInstance().addScene(scene);
                sceneList.add(scene);
                SceneManage.getInstance().addScene(scene);
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        execSceneAdapter = new ExecSceneAdapter(this, sceneList, this);
        recyclerExecScene.addItemDecoration(new DeviceActionDecoration());
        recyclerExecScene.setAdapter(execSceneAdapter);
        recyclerExecScene.setLayoutManager(linearLayoutManager);
    }
}
