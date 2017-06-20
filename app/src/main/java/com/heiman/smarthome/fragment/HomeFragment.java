package com.heiman.smarthome.fragment;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.BaseFragment;
import com.heiman.baselibrary.Constant;
import com.heiman.baselibrary.Json.HeimanCom;
import com.heiman.baselibrary.Json.SmartPlug;
import com.heiman.baselibrary.http.HttpManage;
import com.heiman.baselibrary.http.XlinkUtils;
import com.heiman.baselibrary.manage.DeviceManage;
import com.heiman.baselibrary.manage.RoomManage;
import com.heiman.baselibrary.manage.SubDeviceManage;
import com.heiman.baselibrary.mode.Room;
import com.heiman.baselibrary.mode.Scene;
import com.heiman.baselibrary.mode.SubDevice;
import com.heiman.baselibrary.mode.XlinkDevice;
import com.heiman.baselibrary.utils.SmartHomeUtils;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.activity.AddDeviceActivity;
import com.heiman.smarthome.activity.MainActivity;
import com.heiman.smarthome.activity.SceneManaActivity;
import com.heiman.smarthome.adapter.MainDevicesAdapter;
import com.heiman.smarthome.adapter.MainSceneAdapter;
import com.heiman.smarthome.modle.ListMain;
import com.heiman.utils.AnimationUtil;
import com.heiman.utils.LogUtil;
import com.heiman.utils.UsefullUtill;
import com.heiman.utils.ViewUtils;
import com.heiman.widget.dialog.MenuDialog;
import com.heiman.widget.helper.OnStartDragListener;
import com.heiman.widget.helper.SimpleItemTouchHelperCallback;
import com.heiman.widget.recyclerview.FullyGridLayoutManager;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter;
import de.greenrobot.event.EventBus;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * @Author : 肖力
 * @Time :  2017/5/8 16:08
 * @Description :
 * @Modify record :
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, OnStartDragListener {
    private FrameLayout titleBar;
    private ImageView titleBarReturn;
    private TextView titleBarTitle;
    private MaterialSpinner titleBarTitleSpinner;
    private TextView subTitleBarTitle;
    private ImageView titleBarMore;
    private ImageView titleBarRedpoint;
    private ImageView titleBarShare;

    private SwipeRefreshLayout swipeLayout;
    private FamiliarRecyclerView recyclerScene;
    private FamiliarRecyclerView recyclerDevice;
    private List<ListMain> listMainList;
    private MainDevicesAdapter mainDevicesAdapter;
    private MainSceneAdapter mainSceneAdapter;
    private View viewTopLine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidget(view);
//        initDevice();
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessage(3);
    }

    private void initDevice() {
        HttpManage.getInstance().getSubscribe(MyApplication.getMyApplication(), new HttpManage.ResultCallback<String>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                MyApplication.getLogger().e("获取订阅设备出错:" + error.getMsg() + "\t" + error.getCode());
            }

            @Override
            public void onSuccess(int code, String response) {
                MyApplication.getLogger().json(response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray list = object.getJSONArray("list");
                    int iSize = list.length();
                    for (int i = 0; i < iSize; i++) {
                        JSONObject jsonObj = list.getJSONObject(i);
                        final XlinkDevice xlinkDevice = SmartHomeUtils.subscribeToDevice(jsonObj.toString());
                        if (xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS1GW_NEW || xlinkDevice.getDeviceType() == Constant.DEVICE_TYPE.DEVICE_WIFI_GATEWAY_HS2GW) {
                            ((MainActivity) getActivity()).setDevice(xlinkDevice);
                            ((MainActivity) getActivity()).isDevice = true;
                            if (SmartHomeUtils.isEmptyString(xlinkDevice.getAccessAESKey())) {
                                List<String> OID = new ArrayList<String>();
                                OID.add(HeimanCom.COM_GW_OID.GET_AES_KEY);
                                String AESK = HeimanCom.getOID(SmartPlug.mgetSN(), 0, OID);
                                BaseApplication.getLogger().json(AESK);
                                ((MainActivity) getActivity()).sendData(AESK, false);
                            } else {
                                List<String> OID = new ArrayList<String>();
                                OID.add(HeimanCom.COM_GW_OID.GW_SUB);
                                OID.add(HeimanCom.COM_GW_OID.GW_SUB_SS);
                                String AESK = HeimanCom.getOID(SmartPlug.mgetSN(), 0, OID);
                                BaseApplication.getLogger().json(AESK);
                                ((MainActivity) getActivity()).sendData(AESK, false);
                            }
                        }
//                        Message msg = new Message();
//                        msg.what = 5;
//                        msg.obj = xlinkDevice;
//                        handler.sendMessage(msg);
                    }
                    EventBus.getDefault().post("get bind devices");
                    handler.sendEmptyMessage(2);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void checkDeviceUp(XlinkDevice xlinkDevice) {
        HttpManage.getInstance().onVersion(getActivity(), xlinkDevice.getProductId(), xlinkDevice.getDeviceId() + "", new HttpManage.ResultCallback<String>() {
            @Override
            public void onError(Header[] headers, HttpManage.Error error) {
                MyApplication.getLogger().e("检测固件升级:" + error.getMsg() + "\t" + error.getCode());
            }

            @Override
            public void onSuccess(int code, String response) {
                Message msg = new Message();
                msg.what = 4;
                msg.obj = response;
                handler.sendMessage(msg);
            }
        });
    }

    private void refreshDevice() {
        listMainList.clear();
        List<XlinkDevice> xlinkDeviceList = DeviceManage.getInstance().getDevices();
        for (XlinkDevice xlinkDevice : xlinkDeviceList) {
            listMainList.add(new ListMain(xlinkDevice.getDeviceMac(), "", false, xlinkDevice.getDeviceType()));
        }

        List<SubDevice> subDeviceList = SubDeviceManage.getInstance().getDevices();
        for (SubDevice subDevice : subDeviceList) {
            listMainList.add((new ListMain(subDevice.getDeviceMac(), subDevice.getZigbeeMac(), true, subDevice.getDeviceType())));
        }
        mainDevicesAdapter.notifyDataSetChanged();
    }

    private void initWidget(View view) {
        viewTopLine = view.findViewById(R.id.view_top_line);
        titleBar = (FrameLayout) view.findViewById(R.id.title_bar);
        titleBarReturn = (ImageView) view.findViewById(R.id.title_bar_return);
        titleBarTitle = (TextView) view.findViewById(R.id.title_bar_title);
        titleBarTitleSpinner = (MaterialSpinner) view.findViewById(R.id.title_bar_title_spinner);
        subTitleBarTitle = (TextView) view.findViewById(R.id.sub_title_bar_title);
        titleBarMore = (ImageView) view.findViewById(R.id.title_bar_more);
        titleBarRedpoint = (ImageView) view.findViewById(R.id.title_bar_redpoint);
        titleBarShare = (ImageView) view.findViewById(R.id.title_bar_share);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        recyclerScene = (FamiliarRecyclerView) view.findViewById(R.id.recycler_scene);
        recyclerDevice = (FamiliarRecyclerView) view.findViewById(R.id.recycler_device);
//        recyclerDevice.addHeaderView(HeaderAndFooterViewUtil.getHeadView(getActivity(), true, 0x00000000, getResources().getString(R.string.MyDevice)));
        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
        titleBarReturn.setImageResource(R.mipmap.home_menu);
        titleBarMore.setImageResource(R.mipmap.home_add);
        titleBarTitle.setVisibility(View.VISIBLE);
        Resources res = this.getContext().getResources();
        final Bitmap img = BitmapFactory.decodeResource(res, R.drawable.homedrop);
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f);
        final int width = img.getWidth();
        final int height = img.getHeight();
        Bitmap img_a = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        final Drawable drawable = new BitmapDrawable(img_a);
        titleBarTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

        titleBarTitleSpinner.setVisibility(View.GONE);
        titleBarMore.setVisibility(View.VISIBLE);
        titleBarTitleSpinner.setTextColor(getResources().getColor(R.color.class_V));
        titleBarTitle.setText(getResources().getString(R.string.Home));
        titleBarTitle.setTextColor(getResources().getColor(R.color.class_V));

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
//                showPopRoomTopList(getActivity(), titleBarTitle, 0, -(titleBarTitle.getHeight() + titleBarTitle.getHeight()), mockData());
                List<Room> roomArrayList = RoomManage.getInstance().getHome();
                roomArrayList.add(0, new Room(getResources().getString(R.string.Default_room), ""));
                showLaunchCondition(roomArrayList);
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
        handler.sendEmptyMessage(2);

        List<Scene> sceneList = new ArrayList<Scene>();


//        final FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3);
//        manager.setOrientation(GridLayoutManager.VERTICAL);
//        manager.setSmoothScrollbarEnabled(true);
//        recyclerDevice.setLayoutManager(manager);
        recyclerDevice.setItemAnimator(new LandingAnimator());
        recyclerDevice.setHasFixedSize(true);
        recyclerDevice.setNestedScrollingEnabled(false);
//        recyclerDevice.setDivider() ;
//        recyclerDevice.addItemDecoration(new ListViewDecoration());// 添加分割线。
//        recyclerDevice.setNestedScrollingEnabled(false);
        mainDevicesAdapter = new MainDevicesAdapter(getActivity(), listMainList);
        recyclerDevice.setAdapter(mainDevicesAdapter);
        mainSceneAdapter = new MainSceneAdapter(getActivity(), sceneList);
        recyclerScene.setLayoutManager(new FullyGridLayoutManager(recyclerScene.getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerScene.setAdapter(mainSceneAdapter);
        recyclerScene.setNestedScrollingEnabled(false);

        final int spanCount = getResources().getInteger(R.integer.grid_columns);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerScene.setHasFixedSize(true);
        recyclerScene.setLayoutManager(layoutManager);
        //垂直+水平分割线
//        recyclerScene.addItemDecoration(new GridDividerItemDecortion(1, getResources().getColor(R.color.grid_dr)));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mainSceneAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerScene);
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
                final CharSequence[] items = new CharSequence[]{getString(R.string.add_device), getString(R.string.add_sencen)};
                MenuDialog menuDialog = new MenuDialog(getActivity());
                menuDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!UsefullUtill.judgeClick(R.layout.fragment_scene, 500)) {
                            MyApplication.getLogger().e("点击过快！");
                            return;
                        }
                        MyApplication.getLogger().e("点击：" + items[which]);
                        switch (which) {
                            case 0:
                                Bundle paramBundle = new Bundle();
                                paramBundle.putBoolean(Constant.IS_SUB, false);
                                openActivity(AddDeviceActivity.class, paramBundle);

//                                Bundle paramBundle = new Bundle();
//                                paramBundle.putBoolean(Constant.IS_DEVICE, false);
//                                startActivityForName("com.heiman.gateway.LTLinkActivity", paramBundle);
                                break;
                            case 1:
                                openActivity(SceneManaActivity.class);
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

    private PopupWindow mPopupWindow;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case 1:
                    swipeLayout.setRefreshing(false);
                    break;
                case 2:
                    refreshDevice();
                    break;
                case 3:
                    initDevice();
                    break;
                case 4:
//                    if (msg.obj != null) {
//                        MyApplication.getLogger().json(msg.obj.toString());
//                        Gson gson = new Gson();
//                        DeviceUp deviceUp = gson.fromJson(msg.obj.toString(), DeviceUp.class);
//                        if (deviceUp.getNewest()>deviceUp.getCurrent()){
//
//                        }
//                    }
                    break;
                case 5:
                    checkDeviceUp((XlinkDevice) msg.obj);
                    break;

            }
        }
    };

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.sendEmptyMessage(3);//更新数据
                    Thread.sleep(2000); //然刷新控件停留两秒后消失
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private ItemTouchHelper mItemTouchHelper;

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


    private void showLaunchCondition(final List<Room> conditions) {
        Resources res = this.getContext().getResources();
        final Bitmap img = BitmapFactory.decodeResource(res, R.drawable.homedrop);
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        matrix.postScale(1.5f, 1.5f);
        final int width = img.getWidth();
        final int height = img.getHeight();
        Bitmap img_a = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        final Drawable drawable = new BitmapDrawable(img_a);
        titleBarTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

        View popupView = getActivity().getLayoutInflater().inflate(R.layout.layout_launch_condition, null);
        FamiliarRecyclerView recyclerCondition = (FamiliarRecyclerView) popupView.findViewById(R.id.recycler_conditions);
//        List<String> conditions = Arrays.asList(getResources().getStringArray(R.array.default_condition));
        LogUtil.e("conditions:" + conditions.toString());
//        LaunchConditionAdapter launchConditionAdapter = new LaunchConditionAdapter(getActivity(), conditions, this);
        FamiliarEasyAdapter<Room> mAdapter = new FamiliarEasyAdapter<Room>(getActivity(), R.layout.item_condition, conditions) {
            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                TextView txtCondition = (TextView) holder.findView(R.id.txt_launch_condition);
                Room condition = conditions.get(position);
                txtCondition.setText(condition.getRoom_name());
                txtCondition.setTag(condition);
            }
        };

        recyclerCondition.setAdapter(mAdapter);
        recyclerCondition.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Room condition = conditions.get(position);
                MyApplication.getLogger().i(condition.getRoom_name());
                titleBarTitle.setText(condition.getRoom_name());
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //获取popupwindow高度确定动画开始位置
        int contentHeight = ViewUtils.getViewMeasuredHeight(popupView);
        MyApplication.getLogger().i("contentview height=" + contentHeight);
        final int fromYDelta = -contentHeight - 50;
        MyApplication.getLogger().i("fromYDelta=" + fromYDelta);

        final WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        mPopupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(getActivity(), fromYDelta));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);

                Matrix matrix = new Matrix();
                matrix.postScale(1.5f, 1.5f);
                matrix.postRotate(360);
                Bitmap img_a = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
                Drawable drawable = new BitmapDrawable(img_a);

                titleBarTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(getActivity(), fromYDelta));
                mPopupWindow.getContentView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();
                    }
                }, AnimationUtil.ANIMATION_OUT_TIME);
            }
        });
        mPopupWindow.showAsDropDown(viewTopLine);
    }

}
