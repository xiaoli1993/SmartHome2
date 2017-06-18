package com.heiman.smarthome.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.manage.LinkManage;
import com.heiman.baselibrary.mode.Link;
import com.heiman.baselibrary.mode.link.PL;
import com.heiman.baselibrary.mode.link.plBean.LinkBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.CheckListBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.ConListBean;
import com.heiman.baselibrary.mode.link.plBean.linkBean.ExecBean;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.activity.LinkManaActivity;
import com.heiman.smarthome.activity.MainActivity;
import com.heiman.smarthome.adapter.LinkAdapter;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.heiman.widget.togglebutton.ToggleButton;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/8 16:07
 * @Description :
 * @Modify record : 张泽晋 2017/6/12 完善
 */

public class AutomationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{

    private SwipeRefreshLayout swipeLayout;
    private ImageView titleBarMore;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private SwipeMenuRecyclerView recyclerLink;

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
                        String jsonCmd = HeimanCom.getLink(SmartPlug.mgetSN(), 1);
                        LogUtil.e("gsonCmd:" + jsonCmd);
                        mainActivity.sendData(jsonCmd);
                        initLinks();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_automation, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        titleBarReturn = (ImageView) view.findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) view.findViewById(R.id.title_bar_title);
        titleBarMore = (ImageView) view.findViewById(R.id.title_bar_more);
        recyclerLink = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_link);

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
        titleBarTitle.setText(R.string.Automation);

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

    @Override
    public void onClick(View v) {

        if (!UsefullUtill.judgeClick(R.layout.fragment_automation, 500)) {
            MyApplication.getLogger().e("点击过快！");
            return;
        }

        switch (v.getId()) {
            case R.id.title_bar_return: {
                if(mainActivity != null) {
                    mainActivity.openDrawers();
                }
                break;
            }
            case R.id.title_bar_more: {
                openActivity(LinkManaActivity.class);
                break;
            }
            case R.id.txt_link_name:
            case R.id.img_item_link_left:
            case R.id.img_item_link_right:
                if (v.getTag() != null && v.getTag() instanceof Link) {
                    Link link = (Link) v.getTag();

                    if (link.getPL() != null && link.getPL().getOID() != null) {
                        LogUtil.e("点击：" + link.getPL().getOID().getName());
                        Bundle bundle = new Bundle();
                        bundle.putLong("id", link.getId());
                        openActivity(LinkManaActivity.class, bundle);
                    }
                }
                break;
        }
    }

    private ToggleButton.OnToggleChanged mOnToggleChanged = new ToggleButton.OnToggleChanged() {

        @Override
        public void onToggle(View view, boolean on) {
            if (view.getTag() != null && view.getTag() instanceof Link) {
                Link link = (Link) view.getTag();

                if (link.getPL() != null && link.getPL().getOID() != null) {
                    LogUtil.e(link.getPL().getOID().getName() + "\tenable:" + on);
                    link.getPL().getOID().setEnable(on);
                }
            }
        }
    };

    public void initLinks() {
        if (mainActivity.getSelectedGateway() == null) {
            Toast.makeText(mainActivity, "没有网关！", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (mainActivity.getSelectedGateway().getDeviceState() == 0) {
//            Toast.makeText(mainActivity, "网关不在线！", Toast.LENGTH_SHORT).show();
//            return;
//        }
        List<Link> linkList = LinkManage.getInstance().findAllLink();

        if (linkList == null || linkList.size() <= 0) {
            for (int i = 0; i < 10; i++) {
                Link link = new Link();
                link.setGatewayMac(mainActivity.getSelectedGateway().getDeviceMac());
                PL pl = new PL();
                link.setPL(pl);
                LinkBean linkBean = new LinkBean();
                linkBean.setName("联动名称" + i);
                linkBean.setLinkID(i);
                linkBean.setConList(new ConListBean());
                linkBean.setCheckList(new ArrayList<CheckListBean>());
                linkBean.setExecList(new ArrayList<ExecBean>());

                if (i % 3 == 1) {
                    linkBean.setEnable(true);
                } else {
                    linkBean.setEnable(false);
                }
                link.getPL().setOID(linkBean);
                LinkManage.getInstance().addLink(link);
                linkList.add(link);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        LinkAdapter linkAdapter = new LinkAdapter(mainActivity, linkList, this, mOnToggleChanged);
        recyclerLink.setAdapter(linkAdapter);
        recyclerLink.setLayoutManager(linearLayoutManager);
    }
}
