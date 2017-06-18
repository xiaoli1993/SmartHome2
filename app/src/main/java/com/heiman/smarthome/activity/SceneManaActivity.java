package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.manage.SceneManage;
import com.heiman.baselibrary.mode.Scene;
import com.heiman.baselibrary.mode.scene.PLBean;
import com.heiman.baselibrary.mode.scene.pLBean.SceneBean;
import com.heiman.baselibrary.mode.scene.pLBean.sceneBean.ExecListBean;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.adapter.DeviceExecAdapter;
import com.heiman.smarthome.view.DeviceActionDecoration;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SceneManaActivity extends BaseActivity implements View.OnClickListener{

    private TextView txtTitleRight;
    private Scene scene;
    private SwipeMenuRecyclerView recyclerDeviceAction;
    private View sceneIconContainer;
    private TextView txtSceneEdit;
    private TextView txtTimeDelayEdit;
    private TextView txtTimeDelayExec;
    private TextView txtAddActionExec;
    private TextView txtAddTimeDelayExec;
    private DeviceExecAdapter deviceExecAdapter;
    private List<ExecListBean> execListBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scene_mana);
        setContentLayout(R.layout.activity_scene_mana);
        txtTitleRight = (TextView) findViewById(R.id.title_tv_Right);
        recyclerDeviceAction = (SwipeMenuRecyclerView) findViewById(R.id.recycler_scene_devices_action);
        sceneIconContainer = findViewById(R.id.rl_scene_icon_container);
        txtSceneEdit = (TextView) findViewById(R.id.txt_scene_eidt);
        txtTimeDelayEdit = (TextView) findViewById(R.id.txt_scene_eidt_time_delay);
        txtTimeDelayExec = (TextView) findViewById(R.id.txt_scene_time_delay);
        txtAddActionExec = (TextView) findViewById(R.id.txt_scene_add_action);
        txtAddTimeDelayExec = (TextView) findViewById(R.id.txt_scene_add_time_delay);

        txtTitleRight.setVisibility(View.VISIBLE);
        txtTitleRight.setText(R.string.scene_mana_save_scene);
        txtTitleRight.setTextColor(getResources().getColor(R.color.btn_regist_color));

        txtTitleRight.setOnClickListener(this);
        sceneIconContainer.setOnClickListener(this);
        txtSceneEdit.setOnClickListener(this);
        txtTimeDelayEdit.setOnClickListener(this);
        txtAddActionExec.setOnClickListener(this);
        txtAddTimeDelayExec.setOnClickListener(this);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.scene_mana_add_scene));
        setReturnImage(R.drawable.personal_back);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            long id = bundle.getLong("id");
            scene = SceneManage.getInstance().getScene(id);
        }

        if (scene != null && scene.getPL() != null && scene.getPL().getOID() != null) {
            setTitle(scene.getPL().getOID().getName());
        }

        if (scene == null) {
            scene = new Scene();
        }

        if (scene.getPL() == null) {
            scene.setPL(new PLBean());
        }

        if (scene.getPL().getOID() == null) {
            scene.getPL().setOID(new SceneBean());
        }

        if (scene.getPL().getOID().getExecList() == null) {
            scene.getPL().getOID().setExecList(new ArrayList<ExecListBean>());
        }

        initDeviceExecList();
    }

    @Override
    public void onClick(View v) {

        if (!UsefullUtill.judgeClick(R.layout.activity_scene_mana, 500)) {
            MyApplication.getLogger().e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.title_tv_Right: //保存
                LogUtil.e("点击保存！");
                break;
            case R.id.rl_scene_icon_container:
                LogUtil.e("点击场景图标！");
                break;
            case R.id.txt_scene_eidt:
                LogUtil.e("点击编辑场景执行动作！");
                if (!deviceExecAdapter.getShowDelBtn()) {
                    deviceExecAdapter.setShowDelBtn(true);
                } else {
                    deviceExecAdapter.setShowDelBtn(false);
                }
                break;
            case R.id.txt_scene_eidt_time_delay:
                LogUtil.e("点击编辑延时操作！");
                showSetTimeDelay();
                break;
            case R.id.txt_scene_add_action:
                LogUtil.e("点击添加动作！");
                openActivity(LaunchConditionActivity.class);
                break;
            case R.id.txt_scene_add_time_delay:
                LogUtil.e("点击添加延时!");
                showSetTimeDelay();
                break;
            case R.id.btn_del:
                if (v.getTag() instanceof ExecListBean) {
                    ExecListBean execListBean = (ExecListBean) v.getTag();
                    execListBeanList.remove(execListBean);
                    deviceExecAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void initDeviceExecList() {
//        if (scene == null || scene.getPL() == null || scene.getPL().getOID() == null || scene.getPL().getOID().getExecList() == null) {
//            return;
//        }
        execListBeanList = scene.getPL().getOID().getExecList();

        for (int i = 0; i < 10; i++) {
            ExecListBean execListBean = new ExecListBean();
            execListBean.setDeviceId(i);
            execListBeanList.add(execListBean);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        deviceExecAdapter = new DeviceExecAdapter(this, execListBeanList, this);
        recyclerDeviceAction.addItemDecoration(new DeviceActionDecoration());
        recyclerDeviceAction.setAdapter(deviceExecAdapter);
        recyclerDeviceAction.setLayoutManager(linearLayoutManager);
    }

    private void showSetTimeDelay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                try {
                    LogUtil.e("h:" + date.getHours() + "\tm:" + date.getMinutes() + "\ts:" + date.getSeconds());
                    String str = getString(R.string.scene_mana_time_delay_action).replace("0", date.getSeconds() +"");
                    str = date.getHours() == 0 ? date.getMinutes() == 0 ? str : date.getMinutes() + getString(R.string.picker_minite) + str : date.getHours() + getString(R.string.picker_hour) + date.getMinutes() + getString(R.string.picker_minite) + str;
                    txtTimeDelayExec.setText(str);
                } catch (Exception e) {
                    MyApplication.getLogger().e(e.getMessage());
                }
            }
        }).setType(new boolean[]{false,false,false,true,true,true})
                .setDate(calendar)
                .build();
        pvTime.show();
    }
}
