package com.heiman.smarthome.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.heiman.baselibrary.BaseFragment;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.mode.Scene;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.activity.MainActivity;
import com.heiman.smarthome.adapter.GroupAdapter;
import com.heiman.smarthome.adapter.MainDevicesAdapter;
import com.heiman.smarthome.adapter.MainSceneAdapter;
import com.heiman.smarthome.modle.ListMain;
import com.heiman.widget.dialog.MenuDialog;
import com.heiman.widget.recyclerview.FullyGridLayoutManager;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * @Author : 肖力
 * @Time :  2017/5/8 16:08
 * @Description :
 * @Modify record :
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;

    private SwipeRefreshLayout swipeLayout;
    private SwipeMenuRecyclerView recyclerScene;
    private FamiliarRecyclerView recyclerDevice;
    private List<ListMain> listMainList;
    private MainDevicesAdapter mainDevicesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidget(view);
    }

    private void initWidget(View view) {
        titleBar = (FrameLayout) view.findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) view.findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) view.findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) view.findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) view.findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) view.findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) view.findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) view.findViewById(R.id.title_bar_share);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        recyclerScene = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_scene);
        recyclerDevice = (FamiliarRecyclerView) view.findViewById(R.id.recycler_device);

        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
        titleBarReturn.setImageResource(R.mipmap.home_menu);
        titleBarMore.setImageResource(R.mipmap.home_add);
        titleBarTitle.setVisibility(View.GONE);
        titleBarTitleSpinner.setVisibility(View.VISIBLE);
        titleBarMore.setVisibility(View.VISIBLE);
        titleBarTitleSpinner.setTextColor(getResources().getColor(R.color.class_V));
        titleBarTitle.setText(getResources().getString(R.string.Home));
        titleBarTitle.setTextColor(getResources().getColor(R.color.class_V));


//        titleBarTitleSpinner.setLinkTextColor(getResources().getColor(R.color.class_V));
////        titleBarTitleSpinner.setTintColor(getResources().getColor(R.color.class_V));
//        titleBarTitleSpinner.setHighlightColor(getResources().getColor(R.color.class_V));
//        titleBarTitleSpinner.setHintTextColor(getResources().getColor(R.color.class_V));
//        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
//        titleBarTitleSpinner.attachDataSource(dataset);

        titleBarTitleSpinner.setTextColor(getResources().getColor(R.color.class_V));
        titleBarTitleSpinner.setItems(mockData());
        titleBarTitleSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                XlinkUtils.shortTips(MyApplication.getMyApplication(), "点击：" + item, getResources().getColor(R.color.class_J), getResources().getColor(R.color.white), 0, false);
            }
        });

        titleBarReturn.setOnClickListener(this);
        titleBarMore.setOnClickListener(this);
        titleBarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopwindow(v);
                showPopRoomTopList(getActivity(), titleBarTitle, 0, -(titleBarTitle.getHeight() + titleBarTitle.getHeight()), mockData());
            }
        });
        //改变加载显示的颜色
        swipeLayout.setColorSchemeColors(Color.RED, Color.RED);
//        //设置背景颜色
//        swipeRefreshLayout.setBackgroundColor(Color.YELLOW);
        //设置初始时的大小
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //设置监听
        swipeLayout.setOnRefreshListener(this);
        //设置向下拉多少出现刷新
        swipeLayout.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        swipeLayout.setProgressViewEndTarget(false, 200);

        listMainList = new ArrayList<ListMain>();
        List<XlinkDevice> xlinkDeviceList = DeviceManage.getInstance().getDevices();
        for (int i = 0; i < xlinkDeviceList.size(); i++) {
            MyApplication.getLogger().e("devicemac:" + xlinkDeviceList.get(i).getDeviceMac() + "deviceType:" + xlinkDeviceList.get(i).getDeviceType() + "长度：" + xlinkDeviceList.size());
            listMainList.add(new ListMain(xlinkDeviceList.get(i).getDeviceMac(), "", false, xlinkDeviceList.get(i).getDeviceType()));
        }
        List<Scene> sceneList = new ArrayList<Scene>();
        Scene scene = new Scene();
        Scene.OID scenOID = new Scene.OID();
        scenOID.setName("场景1");
        scenOID.setSceneID(1);
        scene.setScene(scenOID);
        sceneList.add(scene);
        Scene scene1 = new Scene();
        Scene.OID scenOID1 = new Scene.OID();
        scenOID1.setName("场景3");
        scenOID1.setSceneID(3);
        scene1.setScene(scenOID1);
        sceneList.add(scene1);
        Scene scene2 = new Scene();
        Scene.OID scenOID2 = new Scene.OID();
        scenOID2.setName("场景2");
        scenOID2.setSceneID(2);
        scene2.setScene(scenOID2);
        sceneList.add(scene2);
//        recyclerDevice.setLayoutManager(new FullyGridLayoutManager(recyclerDevice.getContext(), 3, GridLayoutManager.VERTICAL, false));


        final FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        recyclerDevice.setLayoutManager(manager);
//        recyclerDevice.setDivider() ;

//        recyclerDevice.addItemDecoration(new ListViewDecoration());// 添加分割线。
//        recyclerDevice.setNestedScrollingEnabled(false);
        recyclerDevice.setAdapter(new MainDevicesAdapter(getActivity(), listMainList));

        recyclerScene.setLayoutManager(new FullyGridLayoutManager(recyclerScene.getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerScene.setAdapter(new MainSceneAdapter(getActivity(), sceneList));
        recyclerScene.setNestedScrollingEnabled(false);

    }

    private List<String> mockData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add("Item:" + i);
        }

        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_more:
                MyApplication.getLogger().e("点击：");
                final CharSequence[] items = new CharSequence[]{"添加设备", "添加场景"};
                MenuDialog menuDialog = new MenuDialog(getActivity());
                menuDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MyApplication.getLogger().e("点击：" + items[which]);
                        switch (which) {
                            case 0:
                                Bundle paramBundle = new Bundle();
                                paramBundle.putBoolean(Constant.IS_DEVICE, false);
                                startActivityForName("com.heiman.gateway.LTLinkActivity", paramBundle);
                                break;
                            case 1:
                                break;
                        }
                    }
                });
                menuDialog.show();
                break;
            case R.id.title_bar_return:
                ((MainActivity) getActivity()).openDrawers();
                break;

        }
    }

    private GroupAdapter groupAdapter;
    private ArrayList<String> groups;
    private PopupWindow mPopupWindow;
    private View contentView;
    private ListView listView;

    private void showPopwindow(View parent) {
        if (mPopupWindow == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(getActivity());
            contentView = mLayoutInflater.inflate(R.layout.group_list, null);

            listView = (ListView) contentView.findViewById(R.id.lv_group);

            // 加载数据
            groups = new ArrayList<String>();

            groups.add("时间排序");

            groups.add("智能排序");

            groups.add("我的微博");

            groups.add("密友圈");

            groups.add("悄悄关注");

            groups.add("周边");

            groupAdapter = new GroupAdapter(getActivity(), groups);
            listView.setAdapter(groupAdapter);

            mPopupWindow = new PopupWindow(contentView, getActivity().getWindowManager()
                    .getDefaultDisplay().getWidth() / 2, 250);
        }
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);

        // 显示的位置为:屏幕的宽度的1/16
        int xPos = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 4;

        mPopupWindow.showAsDropDown(parent, 0, 0);

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        titleBarTitle.setText(groups.get(position));
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
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
