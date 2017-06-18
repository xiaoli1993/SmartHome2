package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.baselibrary.manage.LinkManage;
import com.heiman.baselibrary.mode.Link;
import com.heiman.baselibrary.mode.link.PL;
import com.heiman.baselibrary.mode.link.plBean.LinkBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.CheckListBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.ConListBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.ExecBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.checkListBean.TimeBeanX;
import com.heiman.baselibrary.mode.link.plBean.linkBean.conListBean.TimeBean;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.adapter.CheckListAdapter;
import com.heiman.smarthome.adapter.LinkExecAdapter;
import com.heiman.smarthome.view.DeviceActionDecoration;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;

public class LinkManaActivity extends BaseActivity implements View.OnClickListener{

    private TextView txtTitleRight;
    private Link link;
    private TextView txtRiskWarning;
    private TextView txtLinkCondition1;
    private TextView txtLinkCondition2;
    private TextView txtLinkCondition3;
    private SwipeMenuRecyclerView recyclerLinkCondition2;
    private SwipeMenuRecyclerView recyclerLinkCondition3;
    private ImageView imgConlistCondition;
    private TextView txtConlistConditionName;
    private TextView txtConlistConditionState;
    private CheckListAdapter checkListAdapter;
    private LinkExecAdapter linkExecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_link_mana);
        txtTitleRight = (TextView) findViewById(R.id.title_tv_Right);
        txtRiskWarning = (TextView) findViewById(R.id.txt_risk_warning);
        txtLinkCondition1 = (TextView) findViewById(R.id.txt_link_condition1);
        txtLinkCondition2 = (TextView) findViewById(R.id.txt_link_condition2);
        txtLinkCondition3 = (TextView) findViewById(R.id.txt_link_condition3);
        recyclerLinkCondition2 = (SwipeMenuRecyclerView) findViewById(R.id.recycler_link_condition2);
        recyclerLinkCondition3 = (SwipeMenuRecyclerView) findViewById(R.id.recycler_link_condition3);
        imgConlistCondition = (ImageView) findViewById(R.id.img_icon_conlist_condition);
        txtConlistConditionName = (TextView) findViewById(R.id.txt_conlist_condition_name);
        txtConlistConditionState = (TextView) findViewById(R.id.txt_conlist_condition_state);


        SpannableString spannableString = new SpannableString(getString(R.string.link_risk_warning));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.link_risk_warning_red_font_color)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtRiskWarning.setText(spannableString);

        txtTitleRight.setVisibility(View.VISIBLE);
        txtTitleRight.setText(R.string.scene_mana_save_scene);
        txtTitleRight.setTextColor(getResources().getColor(R.color.btn_regist_color));

        txtTitleRight.setOnClickListener(this);
        txtLinkCondition1.setOnClickListener(this);
        txtLinkCondition2.setOnClickListener(this);
        txtLinkCondition3.setOnClickListener(this);
        imgConlistCondition.setOnClickListener(this);
        txtConlistConditionName.setOnClickListener(this);
        txtConlistConditionState.setOnClickListener(this);

        showHeadView(true);
        showReturnView(true);
        showTitleView(true);

        setTitle(getString(R.string.link_mana_add_automation));
        setReturnImage(R.drawable.personal_back);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            long id = bundle.getLong("id");
            link = LinkManage.getInstance().getLink(id);
        }

        if (link != null && link.getPL() != null && link.getPL().getOID() != null) {
            setTitle(link.getPL().getOID().getName());
        }

        if (link == null) {
            link = new Link();
        }

        if (link.getPL() == null) {
            link.setPL(new PL());
        }

        if (link.getPL().getOID() == null) {
            link.getPL().setOID(new LinkBean());
        }

        if (link.getPL().getOID().getExecList() == null) {
            link.getPL().getOID().setExecList(new ArrayList<ExecBean>());
        }

        if (link.getPL().getOID().getConList() == null) {
            link.getPL().getOID().setConList(new ConListBean());
            link.getPL().getOID().getConList().setTime(new TimeBean());
            link.getPL().getOID().getConList().setType(2);
            link.getPL().getOID().getConList().getTime().setType(1);
            link.getPL().getOID().getConList().getTime().setWkflag(0x7b);
        }

        if (link.getPL().getOID().getCheckList() == null) {
            link.getPL().getOID().setCheckList(new ArrayList<CheckListBean>());
        }
        initLinkConditions();
    }

    @Override
    public void onClick(View v) {
        if (!UsefullUtill.judgeClick(R.layout.activity_link_mana, 500)) {
            MyApplication.getLogger().e("点击过快！");
            return;
        }
        switch (v.getId()) {
            case R.id.title_tv_Right: //保存
                LogUtil.e("点击保存！");
                break;
            case R.id.txt_link_condition1:
                LogUtil.e("点击添加条件1！");
                openActivity(LinkConditionAddActivity.class);
                break;
            case R.id.txt_link_condition2:
                LogUtil.e("点击添加条件2！");
                openActivity(LinkConditionAddActivity.class);
                break;
            case R.id.txt_link_condition3:
                LogUtil.e("点击添加执行任务！");
                openActivity(LinkExecAddActivity.class);
                break;
            case R.id.img_icon_conlist_condition:
            case R.id.txt_conlist_condition_name:
            case R.id.txt_conlist_condition_state:
                LogUtil.e("click conlit!");
                break;
            case R.id.txt_condition_name:
            case R.id.img_icon_condition:
            case R.id.txt_condition_state:
                if (v.getTag() instanceof CheckListBean) {
                    CheckListBean checkListBean = (CheckListBean) v.getTag();
                    LogUtil.e("click checkListBean deviceId:" + checkListBean.getDeviceId());
                }
                break;
            case R.id.txt_delete_condition:
                if (v.getTag() instanceof CheckListBean) {
                    CheckListBean checkListBean = (CheckListBean) v.getTag();
                    LogUtil.e("delete checkListBean deviceId:" + checkListBean.getDeviceId());
                    link.getPL().getOID().getCheckList().remove(checkListBean);
                    checkListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.txt_exec_device_name:
            case R.id.img_icon_exec_device:
            case R.id.txt_exec_device_action:
                if (v.getTag() instanceof ExecBean) {
                    ExecBean execBean = (ExecBean) v.getTag();
                    LogUtil.e("click execBean deviceId:" + execBean.getDeviceId());
                }
                break;
            case R.id.txt_delete_exec_device:
                if (v.getTag() instanceof ExecBean) {
                    ExecBean execBean = (ExecBean) v.getTag();
                    LogUtil.e("delete execBean deviceId:" + execBean.getDeviceId());
                    link.getPL().getOID().getExecList().remove(execBean);
                    linkExecAdapter.notifyDataSetChanged();
                }
                break;

        }
    }

    private void initLinkConditions() {
        initConditinConList();
        /**添加测试数据**/
        for (int i = 0; i < 5 ; i++) {
            CheckListBean checkListBean = new CheckListBean();
            checkListBean.setTime(new TimeBeanX());
            checkListBean.setDeviceId(i);
            checkListBean.setCheck_ep(i);
            link.getPL().getOID().getCheckList().add(checkListBean);

            ExecBean execBean = new ExecBean();
            execBean.setDeviceId(i);
            execBean.setExec_ep(i);
            link.getPL().getOID().getExecList().add(execBean);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        checkListAdapter = new CheckListAdapter(this, link.getPL().getOID().getCheckList(), this);
        recyclerLinkCondition2.addItemDecoration(new DeviceActionDecoration());
        recyclerLinkCondition2.setAdapter(checkListAdapter);
        recyclerLinkCondition2.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linkExecAdapter = new LinkExecAdapter(this, link.getPL().getOID().getExecList(), this);
        recyclerLinkCondition3.addItemDecoration(new DeviceActionDecoration());
        recyclerLinkCondition3.setAdapter(linkExecAdapter);
        recyclerLinkCondition3.setLayoutManager(linearLayoutManager2);
    }

    private void initConditinConList() {
        final int type_condition_device = 0;
        final int type_condition_gateway = 1;
        final int type_condition_time = 2;
        ConListBean conListBean = link.getPL().getOID().getConList();
        switch (conListBean.getType()) {
            case type_condition_device:
                LogUtil.e("conlist执行条件是设备时！");
                break;
            case type_condition_gateway:
                LogUtil.e("conlist执行条件是网关时！");
                break;
            case type_condition_time: {
                LogUtil.e("conlist执行条件是时间时！");
                imgConlistCondition.setImageResource(R.drawable.icon_scene_time);
                txtConlistConditionName.setText(R.string.txt_timing);
                TimeBean timeBean = conListBean.getTime();
                String textState = "";
                if (timeBean.getType() == 1) {
                    textState = SmartHomeUtils.getWkString(this, timeBean.getWkflag()) + " " + timeBean.getHour() + ":" + timeBean.getMin();
                } else {
                    textState = timeBean.getMonth() + getString(R.string.picker_month) + timeBean.getDay() + getString(R.string.picker_day) + " " + timeBean.getHour() + ":" + timeBean.getMin();
                }
                txtConlistConditionState.setText(textState);
                break;
            }
        }
    }
}
